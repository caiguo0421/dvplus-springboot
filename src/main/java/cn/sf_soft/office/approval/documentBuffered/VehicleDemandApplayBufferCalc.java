package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import cn.sf_soft.office.approval.ui.model.Color;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.vehicle.demand.dao.IVehicleDemandApplyDao;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApply;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApplyDetail;

/**
 * 资源需求申报-缓存
 * 
 * @author caigx
 *
 */
@Service("vehicleDemandApplayBufferCalc")
public class VehicleDemandApplayBufferCalc extends ApprovalDocumentCalc {
	public int documentClassId = 10000;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleLoanBudgetBufferCalc.class);
	private static String bufferCalcVersion = "20171108.1";

	private static String moduleId = "101002";

	@Autowired
	private IVehicleDemandApplyDao vehicleDemandApplyDao;

	@Override
	public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId,
			String documentNo) {
		MobileDocumentBuffered bufferedDoc = loadDocumentBuffered(moduleId,
				documentNo);
		// 设置业务单据
		ApproveDocuments<?> approveDoc = (ApproveDocuments<?>) bufferedDoc
				.getBusiBill();
		VehicleDemandApply apply = vehicleDemandApplyDao
				.getDemandApplayByDocumentNo(approveDoc.getDocumentNo());
		if (apply == null) {
			throw new ServiceException("未找到审批单号对应的资源需求申报单："
					+ approveDoc.getDocumentNo());
		}
		bufferedDoc.setRelatedObjects(new Object[] { apply });

		if (this.isNeedToCompute(bufferedDoc)) {
			computeStaticFields(bufferedDoc);
		}
		if (!onlyStatic) {
			computeDynamicFields(bufferedDoc);
		}

		return bufferedDoc;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void computeStaticFields(MobileDocumentBuffered doc) {
		if (null == doc) {
			throw new ServiceException("无法计算空文档。");
		}
		ApproveDocuments<?> approveDoc = (ApproveDocuments<?>) doc
				.getBusiBill();
		if (approveDoc == null) {
			throw new ServiceException("没有审批单，无法计算。");
		}
		if (!moduleId.equals(approveDoc.getModuleId())) {
			throw new ServiceException(approveDoc.getDocumentNo()
					+ "此审批非合同变更审批单");
		}

		VehicleDemandApply apply = getDemandApply(doc);
		if (apply == null) {
			throw new ServiceException("未找到审批单号对应的消贷预算单："
					+ approveDoc.getDocumentNo());
		}

		// 如果buffer_version 和 buffered_doc_version 一致,直接返回
		if (!this.isNeedToCompute(doc)) {
			return;
		}
		if (null != doc.getBoard() && null != doc.getBoard().getFields()
				&& doc.getBoard().getFields().size() > 0) {
			List<MobileBoardField> fields = doc.getBoard().getFields();
			for (int i = fields.size() - 1; i >= 0; i--) {
				dao.delete(fields.get(i));
				fields.remove(i);
			}
			dao.flush();
		}

		if (null != doc.getBoard() && null != doc.getBoard().getTitles()
				&& doc.getBoard().getTitles().size() > 0) {
			List<MobileBoardTitle> titles = doc.getBoard().getTitles();
			for (int i = titles.size() - 1; i >= 0; i--) {
				dao.delete(titles.get(i));
				titles.remove(i);
			}
			dao.flush();
		}

		doc.setBusiBillId(approveDoc.getDocumentId());
		doc.setBufferCalcVersion(bufferCalcVersion);
		doc.setBusiBillVersion(this.getBusiBillVersion(approveDoc));
		doc.setComputeTime(new Timestamp(System.currentTimeMillis()));// 计算时间

		MobileBoardBuffered board = doc.getBoard();
		if (null == board) {
			board = org.springframework.beans.BeanUtils
					.instantiate(MobileBoardBuffered.class);
			doc.setBoard(board);
			board.setDocument(doc);
		}
		DocTitle docTitle = new DocTitle("资源需求申报审批");
		board.setDocTitle(gson.toJson(docTitle));

		// 计算总金额
		StringBuffer buf = new StringBuffer();
		buf.append(
				"select sum(round(purchasePrice,0)*isnull(quantity,0)) as totalAmount")
				.append("\r\n");
		buf.append(
				" from  VehicleDemandApplyDetail  where demandId in (select demandId from VehicleDemandApply  where documentNo=?)")
				.append("\r\n");
		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),
				apply.getDocumentNo());
		double totalAmount = 0;
		if (data1 != null && data1.size() == 1) {
			// Object[] row = (Object[]) data1.get(0);
			totalAmount = data1.get(0) != null ? Tools.toDouble((Double) data1
					.get(0)) : 0;
		}

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("%s   %s元",
				apply.getDocumentNo(), Tools.toCurrencyStr(totalAmount)));
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		short sn = 1;
		// 需求单号
		// createProperty(board, "documentNo", sn++, true, null, null, "需求单号",
		// apply.getDocumentNo(), null, null, null, null, null);
		// 上报人
		createProperty(board, "reportPerson", sn++, true, null, null, "上报人",
				StringUtils.defaultString(apply.getReportPerson()), null, null,
				null, null, null);

		// 备注
		if (StringUtils.isNotEmpty(apply.getRemark())) {
			createProperty(board, "remark", sn++, true, null, null, "备注",
					apply.getRemark(), null, null, null, null, null);
		}

		// 车辆信息
		List<VehicleDemandApplyDetail> detailList = vehicleDemandApplyDao
				.getDetailByDemandId(apply.getDemandId());
		for (int i = 0; i < detailList.size(); i++) {
			VehicleDemandApplyDetail detail = detailList.get(i);
			MobileBoardBuffered detailBoard = computeApplyDetailFields(board,
					detail, i, detailList.size());
			createSubObjectField(board, "VehicleDemandApplyDetailSubobject",
					AppBoardFieldType.Subobject, sn++, detailBoard);

		}

		dao.flush();
		dao.update(doc);

	}

	@Override
	public void computeDynamicFields(MobileDocumentBuffered doc) {

	}

	/**
	 * 计算-车辆信息
	 * 
	 * @param board
	 * @param apply
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MobileBoardBuffered computeApplyDetailFields(
			MobileBoardBuffered board, VehicleDemandApplyDetail detail, int n,
			int detailCount) {
		// 车型数小于等于三种就全展开，大于三种就只展开第一种车型的
		boolean defaultExpanded = false;
		if(detailCount<=3){
			defaultExpanded = true;
		}else{
			if(n==0)
				defaultExpanded = true;	
		}

		MobileBoardBuffered detailBoard = new MobileBoardBuffered();
		String title = String.format("%d、%s   %d辆", n + 1,
				detail.getVehicleVno(), detail.getQuantity());

		BoardTitle salveBoardTitle = new BoardTitle();
		salveBoardTitle.setTitle(title);
		salveBoardTitle.setCollapsable(true);
		salveBoardTitle.setDefaultExpanded(defaultExpanded);
		createBoardTitle(detailBoard, null, null, gson.toJson(salveBoardTitle),null);

		DocTitle docTitle = new DocTitle("资源需求单明细");
		detailBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;

		// 车辆型号
		if(StringUtils.isNotEmpty(detail.getVehicleVno())){
			createProperty(detailBoard, "vehicleVno", sn++, false, true, true,
					"车辆型号", detail.getVehicleVno(), null, null, null, null, null);
		}

		// 标配颜色
		if (StringUtils.isNotEmpty(detail.getColor())) {
			createProperty(detailBoard, "color", sn++, false, true, true,
					"标配颜色", detail.getColor(), null, null, null, null, null);
		}

		// 非标配颜色
		if (StringUtils.isNotEmpty(detail.getColorName())) {
			createProperty(detailBoard, "colorName", sn++, false, true, true,
					"非标配颜色", detail.getColorName(), null, null, null, null,
					null);
		}

		// 数量
		createProperty(detailBoard, "quantity", sn++, false, true, true, "数量",
				detail.getQuantity() + "", null, null, null, null, null);
		// 采购价格
		createProperty(detailBoard, "purchasePrice", sn++, false, true, true,
				"采购价格", Tools.toCurrencyStr(detail.getPurchasePrice()), null,
				null, Color.RED.getCode(), null, null);
		// 付款方式
		createProperty(detailBoard, "payMethod", sn++, false, true, true,
				"付款方式", detail.getPayMethod(), null, null, null, null, null);
		// 期望交期
		createProperty(detailBoard, "expectDate", sn++, false, true, true,
				"期望交期", Tools.formatDate(detail.getExpectDate()), null, null,
				null, null, null);
		// 运输方式
		createProperty(detailBoard, "transportWay", sn++, false, true, true,
				"运输方式", detail.getTransportWay(), null, null,
				null, null, null);
		//送达地
		if(StringUtils.isNotEmpty(detail.getTransportTo())){
			List<String> list = (List<String>) dao.findByHql("select areaName from BaseDestinationDirectory  where areaCode = ?", detail.getTransportTo());
			String transportToName = "";
			if(list!=null && list.size()>0){
				transportToName = list.get(0);
			}
			
			if(StringUtils.isNotEmpty(transportToName)){
				createProperty(detailBoard, "transportTo", sn++, false, true, true,
						"送达地", transportToName, null, null,
						null, null, null);
			}
			
		}
		
		//分库名称
		if(StringUtils.isNotEmpty(detail.getSubStorage())){
			createProperty(detailBoard, "subStorage", sn++, false, true, true,
					"分库名称", detail.getSubStorage(), null, null,
					null, null, null);
		}
		//转供改装企业
		if(StringUtils.isNotEmpty(detail.getExchangeEnterp())){
			createProperty(detailBoard, "exchangeEnterp", sn++, false, true, true,
					"转供改装企业", detail.getExchangeEnterp(), null, null,
					null, null, null);
		}

		// 备注
		if (StringUtils.isNotEmpty(detail.getRemark())) {
			createProperty(detailBoard, "remark", sn++, false, true, true,
					"备注", detail.getRemark(), null, null, null, null, null);
		}
		// creatTail
		createTail(detailBoard, sn++, false, false);

		return detailBoard;

	}

	@Override
	public String getBufferCalcVersion() {
		return bufferCalcVersion;
	}

	@Override
	public String getBusiBillVersion(MobileDocumentBuffered doc) {
		return getBusiBillVersion(this.getApproveDocument(doc));
	}

	public String getBusiBillVersion(ApproveDocuments<?> approveDoc) {
		if (null == approveDoc || null == approveDoc.getSubmitTime())
			throw new ServiceException("审批流程对象是空，不可以获得版本号。");
		else
			return sdf.format(approveDoc.getSubmitTime());
	}

	public VehicleDemandApply getDemandApply(MobileDocumentBuffered doc) {
		VehicleDemandApply bill;
		if (doc.getRelatedObjects() == null
				|| doc.getRelatedObjects().length < 1)
			throw new ServiceException("未找资源需求申报单，单号：" + doc.getBusiBillId());
		try {
			bill = (VehicleDemandApply) (doc.getRelatedObjects()[0]);
		} catch (Exception e) {
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return bill;
	}

	protected Date getBusiBillModifyTime(MobileDocumentBuffered bufferedDoc) {
		return this.getDemandApply(bufferedDoc).getModifyTime();
	}
}
