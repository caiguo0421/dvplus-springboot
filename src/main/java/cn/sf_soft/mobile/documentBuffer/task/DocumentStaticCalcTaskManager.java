package cn.sf_soft.mobile.documentBuffer.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.sf_soft.office.approval.documentBuffered.SaleContractChargeVaryBufferCacl;

@Component("documentStaticCalcTaskManager")
public class DocumentStaticCalcTaskManager {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DocumentStaticCalcTaskManager.class);
	private static ScheduledExecutorService service;
	
	private static final long INITIAL_DELAY = 1;
	private static final long PERIOD = 1;
	
	@Autowired
	private DocumentStaticCalcTask documentStaticCalcTask;
	/**
	 * 启动任务
	 */
	public void start()  {
		try{
			if(null != service)
				service.shutdown();
		}catch(Exception e){
		}
		service = Executors.newScheduledThreadPool(5);
		try{
			service.scheduleAtFixedRate(documentStaticCalcTask, INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
			logger.info(String.format("成功启动缓存文档计算任务：", INITIAL_DELAY, PERIOD));
		}catch(Exception e){
			logger.error("启动缓存文档计算任务失败。");
			throw e;
		}
	}
	
	/**
	 * 停止任务
	 */
	public void stop(){
		service.shutdown();
	}
}
