package cn.sf_soft.common.util;

import javax.servlet.http.HttpSession;

import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.user.model.SysUsers;
/**
 * 用于在ThreadLocal中保存当前请求用户的session
 * @author king
 * @create 2013-9-30下午2:22:22
 */
public class HttpSessionStore {
	private static ThreadLocal<HttpSession> httpSession = new ThreadLocal<HttpSession>();
	
	public static HttpSession getHttpSession(){
		return httpSession.get();
	}
	/**
	 * 获取session中的{@link SysUsers}信息
	 * @return
	 */
	public static SysUsers getSessionUser(){
		return (SysUsers) httpSession.get().getAttribute(Attribute.SESSION_USER);
	}

	/**
	 * 获取session中的OS信息
	 * @return
	 */
	public static String getSessionOs(){
		return (String) httpSession.get().getAttribute(Attribute.OS_TYPE);
	}
	
	public static void setHttpSession(HttpSession session){
		httpSession.set(session);
	}
}
