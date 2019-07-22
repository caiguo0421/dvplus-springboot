package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.List;

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
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractGifts;
import cn.sf_soft.office.approval.model.VehicleSaleContractGiftsVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsuranceVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;

/**
 * 合同-厂家赠品
 * 
 * @author caigx
 *
 */
@Service("saleContractGiftBufferCacl")
public class SaleContractGiftBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractGiftBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractGiftBufferCacl.class.getSimpleName();

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
		List<VehicleSaleContractGifts> giftList = (List<VehicleSaleContractGifts>) dao.findByHql(
				"from VehicleSaleContractGifts where contractDetailId =?", detail.getContractDetailId());
		if (giftList == null || giftList.size() == 0) {
			return null;
		}

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle("厂家赠品");
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle = new DocTitle("厂家赠品");
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < giftList.size(); i++) {
			VehicleSaleContractGifts gift = giftList.get(i);
			sn = createDetailField(sn, n, summaryBoard, gift);
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractGifts gift) {
		String title = String.format("%s", gift.getItemName());
		
		String label = String.format("%d、%s", n + 1, gift.getItemName());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);
		

		// 金额
		Property amount = getAmount(gift, gift.getAbortStatus());
		if (amount != null) {
			saveProperty(board, "amount",   sn++, amount);
		}

		Property giveFlag = getGiveFlag(gift, gift.getAbortStatus());
		if (giveFlag != null) {
			saveProperty(board, "giveFlag",   sn++, giveFlag);
		}

		return sn;
	}

	// 金额
	private Property getAmount(VehicleSaleContractGifts gift, Short abortStatus) {
		Property p = new Property();
		p.setLabel("金额");
		if (gift.getAmount() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(gift.getAmount()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 是否赠送客户
	private Property getGiveFlag(VehicleSaleContractGifts gift, Short abortStatus) {
		Property p = new Property();
		p.setLabel("是否赠送客户");
		String giveFlag = "否";
		if (gift.getGiveFlag() == 1) {
			giveFlag = "是";
		}

		p.setValue(giveFlag);
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

}
