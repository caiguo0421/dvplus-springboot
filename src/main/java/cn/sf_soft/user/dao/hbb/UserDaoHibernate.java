package cn.sf_soft.user.dao.hbb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.user.dao.UserDao;
import cn.sf_soft.user.model.SysUsers;

/**
 * 用户持久化管理Hibernate实现
 * 
 * @author king
 */
@Repository("userDaoHibernate")
public class UserDaoHibernate extends BaseDaoHibernateImpl implements UserDao {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserDaoHibernate.class);
	//static final String POPEDOM_TYPE = "('全局通用', '车辆业务', '配件业务', '客户关系', '办公业务', '财务处理', '财务管理', '移动报表')";

	/**
	 * 添加用户
	 */
	public boolean addUser(SysUsers user) {
		try {
			getHibernateTemplate().save(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据用户Id删除用户
	 */
	public boolean deleteUser(String userId) {
		SysUsers user = new SysUsers();
		user.setUserId(userId);
		try {
			getHibernateTemplate().delete(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据用户Id得到用户信息
	 */
	public SysUsers getUserById(String userId) {
		return getHibernateTemplate().get(SysUsers.class, userId);
	}

	@Override
	public SysUsers getUserByUserNo(String userNo) {
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find(
				"from SysUsers u where u.userNo=?",
				new Object[] { userNo });
		if (user != null && user.size() != 0) {
			return user.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 分页查询用户信息
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SysUsers> getUserListByPage(final int pageNo, final int pageSize) {
		List<SysUsers> userList = (List<SysUsers>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createQuery("from SysUsers").setFirstResult((pageNo - 1) * pageSize)
						.setMaxResults(pageSize).list();
			}
		});
		return userList;
	}

	/**
	 * 用户登录验证
	 */
	@SuppressWarnings("unchecked")
	public SysUsers findUserByNoAndPwd(final String userNo, final String password) {
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find(
				"from SysUsers u left join fetch u.roles  where u.userNo=? and password=? and u.status=1",
				new Object[] { userNo, password });
		if (user != null && user.size() != 0) {
			return user.get(0);
		}

		user = (List<SysUsers>) getHibernateTemplate().find("from SysUsers u left join fetch u.roles  where u.userNo=? and u.status=1", new Object[] { userNo });
		if (user != null && user.size() != 0) {
			SysUsers u =  user.get(0);
			if(u.checkPassword(password, u.getPassword())){
				return u;
			}else{
				return null;
			}
		}

		return null;
	}

	/**
	 * 通过用户编码得到用户的所有权限ID
	 * 
	 * @param userNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPopedomIdByUserNo(final String userNo) {
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session
						.createSQLQuery(
								"SELECT DISTINCT rp.popedom_id FROM sys_users u "
										+ " LEFT JOIN ( SELECT  user_id , role_id FROM  sys_users_roles ) ur ON u.user_id = ur.user_id "
										+ " RIGHT JOIN sys_roles_popedoms rp ON ur.role_id = rp.role_id "
										+ " LEFT JOIN sys_popedoms p ON rp.popedom_id = p.popedom_id "
										+ " WHERE   u.user_no = ? ")
						.setString(0, userNo).list();
			}
		});
	}

	/**
	 * 获取权限功能范围
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getUserModuleStationIds(final String userId) {
		return (Map<String, String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Map<String, String> doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session
						.createSQLQuery("SELECT m.module_id, m.station_id FROM sys_users_modules m WHERE m.user_id = :userId AND m.module_id IN(SELECT DISTINCT sp.module_id FROM dbo.sys_popedoms sp )");
				query.setParameter("userId", userId);
				List<Object[]> list = query.list();
				Map<String, String> map = new HashMap<String, String>(list.size());
				for (Object[] obj : list) {
					map.put(obj[0].toString(), obj[1].toString());
				}
				return map;
			}
		});
	}

	/**
	 * 更新用户
	 */
	public boolean updateUser(SysUsers user) {
		try {
			getHibernateTemplate().update(user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改用户密码
	 */
	@SuppressWarnings("unchecked")
	public boolean updatePassword(String userId, String oldPass, String newPass) {
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find(
				"from SysUsers u  where u.userId=? and password=?", new Object[]{userId, oldPass});
		if (user != null && user.size() != 0) {
			user.get(0).setPassword(newPass);
			user.get(0).setPswdModifyTime(new Timestamp(new Date().getTime()));
			getHibernateTemplate().update(user.get(0));
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPopedom(String userId, String popedomId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(rp.popedom_id) as popedom_count  ").append("\r\n");
		sqlBuffer.append("FROM sys_users u LEFT JOIN ( SELECT  user_id , role_id FROM  sys_users_roles ) ur ON u.user_id = ur.user_id ")
				.append("\r\n");
		sqlBuffer.append("RIGHT JOIN sys_roles_popedoms rp ON ur.role_id = rp.role_id ").append("\r\n");
		sqlBuffer.append(" LEFT JOIN sys_popedoms p ON rp.popedom_id = p.popedom_id ").append("\r\n");
		sqlBuffer.append(" where u.user_id = :userId and  p.popedom_id = :popedomId ").append("\r\n");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("popedomId", popedomId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		if (list == null || list.size() == 0) {
			return false;
		}
		Integer popedomCount = (Integer) list.get(0).get("popedom_count");
		return Tools.toInt(popedomCount) > 0 ? true : false;
	}

	@Override
	public boolean updateJpushId(String userId, String jpushId) {
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find(
				"from SysUsers u  where u.userId=?", new Object[] { userId });
		if (user != null && user.size() != 0) {

			List<SysUsers> oldUsers = (List<SysUsers>) getHibernateTemplate().find(
					"from SysUsers u  where u.jpushId=?", new Object[] { jpushId });
			if (oldUsers != null && oldUsers.size() > 0){
				for(SysUsers oldUser : oldUsers){
					oldUser.setJpushId("");
					oldUser.setPswdModifyTime(new Timestamp(new Date().getTime()));
					getHibernateTemplate().update(oldUser);
				}
			}

			user.get(0).setJpushId(jpushId);
			user.get(0).setPswdModifyTime(new Timestamp(new Date().getTime()));
			getHibernateTemplate().update(user.get(0));
			return true;
		}
		return false;
	}

	@Override
	public boolean updateAvatarUrl(String userId, String avatarUrl) {
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find(
				"from SysUsers u  where u.userId=?", new Object[] { userId });
		if (user != null && user.size() != 0) {
			user.get(0).setAvatarUrl(avatarUrl);
			user.get(0).setPswdModifyTime(new Timestamp(new Date().getTime()));
			getHibernateTemplate().update(user.get(0));
			return true;
		}
		return false;
	}

}
