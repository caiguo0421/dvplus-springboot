package cn.sf_soft.user.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.user.model.SysUsers;

import java.util.List;
import java.util.Map;
/**
 * 用户Dao
 * @author king
 * @create 2012-11-10下午03:01:42
 */
public interface UserDao extends BaseDao{

	/**
	 * 用户登录验证
	 * @param user_no	用户编码
	 * @param password	用户密码
	 * @return	登录成功返回包含用户信息的实体，否则返回null
	 */
	public SysUsers findUserByNoAndPwd(String user_no, String password);
	/**
	 * 通过用户编码得到用户的所有权限ID
	 * @param userNo
	 * @return
	 */
	public List<String> getPopedomIdByUserNo(String userNo);
	
	/**
	 * 获取权限功能范围
	 * @param userId
	 * @return moduleId-stationIds
	 */
	public Map<String, String> getUserModuleStationIds(String userId);
	/**
	 * 修改用户
	 * @param user	要修改的用户
	 * @return	修改是否成功
	 */
	public boolean updateUser(SysUsers user);
	
	/**
	 * 往数据库中添加用户
	 * @param user	要添加的用户
	 * @return	是否添加成功
	 */
	public boolean addUser(SysUsers user);
	
	/**
	 * 删除用户
	 * @param user_id	要删除用户的Id
	 * @return	是否删除成功
	 */
	public boolean deleteUser(String user_id);
	
	/**
	 * 根据用户Id得到用户信息
	 * @param user_id	用户Id
	 * @return	包含用户信息的实体类
	 */
	public SysUsers getUserById(String user_id);


	public SysUsers getUserByUserNo(String userNo);
	
	/**
	 * 分页得到用户列表
	 * @param pageNo	要显示第几页
	 * @param pageSize	每页显示记录的条数
	 * @return	包含用户信息的List集合
	 */
	public List<SysUsers> getUserListByPage(int pageNo, int pageSize);
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public boolean updatePassword(String userId, String oldPass, String newPass);
	
	/**
	 * 用户是否有次权限
	 * @param userId 用户Id
	 * @param popedomId  权限Id
	 * @return true 有此权限
	 */
	public boolean hasPopedom(String userId, String popedomId);

	boolean updateJpushId(String userId, String jpushId);

    boolean updateAvatarUrl(String userId, String avatarUrl);
}
