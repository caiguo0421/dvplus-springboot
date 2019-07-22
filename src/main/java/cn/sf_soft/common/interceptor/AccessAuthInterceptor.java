package cn.sf_soft.common.interceptor;

import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 访问控制拦截器，进行权限控制，对于Action中的方法，使用注解控制访问权限
 *
 * @author king
 * @create 2013-7-19下午3:06:48
 * @modify @modify 2013-9-25下午15:46 对于{@link Access}注解为pass的方法可不验证权限
 */
public class AccessAuthInterceptor implements Interceptor {

    /**
     *
     */
    private static final long serialVersionUID = 3028256411962545884L;
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AccessAuthInterceptor.class);
    private ServletContext servletContext;
    private WebApplicationContext wac;
    protected Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .setExclusionStrategies(new GsonExclutionStrategy()).create();

    @Inject
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void destroy() {
    }

    public void init() {
        wac = getRequiredWebApplicationContext(servletContext);
    }


    public String intercept(ActionInvocation invocation) throws Exception {
        String rtnMsg = "";
        try {
            rtnMsg = doIntercept(invocation);
        } catch (Exception ex) {
            logger.error("拦截器出错", ex);
            throw ex;
        }
        return rtnMsg;
    }


    private String doIntercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        Map<String, String[]> map = request.getParameterMap();


//		StringBuffer headerBuffer = new StringBuffer();
//		Enumeration headerNames = request.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			String key = (String) headerNames.nextElement();
//			String value = request.getHeader(key);
//			headerBuffer.append(key).append(":").append(value).append("\r\n");
//		}

        StringBuffer logBuffer = new StringBuffer();
        for (String keyString : map.keySet()) {
            String value = "";
            for (String s : map.get(keyString)) {
                value += s;
            }
            logBuffer.append(keyString).append(":").append(value).append("\r\n");
        }


        SysUsers user = (SysUsers) request.getSession().getAttribute(Attribute.SESSION_USER);

        /**
         * modify by shichunshan pc端在header中带上用户的信息，当session过期时自动检查用户的信息是否正确，
         * 正确时将用户信息加入session中
         */
        String userNo = request.getHeader("userNo");
        String password = request.getHeader("password");
        String OSString = request.getHeader("OS");
        String clientKey = request.getHeader("token");
        /*String jpushId = request.getHeader("jpushId");*/
        String stationId = request.getHeader("stationId");
        String departmentId = request.getHeader("departmentId");
        String brand = request.getHeader("brand");
        String osVersion = request.getHeader("osVersion");
        String registrationId = request.getHeader("registrationId"); //不同手机厂商消息推送的注册ID

        UserService userService = (UserService) wac.getBean("userService");
        if (user == null && ("PC".equals(OSString) || "H5".equals(OSString))) {
            password = URLDecoder.decode(password, "UTF-8");
            //增加了stationId，departmentId的处理规则 -20190109
            user = userService.login(userNo, password, clientKey, departmentId, stationId, OSString);
            if (user != null) {
                /*if (jpushId != null && jpushId.length() > 0 && !jpushId.equals(user.getJpushId())) {
                    user.setJpushId(jpushId);
                    userService.updateJpushId(user.getUserId(), jpushId);
                }*/
                //增加了stationId，departmentId的处理规则 -20190109
//				if (stationId != null && stationId.length() > 0){
//					user.setLoginStationId(stationId);
//				}
//				if(departmentId != null && departmentId.length() >0){
//					user.setLoginDepartmentId(departmentId);
//				}
                user.setReportSellerIds(userService.queryReportSellerIds(user.getUserId()));
            }
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.SESSION_USER, user);
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.OS_TYPE, OSString);
        }

        String terminalInfo = request.getHeader("User-Agent");
        if (terminalInfo != null && terminalInfo.length() > 0) {
            // 当前登录用户移动终端信息放入session中
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.SESSION_TERMINAL_INFO, terminalInfo);
        }
        if (clientKey != null && clientKey.length() > 0) {
            // clientKey 加入session (token)
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.TOKEN, clientKey);
        }

        /*if (jpushId != null && jpushId.length() > 0) {
            ServletActionContext.getRequest().getSession().setAttribute(Attribute.JPUSH_ID, jpushId);
        }*/

        logger.info(String.format(
                "Servlet请求信息，url:%s \r\n" +
                        "sessionid:%s \r\n" +
                        "系统类型:%s \r\n" +
                        "用户：%s \r\n " +
                        "请求参数:[%s] \r\n" +
                        "访问模块：%s",
                request.getRequestURL().toString(),
                request.getSession().getId(),
                StringUtils.isEmpty(OSString) ? "Moblie" : OSString,
                user == null ? null : user.toString(),
                logBuffer.toString(),
                invocation.getAction().getClass().getName()));
        /*
         * // 将当前登录用户的对象放入session中
         * ServletActionContext.getRequest().getSession()
         * .setAttribute(Attribute.SESSION_USER, user); //
         * 当前登录用户移动终端信息放入session中 ServletActionContext.getRequest().getSession()
         * .setAttribute(Attribute.SESSION_TERMINAL_INFO, terminalInfo);
         */

        if (user == null) {

            setResponse(false);
            return Action.LOGIN;
        } else {
            // ========================验证模块级别的访问权限======================
            boolean hadSetModulePopedom = false; // 是否设置了模块级别的访问权限
            boolean isAnnotationed = invocation.getAction().getClass().isAnnotationPresent(ModuleAccess.class);
            if (isAnnotationed) {
                String className = invocation.getAction().getClass().getName();
                ModuleAccess moduleAccess = invocation.getAction().getClass().getAnnotation(ModuleAccess.class);
                if (moduleAccess.pass()) {
                    logger.debug("访问" + className + "方法不需要权限");
                    // 模块不需要权限则不验证方法所需的权限
                    return invocation.invoke();

                } else if (moduleAccess.needPopedom().length() > 0) {
                    hadSetModulePopedom = true;
                    logger.debug("访问模块" + className + "需要权限：" + moduleAccess.needPopedom());
                    if (!user.getPopedomIds().contains(moduleAccess.needPopedom())) {
                        logger.info("用户" + user.getUserName() + user.getUserNo() + "没有权限访问模块" + className);
                        ServletActionContext.getRequest().setAttribute("msg", "拒绝访问:您没有权限访问此模块");
                        return Action.ERROR;
                    }

                } else if (moduleAccess.needOneOfPopedoms().length > 0) {
                    hadSetModulePopedom = true;
                    logger.debug("访问模块" + className + "需要其中一个权限：" + moduleAccess.needOneOfPopedoms());
                    boolean pass = false;
                    for (String popedomId : moduleAccess.needOneOfPopedoms()) {
                        if (user.getPopedomIds().contains(popedomId)) {
                            pass = true;
                            break;
                        }
                    }
                    if (!pass) {
                        ServletActionContext.getRequest().setAttribute("msg", "拒绝访问:您没有权限访问此模块");
                        return Action.ERROR;
                    }

                } else if (moduleAccess.needAllOfPopedoms().length > 0) {
                    hadSetModulePopedom = true;
                    logger.debug("访问模块" + className + "方法需要所有权限：" + moduleAccess.needAllOfPopedoms());
                    List<String> userPopodoms = user.getPopedomIds();
                    for (String popedomId : moduleAccess.needAllOfPopedoms()) {
                        if (!userPopodoms.contains(popedomId)) {
                            logger.info("用户" + user.getUserName() + user.getUserNo() + "没有权限访问模块" + className);
                            ServletActionContext.getRequest().setAttribute("msg", "拒绝访问:您没有权限访问此模块");
                            return Action.ERROR;
                        }
                    }
                }
            }

            // =====================验证方法级别的访问权限======================
            // 所调用的方法
            String method = invocation.getProxy().getMethod();
            // 获得所有已注解的方法
//            Collection<Method> methods = AnnotationUtils.getAnnotatedMethods(invocation.getAction().getClass(),
//                    Access.class);
            List<Access> accessList = AnnotationUtils.findAnnotations(invocation.getAction().getClass(),
                    Access.class);
            // 遍历所有已注解的方法
            for (Access access : accessList) {
//                if (method.equals(m.getName())) {
//                    Access access = m.getAnnotation(Access.class);

                    if (access.pass()) {
                        logger.debug("访问" + method + "方法不需要权限");
                        return invocation.invoke();
                    } else if (access.needPopedom().length() > 0) {
                        logger.debug("访问" + method + "方法需要权限：" + access.needPopedom());
                        if (user.getPopedomIds().contains(access.needPopedom())) {
                            return invocation.invoke();
                        }
                    } else if (access.needOneOfPopedoms().length > 0) {
                        logger.debug("访问" + method + "方法需要其中一个权限：" + access.needOneOfPopedoms());
                        for (String popedomId : access.needOneOfPopedoms()) {
                            if (user.getPopedomIds().contains(popedomId)) {
                                return invocation.invoke();
                            }
                        }
                    } else if (access.needAllOfPopedoms().length > 0) {
                        logger.debug("访问" + method + "方法需要所有权限：" + access.needAllOfPopedoms());
                        boolean flag = true;
                        List<String> userPopodoms = user.getPopedomIds();
                        for (String popedomId : access.needAllOfPopedoms()) {
                            if (!userPopodoms.contains(popedomId)) {
                                logger.info("用户" + user.getUserName() + user.getUserNo() + "没有权限" + popedomId);
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            return invocation.invoke();
                        }
                    }

                    logger.info("用户" + user.getUserName() + user.getUserNo() + "没有权限访问" + access.needPopedom());
                    ServletActionContext.getRequest().setAttribute("msg", "拒绝访问:您没有权限");
                    return Action.ERROR;

//                }
            }

            if (!hadSetModulePopedom) {
                // 如果模块级别和方法级别的权限都没有设置，则拒绝访问
                logger.warn("访问的方法" + method + "没有注解");
                ServletActionContext.getRequest().setAttribute("msg", "拒绝访问:所访问的方法未定义权限");
                return Action.ERROR;

            } else {
                return invocation.invoke();
            }

        }
    }

    private void setResponse(boolean basicAuth) {
        if (basicAuth) {
            // 设置HTTP响应：需要验证用户信息
            ServletActionContext.getResponse().setStatus(401);
            ServletActionContext.getResponse().setHeader("WWW-authenticate", "Basic realm=\"需要验证用户信息\"");
        }
        ResponseMessage<String> respMsg = new ResponseMessage<String>();
        respMsg.setRet(ResponseMessage.RET_NO_POPEDOM);
        respMsg.setErrcode(2);
        respMsg.setMsg("您没有登录或会话已超时");
        ServletActionContext.getRequest().setAttribute("msg", gson.toJson(respMsg));
    }

    /**
     * @param @param  sc
     * @param @return
     * @param @throws IllegalStateException
     * @throws
     * @Description: 获得spring容器
     */
    public WebApplicationContext getRequiredWebApplicationContext(ServletContext sc) throws IllegalStateException {

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
        if (wac == null) {
            throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
        }
        return wac;
    }
}
