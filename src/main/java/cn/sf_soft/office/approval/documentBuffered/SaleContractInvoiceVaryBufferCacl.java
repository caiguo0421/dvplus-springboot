package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleInvoicesVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractGifts;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同变更-发票
 * 
 * @author caigx
 *
 */
@Service("saleContractInvoiceVaryBufferCacl")
public class SaleContractInvoiceVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractInvoiceVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractInvoiceVaryBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	// 合同变更车辆状态
	private short vehicleAbortStatus;

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

	@Autowired
	private SaleContractInvoiceBufferCacl saleContractInvoiceBufferCacl;

	@Override
	public String getBufferCalcVersion() {
		return bufferVersion;
	}

	@Override
	public void computeStaticFields(MobileDocumentBuffered doc) {

	}

	@Override
	public void computeDynamicFields(MobileDocumentBuffered doc) {

	}

	private List<VehicleInvoices> getNoChangeList(VehicleSaleContractDetailVary contractDetailVary) {
		String hql = "from VehicleInvoices as a where not exists (select 1 from VehicleInvoicesVary as b  where a.invoicesDetailId = b.invoicesDetailId and b.contractDetailId = ?  and  b.detailVaryId = ?) and a.contractDetailId = ?";
		List<VehicleInvoices> list = (List<VehicleInvoices>) dao.findByHql(hql,
				contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId(), contractDetailVary.getContractDetailId());
		return list;
	}

	/**
	 * 计算字段
	 * 
	 * @param contractDetailVary
	 * @return
	 */
	public MobileBoardBuffered computeFields(VehicleSaleContractDetailVary contractDetailVary, String vehicleTitle) {
		List<VehicleInvoicesVary> invoiceVaryList = saleContractsVaryDao
				.getInvoiceVaryByContractDetailId(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
		List<VehicleInvoices> noChangeList = getNoChangeList(contractDetailVary);
		if ((invoiceVaryList == null || invoiceVaryList.size() == 0)
				&& (noChangeList == null || noChangeList.size() == 0)) {
			return null;
		}

		// 车辆状态
		this.setVehicleAbortStatus(contractDetailVary.getAbortStatus());

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(getTitle(contractDetailVary));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle(getTitle(contractDetailVary));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < invoiceVaryList.size(); i++) {
			VehicleInvoicesVary invoice = invoiceVaryList.get(i);
			sn = createDetailField(sn, n, summaryBoard, invoice);
			n++;
		}
		for (int i = 0; i < noChangeList.size(); i++) {
			VehicleInvoices invoice = noChangeList.get(i);
			sn = saleContractInvoiceBufferCacl.createDetailField(sn, n, summaryBoard, invoice);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
	}

	private String getTitle(VehicleSaleContractDetailVary contractDetailVary) {
		return "发票明细";
	}
	
	/**
	 * 生成明细的Field
	 * 
	 * @param sn
	 *            保存的序号
	 * @param n
	 *            显示的序号
	 * @param board
	 * @param insurance
	 * @return 返回的最后序号
	 */
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleInvoicesVary invoice) {
		String abortStatusStr = "";
		if (STATUS_NEW == invoice.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == invoice.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == invoice.getAbortStatus()) {
			abortStatusStr = "终止";
		}
		String title = String.format("%s %s", abortStatusStr, invoice.getInvoiceType());
		if (vehicleAbortStatus == STATUS_NEW) {
			title = String.format("%s", invoice.getInvoiceType());// 新增不用显示状态
		}

		
		String label = String.format("%d、%s", n + 1, title);
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);
		

		// 发票金额
		Property invoiceAmount = getInvoiceAmount(invoice, invoice.getAbortStatus());
		if (invoiceAmount != null) {
			saveProperty(board, "invoiceAmount",   sn++, invoiceAmount);
		}

		// 发票对象
		Property objectName = getObjectName(invoice, invoice.getAbortStatus());
		if (objectName != null) {
			saveProperty(board, "objectName",   sn++, objectName);
		}
		// 已开金额
		Property invoicePaid = getInvoicePaid(invoice, invoice.getAbortStatus());
		if (invoicePaid != null) {
			saveProperty(board, "invoicePaid",   sn++, invoicePaid);
		}

		Property invoiceTime = getInvoiceTime(invoice, invoice.getAbortStatus());
		if (invoiceTime != null) {
			saveProperty(board, "invoiceTime",   sn++, invoiceTime);
		}

		Property invoiceNo = getInvoiceNo(invoice, invoice.getAbortStatus());
		if (invoiceNo != null) {
			saveProperty(board, "invoiceNo",   sn++, invoiceNo);
		}

		// 备注
		Property remark = getRemark(invoice, invoice.getAbortStatus());
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}

		// 终止原因
		Property abortComment = getAbortComment(invoice, invoice.getAbortStatus());
		if (abortComment != null) {
			saveProperty(board, "abortComment",   sn++, abortComment);
		}

		return sn;
	}

	// 发票金额
	private Property getInvoiceAmount(VehicleInvoicesVary invoice, Short status) {
		Property p = new Property();
		p.setLabel("发票金额");
		if (status == STATUS_NEW) {
			if (invoice.getInvoiceAmount() == null) {
				return null;
			}

			p.setValue(Tools.toCurrencyStr(invoice.getInvoiceAmount()));
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (invoice.getInvoiceAmount() == null) {
				return null;
			}
			if (compareEqual(invoice.getInvoiceAmount(), invoice.getOriInvoiceAmount())) {
				p.setValue(Tools.toCurrencyStr(invoice.getInvoiceAmount()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(invoice.getInvoiceAmount(), invoice.getOriInvoiceAmount()));
				// p.setValue(String.format("%s【%s】",
				// Tools.toCurrencyStr(invoice.getInvoiceAmount()),
				// Tools.toCurrencyStr(invoice.getOriInvoiceAmount())));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;

	}

	// 发票对象
	private Property getObjectName(VehicleInvoicesVary invoice, Short status) {
		Property p = new Property();
		p.setLabel("发票对象");
		if (status == STATUS_NEW) {
			if (StringUtils.isEmpty(invoice.getObjectName())) {
				return null;
			}

			p.setValue(invoice.getObjectName());
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(invoice.getObjectName())) {
				return null;
			}
			if (StringUtils.equals(invoice.getObjectName(), invoice.getOriObjectName())) {
				p.setValue(invoice.getObjectName());
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(String.format("%s【%s】", invoice.getObjectName(), invoice.getOriObjectName()));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

	// 已开金额
	private Property getInvoicePaid(VehicleInvoicesVary invoice, Short status) {
		Property p = new Property();
		p.setLabel("已开金额");
		List<Map<String, Object>> resultList = saleContractsVaryDao.getInvoicePaid(invoice.getInvoicesDetailId());
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		Double invoicePaid = (Double) resultList.get(0).get("invoice_paid");
		if (Tools.toDouble(invoicePaid) == 0.00D) {
			return null;
		}
		p.setValue(Tools.toCurrencyStr(invoicePaid));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 开票时间
	private Property getInvoiceTime(VehicleInvoicesVary invoice, Short status) {
		Property p = new Property();
		p.setLabel("开票时间");
		List<Map<String, Object>> resultList = saleContractsVaryDao.getInvoicePaid(invoice.getInvoicesDetailId());
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		Timestamp invoiceTime = (Timestamp) resultList.get(0).get("invoice_time");
		if (invoiceTime == null) {
			return null;
		}
		p.setValue(Tools.formatDate(invoiceTime));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 发票号
	private Property getInvoiceNo(VehicleInvoicesVary invoice, Short status) {
		Property p = new Property();
		p.setLabel("发票号");
		List<Map<String, Object>> resultList = saleContractsVaryDao.getInvoicePaid(invoice.getInvoicesDetailId());
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		if (resultList.get(0).get("after_no") == null
				|| StringUtils.isEmpty(resultList.get(0).get("after_no").toString())) {
			return null;
		}
		String invoiceNo = resultList.get(0).get("after_no").toString();
		p.setValue(invoiceNo);
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 备注
	private Property getRemark(VehicleInvoicesVary invoice, short status) {
		Property p = new Property();
		p.setLabel("备注");

		if (StringUtils.isEmpty(invoice.getRemark())) {
			return null;
		}

		p.setValue(invoice.getRemark());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 终止原因
	private Property getAbortComment(VehicleInvoicesVary invoice, short status) {
		Property p = new Property();
		p.setLabel("终止原因");

		if (StringUtils.isEmpty(invoice.getAbortComment())) {
			return null;
		}

		p.setValue(invoice.getAbortComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	public short getVehicleAbortStatus() {
		return vehicleAbortStatus;
	}

	public void setVehicleAbortStatus(Short vehicleAbortStatus) {
		if (vehicleAbortStatus == null) {
			vehicleAbortStatus = STATUS_MODIFY; // 如果为空按修改处理
		}
		this.vehicleAbortStatus = vehicleAbortStatus;
	}

}
