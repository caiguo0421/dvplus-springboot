package cn.sf_soft;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import cn.sf_soft.mobile.documentBuffer.task.DocumentStaticCalcTaskManager;
import cn.sf_soft.oa.notification.task.NotificationTaskManager;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStop implements ApplicationListener<ContextClosedEvent> {
	@Autowired
	private DocumentStaticCalcTaskManager documentStaticCalcTaskManager;

	@Autowired
	private NotificationTaskManager notificationTaskManager;
	
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationStop.class);
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		logger.debug("ContextStoppedEvent  received");
		if(event.getApplicationContext().getParent() == null){
			stop();
		}
	}
	
	
	private void stop(){
		/*try{
			documentStaticCalcTaskManager.stop();
			logger.debug("成功停止缓存文档计算任务");
		}catch(Exception e){
			logger.error("停止缓存文档计算任务时出错", e);
			throw new RuntimeException(e);
		}*/
		
		
		try{
			notificationTaskManager.stop();
			logger.debug("成功停止通知后台任务");
		}catch(Exception e){
			logger.error("停止通知后台任务时出错", e);
			throw new RuntimeException(e);
		}
	}

}
