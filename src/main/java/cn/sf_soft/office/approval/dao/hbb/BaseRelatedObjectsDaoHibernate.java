package cn.sf_soft.office.approval.dao.hbb;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.BaseRelatedObjectsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;

/**
 * 往来对象操作
 * 
 * @author lenovo
 *
 */
@Repository("baseRelatedObjectsDao")
public class BaseRelatedObjectsDaoHibernate extends BaseDaoHibernateImpl implements BaseRelatedObjectsDao {

	// 根据客户名称 查找记录（不包含该客户）
	@SuppressWarnings("unchecked")
	@Override
	public List<BaseRelatedObjects> getCustomerByName(String objectName, String objectId) {
		String hql = "from BaseRelatedObjects where objectName = ? AND objectId<>? AND (status is null or status = 1)";
		List<BaseRelatedObjects> list = (List<BaseRelatedObjects>) getHibernateTemplate().find(hql, objectName,
				objectId);
		return list;
	}
	
	
	// 根据客户证件号 查找记录(不包含该客户)
	@Override
	@SuppressWarnings("unchecked")
	public List<BaseRelatedObjects> getCustomerByCertificate(String certificateNo,String objectId){
		String hql = "from BaseRelatedObjects where (ISNULL(certificateNo,'')<>'' AND certificateNo = ?) AND objectId<>? AND (status is null or status = 1)";
		List<BaseRelatedObjects> list = (List<BaseRelatedObjects>) getHibernateTemplate().find(hql, certificateNo,
				objectId);
		return list;
	}
	
	
	@Override
	//获取往来对象的还款时间(即应收/付时间)。
	public Timestamp getRepayTimeOfObject(String objectId){
		Timestamp dt = new Timestamp(System.currentTimeMillis()) ;
		if(StringUtils.isEmpty(objectId)){
			return dt;
		}
		BaseRelatedObjects baseObj = getHibernateTemplate().get(BaseRelatedObjects.class, objectId);
		
		if(baseObj== null || baseObj.getBillingDay()==null||baseObj.getRepayMonth()== null || baseObj.getRepayDay() == null){
			return dt;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int nDays = baseObj.getBillingDay()-calendar.get(Calendar.DATE);
		int nMonths = (nDays<0)?1:0;
		
		calendar.add( Calendar.MONTH,nMonths);
		calendar.add(Calendar.DATE,nDays);
		
		calendar.add(Calendar.MONTH,baseObj.getRepayMonth());
		calendar.add(Calendar.DATE, (baseObj.getRepayDay()-calendar.get(Calendar.DATE)) );
		return new Timestamp(calendar.getTime().getTime());
	}

}
