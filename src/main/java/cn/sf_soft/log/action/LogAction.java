package cn.sf_soft.log.action;

import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.log.service.LogService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class LogAction extends BaseAction {

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    private LogService logService;


    private static final String LOG_LIST = "LOG_LIST";


    public String list() {
        List<Map<String, Object>> fileInfoList = logService.getFileInfoList();
        String logPath = logService.getLogPath();
        ServletActionContext.getRequest().setAttribute("log_list", fileInfoList);
        ServletActionContext.getRequest().setAttribute("logPath", logPath);
        return LOG_LIST;
    }




}
