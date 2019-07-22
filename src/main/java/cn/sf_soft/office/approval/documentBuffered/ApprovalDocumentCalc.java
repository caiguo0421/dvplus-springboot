package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentCfg;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VehicleSaleContractsVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同变更-缓存数据接口
 * 
 * @author caigx
 *
 */
@Service("saleContractVaryBufferCalc")
abstract public class ApprovalDocumentCalc extends DocumentBufferCalc {

	public int documentClassId = 10000;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApprovalDocumentCalc.class);
	private static String bufferCalcVersion = "20161202.6";

	private static String moduleId = "102025";
	private static short docTypeNo = 10000;
	
	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	@Override
	public JsonObject getDocumentBuffered(String moduleId, String documentNo){
		try{
			long beginTime = System.currentTimeMillis();
			if(!lockDocument(moduleId, documentNo)){
				throw new ServiceException("可能有其他客户端在操作该文档，目前不能操作它。模块编号："+moduleId+"业务单号："+documentNo);
			}
			long t = System.currentTimeMillis();
			if(t-beginTime>500){
				logger.debug(String.format("等待锁定缓存文档, 审批单号：%s，花费：%d ms", documentNo,
						(System.currentTimeMillis() - beginTime)));
			}

			MobileDocumentBuffered bufferedDoc = compute(false, moduleId, documentNo);
			logger.debug(String.format("计算缓存文档, 审批单号：%s，花费：%d ms", documentNo,
					(System.currentTimeMillis() - beginTime)));
			JsonObject jo = new JsonObject();
			jo.addProperty("moduleId", moduleId);
			jo.addProperty("documentId", bufferedDoc.getBusiBillId());
			jo.addProperty("documentNo", documentNo);
			ApproveDocuments<?> approveDoc= (ApproveDocuments<?>)bufferedDoc.getBusiBill();
			jo.addProperty("stationId", approveDoc.getStationId());
			// 数据版本
			Date d = this.getBusiBillModifyTime(bufferedDoc);
			if(null!=d)
				jo.addProperty("modifyTime", sdfWithMilli.format(d));
			jo.add("board", board2Json(bufferedDoc.getBoard()));
			return jo;
			
		}finally{
			unlockDocument(moduleId, documentNo);
		}
	}

	protected Date getBusiBillModifyTime(MobileDocumentBuffered bufferedDoc){
		return null;
	}
	
	public MobileDocumentBuffered loadDocumentBuffered(String moduleId, String documentNo){
		ApproveDocuments<?> approveDoc;
		@SuppressWarnings("unchecked")
		List<ApproveDocuments<?>> docs = (List<ApproveDocuments<?>>) dao.
				findByHql("from ApproveDocuments where moduleId = ? and documentNo = ? ", moduleId, documentNo);
		if(null==docs || docs.isEmpty()){
			throw new ServiceException("未找到审批流程，模块编号："+moduleId+"审批单号："+documentNo);
		}
		approveDoc = docs.get(0);
		@SuppressWarnings("unchecked")
		List<MobileDocumentBuffered> bufferedDocs = (List<MobileDocumentBuffered>) dao.
				findByHql("from MobileDocumentBuffered where mobileDocumentCfg.moduleId = ? and mobileDocumentCfg.docTypeNo = ? and busiBillId = ? ", 
						moduleId, docTypeNo, approveDoc.getDocumentId());
		MobileDocumentBuffered bufferedDoc;
		if(null==bufferedDocs || bufferedDocs.isEmpty()){
			bufferedDoc = org.springframework.beans.BeanUtils.instantiate(MobileDocumentBuffered.class);
			@SuppressWarnings("unchecked")
			List<MobileDocumentCfg> cfgs = (List<MobileDocumentCfg>) dao.
					findByHql("from MobileDocumentCfg where moduleId = ? and docTypeNo = ? ", moduleId, docTypeNo);
			if(null==cfgs || cfgs.isEmpty()){
				throw new ServiceException("未找到审批流程文档配置信息，模块："+moduleId+"；文档类型："+docTypeNo);
			}else{
				bufferedDoc.setMobileDocumentCfg(cfgs.get(0));
			}
		}else{
			bufferedDoc = bufferedDocs.get(0);
		}
		bufferedDoc.setBusiBill(approveDoc);
		bufferedDoc.setBusiBillId(approveDoc.getDocumentId());
		return bufferedDoc;
	}

	@Override
	public String getBusiBillVersion(MobileDocumentBuffered doc) {
		return getBusiBillVersion(this.getApproveDocument(doc));
	}
	
	public String getBusiBillVersion(ApproveDocuments<?> approveDoc) {
		if (null==approveDoc || null==approveDoc.getSubmitTime())
			throw new ServiceException("审批流程对象是空，不可以获得版本号。");
		else
			return sdf.format(approveDoc.getSubmitTime());
	}

	public ApproveDocuments<?> getApproveDocument(MobileDocumentBuffered doc) {
		ApproveDocuments<?> approveDoc;
		if(doc.getBusiBill()==null)
			throw new ServiceException("未找审批单，单号：" + doc.getBusiBillId());
		try{
			approveDoc = (ApproveDocuments<?>)(doc.getBusiBill());
		}catch(Exception e){
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return approveDoc;
	}
}
