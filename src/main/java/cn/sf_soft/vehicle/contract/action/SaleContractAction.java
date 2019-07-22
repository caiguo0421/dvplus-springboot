package cn.sf_soft.vehicle.contract.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.FileUtil;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.vehicle.contract.model.*;
import cn.sf_soft.vehicle.contract.service.SaleContractService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import cn.sf_soft.vehicle.contract.service.impl.QueryService;
import cn.sf_soft.vehicle.customer.service.CustomerService;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

/**
 * Created by henry on 2018/4/28.
 */
public class SaleContractAction extends BaseAction {


    private static final long serialVersionUID = -8091714193481147168L;
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractAction.class);
    @Resource
    private SaleContractService saleContractService;


    @Autowired
    private cn.sf_soft.vehicle.contract.service.impl.SaleContractService saleContractService1;

    @Autowired
    private QueryService queryService;

    private String jsonData;
    private String orderBy;
    private String objId;
    private String contractNo;
    private Boolean preview = false; //预览
    private String vehicleStatus;
    private String entityStatus;
    private String vehicleVno;
    private String keyword;
    private String vehicleIds;
    private String filter;
    private String sort;
    private Boolean queryDefault = false;
    private String contractDetailId;
    private String groupId;
    private String vehicleVin;
    private Boolean chooseVin = null;
    private Boolean match = null;

    public void setMatch(Boolean match) {
        this.match = match;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public void setChooseVin(Boolean chooseVin) {
        this.chooseVin = chooseVin;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setVehicleIds(String vehicleIds) {
        this.vehicleIds = vehicleIds;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setContractDetailId(String contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    private Map<String, Object> getPostJson() {
        try {
            HashMap json_map = gson.fromJson(jsonData, HashMap.class);
            return json_map;
        } catch (Exception e) {
            throw new ServiceException("提交数据不合法");
        }
    }

    public Boolean getQueryDefault() {
        return queryDefault;
    }

    public void setQueryDefault(Boolean queryDefault) {
        this.queryDefault = queryDefault;
    }


    /**
     * 获取价格体系中的最低限价设定
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleMinPrice() {
        setResponseCommonData(saleContractService1.getVehicleMinPrice(getPostJson()));
        return SUCCESS;
    }

    /**
     * 获取参考成本
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleReferenceCost() {
        setResponseCommonData(saleContractService1.getVehicleReferenceCost(getPostJson()));
        return SUCCESS;
    }

    /**
     * 查看车型的可用资源数目
     *
     * @return
     */
    @Access(pass = true)
    public String getCanUseQty() {
        setResponseCommonData(saleContractService1.getCanUseQty(vehicleVno));
        return SUCCESS;
    }

    /**
     * 初始化数据
     *
     * @return
     */
    @Access(pass = true)
    public String getInitData() {
        setResponseCommonData(saleContractService1.getInitData());
        return SUCCESS;
    }


    /**
     * 保存合同-（新）
     *
     * @return
     */
    @Access(pass = true)
    public String saveContract() {
        logger.debug(String.format("合同保存 OS_TYPE:%s,接收报文：%s", HttpSessionStore.getSessionOs(), jsonData));
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = saleContractService1.saveSaleContract(jsonObject);
        setResponseCommonData(rtnData);
        return SUCCESS;
    }


    /**
     * 保存合同-pc
     *
     * @return
     */
    @Access(pass = true)
    public String saveContractByPC() {
//        logger.debug(String.format("合同保存 OS_TYPE:%s,接收报文：%s", HttpSessionStore.getSessionOs(), jsonData));
        logger.debug("saveContractByPC 开始");
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        logger.debug("gson.fromJson 完成");
        Map<String, List<Object>> rtnData = saleContractService1.saveSaleContract(jsonObject);
        logger.debug("saveSaleContract 完成");
        setResponseData(rtnData); //pc的用setResponseData
        logger.debug("setResponseData 完成");
        return SUCCESS;
    }


    /**
     * @return
     */
    @Access(pass = true)
    public String getContractDetailByPC() {
        logger.debug(String.format("合同保存 OS_TYPE:%s,接收报文：%s", HttpSessionStore.getSessionOs(), jsonData));
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, PageModel<Object>> rtnData = queryService.getData(jsonObject);
        setResponseData(rtnData); //pc的用setResponseData
        return SUCCESS;
    }


    @Access(pass = true)
    public String getContractList() {
        PageModel pageModel = saleContractService1.getContractList(getPostJson(), orderBy, vehicleVin, chooseVin, match, keyword, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getContractTotal() {
        Map<String, Object> result = saleContractService1.getContractTotal(getPostJson(), vehicleVin, chooseVin, match);
        setResponseData(result);
        return SUCCESS;
    }


    /**
     * 配车时可选车辆
     *
     * @return
     */
    @Access(pass = true)
    public String getOptionalVehicles() {
//        Map<String, Object> param = new HashMap<>();
//        param.put("vehicleStatus", vehicleStatus);
//        param.put("entityStatus", entityStatus);
//        param.put("vehicleVno", vehicleVno);
//        param.put("keyword", keyword);
//        param.put("stationIds", stationIds);
        PageModel pageModel = saleContractService1.getOptionalVehicles(getPostJson(), keyword, stationIds, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }


    /**
     * 查看车辆的改装
     *
     * @return
     */
    @Access(pass = true)
    public String getOptionalVehiclesConversion() {
        PageModel pageModel = saleContractService1.getOptionalVehiclesConversion(vehicleIds);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }


    /**
     * 查询车辆的开票网员
     * @return
     */
    @Access(pass = true)
    public String getInvoiceAgency(){
        Object o = saleContractService1.getInvoiceAgency(vehicleIds);
        setResponseCommonData(o);
        return SUCCESS;
    }


    /**
     * 获取Vehicle列表
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleModel() {
        PageModel results = saleContractService1.getVehicleModel(keyword, getFilter(), sort, pageNo, pageSize);
        setResponseCommonData(results);
        return SUCCESS;
    }


    /**
     * 签约客户
     *
     * @return
     */
    @Access(pass = true)
    public String getSignedCustomers() {
        PageModel results = saleContractService1.getSignedCustomers(keyword, getFilter(), pageNo, pageSize);
        setResponseCommonData(results);
        return SUCCESS;
    }


    /**
     * 线索客户
     *
     * @return
     */
    @Access(pass = true)
    public String getClueCustomers() {
        PageModel results = saleContractService1.getClueCustomers(keyword, this.queryDefault, pageNo, pageSize);
        setResponseCommonData(results);
        return SUCCESS;
    }

    private Map<String, Object> getFilter() {
        try {
            HashMap filter_map = gson.fromJson(filter, HashMap.class);
            return filter_map;
        } catch (Exception e) {
            throw new ServiceException("查询条件不合法");
        }
    }


    /**
     * 获得合同明细
     *
     * @return
     */
    @Access(pass = true)
    public String getContractDetail() {
        setResponseCommonData(saleContractService1.convertReturnData(contractNo));
        return SUCCESS;
    }


    /**
     * 校验是否能换车
     *
     * @return
     */
    @Access(pass = true)
    public String canChangeVIN() {
        saleContractService1.canChangeVIN(contractDetailId);
        return SUCCESS;
    }

    /**
     * ADM19030076- 允许已完成的合同改装、车辆费用报销的车辆取消配车
     * 车辆销售合同模块，以前做了车辆费用报销的不能更换和取消配车，现已放开限制；
     * 车辆销售合同模块，以前做了车辆改装做了改装申请单的不能更换和取消配车，现已放开限制；
     * 此方法用于校验取消配车或换车时，该车辆是否已做费用报销或已做改装单申请
     *
     * @return
     */
    @Access(pass = true)
    public String canChangeVinForCharge() {
        String msg = saleContractService1.canChangeVinForCharge(contractDetailId);
        Map<String, Object> data = new HashMap<>(1);
        data.put("msg", msg);
        setResponseData(data);
        return SUCCESS;
    }


    /**
     * ADM19030076- 允许已完成的合同改装、车辆费用报销的车辆取消配车
     * 车辆销售合同模块，以前做了车辆费用报销的不能更换和取消配车，现已放开限制；
     * 车辆销售合同模块，以前做了车辆改装做了改装申请单的不能更换和取消配车，现已放开限制；
     * 此方法用于校验取消配车或换车时，该车辆是否已做费用报销或已做改装单申请
     *
     * @return
     */
    @Access(pass = true)
    public String canChangeVinForConversion() {
        String msg = saleContractService1.canChangeVinForConversion(contractDetailId);
        Map<String, Object> data = new HashMap<>(1);
        data.put("msg", msg);
        setResponseData(data);
        return SUCCESS;
    }


    /**
     * 查询已报销的费用
     *
     * @return
     */
//    @Access(pass = true)
//    public String getReimbursedContractCharge(){
//        List<Object> result = saleContractService1.getReimbursedContractCharge(contractNo);
//        setResponseData(result);
//        return SUCCESS;
//    }
    @Access(pass = true)
    public String createContract() {
        VehicleSaleContracts contracts = saleContractService.createContract(getPostJson());
        setResponseData(contracts);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateContract() {
        VehicleSaleContracts contracts = saleContractService.updateContract(objId, getPostJson());
        setResponseData(contracts);
        return SUCCESS;
    }

    @Access(pass = true)
    public String submitContract() {
        Map<String, List<Object>> result = saleContractService1.submitContract(objId);
        setResponseCommonData(result);
        return SUCCESS;
    }


    /**
     * 提交合同 - PC
     *
     * @return
     */
    @Access(pass = true)
    public String submitContractByPC() {
        logger.debug("submitContract 开始");
        Map<String, List<Object>> result = saleContractService1.submitContract(objId);
        logger.debug("submitContract 完成");
        setResponseData(result);
        logger.debug("setResponseData 完成");
        return SUCCESS;
    }
//    @Access(pass = true)
//    public String cancelContract() {
//        VehicleSaleContracts contracts =saleContractService.cancelContract(objId);
//        setResponseData(contracts);
//        return SUCCESS;
//    }

    /**
     * 车辆信息操作 Group
     */

    @Access(pass = true)
    public String getGroupList() {
        PageModel pageModel = saleContractService.getGroupList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createGroup() {
        VehicleSaleContractDetailGroups group = saleContractService.createGroup(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateGroup() {
        VehicleSaleContractDetailGroups group = saleContractService.updateGroup(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeGroup() {
        VehicleSaleContractDetailGroups group = saleContractService.removeGroup(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }


    /**
     * 车辆明细操作 Detail
     */

    @Access(pass = true)
    public String getDetailList() {
        PageModel pageModel = saleContractService.getDetailList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createDetail() {
        VehicleSaleContractDetail detail = saleContractService.createDetail(getPostJson());
        setResponseData(detail);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateDetail() {
        VehicleSaleContractDetail detail = saleContractService.updateDetail(objId, getPostJson());
        setResponseData(detail);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeDetail() {
        VehicleSaleContractDetail detail = saleContractService.removeDetail(objId, getPostJson());
        setResponseData(detail);
        return SUCCESS;
    }


    /**
     * 车辆改装 Item
     */

    @Access(pass = true)
    public String getItemList() {
        PageModel pageModel = saleContractService.getItemList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createItem() {
        VehicleSaleContractItemGroups group = saleContractService.createItem(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateItem() {
        VehicleSaleContractItemGroups group = saleContractService.updateItem(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeItem() {
        VehicleSaleContractItemGroups group = saleContractService.removeItem(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }


    /**
     * 保险信息 Insurance
     */

    @Access(pass = true)
    public String getInsuranceList() {
        PageModel pageModel = saleContractService.getInsuranceList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createInsurance() {
        VehicleSaleContractInsuranceGroups group = saleContractService.createInsurance(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateInsurance() {
        VehicleSaleContractInsuranceGroups group = saleContractService.updateInsurance(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeInsurance() {
        VehicleSaleContractInsuranceGroups group = saleContractService.removeInsurance(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }


    /**
     * 其他费用 Charge
     */

    @Access(pass = true)
    public String getChargeList() {
        PageModel pageModel = saleContractService.getChargeList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createCharge() {
        VehicleSaleContractChargeGroups group = saleContractService.createCharge(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateCharge() {
        VehicleSaleContractChargeGroups group = saleContractService.updateCharge(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeCharge() {
        VehicleSaleContractChargeGroups group = saleContractService.removeCharge(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    /**
     * 精品安装 Present
     */

    @Access(pass = true)
    public String getPresentList() {
        PageModel pageModel = saleContractService.getPresentList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createPresent() {
        VehicleSaleContractPresentGroups group = saleContractService.createPresent(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updatePresent() {
        VehicleSaleContractPresentGroups group = saleContractService.updatePresent(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removePresent() {
        VehicleSaleContractPresentGroups group = saleContractService.removePresent(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    /**
     * 发票明细 Invoices
     */

    @Access(pass = true)
    public String getInvoicesList() {
        PageModel pageModel = saleContractService.getInvoicesList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createInvoices() {
        VehicleInvoicesGroups group = saleContractService.createInvoices(getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateInvoices() {
        VehicleInvoicesGroups group = saleContractService.updateInvoices(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeInvoices() {
        VehicleInvoicesGroups group = saleContractService.removeInvoices(objId, getPostJson());
        setResponseData(group);
        return SUCCESS;
    }


    @Access(pass = true)
    public String getVehicleList() {
        PageModel pageModel = saleContractService.getVehicleList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getChargeCatalogList() {
        PageModel pageModel = saleContractService.getChargeCatalogList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getInsuranceCompanyCatalogList() {
        PageModel pageModel = saleContractService.getInsuranceCompanyCatalogList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getServiceConsignsList() {
        PageModel pageModel = saleContractService.getServiceConsignsList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getBudgetChargeCatalogList() {
        PageModel pageModel = saleContractService.getBudgetChargeCatalogList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    /**
     * 消费预算 Budget
     */

    @Access(pass = true)
    public String getBudgetList() {
        PageModel pageModel = saleContractService.getBudgetList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createBudget() {
        VehicleLoanBudget budget = saleContractService.createBudget(getPostJson());
        setResponseData(budget);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateBudget() {
        VehicleLoanBudget budget = saleContractService.updateBudget(objId, getPostJson());
        setResponseData(budget);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeBudget() {
        VehicleLoanBudget budget = saleContractService.removeBudget(objId, getPostJson());
        setResponseData(budget);
        return SUCCESS;
    }


//    @Access (pass=true)
//    public String getInitData(){
//        Map<String, Object> response = saleContractService.getInitData();
//        setResponseData(response);
//        return SUCCESS;
//    }

    /**
     * 消贷合同
     */
    @Access(pass = true)
    public String getLoanContractList() {
        PageModel pageModel = saleContractService.getLoanContractList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createLoanContract() {
        VehicleLoanContracts loan = saleContractService.createLoanContract(getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateLoanContract() {
        VehicleLoanContracts loan = saleContractService.updateLoanContract(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeLoanContract() {
        VehicleLoanContracts loan = saleContractService.removeLoanContract(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    /**
     * 消贷明细
     *
     * @return
     */
    @Access(pass = true)
    public String getBudgetDetailList() {
        PageModel pageModel = saleContractService.getBudgetDetailList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createBudgetDetail() {
        VehicleLoanBudgetDetails loan = saleContractService.createBudgetDetail(getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateBudgetDetail() {
        VehicleLoanBudgetDetails loan = saleContractService.updateBudgetDetail(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeBudgetDetail() {
        VehicleLoanBudgetDetails loan = saleContractService.removeBudgetDetail(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }


    /**
     * 消贷费用
     *
     * @return
     */
    @Access(pass = true)
    public String getBudgetChargeList() {
        PageModel pageModel = saleContractService.getBudgetChargeList(getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(pageModel);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createBudgetCharge() {
        VehicleLoanBudgetCharge loan = saleContractService.createBudgetCharge(getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateBudgetCharge() {
        VehicleLoanBudgetCharge loan = saleContractService.updateBudgetCharge(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeBudgetCharge() {
        VehicleLoanBudgetCharge loan = saleContractService.removeBudgetCharge(objId, getPostJson());
        setResponseData(loan);
        return SUCCESS;
    }

    @Access(pass = true)
    public void pdfExport() throws Exception {
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        response.reset();

        InputStream in = null;
        OutputStream out = new BufferedOutputStream(response.getOutputStream());

        String htmlText = saleContractService.generateContractHtml(contractNo);
        if (preview) {
            response.setHeader("content-type", "text/html;charset=UTF-8");
            in = new ByteArrayInputStream(htmlText.getBytes("UTF-8"));
        } else {
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("车辆销售合同_" + contractNo + ".pdf", "UTF-8"));
            response.setContentType("application/octet-stream");
            response.addHeader("Connection", "close");
            in = FileUtil.convertHtmlToPdf(htmlText);
        }

        try {
            int count = 0;
            int pics = 0;
            byte[] buffer = new byte[1024 * 32];
            List<byte[]> pool = new ArrayList<byte[]>();
            List<Integer> sizes = new ArrayList<Integer>();
            while ((pics = in.read(buffer)) >= 0) {
                count += pics;
                sizes.add(pics);
                pool.add(buffer);
                buffer = new byte[1024 * 32];
            }
            response.setContentLength(count);
            for (int i = 0; i < sizes.size(); i++) {
                out.write(pool.get(i), 0, sizes.get(i));
            }
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    //do nothing
                }
            }

            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    //do nothing
                }

            }
        }
    }

    @Access(pass = true)
    public String pdfExportOld() {
        String contractNo = "AVSC18050053";
        Map<String, Object> filter = new HashMap<String, Object>(3);
        filter.put("contractNo", contractNo);
        PageModel page = saleContractService.getContractList(filter, null, 1, 1);
        if (page.getData() == null && page.getData().size() == 0) {
            throw new ServiceException("未找到该合同");
        }
        ServletActionContext.getRequest().setAttribute("contract", page.getData().get(0));

        ServletActionContext.getRequest().setAttribute("buyer", saleContractService.getBuyerByContractNo(contractNo));
        ServletActionContext.getRequest().setAttribute("seller", saleContractService.getSellerByContractNo(contractNo));

        page = saleContractService.getGroupList(filter, null, 1, 999);
        ServletActionContext.getRequest().setAttribute("vehicleGroupList", page.getData());

        List<VwVehicleSaleContractDetailGroups> groups = page.getData();

        filter.clear();
        List<String> vehicleIds = new ArrayList<String>(page.getData().size());
        for (Object model : page.getData()) {
            vehicleIds.add(((VwVehicleSaleContractDetailGroups) model).getVnoId());
        }
        filter.put("selfId__in", "('" + StringUtils.join(vehicleIds, ",") + "')");
        page = saleContractService.getVehicleList(filter, null, 1, 999);
        ServletActionContext.getRequest().setAttribute("vehicleModelList", page.getData());


        List<List> insuranceListGroup = new ArrayList<List>(groups.size());
        List<List> invoicesListGroup = new ArrayList<List>(groups.size());
        List<List> chargeListGroup = new ArrayList<List>(groups.size());
        List<List> presentListGroup = new ArrayList<List>(groups.size());
        List<List> itemListGroup = new ArrayList<List>(groups.size());


        for (VwVehicleSaleContractDetailGroups group : groups) {
            filter.clear();
            filter.put("contractNo", contractNo);
            filter.put("groupId", group.getGroupId());
            insuranceListGroup.add(saleContractService.getInsuranceList(filter, null, 1, 999).getData());
            invoicesListGroup.add(saleContractService.getInvoicesList(filter, null, 1, 999).getData());
            chargeListGroup.add(saleContractService.getChargeList(filter, null, 1, 999).getData());
            presentListGroup.add(saleContractService.getPresentList(filter, null, 1, 999).getData());
            itemListGroup.add(saleContractService.getItemList(filter, null, 1, 999).getData());
        }

        ServletActionContext.getRequest().setAttribute("insuranceListGroup", insuranceListGroup);
        ServletActionContext.getRequest().setAttribute("invoicesListGroup", invoicesListGroup);
        ServletActionContext.getRequest().setAttribute("chargeListGroup", chargeListGroup);
        ServletActionContext.getRequest().setAttribute("presentListGroup", presentListGroup);
        ServletActionContext.getRequest().setAttribute("itemListGroup", itemListGroup);

        return "PDF_EXPORT";
    }
}
