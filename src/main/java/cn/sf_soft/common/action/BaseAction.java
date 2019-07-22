package cn.sf_soft.common.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.sf_soft.common.gson.HibernateProxyTypeAdapter;
import cn.sf_soft.common.util.HttpSessionStore;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.sf_soft.common.Config;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.user.model.SysUsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action 基类，
 * 
 * @author king
 * @create 2013-9-29上午11:32:20
 */
public class BaseAction extends ActionSupport {
	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4184338576413852152L;

	protected List<String> stationIds;
	protected int pageNo;
	protected int pageSize;
	protected String searchCriteria;
	protected String version;// 客户端APP版本号
	protected String OS;// 客户端系统名称

	protected String brand; //手机品牌
	protected String osVersion; //手机操作系统版本

	@Autowired
	protected Config config;

	protected Gson gson = new GsonBuilder()
			.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
			.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
			.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
			.setExclusionStrategies(new GsonExclutionStrategy()).create();

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public void setStationIds(List<String> stationIds) {
		if (stationIds == null) {
			return;
		}
		this.stationIds = new ArrayList<String>(stationIds.size());
		for (String sid : stationIds) {
			this.stationIds.add(sid.trim());
		}
	}

	public void setPageNo(int pageNo) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 999 || pageSize < 1)
			pageSize = 999;
		this.pageSize = pageSize;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	protected Object getAttributeFromSession(String name) {
		return ServletActionContext.getRequest().getSession()
				.getAttribute(name);
	}

	/**
	 * 封装 {@link HttpServletRequest#setAttribute(String, String)}
	 * 
	 * @param msg
	 * @return {@link Action#ERROR}
	 */
	protected String showErrorMsg(String msg) {
		ServletActionContext.getRequest().setAttribute("msg", msg);
		return ERROR;
	}

	/**
	 * call {@link HttpServletRequest#setAttribute("result", result)}
	 * 
	 * @param result
	 */
	protected void setAttribute(String result) {
//		logger.info("返回给客户端的结果：" + result);
		ServletActionContext.getRequest().setAttribute("result", result);
	}

	protected void setResponseCommonData(Object data) {
		setAttribute(gson.toJson(data));
	}

	/**
	 * 设置返回结果<br>
	 * (将 respMsg 转成json格式 并调用{@link #setAttribute(String)})
	 * 
	 * @param respMsg
	 */
	protected void setResponseMessageData(ResponseMessage<?> respMsg) {
		respMsg.setPageNo(pageNo);
		respMsg.setPageSize(pageSize);
		setAttribute(gson.toJson(respMsg));
	}

	/**
	 * 设置返回结果<br>
	 * (将 data封装为{@link ResponseMessage} 并调用
	 * {@link #setResponseMessageData(ResponseMessage)})<br>
	 * 最终封装成{@link ResponseMessage}的JSON格式
	 * 
	 * @param data
	 */
	protected void setResponseData(Object data) {
		ResponseMessage<Object> respMsg = new ResponseMessage<Object>(data);
		setResponseMessageData(respMsg);
	}

	/**
	 * 获取当前用户的默认站点
	 * 
	 * @return
	 */
	protected String getDefaultStationId() {
//		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
//		String stationId;
//		if ("".equals(user.getInstitution().getDefaultStation()) || user.getInstitution().getDefaultStation() == null) {
//			stationId = "";
//		} else {
//			stationId = user.getInstitution().getDefaultStation();
//		}
//		return stationId;
		//DefaultStationId的算法变更，取user的DefaulStationId
		SysUsers user = HttpSessionStore.getSessionUser();
		return user.getDefaulStationId();
	}

	/**
	 * 获取当前用户
	 *
	 * @return
	 */
	protected SysUsers getLoginUser(){
		return (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
	}
}
