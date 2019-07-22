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

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 处理用户信息变更请求
 * 
 * @author henry
 * @create 2017年8月6日
 */
public class UserInfoAction extends BaseAction {
	/**
	 * 
	 */
	@Autowired
	private Config config;

	private static final long serialVersionUID = 8631929665121774782L;
	
	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserInfoAction.class);
	
	protected Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
			.excludeFieldsWithoutExposeAnnotation().create();

	private UserService userService;

	private String avatarUrl;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Access(pass=true)
	public String getUploadImageToken(){
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

	@Access(pass=true)
	public String updateAvatar(){
		SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		if (user == null) {
			return showErrorMsg("您还没有登录");
		}
		user.setAvatarUrl(this.avatarUrl);
		userService.updateAvatarUrl(user.getUserId(), this.avatarUrl);
		setResponseData(true);
		return SUCCESS;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
