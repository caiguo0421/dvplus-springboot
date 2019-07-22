package cn.sf_soft.vehicle.contract.service;

import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.*;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by henry on 2018/4/28.
 */
public interface SaleContractService {

    /**
     * 合同操作
     */

    PageModel getContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    Map<String,Object> getContractTotal(Map<String, Object> filter);

    VehicleSaleContracts createContract(Map<String, Object> data);

    SysUnits getLoginDepartment();

    VehicleSaleContracts updateContract(String objId, Map<String, Object> data);

    VehicleSaleContracts submitContract(String objId);

//    VehicleSaleContracts cancelContract(String objId);


    /**
     * 车辆信息
     */

    PageModel getGroupList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractDetailGroups createGroup(Map<String, Object> postJson);

    VehicleSaleContractDetailGroups updateGroup(String objId, Map<String, Object> postJson);

    VehicleSaleContractDetailGroups removeGroup(String objId, Map<String, Object> postJson);

    /**
     * 车辆明细操作 Detail
     */

    PageModel getDetailList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractDetail createDetail(Map<String, Object> postJson);

    VehicleSaleContractDetail updateDetail(String objId, Map<String, Object> postJson);

    VehicleSaleContractDetail removeDetail(String objId, Map<String, Object> postJson);

    /**
     * 车辆改装 Item
     */

    PageModel getItemList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractItemGroups createItem(Map<String, Object> postJson);

    VehicleSaleContractItemGroups updateItem(String objId, Map<String, Object> postJson);

    VehicleSaleContractItemGroups removeItem(String objId, Map<String, Object> postJson);

    /**
     * 保险信息 Insurance
     */

    PageModel getInsuranceList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractInsuranceGroups createInsurance(Map<String, Object> postJson);

    VehicleSaleContractInsuranceGroups updateInsurance(String objId, Map<String, Object> postJson);

    VehicleSaleContractInsuranceGroups removeInsurance(String objId, Map<String, Object> postJson);


    /**
     * 其他费用 Charge
     */

    PageModel getChargeList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractChargeGroups createCharge(Map<String, Object> postJson);

    VehicleSaleContractChargeGroups updateCharge(String objId, Map<String, Object> postJson);

    VehicleSaleContractChargeGroups removeCharge(String objId, Map<String, Object> postJson);

    /**
     * 精品安装 Present
     */
    PageModel getPresentList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleSaleContractPresentGroups createPresent(Map<String, Object> postJson);

    VehicleSaleContractPresentGroups updatePresent(String objId, Map<String, Object> postJson);

    VehicleSaleContractPresentGroups removePresent(String objId, Map<String, Object> postJson);

    /**
     * 发票明细 Invoices
     */

    PageModel getInvoicesList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    VehicleInvoicesGroups createInvoices(Map<String, Object> postJson);

    VehicleInvoicesGroups updateInvoices(String objId, Map<String, Object> postJson);

    VehicleInvoicesGroups removeInvoices(String objId, Map<String, Object> postJson);

    PageModel getVehicleList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getChargeCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getInsuranceCompanyCatalogList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    PageModel getServiceConsignsList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    /**
     * 消费预算 Budget
     */

    PageModel getBudgetList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    VehicleLoanBudget createBudget(Map<String, Object> postJson);

    VehicleLoanBudget updateBudget(String objId, Map<String, Object> postJson);

    VehicleLoanBudget removeBudget(String objId, Map<String, Object> postJson);

    Map<String,Object> getInitData();

    PageModel getLoanContractList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    VehicleLoanContracts createLoanContract(Map<String, Object> postJson);

    VehicleLoanContracts updateLoanContract(String objId, Map<String, Object> postJson);

    VehicleLoanContracts removeLoanContract(String objId, Map<String, Object> postJson);

    PageModel getBudgetDetailList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    VehicleLoanBudgetDetails createBudgetDetail(Map<String, Object> postJson);

    VehicleLoanBudgetDetails updateBudgetDetail(String objId, Map<String, Object> postJson);

    VehicleLoanBudgetDetails removeBudgetDetail(String objId, Map<String, Object> postJson);

    PageModel getBudgetChargeList(Map<String, Object> filter, String orderBy, int pageNo, int pageSize);

    VehicleLoanBudgetCharge createBudgetCharge(Map<String, Object> postJson);

    VehicleLoanBudgetCharge updateBudgetCharge(String objId, Map<String, Object> postJson);

    VehicleLoanBudgetCharge removeBudgetCharge(String objId, Map<String, Object> postJson);



    PageModel restList(Class type, Map<String, Object> filter, String orderBy, int pageNo, int pageSize);
    Object restCreate(Class type, Map<String, Object> postJson);
    Object restUpdate(Class type, String objId, Map<String, Object> postJson);
    Object restRemove(Class type, String objId);

    Map<String,Object> getBuyerByContractNo(String contractNo);
    Map<String,Object> getSellerByContractNo(String contractNo);


    String generateContractHtml(String contractNo) throws IOException, TemplateException;

    PageModel getBudgetChargeCatalogList(Map<String, Object> postJson, String orderBy, int pageNo, int pageSize);

    PageModel submitQuotation(String quotationId);

    String generateQuotationHtml(String objId) throws IOException, TemplateException;

    Map<String, Object> getSeller(SysUsers user);


}
