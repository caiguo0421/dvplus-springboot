package cn.sf_soft.oa.notification.task;

import cn.sf_soft.message.service.MessageCenterService;
import cn.sf_soft.oa.notification.service.impl.OaNotificationA4sApprovalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;


@Component("notificationTask")
public class NotificationTask implements Runnable{

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NotificationTask.class);
	
	
	// 工作时间8-20
	private static final long BEGIN_WORK_HOUR = 8;

	private static final long END_WORK_HOUR = 20;
	
	@Autowired
	private  OaNotificationA4sApprovalService  oaNotificationA4sApprovalService;

	@Autowired
	private MessageCenterService messageCenterService;

	
	@Override
	public void run() {
		try{
			//可以使用quartz，但是现在暂时这样简单处理
			Calendar c = Calendar.getInstance();
			if (c.get(Calendar.HOUR_OF_DAY) < BEGIN_WORK_HOUR || c.get(Calendar.HOUR_OF_DAY) > END_WORK_HOUR) {
				// logger.debug("非工作时间，不启动后台通知任务:"+new Timestamp(System.currentTimeMillis()));
				return;
			}else{
				doTask();
			}
		}catch(Throwable e){
			logger.error("通知自动任务出错", e);
		}
	}
	
	
	private void doTask() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis());
		// logger.info("通知任务开始，at:"+beginTime);
//		 oaNotificationA4sApprovalService.pushMobileNofication();
//		 oaNotificationA4sApprovalService.pushUrgeNotification();
		messageCenterService.pushApproveMessage();

		// logger.info("通知任务结束，at:"+new Timestamp(System.currentTimeMillis())+",花费"+(System.currentTimeMillis()-beginTime.getTime())+"ms");
	}

}
