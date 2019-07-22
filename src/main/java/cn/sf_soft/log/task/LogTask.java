package cn.sf_soft.log.task;

import cn.sf_soft.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 日志定时任务
 */
@Component
public class LogTask {

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    private LogService logService;

    /**
     * 定时清理日志任务
     */
    //Fire at 00:15am every day
    @Scheduled(cron = "0 15 0 * * ? ")
    public void cleanLog() {
        logger.debug("定时清理日志任务开始");
        try {
            logService.cleanLog();
        } catch (Exception ex) {
            logger.error("定时清理日志任务出错", ex);
        }
    }

}
