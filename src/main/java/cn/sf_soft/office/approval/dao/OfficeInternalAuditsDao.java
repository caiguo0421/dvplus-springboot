package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.OfficeInternalAudits;
/**
 * 内部审计DAO
 * @author king
 * @create 2013-1-17下午02:55:57
 */
public interface OfficeInternalAuditsDao {

	/**
	 * 根据单据编号得到内部审计单据的详细信息
	 * @param documentNo
	 * @return
	 */
	public OfficeInternalAudits getDocumentDetail(String documentNo);
	
	/**
	 * 更新单据
	 * @param officeInternalAudits
	 * @return
	 */
	public boolean updateOfficeInternalAutits(OfficeInternalAudits officeInternalAudits);
}
