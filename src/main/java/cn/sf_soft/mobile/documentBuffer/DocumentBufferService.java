package cn.sf_soft.mobile.documentBuffer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Timestamp;

import cn.sf_soft.common.util.TimestampUitls;
import org.apache.commons.lang3.StringUtils;


import cn.sf_soft.common.ServiceException;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.office.approval.service.impl.BaseApproveProcess;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class DocumentBufferService extends BaseApproveProcess {
	protected int documentClassId = 0;
	protected DocumentBufferCalc docBuffer; // 缓存文旦计算, 在子类中赋值。
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DocumentBufferService.class);

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Object getDocumentBuffered(String moduleId, String documentNo) {
		long beginTime = System.currentTimeMillis();
		JsonObject jo = docBuffer.getDocumentBuffered(moduleId, documentNo);
		
		JsonObject jwf = getWfTasks(jo.get("documentId").getAsString());
		if (null != jwf.getAsJsonObject("wfHistory")) {
			jo.add("wfHistory", jwf.getAsJsonObject("wfHistory"));
		}
		if (null != jwf.getAsJsonObject("wfCurrent")) {
			jo.add("wfCurrent", jwf.getAsJsonObject("wfCurrent"));
		}
		logger.debug(documentNo + "返回报文：" + jo);
		logger.debug(String.format("调用getDocumentBuffered方法,审批单号：%s，花费：%d ms", documentNo,
				(System.currentTimeMillis() - beginTime)));
		return jo;
	}

	private JsonObject getWfTasks(String documentId) {
		List<ApproveDocumentsPoints> points = this.getDocumentHistory(documentId);
		if(null==points || points.size()==0)
			throw new ServiceException("未找到文档的审批流程："+documentId);
		JsonArray wfHistoryTasks = new JsonArray();
		JsonArray wfCurrentTasks = new JsonArray();
		JsonArray wfFollowingTasks = new JsonArray();
		boolean following = false, deadForAccessCtrl = false;
		Timestamp lastApproveTime = null;
		ApproveDocumentsPoints p = null;
		for(int i=0; i<points.size(); i++){
			p  = points.get(i);
			if(!following && 0 == p.getApproveLevel() && wfCurrentTasks.size()>0){
				wfHistoryTasks.addAll(wfCurrentTasks);
				wfCurrentTasks = new JsonArray();
			}
			JsonObject task = new JsonObject();
			task.addProperty("oadpId", p.getOadpId());
			task.addProperty("status", p.getStatus());
			task.addProperty("approveLevel", p.getApproveLevel());
			if(0==p.getApproveLevel()){
				task.addProperty("approveName", "发起人");
			}else{
				task.addProperty("approveName", p.getApproveName());
			}
			task.addProperty("approverId", p.getApproverId());
			task.addProperty("approverNo", p.getApproverNo());
			task.addProperty("approverName", p.getApproverName());
			if(null != p.getRemark()){
				task.addProperty("remark", p.getRemark());
			}
			Timestamp approveTime;
			if(null != p.getApproveTime()){
				approveTime = p.getApproveTime();
				task.addProperty("approveTime", sdf.format(approveTime));
			}else{
				approveTime = new Timestamp(System.currentTimeMillis());
			}
			if (!following && p.getApproveLevel()!=0 && null != approveTime && null != lastApproveTime){
				long t = (approveTime.getTime() - lastApproveTime.getTime())/1000/60;
				long d = t/(60*24);
				long h = (t%(60*24))/60;
				long m = t%(60);
				// long s = (t%(3600*24*60))%60;
				String str;
				if(d>0){
					str = d + "天";
				}else{
					str = "";
				}
				if(h>0){
					str += h + "小时";
				}
				if(m>0){
					str += m + "分";
				}
				//task.addProperty("holdingTime", str + String.format("%1$02d:%2$02d", h, m));
				if(null!=str && !"".equals(str))
					task.addProperty("holdingTime", str);
			}
			lastApproveTime = p.getApproveTime();
			if(!deadForAccessCtrl && null == p.getApproveTime()){//增加deadForAthorization
				deadForAccessCtrl = p.getDeadForAccessCtrl();
				if(deadForAccessCtrl)
					task.addProperty("deadForAccessCtrl", deadForAccessCtrl);
			}
			if(following){
				wfFollowingTasks.add(task);
			}else{
				wfCurrentTasks.add(task);
			}
			if(null == p.getApproveTime()){
				following = true;
			}
		}
		if(null != p.getApproveTime()){
			wfHistoryTasks.addAll(wfCurrentTasks);
			wfCurrentTasks = null;
		}

		JsonObject jWf = new JsonObject();
		if(wfHistoryTasks.size()>0){
			JsonObject jo = new JsonObject();
			JsonObject jTitle = new JsonObject();
			jTitle.addProperty("title", "历史审批流程记录");
			jTitle.addProperty("collapsable", true);
			
			jTitle.addProperty("defaultExpanded", false);
			jo.add("boardTitle", jTitle);
			jo.add("tasks", wfHistoryTasks);
			jWf.add("wfHistory", jo);
		}
		if(null!=wfCurrentTasks && wfCurrentTasks.size()>0){
			JsonObject jo = new JsonObject();
			if(deadForAccessCtrl){
				jo.addProperty("deadForAccessCtrl", deadForAccessCtrl);
			}
			JsonObject jTitle = new JsonObject();
			jTitle.addProperty("title", "当前审批流程");
			jTitle.addProperty("collapsable", false);
			jTitle.addProperty("defaultExpanded", true);
			jo.add("boardTitle", jTitle);
			jo.add("tasks", wfCurrentTasks);
			if(wfFollowingTasks.size()>0){
				jo.add("followingTasks", wfFollowingTasks);
			}
			jWf.add("wfCurrent", jo);
		}
		return jWf;
	}

}
