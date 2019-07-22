package cn.sf_soft.common.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.sf_soft.user.service.UserService;

import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.user.model.SysUsers;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 站点范围权限验证拦截器
 *
 * @author king
 * @create 2013-8-22下午3:00:25
 */
public class StationAuthInterceptor implements Interceptor {

    private static final long serialVersionUID = 6853979016392076380L;
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StationAuthInterceptor.class);

    @Autowired
    private UserService userService;

    public void destroy() {
    }

    public void init() {

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
        logger.debug("sessionid-->" + ServletActionContext.getRequest().getSession().getId());
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
        Map<String, Object> paramMap = invocation.getInvocationContext().getContextMap();//得到传递过来的参数
        String[] stationIds = (String[])paramMap.get("stationIds");

        //得到当前登录用户的所管辖的站点范围
        // List<String> ruledStationIds = user.getRuledStationIds();//new ArrayList<String>();
        List<String> ruledStationIds = userService.getAllStationIds(user);
        boolean isAnnotationed = invocation.getAction().getClass().isAnnotationPresent(ModuleAccess.class);
        if (isAnnotationed) {
            //如果Action中添加了ModuleAccess注解，则看该模块有没有设置权限功能范围
            ModuleAccess moduleAccess = invocation.getAction().getClass().getAnnotation(ModuleAccess.class);
            String moduleId = moduleAccess.moduleId();
            String[] userModuleStations = user.getRuledStationIdsByModuleId(moduleId);
            if (userModuleStations != null && userModuleStations.length > 0) {
                ruledStationIds = Arrays.asList(userModuleStations);
            }
        }


        if (stationIds == null || (stationIds.length > 0 && "".equals(stationIds[0]))) {
            //如果客户端没有指定访问的站点范围，则默认为所管辖范围内的所有站点
            paramMap.put("stationIds", ruledStationIds);
//            invocation.getInvocationContext().setParameters(paramMap);
            return invocation.invoke();
        }
        for (String s : stationIds) {
            if (!ruledStationIds.contains(s.trim())) {
                ServletActionContext.getRequest().setAttribute("msg", "暂无权限访问站点" + s);
                return Action.ERROR;
            }
        }
        return invocation.invoke();
    }
}
