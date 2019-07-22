package cn.sf_soft.office.approval.dao;

import java.sql.Timestamp;
import java.util.List;

import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;

public interface BaseRelatedObjectsDao {

	/**
	 * 根据客户名称 查找记录（不包含该客户）
	 * @param objectName
	 * @param objectId
	 * @return
	 */
	List<BaseRelatedObjects> getCustomerByName(String objectName, String objectId);

	/**
	 * 根据客户证件号 查找记录(不包含该客户)
	 * @param certificateNo
	 * @param objectId
	 * @return
	 */
	List<BaseRelatedObjects> getCustomerByCertificate(String certificateNo, String objectId);

	/**
	 * 获取往来对象的还款时间(即应收/付时间)。
	 * @param objectId
	 * @return
	 */
	Timestamp getRepayTimeOfObject(String objectId);

}
