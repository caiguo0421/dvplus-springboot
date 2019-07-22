package cn.sf_soft.user.service;

import cn.sf_soft.user.model.SysUsers;

import java.util.List;
import java.util.Map;

/**
 * 用户相关的业务
 *
 * @author king
 * @create 2013-9-10下午4:26:37
 */
public interface UserService {

    /**
     * @param userNo
     * @param password  以数据库中密码加密方式加密之后的数据
     * @param clientKey 手机认证证书
     * @return user, if login fail return null
     */
    public SysUsers login(String userNo, String password, String clientKey, String loginDepartmentId, String loginStationId, String OS);

    /**
     * 修改密码
     *
     * @param userNo 用户编码
     * @param oldPass
     * @param newPass
     * @param OS
     * @return
     */
    public boolean updatepass(String userNo, String oldPass, String newPass, String OS);

    public boolean updateJpushId(String userId, String jpushId);

    public List<Map<String, Object>> getContacts(String unitId, int pageNo, int pageSize);

    public Map<String, Object> getContact(String userId);

    long getContactsCount(String unitId);

    public Map<String, Object> queryUserDepartment(String userNo);

    List<String> queryReportSellerIds(String userId);

    public boolean updateAvatarUrl(String userId, String avatarUrl);

    public List<String> getAllStationIds(SysUsers user);

    public String[] getModuleStationId(SysUsers user, String moduleId);
}
