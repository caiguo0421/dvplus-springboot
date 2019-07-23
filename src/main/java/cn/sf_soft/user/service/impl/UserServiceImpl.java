package cn.sf_soft.user.service.impl;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.common.Config;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.EncryptionUtil;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.user.dao.UserDao;
import cn.sf_soft.user.model.SysLoginLogs;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;



    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
    // modify by shichunshan 设置用户权限改到业务层做
    @Autowired
    protected Config config;

    

//    @SuppressWarnings("unchecked")
//    public SysUsers login(String userNo, String password, String clientKey, String OS) {
//        if (userNo == null || password == null || clientKey == null) {
//            return null;
//        }
//        // pc端不用再解码，手机端要解码
//        if (!"PC".equals(OS)) {
//            password = EncryptionUtil.encode(password);
//        }
//
//        SysUsers user = userDao.findUserByNoAndPwd(userNo, password);
//        if (user != null) {
//            // 用户名和密码都正确
//            logger.debug("用户名和密码验证通过:" + userNo);
//
//            List<String> stationIds = getAllStationIds(user);
//            DetachedCriteria dc = DetachedCriteria.forClass(SysStations.class);
//            dc.add(Restrictions.in("stationId", stationIds));
//            List<SysStations> stations = userDao.findByCriteria(dc);
//            user.setSysStations(stations);
//            user.setPopedomIds(userDao.getPopedomIdByUserNo(userNo));
//            user.setModuleStations(userDao.getUserModuleStationIds(user.getUserId()));
//        } else {
//            logger.debug("用户名或密码错误:" + userNo + ";" + password);
//        }
//        if (null != user) {
//            user.setModuleIds(config.getModuleIds());
//        }
//        return user;
//    }

    /*public SysUsers login(String userNo, String password, String clientKey, String loginDepartmentId, String loginStationId, String OS) {
        if (userNo == null || password == null || clientKey == null) {
            return null;
        }
        // pc端不用再解码，手机端要解码
        if (!"PC".equals(OS)) {
            password = EncryptionUtil.encode(password);
        }

        SysUsers user = userDao.findUserByNoAndPwd(userNo, password);

        if (user != null) {
            // 用户名和密码都正确
            logger.debug("用户名和密码验证通过:" + userNo);

            List<String> stationIds = getAllStationIds(user);
            DetachedCriteria dc = DetachedCriteria.forClass(SysStations.class);
            dc.add(Restrictions.in("stationId", stationIds));
            List<SysStations> stations = userDao.findByCriteria(dc);
            user.setSysStations(stations);
            user.setPopedomIds(userDao.getPopedomIdByUserNo(userNo));
            user.setModuleStations(userDao.getUserModuleStationIds(user.getUserId()));
            user.setModuleIds(config.getModuleIds());

            //增加loginDepartmentId，loginStationId的处理
            if (StringUtils.isNotBlank(loginStationId)) {
                user.setLoginStationId(loginStationId);
                if (loginStationId.contains(",")) {
                    logger.warn(String.format("登陆站点部门有误，loginStationId格式有误:%s", loginStationId));
                } else {
                    user.setDefaulStationId(loginStationId);
                    logger.debug(String.format("用户%s的defaulStationId设置为:%s", userNo, loginStationId));
                }
            } else {
                logger.warn("登陆站点部门有误，登陆未设置参数loginStationId");
            }

            if (StringUtils.isNotBlank(loginDepartmentId)) {
                user.setLoginDepartmentId(loginDepartmentId);
                SysUnits loginDepartment = userDao.get(SysUnits.class, loginDepartmentId);
                if (loginDepartment == null) {
                    logger.warn(String.format("登陆站点部门有误，loginDepartmentId有误:%s,未查到记录", loginStationId));
                } else {
                    user.setDepartment(loginDepartment.getUnitId());
                    user.setDepartmentNo(loginDepartment.getUnitNo());
                    user.setDepartmentName(loginDepartment.getUnitName());
                    logger.debug(String.format("用户%s的department设置为:%s - %s", userNo, loginDepartment.getUnitId(), loginDepartment.getUnitNo()));
                }
            } else {
                logger.warn("登陆站点部门有误，登陆未设置参数loginDepartmentId");
            }

            //记录登陆的日志
            if (!"PC".equals(OS)) {
                SysLoginLogs loginLogs = new SysLoginLogs();
                loginLogs.setLogId(UUID.randomUUID().toString());
                loginLogs.setStationId(user.getDefaulStationId());
                loginLogs.setLoginTime(new Timestamp(System.currentTimeMillis()));
                loginLogs.setLoginUserId(user.getUserId());
                loginLogs.setLoginType(StringUtils.isEmpty(OS) ? "H5" : OS);
                loginLogs.setLoginDevice(clientKey);
                userDao.save(loginLogs);
            }
        }
        return user;
    }*/

    public SysUsers login(String userNo, String password, String clientKey, String loginDepartmentId, String loginStationId, String OS) {
        if (userNo == null || password == null || clientKey == null) {
            return null;
        }
        SysUsers user = checkUser(OS, userNo, password, true);

        if (user != null) {
            // 用户名和密码都正确
            logger.debug("用户名和密码验证通过:" + userNo);

            List<String> stationIds = getAllStationIds(user);
            DetachedCriteria dc = DetachedCriteria.forClass(SysStations.class);
            dc.add(Restrictions.in("stationId", stationIds));
            List<SysStations> stations = userDao.findByCriteria(dc);
            user.setSysStations(stations);
            user.setPopedomIds(userDao.getPopedomIdByUserNo(userNo));
            user.setModuleStations(userDao.getUserModuleStationIds(user.getUserId()));
            user.setModuleIds(config.getModuleIds());

            //增加loginDepartmentId，loginStationId的处理
            if (StringUtils.isNotBlank(loginStationId)) {
                user.setLoginStationId(loginStationId);
                if (loginStationId.contains(",")) {
                    logger.warn(String.format("登陆站点部门有误，loginStationId格式有误:%s", loginStationId));
                } else {
                    user.setDefaulStationId(loginStationId);
                    logger.debug(String.format("用户%s的defaulStationId设置为:%s", userNo, loginStationId));
                }
            } else {
                logger.warn("登陆站点部门有误，登陆未设置参数loginStationId");
            }

            if (StringUtils.isNotBlank(loginDepartmentId)) {
                user.setLoginDepartmentId(loginDepartmentId);
                SysUnits loginDepartment = userDao.get(SysUnits.class, loginDepartmentId);
                if (loginDepartment == null) {
                    logger.warn(String.format("登陆站点部门有误，loginDepartmentId有误:%s,未查到记录", loginStationId));
                } else {
                    user.setDepartment(loginDepartment.getUnitId());
                    user.setDepartmentNo(loginDepartment.getUnitNo());
                    user.setDepartmentName(loginDepartment.getUnitName());
                    logger.debug(String.format("用户%s的department设置为:%s - %s", userNo, loginDepartment.getUnitId(), loginDepartment.getUnitNo()));
                }
            } else {
                logger.warn("登陆站点部门有误，登陆未设置参数loginDepartmentId");
            }

            //记录登陆的日志
            if (!"PC".equals(OS)) {
                SysLoginLogs loginLogs = new SysLoginLogs();
                loginLogs.setLogId(UUID.randomUUID().toString());
                loginLogs.setStationId(user.getDefaulStationId());
                loginLogs.setLoginTime(new Timestamp(System.currentTimeMillis()));
                loginLogs.setLoginUserId(user.getUserId());
                loginLogs.setLoginType(StringUtils.isEmpty(OS) ? "H5" : OS);
                loginLogs.setLoginDevice(clientKey);
                userDao.save(loginLogs);
            }
        }
        return user;
    }

    private SysUsers checkUser(String OS, String userNo, String password, boolean updatePwd){
        logger.debug("检查用户是否存在(os:{};userNo:{};password:{})", new Object[]{OS, userNo, password});
        List<SysUsers> users = (List<SysUsers>)userDao.findByHql("from SysUsers u left join fetch u.roles  " +
                        "where u.userNo=? and u.status=1",
                new Object[] { userNo });
        if(null == users || users.isEmpty()){
            logger.debug("没有找到用户：{}", userNo);
            return null;
        }
        SysUsers user = users.get(0);
        String currentPassword = user.getPassword();
        String regex = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        String pwdEncoded = null;
        // 判断用户的密码是否是base64格式
        if(Pattern.matches(regex, currentPassword)){
            logger.debug("使用新编码校验密码");
            pwdEncoded = !"PC".equals(OS) ? EncryptionUtil.encodeNew(password) : password;
            if(StringUtils.equals(currentPassword, pwdEncoded)){
                return user;
            }
        }else{
            logger.debug("使用旧编码校验密码");
            pwdEncoded = !"PC".equals(OS) ? EncryptionUtil.encode(password) : password;
            if(user.checkPassword(currentPassword, pwdEncoded)){
                if(updatePwd){
                    try {
                        //将用户密码修改成base64编码后的密码
                        pwdEncoded = EncryptionUtil.encodeNew(password);
                        Query query = userDao.getCurrentSession().createQuery("update SysUsers set password=? where userId=?");
                        query.setParameter(0, pwdEncoded);
                        query.setParameter(1, user.getUserId());
                        query.executeUpdate();
                    }catch (Throwable e){
                        logger.error("将密码更换为新的编码方式出错", e);
                    }
                }
                return user;
            }
        }
        return null;
    }


    public boolean updatepass(String userNo, String oldPass, String newPass, String OS) {
        /*String encodePassword = EncryptionUtil.encodeNew(oldPass);
        String encodeNewPass = EncryptionUtil.encodeNew(newPass);
        logger.debug(String.format("用户:%s 原密码:%s(%s),修改后密码:%s(%s)", userNo, oldPass, encodePassword, newPass, encodeNewPass));

        SysUsers user = userDao.findUserByNoAndPwd(userNo, encodePassword);
        if (user == null) {
            throw new ServiceException("原密码不正确");
        }
        user.setPassword(encodeNewPass);
        user.setPswdModifyTime(new Timestamp(new Date().getTime()));
        boolean b = userDao.update(user);

        return b;*/
        SysUsers user = checkUser(OS, userNo, oldPass, false);
        if(null == user){
            throw new ServiceException("原密码不正确");
        }else{
            String encodeNewPass = EncryptionUtil.encodeNew(newPass);
            user.setPassword(encodeNewPass);
            user.setPswdModifyTime(new Timestamp(new Date().getTime()));
            boolean b = userDao.update(user);
            return b;
        }
    }

    private SysUsers findUser(String OS, String userNo, String password){
        List<SysUsers> users = (List<SysUsers>)userDao.findByHql("from SysUsers u left join fetch u.roles  where u.userNo=? and u.status=1",
                new Object[] { userNo });
        if(null == users || users.isEmpty()){
            return null;
        }
        SysUsers user = users.get(0);
        String pwd = user.getPassword();
        String regex = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        if(Pattern.matches(regex, pwd)){

        }
        return null;
    }

    public boolean updateJpushId(String userId, String jpushId) {
        boolean b = userDao.updateJpushId(userId, jpushId);
        return b;
    }

    @Override
    public List<Map<String, Object>> getContacts(String unitId, int pageNo, int pageSize) {
        String sql = "SELECT \n" +
                "ROW_NUMBER() OVER (ORDER BY a.user_id) as row_id, \n" +
                "ROW_NUMBER() OVER (PARTITION BY a.user_id  ORDER BY a.user_id DESC ) AS _id, \n" +
                "a.user_id,--用户ID\n" +
                "a.avatar_url,--用户头像\n" +
                "a.icq,--用户QQ\n" +
                "a.status, --用户状态\n" +
                "b.meaning AS status_meaning,--状态意思 \n" +
                "a.user_no,--用户编码\n" +
                "a.user_name,--用户名称\n" +
                "a.sex,--性别\n" +
                "a.birthday,--生日\t\n" +
                "a.phone,--固定电话\n" +
                "a.mobile,--移动电话\n" +
                "a.id_no,--身份证号\n" +
                "a.staff_no,--员工号\n" +
                "a.email,--邮件地址\n" +
                "a.institution,--所属单位ID\n" +
                "c.unit_name,--所属单位名称\n" +
                "c.station_id, --所属站点\n" +
                "a.department,--所属部门ID\n" +
                "d.unit_name AS department_name, --所属部门名称\n" +
                "f.role_type as role_type --角色类型\n" +
                "FROM \n" +
                "sys_users a \n" +
                "LEFT JOIN \n" +
                "(SELECT code, meaning FROM sys_flags WHERE field_no ='sys_users.status') b \n" +
                "ON a.status = b.code \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, full_id,unit_name, station_id FROM sys_units) c\n" +
                "ON a.institution = c.unit_id \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, unit_name FROM sys_units) d ON a.department = d.unit_id " +
                "LEFT JOIN sys_users_roles e on a.user_id=e.user_id " +
                "LEFT JOIN sys_roles f on f.role_id=e.role_id WHERE a.status=1 ";
        if (unitId != null && unitId.length() > 0) {
            sql += " AND a.department LIKE '" + unitId + "'";
        }
        sql = "SELECT * FROM ( " + sql + ") r WHERE r._id = 1 AND r.row_id > " + (pageNo - 1) * pageSize +
                " AND " +
                " r.row_id <= " + (pageNo) * pageSize;
        List<Map<String, Object>> rtnData = userDao.getMapBySQL(sql, null);
        //增加拼音 caigx
        if (rtnData != null && rtnData.size() > 0) {
            for (Map<String, Object> item : rtnData) {
                if (!item.containsKey("user_name")) {
                    continue;
                }
                String userName = (String) item.get("user_name");
                if (StringUtils.isBlank(userName)) {
                    item.put("name_pinyin", "");
                } else {
                    item.put("name_pinyin", GetChineseFirstChar.getFirstLetter(userName));
                }
            }
        }

        return rtnData;
    }

    @Override
    public Map<String, Object> getContact(String userId) {
        String sql = "SELECT \n" +
                "a.user_id,--用户ID\n" +
                "a.avatar_url,--用户头像\n" +
                "a.icq,--用户QQ\n" +
                "a.status, --用户状态\n" +
                "b.meaning AS status_meaning,--状态意思 \n" +
                "a.user_no,--用户编码\n" +
                "a.user_name,--用户名称\n" +
                "a.sex,--性别\n" +
                "a.birthday,--生日\t\n" +
                "a.phone,--固定电话\n" +
                "a.mobile,--移动电话\n" +
                "a.id_no,--身份证号\n" +
                "a.staff_no,--员工号\n" +
                "a.email,--邮件地址\n" +
                "a.institution,--所属单位ID\n" +
                "c.unit_name,--所属单位名称\n" +
                "c.station_id, --所属站点\n" +
                "a.department,--所属部门ID\n" +
                "d.unit_name AS department_name --所属部门名称\n" +
                "FROM \n" +
                "sys_users a \n" +
                "LEFT JOIN \n" +
                "(SELECT code, meaning FROM sys_flags WHERE field_no ='sys_users.status') b \n" +
                "ON a.status = b.code \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, full_id,unit_name, station_id FROM sys_units) c\n" +
                "ON a.institution = c.unit_id \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, unit_name FROM sys_units) d ON a.department = d.unit_id \n" +
                "WHERE a.user_id = '" + userId + "'";
        List<Map<String, Object>> result = userDao.getMapBySQL(sql, null);
        if (result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public long getContactsCount(String unitId) {
        String sql = "SELECT \n" +
                "COUNT(*) as ct " +
                "FROM \n" +
                "sys_users a \n" +
                "LEFT JOIN \n" +
                "(SELECT code, meaning FROM sys_flags WHERE field_no ='sys_users.status') b \n" +
                "ON a.status = b.code \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, full_id,unit_name, station_id FROM sys_units) c\n" +
                "ON a.institution = c.unit_id \n" +
                "LEFT JOIN \n" +
                "(SELECT unit_id, unit_name FROM sys_units) d ON a.department = d.unit_id WHERE a.status=1 ";
        if (unitId != null && unitId.length() > 0) {
            sql += " AND a.department LIKE '" + unitId + "'";
        }
        List<Map<String, Object>> result = userDao.getMapBySQL(sql, null);
        return Long.parseLong(result.get(0).get("ct").toString());
    }

    @Override
    public Map<String, Object> queryUserDepartment(String userNo) {
        Map<String, Object> response = new HashMap<String, Object>();
        String sql = "SELECT\n" +
                "  a.user_id,--用户ID\n" +
                "  b.user_no,--用户编码\n" +
                "  b.user_name,--用户名称\n" +
                "  d.station_id AS station_id,--站点ID\n" +
                "  d.default_station AS default_station_id,--默认站点ID\n" +
                "  c.unit_id,--兼职单位ID\n" +
                "  c.unit_no,--兼职单位编码\n" +
                "  c.unit_name,--兼职单位名称\n" +
                "  d.unit_id as department_id,--兼职部门ID\n" +
                "  d.unit_no as department_no,--兼职部门编码\n" +
                "  d.unit_name as department_name--兼职部门名称\n" +
                "FROM sys_users_units a\n" +
                "  INNER JOIN sys_users b ON a.user_id=b.user_id\n" +
                "  INNER JOIN sys_units c ON a.institution=c.unit_id\n" +
                "  INNER JOIN sys_units d ON a.department=d.unit_id\n" +
                "WHERE\n" +
                "  b.user_no='" + userNo + "'";
        response.put("secondary", userDao.getMapBySQL(sql, null));

        sql = "SELECT\n" +
                "  a.user_id,--用户ID\n" +
                "  a.user_no,--用户编码\n" +
                "  a.user_name, --用户名称\n" +
                "  c.unit_id, \n" +
                "  c.unit_name,--所属单位名称\n" +
                "  c.station_id AS station_id, --所属站点\n" +
                "  c.default_station AS default_station_id,--默认站点ID\n" +
                "  a.department as department_id,--所属部门ID\n" +
                "  d.unit_name AS department_name --所属部门名称\n" +
                "FROM\n" +
                "  sys_users a\n" +
                "  LEFT JOIN\n" +
                "  (SELECT code, meaning FROM sys_flags WHERE field_no ='sys_users.status') b\n" +
                "    ON a.status = b.code\n" +
                "  LEFT JOIN\n" +
                "  (SELECT unit_id, full_id, unit_name, station_id,default_station FROM sys_units) c\n" +
                "    ON a.institution = c.unit_id\n" +
                "  LEFT JOIN\n" +
                "  (SELECT unit_id, unit_name FROM sys_units) d ON a.department = d.unit_id\n" +
                "where a.user_no = '" + userNo + "'";
        List<Map<String, Object>> l = userDao.getMapBySQL(sql, null);
        response.put("major", l.size() > 0 ? l.get(0) : null);
        return response;
    }

    @Override
    public List<String> queryReportSellerIds(String userId) {
        Boolean extend_popedom = userDao.getMapBySQL("SELECT * FROM sys_roles_popedoms a\n" +
                "  INNER JOIN sys_users_roles b ON a.role_id=b.role_id\n" +
                "WHERE\n" +
                "  a.popedom_id='00851020' AND b.user_id = '" + userId + "'", null).size() > 0;
        if (extend_popedom) {
            // 跨部门权限
            return null;
        }

        Boolean department_popedom = userDao.getMapBySQL("SELECT * FROM sys_roles_popedoms a\n" +
                "INNER JOIN sys_users_roles b ON a.role_id=b.role_id\n" +
                "WHERE \n" +
                "a.popedom_id='00851010'\n" +
                "AND b.user_id='" + userId + "'", null).size() > 0;

        List<String> user_ids;

        if (department_popedom) {
            List<Map<String, Object>> result = userDao.getMapBySQL("SELECT user_id FROM sys_users\n" +
                    "WHERE department = (SELECT department\n" +
                    "FROM sys_users\n" +
                    "WHERE user_id = '" + userId + "')", null);
            user_ids = new ArrayList<String>(result.size());
            for (Map<String, Object> tmp : result) {
                user_ids.add(tmp.get("user_id").toString());
            }
            return user_ids;
        }
        user_ids = new ArrayList<String>(1);
        user_ids.add(userId);
        return user_ids;
    }

    public List<String> getAllStationIds(SysUsers user) {
        String sql = "SELECT\n" +
                "  a.station_id\n" +
                "FROM sys_units a\n" +
                "  INNER JOIN sys_users_units b ON b.department=a.unit_id\n" +
                "WHERE b.user_id='" + user.getUserId() + "'";
        List<Map<String, Object>> result = userDao.getMapBySQL(sql, null);
        List<String> stationIds = new ArrayList<>();

        String[] id_main = user.getInstitution().getStationId().split(",");
        stationIds.addAll(Arrays.asList(id_main));

        for (Map<String, Object> row : result) {
            String[] ids = row.get("station_id").toString().split(",");
            for (String id : ids) {
                if (!stationIds.contains(id)) {
                    stationIds.add(id);
                }
            }
        }

        return stationIds;
    }

    @Override
    public boolean updateAvatarUrl(String userId, String avatarUrl) {
        boolean b = userDao.updateAvatarUrl(userId, avatarUrl);
        return b;
    }


    /**
     * 取moduleId的管辖暂定
     * 如果根据模块ID取不取模块的管辖站点，需取当前登陆部门的管辖站点
     *
     * @param user
     * @param moduleId
     * @return
     */
    @Override
    public String[] getModuleStationId(SysUsers user, String moduleId) {
        String[] stations = user.getRuledStationIdsByModuleId(moduleId);
        if (stations == null || stations.length == 0) {
            //如果根据模块ID取不取模块的管辖站点，需取当前登陆部门的管辖站点
            SysUnits department = userDao.get(SysUnits.class, user.getDepartment());
            String stationId = department.getStationId();
            if (StringUtils.isNotEmpty(stationId)) {
                stations = stationId.split(",");
            }
        }
        return stations;
    }


}
