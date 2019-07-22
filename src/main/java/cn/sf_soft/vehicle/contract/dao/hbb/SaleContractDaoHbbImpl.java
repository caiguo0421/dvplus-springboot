package cn.sf_soft.vehicle.contract.dao.hbb;

import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.dao.SaleContractDao;
import cn.sf_soft.vehicle.contract.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by henry on 2018/4/28.
 */
@Repository("saleContractDao")
public class SaleContractDaoHbbImpl extends BaseDaoHibernateImpl implements SaleContractDao {
    @Override
    public PageModel getContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = " 1 = 1 ";
        if(filter!=null && filter.size()>0) {
            Map<String, Object> underLineFilter = new HashMap<String, Object>(filter.size());
            for (String key : filter.keySet()) {
                underLineFilter.put(camel2Underline(key), filter.get(key));
            }
            filterCondition = mapToFilterString(underLineFilter, "obj");
        }
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "createTime DESC";
        }

        orderBy = orderBy.replaceAll(" DESC", " desc").replaceAll(" ASC", " asc");
        orderBy = camel2Underline(orderBy);

        String sql = " SELECT obj.* FROM ( SELECT contracts.*, \n" +
                "  (STUFF(( SELECT ',' + vehicle_vin FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vins,\n " +
                "  (STUFF(( SELECT ',' + vehicle_vno FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vnos,\n " +
                "  (ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(receive_money, 0) - ISNULL(receive_money_loan_charge, 0) ) AS remain_money \n" +
                " FROM vw_vehicle_sale_contracts contracts ) obj WHERE " + filterCondition + " ORDER BY " + orderBy;

        return findBySql(sql, VehicleSaleContracts.class, pageNo, pageSize);
    }

    @Override
    public Map<String, Object> getContractTotal(Map<String, Object> filter) {
        String filterCondition = " 1 = 1 ";
        if(filter!=null && filter.size()>0) {
            Map<String, Object> underLineFilter = new HashMap<String, Object>(filter.size());
            for (String key : filter.keySet()) {
                underLineFilter.put(camel2Underline(key), filter.get(key));
            }
            filterCondition = mapToFilterString(underLineFilter, "obj");
        }

        String sql = "SELECT SUM(contract_quantity) AS contract_quantity,\n" +
                " SUM(arrived_quantity) AS arrived_quantity,\n" +
                " SUM(allotted_quantity) AS allotted_quantity,\n" +
                " SUM(contract_quantity - arrived_quantity) AS available_quantity,\n" +
                " SUM(ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0)) AS contract_money,\n" +
                " SUM(ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(loan_amount_totv,0) + ISNULL(first_pay_totc,0)) AS receivable_money,\n" +
                " SUM(ISNULL(receive_money, 0) + ISNULL(receive_money_loan_charge, 0)) AS receive_money\n" +
                " FROM ( SELECT contracts.*,\n" +
                "  (STUFF(( SELECT ',' + vehicle_vin FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vins,\n" +
                "  (STUFF(( SELECT ',' + vehicle_vno FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vnos,\n" +
                "  (ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(receive_money, 0) - ISNULL(receive_money_loan_charge, 0) ) AS remain_money,\n" +
                "  (SELECT ISNULL(COUNT(1),0) FROM vehicle_sale_contract_detail detail WHERE vehicle_vin IS NOT NULL AND detail.contract_no = contracts.contract_no) AS allotted_quantity\n" +
                " FROM vw_vehicle_sale_contracts contracts ) obj WHERE " + filterCondition;
        List<Map<String, Object>> result = getMapBySQL(sql, null);
        if(result == null || result.size() == 0){
            return null;
        }
        return result.get(0);
    }

    @Override
    public PageModel getGroupList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "vehicleVno DESC";
        }
        return findByHql("FROM VwVehicleSaleContractDetailGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleSaleContractDetail WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getItemList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleSaleContractItemGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getInsuranceList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleSaleContractInsuranceGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleSaleContractChargeGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getPresentList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleSaleContractPresentGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getInvoicesList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "groupId DESC";
        }
        return findByHql("FROM VehicleInvoicesGroups WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public Serializable saveGroup(VehicleSaleContractDetailGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String groupId = UUID.randomUUID().toString();
        group.setGroupId(groupId);
        group.setGroupNo(StringUtils.split(groupId, '-')[4]);

        // 新建足量detail
        for(int i = 0;i<group.getVehicleQuantity();i++){
            VehicleSaleContractDetail detail = new VehicleSaleContractDetail();
            this.setDetailDefaultValue(detail);
            detail.setContractDetailId(UUID.randomUUID().toString());
            detail.setDocumentNo(detail.getContractDetailId());
            saveMapToObject(jsonData, detail);
            detail.setGroupId(groupId);
            save(detail);
        }

        return save(group);
    }

    private void setDetailDefaultValue(VehicleSaleContractDetail detail){
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);
        detail.setUserId(user.getUserId());
        detail.setUserName(user.getUserName());
        detail.setUserNo(user.getUserNo());

        detail.setDepartmentId(user.getDepartment());
        detail.setDepartmentNo(user.getDepartmentNo());
        detail.setDepartmentName(user.getDepartmentName());

        detail.setSubmitStationId(user.getDefaulStationId());
        detail.setSubmitStationName(user.getStationName());
    }

    @Override
    public boolean updateGroup(VehicleSaleContractDetailGroups group, Map<String, Object> jsonData){
        // detail 删除判断
         if(jsonData.containsKey("vehicleQuantity") && jsonData.get("vehicleQuantity") != null){
            Integer vehicleQuantity = Double.valueOf(jsonData.get("vehicleQuantity").toString()).intValue();
            Integer originVehicleQuantity = group.getVehicleQuantity() == null ? 0 : group.getVehicleQuantity();
            if ( originVehicleQuantity > vehicleQuantity){
                List<VehicleSaleContractDetail> originDetails = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
                int deleteCount = Math.min(originDetails == null ? 0 : originDetails.size(), originVehicleQuantity-vehicleQuantity);
                for(int i = 0;i<deleteCount;i++){
                    deleteDetail(originDetails.get(i));
                }
            }else if(originVehicleQuantity < vehicleQuantity){
                int addCount = vehicleQuantity - originVehicleQuantity;
                for(int i = 0;i<addCount;i++){
                    VehicleSaleContractDetail detail = new VehicleSaleContractDetail();
                    this.setDetailDefaultValue(detail);
                    detail.setContractDetailId(UUID.randomUUID().toString());
                    detail.setDocumentNo(detail.getContractDetailId());
                    detail.setGroupId(group.getGroupId());
                    saveDetail(detail, toMap(group));
                }
            }
        }

        saveMapToObject(jsonData, group);
        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        updateGroupItems(details, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleSaleContractDetailGroups group){
        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        for(VehicleSaleContractDetail detail: details){
            deleteDetail(detail);
        }
        return delete(group);
    }

    @Override
    public Serializable saveGroup(VehicleSaleContractItemGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String itemGroupId = UUID.randomUUID().toString();
        group.setItemGroupId(itemGroupId);

        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        if(details!=null && details.size()>0){
            for(VehicleSaleContractDetail detail : details){
                saveItemByGroup(group, detail);
            }
        }
        return save(group);
    }

    private void saveItemByGroup(VehicleSaleContractItemGroups group, VehicleSaleContractDetail detail){
        VehicleSaleContractItem item = new VehicleSaleContractItem();
        saveMapToObject(toMap(group), item);
        item.setItemGroupId(group.getItemGroupId());
        item.setSaleContractItemId(UUID.randomUUID().toString());
        item.setContractDetailId(detail.getContractDetailId());
        save(item);
    }

    @Override
    public boolean updateGroup(VehicleSaleContractItemGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        List<VehicleSaleContractItem> items = (List<VehicleSaleContractItem>) findByHql("From VehicleSaleContractItem WHERE itemId = '"+group.getItemId()+"'");
        updateGroupItems(items, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleSaleContractItemGroups group) {
        List<VehicleSaleContractItem> items = (List<VehicleSaleContractItem>) findByHql("From VehicleSaleContractItem WHERE itemId = '"+group.getItemId()+"'");
        deleteGroupItems(items);
        return delete(group);
    }

    @Override
    public Serializable saveGroup(VehicleSaleContractInsuranceGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String insuranceGroupId = UUID.randomUUID().toString();
        group.setInsuranceGroupId(insuranceGroupId);

        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        if(details!=null && details.size()>0){
            for(VehicleSaleContractDetail detail : details){
                saveItemByGroup(group, detail);
            }
        }
        return save(group);
    }

    private void saveItemByGroup(VehicleSaleContractInsuranceGroups group, VehicleSaleContractDetail detail){
        VehicleSaleContractInsurance insurance = new VehicleSaleContractInsurance();
        saveMapToObject(toMap(group), insurance);
        insurance.setInsuranceGroupId(group.getGroupId());
        insurance.setSaleContractInsuranceId(UUID.randomUUID().toString());
        insurance.setContractDetailId(detail.getContractDetailId());
        save(insurance);
    }

    @Override
    public boolean updateGroup(VehicleSaleContractInsuranceGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        List<VehicleSaleContractInsurance> insurances = (List<VehicleSaleContractInsurance>) findByHql("From VehicleSaleContractInsurance WHERE insuranceGroupId = '"+group.getInsuranceGroupId()+"'");
        updateGroupItems(insurances, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleSaleContractInsuranceGroups group) {
        List<VehicleSaleContractInsurance> insurances = (List<VehicleSaleContractInsurance>) findByHql("From VehicleSaleContractInsurance WHERE insuranceGroupId = '"+group.getInsuranceGroupId()+"'");
        deleteGroupItems(insurances);
        return delete(group);
    }

    @Override
    public Serializable saveGroup(VehicleSaleContractChargeGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String chargeGroupId = UUID.randomUUID().toString();
        group.setChargeGroupId(chargeGroupId);

        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        if(details!=null && details.size()>0){
            for(VehicleSaleContractDetail detail : details){
                saveItemByGroup(group, detail);
            }
        }

        return save(group);
    }

    private void saveItemByGroup(VehicleSaleContractChargeGroups group, VehicleSaleContractDetail detail){
        VehicleSaleContractCharge insurance = new VehicleSaleContractCharge();
        saveMapToObject(toMap(group), insurance);
        insurance.setChargeGroupId(group.getGroupId());
        insurance.setSaleContractChargeId(UUID.randomUUID().toString());
        insurance.setContractDetailId(detail.getContractDetailId());
        save(insurance);
    }

    @Override
    public boolean updateGroup(VehicleSaleContractChargeGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        List<VehicleSaleContractCharge> insurances = (List<VehicleSaleContractCharge>) findByHql("From VehicleSaleContractCharge WHERE chargeGroupId = '"+group.getChargeGroupId()+"'");
        updateGroupItems(insurances, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleSaleContractChargeGroups group) {
        List<VehicleSaleContractCharge> insurances = (List<VehicleSaleContractCharge>) findByHql("From VehicleSaleContractCharge WHERE chargeGroupId = '"+group.getChargeGroupId()+"'");
        deleteGroupItems(insurances);
        return delete(group);
    }

    @Override
    public Serializable saveGroup(VehicleSaleContractPresentGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String presentGroupId = UUID.randomUUID().toString();
        group.setPresentGroupId(presentGroupId);

        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        if(details!=null && details.size()>0){
            for(VehicleSaleContractDetail detail : details){
                saveItemByGroup(group, detail);
            }
        }

        return save(group);
    }

    private void saveItemByGroup(VehicleSaleContractPresentGroups group, VehicleSaleContractDetail detail){
        VehicleSaleContractPresent present = new VehicleSaleContractPresent();
        saveMapToObject(toMap(group), present);
        present.setPresentGroupId(group.getGroupId());
        present.setSaleContractPresentId(UUID.randomUUID().toString());
        present.setContractDetailId(detail.getContractDetailId());
        save(present);
    }

    @Override
    public boolean updateGroup(VehicleSaleContractPresentGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        List<VehicleSaleContractPresent> presents = (List<VehicleSaleContractPresent>) findByHql("From VehicleSaleContractPresent WHERE presentGroupId = '"+group.getPresentGroupId()+"'");
        updateGroupItems(presents, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleSaleContractPresentGroups group) {
        List<VehicleSaleContractPresent> presents = (List<VehicleSaleContractPresent>) findByHql("From VehicleSaleContractPresent WHERE presentGroupId = '"+group.getPresentGroupId()+"'");
        deleteGroupItems(presents);
        return delete(group);
    }

    @Override
    public Serializable saveGroup(VehicleInvoicesGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        String invoiceGroupId = UUID.randomUUID().toString();
        group.setInvoiceGroupId(invoiceGroupId);

        List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) findByHql("From VehicleSaleContractDetail WHERE groupId = '"+group.getGroupId()+"'");
        if(details!=null && details.size()>0){
            for(VehicleSaleContractDetail detail : details){
                saveItemByGroup(group, detail);
            }
        }

        return save(group);
    }

    private void saveItemByGroup(VehicleInvoicesGroups group, VehicleSaleContractDetail detail){
        VehicleInvoices present = new VehicleInvoices();
        saveMapToObject(toMap(group), present);
        present.setInvoiceGroupId(group.getGroupId());
        present.setInvoicesDetailId(UUID.randomUUID().toString());
        present.setContractDetailId(detail.getContractDetailId());
        save(present);
    }

    @Override
    public boolean updateGroup(VehicleInvoicesGroups group, Map<String, Object> jsonData) {
        saveMapToObject(jsonData, group);
        List<VehicleInvoices> invoices = (List<VehicleInvoices>) findByHql("From VehicleInvoices WHERE invoiceGroupId = '"+group.getInvoiceGroupId()+"'");
        updateGroupItems(invoices, jsonData);
        return update(group);
    }

    @Override
    public boolean deleteGroup(VehicleInvoicesGroups group) {
        List<VehicleInvoices> invoices = (List<VehicleInvoices>) findByHql("From VehicleInvoices WHERE invoiceGroupId = '"+group.getInvoiceGroupId()+"'");
        deleteGroupItems(invoices);
        return delete(group);
    }

    @Override
    public boolean deleteDetail(VehicleSaleContractDetail detail) {
        List<VehicleSaleContractItem> items = (List<VehicleSaleContractItem>) findByHql("From VehicleSaleContractItem WHERE contractDetailId = '"+detail.getContractDetailId()+"'");
        deleteGroupItems(items);
        List<VehicleSaleContractCharge> insurances = (List<VehicleSaleContractCharge>) findByHql("From VehicleSaleContractCharge WHERE contractDetailId = '"+detail.getContractDetailId()+"'");
        deleteGroupItems(insurances);
        List<VehicleSaleContractPresent> presents = (List<VehicleSaleContractPresent>) findByHql("From VehicleSaleContractPresent WHERE contractDetailId = '"+detail.getContractDetailId()+"'");
        deleteGroupItems(presents);
        List<VehicleInvoices> invoices = (List<VehicleInvoices>) findByHql("From VehicleInvoices WHERE contractDetailId = '"+detail.getContractDetailId()+"'");
        deleteGroupItems(invoices);
        return delete(detail);
    }

    @Override
    public boolean saveDetail(VehicleSaleContractDetail detail, Map<String, Object> postJson) {
        saveMapToObject(postJson, detail);

        List<VehicleSaleContractItemGroups> itemGroups = (List<VehicleSaleContractItemGroups>) findByHql("From VehicleSaleContractItemGroups WHERE groupId = '"+detail.getGroupId()+"'");
        for(VehicleSaleContractItemGroups itemGroup : itemGroups){
            saveItemByGroup(itemGroup, detail);
        }
        List<VehicleSaleContractChargeGroups> insuranceGroups = (List<VehicleSaleContractChargeGroups>) findByHql("From VehicleSaleContractChargeGroups WHERE groupId = '"+detail.getGroupId()+"'");
        for(VehicleSaleContractChargeGroups insuranceGroup : insuranceGroups){
            saveItemByGroup(insuranceGroup, detail);
        }
        List<VehicleSaleContractPresentGroups> presentGroups = (List<VehicleSaleContractPresentGroups>) findByHql("From VehicleSaleContractPresentGroups WHERE groupId = '"+detail.getGroupId()+"'");
        for(VehicleSaleContractPresentGroups presentGroup : presentGroups){
            saveItemByGroup(presentGroup, detail);
        }
        List<VehicleInvoicesGroups> invoiceGroups = (List<VehicleInvoicesGroups>) findByHql("From VehicleInvoicesGroups WHERE groupId = '"+detail.getGroupId()+"'");
        for(VehicleInvoicesGroups invoiceGroup : invoiceGroups){
            saveItemByGroup(invoiceGroup, detail);
        }
        save(detail);
        return true;
    }

    @Override
    public boolean updateDetail(VehicleSaleContractDetail detail, Map<String, Object> postJson) {
        saveMapToObject(postJson, detail);
        update(detail);
        return true;
    }

    @Override
    public PageModel getChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        List<Map<String, Object>> list = getMapBySQL("SELECT * FROM charge_catalog WHERE forbid_flag = 0 AND (charge_type IS NULL OR charge_type = 10 OR charge_type = 20)", null);
        PageModel result = new PageModel(list, 1, list.size(), list.size());
        return result;
    }



    @Override
    public PageModel getInsuranceCompanyCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        List<Map<String, Object>> list = getMapBySQL("SELECT a.*, b.category_name, c.object_no, c.object_name\n" +
                "FROM insurance_company_catalog AS a, \n" +
                "insurance_category_catalog AS b, base_related_objects AS c \n" +
                "WHERE a.category_id = b.category_id AND a.supplier_id = c.object_id\n", null);
        PageModel result = new PageModel(list, 1, list.size(), list.size());
        return result;
    }

    @Override
    public PageModel getServiceConsignsList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        List<Map<String, Object>> list = getMapBySQL("SELECT * FROM (\n" +
                "SELECT a.*, b.supplier_id, c.object_no AS supplier_no, \n" +
                "c.object_name AS supplier_name, b.cost,b.price_sale FROM base_service_consigns AS a \n" +
                "LEFT JOIN base_service_consign_manhours as b on a.consign_id=b.consign_id\n" +
                "LEFT JOIN base_related_objects as c on b.supplier_id = c.object_id\n" +
                "WHERE b.supplier_id IS NOT NULL AND b.supplier_id<>'' AND b.station_id = '默认' \n" +
                "UNION ALL\n" +
                "SELECT a.*, b.supplier_id, NULL supplier_no, \n" +
                "NULL supplier_name, b.cost,b.price_sale  FROM base_service_consigns AS a \n" +
                "LEFT JOIN base_service_consign_manhours as b on a.consign_id=b.consign_id\n" +
                "WHERE (b.supplier_id IS NULL OR b.supplier_id='') AND b.station_id = '默认' \n" +
                ") a WHERE 1=1", null);
        PageModel result = new PageModel(list, 1, list.size(), list.size());
        return result;
    }

    @Override
    public PageModel getVehicleList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "createTime DESC";
        }
        return findByHql("FROM BaseVehicleModelCatalog WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getBudgetList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "submitTime DESC";
        }
        return findByHql("FROM VehicleLoanBudget WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public Map<String, Object> getInitData() {
        Map<String, Object> result = new HashMap<String, Object>(5);
        result.put("loan_mode",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"loan_mode"}));
        result.put("loan_type",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"loan_type"}));
        result.put("pay_type",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"pay_type"}));
        result.put("budget_status",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"budget_status"}));
        result.put("slc_rate_type",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"slc_rate_type"}));
        result.put("confirm_status",getHibernateTemplate().find("FROM SysFlags where fieldNo=?",new Object[]{"confirm_status"}));
        return result;
    }

    @Override
    public PageModel getLoanContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "createTime DESC";
        }
        return findByHql("FROM VehicleLoanContracts WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel getBudgetDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "selfId DESC";
        }
        return findByHql("FROM VwVehicleLoanBudgetDetails WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);

    }

    @Override
    public PageModel getBudgetChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "selfId DESC";
        }
        return findByHql("FROM VwVehicleLoanBudgetCharge WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }

    @Override
    public PageModel restList(Class type, Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String filterCondition = mapToFilterString(filter, null);
        if(orderBy == null || orderBy.length() == 0){
            throw new ServiceException("缺少orderBy");
        }
        return findByHql("FROM " + type.getName() + " WHERE " + filterCondition + " ORDER BY " + orderBy, pageNo, pageSize);
    }


    private void updateGroupItems(List items, Map<String, Object> jsonData){
        if(items != null && items.size() > 0) {
            for (Object item : items) {
                saveMapToObject(jsonData, items);
                save(item);
            }
        }
    }

    private void deleteGroupItems(List items){
        if(items != null && items.size() > 0) {
            for (Object item : items) {
                delete(item);
            }
        }
    }

    @Override
    public PageModel getBudgetChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize) {
        String sql = "SELECT a.*, b.meaning AS charge_type_meaning,c.meaning AS default_flag_meaning,\n" +
                "d.meaning AS direction_meaning,e.meaning As money_type_meaning,f.meaning AS accrued_type_meaning\n" +
                "FROM charge_catalog AS a \n" +
                "LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'charge_type') AS b ON a.charge_type = b.code \n" +
                "LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'is_yes') AS c ON a.default_flag = c.code \n" +
                "LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'direction') AS d ON a.direction = d.code \n" +
                "LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'money_type') AS e ON a.money_type = e.code \n" +
                "LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'accrued_type') AS f ON a.accrued_type = f.code \n" +
                "WHERE charge_type=40 AND ISNULL(forbid_flag,0)=0";
        List<Map<String, Object>> result = getMapBySQL(sql, null);
        PageModel page = new PageModel(result, 1, result.size(), result.size());
        return page;
    }

    private boolean dealBillDocment(){
        return true;
    }

    private boolean dealCustomerCommission(){

        return true;
    }
}
