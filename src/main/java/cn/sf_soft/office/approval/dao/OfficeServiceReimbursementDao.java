package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.OfficeServiceReimbursements;
import cn.sf_soft.office.approval.model.ServiceWorkLists;
import cn.sf_soft.office.approval.model.ServiceWorkListsCharge;

/**
 * 维修费用报销DAO
 * @author king
 * @create 2013-1-16下午02:25:15
 */
public interface OfficeServiceReimbursementDao {

	/**
	 * 根据单据编号得到维修费用报销单的详细信息
	 * @param documentNo
	 * @return
	 */
	public OfficeServiceReimbursements getDocumentDetail(String documentNo);
	
	/**
	 * 修改维修费用报销单
	 * @param officeServiceReimbursements
	 * @return
	 */
	public boolean updateOfficeServiceReimbursements(OfficeServiceReimbursements officeServiceReimbursements);
	
	/**
	 * 根据Id得到可报销的维修工单费用信息
	 * @param swlcIds
	 * @return
	 */
	public List<ServiceWorkListsCharge> getServiceWorkListChargesReimburseAble(String[] swlcIds);
	
	/**
	 * 根据Id得到维修工单费用信息
	 * @param swlcId
	 * @return
	 */
	public ServiceWorkListsCharge getServiceWorkListCharges(String swlcId);
	
	/**
	 * 根据工单号得到维修工单的详细信息
	 * @param taskNo
	 * @return
	 */
	public ServiceWorkLists getServiceWorkLists(String taskNo);
	
	/**
	 * 修改维修工单费用信息
	 * @param serviceWorkListsCharge
	 * @return
	 */
	public boolean updateServiceWorkListsCharge(ServiceWorkListsCharge serviceWorkListsCharge);
	
}
