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
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同-发票
 * 
 * @author caigx
 *
 */
@Service("saleContractInvoiceBufferCacl")
public class SaleContractInvoiceBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractInvoiceBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractInvoiceBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

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

	/**
	 * 计算字段
	 * 
	 * @param contractDetailVary
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MobileBoardBuffered computeFields(VehicleSaleContractDetail detail, String vehicleTitle) {
		List<VehicleInvoices> invoiceList = (List<VehicleInvoices>) dao.findByHql(
				"from VehicleInvoices where contractDetailId = ?", detail.getContractDetailId());
		if (invoiceList == null || invoiceList.size() == 0) {
			return null;
		}
		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle("发票明细");
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle("发票明细");
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < invoiceList.size(); i++) {
			VehicleInvoices invoice = invoiceList.get(i);
			sn = createDetailField(sn, n, summaryBoard, invoice);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleInvoices invoice) {
		String title = String.format(" %s", invoice.getInvoiceType());
		
		String label = String.format("%d、 %s", n + 1, invoice.getInvoiceType());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);

		// 发票金额
		Property invoiceAmount = getInvoiceAmount(invoice);
		if (invoiceAmount != null) {
			saveProperty(board, "invoiceAmount",   sn++, invoiceAmount);
		}

		// 发票对象
		Property objectName = getObjectName(invoice);
		if (objectName != null) {
			saveProperty(board, "objectName",   sn++, objectName);
		}
		// 已开金额
		Property invoicePaid = getInvoicePaid(invoice);
		if (invoicePaid != null) {
			saveProperty(board, "invoicePaid",   sn++, invoicePaid);
		}

		Property invoiceTime = getInvoiceTime(invoice);
		if (invoiceTime != null) {
			saveProperty(board, "invoiceTime",   sn++, invoiceTime);
		}

		Property invoiceNo = getInvoiceNo(invoice);
		if (invoiceNo != null) {
			saveProperty(board, "invoiceNo",   sn++, invoiceNo);
		}

		// 备注
		Property remark = getRemark(invoice);
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}

		return sn;
	}

	/**
	 * 计算明细
	 * 
	 * @param charge
	 * @return
	 */
//	private MobileBoardBuffered getInvoiceDetail(VehicleInvoices invoice, String vehicleTitle, String title) {
//		MobileBoardBuffered lableBoard = new MobileBoardBuffered();
//
//		BoardTitle baseBoardTitle = new BoardTitle();
//		baseBoardTitle.setTitle(String.format("%s %s", vehicleTitle, title));
//		baseBoardTitle.setCollapsable(true);
//		baseBoardTitle.setDefaultExpanded(true);
//		createBoardTitle(lableBoard,gson.toJson(baseBoardTitle),null,null,null);
//
//		DocTitle docTitle = new DocTitle(String.format("%s", invoice.getInvoiceType()));
//		lableBoard.setDocTitle(gson.toJson(docTitle));
//
//		short sn = 1;
//		// 发票金额
//		Property invoiceAmount = getInvoiceAmount(invoice);
//		if (invoiceAmount != null) {
//			saveProperty(lableBoard, "invoiceAmount",   sn++, invoiceAmount);
//		}
//
//		// 发票对象
//		Property objectName = getObjectName(invoice);
//		if (objectName != null) {
//			saveProperty(lableBoard, "objectName",   sn++, objectName);
//		}
//		// 已开金额
//		Property invoicePaid = getInvoicePaid(invoice);
//		if (invoicePaid != null) {
//			saveProperty(lableBoard, "invoicePaid",   sn++, invoicePaid);
//		}
//
//		Property invoiceTime = getInvoiceTime(invoice);
//		if (invoiceTime != null) {
//			saveProperty(lableBoard, "invoiceTime",   sn++, invoiceTime);
//		}
//
//		Property invoiceNo = getInvoiceNo(invoice);
//		if (invoiceNo != null) {
//			saveProperty(lableBoard, "invoiceNo",   sn++, invoiceNo);
//		}
//
//		// 备注
//		Property remark = getRemark(invoice);
//		if (remark != null) {
//			saveProperty(lableBoard, "remark",   sn++, remark);
//		}
//
//		return lableBoard;
//	}

	// 发票金额
	private Property getInvoiceAmount(VehicleInvoices invoice) {
		Property p = new Property();
		p.setLabel("发票金额");
		if (invoice.getInvoiceAmount() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(invoice.getInvoiceAmount()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 发票对象
	private Property getObjectName(VehicleInvoices invoice) {
		Property p = new Property();
		p.setLabel("发票对象");
		if (StringUtils.isEmpty(invoice.getObjectName())) {
			return null;
		}

		p.setValue(invoice.getObjectName());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 已开金额
	private Property getInvoicePaid(VehicleInvoices invoice) {
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
	private Property getInvoiceTime(VehicleInvoices invoice) {
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
	private Property getInvoiceNo(VehicleInvoices invoice) {
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
	private Property getRemark(VehicleInvoices invoice) {
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

}
