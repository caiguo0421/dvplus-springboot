package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.PrincipalDao;
import cn.sf_soft.user.model.SysUsers;

/**
 * find the principal of document.
 * @author minggo
 * @created 2012-12-12
 */
@Repository("principalDaoHibernate")
public class PrincipalDaoHibernate extends BaseDaoHibernateImpl implements PrincipalDao {
	
	/**
	 * find out the principal
	 * @param departmentId  it's not only a departmentId in the document,
	 *  but also the same to units table's unit_id
	 * @return a user or null
	 */
	@SuppressWarnings("unchecked")
	public SysUsers getPrincipal(String departmentId){
		SysUsers principal = new SysUsers();
		String hql = "select u.userId, u.userNo,u.userName from SysUnits un,SysUsers u where u.userId=un.principal and un.unitId=?";
		List<Object> principals = (List<Object>) getHibernateTemplate().find(hql,departmentId);
		if(!principals.isEmpty()){
			for(Object o : principals){
				Object[] princi = (Object[]) o;
				principal.setUserId((String) princi[0]);
				principal.setUserNo((String) princi[1]);
				principal.setUserName((String) princi[2]);
			}
			return principal;
		}
		return null;
	}
}
