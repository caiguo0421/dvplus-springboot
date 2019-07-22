package cn.sf_soft.common.baseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by henry on 17-6-9.
 */

public class AjaxCrossDomainServlet extends HttpServlet {
    void setCrossDomainHeaders(HttpServletRequest req, HttpServletResponse resp){
        String origin = req.getHeader("origin");
        if(origin != null){
            resp.setHeader("Access-Control-Allow-Origin",origin);
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since, jpushid,os,password,stationid,token,userno,departmentId");
            resp.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");

            resp.setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        this.setCrossDomainHeaders(req, resp);
        resp.setContentLength(0);
        PrintWriter out = resp.getWriter();
        out.print("");
    }
}
