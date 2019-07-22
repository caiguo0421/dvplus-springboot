package cn.sf_soft.common.baseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 错误信息的输出，取代原的error2.jsp
 * @author caigx
 *
 */
@WebServlet(urlPatterns = {"/error2Servlet"}) 
public class Error2Servlet extends AjaxCrossDomainServlet {

	private static final long serialVersionUID = 5499614195251694500L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg =req.getAttribute("msg")+"";
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;application/json");

        this.setCrossDomainHeaders(req, resp);

		PrintWriter out = resp.getWriter();
		String rtnMsg = "{\"ret\":2,\"msg\":\""+msg+"\",\"data\":[]}";
		out.print(rtnMsg);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
