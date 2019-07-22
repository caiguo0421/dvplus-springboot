package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;


import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleConversionModifyItemDao;
import cn.sf_soft.office.approval.model.VehicleConversionModifyItem;

@Repository("conversionModifyItemDao")
public class VehicleConversionModifyItemDaoHibernate extends BaseDaoHibernateImpl implements  VehicleConversionModifyItemDao{
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleConversionModifyItemDaoHibernate.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleConversionModifyItem> getModifyItemsByVsccId(String conversionNo,String vsccId,short varyType){
		String hql = "from VehicleConversionModifyItem  where conversionNo = ? and  vsccId= ?  and varyType = ?";
		List<VehicleConversionModifyItem> conversions = (List<VehicleConversionModifyItem>) getHibernateTemplate().find(hql,
				new Object[] {conversionNo, vsccId,varyType });
		return conversions;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleConversionModifyItem> getModifyItemsByVsccId(String conversionNo,String vsccId){
		String hql = "from VehicleConversionModifyItem  where conversionNo = ? and  vsccId= ? ";
		List<VehicleConversionModifyItem> conversions = (List<VehicleConversionModifyItem>) getHibernateTemplate().find(hql,
				new Object[] {conversionNo, vsccId });
		return conversions;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleConversionModifyItem> getModifyItemsByConversionNo(String conversionNo){
		String hql = "from VehicleConversionModifyItem  where  conversionNo = ?";
		List<VehicleConversionModifyItem> conversions = (List<VehicleConversionModifyItem>) getHibernateTemplate().find(hql,
				new Object[] { conversionNo });
		return conversions;
	}

}
