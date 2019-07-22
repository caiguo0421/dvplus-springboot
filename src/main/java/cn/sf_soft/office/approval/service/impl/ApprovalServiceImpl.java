package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.DocumentStatus;
import cn.sf_soft.common.util.Constant.OSType;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.message.MessageSimpleEntity;
import cn.sf_soft.message.service.MessageCenterService;
import cn.sf_soft.office.approval.dao.ApproveDocumentDao;
import cn.sf_soft.office.approval.dao.ApproveDocumentPointsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.user.model.SysUsers;
import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import cn.sf_soft.file.service.FileService;

/**
 * 审批
 * 
 * @author king
 * @create 2013-9-27下午5:02:08
 */
@Service("approvalService")
public class ApprovalServiceImpl implements ApprovalService {
	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApprovalServiceImpl.class);
	@Autowired
	private ApproveDocumentDao approveDocumentDao;

	@Autowired
	protected ApproveDocumentPointsDao documentPointDao;// 审批历史

	@Autowired
	@Qualifier("baseDao")
	protected BaseDao dao;

	@Autowired
	protected SysLogsDao sysLogsDao;

	@Autowired
	private VehicleLoanBudgetBuf vehicleLoanBudgetBuf;

	@Autowired
	private FileService fileService;

	@Autowired
	private MessageCenterService messageCenterService;

	private static Map<String, String> sendMessageModuleMap = new HashMap<String, String>(){
		{
			put("401010", "业务请款");
			put("355510", "日常费用报销");
			put("355520", "车辆费用报销");
			put("355525", "车辆移调报销");
			put("355530", "维修费用报销");
			put("355540", "资产费用报销");
			put("355527", "配件运费报销");
			put("355522", "消贷费用报销");
			put("102020", "车辆销售合同");
		}
	};



	@Resource(name = "approveManager")
	private Map<String, BaseApproveProcess> approveManager;

	public void setApproveDocumentDao(ApproveDocumentDao approveDocumentDao) {
		this.approveDocumentDao = approveDocumentDao;
	}

	public void setApproveManager(Map<String, BaseApproveProcess> approveManager) {
		this.approveManager = approveManager;
	}

	/**
	 * 待审事宜
	 */
	public List<VwOfficeApproveDocuments> getPendingMatters(String userId, ApproveDocumentSearchCriteria searchCriteria) {
		List<VwOfficeApproveDocuments> approvelist;
		if(searchCriteria != null) {
			approvelist = approveDocumentDao.getPendingMatters(userId, searchCriteria.getDocumentNo(), searchCriteria.getSubmitUser(), searchCriteria.getModuleId());
		}else{
			approvelist = approveDocumentDao.getPendingMatters(userId, null, null, null);
		}
//		approvelist = filterApprovalPopedom(approvelist);// 按审批权限过滤
//		approvelist = filterApprovalOther(approvelist); // 其他特殊情况过滤 (checkData)
		approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
		return approvelist;
	}

	/**
	 * 按审批权限过滤
	 * 
	 * @param approvelist
	 * @return
	 */
	private List<VwOfficeApproveDocuments> filterApprovalPopedom(List<VwOfficeApproveDocuments> approvelist) {
		for (int i = approvelist.size() - 1; i >= 0; i--) {
			VwOfficeApproveDocuments vwOfficeApproveDocument = approvelist.get(i);
			BaseApproveProcess approveProcessor = approveManager.get(vwOfficeApproveDocument.getModuleId());
			if (null != approveProcessor) {
				if (!approveProcessor.hasApprovalPopedom()) {// 是否有审批的权限
					approvelist.remove(i);
				}
			}
		}
		return approvelist;
	}

//	/**
//	 * 其他审批过滤
//	 *
//	 * @param approvelist
//	 * @return
//	 */
//	private List<VwOfficeApproveDocuments> filterApprovalOther(List<VwOfficeApproveDocuments> approvelist) {
//		for (int i = approvelist.size() - 1; i >= 0; i--) {
//			VwOfficeApproveDocuments vwOfficeApproveDocument = approvelist.get(i);
//			if(vwOfficeApproveDocument.getModuleId().equals("355522")){
//				try {
//					if (vehicleLoanBudgetBuf.checkData(vwOfficeApproveDocument, Constant.ApproveStatus.APPROVING, false) != ApproveResultCode.APPROVE_DATA_CHECKED_PASS){
//						approvelist.remove(i);
//					}
//				}catch (ServiceException e){
//					approvelist.remove(i);
//				}
//			}
//		}
//		return approvelist;
//	}

	/**
	 * 处理待审事宜列表 1、个性化字段处理
	 * 
	 * @param approvelist
	 * @return
	 */
	private List<VwOfficeApproveDocuments> dealApproveList(List<VwOfficeApproveDocuments> approvelist) {
		for (int i = approvelist.size() - 1; i >= 0; i--) {
			VwOfficeApproveDocuments vwOfficeApproveDocument = approvelist.get(i);
			BaseApproveProcess approveProcessor = approveManager.get(vwOfficeApproveDocument.getModuleId());
			if (null != approveProcessor) {
				approvelist.set(i, approveProcessor.dealApproveDocument(vwOfficeApproveDocument));
			}
		}
		return approvelist;
	}

	/**
	 * 已审事宜
	 */
	public List<VwOfficeApproveDocuments> getApprovedMattersByProperties(String userId,
			ApproveDocumentSearchCriteria searchCriteria, int pageNo, int pageSize) {
		List<VwOfficeApproveDocuments> approvelist = null;
		if (pageNo > 0) {
			pageNo--;
		}
		if (searchCriteria != null) {
			Date beginDate = null;
			Date endDate = null;
			try {
				if (searchCriteria.getBeginTime() != null && searchCriteria.getBeginTime().length() > 0)
					beginDate = TimestampUitls.formatDate2(searchCriteria.getBeginTime());
				if (searchCriteria.getEndTime() != null && searchCriteria.getEndTime().length() > 0)
					endDate = TimestampUitls.formatDate2(searchCriteria.getEndTime());

			} catch (ParseException e) {
				throw new ServiceException("查询日期参数错误");
			}
			approvelist = approveDocumentDao.findApprovedMatters(userId, searchCriteria.getDocumentNo(),
					searchCriteria.getSubmitUser(), searchCriteria.getModuleId(), beginDate, endDate, pageNo, pageSize);
			approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
			return approvelist;
		}
		approvelist = approveDocumentDao.findApprovedMatters(userId, null, null, null, null, null, pageNo, pageSize);
		approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
		return approvelist;
	}

	/**
	 * 我的待审
	 */
	public List<VwOfficeApproveDocuments> getMyApprovingMatters(String userId, ApproveDocumentSearchCriteria searchCriteria) {
		List<VwOfficeApproveDocuments> approvelist;
		if(searchCriteria != null) {
			approvelist = approveDocumentDao.getMyApprovingMatters(userId, searchCriteria.getDocumentNo(), searchCriteria.getSubmitUser(), searchCriteria.getModuleId());
		}else{
			approvelist = approveDocumentDao.getMyApprovingMatters(userId, null, null, null);
		}
		approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
		approvelist = dealDeadApprove(approvelist);//处理死流程
		return approvelist;
	}
	
	
	
	/**
	 * 处理死流程，当待审审判点的所有用户都没有审批权限，则为死流程
	 * @param approvelist
	 * @return
	 */
	private List<VwOfficeApproveDocuments> dealDeadApprove(List<VwOfficeApproveDocuments> approvelist) {
		for (int i = approvelist.size() - 1; i >= 0; i--) {
			VwOfficeApproveDocuments vwOfficeApproveDocument = approvelist.get(i);
			BaseApproveProcess approveProcessor = approveManager.get(vwOfficeApproveDocument.getModuleId());
			if (null != approveProcessor) {
				if(approveProcessor.isDeadApprove(vwOfficeApproveDocument)){
					vwOfficeApproveDocument.setDeadForAccessCtrl(true);
				}
			}
		}
		return approvelist;
	}

	/**
	 * 我的已审
	 */
	@Override
	public List<VwOfficeApproveDocuments> getMyApprovedMatters(String userId,
			ApproveDocumentSearchCriteria searchCriteria, int pageNo, int pageSize) {
		List<VwOfficeApproveDocuments> approvelist = null;
		if (pageNo > 0) {
			pageNo--;
		}
		if (searchCriteria != null) {
			Date beginDate = null;
			Date endDate = null;
			try {
				if (searchCriteria.getBeginTime() != null && searchCriteria.getBeginTime().length() > 0)
					beginDate = TimestampUitls.formatDate2(searchCriteria.getBeginTime());
				if (searchCriteria.getEndTime() != null && searchCriteria.getEndTime().length() > 0)
					endDate = TimestampUitls.formatDate2(searchCriteria.getEndTime());

			} catch (ParseException e) {
				throw new ServiceException("查询日期参数错误");
			}
			approvelist = approveDocumentDao.getMyApprovedMatters(userId, searchCriteria.getDocumentNo(),
					searchCriteria.getSubmitUser(), searchCriteria.getModuleId(), beginDate, endDate, pageNo, pageSize);
			approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
			return approvelist;
		}
		approvelist = approveDocumentDao.getMyApprovedMatters(userId, null, null, null, null, null, pageNo, pageSize);
		approvelist = dealApproveList(approvelist);// 处理审批列表中的个性字段
		return approvelist;
	}





	/**
	 * 审批
	 */
	@Override
	public ApproveResult approveDocument(SysUsers user, boolean agree, String documentId, String comment,
			String modifyTime, String doucumentNo, OSType osType) {
		return approveDocumentWithExtraData(user,agree,documentId,comment,modifyTime,doucumentNo,null,osType);
	}


	@Override
	public ApproveResult approveDocumentWithExtraData(SysUsers user, boolean agree, String documentId, String comment,  String modifyTime,
										 String doucumentNo, Map<String,Object>extraData, OSType osType){

		if (StringUtils.isEmpty(documentId) && StringUtils.isEmpty(doucumentNo)) {
			throw new ServiceException("审批失败:单据"+(StringUtils.isEmpty(doucumentNo)?documentId:doucumentNo)+"不存在");
		}

		ApproveDocuments<?> approveDocument = null;
		if (StringUtils.isNotEmpty(doucumentNo)) {
			approveDocument = approveDocumentDao.getApproveDocumentsByNo(doucumentNo);
		} else if (StringUtils.isNotEmpty(documentId)) {
			approveDocument = approveDocumentDao.getApproveDocumentsById(documentId);
		}
		if (approveDocument == null) {
			throw new ServiceException("审批失败:单据"+(StringUtils.isEmpty(doucumentNo)?documentId:doucumentNo)+"不存在");
		}

		short docStatu = approveDocument.getStatus();
		if (docStatu != DocumentStatus.SUBMITED && docStatu != DocumentStatus.APPROVEING) {
			throw new ServiceException("审批失败:单据处于不可审批状态");
		}

		if (!approveDocument.getApproverId().contains(user.getUserId())) {
			throw new ServiceException("审批失败:您没有权限审批该单据");
		}

		BaseApproveProcess approveProcessor = approveManager.get(approveDocument.getModuleId());
		if (approveProcessor == null) {
			throw new ServiceException("审批失败:系统暂不支持该模块的审批");
		}

		List<ApproveDocumentsPoints> approvingPoints = documentPointDao.getApproveDocumentsPoints(approveDocument.getDocumentId());// 得到“审批中”的审批点
		ApproveResult approveResult = approveProcessor.approve(agree, approveDocument, comment, modifyTime, osType);

		if(extraData!=null && approvingPoints!=null && approvingPoints.size() > 0){
			//审批中的审判点的第一个就是要插入附件的审判点
			updateApprovePointAttachments(approvingPoints.get(0),extraData);
		}

		//增加日志
		sysLogsDao.addSysLog("审批","审批",String.format("模块ID：%s，单号：%s",approveDocument.getModuleId(), approveDocument.getDocumentNo()));
		this.dao.flush();
		this.sendMessage(approveDocument, approveResult);
		return approveResult;
	}

	protected void sendMessage(ApproveDocuments approveDocument, ApproveResult approveResult){
		logger.debug("给单据的提交人推送消息。documentNo:{}", approveDocument.getDocumentNo());
		short statusNo = approveResult.getStatusNo();
		logger.debug("给单据的提交人推送消息。documentNo:{}, statusNo:{}", approveDocument.getDocumentNo(), statusNo);
		if(statusNo == DocumentStatus.AGREED || statusNo == DocumentStatus.DISAGREE){
			String moduleId = approveDocument.getModuleId();
			if(!sendMessageModuleMap.keySet().contains(moduleId)) {
				logger.debug("给单据的提交人推送消息，但moduleId不在可推送范围内。moduleId：{}，documentNo:{}", moduleId, approveDocument.getDocumentNo());
				return;
			}
			try {
				MessageSimpleEntity message = new MessageSimpleEntity();
				message.setTitle("通知");
				if(statusNo == DocumentStatus.DISAGREE){
					message.setContent(String.format("您提交的%s审批未通过", sendMessageModuleMap.get(moduleId)));
				}else{
					message.setContent(String.format("您提交的%s已%s", sendMessageModuleMap.get(moduleId), "审批通过"));
				}

				message.setUserNos(new String[]{approveDocument.getUserNo()});
				messageCenterService.send(message, 1);
			}catch (Throwable e){
				logger.error("发送审批同意通知出错", e);
			}
		}
	}

	/**
	 * 更新审批点的图片和附件
	 */
	private void updateApprovePointAttachments(ApproveDocumentsPoints approvingPoint, Map<String,Object> extraData){
		if(extraData == null ||extraData.keySet().size()== 0){
			return;
		}

		if(approvingPoint==null){
			throw new ServiceException("审批点为空");
		}

		//approvingPoint 可能已经修改，重新加载一次
		approvingPoint = dao.get(ApproveDocumentsPoints.class,approvingPoint.getOadpId());
		SysUsers user = (SysUsers)ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);

		if(extraData.containsKey("pics") && extraData.get("pics")!=null) {
			String pics = extraData.get("pics").toString();
			pics = fileService.addPicsToFtp(user,approvingPoint.getOadpId(), pics);
			approvingPoint.setPics(pics);
		}

		if(extraData.containsKey("fileUrls") && extraData.get("fileUrls")!=null) {
			String fileUrls = extraData.get("fileUrls").toString();
			fileUrls = fileService.addAttachmentsToFtp(user,approvingPoint.getOadpId(), fileUrls);
			approvingPoint.setFileUrls(fileUrls);
		}

		dao.update(approvingPoint);
	}


	/**
	 * 提交
	 */
	@Override
	public ApproveResult submitRecord(SysUsers user, String documentNo, String moduleId, boolean agree, String comment,
			String modifyTime, OSType osType) {
		if (StringUtils.isEmpty(documentNo)) {
			throw new ServiceException("提交失败：未找到单据编号");
		}
		BaseApproveProcess approveProcessor = approveManager.get(moduleId);
		if (approveProcessor == null) {
			throw new ServiceException("提交失败：系统暂不支持该模块");
		}

		ApproveResult approveResult = approveProcessor.submitRecord(agree, documentNo, moduleId, comment, modifyTime,
				osType);
		//增加日志
		sysLogsDao.addSysLog("提交","提交",String.format("模块ID：%s，单号：%s",moduleId, documentNo));
		ApproveDocuments approveDocument = approveDocumentDao.getApproveDocumentsByNo(documentNo);
		this.dao.flush();
		this.sendMessage(approveDocument, approveResult);
		return approveResult;
	}

	/**
	 * 撤销操作
	 * @param documentNo
	 * @param moduleId
	 * @param modifyTime
	 */
	@Override
	public Object revokeRecord(String documentNo, String moduleId, String modifyTime){
		BaseApproveProcess approveProcessor = approveManager.get(moduleId);
		if (approveProcessor == null) {
			throw new ServiceException("撤销失败：系统暂不支持该模块");
		}
		approveProcessor.revokingRecord(documentNo, modifyTime);
		//制单中也可以撤销，可能还没有ApproveDocuments
		ApproveDocuments approveDocument = approveDocumentDao.getApproveDocumentsByNo(documentNo);
		if(approveDocument!=null){
			//在各自的审批中校验状态
//			if(approveDocument.getStatus()>=40){
//				throw new ServiceException("撤销失败:单据处于不可撤销状态");
//			}
			doRevokeRecord(approveDocument);
		}

		//增加日志
		sysLogsDao.addSysLog("撤销","撤销",String.format("模块ID：%s，单号：%s",moduleId, documentNo));
		return approveProcessor.getReturnDataWithRevoke(documentNo);
	}


	private void doRevokeRecord(ApproveDocuments approveDocument){
		Short bytStatus = approveDocument.getStatus();
		SysUsers user = HttpSessionStore.getSessionUser();

		List<ApproveDocumentsPoints> list = (List<ApproveDocumentsPoints>)dao.findByHql(
				"from ApproveDocumentsPoints a where a.documentId=? AND a.status=30 ORDER BY a.approveLevel",
				approveDocument.getDocumentId());

		if(list != null && list.size()>0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				ApproveDocumentsPoints point = list.get(i);
				//删除待审批的审批点
				dao.delete(point);
			}
		}

		//新增撤销的审批点
		ApproveDocumentsPoints revokingPoint = new ApproveDocumentsPoints();
		revokingPoint.setOadpId(UUID.randomUUID().toString());
		revokingPoint.setDocumentId(approveDocument.getDocumentId());
		revokingPoint.setStatus((short)70);
		revokingPoint.setApproveLevel(-1);
		revokingPoint.setApproveName("审批完成前撤销");
		revokingPoint.setApproverId(user.getUserId());
		revokingPoint.setApproverNo(user.getUserNo());
		revokingPoint.setApproverName(user.getUserName());
		revokingPoint.setApproveTime(new Timestamp(System.currentTimeMillis()));
		revokingPoint.setRemark("(手机)");

		dao.save(revokingPoint);

		// 如果单据在'不同意'或'制单中'状态时撤销，则置为'已撤销'状态；
		// 否则在'已提交'或'审批中'状态时撤销，则打回'制单中'状态--从c#源代码中摘抄，caigx
		approveDocument.setStatus(bytStatus < 20 ? (short)70 : (short)10);
		approveDocument.setRevokeTime(new Timestamp(System.currentTimeMillis()));
		dao.update(approveDocument);
	}


	/**
	 * 作废
	 * @param documentNo
	 * @param moduleId
	 * @param modifyTime
	 */
	@Override
	public Object forbidRecord(String documentNo, String moduleId, String modifyTime){
		BaseApproveProcess approveProcessor = approveManager.get(moduleId);
		if (approveProcessor == null) {
			throw new ServiceException("撤销失败：系统暂不支持该模块");
		}
		approveProcessor.forbiddingRecord(documentNo, modifyTime);
		ApproveDocuments approveDocument = approveDocumentDao.getApproveDocumentsByNo(documentNo);
		if(approveDocument==null){
			throw new ServiceException("作废失败,找不到审批单据");
		}
		if(approveDocument.getStatus()!=50){
			throw new ServiceException("撤销失败:单据处于不可作废状态");
		}

		doForbidRecord(approveDocument);
		//增加日志
		sysLogsDao.addSysLog("作废","作废",String.format("模块ID：%s，单号：%s",moduleId, documentNo));
		return approveProcessor.getReturnDataWithForbid(documentNo);
	}


	private void doForbidRecord(ApproveDocuments approveDocument){
		SysUsers user = HttpSessionStore.getSessionUser();
		List<ApproveDocumentsPoints> list = (List<ApproveDocumentsPoints>)dao.findByHql("from ApproveDocumentsPoints a where a.documentId=? AND a.status=30 ORDER BY a.approveLevel",
				approveDocument.getDocumentId());

		//新增撤销的审批点
		ApproveDocumentsPoints fobidPoint = new ApproveDocumentsPoints();
		fobidPoint.setOadpId(UUID.randomUUID().toString());
		fobidPoint.setDocumentId(approveDocument.getDocumentId());
		fobidPoint.setStatus((short)80);
		fobidPoint.setApproveLevel(-1);
		fobidPoint.setApproveName("审批完成后作废");
		fobidPoint.setApproverId(user.getUserId());
		fobidPoint.setApproverNo(user.getUserNo());
		fobidPoint.setApproverName(user.getUserName());
		fobidPoint.setApproveTime(new Timestamp(System.currentTimeMillis()));
		fobidPoint.setRemark("(手机)");

		dao.save(fobidPoint);

		approveDocument.setStatus((short)80);
		approveDocument.setInvalidTime(new Timestamp(System.currentTimeMillis()));
	}



	/**
	 * 获得审批单据明细
	 */
	public ApproveDocuments<?> getDocumentDetail(String documentNo, String moduleId) {
		Set<String> keys = approveManager.keySet();
		BaseApproveProcess approveProcessor = approveManager.get(moduleId);
		if (approveProcessor == null) {
			throw new ServiceException("暂不支持该模块的审批");
		}
		ApproveDocuments<?> detail = approveProcessor.getDocumentDetail(documentNo);
		return detail;
	}

	/**
	 * 获取审批历史
	 */
	public List<ApproveDocumentsPoints> getDocumentHistory(String documentId, String moduleId) {
		BaseApproveProcess approveProcessor = approveManager.get(moduleId);
		if (approveProcessor == null) {
			throw new ServiceException("暂不支持该模块的审批");
		}
		 List<ApproveDocumentsPoints> list =  approveProcessor.getDocumentHistory(documentId);
		
		 
		 return list;
	}

	public List<Map<String, Object>> getApproveModuleList(){
		return approveDocumentDao.getApproveModuleList();
	}

}
