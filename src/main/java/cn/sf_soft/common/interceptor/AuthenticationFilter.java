package cn.sf_soft.common.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import cn.sf_soft.common.util.Constant.Attribute;

/**
 * 过滤器，过滤所有请求管理员页面的未经过验证的请求
 * @author king
 * @create 2013-4-25下午07:18:03
 */
public class AuthenticationFilter implements Filter {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationFilter.class);
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		String uri = httpRequest.getRequestURI();//得到请求的URI
		
		
		
		if(session.getAttribute(Attribute.SESSION_USER) == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
