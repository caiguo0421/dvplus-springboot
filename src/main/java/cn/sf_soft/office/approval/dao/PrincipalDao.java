package cn.sf_soft.office.approval.dao;

import cn.sf_soft.user.model.SysUsers;
/**
 * 
 * @author minggo
 * @created 2012-12-12
 */
public interface PrincipalDao {
	/**
	 * 
	 * @param departmentId
	 * @return
	 */
	public SysUsers getPrincipal(String departmentId);
}
