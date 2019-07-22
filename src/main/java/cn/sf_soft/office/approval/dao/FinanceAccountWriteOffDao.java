package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.FinanceAccountWriteOffs;

/**
 * 销账处理
 * @author king
 * @create 2013-3-10上午11:42:06
 */
public interface FinanceAccountWriteOffDao {

	public List<FinanceAccountWriteOffs> findByProperty(String property, Object value);
	
	public boolean save(FinanceAccountWriteOffs financeAccountWriteOff);
	
	public FinanceAccountWriteOffs getDocumentDetails(String documentNo);
	
	public void update(FinanceAccountWriteOffs accountWriteOff);
}
