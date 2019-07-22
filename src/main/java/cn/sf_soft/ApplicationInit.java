package cn.sf_soft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.sf_soft.mobile.documentBuffer.task.DocumentStaticCalcTaskManager;
import cn.sf_soft.oa.notification.task.NotificationTaskManager;

public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private DocumentStaticCalcTaskManager documentStaticCalcTaskManager;
	
	@Autowired
	private NotificationTaskManager notificationTaskManager;

	
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationInit.class);
	
	@Override 
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			/*try{
				documentStaticCalcTaskManager.start();
				logger.debug("成功启动缓存文档计算任务");
			}catch(Exception e){
				logger.error("启动缓存文档计算任务时出错", e);
				throw new RuntimeException(e);
			}*/
			
			try{
				notificationTaskManager.start();
				logger.debug("成功启动通知后台任务");
			}catch(Exception e){
				logger.error("成功启动通知后台任务出错", e);
				throw new RuntimeException(e);
			}
		}
	}
	
	
	public void destory(){
	}

}
