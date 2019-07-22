package cn.sf_soft.oa.notification.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("notificationTaskManager")
public class NotificationTaskManager {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NotificationTaskManager.class);
	private static ScheduledExecutorService service;
	private static ScheduledFuture<?> future;

	private static final long INITIAL_DELAY = 1;
	private static final long PERIOD = 1;

	

	@Autowired
	private NotificationTask notificationTask;

	/**
	 * 启动任务
	 */
	public void start() {
		try {
			if (null != service)
				service.shutdown();
		} catch (Exception e) {
		}
//		service = Executors.newScheduledThreadPool(5);
		service = Executors.newSingleThreadScheduledExecutor();
		try {
			future = service.scheduleAtFixedRate(notificationTask, INITIAL_DELAY,PERIOD, TimeUnit.MINUTES);
			logger.info(String.format("成功启动通知后台任务：", INITIAL_DELAY, PERIOD));
		} catch (Exception e) {
			logger.error("启动通知后台任务失败。");
			throw e;
		}
	}

	/**
	 * 停止任务
	 */
	public void stop() {
		if(null != future){
			future.cancel(true);
		}
		if(null != service){
			service.shutdown();
		}
	}
}
