package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.FileUtil;
import cn.sf_soft.common.util.NumberToCN;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.dao.SaleContractDao;
import cn.sf_soft.vehicle.contract.model.*;
import cn.sf_soft.vehicle.contract.service.SaleContractService;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.VwInterestedCustomers;
import cn.sf_soft.vehicle.customer.model.VwStatEffectiveCustomer;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.sql.BatchUpdateException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by henry on 2018/4/28.
 */

@Service("saleContractService")
public class SaleContractServiceImpl implements SaleContractService {

    public final String CODE_RULE_NO = "VSC_NO";

    @Resource
    SaleContractDao saleContractDao;

    @Resource
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private FileService fileService;

    @Override
    public PageModel getContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getContractList(filter, orderBy, pageNo, pageSize);
    }



    @Override
    public SysUnits getLoginDepartment(){
        SysUsers user = getLoginUser();
        return saleContractDao.get(SysUnits.class, user.getLoginDepartmentId());
    }


    @Override
    public String generateContractHtml(String contractNo) throws IOException, TemplateException {
        Map<String,Object> variables = new HashMap<String,Object>();

        Map<String, Object> filter = new HashMap<String, Object>(3);
        filter.put("contractNo", contractNo);
        PageModel page = getContractList(filter, null, 1, 1);
        if(page.getData() == null && page.getData().size() == 0){
            throw new ServiceException("未找到该合同:"+contractNo);
        }

        variables.put("contract",page.getData().get(0));
        variables.put("buyer",getBuyerByContractNo(contractNo));
        variables.put("seller",getSellerByContractNo(contractNo));
        variables.put("contractMoneyCN", NumberToCN.number2CNMontrayUnit(new BigDecimal(((HashMap)page.getData().get(0)).get("contractMoney").toString())).toString());

        page = getGroupList(filter, null, 1, 999);
        variables.put("vehicleGroupList", page.getData());
        List<VwVehicleSaleContractDetailGroups> groups = page.getData();

        filter.clear();
        List<String> vehicleIds = new ArrayList<String>(page.getData().size());
        for(Object model : page.getData()){
            vehicleIds.add(((VwVehicleSaleContractDetailGroups)model).getVnoId());
        }
        filter.put("selfId__in", "('" + StringUtils.join(vehicleIds, ",") + "')");
        page = getVehicleList(filter, null, 1, 999);
        variables.put("vehicleModelList", page.getData());

        List<List> insuranceListGroup = new ArrayList<List>(groups.size());
        List<List> invoicesListGroup = new ArrayList<List>(groups.size());
        List<List> chargeListGroup = new ArrayList<List>(groups.size());
        List<List> presentListGroup = new ArrayList<List>(groups.size());
        List<List> itemListGroup = new ArrayList<List>(groups.size());


        for(VwVehicleSaleContractDetailGroups group: groups){
            filter.clear();
            filter.put("contractNo", contractNo);
            filter.put("groupId", group.getGroupId());
            insuranceListGroup.add(getInsuranceList(filter, null, 1, 999).getData());
            invoicesListGroup.add(getInvoicesList(filter, null, 1, 999).getData());
            chargeListGroup.add(getChargeList(filter, null, 1, 999).getData());
            presentListGroup.add(getPresentList(filter, null, 1, 999).getData());
            itemListGroup.add(getItemList(filter, null, 1, 999).getData());
        }

        variables.put("insuranceListGroup", insuranceListGroup);
        variables.put("invoicesListGroup", invoicesListGroup);
        variables.put("chargeListGroup", chargeListGroup);
        variables.put("presentListGroup", presentListGroup);
        variables.put("itemListGroup", itemListGroup);

        String htmlText = FileUtil.generateHtml("saleContract.ftl",variables);

        return htmlText;
    }

    @Override
    public PageModel getBudgetChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getBudgetChargeCatalogList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel submitQuotation(String quotationId) {
        Map<String, Object> filter = new HashMap<String, Object>();

        if(quotationId == null || quotationId.length() == 0){
            throw new ServiceException("缺少报价单ID");
        }
        VehicleSaleQuotation quotation = saleContractDao.get(VehicleSaleQuotation.class, quotationId);
        if(quotation == null){
            throw new ServiceException("未找到该报价单");
        }
        if(quotation.getContractNo() != null && quotation.getContractNo().length() > 0){
            filter.clear();
            filter.put("contractNo", quotation.getContractNo());
            return getContractList(filter, "contractNo", 1, 999);
        }
        Map<String, Object> postData = saleContractDao.toMap(quotation);

        Double insuranceMoney = Double.valueOf(0);
        Double itemMoney = Double.valueOf(0);
        Double chargeMoney =Double.valueOf(0);
        Double presentMoney = Double.valueOf(0);
        Double insurancePf = Double.valueOf(0);
        Double presentPf = Double.valueOf(0);
        Double conversionPf =Double.valueOf(0);
        Double chargePf = Double.valueOf(0);


        SysUsers user = getLoginUser();

        if(postData.containsKey("pics") && postData.get("pics")!=null) {
            String pics = postData.get("pics").toString();
            pics = fileService.addPicsToFtp(user,quotation.getContractNo(),pics);
            postData.put("pics", pics);
        }

        if(postData.containsKey("fileUrls") && postData.get("fileUrls")!=null) {
            String fileUrls = postData.get("fileUrls").toString();
            fileUrls = fileService.addAttachmentsToFtp(user,quotation.getContractNo(),fileUrls);
            postData.put("fileUrls", fileUrls);
        }

        VehicleSaleContracts contract = createContract(postData);
        quotation.setContractNo(contract.getContractNo());
        VehicleSaleContractDetailGroups group = createGroup(saleContractDao.toMap(quotation));
        saleContractDao.update(quotation);

        List<VehicleSaleContractInsuranceGroups> insuranceList = new ArrayList<>();
        List<VehicleSaleContractItemGroups> itemList = new ArrayList<>();
        List<VehicleSaleContractChargeGroups> chargeList = new ArrayList<>();
        List<VehicleSaleContractPresentGroups> presentList = new ArrayList<>();
        List<VehicleLoanBudgetDetails> budgetList = new ArrayList<>();

        filter.put("quotationId", quotationId);
        PageModel page = restList(VehicleSaleQuotationInsurance.class, filter, "quotationId", 1, 999);
        List<VehicleSaleQuotationInsurance> insurances = page.getData();
        if(insurances != null && insurances.size() > 0){
            for(VehicleSaleQuotationInsurance insurance : insurances){
                if(insurance.getCategoryIncome() != null)insuranceMoney += insurance.getCategoryIncome().doubleValue();
                postData = saleContractDao.toMap(insurance);
                postData.put("groupId", group.getGroupId());
                postData.put("contractNo", contract.getContractNo());
                insuranceList.add(createInsurance(postData));
            }
        }

        page = restList(VehicleSaleQuotationItem.class, filter, "quotationId", 1, 999);
        List<VehicleSaleQuotationItem> items = page.getData();
        if(items != null && items.size() > 0){
            for(VehicleSaleQuotationItem item : items){
                if(item.getIncome() !=  null) itemMoney+=item.getIncome().doubleValue();
                if(item.getItemCost() != null) conversionPf+=item.getItemCost().doubleValue();
                postData = saleContractDao.toMap(item);
                postData.put("groupId", group.getGroupId());
                postData.put("contractNo", contract.getContractNo());
                itemList.add(createItem(postData));
            }
        }

        page = restList(VehicleSaleQuotationCharge.class, filter, "quotationId", 1, 999);
        List<VehicleSaleQuotationCharge> charges = page.getData();
        if(charges != null && charges.size() > 0){
            for(VehicleSaleQuotationCharge charge : charges){
                if(charge.getIncome() != null ) chargeMoney+=charge.getIncome().doubleValue();
                postData = saleContractDao.toMap(charge);
                postData.put("groupId", group.getGroupId());
                postData.put("contractNo", contract.getContractNo());
                chargeList.add(createCharge(postData));
            }
        }

        page = restList(VehicleSaleQuotationPresent.class, filter, "quotationId", 1, 999);
        List<VehicleSaleQuotationPresent> presents = page.getData();
        if(presents != null && presents.size() > 0){
            for(VehicleSaleQuotationPresent present : presents){
                if(present.getIncome() != null) {
                    presentMoney += present.getIncome().doubleValue();
                }
                if(present.getCostRecord() != null) {
                    presentPf += present.getCostRecord().doubleValue();
                }
                postData = saleContractDao.toMap(present);
                postData.put("groupId", group.getGroupId());
                postData.put("contractNo", contract.getContractNo());
                presentList.add(createPresent(postData));
            }
        }

//        page = restList(VehicleSaleQuotationLoanBudget.class, filter, "quotationId", 1, 999);
//        List<VehicleSaleQuotationLoanBudget> loans = page.getData();
//        if(presents != null && presents.size() > 0){
//            for(VehicleSaleQuotationLoanBudget loan : loans){
//                postData = saleContractDao.toMap(loan);
//                // postData.put("groupId", group.getGroupId());
//                postData.put("saleContractNo", contract.getContractNo());
//                budgetList.add(createBudgetDetail(postData));
//            }
//        }
        contract = saleContractDao.get(VehicleSaleContracts.class, contract.getContractNo());

        Double contractMoney = Double.valueOf(0);
        contractMoney += (quotation.getVehiclePrice() == null? 0 : quotation.getVehiclePrice().doubleValue())
                *(quotation.getVehicleQuantity()!= null ? quotation.getVehicleQuantity() : 0)+ (insuranceMoney+itemMoney+chargeMoney+presentMoney)*(quotation.getVehicleQuantity()!=null?quotation.getVehicleQuantity():0);
        contract.setContractMoney(contractMoney);
        VwStatEffectiveCustomer customer = saleContractDao.get(VwStatEffectiveCustomer.class, quotation.getCustomerId());

        if(customer == null){
            throw new ServiceException("该客户不存在");
        }

        contract.setCustomerId(quotation.getCustomerId());
        contract.setCustomerName(customer.getObjectName());
        contract.setCustomerMobile(customer.getMobile());
        contract.setCustomerPhone(customer.getPhone());
        contract.setCustomerCertificateNo(customer.getCertificateNo());
        contract.setCustomerCertificateType(customer.getCertificateType());
        contract.setCustomerCity(customer.getCity());
        contract.setLinkman(customer.getLinkman());
        contract.setCustomerId(customer.getObjectId());
        contract.setCustomerNo(customer.getObjectNo());
        contract.setCusotmerSex(customer.getSex());
        contract.setCustomerAddress(customer.getAddress());
        contract.setCustomerArea(customer.getArea());
        contract.setCustomerEmail(customer.getEmail());
        contract.setCustomerFax(customer.getCertificateNo());
        contract.setCustomerProvince(customer.getProvince());
        contract.setCustomerProfession(customer.getProfession());

        contract.setInsuranceAmt(insuranceMoney);
        contract.setConversionAmt(itemMoney);
        contract.setPresentAmt(presentMoney);
        contract.setExpenseAmt(chargeMoney);


        contract.setBuyType(quotation.getBuyType());
        contract.setPlanFinishTime(quotation.getPlanFinishTime());

        contract.setSellerId(user.getUserId());
        contract.setSeller(user.getUserFullName());
        contract.setContractComment(quotation.getVehicleComment());

        saleContractDao.update(contract);


        filter.clear();
        filter.put("contractNo", contract.getContractNo());
        List<VehicleSaleContracts> list = new ArrayList<>(1);
        list.add(contract);
        return new PageModel(list, 1, 1, 1);
    }

    @Override
    public String generateQuotationHtml(String objId) throws IOException, TemplateException {
        Map<String,Object> variables = new HashMap<String,Object>();

        VehicleSaleQuotation quotation = saleContractDao.get(VehicleSaleQuotation.class, objId);
        if(quotation == null){
            throw new ServiceException("未找到该报价单");
        }

        variables.put("buyer", saleContractDao.get(VwStatEffectiveCustomer.class, quotation.getCustomerId()));
        variables.put("seller",getSeller(getLoginUser()));

        Double contractMoney = Double.valueOf(0);

        variables.put("contractMoney", contractMoney);

        variables.put("contractMoneyCN", NumberToCN.number2CNMontrayUnit(new BigDecimal(contractMoney)));

        Map<String, Object> filter = new HashMap<String, Object>(3);
        filter.put("quotationId", objId);
        PageModel page = restList(VehicleSaleQuotation.class, filter, "quotationId DESC", 1, 1);
        variables.put("vehicleGroupList", page.getData());
        List<VehicleSaleQuotation> groups = page.getData();

        filter.clear();
        List<String> vehicleIds = new ArrayList<String>(page.getData().size());
        for(Object model : page.getData()){
            vehicleIds.add(((VehicleSaleQuotation)model).getVnoId());
        }
        filter.put("selfId__in", "('" + StringUtils.join(vehicleIds, ",") + "')");
        page = getVehicleList(filter, null, 1, 999);
        variables.put("vehicleModelList", page.getData());

        List<List> insuranceListGroup = new ArrayList<List>(groups.size());
        // List<List> invoicesListGroup = new ArrayList<List>(groups.size());
        List<List> chargeListGroup = new ArrayList<List>(groups.size());
        List<List> presentListGroup = new ArrayList<List>(groups.size());
        List<List> itemListGroup = new ArrayList<List>(groups.size());


        for(VehicleSaleQuotation group: groups){
            filter.clear();
            filter.put("quotationId", objId);
            insuranceListGroup.add(restList(VehicleSaleQuotationInsurance.class, filter, "quotationId DESC", 1, 999).getData());
            chargeListGroup.add(restList(VehicleSaleQuotationCharge.class,filter, "quotationId DESC", 1, 999).getData());
            presentListGroup.add(restList(VehicleSaleQuotationPresent.class,filter, "quotationId DESC", 1, 999).getData());
            itemListGroup.add(restList(VehicleSaleQuotationItem.class,filter, "quotationId DESC", 1, 999).getData());
        }

        variables.put("insuranceListGroup", insuranceListGroup);
        // variables.put("invoicesListGroup", invoicesListGroup);
        variables.put("chargeListGroup", chargeListGroup);
        variables.put("presentListGroup", presentListGroup);
        variables.put("itemListGroup", itemListGroup);

        String htmlText = FileUtil.generateHtml("saleQuotation.ftl",variables);

        return htmlText;
    }

    @Override
    public Map<String, Object> getContractTotal(Map<String, Object> filter) {
        return saleContractDao.getContractTotal(filter);
    }

    private SysUsers getLoginUser(){
        return (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);
    }

    @Override
    public VehicleSaleContracts createContract(Map<String, Object> data) {
        SysUsers user = getLoginUser();
        Timestamp now = new Timestamp(new Date().getTime());
        VehicleSaleContracts contract = new VehicleSaleContracts();

        data.put("status", 10);
        data.put("contractStatus", 0);
        data.put("userId",  user.getUserId());
        data.put("userName",  user.getUserName());
        data.put("userNo",  user.getUserNo());
        data.put("stationId", user.getLoginStationId());
        data.put("creator", user.getUserFullName());
        data.put("createTime",  now);
        SysUnits unit = getLoginDepartment();
        data.put("departmentId", unit.getUnitId());
        data.put("departmentNo", unit.getUnitNo());
        data.put("departmentName",  unit.getUnitName());

        String documentNo = sysCodeService.createSysCodeRules(CODE_RULE_NO, user.getLoginStationId());
        data.put("documentNo", documentNo);
        if(!data.containsKey("contractNo")) {
            data.put("contractNo", documentNo);
        }
        if(!data.containsKey("contractCode")) {
            data.put("contractCode", documentNo);
        }


        saleContractDao.saveMapToObject(data, contract);
        saleContractDao.save(contract);
        return contract;
    }

    @Override
    public VehicleSaleContracts updateContract(String objId, Map<String, Object> data) {
        VehicleSaleContracts contract = saleContractDao.get(VehicleSaleContracts.class, objId);
        if(contract == null){
            throw new ServiceException("未找到该合同");
        }
        SysUsers user = getLoginUser();
        Timestamp now = new Timestamp(new Date().getTime());
        data.put("modifyTime",  now);
        data.put("modifier",  user.getUserFullName());

        if(data.containsKey("pics") && data.get("pics")!=null && data.get("pics").equals(contract.getPics())) {
            String pics = data.get("pics").toString();
            pics = fileService.addPicsToFtp(user,contract.getContractNo(), pics);
            data.put("pics", pics);
        }

        if(data.containsKey("fileUrls") && data.get("fileUrls")!=null && data.get("fileUrls").equals(contract.getFileUrls())) {
            String fileUrls = data.get("fileUrls").toString();
            fileUrls = fileService.addAttachmentsToFtp(user,contract.getContractNo(),fileUrls);
            data.put("fileUrls", fileUrls);
        }

        saleContractDao.saveMapToObject(data, contract);
        saleContractDao.update(contract);
        return contract;
    }

    @Override
    public VehicleSaleContracts submitContract(String objId) {
        SysUsers user = getLoginUser();
        Timestamp now = new Timestamp(new Date().getTime());
        VehicleSaleContracts contract = saleContractDao.get(VehicleSaleContracts.class, objId);
        if(contract == null){
            throw new ServiceException("未找到该合同");
        }
//        contract.setStatus((short)20);
//        contract.setContractStatus((short)20);
        contract.setModifyTime(new Timestamp(new Date().getTime()));
        contract.setModifier(getLoginUser().getUserFullName());
        contract.setSubmitStationId(user.getLoginStationId());
        contract.setSubmitStationName(user.getStationName());
        contract.setSubmitTime(now);

        saleContractDao.update(contract);
        return contract;
    }

//    @Override
//    public VehicleSaleContracts cancelContract(String objId) {
//        VehicleSaleContracts contract = saleContractDao.get(VehicleSaleContracts.class, objId);
//        if(contract == null){
//            throw new ServiceException("未找到该合同");
//        }
//        contract.setStatus((short)10);
//        contract.setContractStatus((short)0);
//        contract.setModifyTime(new Timestamp(new Date().getTime()));
//        contract.setModifier(getLoginUser().getUserFullName());
//        // 撤回审批点等数据
//        approvalService.revokingRecord(getLoginUser(),contract.getDocumentNo());
//        saleContractDao.update(contract);
//        return contract;
//    }

    @Override
    public PageModel getGroupList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getGroupList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractDetailGroups createGroup(Map<String, Object> postJson) {
        VehicleSaleContractDetailGroups group = new VehicleSaleContractDetailGroups();
        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractDetailGroups updateGroup(String objId, Map<String, Object> postJson) {
        VehicleSaleContractDetailGroups group = saleContractDao.get(VehicleSaleContractDetailGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该车型");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractDetailGroups removeGroup(String objId, Map<String, Object> postJson) {
        VehicleSaleContractDetailGroups group = saleContractDao.get(VehicleSaleContractDetailGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该车型");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getDetailList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractDetail createDetail(Map<String, Object> postJson) {
        VehicleSaleContractDetail detail = new VehicleSaleContractDetail();
        detail.setContractDetailId(UUID.randomUUID().toString());
        detail.setDocumentNo(UUID.randomUUID().toString());

        SysUsers user = getLoginUser();
        postJson.put("userId",  user.getUserId());
        postJson.put("userName",  user.getUserName());
        postJson.put("userNo",  user.getUserNo());

        SysUnits unit = getLoginDepartment();
        postJson.put("departmentId",  unit.getUnitId());
        postJson.put("departmentNo",  unit.getUnitNo());
        postJson.put("departmentName", unit.getUnitName());

        postJson.put("submitStationId",  user.getLoginStationId());
        postJson.put("submitStationName",  user.getStationName());

        saleContractDao.saveDetail(detail, postJson);

        VehicleStocks vehicle = saleContractDao.get(VehicleStocks.class, detail.getVehicleId());
        if(vehicle != null){
            vehicle.setStatus((short)1);
            saleContractDao.save(vehicle);
        }


        return detail;
    }

    @Override
    public VehicleSaleContractDetail updateDetail(String objId, Map<String, Object> postJson) {
        VehicleSaleContractDetail detail = saleContractDao.get(VehicleSaleContractDetail.class, objId);
        if(detail == null){
            throw new ServiceException("未找到该车辆详情");
        }
        SysUsers user = getLoginUser();
        Timestamp now = new Timestamp(new Date().getTime());
        postJson.put("modifyTime",  now);
        postJson.put("modifier",  user.getUserFullName());

        if(postJson.containsKey("vehicleId") && postJson.get("vehicleId") !=null && postJson.get("vehicleId").toString().length() > 0){
            VehicleStocks vehicle = saleContractDao.get(VehicleStocks.class, postJson.get("vehicleId").toString());
            if(vehicle != null){
                vehicle.setStatus((short)1);
                saleContractDao.save(vehicle);
            }
        }else if(detail.getVehicleId() != null && detail.getVehicleId().length() > 0){
            VehicleStocks vehicle = saleContractDao.get(VehicleStocks.class, detail.getVehicleId());
            if(vehicle != null){
                vehicle.setStatus((short)0);
                saleContractDao.save(vehicle);
            }
        }

        saleContractDao.updateDetail(detail, postJson);
        return detail;
    }

    @Override
    public VehicleSaleContractDetail removeDetail(String objId, Map<String, Object> postJson) {
        VehicleSaleContractDetail detail = saleContractDao.get(VehicleSaleContractDetail.class, objId);
        if(detail == null){
            throw new ServiceException("未找到该车辆详情");
        }

        if(detail.getVehicleId() == null){
            throw new ServiceException("未找到该配车信息");
        }

        VehicleStocks vehicle = saleContractDao.get(VehicleStocks.class, detail.getVehicleId());
        if(vehicle != null){
            vehicle.setStatus((short)0);
            saleContractDao.save(vehicle);
        }

        detail.setVehicleCardNo(null);
        detail.setVehicleCardTime(null);
        detail.setVehicleId(null);
        detail.setVehicleEngineNo(null);
        detail.setVehicleVin(null);
        detail.setVehicleVinNew(null);
        detail.setVehicleVno(null);
        detail.setVehicleVnoNew(null);
        detail.setVehicleCarriage(null);
        // detail.setVehicleColor(null);

        saleContractDao.save(detail);
        // saleContractDao.deleteDetail(detail);

        return detail;
    }

    @Override
    public PageModel getItemList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getItemList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractItemGroups createItem(Map<String, Object> postJson) {
        VehicleSaleContractItemGroups group = new VehicleSaleContractItemGroups();

        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractItemGroups updateItem(String objId, Map<String, Object> postJson) {
        VehicleSaleContractItemGroups group = saleContractDao.get(VehicleSaleContractItemGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该改装信息");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractItemGroups removeItem(String objId, Map<String, Object> postJson) {
        VehicleSaleContractItemGroups group = saleContractDao.get(VehicleSaleContractItemGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该改装信息");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getInsuranceList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getInsuranceList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractInsuranceGroups createInsurance(Map<String, Object> postJson) {
        VehicleSaleContractInsuranceGroups group = new VehicleSaleContractInsuranceGroups();
        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractInsuranceGroups updateInsurance(String objId, Map<String, Object> postJson) {
        VehicleSaleContractInsuranceGroups group = saleContractDao.get(VehicleSaleContractInsuranceGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该保险信息");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractInsuranceGroups removeInsurance(String objId, Map<String, Object> postJson) {
        VehicleSaleContractInsuranceGroups group = saleContractDao.get(VehicleSaleContractInsuranceGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该保险信息");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getChargeList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractChargeGroups createCharge(Map<String, Object> postJson) {
        VehicleSaleContractChargeGroups group = new VehicleSaleContractChargeGroups();

        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractChargeGroups updateCharge(String objId, Map<String, Object> postJson) {
        VehicleSaleContractChargeGroups group = saleContractDao.get(VehicleSaleContractChargeGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该费用信息");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractChargeGroups removeCharge(String objId, Map<String, Object> postJson) {
        VehicleSaleContractChargeGroups group = saleContractDao.get(VehicleSaleContractChargeGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该费用信息");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getPresentList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getPresentList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleSaleContractPresentGroups createPresent(Map<String, Object> postJson) {
        VehicleSaleContractPresentGroups group = new VehicleSaleContractPresentGroups();

        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractPresentGroups updatePresent(String objId, Map<String, Object> postJson) {
        VehicleSaleContractPresentGroups group = saleContractDao.get(VehicleSaleContractPresentGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该精品信息");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleSaleContractPresentGroups removePresent(String objId, Map<String, Object> postJson) {
        VehicleSaleContractPresentGroups group = saleContractDao.get(VehicleSaleContractPresentGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该精品信息");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getInvoicesList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getInvoicesList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleInvoicesGroups createInvoices(Map<String, Object> postJson) {
        VehicleInvoicesGroups group = new VehicleInvoicesGroups();
        saleContractDao.saveGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleInvoicesGroups updateInvoices(String objId, Map<String, Object> postJson) {
        VehicleInvoicesGroups group = saleContractDao.get(VehicleInvoicesGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该发票信息");
        }
        saleContractDao.updateGroup(group, postJson);
        return group;
    }

    @Override
    public VehicleInvoicesGroups removeInvoices(String objId, Map<String, Object> postJson) {
        VehicleInvoicesGroups group = saleContractDao.get(VehicleInvoicesGroups.class, objId);
        if(group == null){
            throw new ServiceException("未找到该发票信息");
        }
        saleContractDao.deleteGroup(group);
        return group;
    }

    @Override
    public PageModel getVehicleList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getVehicleList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getChargeCatalogList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getInsuranceCompanyCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getInsuranceCompanyCatalogList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getServiceConsignsList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getServiceConsignsList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getBudgetList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getBudgetList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleLoanBudget createBudget(Map<String, Object> postJson) {
        VehicleLoanBudget budget = new VehicleLoanBudget();
        String documentNo = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        postJson.put("documentNo", documentNo);
        postJson.put("stationId", user.getLoginStationId());
        postJson.put("status", 10);
        postJson.put("creatorId", user.getUserId());
        postJson.put("createTime", now);
        postJson.put("userId", user.getUserId());

        SysUnits unit = getLoginDepartment();
        postJson.put("departmentId", unit.getUnitId());

        saleContractDao.saveMapToObject(postJson, budget);
        saleContractDao.save(budget);
        return budget;
    }

    @Override
    public VehicleLoanBudget updateBudget(String objId, Map<String, Object> postJson) {
        VehicleLoanBudget budget = saleContractDao.get(VehicleLoanBudget.class, objId);
        if(budget == null){
            throw new ServiceException("未找到该预算信息");
        }
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        postJson.put("modifyTime",  now);
        postJson.put("modifierId",  user.getUserId());

        saleContractDao.saveMapToObject(postJson, budget);
        saleContractDao.update(budget);
        return budget;
    }

    @Override
    public VehicleLoanBudget removeBudget(String objId, Map<String, Object> postJson) {
        VehicleLoanBudget budget = saleContractDao.get(VehicleLoanBudget.class, objId);
        if(budget == null){
            throw new ServiceException("未找到该预算信息");
        }
        saleContractDao.delete(budget);
        return budget;
    }

    @Override
    public Map<String, Object> getInitData() {
        return saleContractDao.getInitData();
    }

    @Override
    public PageModel getLoanContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getLoanContractList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleLoanContracts createLoanContract(Map<String, Object> postJson) {
        VehicleLoanContracts loan = new VehicleLoanContracts();
        String documentNo = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        postJson.put("slcCode", documentNo);
        postJson.put("stationId", user.getLoginStationId());
        postJson.put("status", 10);
        postJson.put("creatorId", user.getUserId());
        postJson.put("createTime", now);
        // postJson.put("userId", user.getUserId());
        // postJson.put("departmentId", user.getDepartment());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.save(loan);
        return loan;
    }

    @Override
    public VehicleLoanContracts updateLoanContract(String objId, Map<String, Object> postJson) {
        VehicleLoanContracts loan = saleContractDao.get(VehicleLoanContracts.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷合同");
        }
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        postJson.put("modifyTime",  now);
        postJson.put("modifierId",  user.getUserId());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.update(loan);
        return loan;
    }

    @Override
    public VehicleLoanContracts removeLoanContract(String objId, Map<String, Object> postJson) {
        VehicleLoanContracts loan = saleContractDao.get(VehicleLoanContracts.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷合同");
        }
        saleContractDao.delete(loan);
        return loan;
    }

    @Override
    public PageModel getBudgetDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getBudgetDetailList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleLoanBudgetDetails createBudgetDetail(Map<String, Object> postJson) {
        VehicleLoanBudgetDetails loan = new VehicleLoanBudgetDetails();
        String selfId = UUID.randomUUID().toString();
//        Timestamp now = new Timestamp(new Date().getTime());
//        SysUsers user = getLoginUser();
        postJson.put("selfId", selfId);
//        postJson.put("stationId", user.getLoginStationId());
//        postJson.put("status", 10);
//        postJson.put("creatorId", user.getUserId());
//        postJson.put("createTime", now);
        // postJson.put("userId", user.getUserId());
        // postJson.put("departmentId", user.getDepartment());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.save(loan);
        return loan;
    }

    @Override
    public VehicleLoanBudgetDetails updateBudgetDetail(String objId, Map<String, Object> postJson) {
        VehicleLoanBudgetDetails loan = saleContractDao.get(VehicleLoanBudgetDetails.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷预算");
        }
//        Timestamp now = new Timestamp(new Date().getTime());
//        SysUsers user = getLoginUser();
//        postJson.put("modifyTime",  now);
//        postJson.put("modifierId",  user.getUserId());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.update(loan);
        return loan;
    }

    @Override
    public VehicleLoanBudgetDetails removeBudgetDetail(String objId, Map<String, Object> postJson) {
        VehicleLoanBudgetDetails loan = saleContractDao.get(VehicleLoanBudgetDetails.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷预算");
        }
        saleContractDao.delete(loan);
        return loan;
    }

    @Override
    public PageModel getBudgetChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.getBudgetChargeList(filter, orderBy, pageNo, pageSize);
    }

    @Override
    public VehicleLoanBudgetCharge createBudgetCharge(Map<String, Object> postJson) {
        VehicleLoanBudgetCharge loan = new VehicleLoanBudgetCharge();
        String selfId = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        postJson.put("selfId", selfId);
//        postJson.put("stationId", user.getLoginStationId());
//        postJson.put("status", 10);
//        postJson.put("creatorId", user.getUserId());
        postJson.put("createTime", now);
        postJson.put("creatorId", user.getUserId());
//        postJson.put("departmentId", user.getDepartment());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.save(loan);
        return loan;
    }

    @Override
    public VehicleLoanBudgetCharge updateBudgetCharge(String objId, Map<String, Object> postJson) {
        VehicleLoanBudgetCharge loan = saleContractDao.get(VehicleLoanBudgetCharge.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷费用信息");
        }
//        Timestamp now = new Timestamp(new Date().getTime());
//        SysUsers user = getLoginUser();
//        postJson.put("modifyTime",  now);
//        postJson.put("modifierId",  user.getUserId());

        saleContractDao.saveMapToObject(postJson, loan);
        saleContractDao.update(loan);
        return loan;
    }

    @Override
    public VehicleLoanBudgetCharge removeBudgetCharge(String objId, Map<String, Object> postJson) {
        VehicleLoanBudgetCharge loan = saleContractDao.get(VehicleLoanBudgetCharge.class, objId);
        if(loan == null){
            throw new ServiceException("未找到该消贷费用信息");
        }
        saleContractDao.delete(loan);
        return loan;
    }

    @Override
    public PageModel restList(Class type, Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        return saleContractDao.restList(type, filter, orderBy, pageNo, pageSize);
    }

    @Override
    public Object restCreate(Class type, Map<String, Object> data) {
        Object obj = null;
        try {
            obj = type.newInstance();
            saleContractDao.saveMapToObject(data, obj);
            saleContractDao.save(obj);
        } catch (InstantiationException | IllegalAccessException | DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ServiceException(e.getMessage());
        }
        return obj;
    }

    @Override
    public Object restUpdate(Class type, String objId, Map<String, Object> postJson) {
        Object obj = saleContractDao.get(type, objId);
        if(obj == null){
            throw new ServiceException("未找到该信息");
        }
        try {
            saleContractDao.saveMapToObject(postJson, obj);
            saleContractDao.update(obj);
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
        return obj;
    }

    @Override
    public Object restRemove(Class type, String objId) {
        Object obj = saleContractDao.get(type, objId);
        if(obj == null){
            throw new ServiceException("未找到该信息");
        }
        saleContractDao.delete(obj);
        return obj;
    }

    @Override
    public Map<String, Object> getBuyerByContractNo(String contractNo) {
        VehicleSaleContracts obj = saleContractDao.get(VehicleSaleContracts.class, contractNo);
        return saleContractDao.toMap(saleContractDao.get(BaseRelatedObjects.class, obj.getCustomerId()));
    }

    @Override
    public Map<String, Object> getSellerByContractNo(String contractNo) {
        Map<String, Object> result = new HashMap<String, Object>(12);
        VehicleSaleContracts obj = saleContractDao.get(VehicleSaleContracts.class, contractNo);
        result.put("SELLER", obj.getSeller());
        result.put("CORPORATION_NAME", sysOptionsDao.getOptionForString("CORPORATION_NAME", obj.getStationId()));
        result.put("CORPORATION_LEGAL_PERSON", sysOptionsDao.getOptionForString("CORPORATION_LEGAL_PERSON", obj.getStationId()));
        result.put("CORPORATION_ADDRESS", sysOptionsDao.getOptionForString("CORPORATION_ADDRESS", obj.getStationId()));
        result.put("CORPORATION_TAX_REGISTER_NO", sysOptionsDao.getOptionForString("CORPORATION_TAX_REGISTER_NO", obj.getStationId()));
        result.put("CORPORATION_PHONE_ONE", sysOptionsDao.getOptionForString("CORPORATION_PHONE_ONE", obj.getStationId()));
        result.put("CORPORATION_ACCOUNT_BANK", sysOptionsDao.getOptionForString("CORPORATION_ACCOUNT_BANK", obj.getStationId()));
        result.put("CORPORATION_ACCOUNT_NO", sysOptionsDao.getOptionForString("CORPORATION_ACCOUNT_NO", obj.getStationId()));
        result.put("CORPORATION_FAX", sysOptionsDao.getOptionForString("CORPORATION_FAX", obj.getStationId()));
        result.put("CORPORATION_POSTAL_CODE", sysOptionsDao.getOptionForString("CORPORATION_POSTAL_CODE", obj.getStationId()));
        return result;
    }

    @Override
    public Map<String, Object> getSeller(SysUsers user) {
        Map<String, Object> result = new HashMap<String, Object>(12);
        result.put("SELLER", user.getUserName());
        result.put("CORPORATION_NAME", sysOptionsDao.getOptionForString("CORPORATION_NAME", user.getLoginStationId()));
        result.put("CORPORATION_LEGAL_PERSON", sysOptionsDao.getOptionForString("CORPORATION_LEGAL_PERSON", user.getLoginStationId()));
        result.put("CORPORATION_ADDRESS", sysOptionsDao.getOptionForString("CORPORATION_ADDRESS", user.getLoginStationId()));
        result.put("CORPORATION_TAX_REGISTER_NO", sysOptionsDao.getOptionForString("CORPORATION_TAX_REGISTER_NO", user.getLoginStationId()));
        result.put("CORPORATION_PHONE_ONE", sysOptionsDao.getOptionForString("CORPORATION_PHONE_ONE", user.getLoginStationId()));
        result.put("CORPORATION_ACCOUNT_BANK", sysOptionsDao.getOptionForString("CORPORATION_ACCOUNT_BANK", user.getLoginStationId()));
        result.put("CORPORATION_ACCOUNT_NO", sysOptionsDao.getOptionForString("CORPORATION_ACCOUNT_NO", user.getLoginStationId()));
        result.put("CORPORATION_FAX", sysOptionsDao.getOptionForString("CORPORATION_FAX", user.getLoginStationId()));
        result.put("CORPORATION_POSTAL_CODE", sysOptionsDao.getOptionForString("CORPORATION_POSTAL_CODE", user.getLoginStationId()));
        return result;
    }

}
