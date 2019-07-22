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
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.vehicle.loan.dao.IVehicleLoanBudgetDao;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudget;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetails;

/**
 * 消贷费用预算-缓存
 * 
 * @author caigx
 *
 */
@Service("vehicleLoanBudgetBufferCalc")
public class VehicleLoanBudgetBufferCalc extends ApprovalDocumentCalc {
	public int documentClassId = 10000;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleLoanBudgetBufferCalc.class);
	private static String bufferCalcVersion = "201711091.2";

	private static String moduleId = "101803";

	@Autowired
	private IVehicleLoanBudgetDao vehicleLoanBudgetDao;

	@Override
	public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId,
			String documentNo) {
		MobileDocumentBuffered bufferedDoc = loadDocumentBuffered(moduleId,
				documentNo);
		// 设置业务单据：车辆销售合同变更单
		ApproveDocuments<?> approveDoc = (ApproveDocuments<?>) bufferedDoc
				.getBusiBill();
		VehicleLoanBudget loanBudget = dao.get(VehicleLoanBudget.class,
				approveDoc.getDocumentNo());
		if (loanBudget == null) {
			throw new ServiceException("未找到审批单号对应的消贷费用预算单："
					+ approveDoc.getDocumentNo());
		}
		bufferedDoc.setRelatedObjects(new Object[] { loanBudget });

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

		VehicleLoanBudget loanBudget = getLoanBudget(doc);
		if (loanBudget == null) {
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

		VwVehicleLoanBudget vwLoanBudget = dao.get(VwVehicleLoanBudget.class,
				loanBudget.getDocumentNo());
		if (vwLoanBudget == null) {
			throw new ServiceException("未找到审批单号对应的消贷预算单："
					+ loanBudget.getDocumentNo());
		}

		StringBuffer buf = new StringBuffer();
		buf.append(
				"select  sum(round(vehiclePriceTotal,0)) as vehiclePriceTotal,")
				.append("\r\n");
		buf.append("sum(round(firstPayTot,0)) as firstPayTot,").append("\r\n");
		buf.append("sum(round(loanAmount,0)) as loanAmount,").append("\r\n");
		buf.append("sum(round(chargeLoanAmount,0)) as chargeLoanAmount,")
				.append("\r\n");
		buf.append("sum(round(agentAmount,0)) as agentAmount").append("\r\n");
		buf.append("from VwVehicleLoanBudgetDetails  where documentNo=?")
				.append("\r\n");

		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),
				loanBudget.getDocumentNo());
		double vehiclePriceTotal = 0, firstPayTot = 0, loanAmount = 0, chargeLoanAmount = 0, agentAmount = 0;
		if (data1 != null && data1.size() == 1) {
			Object[] row = (Object[]) data1.get(0);
			vehiclePriceTotal = row[0] != null ? Tools
					.toDouble((Double) row[0]) : 0;
			firstPayTot = row[1] != null ? Tools.toDouble((Double) row[1]) : 0;
			loanAmount = row[2] != null ? Tools.toDouble((Double) row[2]) : 0;
			chargeLoanAmount = row[3] != null ? Tools.toDouble((Double) row[3])
					: 0;
			agentAmount = row[4] != null ? Tools.toDouble((Double) row[4]) : 0;
		}

		DocTitle docTitle = new DocTitle("消贷费用预算审批");
		board.setDocTitle(gson.toJson(docTitle));

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("%s   贷款总额:%s元", loanBudget
				.getDocumentNo(), Tools.toCurrencyStr(loanAmount
				+ chargeLoanAmount + agentAmount)));
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		// 车辆信息
		List<VehicleLoanBudgetDetails> budgetDetailList = vehicleLoanBudgetDao
				.getBudgetDetailList(loanBudget.getDocumentNo());

		short sn = 1;
		// 预算单号
		// createProperty(board, "documentNo", sn++, true, null, null, "预算单号",
		// loanBudget.getDocumentNo(), null, null, null, null, null);
		// 贷款模式
		createProperty(board, "loanMode", sn++, true, null, null, "贷款模式",
				vwLoanBudget.getLoanModeMeaning(), null, null, null, null, null);
		// 代理商
		if (StringUtils.isNotEmpty(vwLoanBudget.getAgentName())) {
			createProperty(board, "agentId", sn++, true, null, null, "代理商",
					vwLoanBudget.getAgentName(), null, null, null, null, null);
		}

		// 贷款方式
		if (StringUtils.isNotEmpty(vwLoanBudget.getLoanTypeMeaning())) {
			createProperty(board, "loanType", sn++, true, null, null, "贷款方式",
					vwLoanBudget.getLoanTypeMeaning(), null, null, null, null,
					null);
		}

		// 销售合同
		if (StringUtils.isNotEmpty(loanBudget.getSaleContractNo())) {
			createProperty(board, "saleContractNo", sn++, true, null, null,
					"销售合同", loanBudget.getSaleContractNo(), null, null, null,
					null, null);
		}

		// 购车客户
		createProperty(board, "customerId", sn++, true, null, null, "购车客户",
				vwLoanBudget.getCustomerName(), null, null, null, null, null);

		// 贷款人
		createProperty(board, "loanObjectId", sn++, true, null, null, "贷款人",
				vwLoanBudget.getLoanObjectName(), null, null, null, null, null);

		// 当车辆为一台车的时候，消贷费用预算的车款合计，首付总额，车辆贷款，费用贷款，贷款总额不显示（从对象车辆明细里的依旧显示），下面明细的因为一致。当车辆大于一台时候，主对象里的照旧显示
		if (budgetDetailList != null && budgetDetailList.size() > 1) {
			// // 车款合计
			// createProperty(board, "vehiclePriceTotal", sn++, true, null,
			// null,
			// "车款合计", Tools.toCurrencyStr(vehiclePriceTotal), null, null,
			// null, null, null);

			// 车辆贷款
			createProperty(board, "loanAmount", sn++, true, null, null, "车辆贷款",
					Tools.toCurrencyStr(loanAmount), null, null, Color.RED.getCode(), null,
					null);

			// 费用贷款
			if (chargeLoanAmount != 0) {
				createProperty(board, "chargeLoanAmount", sn++, true, null,
						null, "费用贷款", Tools.toCurrencyStr(chargeLoanAmount),
						null, null, Color.RED.getCode(), null, null);
			}

			// 代理贷款
			if (agentAmount != 0) {
				createProperty(board, "agentAmount", sn++, true, null, null,
						"代理贷款", Tools.toCurrencyStr(agentAmount), null, null,
						Color.RED.getCode(), null, null);
			}

			// 贷款总额
			if (loanAmount + chargeLoanAmount + agentAmount != 0) {
				createProperty(
						board,
						"amountTotal",
						sn++,
						true,
						null,
						null,
						"贷款总额",
						Tools.toCurrencyStr(loanAmount + chargeLoanAmount
								+ agentAmount), null, null, Color.RED.getCode(), null, null);
			}

			// 首付总额
			createProperty(board, "firstPayTot", sn++, true, null, null,
					"首付总额", Tools.toCurrencyStr(firstPayTot), null, null, Color.RED.getCode(),
					null, null);
		}

		// 备注
		if (StringUtils.isNotEmpty(loanBudget.getRemark())) {
			createProperty(board, "remark", sn++, true, null, null, "备注",
					loanBudget.getRemark(), null, null, null, null, null);
		}

		
		for (int i = 0; i < budgetDetailList.size(); i++) {
			VehicleLoanBudgetDetails detail = budgetDetailList.get(i);
			MobileBoardBuffered detailBoard = computeBudgetDetailFields(board,
					detail, i, budgetDetailList.size());
			createSubObjectField(board, "VehicleLoanBudgetDetailsSubobject",
					AppBoardFieldType.Subobject, sn++, detailBoard);

		}

		dao.flush();
		dao.update(doc);
	}

	@Override
	public void computeDynamicFields(MobileDocumentBuffered doc) {

	}

	/**
	 * 计算 消贷车辆
	 * 
	 * @param board
	 * @param detail
	 * @param n
	 * @return
	 */
	private MobileBoardBuffered computeBudgetDetailFields(
			MobileBoardBuffered board, VehicleLoanBudgetDetails detail, int n,
			int vehicleCount) {

		VwVehicleLoanBudgetDetails vwDetail = dao.get(
				VwVehicleLoanBudgetDetails.class, detail.getSelfId());
		if (vwDetail == null) {
			throw new ServiceException("未找到消贷预算单明细，selfId："
					+ detail.getSelfId());
		}
		MobileBoardBuffered detailBoard = new MobileBoardBuffered();
		String salesCode = vwDetail.getVehicleSalesCode() == null ? ""
				: vwDetail.getVehicleSalesCode();
		String title = String.format("%d、%s", n + 1, salesCode);

		BoardTitle slaveBoardTitle = new BoardTitle();
		slaveBoardTitle.setTitle(title);
		slaveBoardTitle.setCollapsable(true);
		if (n == 0) {
			slaveBoardTitle.setDefaultExpanded(true);
		} else {
			slaveBoardTitle.setDefaultExpanded(false);
		}

		BoardTitle detailBoardTitle = new BoardTitle();
		detailBoardTitle.setTitle(title);
		detailBoardTitle.setCollapsable(true);
		detailBoardTitle.setDefaultExpanded(true);

		createBoardTitle(detailBoard, null, gson.toJson(detailBoardTitle),
				gson.toJson(slaveBoardTitle), null);

		DocTitle docTitle = new DocTitle("消贷费用明细");
		detailBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		if (StringUtils.isNotEmpty(vwDetail.getVehicleName())) {
			// 车辆名称
			createProperty(detailBoard, "vehicleName", sn++, false, true, true,
					"车辆名称", vwDetail.getVehicleName(), null, null, null, null,
					null);
		}

		// 车辆VIN
		createProperty(detailBoard, "vehicleVin", sn++, false, true, true,
				"车辆VIN", StringUtils.defaultString(vwDetail.getVehicleVin()),
				null, null, null, null, null);
		
		// 上牌型号
		createProperty(detailBoard, "bulletinNo", sn++, false, true, true,
				"上牌型号", StringUtils.defaultString(detail.getBulletinNo()),
				null, null, null, null, null);

		// 车辆售价
		createProperty(detailBoard, "vehiclePriceTotal", sn++, false, true,
				true, "车辆售价",
				Tools.toCurrencyStr(detail.getVehiclePriceTotal()), null, null,
				Color.RED.getCode(), null, null);
		// 车辆单价
		// double vehiclePrice = 0.00D;
		// VehicleSaleContractDetail contractDetail = dao.get(
		// VehicleSaleContractDetail.class,
		// detail.getSaleContractDetailId() + "");
		// if (contractDetail != null) {
		// vehiclePrice = Tools.toDouble(contractDetail.getVehiclePrice());
		// }
		// createProperty(detailBoard, "vehiclePrice", sn++, false, true, true,
		// "车辆单价", Tools.toCurrencyStr(vehiclePrice), null, null, null,
		// null, null);
		// 车辆首付
		createProperty(detailBoard, "vehicleFirstPay", sn++, false, true, true,
				"车辆首付", Tools.toCurrencyStr(vwDetail.getVehicleFirstPay()),
				null, null, Color.RED.getCode(), null, null);
		if (!compareEqual(vwDetail.getChargeFirstPay(), 0.00D)) {
			// 费用首付
			createProperty(detailBoard, "chargeFirstPay", sn++, false, true,
					true, "费用首付",
					Tools.toCurrencyStr(vwDetail.getChargeFirstPay()), null,
					null, Color.RED.getCode(), null, null);
			// 首付总额
			createProperty(detailBoard, "firstPayTot", sn++, false, true, true,
					"首付总额", Tools.toCurrencyStr(vwDetail.getFirstPayTot()),
					null, null, Color.RED.getCode(), null, null);
		}

		// 车辆贷款
		createProperty(detailBoard, "loanAmount", sn++, false, true, true,
				"车辆贷款", Tools.toCurrencyStr(vwDetail.getLoanAmount()), null,
				null, Color.RED.getCode(), null, null);
		// 费用贷款
		if (!compareEqual(vwDetail.getChargeLoanAmount(), 0.00D)) {
			createProperty(detailBoard, "chargeLoanAmount", sn++, false, true,
					true, "费用贷款",
					Tools.toCurrencyStr(vwDetail.getChargeLoanAmount()), null,
					null, Color.RED.getCode(), null, null);
		}

		// 代理贷款
		if (!compareEqual(vwDetail.getAgentAmount(), 0.00D)) {
			createProperty(detailBoard, "agentAmount", sn++, false, true, true,
					"代理贷款", Tools.toCurrencyStr(vwDetail.getAgentAmount()),
					null, null, Color.RED.getCode(), null, null);
		}

		// 贷款总额
		double loanAmountTot = Math.round(Tools.toDouble(vwDetail
				.getLoanAmount()))
				+ Math.round(Tools.toDouble(vwDetail.getChargeLoanAmount()))
				+ Math.round(Tools.toDouble(vwDetail.getAgentAmount()));
		if (!compareEqual(vwDetail.getLoanAmount(), loanAmountTot)) {

			createProperty(detailBoard, "loanAmountTot", sn++, false, true,
					true, "贷款总额", Tools.toCurrencyStr(loanAmountTot), null,
					null, Color.RED.getCode(), null, null);
		}

		// 挂靠单位
		if (StringUtils.isNotEmpty(vwDetail.getAffiliatedCompanyName())) {
			createProperty(detailBoard, "bulletinNo", sn++, false, true, true,
					"挂靠单位", StringUtils.defaultString(vwDetail.getAffiliatedCompanyName()),
					null, null, null, null, null);
		}

		// 备注
		if (StringUtils.isNotEmpty(vwDetail.getRemark())) {
			createProperty(detailBoard, "remark", sn++, false, true, true,
					"备注", vwDetail.getRemark(), null, null, null, null, null);
		}

		// 计算-费用信息
		List<VwVehicleLoanBudgetCharge> chargeList = vehicleLoanBudgetDao
				.getBudgetCharge(vwDetail.getSelfId());
		if (chargeList != null && chargeList.size() > 0) {
			createTail(detailBoard, sn++, false, true);
		} else {
			createTail(detailBoard, sn++, false, false);
		}

		for (int i = 0; i < chargeList.size(); i++) {
			VwVehicleLoanBudgetCharge charge = chargeList.get(i);
			MobileBoardBuffered chargeBoard = computeBudgetChargeFields(
					detailBoard, charge, i);
			createSubObjectField(detailBoard,
					"VehicleLoanBudgetChargeSubobject",
					AppBoardFieldType.Subobject, sn++, chargeBoard);

		}

		return detailBoard;
	}

	/**
	 * 计算-费用信息
	 * 
	 * @param detailBoard
	 * @param charge
	 * @return
	 */
	private MobileBoardBuffered computeBudgetChargeFields(
			MobileBoardBuffered detailBoard, VwVehicleLoanBudgetCharge charge,
			int n) {
		MobileBoardBuffered chargeBoard = new MobileBoardBuffered();
		String title = String.format("%s", charge.getChargeName());
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title);
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(chargeBoard, gson.toJson(baseBoardTitle),
				gson.toJson(baseBoardTitle), gson.toJson(baseBoardTitle), null);

		DocTitle docTitle = new DocTitle("消贷费用明细");
		chargeBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		/*// 往来方向  该字段取消
		createProperty(chargeBoard, "direction", sn++, true, true, true,
				"往来方向", charge.getDirectionMeaning(), null, null, null, null,
				null);*/
		// 款项金额
		/*createProperty(chargeBoard, "amount", sn++, true, true, true, "款项金额",
				Tools.toCurrencyStr(charge.getAmount()), null, null, Color.RED.getCode(),
				null, null);*/
		if(null != charge.getIncome()) {
			createProperty(chargeBoard, "income", sn++, true, true, true, "收入",
					Tools.toCurrencyStr(charge.getIncome()), null, null, Color.RED.getCode(),
					null, null);
		}
		if(null != charge.getExpenditure()){
			createProperty(chargeBoard, "expenditure", sn++, true, true, true, "支出",
					Tools.toCurrencyStr(charge.getExpenditure()), null, null, Color.RED.getCode(),
					null, null);
		}

		// 费用贷款
		if (!compareEqual(charge.getLoanAmount(), 0.00D)) {
			createProperty(chargeBoard, "loanAmount", sn++, true, true, true,
					"费用贷款", Tools.toCurrencyStr(charge.getLoanAmount()), null,
					null, Color.RED.getCode(), null, null);
		}

		if(null != charge.getPaymentDate()){
			createProperty(chargeBoard, "paymentDate", sn++, true, true, true,
					"应付日期", Tools.formatDate(charge.getPaymentDate()), null,
					null, null, null, null);
		}

		// 款项对象
		if (StringUtils.isNotEmpty(charge.getObjectName())) {
			createProperty(chargeBoard, "objectName", sn++, true, true, true,
					"款项对象", charge.getObjectName(), null, null, null, null,
					null);
		}

		// 备注
		if (StringUtils.isNotEmpty(charge.getRemark())) {
			createProperty(chargeBoard, "remark", sn++, true, true, true, "备注",
					charge.getRemark(), null, null, null, null, null);
		}

		return chargeBoard;
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
		else{
			//修改 BusiBillVersion 已提交时间 和 修改时间一起 
			String modifyTimestr = "";
			VehicleLoanBudget budget = dao.get(VehicleLoanBudget.class, approveDoc.getDocumentNo());
			if(budget!=null && budget.getModifyTime()!=null)
				modifyTimestr = sdf.format(budget.getModifyTime());
			
			return sdf.format(approveDoc.getSubmitTime())+modifyTimestr;
		}
			
		
	}

	public VehicleLoanBudget getLoanBudget(MobileDocumentBuffered doc) {
		VehicleLoanBudget bill;
		if (doc.getRelatedObjects() == null
				|| doc.getRelatedObjects().length < 1)
			throw new ServiceException("未找销售合同变更单，单号：" + doc.getBusiBillId());
		try {
			bill = (VehicleLoanBudget) (doc.getRelatedObjects()[0]);
		} catch (Exception e) {
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return bill;
	}

	protected Date getBusiBillModifyTime(MobileDocumentBuffered bufferedDoc) {
		return this.getLoanBudget(bufferedDoc).getModifyTime();
	}

}
