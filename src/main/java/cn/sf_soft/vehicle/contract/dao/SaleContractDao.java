package cn.sf_soft.vehicle.contract.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.vehicle.contract.model.*;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by henry on 2018/4/28.
 */
public interface SaleContractDao extends BaseDao {
    PageModel getContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    Map<String,Object> getContractTotal(Map<String, Object> filter);

    PageModel getGroupList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getItemList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getInsuranceList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getPresentList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getInvoicesList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getVehicleList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    Serializable saveGroup(VehicleSaleContractDetailGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleSaleContractDetailGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleSaleContractDetailGroups group);

    Serializable saveGroup(VehicleSaleContractItemGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleSaleContractItemGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleSaleContractItemGroups group);

    Serializable saveGroup(VehicleSaleContractInsuranceGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleSaleContractInsuranceGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleSaleContractInsuranceGroups group);

    Serializable saveGroup(VehicleSaleContractChargeGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleSaleContractChargeGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleSaleContractChargeGroups group);

    Serializable saveGroup(VehicleSaleContractPresentGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleSaleContractPresentGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleSaleContractPresentGroups group);

    Serializable saveGroup(VehicleInvoicesGroups group, Map<String, Object> jsonData);

    boolean updateGroup(VehicleInvoicesGroups group, Map<String, Object> jsonData);

    boolean deleteGroup(VehicleInvoicesGroups group);

    boolean deleteDetail(VehicleSaleContractDetail detail);

    boolean saveDetail(VehicleSaleContractDetail detail, Map<String, Object> postJson);

    boolean updateDetail(VehicleSaleContractDetail detail, Map<String, Object> postJson);

    PageModel getChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getInsuranceCompanyCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getServiceConsignsList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getBudgetList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    Map<String,Object> getInitData();

    PageModel getLoanContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getBudgetDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getBudgetChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel restList(Class type, Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getBudgetChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);
}
