package cn.sf_soft.user.action;

import cn.sf_soft.common.Config;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.common.util.EncryptionUtil;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.MobileAttestationService;
import cn.sf_soft.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 处理用户请求
 *
 * @author king
 * @create 2013-9-17上午11:32:23
 * @modify by ShiChunshan 修改了登陆方法，以适应pc端登陆 2015年4月30日09:43:08
 */
public class UserAction extends BaseAction {
    /**
     *
     */
    @Autowired
    private Config config;

    private static final long serialVersionUID = 8631929665121774782L;

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserAction.class);

    protected Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation().create();

    private String userNo;
    private String token;
    private String password;
    private String newPass;
    private String terminalInfo;
    private UserService userService;
    private MobileAttestationService mobileAuthService;

    private String stationId;
    private String departmentId;
    private String userId;
    private String unitId;

    private String jpushId;
    private String avatarUrl;

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public void setTerminalInfo(String terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setMobileAuthService(MobileAttestationService mobileAuthService) {
        this.mobileAuthService = mobileAuthService;
    }

    public String queryUserDepartment() {
        setResponseData(userService.queryUserDepartment(userNo));
        return SUCCESS;
    }

    /**
     * 用户登录 modify by ShiChunshan
     */
    public String login() {
        if (Tools.isEmpty(userNo) || Tools.isEmpty(password) || Tools.isEmpty(token)) {
            if (Tools.isEmpty(token)) {
                logger.warn("token是空的！");
            }
            return showErrorMsg("用户名或密码错误或手机未通过认证");
        }

        String clientKey = "";
        // pc登录 pc端规定传OS为“PC”
        if (OS.equals("PC")) {
            clientKey = token;
        } else {
            // 移动端登录
            if (OS.equals("H5")) {
                clientKey = token;
            }
//			else if (OS != null) {
//				// 老版本的客户端登录时没有传递OS参数，并且使用的ECB模式，新版本客户端改成了CBC模式，为兼容旧版本，作此判断
//				password = DES_cbc.decrypt(password, key);
//				clientKey = DES_cbc.decrypt(token, key);
//
//			} else {
//				clientKey = DES.decrypt(token, key);
//				password = DES.decrypt(password, key);
//			}
            if (config.isMobileAuthOn()) {
                List<SysComputerKey> computerKey = mobileAuthService.getComputerKeyByKey(clientKey);
                if (computerKey == null || computerKey.size() == 0 || computerKey.get(0).getApproveStatus() != 1) {
                    if (computerKey == null || computerKey.size() == 0) {
                        logger.warn("客户端传过来的token如下：" + token + ",从电脑中查询的是空的！");

                    } else {
                        logger.warn("客户端传过来的token如下：" + token + ",从电脑中查询的token如下：");
                        for (SysComputerKey computerKey2 : computerKey) {
                            logger.warn(computerKey2.toString());
                        }
                    }
                    return showErrorMsg("手机未通过认证");
                }
            }
        }

        //增加了stationId，departmentId的处理规则 -20190109
        SysUsers user = userService.login(userNo, password, clientKey, departmentId, stationId, OS);

        if (user == null) {
            return showErrorMsg("密码错误,如果您修改过密码，请重新登录");
        }

        user.setModuleIds(config.getModuleIds());

        if (this.jpushId != null && this.jpushId.length() > 0 && !this.jpushId.equals(user.getJpushId())) {
            user.setJpushId(this.jpushId);
            userService.updateJpushId(user.getUserId(), this.jpushId);
        }


        //loginStationId,loginDepartmentId的处理放到userService.login中 -20190109
//        if(stationId != null && stationId.length() > 0){
//            user.setLoginStationId(stationId);
//        }
//
//        if(departmentId !=null && departmentId.length() > 0){
//            user.setLoginDepartmentId(departmentId);
//        }


        user.setReportSellerIds(userService.queryReportSellerIds(user.getUserId()));

        if (terminalInfo == null || terminalInfo.length() == 0) {
            HttpServletRequest request = ServletActionContext.getRequest();
            terminalInfo = request.getHeader("User-Agent");
        }

        // 将当前登录用户的对象放入session中
        ServletActionContext.getRequest().getSession().setAttribute(Attribute.SESSION_USER, user);
        // 当前登录用户移动终端信息放入session中
        ServletActionContext.getRequest().getSession().setAttribute(Attribute.SESSION_TERMINAL_INFO, terminalInfo);
        // clientKey 加入session (token)
        ServletActionContext.getRequest().getSession().setAttribute(Attribute.TOKEN, clientKey);
        // os 加入session
        ServletActionContext.getRequest().getSession().setAttribute(Attribute.OS_TYPE, OS);

        /*if (this.jpushId != null && this.jpushId.length() > 0) {
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.JPUSH_ID, this.jpushId);
        }*/

        ResponseMessage<SysUsers> respMsg = new ResponseMessage<SysUsers>(user);
        ServletActionContext.getRequest().setAttribute("result", gson.toJson(respMsg));
        return SUCCESS;
    }

    /**
     * 退出登录
     *
     * @return
     */
    public String logout() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute(Attribute.SESSION_TERMINAL_INFO);
        session.removeAttribute(Attribute.SESSION_USER);
        session.invalidate();
        setResponseData("注销登录成功");
        return SUCCESS;
    }

    /**
     * 修改密码
     *
     * @return
     */
    public String updatePass() {
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
        if (user == null) {
            return showErrorMsg("您还没有登录");
        }

        try {
            userService.updatepass(user.getUserNo(), password, newPass, OS);
        } catch (Exception ex) {
            return showErrorMsg(ex.getMessage());
        }

        setResponseData("密码修改成功");
        return SUCCESS;
    }

    public String getUploadImageToken() {
//		String accessKey = "JwNkqQ73f7wKrMiDu-YbkEC-eEpOjNvi4VpK-m4X";
//		String secretKey = "GThGCGBitVJe0Sl3PVrPlGZWcFDBUC4ayvGfrEf_";
//		String bucket = "dvplus-mobile";
        String accessKey = this.config.getApplicationConfig("qiniu.accessKey");
        String secretKey = this.config.getApplicationConfig("qiniu.secretKey");
        String bucket = this.config.getApplicationConfig("qiniu.bucket");
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("key", "avatar/uuid/" + UUID.randomUUID().toString());
        putPolicy.put("save_key", true);
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        setResponseData(upToken);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateAvatar() {
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
        if (user == null) {
            return showErrorMsg("您还没有登录");
        }
        user.setAvatarUrl(this.avatarUrl);
        userService.updateAvatarUrl(user.getUserId(), this.avatarUrl);
        setResponseData(true);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getContacts() {
        List<Map<String, Object>> list = userService.getContacts(unitId, pageNo, pageSize);
        ResponseMessage<List<Map<String, Object>>> result = new ResponseMessage<List<Map<String, Object>>>(list);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalSize(userService.getContactsCount(unitId));
        setResponseMessageData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getContact() {
        setResponseData(userService.getContact(userId));
        return SUCCESS;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


}
