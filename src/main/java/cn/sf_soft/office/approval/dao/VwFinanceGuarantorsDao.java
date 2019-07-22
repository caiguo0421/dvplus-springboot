package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.VwFinanceGuarantors;

public interface VwFinanceGuarantorsDao {

	public void update(VwFinanceGuarantors vwFinanceGuarantors);
	
	public List<VwFinanceGuarantors> findByProperty(String propertyName, Object value);
}
