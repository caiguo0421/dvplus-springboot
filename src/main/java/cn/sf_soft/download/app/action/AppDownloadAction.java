package cn.sf_soft.download.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @Description: 返回移动客户端的下载地址
 * @author scs@sf-soft.cn
 * @date 2015-6-1 下午3:04:06
 */

public class AppDownloadAction extends ActionSupport {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AppDownloadAction.class);
	private String contentType = "text/html;charset=utf-8";
	// ios下载地址
	private String iosUrl;
	// android下载地址
	private String androidUrl;

	public String getIosUrl() {
		return iosUrl;
	}

	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}

	public String getAndroidUrl() {
		return androidUrl;
	}

	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}

	public String downloadApp() throws IOException, ServletException {
		HttpServletRequest httpServletRequest = ServletActionContext
				.getRequest();
		String userAgent = httpServletRequest.getHeader("user-agent");
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置输出文本的编码格式
		// response.setContentType(contentType);
		logger.info("userAgent:" + userAgent);
		if (userAgent.contains("iPhone")) {
			response.sendRedirect(iosUrl);
		} else if (userAgent.contains("Android")) {
			response.sendRedirect(androidUrl);
		} else {
			response.setContentType(contentType);
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				// 输出文本信息

				out.print("很抱歉，暂不支持该客户端！");
				out.flush();
				out.close();
			} catch (Exception ex) {
				out.println(ex.toString());
			}

		}
		return null;
	}
}
