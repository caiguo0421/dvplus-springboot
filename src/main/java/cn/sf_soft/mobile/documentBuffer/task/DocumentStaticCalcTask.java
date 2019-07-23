package cn.sf_soft.mobile.documentBuffer.task;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentCfg;
import cn.sf_soft.office.approval.model.ApproveDocuments;

@Component("documentStaticCalcTask")
public class DocumentStaticCalcTask extends ApplicationObjectSupport implements Runnable {
	@Qualifier("baseDao")
	protected BaseDao dao;
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DocumentStaticCalcTask.class);

	@Override
	public void run() {
		try{
			compute();
		}catch(Throwable e){
			logger.error("自动计算缓存文档时出错.", e);
		}
	}
	
	/*String[] bufferedModules = {"102025"};
	Short[] statusesToCompute = {20, 30};
	*/
	String bufferedModules = "('102025')";
	String statusesToCompute = "(20, 30)";
	public void compute(){
		@SuppressWarnings("unchecked")
		List<ApproveDocuments<?>> docs = (List<ApproveDocuments<?>>) dao.
				findByHql(String.format("from ApproveDocuments where moduleId in %s and status in %s", 
						bufferedModules, statusesToCompute));
		if(null==docs || docs.isEmpty())
			return;
		DocumentBufferCalc calc=null;
		String lastModuleId = null;
		ApplicationContext ctx = getApplicationContext();
		for(int i=0; i<docs.size(); i++){
			ApproveDocuments<?> doc = docs.get(i);
			String moduleId = doc.getModuleId();
			String documentNo = doc.getDocumentNo();
			Short docTypeNo = 10000;
			if(null==moduleId)
				continue;
			if(null==calc || !moduleId.equals(lastModuleId)){
				@SuppressWarnings("unchecked")
				List<MobileDocumentCfg> cfgs = (List<MobileDocumentCfg>) dao.
						findByHql("from MobileDocumentCfg where moduleId = ? and docTypeNo = ? ", moduleId, docTypeNo);
				if(null==cfgs || cfgs.isEmpty()){
					logger.error("未找到审批流程文档配置信息，模块："+moduleId+"；文档类型："+docTypeNo);
					continue;
				}
				/*String str = cfgs.get(0).getBufferCalcClass();
				if(null==str || "".equals(str)){
					logger.error("没有配置审批对象的计算类，模块："+moduleId+"；文档类型："+docTypeNo);
					continue;
				}
				try{
					calc = (DocumentBufferCalc)Class.forName(str).newInstance();
				}catch(Exception e){
					logger.error("实例化审批计算对象失败，模块："+moduleId+"；文档类型："+docTypeNo + "；类：" + str);
					continue;
				}*/
				String str = cfgs.get(0).getBufferCalcObject();
				if(null==str || "".equals(str)){
					logger.error("没有配置审批对象的计算类，模块："+moduleId+"；文档类型："+docTypeNo);
					continue;
				}
				Object obj = ctx.getBean(str);
				if(null==obj){
					logger.error("获取置审批对象失败，模块："+moduleId+"；文档类型："+docTypeNo);
					continue;
				}
				try{
					calc = (DocumentBufferCalc)obj;
				}catch(Exception e){
					logger.error("实例化审批计算对象失败，模块："+moduleId+"；文档类型："+docTypeNo + "；类：" + str);
					continue;
				}
				lastModuleId = moduleId;
			}
			try{
				calc.compute(false, moduleId, documentNo);
			}catch(Throwable e){
				logger.error("自动计算缓存文档时出错，模块："+moduleId+"；文档："+documentNo, e);
			}
		}
	}

}
