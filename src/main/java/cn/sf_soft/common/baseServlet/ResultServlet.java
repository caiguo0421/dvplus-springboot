package cn.sf_soft.common.baseServlet;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 正常信息的输出，取代原的result.jsp
 * @author lenovo
 *
 */
@WebServlet(urlPatterns = {"/resultServlet"}) 
public class ResultServlet extends AjaxCrossDomainServlet {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ResultServlet.class);
	private static final long serialVersionUID = 861738602549467000L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result =req.getAttribute("result")+"";
//		logger.debug(String.format("返回客户端结果：result:%s", result));
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;application/json");

		this.setCrossDomainHeaders(req, resp);

		PrintWriter out = resp.getWriter();
		out.print(result);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
