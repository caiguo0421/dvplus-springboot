package cn.sf_soft.basedata.dao;

import cn.sf_soft.basedata.model.SysLogs;

/**
 * 系统日志
 * @author king
 * @create 2013-3-7下午03:33:30
 */
public interface SysLogsDao {

	public void addSysLog(SysLogs sysLogs);

	public void addSysLog(String logType, String module, String description);
}
