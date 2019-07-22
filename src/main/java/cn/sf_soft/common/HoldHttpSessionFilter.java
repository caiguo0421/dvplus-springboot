package cn.sf_soft.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.sf_soft.common.util.HttpSessionStore;
/**
 * 在ThreadLocal中保存当前用户的HttpSession
 * @author king
 * @create 2013-9-30下午2:23:04
 */
public class HoldHttpSessionFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		HttpSessionStore.setHttpSession(((HttpServletRequest)request).getSession());
		filter.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
