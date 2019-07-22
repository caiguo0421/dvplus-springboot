package cn.sf_soft.common.baseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 取代login.jsp
 * @author caigx
 *
 */
@WebServlet(urlPatterns = {"/loginServlet"}) 
public class LoginServlet extends AjaxCrossDomainServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg =req.getAttribute("msg")+"";
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;application/json");

		this.setCrossDomainHeaders(req, resp);

		PrintWriter out = resp.getWriter();
		out.print(msg);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
