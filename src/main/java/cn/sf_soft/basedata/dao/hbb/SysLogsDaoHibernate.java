package cn.sf_soft.basedata.dao.hbb;

import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.user.model.SysUsers;
import org.springframework.stereotype.Repository;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.basedata.model.SysLogs;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;

import java.sql.Timestamp;
import java.util.UUID;

@Repository("sysLogDao")
public class SysLogsDaoHibernate extends BaseDaoHibernateImpl implements SysLogsDao {

    private static final String COMPUTER_STRING = "手机服务端";

    public void addSysLog(SysLogs sysLogs) {
        getHibernateTemplate().save(sysLogs);
    }


    public void addSysLog(String logType, String module, String description) {
        SysUsers user = HttpSessionStore.getSessionUser();
        SysLogs logs = new SysLogs();
        logs.setLogId(UUID.randomUUID().toString());
        if (user != null) {
            logs.setStationId(user.getDefaulStationId());
            logs.setSysUser(user.getUserFullName());
        }
        logs.setLogType(logType);
        logs.setOccurTime(new Timestamp(System.currentTimeMillis()));
        logs.setModule(module);
        logs.setComputer(String.format("%s(%s)", COMPUTER_STRING, HttpSessionStore.getSessionOs()));
        logs.setDescription(description);

        getHibernateTemplate().save(logs);
    }

}
