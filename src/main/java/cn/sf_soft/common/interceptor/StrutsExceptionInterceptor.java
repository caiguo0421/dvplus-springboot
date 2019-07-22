package cn.sf_soft.common.interceptor;


import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.ServiceException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 拦截Struts Action 中的异常，并统一处理
 * @author king
 * @create 2013-9-29上午11:22:22
 */
public class StrutsExceptionInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1104809981704831199L;
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StrutsExceptionInterceptor.class);
	
	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		}catch (ServiceException e) {
			ServletActionContext.getRequest().setAttribute("msg", e.getMessage());
			return Action.ERROR;
		} catch (Exception e) {
			logger.error("Struts Action中出现异常", e);
			ServletActionContext.getRequest().setAttribute("msg", "服务器内部错误");
			return Action.ERROR;
		}
	}

}
