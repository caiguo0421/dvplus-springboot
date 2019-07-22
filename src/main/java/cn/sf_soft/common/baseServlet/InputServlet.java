package cn.sf_soft.common.baseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 取代input.jsp
 * @author lenovo
 *
 */
@WebServlet(urlPatterns = {"/inputServlet"}) 
public class InputServlet extends AjaxCrossDomainServlet {
	private static final long serialVersionUID = 4202118751365600516L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = "{\"ret\":2,\"msg\":\"参数错误\"}";
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;application/json");

		this.setCrossDomainHeaders(req, resp);

		PrintWriter out = resp.getWriter();
		out.print(msg);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
