package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.dao.BaseOthersDao;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.finance.funds.model.FinanceSettlementsDetails;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dao.BaseRelatedObjectsDao;
import cn.sf_soft.office.approval.dao.SaleContractsDao;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.ui.vo.VehicleSaleContractsVaryView;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;

/**
 * 销售合同变更
 * 
 * @author caigx
 *
 */
@Service("vehicleSaleContractsVaryBuf")
public class VehicleSaleContractsVaryBuf extends DocumentBufferService {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "10202520";
	
//	@Autowired
//	private SaleContractVaryBuffer saleContractVaryBuffer; // 缓存文旦计算
	
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractsVaryBuf.class);
	
	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	// 发票高开选项
	private static final String VSC_HIGHT_INVOICE = "VSC_HIGHT_INVOICE";

	// 四舍五入规则
	private static final String VEHICLE_PURCHASE_TAX_ROUNDING_RULE = "VEHICLE_PURCHASE_TAX_ROUNDING_RULE";

	// 是否需要创建保险预收款
	private static final String vsc_insurance_advances_received = "vsc_insurance_advances_received";

	//销售合同订金录入方式
	private static final String VEHICLE_DEPOSIT_INPUT_TYPE = "VEHICLE_DEPOSIT_INPUT_TYPE";

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

	@Autowired
	private SysOptionsDao sysOptionsDao;

	@Autowired
	private BaseOthersDao baseOtherDao;

	@Autowired
	private BaseRelatedObjectsDao baseRelatedObjectsDao;
	
	@Autowired
	private SysCodeRulesService sysCodeService; // 获得系统编码

	@Autowired
	private SaleContractsDao saleContractsDao;
	
	@Autowired
	@Qualifier("saleContractVaryBufferCalc") 
	public void setDocBuffer( DocumentBufferCalc docBuffer){
		super.docBuffer = docBuffer;
	}
	
	
	public VehicleSaleContractsVaryBuf(){
		documentClassId = 10000;
		//docBuffer = saleContractVaryBuffer; // 缓存文旦计算
	}
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	/**
	 * 处理审批列表中的审批单据信息
	 * @param vwOfficeApproveDocuments
	 * @return
	 */
	@Override
	public VehicleSaleContractsVaryView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
		VehicleSaleContractsVaryView contractsVaryView = VehicleSaleContractsVaryView.fillDataByVwDocuments(vwOfficeApproveDocuments);
		try{
			VehicleSaleContractsVary contractsVery = dao.get(VehicleSaleContractsVary.class,vwOfficeApproveDocuments.getDocumentNo());
			// 处理原合同单将当前变更值更新至原销售合同
			VehicleSaleContracts contract = saleContractsVaryDao.getSaleContractsByContractNo(contractsVery.getContractNo());
			contractsVaryView.setCustomerName(contract.getCustomerName());
			contractsVaryView.setSalesman(contractsVery.getUserName());
			contractsVaryView.setContractNo(contractsVery.getContractNo());
			contractsVaryView.setRemark(contractsVery.getRemark());
//			contractsVaryView.setPriceRequest(contractsVery.getPriceRequest());
		}catch(Exception ex){
			logger.warn(String.format("销售合同变更 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);
		}
		
		return contractsVaryView;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getDocumentDetail(String documentNo) {
		if(StringUtils.isEmpty(documentNo)){
			throw new ServiceException("审批单号为空");
		}
		VehicleSaleContractsVary contractsVery = dao.get(VehicleSaleContractsVary.class,documentNo);
		if(contractsVery==null){
			throw new ServiceException("根据审批单号："+documentNo+"未找到合同变更的记录");
		}
		
		return contractsVery;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		try {
			if (approveDocument == null || StringUtils.isEmpty(approveDocument.getDocumentNo())) {
				throw new ServiceException("审批失败:审批单号不能为空");
			}
			validateRecord(approveDocument);// 校验销售合同变更

			return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
		}catch (ServiceException e){
			ApproveResultCode code = ApproveResultCode.OTHER_KNOWN_ERROR;
			code.setMessage(e.getMessage());
			return code;
		}
	}

	/**
	 * 校验销售合同变更
	 * 
	 * @param approveDocument
	 */
	@SuppressWarnings("rawtypes")
	private void validateRecord(ApproveDocuments approveDocument) {
		VehicleSaleContractsVary contractsVery = dao.get(VehicleSaleContractsVary.class,
				approveDocument.getDocumentNo());
		if (contractsVery == null) {
			throw new ServiceException("审批失败:未找到合同变更主表记录");
		}

		if (StringUtils.isEmpty(contractsVery.getContractNo())) {
			throw new ServiceException("审批失败:原合同号不能为空");
		}

		if (Tools.toDouble(contractsVery.getPriceRequest()) < 0) {
			throw new ServiceException("审批失败:需请款金额不能为负数");
		}

		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao
				.getContractDetailVaryByDocumentNo(approveDocument.getDocumentNo());

		if (contractDetailVarys == null || contractDetailVarys.size() == 0) {
			throw new ServiceException("审批失败:没有变更车辆信息");
		}

		List<VehicleSaleContractsVary> otherContractsVerys = saleContractsVaryDao.getContractVeryInOtherDocument(
				contractsVery.getContractNo(), contractsVery.getDocumentNo());
		if (otherContractsVerys != null && otherContractsVerys.size() > 0) {
			throw new ServiceException("审批失败:没有变更车辆信息");
		}

		VehicleSaleContracts contracts = saleContractsDao.getContractByContractNo(contractsVery.getContractNo());
		if(!contractsVery.getOriCommissionAmt().equals(contracts.getCommissionAmt()) ||
				!contractsVery.getOriContractMoney().equals(contracts.getContractMoney())){
			throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
		}

		checkVehicleDetail(contractDetailVarys); // 车辆合同详情校验
		validateVehicleVary(contractDetailVarys);// 车辆校验
		checkVehicleStatusIsFit(contractDetailVarys); // 检查车辆状态是否合适

	}

	private <T> boolean allowNullEquals(T a, T b){
		if(a == null && b == null){
			return true;
		}
		if(a == null || b == null){
			return false;
		}
		if(a == b){
			return true;
		}
		return a.equals(b);
	}

	private void checkVehicleDetail(List<VehicleSaleContractDetailVary> contractDetailVaries) {
		for (VehicleSaleContractDetailVary vary : contractDetailVaries) {
			VehicleSaleContractDetail detail = saleContractsDao.getContractDetailByDetailId(vary.getContractDetailId());
			if(detail!=null) {
				if (!allowNullEquals(vary.getOriInsurancePf(), detail.getInsurancePf())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriInsuranceCost(), detail.getInsuranceCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriPresentIncome(), detail.getPresentIncome())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriPresentPf(), detail.getPresentPf())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriPresentCost(), detail.getPresentCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriConversionIncome(), detail.getConversionIncome())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriConversionPf(), detail.getConversionPf())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriConversionCost(), detail.getConversionCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriModifiedFee(), detail.getModifiedFee())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriChargeIncome(), detail.getChargeIncome())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriChargePf(), detail.getChargePf())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriChargeCost(), detail.getChargeCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleProfit(), detail.getVehicleProfit())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriLargessAmount(), detail.getLargessAmount())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriDeliverAddress(), detail.getDeliverAddress())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriPlanDeliverTime(), detail.getPlanDeliverTime())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriRealDeliverTime(), detail.getRealDeliverTime())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleCardNo(), detail.getVehicleCardNo())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleCardTime(), detail.getVehicleCardTime())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriInsuranceNo(), detail.getInsuranceNo())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriInsuranceCompanyName(), detail.getInsuranceCompanyName())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriInsuranceCompanyNo(), detail.getInsuranceCompanyNo())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriInsuranceCompanyId(), detail.getInsuranceCompanyId())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriDriveRoomNo(), detail.getDriveRoomNo())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriMaintainFee(), detail.getMaintainFee())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriLargessPart(), detail.getLargessPart())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriLargessService(), detail.getLargessService())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriCustomerIdProfit(), detail.getCustomerIdProfit())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriCustomerNoProfit(), detail.getCustomerNoProfit())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriCustomerNameProfit(), detail.getCustomerNameProfit())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleCostRef(), detail.getVehicleCostRef())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleNameNew(), detail.getVehicleNameNew())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVehicleEligibleNoNew(), detail.getVehicleEligibleNoNew())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriVnoIdNew(), detail.getVnoIdNew())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriIsContainInsuranceCost(), detail.getIsContainInsuranceCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriProfession(), detail.getProfession())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriTicketOutStockFlag(), detail.getTicketOutStockFlag())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriTicketOutStockAmount(), detail.getTicketOutStockAmount())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriBelongToSupplierRebate(), detail.getBelongToSupplierRebate())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriDiscountAmount(), detail.getDiscountAmount())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriTaxationAmount(), detail.getTaxationAmount())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriProfitReturn(), detail.getProfitReturn())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriOtherCost(), detail.getOtherCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriSubjectMatter(), detail.getSubjectMatter())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriTransportRoutes(), detail.getTransportRoutes())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriStartPoint(), detail.getStartPoint())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriWaysPoint(), detail.getWaysPoint())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriEndPoint(), detail.getEndPoint())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(vary.getOriRelationDetailId(), detail.getRelationDetailId())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
			}
		}
	}

	/**
	 * 车辆校验
	 * 
	 * @param contractDetailVarys
	 */
	private void validateVehicleVary(List<VehicleSaleContractDetailVary> contractDetailVarys) {
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
				if (StringUtils.isEmpty(contractDetailVary.getAbortComment())) {
					throw new ServiceException(String.format("终止的车辆 VIN：%s，终止原因为空", contractDetailVary.getVehicleVin()));
				}
				String condition = "AND contract_detail_id = :contractDetailId AND ISNULL(after_no,'')<>'' AND paid_by_bill=1";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("contractNo", contractDetailVary.getContractNo());
				params.put("contractDetailId", contractDetailVary.getContractDetailId());
				List<Map<String, Object>> oriChargeList = saleContractsVaryDao.getOriChargeListByCondition(condition,
						params);
				if (oriChargeList != null && oriChargeList.size() > 0) {
					throw new ServiceException(String.format("终止的车辆 VIN：%s，存在已收款的凭单支付的费用,不能终止",
							contractDetailVary.getVehicleVin()));
				}

			} else {// 非终止的车辆
				if (StringUtils.isEmpty(contractDetailVary.getVehicleVno())) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 车辆型号不能为空",
							contractDetailVary.getVehicleVin()));
				}

				if (Tools.toDouble(contractDetailVary.getVehiclePrice()) < 0.00D) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 车辆价格不能小于0",
							contractDetailVary.getVehicleVin()));
				}

				if (contractDetailVary.getPlanDeliverTime() == null) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 计划交车时间不能为空",
							contractDetailVary.getVehicleVin()));
				}
				if (StringUtils.isEmpty(contractDetailVary.getDeliverAddress())) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 交车地点不能为空",
							contractDetailVary.getVehicleVin()));
				}

				if (StringUtils.isEmpty(contractDetailVary.getSubjectMatter())) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 标的物不能为空",
							contractDetailVary.getVehicleVin()));
				}
				if (Tools.toDouble(contractDetailVary.getVehicleProfit()) > 0.00
						&& StringUtils.isEmpty(contractDetailVary.getCustomerIdProfit())) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 佣金不为零时佣金客户不能为空",
							contractDetailVary.getVehicleVin()));
				}

				if (Tools.toDouble(contractDetailVary.getVehicleProfit()) < 0.00) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 佣金金额不能小于0",
							contractDetailVary.getVehicleVin()));
				}
				if (Tools.toDouble(contractDetailVary.getLargessAmount()) < 0.00) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 公司赠券不能小于0",
							contractDetailVary.getVehicleVin()));
				}
				if (Tools.toDouble(contractDetailVary.getDiscountAmount()) < 0.00) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 优惠金额不能小于0",
							contractDetailVary.getVehicleVin()));
				}
				if (Tools.toDouble(contractDetailVary.getVehiclePriceTotal()) <= 0.00) {
					throw new ServiceException(String.format("审批失败：车辆 VIN：%s 车款合计必须为大于0",
							contractDetailVary.getVehicleVin()));
				}
				
				//修改
				if (STATUS_MODIFY == contractDetailVary.getAbortStatus()){
					//ADM17090021,已做了保险，不能变更是否含保费选项 -20170919 caigx
					if(Tools.toBoolean(contractDetailVary.getIsContainInsuranceCost()) != Tools.toBoolean(contractDetailVary.getOriIsContainInsuranceCost())){
						List<Map<String, Object>> list = saleContractsVaryDao.getInsuranceByContractDetailId(contractDetailVary.getContractDetailId());
						if(list!=null && list.size()>0){
							throw new ServiceException(String.format("审批失败：车辆 VIN：%s 已经做了保险购买，不能变更是否含保费选项",contractDetailVary.getVehicleVin()));
						}
					}
					
					
					//同步ori_vid和ori_vin
					VehicleSaleContractDetail detail = dao.get(VehicleSaleContractDetail.class, contractDetailVary.getContractDetailId());
					if(detail!=null && !StringUtils.equals(detail.getVehicleId(), contractDetailVary.getOriVehicleId())){
						contractDetailVary.setOriVehicleId(detail.getVehicleId());
						contractDetailVary.setOriVehicleVin(detail.getVehicleVin());
						dao.update(contractDetailVary);
					}
				}

				validateInsuranceVary(contractDetailVary);// 保险校验
				validatePresentVary(contractDetailVary);// 精品校验
				validateItemVary(contractDetailVary);// 改装校验
				validateGiftVary(contractDetailVary);// 赠品校验
				validateChargeVary(contractDetailVary);// 校验费用
				validateDetailVaryOther(contractDetailVary);// 变更明细的其他一些校验
				validateInvoiceVary(contractDetailVary);// 发票校验
			}

		}
	}

	/**
	 * 保险校验
	 * 
	 * @param contractDetailVary
	 */
	private void validateInsuranceVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleSaleContractInsuranceVary> insuranceVaryList = saleContractsVaryDao
				.getInsuranceVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		for (VehicleSaleContractInsuranceVary insuranceVary : insuranceVaryList) {
			if (STATUS_ABORT == insuranceVary.getAbortStatus()) {
				if (StringUtils.isEmpty(insuranceVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：保险 %s 终止但终止原因为空", insuranceVary.getCategoryName()));
				}
			}

			if (Tools.toDouble(insuranceVary.getCategoryIncome()) < 0.00) {
				throw new ServiceException(String.format("审批失败：保险 %s 金额不能小于0", insuranceVary.getCategoryName()));
			}

			if (STATUS_ABORT == insuranceVary.getAbortStatus() || STATUS_MODIFY == insuranceVary.getAbortStatus()) {
				// 验证保险是否已购买
				String condition = "AND sale_contract_insurance_id = :saleContractInsuranceId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("saleContractInsuranceId", insuranceVary.getSaleContractInsuranceId());
				List<Map<String, Object>> insuranceList = saleContractsVaryDao.getInsuranceListByCondition(condition,
						params);
				if (insuranceList != null && insuranceList.size() > 0) {
					String sStr = STATUS_ABORT == insuranceVary.getAbortStatus() ? "终止" : "修改";
					throw new ServiceException(String.format("审批失败：车辆 %s 已经购买保险或建立了保单，不能%s",
							insuranceList.get(0).get("vehicle_vin"), sStr));
				}
			}

			List<VehicleSaleContractInsurance> insurances = saleContractsDao.getInsureanceBySaleContractInsuranceId(insuranceVary.getSaleContractInsuranceId());
			if(insurances!=null && insurances.size() > 0){
				VehicleSaleContractInsurance insurance = insurances.get(0);
				if(!allowNullEquals(insuranceVary.getOriCategoryIncome(),insurance.getCategoryIncome())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(insuranceVary.getCategoryId(),insurance.getCategoryId())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(insuranceVary.getCategoryScale(),insurance.getCategoryScale())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(insuranceVary.getCategoryCost(),insurance.getCategoryCost())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(insuranceVary.getCostStatus(),insurance.getCostStatus())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			}
		}
	}

	/**
	 * 精品校验
	 * 
	 * @param contractDetailVary
	 */
	private void validatePresentVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleSaleContractPresentVary> presentVaryList = saleContractsVaryDao
				.getPresentVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		for (VehicleSaleContractPresentVary presentVary : presentVaryList) {
			if (STATUS_ABORT == presentVary.getAbortStatus()) {
				if (StringUtils.isEmpty(presentVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：精品 %s 终止但终止原因为空", presentVary.getPartNo()));
				}
			}

			if (Tools.toDouble(presentVary.getPlanQuantity()) <= 0) {
				throw new ServiceException(String.format("审批失败：精品 %s的计划数量不能小于等于0", presentVary.getPartNo()));
			}

			if (Tools.toDouble(presentVary.getPlanQuantity()) < Tools.toDouble(presentVary.getGetQuantity())) {
				throw new ServiceException(String.format("审批失败：精品 %s的计划数量不能小于已领数量", presentVary.getPartNo()));
			}

			VehicleSaleContractPresent present = saleContractsDao.getPresentBySaleContractPresentId(presentVary.getSaleContractPresentId());
			if(present != null) {
				if (!allowNullEquals(presentVary.getOriPlanQuantity(), present.getPlanQuantity())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(presentVary.getOriIncome(), present.getIncome())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(presentVary.getPosPrice(), present.getPosPrice())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(presentVary.getPosAgio(), present.getPosAgio())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(presentVary.getStockId(), present.getStockId())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
			}
		}

	}

	/**
	 * 改装校验
	 * 
	 * @param contractDetailVary
	 */
	private void validateItemVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleSaleContractItemVary> itemVaryList = saleContractsVaryDao
				.getItemVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		for (VehicleSaleContractItemVary itemVary : itemVaryList) {
			if (STATUS_ABORT == itemVary.getAbortStatus()) {
				if (StringUtils.isEmpty(itemVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：改装预估项目  %s 终止但终止原因为空", itemVary.getItemName()));
				}
			}

			/* if (Tools.toDouble(itemVary.getItemCost()) < 0.00) {
				throw new ServiceException(String.format("审批失败：改装预估项目  %s 金额不能小于0", itemVary.getItemName()));
			} */

			if (STATUS_ABORT == itemVary.getAbortStatus() || STATUS_MODIFY == itemVary.getAbortStatus()) {
				String condition = "AND sale_contract_item_id=:saleContractItemId AND status<>2";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("saleContractItemId", itemVary.getSaleContractItemId());
				List<Map<String, Object>> conversionList = saleContractsVaryDao.getConversionListByCondition(condition,
						params);
				if (conversionList != null && conversionList.size() > 0) {
					if (STATUS_ABORT == itemVary.getAbortStatus()) {
						throw new ServiceException(String.format("审批失败：车辆 %s中【%s】已经做了建立了改装申请单但未完成改装，不能终止。请先完成改装或作废改装单后再试。",
								conversionList.get(0).get("vehicle_vin"),conversionList.get(0).get("item_name")));
					}
				
					if (Tools.toDouble(itemVary.getItemCost()) != Tools.toDouble(itemVary.getOriItemCost())) {
						throw new ServiceException(String.format("审批失败：车辆 %s 已经做了改装或建立了改装申请单，不能修改改装成本",
								conversionList.get(0).get("vehicle_vin")));
					}
				}

				// 验证原销售合同的改装项目是否存在
				VehicleSaleContractItem item = dao.get(VehicleSaleContractItem.class, itemVary.getSaleContractItemId());
				if (item == null) {
					throw new ServiceException(String.format("审批失败：改装项目  %s在原销售合中不存在", itemVary.getItemName()));
				}

				if(!allowNullEquals(itemVary.getOriItemCost(),item.getItemCost())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(itemVary.getOriIncome(),item.getIncome())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
				if(!allowNullEquals(itemVary.getItemId(),item.getItemId())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			}
		}
	}

	/**
	 * 赠品校验
	 * 
	 * @param contractDetailVary
	 */
	private void validateGiftVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleSaleContractGiftsVary> giftVaryList = saleContractsVaryDao
				.getGiftVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		for (VehicleSaleContractGiftsVary giftVary : giftVaryList) {
			if (STATUS_ABORT == giftVary.getAbortStatus()) {
				if (StringUtils.isEmpty(giftVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：赠品 %s 终止但终止原因为空", giftVary.getItemName()));
				}
			} else {
				if (giftVary.getGiveFlag() == null) {
					throw new ServiceException(String.format("审批失败：赠品 %s 赠送方式不能为空", giftVary.getItemName()));
				}
			}
			VehicleSaleContractGifts gift = dao.get(VehicleSaleContractGifts.class, giftVary.getGiftId());
			if(!allowNullEquals(giftVary.getDetailId(),gift.getDetailId())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(giftVary.getItemId(),gift.getItemId())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(giftVary.getGiveFlag(),gift.getGiveFlag())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
		}
	}

	/**
	 * 校验费用
	 * 
	 * @param contractDetailVary
	 */
	private void validateChargeVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleSaleContractChargeVary> chargeVaryList = saleContractsVaryDao
				.getChargeVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		for (VehicleSaleContractChargeVary chargeVary : chargeVaryList) {
			if (STATUS_ABORT == chargeVary.getAbortStatus()) {
				if (StringUtils.isEmpty(chargeVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：费用 %s 终止但终止原因为空", chargeVary.getChargeName()));
				}
				String condition = "AND sale_contract_charge_id = :saleContractChargeId AND ISNULL(after_no,'')<>'' AND paid_by_bill=1";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("contractNo", contractDetailVary.getContractNo());
				params.put("saleContractChargeId", chargeVary.getSaleContractChargeId());
				List<Map<String, Object>> oriChargeList = saleContractsVaryDao.getOriChargeListByCondition(condition,
						params);
				if (oriChargeList != null && oriChargeList.size() > 0) {
					throw new ServiceException(
							String.format("审批失败：费用 %s 为凭单支付且已收款，不能终止该费用", chargeVary.getChargeName()));
				}
			} else if (STATUS_MODIFY == chargeVary.getAbortStatus()) {
				if (Tools.toBoolean(chargeVary.getPaidByBill()) != Tools.toBoolean(chargeVary.getOriPaidByBill())) {// 修改了凭单支付
					if (!Tools.toBoolean(chargeVary.getPaidByBill())) {// 如果由凭单变为非凭单
						String condition = "AND sale_contract_charge_id = :saleContractChargeId AND ISNULL(after_no,'')<>'' AND paid_by_bill=1";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("contractNo", contractDetailVary.getContractNo());
						params.put("saleContractChargeId", chargeVary.getSaleContractChargeId());
						List<Map<String, Object>> oriChargeList = saleContractsVaryDao.getOriChargeListByCondition(
								condition, params);
						if (oriChargeList != null && oriChargeList.size() > 0) {
							throw new ServiceException(String.format("审批失败：原费用 %s为凭单支付且已收款，不能变更为非凭单支付",
									chargeVary.getChargeName()));
						}
					}
				} else {
					if (Tools.toBoolean(chargeVary.getPaidByBill())) {// 没修改凭单支付且当前费用为凭单
						String condition = "AND sale_contract_charge_id = :saleContractChargeId AND ISNULL(after_no,'') <>'' AND paid_by_bill=1";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("contractNo", contractDetailVary.getContractNo());
						params.put("saleContractChargeId", chargeVary.getSaleContractChargeId());
						List<Map<String, Object>> oriChargeList = saleContractsVaryDao.getOriChargeListByCondition(
								condition, params);
						if (oriChargeList != null && oriChargeList.size() > 0) {
							Double income = (Double) oriChargeList.get(0).get("income");
							Double paidAmount = (Double) oriChargeList.get(0).get("paidAmount");
							if (Tools.toDouble(income) < Tools.toDouble(paidAmount)) {
								throw new ServiceException(String.format("审批失败：费用 %s 收入金额不能小于已收款金额",
										chargeVary.getChargeName()));
							}
						}
					}
				}

				// 判断是否已经报销，如果已经报销不能修改凭单支付及费用金额，只能修改收入
				String condition = "AND sale_contract_charge_id = :saleContractChargeId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("contractNo", contractDetailVary.getContractNo());
				params.put("saleContractChargeId", chargeVary.getSaleContractChargeId());
				List<Map<String, Object>> oriChargeList = saleContractsVaryDao.getOriChargeListByCondition(condition,
						params);
				if (oriChargeList != null && oriChargeList.size() > 0) {
					int costStatus = 0;
					if (oriChargeList.get(0).get("cost_status")!=null && StringUtils.isNotEmpty(oriChargeList.get(0).get("cost_status").toString())) {
						costStatus = Integer.parseInt(oriChargeList.get(0).get("cost_status").toString());
					}

					if (costStatus == 1) {
						if (Tools.toBoolean(chargeVary.getPaidByBill()) != Tools.toBoolean(chargeVary
								.getOriPaidByBill())
								|| Tools.toDouble(chargeVary.getChargeCost()) != Tools.toDouble(chargeVary
										.getOriChargeCost())) {
							throw new ServiceException(String.format("审批失败：费用 %s已做费用报销，不能变更凭单支付及费用金额",
									chargeVary.getChargeName()));
						}
					}
				}
			}

			if (Tools.toDouble(chargeVary.getIncome()) == 0) {
				if (Tools.toDouble(chargeVary.getChargePf()) <= 0) {
					throw new ServiceException(String.format("审批失败：费用 %s的金额必须大于0", chargeVary.getChargeName()));
				}
			}

			VehicleSaleContractCharge charge = dao.get(VehicleSaleContractCharge.class, chargeVary.getSaleContractChargeId());
			if(charge != null) {
				if (!allowNullEquals(chargeVary.getOriPaidByBill(), charge.getPaidByBill())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(chargeVary.getOriIncome(), charge.getIncome())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(chargeVary.getOriChargeCost(), charge.getChargeCost())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(chargeVary.getOriChargePf(), charge.getChargePf())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
				if (!allowNullEquals(chargeVary.getChargeId(), charge.getChargeId())) {
					throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");
				}
			}
		}
	}

	/**
	 * 变更明细的其他一些校验
	 * 
	 * @param contractDetailVary
	 */
	private void validateDetailVaryOther(VehicleSaleContractDetailVary contractDetailVary) {
		// 验证车辆库存状态，只有已订购才允许变更
		if (StringUtils.isNotEmpty(contractDetailVary.getVehicleId())) {
			VehicleStocks stock = dao.get(VehicleStocks.class, contractDetailVary.getVehicleId());
			if (stock != null && stock.getStatus() != 1 && stock.getStatus() != 0) {
				// 如果该车已经出库，判断是否符合出库审批日期小于设定，如果符合也允许
				List<Map<String, Object>> outStocksList = saleContractsVaryDao.getoutStocksInTime(contractDetailVary
						.getContractDetailId());
				if (outStocksList == null || outStocksList.size() == 0) {
					throw new ServiceException(String.format("审批失败：车辆%s的审批状态不正确", contractDetailVary.getVehicleVin()));
				}
			}
		}
		// 验证原销售合同明细是否已审核,非新增的需要判断
		if (contractDetailVary.getAbortStatus() != STATUS_NEW) {
			VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class,
					contractDetailVary.getContractDetailId());
			if (contractDetail != null && contractDetail.getApproveStatus() != 1) {
				throw new ServiceException(String.format("审批失败：车辆%s对应的销售合同明细状态不正确", contractDetailVary.getVehicleVin()));
			}
		}

		// 如果是终止车辆，验证能否终止
		if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
			// 验证佣金是否已请或已付
			List<FinanceDocumentEntries> brokerageEntries = saleContractsVaryDao
					.getBrokerageEntriesByDocumentId(contractDetailVary.getContractDetailId());
			if (brokerageEntries != null && brokerageEntries.size() > 0
					&& StringUtils.isNotEmpty(brokerageEntries.get(0).getAfterNo())) {
				throw new ServiceException(String.format("审批失败：车辆%s对应的客户佣金财务已做了后续操作，不能终止",
						contractDetailVary.getVehicleVin()));
			}

			// 验证赠券是否已送
			VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class,
					contractDetailVary.getContractDetailId());
			if (contractDetail != null && Tools.toDouble(contractDetail.getTicketOutStockAmount()) > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s对应的公司赠券已经发放，不能终止",
						contractDetailVary.getVehicleVin()));
			}
			// 验证保险是否已购买
			String condition = "AND contract_detail_id = :contractDetailId";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("contractDetailId", contractDetailVary.getContractDetailId());
			List<Map<String, Object>> insuranceList = saleContractsVaryDao.getInsuranceListByCondition(condition,
					params);
			if (insuranceList != null && insuranceList.size() > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s已经购买保险或建立了保单，不能终止",
						contractDetailVary.getVehicleVin()));
			}

			// 验证精品是否已出库
			List<VehicleSaleContractPresent> presentList = saleContractsVaryDao.getOutPresent(contractDetailVary
					.getContractDetailId());
			if (presentList != null && presentList.size() > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s的精品已出库，不能终止", contractDetailVary.getVehicleVin()));
			}
			// 验证是否做了改装单
			String condition1 = "AND vscd_id=:contractDetailId AND status<> 2";
			Map<String, Object> params1 = new HashMap<String, Object>();
			params1.put("contractDetailId", contractDetailVary.getContractDetailId());
			List<Map<String, Object>> conversionList = saleContractsVaryDao.getConversionListByCondition(condition1,
					params1);
			if (conversionList != null && conversionList.size() > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s已经建立了改装申请单但未完成，不能终止，请先完成改装或作废改装单后再试",
						contractDetailVary.getVehicleVin()));
			}

			// 验证发票是否已开，已开不能终止车辆
			String condition2 = "AND contract_detail_id=:contractDetailId AND isnull(after_no,'')<>''";
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("contractDetailId", contractDetailVary.getContractDetailId());
			List<Map<String, Object>> invoiceList = saleContractsVaryDao.getInvoiceListByCondition(condition2, params2);
			if (invoiceList != null && invoiceList.size() > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s已经开具发票，不能终止", contractDetailVary.getVehicleVin()));
			}

			// 验证是否已经做了预算单
			String condition3 = "AND sale_contract_detail_id=:contractDetailId AND isnull(after_no,'')<>''";
			Map<String, Object> params3 = new HashMap<String, Object>();
			params3.put("contractDetailId", contractDetailVary.getContractDetailId());
			List<Map<String, Object>> budgetList = saleContractsVaryDao.getBudgetListByCondition(condition3, params3);
			if (budgetList != null && budgetList.size() > 0) {
				throw new ServiceException(String.format("审批失败：车辆%s已建立消贷预算单，不能终止", contractDetailVary.getVehicleVin()));
			}
		}

		// 验证赠券是否已送
		VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class,
				contractDetailVary.getContractDetailId());
		if (contractDetail != null) {
			if (Tools.toDouble(contractDetailVary.getLargessAmount()) < Tools.toDouble(contractDetail
					.getTicketOutStockAmount())) {
				throw new ServiceException(String.format("审批失败：车辆%s对应的公司赠券不能小于已发放的赠券金额",
						contractDetailVary.getVehicleVin()));
			}
		}

		// 如果有做预算则验证车款合计是否小于已做预算的车辆贷款（不含费用贷款）
		String condition3 = "AND sale_contract_detail_id=:contractDetailId ";
		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("contractDetailId", contractDetailVary.getContractDetailId());
		List<Map<String, Object>> budgetList = saleContractsVaryDao.getBudgetListByCondition(condition3, params3);
		if (budgetList != null && budgetList.size() > 0) {
			double loanAmount = 0.00;
			if (budgetList.get(0).get("loan_amount")!=null && StringUtils.isNotEmpty(budgetList.get(0).get("loan_amount").toString())) {
				loanAmount = Double.parseDouble(budgetList.get(0).get("loan_amount").toString());
			}
			if (Tools.toDouble(contractDetailVary.getVehiclePriceTotal()) < loanAmount) {
				throw new ServiceException(String.format("审批失败：车辆%s车款合计不能小于已经建立预算单的车辆贷款",
						contractDetailVary.getVehicleVin()));
			}
		}

		// 验证佣金--begin
		double dAmount = 0.00D;
		String sCustomer_id_profit = "";
		String sCustomer_no_profit = "";
		String sCustomer_name_profit = "";
		List<FinanceDocumentEntries> brokerageEntries = saleContractsVaryDao
				.getBrokerageEntriesByDocumentId(contractDetailVary.getContractDetailId());
		if (brokerageEntries != null && brokerageEntries.size() > 0) {
			if (StringUtils.isNotEmpty(brokerageEntries.get(0).getAfterNo())) {
				dAmount = Tools.toDouble(brokerageEntries.get(0).getDocumentAmount());
				sCustomer_id_profit = brokerageEntries.get(0).getObjectId();
				sCustomer_no_profit = brokerageEntries.get(0).getObjectNo();
				sCustomer_name_profit = brokerageEntries.get(0).getObjectName();

				if (Tools.toDouble(contractDetailVary.getVehicleProfit()) != dAmount
						|| !sCustomer_id_profit.equals(contractDetailVary.getCustomerIdProfit())) {
					throw new ServiceException(String.format("审批失败：车辆%s已请或已付佣金款，不能再变更客户佣金和佣金客户",
							contractDetailVary.getVehicleVin()));
				}
			}
		}
	}

	/**
	 * 发票验证
	 * 
	 * @param contractDetailVary
	 */
	private void validateInvoiceVary(VehicleSaleContractDetailVary contractDetailVary) {
		List<VehicleInvoicesVary> invoiceVaryList = saleContractsVaryDao.getInvoiceVaryByContractDetailId(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
		double dInvoice = 0.00D;
		for (VehicleInvoicesVary invoiceVary : invoiceVaryList) {
			if (STATUS_ABORT == invoiceVary.getAbortStatus()) {
				if (StringUtils.isEmpty(invoiceVary.getAbortComment())) {
					throw new ServiceException(String.format("审批失败：车辆%s中,终止发票明细时必须填写终止原因",
							contractDetailVary.getVehicleVin()));
				}
				String condition2 = "AND invoices_detail_vary_id=:invoicesDetailVaryId AND isnull(after_no,'')<>''";
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("invoicesDetailVaryId", invoiceVary.getInvoicesDetailVaryId());
				List<Map<String, Object>> invoiceList = saleContractsVaryDao.getInvoiceListByCondition(condition2,
						params2);
				if (invoiceList != null && invoiceList.size() > 0) {
					throw new ServiceException(String.format("审批失败：车辆%s中发票已经开票，不能终止发票明细",
							contractDetailVary.getVehicleVin()));
				}

				dInvoice = dInvoice - Tools.toDouble(invoiceVary.getInvoiceAmount());
			} else if (STATUS_NEW == invoiceVary.getAbortStatus()) {
				dInvoice = dInvoice + Tools.toDouble(invoiceVary.getInvoiceAmount());
			} else if (STATUS_MODIFY == invoiceVary.getAbortStatus()) {
				dInvoice += Tools.toDouble(invoiceVary.getInvoiceAmount())
						- Tools.toDouble(invoiceVary.getOriInvoiceAmount());
			}

			VehicleInvoices invoices = dao.get(VehicleInvoices.class, invoiceVary.getInvoicesDetailId());
			if(!allowNullEquals(invoiceVary.getOriObjectName(),invoices.getObjectName())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(invoiceVary.getOriObjectNo(),invoices.getObjectNo())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(invoiceVary.getOriObjectId(),invoices.getObjectId())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(invoiceVary.getOriInvoiceAmount(),invoices.getInvoiceAmount())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
			if(!allowNullEquals(invoiceVary.getOriInvoiceType(),invoices.getInvoiceType())){throw new ServiceException("审批失败: 数据过时，请使用PC端进行审批");}
		}

		List<VehicleInvoices> oriInvoiceList = saleContractsVaryDao.getOriInvoiceByContractDetailId(contractDetailVary
				.getContractDetailId());
		for (VehicleInvoices oriInvoice : oriInvoiceList) {
			dInvoice = dInvoice + Tools.toDouble(oriInvoice.getInvoiceAmount());
		}

		if (dInvoice > Tools.toDouble(contractDetailVary.getVehiclePriceTotal())) {
			if (!isHightInvoice()) {
				throw new ServiceException(String.format("审批失败：车辆%s开票金额不能大于车款合计", contractDetailVary.getVehicleVin()));
			}
		}

		// 验证购车发票是否唯一
		// C#中的算法不合适，只需要判断发票（包括变更发票）中的 购车发票张数是否大于1
		int vehicleInvoiceCount = 0;
		List<VehicleInvoices> oriInvoiceNotInVaryList = saleContractsVaryDao.getOriInvoiceNotInVary(contractDetailVary
				.getContractDetailId());
		for (VehicleInvoices oriInvoiceNotInVary : oriInvoiceNotInVaryList) {
			if ("购车发票".equals(oriInvoiceNotInVary.getInvoiceType())) {// 不在变更记录中的购车发票
				vehicleInvoiceCount++;
			}
		}
		for (VehicleInvoicesVary invoiceVary : invoiceVaryList) {
			if (STATUS_ABORT != invoiceVary.getAbortStatus() && "购车发票".equals(invoiceVary.getInvoiceType())) {// 非终止的购车发票
				vehicleInvoiceCount++;
			}
		}

		if (vehicleInvoiceCount > 1) {
			throw new ServiceException(String.format("审批失败：车辆%s只能有一张购车发票", contractDetailVary.getVehicleVin()));
		}

	}

	/**
	 * 是否高开选项
	 * 
	 * @return
	 */
	private boolean isHightInvoice() {
		boolean isHightInvoice = false;
		SysUsers user = HttpSessionStore.getSessionUser();
		List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(VSC_HIGHT_INVOICE, user.getDefaulStationId());
		if (options != null && options.size() > 0) {
			if ("是".equals(options.get(0).getOptionValue())) {
				isHightInvoice = true;
			}
		}
		return isHightInvoice;
	}

	/**
	 * 检查状态是否合适
	 * 
	 * @param contractDetailVarys
	 */
	private void checkVehicleStatusIsFit(List<VehicleSaleContractDetailVary> contractDetailVarys) {
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class,
					contractDetailVary.getContractDetailId());
			if (contractDetail != null && Tools.toShort(contractDetail.getStatus()) == 2) {
				throw new ServiceException(String.format("审批失败：车辆%s已经被财务审核，不能再变更", contractDetailVary.getVehicleVin()));
			}

			if (contractDetailVary.getAbortStatus() == STATUS_NEW && StringUtils.isNotEmpty(contractDetailVary.getVehicleId())) {
				VehicleStocks stock = dao.get(VehicleStocks.class, contractDetailVary.getVehicleId());
				// 如果车辆库存状态不是为“库存中”则不能通过校验
				if (stock != null && stock.getStatus() != null && stock.getStatus() != 0)
					throw new ServiceException(String.format("审批失败：不能增加车辆%s，该车库存状态不正确", contractDetailVary.getVehicleVin()));
			}
			
			if (StringUtils.isNotEmpty(contractDetailVary.getVehicleVin())) {
				List<Map<String, Object>> resultList = saleContractsVaryDao.getVehicleInOtherContract(
						contractDetailVary.getVehicleVin(), contractDetailVary.getContractNo());
				if (resultList != null && resultList.size() > 0) {
					throw new ServiceException(String.format("审批失败：车辆%s已被其他销售合同%s使用了，不能审批",
							contractDetailVary.getVehicleVin(), resultList.get(0).get("contract_no")));
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		VehicleSaleContractsVary contractsVery = dao.get(VehicleSaleContractsVary.class, approveDocument.getDocumentNo());
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(approveDocument.getDocumentNo());
		VehicleSaleContracts contract = saleContractsVaryDao.getSaleContractsByContractNo(contractsVery.getContractNo());
		// 更新合同信息
		updateContract(contractsVery);
		// 处理购置税
		dealPurchaseTax(contractDetailVarys);
		// 更新原销售合同执行状态
		updateContractStatus(contractsVery, contract);
		// 导出客户及车辆档案
		exportArchives(contractsVery, contractDetailVarys, contract);
		// 处理单据分录
		dealBillDocment(contractsVery, contract);
		// 更新车辆相关费用
		updateVehicleCharge(contractsVery, contract);
		// 更新预算单
		updateBudget(contractsVery, contract);
		// 处理费用单据分录
		dealChargeDocument(contractsVery, contract);
		//setSetFlowData方法的处理放到updateLastVehicleInfo中

		//处理预付订金
		dealDeposit(contractsVery,contract);

		//自动关联相同的改装项目
		autoBuildItemRelation(contractsVery,contract);
		return super.onLastApproveLevel(approveDocument, comment);
	}

	//自动关联相同的改装项目
	@SuppressWarnings({"unchecked" })
	private void autoBuildItemRelation(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = (List<VehicleSaleContractDetailVary>)dao.findByHql("from VehicleSaleContractDetailVary where  documentNo = ?", contractsVery.getDocumentNo());
		for(VehicleSaleContractDetailVary contractDetailVary:contractDetailVarys){
			if(STATUS_ABORT == Tools.toShort(contractDetailVary.getAbortStatus()) && StringUtils.isNotEmpty(contractDetailVary.getOriVehicleId())){
				dealRelation(contractDetailVary,contractDetailVary.getOriVehicleId(),true);
			}else{
				//被换的车或被清空的车辆
				if(StringUtils.isNotEmpty(contractDetailVary.getOriVehicleId()) && !StringUtils.equals(contractDetailVary.getVehicleId(), contractDetailVary.getOriVehicleId())){
					dealRelation(contractDetailVary,contractDetailVary.getOriVehicleId(),true);
				}
				 //当前改装单已选车辆
				if(StringUtils.isNotEmpty(contractDetailVary.getVehicleId())){
					dealRelation(contractDetailVary,contractDetailVary.getVehicleId(),false);
				}
			}
		}

	}

	@SuppressWarnings({"unchecked" })
	private void dealRelation(VehicleSaleContractDetailVary contractDetailVary,String vehicleId, boolean isDel){
		List<VehicleSaleContractItem> contractItemList =  (List<VehicleSaleContractItem>)dao.findByHql("FROM VehicleSaleContractItem  WHERE contractDetailId = ?", contractDetailVary.getContractDetailId());
		for(VehicleSaleContractItem contractItem:contractItemList){
			List<VehicleConversionDetail> conversionDetailList = (List<VehicleConversionDetail>)dao.findByHql("FROM VehicleConversionDetail WHERE status IN (1,2,30,50) "
					+ "AND (ISNULL(saleContractItemId,'') = '' OR ISNULL(isAutoRelation,false) = true ) AND vehicleId = ? AND itemId = ? ORDER BY conversionType ASC",
					vehicleId, contractItem.getItemId());
			for(VehicleConversionDetail conversionDetail:conversionDetailList){
				if(isDel){
					conversionDetail.setSaleContractItemId(null);
					conversionDetail.setIsAutoRelation(null);
				}else{
					conversionDetail.setSaleContractItemId(contractItem.getSaleContractItemId());
					conversionDetail.setIsAutoRelation(true);
				}
				dao.update(conversionDetail);
			}
		}
	}


	//处理预付订金
	@SuppressWarnings("unchecked")
	private void dealDeposit(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		if("车辆录入".equals(getVehicleDepositInputType(contractsVery.getStationId()))){
			List<Double> depositList = (List<Double>) dao.findByHql("SELECT SUM(deposit) FROM VehicleSaleContractDetail  WHERE contractNo = ? AND approveStatus<>30", contract.getContractNo());
			double deposit = (depositList == null || depositList.size()==0)?0.00D:Tools.toDouble(depositList.get(0));
			contract.setDeposit(deposit);
		}
	}

	//获得销售合同订金录入方式
	private String getVehicleDepositInputType(String stationId) {
		List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(VEHICLE_DEPOSIT_INPUT_TYPE,stationId);
		if (options != null && options.size() > 0) {
			return options.get(0).getOptionValue();
		}
		return "";
	}

	/**
	 * 更新合同信息
	 * 
	 * @param contractsVery
	 */
	private void updateContract(VehicleSaleContractsVary contractsVery) {
		// 处理原合同单将当前变更值更新至原销售合同
		VehicleSaleContracts contract = saleContractsVaryDao.getSaleContractsByContractNo(contractsVery.getContractNo());
		double contractMoney = Tools.toDouble(contractsVery.getOriContractMoney()) + Tools.toDouble(contractsVery.getPriceVary());
		double commissionAmt = Tools.toDouble(contractsVery.getOriCommissionAmt()) + Tools.toDouble(contractsVery.getCommissionAmtVary());
		contract.setContractMoney(contractMoney);
		contract.setCommissionAmt(commissionAmt);
		//更新合同变更次数  20170122 -caigx
		int varyCount = Tools.toInt(contract.getVaryCount())+1;
		contractsVery.setVaryCount(varyCount);
		contract.setVaryCount(varyCount);
		// 更新车辆信息
		Map<String, Short> dict = updateLastVehicleInfo(contractsVery, contract);
		// 更新保险信息
		updateLastInsuranceInfo(contractsVery);
		// 更新精品信息
		updateLastPresentInfo(contractsVery);
		// 更新改装信息
		updateLastEpibolicInfo(contractsVery);
		// 更新费用信息
		updateLastChargeInfo(contractsVery);
		// 更新发票
		updateLastInvoiceInfo(contractsVery);
		// 更新赠品
		updateLastGiftInfo(contractsVery);

		// 更新车辆库存状态
		updateVehicleStockStaus(contractsVery,dict);

	}
	
	/**
	 * 更新车辆库存状态
	 * @param contractsVery
	 * @param dict 
	 */
	@SuppressWarnings("unchecked")
	private void updateVehicleStockStaus(VehicleSaleContractsVary contractsVery, Map<String, Short> dict) {
		VehicleSaleContracts contract = saleContractsVaryDao.getSaleContractsByContractNo(contractsVery.getContractNo());
		for(String vin:dict.keySet()){
			List<VehicleStocks> stockList =  (List<VehicleStocks>) dao.findByHql("from VehicleStocks  where vehicleVin= ? ", vin);
			if(stockList==null || stockList.size()==0){
				continue;
			}
			VehicleStocks stock = stockList.get(0);
			Short value = dict.get(vin);
			if(STATUS_ABORT == value){
				double chargeCost = 0;//已报销费用
                String contractDetailId = "";
				List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql("from VehicleSaleContractDetail where vehicleVin = ?  and contractNo = ?", vin,contractsVery.getContractNo());
				if(contractDetailList!=null && contractDetailList.size()>0){
					contractDetailId = contractDetailList.get(0).getContractDetailId();
					List<VehicleSaleContractCharge> contractChargeList = (List<VehicleSaleContractCharge>) dao.findByHql("from VehicleSaleContractCharge where contractDetailId = ?", contractDetailId);
					for(VehicleSaleContractCharge contractCharge:contractChargeList){
						chargeCost+=Tools.toDouble(contractCharge.getChargeCost());
					}
				}
				stock.setSaleContractNo(null);
				stock.setSaleContractCode(null);
				stock.setCustomerId(null);
				stock.setCustomerName(null);
				stock.setSeller(null);
				stock.setSellerId(null);
				stock.setStatus((short)0);
				stock.setOtherCost(Tools.toDouble(stock.getOtherCost())+ chargeCost);
				dao.update(stock);
			}else if(STATUS_NEW == value){
				stock.setSaleContractNo(contract.getContractNo());
				stock.setSaleContractCode(contract.getContractCode());
				stock.setCustomerId(contract.getCustomerId());
				stock.setCustomerName(contract.getCustomerName());
				stock.setSeller(contract.getSeller());
				
				List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql("from VehicleSaleContractDetail where vehicleVin = ?  and contractNo = ?", vin,contractsVery.getContractNo());
				for(VehicleSaleContractDetail contractDetail:contractDetailList){
					if(contractDetail.getAbortStatus()!=null&& contractDetail.getAbortStatus()==STATUS_ABORT){
						continue;
					}
					
					if(contractDetail.getApproveStatus()==(short)0||contractDetail.getApproveStatus()==(short)20||contractDetail.getApproveStatus()==(short)1||contractDetail.getApproveStatus()==(short)2){
						stock.setStatus((short)1);
					}
				}
				dao.update(stock);
			}
			
		}
	}

	/**
	 * 更新赠品
	 * @param contractsVery
	 */
	private void updateLastGiftInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao
				.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			List<VehicleSaleContractGiftsVary> giftVaryList = saleContractsVaryDao.getGiftVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
			for (VehicleSaleContractGiftsVary giftVary : giftVaryList) {
				List<VehicleSaleContractGifts> oriGiftList = (List<VehicleSaleContractGifts>) dao.findByHql("from VehicleSaleContractGifts where detailId = ? AND contractDetailId =? ", giftVary.getDetailId(), giftVary.getContractDetailId());
				if (STATUS_ABORT == giftVary.getAbortStatus()) {
					if (oriGiftList == null || oriGiftList.size() == 0) {
						continue;
					}
//					logger.debug("删除gift,detailId =" + oriGiftList.get(0).getDetailId()+",giftId ="+ oriGiftList.get(0).getGiftId());
					dao.delete(oriGiftList.get(0));
//					dao.flush();

				} else if (STATUS_NEW == giftVary.getAbortStatus()) {
					VehicleSaleContractGifts oriGift = new VehicleSaleContractGifts();
					FillContractGifts(oriGift, giftVary);
					oriGift.setAbortStatus(STATUS_NEW);
					dao.save(oriGift);

				} else if (STATUS_MODIFY == giftVary.getAbortStatus()) {
					if (oriGiftList == null || oriGiftList.size() == 0) {
						continue;
					}
					VehicleSaleContractGifts oriGift = oriGiftList.get(0);

					if (oriGift != null && (STATUS_ABORT != Tools.toShort(oriGift.getAbortStatus()))) {
						FillContractGifts(oriGift, giftVary);
//						logger.debug("修改gift,detailId =" + oriGift.getDetailId());
						dao.update(oriGift);
					}
				}
			}
		}
	}

	/**
	 * 更新oriGift
	 * @param oriGift
	 * @param giftVary
	 * @return
	 */
	private VehicleSaleContractGifts FillContractGifts(VehicleSaleContractGifts oriGift, VehicleSaleContractGiftsVary giftVary) {
		oriGift.setGiftId(giftVary.getGiftId());
		oriGift.setContractDetailId(giftVary.getContractDetailId());
		oriGift.setDetailId(giftVary.getDetailId());
		oriGift.setItemId(giftVary.getItemId());
		oriGift.setItemName(giftVary.getItemName());
		oriGift.setItemType(giftVary.getItemType());
		oriGift.setAmount(giftVary.getAmount());
		oriGift.setGiveFlag(giftVary.getGiveFlag());
		oriGift.setRemark(giftVary.getRemark());
		oriGift.setAbortStatus(giftVary.getAbortStatus());
		oriGift.setContractNo(giftVary.getContractNo());
		
		
		return oriGift;
	}

	/**
	 * 更新发票
	 * @param contractsVery
	 */
	@SuppressWarnings("unchecked")
	private void updateLastInvoiceInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			List<VehicleInvoicesVary> invoiceVaryList = (List<VehicleInvoicesVary>)dao.findByHql(" from VehicleInvoicesVary  where detailVaryId = ?", contractDetailVary.getDetailVaryId());
			for(VehicleInvoicesVary invoiceVary:invoiceVaryList){
				if (STATUS_ABORT == invoiceVary.getAbortStatus()) {
					VehicleInvoices oriInvoice = dao.get(VehicleInvoices.class, invoiceVary.getInvoicesDetailId());
					if(oriInvoice!=null){
						dao.delete(oriInvoice);
					}
				}else if(STATUS_NEW == invoiceVary.getAbortStatus()){
					VehicleInvoices oriInvoice  = new VehicleInvoices();
					FillVehicleInvoices(oriInvoice,invoiceVary);
					dao.save(oriInvoice);
				}else if(STATUS_MODIFY == invoiceVary.getAbortStatus()){
					VehicleInvoices oriInvoice = dao.get(VehicleInvoices.class, invoiceVary.getInvoicesDetailId());
					if(oriInvoice!=null){
						FillVehicleInvoices(oriInvoice,invoiceVary);
						dao.update(oriInvoice);
					}
				}
			}
		}
	}

	private VehicleInvoices FillVehicleInvoices(VehicleInvoices oriInvoice, VehicleInvoicesVary invoiceVary) {
		oriInvoice.setInvoicesDetailId(invoiceVary.getInvoicesDetailId());
		oriInvoice.setContractDetailId(invoiceVary.getContractDetailId());
		oriInvoice.setInvoiceType(invoiceVary.getInvoiceType());
		oriInvoice.setInvoiceAmount(Tools.toBigDecimal(invoiceVary.getInvoiceAmount()));
		oriInvoice.setObjectId(invoiceVary.getObjectId());
		oriInvoice.setObjectNo(invoiceVary.getObjectNo());
		oriInvoice.setObjectName(invoiceVary.getObjectName());
		oriInvoice.setRemark(invoiceVary.getRemark());
		oriInvoice.setCreator(invoiceVary.getCreator());
		oriInvoice.setCreateTime(invoiceVary.getCreateTime());
		//oriInvoice.setContractNo(invoiceVary.getContractNo());
		
		return oriInvoice;
	}

	/**
	 * 更新费用信息
	 * @param contractsVery
	 */
	private void updateLastChargeInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			List<VehicleSaleContractChargeVary> chargeVaryList = saleContractsVaryDao.getChargeVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
			for (VehicleSaleContractChargeVary chargeVary : chargeVaryList) {
				if (STATUS_ABORT == chargeVary.getAbortStatus()) {
					VehicleSaleContractCharge oriCharge = dao.get(VehicleSaleContractCharge.class,
							chargeVary.getSaleContractChargeId());
					if (oriCharge != null) {
						dao.delete(oriCharge);
					}
				} else if (STATUS_NEW == chargeVary.getAbortStatus()) {
					VehicleSaleContractCharge oriCharge = new VehicleSaleContractCharge();
					FillContractCharge(oriCharge,chargeVary);
					oriCharge.setAbortStatus(STATUS_NEW);
					dao.save(oriCharge);
				}else if(STATUS_MODIFY == chargeVary.getAbortStatus()){
					VehicleSaleContractCharge oriCharge = dao.get(VehicleSaleContractCharge.class,
							chargeVary.getSaleContractChargeId());
					if(oriCharge!=null && (STATUS_ABORT!=Tools.toShort(oriCharge.getAbortStatus()))){
						FillContractCharge(oriCharge,chargeVary);
						dao.update(oriCharge);
					}
				}
			}

		}
	}
	

	/**
	 * 填充oriCharge
	 * @param oriCharge
	 * @param chargeVary
	 */
	private VehicleSaleContractCharge FillContractCharge(VehicleSaleContractCharge oriCharge, VehicleSaleContractChargeVary chargeVary) {
		oriCharge.setSaleContractChargeId(chargeVary.getSaleContractChargeId());
		oriCharge.setContractDetailId(chargeVary.getContractDetailId());
		oriCharge.setChargeId(chargeVary.getChargeId());
		oriCharge.setChargeName(chargeVary.getChargeName());
		oriCharge.setChargePf(Tools.toBigDecimal(chargeVary.getChargePf()));
		oriCharge.setChargeCost(Tools.toBigDecimal(chargeVary.getChargeCost()));
		oriCharge.setCostStatus(chargeVary.getCostStatus());
		oriCharge.setChargeComment(chargeVary.getChargeComment());
		oriCharge.setAbortStatus(chargeVary.getAbortStatus());
		oriCharge.setIncome(Tools.toBigDecimal(chargeVary.getIncome()));
		oriCharge.setRemark(chargeVary.getRemark());
		oriCharge.setPaidByBill(chargeVary.getPaidByBill());
		oriCharge.setContractNo(chargeVary.getContractNo());
		
		return oriCharge;
	}

	/**
	 * 更新改装信息
	 * @param contractsVery
	 */
	private void updateLastEpibolicInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			List<VehicleSaleContractItemVary> itemVaryList = saleContractsVaryDao.getItemVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
			for (VehicleSaleContractItemVary itemVary : itemVaryList) {
				if (STATUS_ABORT == itemVary.getAbortStatus()) {
					VehicleSaleContractItem oriItem = dao.get(VehicleSaleContractItem.class,itemVary.getSaleContractItemId());
					if (oriItem != null) {
						dao.delete(oriItem);
					}
				} else if (STATUS_NEW == itemVary.getAbortStatus()) {
					VehicleSaleContractItem oriItem = new VehicleSaleContractItem();
					FillContractItem(oriItem,itemVary);
					oriItem.setAbortStatus(STATUS_NEW);
					dao.save(oriItem);
				}else if(STATUS_MODIFY == itemVary.getAbortStatus()){
					VehicleSaleContractItem oriItem = dao.get(VehicleSaleContractItem.class,itemVary.getSaleContractItemId());
					if(oriItem!=null && (STATUS_ABORT!=Tools.toShort(oriItem.getAbortStatus()))){
						FillContractItem(oriItem,itemVary);
						dao.update(oriItem);
					}
				}
			}
		}
	}
		
	

	/**
	 * 根据 itemVary 填充 oriItem
	 * @param oriItem
	 * @param itemVary
	 */
	private VehicleSaleContractItem FillContractItem(VehicleSaleContractItem oriItem, VehicleSaleContractItemVary itemVary) {
		oriItem.setSaleContractItemId(itemVary.getSaleContractItemId());
		oriItem.setContractDetailId(itemVary.getContractDetailId());
		oriItem.setItemId(itemVary.getItemId());
		oriItem.setItemNo(itemVary.getItemNo());
		oriItem.setItemName(itemVary.getItemName());
		oriItem.setSupplierId(itemVary.getSupplierId());
		oriItem.setSupplierNo(itemVary.getSupplierNo());
		oriItem.setSupplierName(itemVary.getSupplierName());
		oriItem.setItemCost(Tools.toBigDecimal(itemVary.getItemCost()));
		oriItem.setComment(itemVary.getComment());
		oriItem.setAbortStatus(itemVary.getAbortStatus());
		oriItem.setIncome(Tools.toBigDecimal(itemVary.getIncome()));
		oriItem.setContractNo(itemVary.getContractNo());
		
		return oriItem;
	}

	/**
	 * 更新精品信息
	 * @param contractsVery
	 */
	private void updateLastPresentInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for(VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys){
			List<VehicleSaleContractPresentVary> presentVaryList = saleContractsVaryDao.getPresentVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
			for(VehicleSaleContractPresentVary presentVary:presentVaryList){
				if(STATUS_ABORT == presentVary.getAbortStatus()){
					VehicleSaleContractPresent oriPresent = dao.get(VehicleSaleContractPresent.class,presentVary.getSaleContractPresentId());
					if(oriPresent!=null){
						dao.delete(oriPresent);
					}
				}else if(STATUS_NEW == presentVary.getAbortStatus()){
					VehicleSaleContractPresent oriPresent  = new VehicleSaleContractPresent();
					FillContractPresent(oriPresent,presentVary);
					oriPresent.setAbortStatus(STATUS_NEW);
					dao.save(oriPresent);
				}else if(STATUS_MODIFY == presentVary.getAbortStatus()){
					VehicleSaleContractPresent oriPresent = dao.get(VehicleSaleContractPresent.class,presentVary.getSaleContractPresentId());
					if(oriPresent!=null && (Tools.toShort(oriPresent.getAbortStatus())!=STATUS_ABORT)){
						FillContractPresent(oriPresent,presentVary);
						dao.update(oriPresent);
					}
				}
			}
		}
	}

	/**
	 * 按照presentVary 填充 oriPresent
	 * @param oriPresent
	 * @param presentVary
	 */
	private VehicleSaleContractPresent FillContractPresent(VehicleSaleContractPresent oriPresent, VehicleSaleContractPresentVary presentVary) {
		oriPresent.setSaleContractPresentId(presentVary.getSaleContractPresentId());
		oriPresent.setContractDetailId(presentVary.getContractDetailId());
		oriPresent.setPlanQuantity(Tools.toBigDecimal(presentVary.getPlanQuantity()));
		oriPresent.setGetQuantity(Tools.toBigDecimal(presentVary.getGetQuantity()));
		oriPresent.setPosPrice(Tools.toBigDecimal(presentVary.getPosPrice()));
		oriPresent.setPosAgio(Tools.toBigDecimal(presentVary.getPosAgio()));
		oriPresent.setStockId(presentVary.getStockId());
		oriPresent.setDepositPosition(presentVary.getDepositPosition());
		oriPresent.setPartName(presentVary.getPartName());
		oriPresent.setPartNo(presentVary.getPartNo());
		oriPresent.setPartMnemonic(presentVary.getPartMnemonic());
		oriPresent.setProducingArea(presentVary.getProducingArea());
		oriPresent.setPartType(presentVary.getPartType());
		oriPresent.setSpecModel(presentVary.getSpecModel());
		oriPresent.setApplicableModel(presentVary.getApplicableModel());
		oriPresent.setUnit(presentVary.getUnit());
		oriPresent.setWarehouseName(presentVary.getWarehouseName());
		oriPresent.setCostRecord(Tools.toBigDecimal(presentVary.getCostRecord()));
		oriPresent.setCarriageRecord(Tools.toBigDecimal(presentVary.getCarriageRecord()));
		oriPresent.setAbortStatus(presentVary.getAbortStatus());
		oriPresent.setIncome(Tools.toBigDecimal(presentVary.getIncome()));
		oriPresent.setRemark(presentVary.getRemark());
		//oriPresent.setModifyTime(presentVary.getModifyTime());
		oriPresent.setContractNo(presentVary.getContractNo());
		
		return oriPresent;
	}

	/**
	 * 更新保险信息
	 * @param contractsVery
	 */
	@SuppressWarnings("unchecked")
	private void updateLastInsuranceInfo(VehicleSaleContractsVary contractsVery) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for(VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys){
			List<VehicleSaleContractInsuranceVary> insuranceVaryList = saleContractsVaryDao.getInsuranceVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
			for(VehicleSaleContractInsuranceVary insuranceVary:insuranceVaryList){
				if(STATUS_ABORT == insuranceVary.getAbortStatus()){
					VehicleSaleContractInsurance  oriInsurance =dao.get(VehicleSaleContractInsurance.class, insuranceVary.getSaleContractInsuranceId());
					if(oriInsurance!=null){
						dao.delete(oriInsurance);
					}
				}else if(STATUS_NEW == insuranceVary.getAbortStatus()){
					VehicleSaleContractInsurance  oriInsurance = new VehicleSaleContractInsurance();
					FillContractInsurance(oriInsurance,insuranceVary);
					oriInsurance.setAbortStatus(STATUS_NEW);
					dao.save(oriInsurance);
				}else if(STATUS_MODIFY == insuranceVary.getAbortStatus()){
					VehicleSaleContractInsurance  oriInsurance =dao.get(VehicleSaleContractInsurance.class, insuranceVary.getSaleContractInsuranceId());
					if(oriInsurance!=null && (Tools.toShort(oriInsurance.getAbortStatus())!=STATUS_ABORT)){
						FillContractInsurance(oriInsurance,insuranceVary);
						dao.update(oriInsurance);
						
					}
				}
				
				//刷新购买次数(insuranceYear,相同categoryId的数据从1开始 顺序向后+1)
				List<VehicleSaleContractInsurance> insuranceList = (List<VehicleSaleContractInsurance>) dao.findByHql("from VehicleSaleContractInsurance where contractDetailId = ? ", contractDetailVary.getContractDetailId());
				for(VehicleSaleContractInsurance insurance:insuranceList){
					List<VehicleSaleContractInsurance> sameCategoryInsuranceList =  (List<VehicleSaleContractInsurance>)dao.findByHql("from VehicleSaleContractInsurance where contractDetailId = ?  and categoryId =?", contractDetailVary.getContractDetailId(),insurance.getCategoryId());
					
					int n =1;
					for(VehicleSaleContractInsurance sameCategoryInsurance:sameCategoryInsuranceList){
						sameCategoryInsurance.setInsuranceYear(n);
						dao.update(sameCategoryInsurance);
						n++;
					}
					
				}
				
			}
			
		}
	}

	/**
	 * 根据insuranceVary 填充 oriInsurance
	 * @param oriInsurance
	 * @param insuranceVary
	 */
	private VehicleSaleContractInsurance FillContractInsurance(VehicleSaleContractInsurance oriInsurance, VehicleSaleContractInsuranceVary insuranceVary) {
		oriInsurance.setSaleContractInsuranceId(insuranceVary.getSaleContractInsuranceId());
		oriInsurance.setContractDetailId(insuranceVary.getContractDetailId());
		oriInsurance.setInsuranceYear(insuranceVary.getInsuranceYear());
		oriInsurance.setCategoryId(insuranceVary.getCategoryId());
		oriInsurance.setCategoryName(insuranceVary.getCategoryName());
		oriInsurance.setCategoryType(insuranceVary.getCategoryType());
		oriInsurance.setCategoryIncome(Tools.toBigDecimal(insuranceVary.getCategoryIncome()));
		oriInsurance.setCategoryScale(Tools.toBigDecimal(insuranceVary.getCategoryScale()));
		oriInsurance.setCategoryCost(Tools.toBigDecimal(insuranceVary.getCategoryCost()));
		oriInsurance.setCostStatus(insuranceVary.getCostStatus());
		oriInsurance.setAbortStatus(insuranceVary.getAbortStatus());
		oriInsurance.setRemark(insuranceVary.getRemark());
		oriInsurance.setSupplierId(insuranceVary.getSupplierId());
		oriInsurance.setPurchaseSort(insuranceVary.getPurchaseSort());
		oriInsurance.setIsFree(insuranceVary.getIsFree());
		oriInsurance.setContractNo(insuranceVary.getContractNo());
		
		return oriInsurance;
	}

	/**
     * &#x66f4;&#x65b0;&#x8f66;&#x8f86;&#x4fe1;&#x606f;
     *
     * @param contractsVery
     * @param oriContract
     */
	private Map<String, Short> updateLastVehicleInfo(VehicleSaleContractsVary contractsVery,VehicleSaleContracts oriContract) {
		SysUsers user = HttpSessionStore.getSessionUser();
		Map<String, Short> dict = new HashMap<String, Short>();
		
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());

		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			String vin = contractDetailVary.getVehicleVin();
			Short abortStatus = contractDetailVary.getAbortStatus();
			String contractDetailId = contractDetailVary.getContractDetailId();

			if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
				VehicleSaleContractDetail oriDetail = dao.get(VehicleSaleContractDetail.class,contractDetailVary.getContractDetailId());
				
				if(StringUtils.isNotEmpty(vin)){
					dict.put(vin, STATUS_ABORT);
				}
				
				if (oriDetail != null) {
					oriDetail.setAbortStatus(STATUS_ABORT);
					oriDetail.setApproveStatus((short) 30);
					oriDetail.setRealDeliverTime(null);
					oriContract.setContractQuantity(Tools.toInt(oriContract.getContractQuantity()) - 1);
				}
			} else if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
				
				if(StringUtils.isNotEmpty(vin)){
					dict.put(vin, STATUS_NEW);
				}
				
				VehicleSaleContractDetail oriDetail = new VehicleSaleContractDetail();
				// 根据VehicleSaleContractDetailVary 填充 VehicleSaleContractDetail
				FillVehicleSaleContractDetail(oriDetail, contractDetailVary,oriContract);
				
				oriDetail.setAbortStatus(STATUS_NEW);
				oriDetail.setApproveStatus((short) 1);
				oriDetail.setApproveName(user.getUserName());
				oriDetail.setApproverId(user.getUserId());
				oriDetail.setApproverNo(user.getUserNo());
				oriDetail.setApproveTime(new Timestamp(System.currentTimeMillis()));
				
				setSetFlowData(oriDetail,contractsVery);
				dao.save(oriDetail);

				oriContract.setContractQuantity(Tools.toInt(oriContract.getContractQuantity()) + 1);
			} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
				//List<VehicleSaleContractDetail> vehicleOriContractDetailList = saleContractsVaryDao.getVehicleOriContractDetailList(contractDetailVary.getVehicleVin());
				//如果换车
				if (!StringUtils.equals(contractDetailVary.getVehicleVin(), contractDetailVary.getOriVehicleVin())) {
					VehicleSaleContractDetail oriDetail = dao.get(VehicleSaleContractDetail.class,contractDetailVary.getContractDetailId());
					if(oriDetail!=null){
						FillVehicleSaleContractDetail(oriDetail, contractDetailVary,oriContract);
						
						//setSetFlowData(oriDetail,contractsVery);
						dao.update(oriDetail);
						
						//换车，则将原车辆放入dict 状态为 STATUS_ABORT，以便UpdateVehicleStockStaus中清除状态  20170302 caigx
						if(StringUtils.isNotEmpty(contractDetailVary.getOriVehicleVin())){
							dict.put(contractDetailVary.getOriVehicleVin(), STATUS_ABORT);
						}
						
						if(StringUtils.isNotEmpty(vin)){
							dict.put(vin, STATUS_NEW);
						}
					}
					
				} else {
					VehicleSaleContractDetail oriDetail = dao.get(VehicleSaleContractDetail.class,contractDetailVary.getContractDetailId());
					if (oriDetail != null) {
						FillVehicleSaleContractDetail(oriDetail, contractDetailVary,oriContract);
						
						//setSetFlowData(oriDetail,contractsVery);
						dao.update(oriDetail);
						
						if(StringUtils.isNotEmpty(vin)){
							dict.put(vin, STATUS_NEW);
							//dict.put(vin, STATUS_ABORT);
						}
					}
				}
			}
		}

		// 更新关联车辆
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
				if (StringUtils.isEmpty(contractDetailVary.getVehicleVin())) {
					VehicleSaleContractDetail oriDetail = dao.get(VehicleSaleContractDetail.class,
							contractDetailVary.getContractDetailId());
					if (oriDetail != null) {
						oriDetail.setRelationDetailId(null);
					}

					List<VehicleSaleContractDetail> oriDetailList = saleContractsVaryDao.getOriDetailListByRelation(contractDetailVary.getContractDetailId());
					if (oriDetailList != null && oriDetailList.size() > 0) {
						oriDetailList.get(0).setRelationDetailId(null);
					}
				}
				continue;
			}

			if (StringUtils.isNotEmpty(contractDetailVary.getRelationDetailId())) {
				VehicleSaleContractDetail oriDetail = dao.get(VehicleSaleContractDetail.class,
						contractDetailVary.getRelationDetailId());
				if (oriDetail != null) {
					oriDetail.setRelationDetailId(contractDetailVary.getContractDetailId());
				}
			} else {
				List<VehicleSaleContractDetail> oriDetailList = saleContractsVaryDao.getOriDetailListByRelation(contractDetailVary.getContractDetailId());
				if (oriDetailList != null && oriDetailList.size() > 0) {
					oriDetailList.get(0).setRelationDetailId(null);
				}
			}
		}

		return dict;
	}
	
	/**
	 * 设置明细中与审批流程相关的字段的值
	 * @param oriDetail
	 * @param contractsVery
	 */
	private VehicleSaleContractDetail setSetFlowData(VehicleSaleContractDetail oriDetail, VehicleSaleContractsVary contractsVery) {
		if(StringUtils.isEmpty(oriDetail.getDocumentNo())){
			SysUsers user = HttpSessionStore.getSessionUser();
			// 获得SVD_NO的编码
			String code = sysCodeService.createSysCodeRules("SVD_NO", contractsVery.getStationId());
			List<Map<String,Object>> userList = saleContractsVaryDao.getUserByCreator(contractsVery.getCreator());
			if(userList==null ||userList.size()==0){
				return oriDetail;
			}
			oriDetail.setDocumentNo(code);
			oriDetail.setStatus((short)50); //已同意
			oriDetail.setUserId(userList.get(0).get("user_id") == null?null:userList.get(0).get("user_id").toString());
			oriDetail.setUserNo( userList.get(0).get("user_no") == null?null:userList.get(0).get("user_no").toString());
			oriDetail.setUserName( userList.get(0).get("user_name") == null?null:userList.get(0).get("user_name").toString()); 
			oriDetail.setDepartmentId(userList.get(0).get("unit_id") == null?null:userList.get(0).get("unit_id").toString());
			oriDetail.setDepartmentNo(userList.get(0).get("unit_no") == null?null:userList.get(0).get("unit_no").toString());
			oriDetail.setDepartmentName(userList.get(0).get("unit_name") == null?null:userList.get(0).get("unit_name").toString());
			oriDetail.setSubmitStationId(contractsVery.getStationId());
			oriDetail.setApproverId(user.getUserId());
			oriDetail.setApproverNo(user.getUserNo());
			oriDetail.setApproverName(user.getUserName());
		}
		return oriDetail;
		
	}

	/**
	 * 处理费用单据分录
	 * @param contractsVery
	 * @param contract
	 */
	@SuppressWarnings("unchecked")
	private void dealChargeDocument(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		SysUsers user = HttpSessionStore.getSessionUser();
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
				List<VehicleSaleContractChargeVary> chargeVaryList = (List<VehicleSaleContractChargeVary>) dao.findByHql("from VehicleSaleContractChargeVary where detailVaryId = ?  AND paidByBill = true",
								contractDetailVary.getDetailVaryId());
				for (VehicleSaleContractChargeVary chargeVary : chargeVaryList) {
					double mChargeAmount = Tools.toDouble(chargeVary.getChargePf());
					String sSummary = chargeVary.getRemark();

					if (mChargeAmount == 0.00D) {
						continue;
					}

					financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 19, (short) 15, "车辆-"
							+ chargeVary.getChargeName(), chargeVary.getSaleContractChargeId(),
							contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(), (short) 20,
							mChargeAmount, null, sSummary,
							contractDetailVary.getVehicleVin() + "," + contract.getContractNo(),
							contract.getContractNo(), 1, 0.00D, null, user.getUserId(), user.getUserNo(),
							user.getUserName(), user.getDepartment(), user.getDepartmentNo(), user.getDepartmentName());
				}
			} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
				List<VehicleSaleContractChargeVary> chargeVaryList = (List<VehicleSaleContractChargeVary>) dao.findByHql("from VehicleSaleContractChargeVary where detailVaryId = ? ",
								contractDetailVary.getDetailVaryId());
				for (VehicleSaleContractChargeVary chargeVary : chargeVaryList) {
					double mChargeAmount = Tools.toDouble(chargeVary.getChargePf());
					String sSummary = chargeVary.getRemark();
					if (STATUS_NEW == chargeVary.getAbortStatus()) {// 新增
						if (!Tools.toBoolean(chargeVary.getPaidByBill())) {// 非凭单支付
							continue;
						}
						financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 19, (short) 15, "车辆-"
								+ chargeVary.getChargeName(), chargeVary.getSaleContractChargeId(),
								contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(),
								(short) 20, mChargeAmount, null, sSummary, contractDetailVary.getVehicleVin() + ","
										+ contract.getContractNo(), contract.getContractNo(), 1, 0.00D, null,
								user.getUserId(), user.getUserNo(), user.getUserName(), user.getDepartment(),
								user.getDepartmentNo(), user.getDepartmentName());
					} else if (STATUS_ABORT == chargeVary.getAbortStatus()) {
						if (!Tools.toBoolean(chargeVary.getPaidByBill())) {// 非凭单支付
							continue;
						}

						List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
								"from FinanceDocumentEntries  where documentId = ?",
								chargeVary.getSaleContractChargeId());
						if (entryList != null && entryList.size() > 0) {
							if (StringUtils.isNotEmpty(entryList.get(0).getAfterNo())) {
								throw new ServiceException(String.format("单据[%s]已经收款，不能终止，请冲红该费用收款后再试", entryList
										.get(0).getDocumentType()));
							}

							dao.delete(entryList.get(0));
						}
					} else if (STATUS_MODIFY == chargeVary.getAbortStatus()) {
						if (Tools.toBoolean(chargeVary.getPaidByBill()) != Tools.toBoolean(chargeVary
								.getOriPaidByBill())) {// 有变更凭单支付
							if (Tools.toBoolean(chargeVary.getPaidByBill())) {// 由非凭单变为凭单则新增单据分录
								if (mChargeAmount == 0.00D)
									continue;
								financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 19, (short) 15,
										"车辆-" + chargeVary.getChargeName(), chargeVary.getSaleContractChargeId(),
										contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(),
										(short) 20, mChargeAmount, null, sSummary, contractDetailVary.getVehicleVin()
												+ "," + contract.getContractNo(), contract.getContractNo(), 1, 0.00D,
										null, user.getUserId(), user.getUserNo(), user.getUserName(),
										user.getDepartment(), user.getDepartmentNo(), user.getDepartmentName());

							} else {// 由凭单变为非凭单 删除单据分录
								List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
										"from FinanceDocumentEntries  where documentId = ?",
										chargeVary.getSaleContractChargeId());
								if (entryList != null && entryList.size() > 0) {
									if (StringUtils.isNotEmpty(entryList.get(0).getAfterNo())) {
										throw new ServiceException(String.format("单据[%s]已经收款，不能终止，请冲红该费用收款后再试",
												entryList.get(0).getDocumentType()));
									}

									dao.delete(entryList.get(0));
								}

							}

						} else if (Tools.toBoolean(chargeVary.getPaidByBill())) {// 未变更凭单支付,且当前项目是凭单支付
							List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql("from FinanceDocumentEntries  where documentId = ?",
									chargeVary.getSaleContractChargeId());
							if(entryList!=null && entryList.size()>0){ //已产生的则修改
								FinanceDocumentEntries entry = entryList.get(0);
								 double dDocumentAmount =Tools.toDouble(entry.getDocumentAmount());
								 double dLeftAmount = Tools.toDouble(entry.getLeftAmount());
                                 double dUsedCredit = Tools.toDouble(entry.getUsedCredit());
                                 double dPaid = dDocumentAmount - dLeftAmount + dUsedCredit;
                                 if (dPaid > dDocumentAmount)
                                 {
                                     dPaid = dDocumentAmount;
                                 }

                                 if (mChargeAmount < dPaid)
                                 {
                                	 throw new ServiceException(String.format("单据[%s]已经收款,修改后的费用金额%f不能小于已收金额%f",
                                			 entry.getDocumentType(),mChargeAmount,dPaid));
                                 }
                                 entry.setDocumentAmount(mChargeAmount);
                                 entry.setLeftAmount( dLeftAmount + (mChargeAmount - dDocumentAmount));
                                 dao.update(entry);
							}else{//如果原来没产生则重新产生
								   if (mChargeAmount == 0)
                                       continue;
								   
								   financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 19, (short) 15,
											"车辆-" + chargeVary.getChargeName(), chargeVary.getSaleContractChargeId(),
											contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(),
											(short) 20, mChargeAmount, null, sSummary, contractDetailVary.getVehicleVin()
													+ "," + contract.getContractNo(), contract.getContractNo(), 1, 0.00D,
											null, user.getUserId(), user.getUserNo(), user.getUserName(),
											user.getDepartment(), user.getDepartmentNo(), user.getDepartmentName());
							}
							
						}
					}
				}
				
				if(!StringUtils.equals(contractDetailVary.getVehicleVin(), contractDetailVary.getOriVehicleVin())){//如果有修改VIN
					
					List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql("from FinanceDocumentEntries where documentId IN (select saleContractChargeId from VehicleSaleContractCharge where contractDetailId = ? and paidByBill = true)  and documentId not in (select saleContractChargeId  from VehicleSaleContractChargeVary where detailVaryId = ? )", contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
					for(FinanceDocumentEntries entry:entryList){
						if(StringUtils.isNotEmpty(entry.getAfterNo())){
							 throw new ServiceException(String.format("单据[%s]已经收款,不能变更车辆VIN，请冲红该费用收款后再试",
                        			 entry.getDocumentType()));
						}
						
						 //找出原车辆VIN和新车辆VIN
                        String sVinOri = "";//旧
                        String sVinCur = "";//新
                        String sDocumentNo = entry.getDocumentNo();
                        String sDetailId = "";
                        VehicleSaleContractCharge charge = dao.get(VehicleSaleContractCharge.class, entry.getDocumentId());
                        if(charge!=null){
                        	sDetailId = charge.getContractDetailId();
                        }
                        List<VehicleSaleContractDetailVary> vehicleVaryList = (List<VehicleSaleContractDetailVary>) dao.findByHql("from VehicleSaleContractDetailVary where contractDetailId = ?", sDetailId);
                        if(vehicleVaryList!=null && vehicleVaryList.size()>0){
                        	sVinOri = vehicleVaryList.get(0).getOriVehicleVin();
                        	sVinCur = vehicleVaryList.get(0).getVehicleVin();
                        }
                        
                        if (StringUtils.isEmpty(sVinOri))
                        {
                            sDocumentNo = sVinCur + sDocumentNo;
                        }
                        else
                        {
                            sDocumentNo = sDocumentNo.replace(sVinOri, sVinOri);
                        }
                        
                        FinanceDocumentEntries newEntry = copyEntry(entry);
                        dao.delete(entry);
                        dao.flush();
                        //entry.setEntryId(UUID.randomUUID().toString());
                        newEntry.setDocumentId(sDocumentNo);
                        dao.update(newEntry);
					}
					
				}
				
			}else if(contractDetailVary.getAbortStatus()!=null && STATUS_ABORT == contractDetailVary.getAbortStatus()){//终止
				
				List<VehicleSaleContractChargeVary> chargeVaryList = (List<VehicleSaleContractChargeVary>) dao.findByHql("from VehicleSaleContractChargeVary where detailVaryId = ?  AND paidByBill = true",
						contractDetailVary.getDetailVaryId());
				for(VehicleSaleContractChargeVary chargeVary:chargeVaryList ){
					List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql("from FinanceDocumentEntries  where documentId = ?",
							chargeVary.getSaleContractChargeId());
					if(entryList!=null && entryList.size()>0){
						FinanceDocumentEntries entry = entryList.get(0);
						if(StringUtils.isNotEmpty(entry.getAfterNo())){
							 throw new ServiceException(String.format("单据[%s]已经收款，不能终止，请冲红该费用收款后再试", entry.getDocumentType()));
						}
						dao.delete(entry);
					}
				}
			}
		}
	}

	/**
	 * 更新预算单
	 * @param contractsVery
	 * @param contract
	 */
	private void updateBudget(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for(VehicleSaleContractDetailVary contractDetailVary:contractDetailVarys){
			if(STATUS_MODIFY!=contractDetailVary.getAbortStatus()){
				continue;
			}
			if(!StringUtils.equals(contractDetailVary.getVehicleVin(), contractDetailVary.getOriVehicleVin())
					||!StringUtils.equals(contractDetailVary.getVnoId(), contractDetailVary.getOriVnoId())
					||Tools.toDouble(contractDetailVary.getVehiclePriceTotal())!=Tools.toDouble(contractDetailVary.getOriVehiclePriceTotal())
					||!StringUtils.equals(contractDetailVary.getOriVnoIdNew(), contractDetailVary.getVnoIdNew())
					||!StringUtils.equals(contractDetailVary.getOriVehicleVnoNew(), contractDetailVary.getVehicleVnoNew()) ){
				List<Map<String,Object>>budgetDetailList = saleContractsVaryDao.getBudgetDetailList(contractDetailVary.getContractDetailId());
				for(Map<String,Object> budgetDetail:budgetDetailList){
					if(!StringUtils.equals(contractDetailVary.getVehicleVin(), budgetDetail.get("vehicle_vin")+"")){
						List<Map<String,Object>>budgetChargeList = saleContractsVaryDao.getBudgetChargeList(contractDetailVary.getContractDetailId());
						for(Map<String,Object>budgetCharge:budgetChargeList ){
							boolean is_reimbursed = false;
							if(budgetCharge.get("is_reimbursed")!=null && StringUtils.isEmpty(budgetCharge.get("is_reimbursed").toString())){
								is_reimbursed = Tools.toBoolean((Boolean) budgetCharge.get("is_reimbursed"));
								if(is_reimbursed){
									throw new ServiceException(String.format("车辆 VIN：%s，对应的预算费用，已经做了报销处理，不能变更VIN",
											contractDetailVary.getVehicleVin()));
								}
							}
						}
						
						//判断能否更新
						List<Map<String,Object>> documentEntriesByBudgetChargeList = saleContractsVaryDao.getDocumentEntriesByBudgetCharge(contractDetailVary.getContractDetailId());
						for(Map<String,Object> documentEntriesByBudgetCharge:documentEntriesByBudgetChargeList){
							 boolean bPaid = false;//是否做过收款
							if(documentEntriesByBudgetCharge.get("after_no")!=null &&StringUtils.isNotEmpty(documentEntriesByBudgetCharge.get("after_no").toString())){
								if(!(StringUtils.isEmpty(contractDetailVary.getOriVehicleVin())&&StringUtils.isNotEmpty(contractDetailVary.getVehicleVin()))){//如果原来VIN为空，再选择VIN时，就算收过款也不提示
									throw new ServiceException(String.format("车辆 VIN：%s，对应的预算费用，财务已经做了后续处理，不能变更VIN",
											contractDetailVary.getVehicleVin()));
								}
								
								 bPaid = true;
							}
							
							List<Map<String,Object>>budgetChargeList2 = saleContractsVaryDao.getBudgetChargeListBySelfId(contractDetailVary.getContractDetailId(),documentEntriesByBudgetCharge.get("document_id")+"");

							
							if(budgetChargeList2!=null&&budgetChargeList2.size()>0){
								String sDocNo = budgetChargeList2.get(0).get("document_no")+ "," + budgetChargeList2.get(0).get("sale_contract_no") + "," + budgetChargeList2.get(0).get("vehicle_vin");
								if(bPaid){
									List<Map<String,Object>>settlementsDetailList = saleContractsVaryDao.getSettlementsDetailList(documentEntriesByBudgetCharge.get("entry_id")+"");
									for(Map<String,Object> settlementsDetail:settlementsDetailList){
										FinanceSettlementsDetails detail = dao.get(FinanceSettlementsDetails.class, settlementsDetail.get("fsd_id")+"");
										detail.setDocumentNo(sDocNo);
										dao.update(detail);
									}
									FinanceDocumentEntries financeEntry = dao.get(FinanceDocumentEntries.class, documentEntriesByBudgetCharge.get("entry_id")+"");
									if(financeEntry!=null){
										financeEntry.setDocumentNo(sDocNo);
										financeEntry.setSubDocumentNo(sDocNo);
										dao.update(financeEntry);
									}
								}else{
									FinanceDocumentEntries financeEntry = dao.get(FinanceDocumentEntries.class, documentEntriesByBudgetCharge.get("entry_id")+"");
									if(financeEntry!=null){
										FinanceDocumentEntries newEntry = copyEntry(financeEntry);
										dao.delete(financeEntry);
										dao.flush();
										newEntry.setDocumentNo(sDocNo);
										newEntry.setSubDocumentNo(sDocNo);
										dao.update(newEntry);
									}
									
								}
							}
						}
						contractDetailVary.setVehicleVin(budgetDetail.get("vehicle_vin") ==null?null: budgetDetail.get("vehicle_vin").toString());
						dao.update(contractDetailVary);
					}
						
						//ADM17060020 
						VehicleLoanBudgetDetails detail = dao.get(VehicleLoanBudgetDetails.class, budgetDetail.get("self_id")+"");
						if(detail!=null && (!StringUtils.equals(contractDetailVary.getVnoId(), detail.getVnoId())
								||Tools.toDouble(contractDetailVary.getVehiclePriceTotal())!=Tools.toDouble(detail.getVehiclePriceTotal())
								||!StringUtils.equals(contractDetailVary.getVnoIdNew(), detail.getBulletinId())
								||!StringUtils.equals(contractDetailVary.getVehicleVnoNew(), detail.getBulletinNo()) )){
							detail.setVehiclePriceTotal(contractDetailVary.getVehiclePriceTotal());
							detail.setVnoId(contractDetailVary.getVnoId());
							detail.setBulletinId(contractDetailVary.getVnoIdNew());
							detail.setBulletinNo(contractDetailVary.getVehicleVnoNew());
							detail.setVehicleVin(contractDetailVary.getVehicleVin());
							//更新VehicleLoanBudget 的modiftTime,让手机的 消贷费用预算 数据能刷新
							VehicleLoanBudget budget = dao.get(VehicleLoanBudget.class, detail.getDocumentNo());
							budget.setModifyTime(new Timestamp(System.currentTimeMillis()));
							dao.update(budget);
							
							dao.update(detail);
						}
					}
				}
		}
	}

	/**
	 * 更新车辆相关费用
	 * 
	 * @param contractsVery
	 * @param contract
	 */
	@SuppressWarnings("unchecked")
	private void updateVehicleCharge(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) dao.findByHql(
				"from VehicleSaleContractDetail where contractNo = ?", contract.getContractNo());
		for (VehicleSaleContractDetail detail : detailList) {
			if(detail.getAbortStatus()==null){ //Status为空，不处理
				continue;
			}
			String sVIN = detail.getVehicleVin();
			String sSalesCode = detail.getVehicleSalesCode();
			String sContractNo = contract.getContractNo();
			String sCustomerID = contract.getCustomerId();
			String sCustomerNo = contract.getCustomerNo();
			String sCustomerName = contract.getCustomerName();
			String sStationId = contract.getStationId();

			Timestamp dtRP = contract.getPlanFinishTime();

			// 如果客户的赠送金额不为0，则产生应付的单据分录
			double dLargessPrice = Tools.toDouble(detail.getLargessAmount());
			double dLargessPart = Tools.toDouble(detail.getLargessPart());
			double dLargessService = Tools.toDouble(detail.getLargessService());
			double dPriceProfit = Tools.toDouble(detail.getVehicleProfit());
			List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
					"from FinanceDocumentEntries where documentId = ? and documentType = '车辆-客户佣金'",
					detail.getContractDetailId());
			if (detail.getAbortStatus()!=null && STATUS_ABORT == detail.getAbortStatus()) {

				if (entryList != null && entryList.size() > 0) {
					dao.delete(entryList.get(0));
				}
			} else {
				if (dPriceProfit > 0) {
					String sCId = StringUtils.isNotEmpty(detail.getCustomerIdProfit()) ? detail.getCustomerIdProfit()
							: contract.getCustomerId();
					String sCNo = StringUtils.isNotEmpty(detail.getCustomerIdProfit()) ? detail.getCustomerNoProfit()
							: contract.getCustomerNo();
					String sCName = StringUtils.isNotEmpty(detail.getCustomerIdProfit()) ? detail
							.getCustomerNameProfit() : contract.getCustomerName();
					if (entryList == null || entryList.size() == 0) {
						financeDocumentEntriesDao.insertEntryEx(contractsVery.getStationId(), 1, (short) 65, "车辆-客户佣金",
								detail.getContractDetailId(), sCId, sCNo, sCName, (short) 70, dPriceProfit,
								detail.getVehicleVin() + "," + contractsVery.getContractNo(), null, dtRP);
					} else {
						FinanceDocumentEntries entry = entryList.get(0);
						if (StringUtils.isEmpty(entry.getAfterNo())) {
							if (!sCId.equals(entry.getObjectId()) || dPriceProfit != Tools.toDouble(entry.getDocumentAmount())) {
								FinanceDocumentEntries newEntry = copyEntry(entry);
								dao.delete(entry);
								dao.flush();
								//entry.setEntryId(UUID.randomUUID().toString());
								newEntry.setDocumentNo(detail.getVehicleVin() + "," + contractsVery.getContractNo());
								newEntry.setObjectId(sCId);
								newEntry.setObjectNo(sCNo);
								newEntry.setObjectName(sCName);
								newEntry.setLeftAmount(dPriceProfit);
								newEntry.setDocumentAmount(dPriceProfit);
								dao.update(newEntry);

							} else {
								entry.setDocumentNo(detail.getVehicleVin() + "," + contractsVery.getContractNo());
								entry.setObjectId(sCId);
								entry.setObjectNo(sCNo);
								entry.setObjectName(sCName);
								entry.setLeftAmount(dPriceProfit);
								entry.setDocumentAmount(dPriceProfit);
								dao.update(entry);
							}

						}

					}
				} else {
					if (entryList != null && entryList.size() > 0) {
						dao.delete(entryList.get(0));
					}
				}
			}
		}

	}

	

	

	/**
	 * 处理车辆购置税
	 * 
	 * @param contractsVery
	 * @param contractDetailVarys
	 */
	private void dealPurchaseTax(List<VehicleSaleContractDetailVary> contractDetailVarys) {

		List<String> taxNameList = baseOtherDao.getDataByTypeNo("vehicle_purchase_tax_name");
		if (taxNameList == null || taxNameList.size() == 0) {
			return;
		}
		String sPurchaseTaxName = taxNameList.get(0);
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
				continue;
			}
			List<VehicleInvoices> oirInvoiceList = saleContractsVaryDao.getVehicleInvoiceList(contractDetailVary
					.getContractDetailId());
			List<VehicleInvoicesVary> invoiceList = saleContractsVaryDao.getVehicleInvoiceVaryList(contractDetailVary
					.getContractDetailId());

			// 如果变更单中有非终止的购车发票以变更单的为主，否则以变更前的购车发票为主，都没有则跳出
			double invoiceAmount = 0.00D;
			if (invoiceList != null && invoiceList.size() > 0) {
				invoiceAmount = Tools.toDouble(invoiceList.get(0).getInvoiceAmount());
			} else {
				if (oirInvoiceList != null && oirInvoiceList.size() > 0) {
					invoiceAmount = Tools.toDouble(oirInvoiceList.get(0).getInvoiceAmount());
				} else {
					continue;
				}
			}
			if (invoiceAmount <= 0) {
				continue;
			}
			double mIncome = calcPurchaseTax(invoiceAmount);

			List<String> lstCC = new ArrayList<String>();
			List<VehicleSaleContractChargeVary> chargeVaryList = saleContractsVaryDao.getChargeVaryListByChargeName(
					contractDetailVary.getContractDetailId(), sPurchaseTaxName);
			for (VehicleSaleContractChargeVary chargeVary : chargeVaryList) {
				chargeVary.setIncome(mIncome);
				chargeVary.setChargePf(mIncome);
				dao.update(chargeVary);
				lstCC.add(chargeVary.getSaleContractChargeId());
			}
			List<VehicleSaleContractCharge> oriChargeList = saleContractsVaryDao.getOriChargeListByChargeName(
					contractDetailVary.getContractDetailId(), sPurchaseTaxName);
			for (VehicleSaleContractCharge oriCharge : oriChargeList) {
				if (!lstCC.contains(oriCharge.getSaleContractChargeId())) {
					oriCharge.setIncome(Tools.toBigDecimal(mIncome));
					oriCharge.setChargePf(Tools.toBigDecimal(mIncome));
					dao.update(oriCharge);
				}
			}
		}

	}

	/**
	 * 处理合同状态
	 * 
	 * @param contractsVery
	 * @param contractDetailVarys
	 * @param contract
	 */
	@SuppressWarnings("unchecked")
	private void updateContractStatus(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		// 如果全部明细都是终止则终止原销售合同
		//查询增加documentNo限制-20170511
		List<VehicleSaleContractDetailVary> detailVarysNotAbort = (List<VehicleSaleContractDetailVary>) dao.findByHql(
				"from VehicleSaleContractDetailVary as a  where  isNull(abortStatus ,20)<>10 and contractNo = ?  and documentNo= ?",
				contractsVery.getContractNo(), contractsVery.getDocumentNo());
		//查询增加documentNo限制-20170511
		List<VehicleSaleContractDetail> detailNotInVary = (List<VehicleSaleContractDetail>) dao
				.findByHql(
						"from VehicleSaleContractDetail as a  where a.contractNo = ? and not exists (select 1 from VehicleSaleContractDetailVary as b  where a.contractDetailId = b.contractDetailId  and b.documentNo = ? and  isNull(b.abortStatus ,20)=10)",
						contract.getContractNo(),contractsVery.getDocumentNo());
		if (detailVarysNotAbort.size() == 0 && detailNotInVary.size() == 0) {
			contract.setContractStatus((short)4);
			dao.update(contract);
			return;
		}

		if (Tools.toInt(contract.getContractQuantity()) == Tools.toInt(contract.getArrivedQuantity())) {
			contract.setContractStatus((short)2);
		} else if (Tools.toInt(contract.getContractQuantity()) > Tools.toInt(contract.getArrivedQuantity())) {
			if (Tools.toInt(contract.getArrivedQuantity()) > 0) {
				contract.setContractStatus((short)1);
			}
		} else if (Tools.toInt(contract.getArrivedQuantity()) == 0) {
			contract.setContractStatus((short)0);
		}
		dao.update(contract);
	}

	/**
	 * 导出到客户及车辆档案
	 * 
	 * @param contractsVery
	 * @param contractDetailVarys
	 */
	@SuppressWarnings("unchecked")
	private void exportArchives(VehicleSaleContractsVary contractsVery,
			List<VehicleSaleContractDetailVary> contractDetailVarys, VehicleSaleContracts contract) {
		if (StringUtils.isEmpty(contract.getCustomerId())) {
			throw new ServiceException("导出客户档案失败:合同上的客户ID不能为空");
		}
		BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
		if (relObj == null) {
			throw new ServiceException("导出客户档案失败:档案中的客户ID不存在");
		} else {
			if (Tools.toShort(relObj.getStatus()) <= 0) {
				throw new ServiceException("导出客户档案失败:档案中的客户已被禁用");
			}
		}
		SysUsers user = HttpSessionStore.getSessionUser();
		// 更新客户档案
		if (StringUtils.isEmpty(relObj.getStationId())) {
			relObj.setStationId(user.getDefaulStationId());
		}
		relObj.setObjectName(contract.getCustomerName());
		relObj.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
		relObj.setSex(contract.getCusotmerSex());
		relObj.setCertificateType(contract.getCustomerCertificateType());
		relObj.setCertificateNo(contract.getCustomerCertificateNo());
		relObj.setLinkman(contract.getLinkman());
		relObj.setPhone(contract.getCustomerPhone());
		relObj.setMobile(contract.getCustomerMobile());
		relObj.setEmail(contract.getCustomerEmail());
		relObj.setAddress(contract.getCustomerAddress());
		relObj.setPostalcode(contract.getCustomerPostcode());
		relObj.setEducation(contract.getCustomerEducation());
		relObj.setOccupation(contract.getCustomerOccupation());
		relObj.setProvince(contract.getCustomerProvince());
		relObj.setCity(contract.getCustomerCity());
		relObj.setArea(contract.getCustomerArea());
		int nKind = Tools.toInt(relObj.getObjectKind());
		if ((nKind & 1) != 1) {
			nKind = nKind + 1;
		}
		if ((nKind & 2) != 2) {
			nKind = nKind + 2;
		}
		if ((nKind & 4) != 4) {
			nKind = nKind + 4;
		}

		relObj.setObjectKind(nKind);
		relObj.setPlanBackTime(new Timestamp(System.currentTimeMillis()));
		relObj.setBackFlag(true);
		// relObj.setCustomerType((short) 30);
		relObj.setModifier(user.getUserName());
		relObj.setModifyTime(new Timestamp(System.currentTimeMillis()));
		dao.update(relObj);

		// 更新车辆档案，分为遍历更新和当前条更新两种情况
		for (VehicleSaleContractDetailVary contractDetailVary : contractDetailVarys) {
			//如果终止或未选vin不处理档案
			if (contractDetailVary.getAbortStatus()!=null && STATUS_ABORT == contractDetailVary.getAbortStatus()) {
				clearVehicleArchivesCustomerId(contractDetailVary.getVehicleId());
				continue;
			}
			if(StringUtils.isEmpty(contractDetailVary.getVehicleId())){
				clearVehicleArchivesCustomerId(contractDetailVary.getOriVehicleId());
				continue;
			}
			
			String vid = StringUtils.isEmpty(contractDetailVary.getVehicleId()) ? "" : contractDetailVary
					.getVehicleId();
			String vin = StringUtils.isEmpty(contractDetailVary.getVehicleVin()) ? "" : contractDetailVary
					.getVehicleVin();
			List<VehicleArchives> vehicleArchiveList = (List<VehicleArchives>) dao.findByHql(
					"from VehicleArchives where vehicleId = ? OR vehicleVin = ? ", vid, vin);
			VehicleArchives vehicleArchive = null;
			if (vehicleArchiveList != null && vehicleArchiveList.size() > 0) {
				vehicleArchive = vehicleArchiveList.get(0);
			}
			if (vehicleArchive == null) {
				vehicleArchive = new VehicleArchives();
				vehicleArchive.setVehicleId(contractDetailVary.getVehicleId());
			}

			if (StringUtils.isEmpty(vehicleArchive.getStationId())) {
				vehicleArchive.setStationId(user.getDefaulStationId());
			}
			vehicleArchive.setCustomerId(contract.getCustomerId());
			vehicleArchive.setVehicleLinkman(contract.getLinkman());
			vehicleArchive.setVehicleLinkmanPhone(contract.getCustomerPhone());

			vehicleArchive.setVehicleLinkmanMobile(contract.getCustomerMobile());
			vehicleArchive.setVehicleLinkmanAddress(contract.getCustomerAddress());
			vehicleArchive.setSeller(contract.getSeller());
			vehicleArchive.setCreator(contract.getCreator());
			vehicleArchive.setCreateTime(contract.getCreateTime());
			vehicleArchive.setVehicleVno(contractDetailVary.getVehicleVno());
			vehicleArchive.setVehicleCardModel(contractDetailVary.getVehicleVnoNew());
			if (StringUtils.isEmpty(contractDetailVary.getVehicleVinNew())) {
				vehicleArchive.setVehicleVin(contractDetailVary.getVehicleVin());
			} else {
				vehicleArchive.setVehicleVin(contractDetailVary.getVehicleVinNew());
			}
			vehicleArchive.setVnoId(contractDetailVary.getVnoId());
			vehicleArchive.setVehicleSalesCode(contractDetailVary.getVehicleSalesCode());
			vehicleArchive.setVehicleName(contractDetailVary.getVehicleName());
			vehicleArchive.setVehicleStrain(contractDetailVary.getVehicleStrain());
			vehicleArchive.setVehicleColor(contractDetailVary.getVehicleColor());
			vehicleArchive.setVehicleEngineType(contractDetailVary.getVehicleEngineType());
			vehicleArchive.setVehicleEngineNo(contractDetailVary.getVehicleEngineNo());
			vehicleArchive
					.setVehicleEligibleNo(StringUtils.isEmpty(contractDetailVary.getVehicleEligibleNoNew()) ? contractDetailVary
							.getVehicleEligibleNo() : contractDetailVary.getVehicleEligibleNoNew());

			vehicleArchive.setVehicleOutFactoryTime(contractDetailVary.getVehicleOutFactoryTime());
			vehicleArchive.setVehiclePurchaseTime(contractDetailVary.getRealDeliverTime());
			vehicleArchive.setVehicleCardNo(contractDetailVary.getVehicleCardNo());
			vehicleArchive.setVehiclePrice(contractDetailVary.getVehiclePrice());

			vehicleArchive.setVehiclePurchaseFlag(true);
			vehicleArchive.setMaintainRemindFlag(true);
			vehicleArchive.setVehicleBelongTo(false);
			vehicleArchive.setStatus((short) 1);
			vehicleArchive.setDriveRoomNo(contractDetailVary.getDriveRoomNo());
			vehicleArchive.setModifier(user.getUserFullName());
			vehicleArchive.setModifyTime(new Timestamp(System.currentTimeMillis()));
			List<String> lst = getVehicleInvoice(contractDetailVary);
			if (lst.size() > 0) {
				vehicleArchive.setBelongToSupplierId(lst.get(0));
				vehicleArchive.setBelongToSupplierNo(lst.get(1));
				vehicleArchive.setBelongToSupplierName(lst.get(2));
			} else {
				vehicleArchive.setBelongToSupplierId(null);
				vehicleArchive.setBelongToSupplierNo(null);
				vehicleArchive.setBelongToSupplierName(null);
			}
			vehicleArchive.setBackAllow(1023);
			dao.update(vehicleArchive);

			// 验证是否换车
			if (contractDetailVary != null && StringUtils.isNotEmpty(contractDetailVary.getOriVehicleId()) && !contractDetailVary.getOriVehicleId().equals(contractDetailVary.getVehicleId())) {
				clearVehicleArchivesCustomerId(contractDetailVary.getOriVehicleId());
			}
		}

	}
	
	/**
	 *  setCustomerId(null)
	 * @param vehicleId
	 */
	void clearVehicleArchivesCustomerId(String vehicleId){
		if(StringUtils.isEmpty(vehicleId)){
			return;
		}
		VehicleArchives archives = dao.get(VehicleArchives.class, vehicleId);
		if (archives != null) {
			archives.setCustomerId(null);
			dao.update(archives);// 如果是换车，则清掉customerId
		}
	}
	

	/**
	 * 处理单据分录
	 * 
	 * @param contractsVery
	 * @param contract
	 */
	private void dealBillDocment(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		dealInvoice(contractsVery, contract);// 处理发票

		// 变更后的贷款
		double dLoanAmountTotal = getNewLoanAmount(contract);
		Timestamp dtRP = getMaxPlanDeliverTime(contract);

		// 变更后的合同金额
		double dContractMoney = Tools.toDouble(contract.getContractMoney());
		double dContractMoneyOri = Tools.toDouble(contractsVery.getOriContractMoney());

		// 当变更后的合同金额与原合同金额相同时不作处理
		if (dContractMoney == dContractMoneyOri) {
			dealInstPreDocument(contractsVery, contract);
//			dealInstDocument(contractsVery, contract);
			return;
		}
		// 如果原贷款合同是全额贷款，因销售合同不产生首付单据分录，需跳过处理
		if (dContractMoney - dLoanAmountTotal <= 0 && dContractMoneyOri - dLoanAmountTotal == 0) {
			dealInstPreDocument(contractsVery, contract);
//			dealInstDocument(contractsVery, contract);
			return;
		}

		// 当前合同与原合同的合同金额差额
		double dPriceReduce = dContractMoney - dContractMoneyOri;
		List<Map<String, Object>> billList = saleContractsVaryDao.getSaleBillDocument(contractsVery.getContractNo());
		if (billList == null || billList.size() == 0) {
			// 如果从全额贷款变更非全额贷款时，原来无单据分录，直接新增单据分录即可
			if (dPriceReduce > 0) {
				financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), 19, (short) 15, "车辆-销售合同",
						contractsVery.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(),
						contract.getCustomerName(), (short) 20, dPriceReduce, null, null, dtRP);
			}
			dealInstPreDocument(contractsVery, contract);
//			dealInstDocument(contractsVery, contract);
			return;
		}
		FinanceDocumentEntries entry = dao.get(FinanceDocumentEntries.class, billList.get(0).get("entry_id") + "");
		// 原始业务单据金额
		double dDocumentAmount = Tools.toDouble(entry.getDocumentAmount());
		// 原始单据的剩余金额(不算授信担保)
		double dLeftAmount = Tools.toDouble(entry.getLeftAmount());
		// 原始单据已使用的授信或担保
		double dUsedCredit = Tools.toDouble(entry.getUsedCredit());
		// 原始业务单据已收金额（包括授信和担保）
		double dPaidAmount = dDocumentAmount - dLeftAmount + dUsedCredit;
		if (dPaidAmount > dDocumentAmount) {
			dPaidAmount = dDocumentAmount;
		}
		// 原始单据可用剩余金额
		double dLeftAmountCanUse = dDocumentAmount - dPaidAmount;

		// 预付款类型的变更单金额
		double dDocumentAmountVary = 0;
		double dLeftAmountVary = 0;
		// 变更单已请款金额
		double dRequestAmount = 0;
		// 变更单可用剩余金额
		double dPriceRest = 0;

		FinanceDocumentEntries entryVary = null;
		List<Map<String, Object>> billVaryList = saleContractsVaryDao.getSaleBillDocumentVary(contractsVery
				.getContractNo());
		if (billVaryList != null && billVaryList.size() > 0) {
			entryVary = dao.get(FinanceDocumentEntries.class, billVaryList.get(0).get("entry_id") + "");
			dDocumentAmountVary = Tools.toDouble(entryVary.getDocumentAmount());
			dLeftAmountVary = Tools.toDouble(entryVary.getLeftAmount());
			if (billVaryList.get(0).get("request_amount")!=null &&StringUtils.isNotEmpty(billVaryList.get(0).get("request_amount").toString())) {
				dRequestAmount = Tools.toDouble(Double.parseDouble(billVaryList.get(0).get("request_amount").toString()));
			}

			dPriceRest = dDocumentAmountVary - dRequestAmount;
		}

		// 当前变更手工输入的请款金额
		double dPriceRequest = Tools.toDouble(contractsVery.getPriceRequest());
		// 当前合同与原单据的差价
		double dPriceReduceDoc = (dContractMoney - dLoanAmountTotal) - dDocumentAmount;
		// 当前的首付款
		double dFirstPay = dContractMoney - dLoanAmountTotal;
		// 如果首付款>=已收客户款，说明还要继续收客户钱
		if (dFirstPay >= dPaidAmount) {
			// 如果处理过付款
			if (dRequestAmount > 0) {
				entry.setDocumentAmount(dFirstPay + dRequestAmount);
				entry.setLeftAmount(dFirstPay - (dDocumentAmount - dLeftAmount) + dRequestAmount);
				entry.setArapTime(dtRP);
				dao.update(entry);
				if (entryVary != null) {
					entryVary.setDocumentAmount(dRequestAmount);
					entryVary.setLeftAmount(dRequestAmount - (dDocumentAmountVary - dLeftAmountVary));
					entryVary.setArapTime(dtRP);
					dao.update(entryVary);
				}

			} else {// 如果未处理过则删除预收单
				entry.setDocumentAmount(dFirstPay);
				entry.setLeftAmount(dFirstPay - (dDocumentAmount - dLeftAmount));
				entry.setArapTime(dtRP);
				dao.update(entry);
				if (entryVary != null) {
					dao.delete(entryVary);
				}
			}
		} else {// 如果首付款<已收客户款，说明已经收多了客户钱，要退客户钱
			entry.setDocumentAmount(dPaidAmount);
			entry.setLeftAmount(dPaidAmount - (dDocumentAmount - dLeftAmount));
			entry.setArapTime(dtRP);
			dao.update(entry);
			if (billVaryList == null || billVaryList.size() == 0) {// 如果没有变更单据
				// 需请款金额，如果有手输则取手输的，无则自动算
				double dPriceReq = dPriceRequest > 0 ? dPriceRequest : (dPaidAmount - (dFirstPay < 0 ? 0 : dFirstPay));
				financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), 19, (short) 70, "车辆-销售合同变更",
						contract.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(),
						contract.getCustomerName(), (short) 10, dPriceReq, null, null, dtRP);
			} else {// 如果已存在变更单据
					// 还请款金额，如果有手输则取手输的，无则自动算。原已付金额-当前首付=当前合同多出的应退金额。
				double dPriceReq = dPriceRequest > 0 ? dPriceRequest
						: (dPaidAmount - (dFirstPay < 0 ? 0 : dFirstPay) - dRequestAmount);// 当前合同多出的应退金额-已经实际退了的金额
				// 退客户差额
				if (dPriceReq >= 0) {
					entryVary.setDocumentAmount(dPaidAmount - dFirstPay);
					entryVary.setLeftAmount((dRequestAmount - (dDocumentAmountVary - dLeftAmountVary)) + dPriceReq);
					entryVary.setArapTime(dtRP);
					dao.update(entryVary);
				} else {// 如果为负数说明需要向客户收差额款,增加销售合同单据金额
					entry.setDocumentAmount(dDocumentAmount - dPriceReq);
					entry.setLeftAmount(-dPriceReq + dLeftAmount);
					entry.setArapTime(dtRP);
					dao.update(entry);

					entryVary.setDocumentAmount(dRequestAmount);
					entryVary.setLeftAmount((dRequestAmount - (dDocumentAmountVary - dLeftAmountVary)));
					entryVary.setArapTime(dtRP);
					dao.update(entryVary);
				}
			}
		}

		dealInstPreDocument(contractsVery, contract);
//		dealInstDocument(contractsVery, contract);
	}

	@SuppressWarnings("unchecked")
	private Timestamp getMaxPlanDeliverTime(VehicleSaleContracts contract) {
		Timestamp dtRP = null;
		List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) dao.findByHql(
				"from VehicleSaleContractDetail where contractNo = ?", contract.getContractNo());
		for (VehicleSaleContractDetail detail : detailList) {
			if (detail.getPlanDeliverTime() != null) {
				long dtRPTime = dtRP == null ? 0L : dtRP.getTime();
				if (detail.getPlanDeliverTime().getTime() > dtRPTime) {
					dtRP = detail.getPlanDeliverTime();
				}
			}
		}

		if (dtRP == null) {
			dtRP = new Timestamp(System.currentTimeMillis());
		}

		return dtRP;
	}

	/**
	 * 处理已做保险单的单据分录
	 * 
	 * @param contractsVery
	 * @param contract
	 */
/**	@SuppressWarnings("unchecked")
	private void dealInstDocument(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		List<Map<String, Object>> resultList = saleContractsVaryDao.getInstDocumentList(contractsVery.getDocumentNo());
		for (Map<String, Object> result : resultList) {
			boolean isContainV = true;
			isContainV = (Boolean)result.get("is_contain_v");
//			if (result.get("is_contain_v")!=null &&StringUtils.isNotEmpty(result.get("is_contain_v") + "")) {
//				isContainV = Boolean.parseBoolean(result.get("is_contain_v") + "");
//			}
			boolean isContainS = true;
			isContainS = (Boolean)result.get("is_contain_s");
//			if (StringUtils.isNotEmpty(result.get("is_contain_s") + "")) {
//				isContainS = Boolean.parseBoolean(result.get("is_contain_s") + "");
//			}

			// 如果含保费，需检查已审批的保险单，如果有未收款的应收客户保费，则需要删除
			if (isContainV && !isContainS) {
				if (result.get("after_no")!=null &&StringUtils.isNotEmpty(result.get("after_no").toString())) {
					throw new ServiceException(String.format("审批失败：车辆%s已经购买保险且保险应收款已做后续处理，不能变更含保费",
							result.get("vehicle_vin")));
				} else if (result.get("document_id")!=null && StringUtils.isNotEmpty(result.get("document_id").toString())) {
					List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
							"from FinanceDocumentEntries where documentId = ? and documentType = ? ",
							result.get("document_id") + "", result.get("document_type") + "");
					if (entryList != null && entryList.size() > 0) {
						dao.delete(entryList.get(0));
					}
				}
			} else if (!isContainV && isContainS) { // 如果取消含保费，则需要重新生成应收客户保费
				double dPriceInsurance = 0.00D;// 保单金额
				if (result.get("insurance_income")!=null &&StringUtils.isNotEmpty(result.get("insurance_income").toString())) {
					dPriceInsurance = Double.parseDouble(result.get("insurance_income").toString() );
				}
				double dCustomerPay = dPriceInsurance;
				String sDocumentNo = result.get("insurance_no") + "," + result.get("vehicle_vin");
				Timestamp dt = null;
				if (result.get("payables_date")!=null && StringUtils.isEmpty(result.get("payables_date").toString())) {
					dt = baseRelatedObjectsDao.getRepayTimeOfObject(result.get("supplier_id").toString());
				} else {
					dt = (Timestamp) result.get("payables_date");
				}

				if (dPriceInsurance > 0) {
					if ("2".equals(result.get("insurance_pay_mode") + "")) {
						boolean b = financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 3, (short) 15, "保险-购买代收",
								sDocumentNo, result.get("customer_id") + "", result.get("customer_no") + "",
								result.get("customer_name") + "", (short) 20, dCustomerPay);
						if(!b){
							throw new ServiceException("审批失败:添加单据分录出错");
						}
					} else {
						// 如果销售合同不为空，切保单费用不包含在销售合同中
						boolean b =  financeDocumentEntriesDao.insertEntryEx(contractsVery.getStationId(), 3, (short) 15, "保险-购买代收",
								sDocumentNo, result.get("customer_id") + "", result.get("customer_no") + "",
								result.get("customer_name") + "", (short) 20, dCustomerPay);
						if(!b){
							throw new ServiceException("审批失败:添加单据分录出错");
						}
					}
				}
			}
		}
	}
	**/

	/**
	 * 处理保险预收单据
	 * 
	 * @param contractsVery
	 * @param contract
	 */
	@SuppressWarnings("unchecked")
	private boolean dealInstPreDocument(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		if (!getAdvancesReceived()) {
			List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao
					.findByHql(
							"from FinanceDocumentEntries where documentId = ? and documentType = '车辆-保险预收' AND afterNo is null",
							contract.getContractNo());
			if (entryList != null && entryList.size() > 0) {
				dao.delete(entryList.get(0));
			}
			return false;
		}
		// 如果之前没有保险，编辑时增加保险
		double dAmountOri = getInsuranceAmountTotal(contract);
		double dAmount = getCurrentInsuranceAmountTotal(contract);
		List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
				"from FinanceDocumentEntries where documentId = ? and documentType = '车辆-保险预收' ",
				contract.getContractNo());

		if (entryList == null || entryList.size() == 0) {
			if (dAmount > 0) {
				financeDocumentEntriesDao.insertEntryEx(
						contractsVery.getStationId(),
						18,
						(short) 15,
						"车辆-保险预收",
						contractsVery.getContractNo(),
						contract.getCustomerId(),
						contract.getCustomerNo(),
						contract.getCustomerName(),
						(short) 10,
						dAmount,
						null,
						null,
						contract.getPlanFinishTime() == null ? new Timestamp(System.currentTimeMillis()) : contract
								.getPlanFinishTime());
				return true;
			}
		}

		// 如果原金额与现金额相同时更新对象
		if (dAmount == dAmountOri) {
			List<FinanceDocumentEntries> entryList2 = (List<FinanceDocumentEntries>) dao.findByHql(
					"from FinanceDocumentEntries where documentId = ? and documentType = '车辆-保险预收' ",
					contract.getContractNo());

			for (FinanceDocumentEntries entry2 : entryList2) {
				if (!entry2.getObjectName().equals(contract.getCustomerName())) {
					dao.delete(entry2);
					entry2 = copyEntry(entry2);
					dao.flush();
					//entry2.setEntryId(UUID.randomUUID().toString());
				}
				entry2.setObjectId(contract.getCustomerId());
				entry2.setObjectNo(contract.getCustomerNo());
				entry2.setObjectName(contract.getCustomerName());
				dao.update(entry2);
			}
			return true;
		}

		if (entryList == null || entryList.size() == 0) {
			return true;
		}

		double dPriceReduce = dAmount - dAmountOri;
		double dPriceDocument = Tools.toDouble(entryList.get(0).getDocumentAmount());
		double dPriceRest = Tools.toDouble(entryList.get(0).getLeftAmount());
		double dPricePaid = dPriceDocument - dPriceRest;
		String sObjectName = entryList.get(0).getObjectName();

		FinanceDocumentEntries entry = entryList.get(0);
		// 因为预收款特殊就算多收也可通过请预收款进行退钱，所以不需要另外产生变更单
		if (dAmount >= dPricePaid) {
			
			if (!sObjectName.equals(contract.getCustomerName())) {
				dao.delete(entry);
				dao.flush();
				FinanceDocumentEntries newEntry = copyEntry(entry);
				newEntry.setDocumentAmount(dPricePaid);
				newEntry.setLeftAmount(0.00D);
				newEntry.setObjectId(contract.getCustomerId());
				newEntry.setObjectNo(contract.getCustomerNo());
				newEntry.setObjectName(contract.getCustomerName());
				dao.update(newEntry);
			}else{
				entry.setDocumentAmount(dPricePaid);
				entry.setLeftAmount(0.00D);
				entry.setObjectId(contract.getCustomerId());
				entry.setObjectNo(contract.getCustomerNo());
				entry.setObjectName(contract.getCustomerName());

				dao.update(entry);
			}
			
		} else {
			if (!sObjectName.equals(contract.getCustomerName())) {
				dao.delete(entry);
				dao.flush();
				FinanceDocumentEntries newEntry = copyEntry(entry);
				newEntry.setDocumentAmount(dPricePaid);
				newEntry.setLeftAmount(0.00D);
				newEntry.setObjectId(contract.getCustomerId());
				newEntry.setObjectNo(contract.getCustomerNo());
				newEntry.setObjectName(contract.getCustomerName());
				dao.update(newEntry);
			}else{
				entry.setDocumentAmount(dPricePaid);
				entry.setLeftAmount(0.00D);
				entry.setObjectId(contract.getCustomerId());
				entry.setObjectNo(contract.getCustomerNo());
				entry.setObjectName(contract.getCustomerName());
				dao.update(entry);
			}
			
		}
		return true;
	}

	/**
	 * 获取当前修改后的保险金额
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private double getCurrentInsuranceAmountTotal(VehicleSaleContracts contract) {
		double dAmount = 0;
		List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql(
				"from VehicleSaleContractDetail  where contractNo = ? ", contract.getContractNo());
		for (VehicleSaleContractDetail contractDetail : contractDetailList) {
			if (Tools.toBoolean(contractDetail.getIsContainInsuranceCost())) {
				continue;
			}

			List<VehicleSaleContractInsurance> insuranceList = (List<VehicleSaleContractInsurance>) dao.findByHql(
					"from VehicleSaleContractInsurance where ISNULL(abortStatus,20)<>10 and contractDetailId = ?",
					contractDetail.getContractDetailId());
			for (VehicleSaleContractInsurance insurance : insuranceList) {
				dAmount += Tools.toDouble(insurance.getCategoryIncome());
			}

		}

		return dAmount;
	}

	/**
	 * 获取合同已有保险金额(不含保费的保险)
	 * 
	 * @param contract
	 * @return
	 */
	private double getInsuranceAmountTotal(VehicleSaleContracts contract) {
		double totalInsuranceAmount = 0.00D;
		List<Map<String, Object>> resultList = saleContractsVaryDao.getInsuranceAmountTotal(contract.getContractNo());
		if (resultList != null && resultList.size() > 0) {
			String totalInsuranceAmountStr = resultList.get(0).get("category_income").toString();
			if (StringUtils.isNotEmpty(totalInsuranceAmountStr)) {
				totalInsuranceAmount = Tools.toDouble(Double.parseDouble(totalInsuranceAmountStr));
			}
		}
		return totalInsuranceAmount;
	}

	/**
	 * 是否需要创建保险预收款
	 * 
	 * @return
	 */
	private boolean getAdvancesReceived() {
		boolean advancesReceived = false;
		SysUsers user = HttpSessionStore.getSessionUser();
		List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(vsc_insurance_advances_received,
				user.getDefaulStationId());
		if (options != null && options.size() > 0) {
			if ("是".equals(options.get(0).getOptionValue())) {
				advancesReceived = true;
			}
		}
		return advancesReceived;
	}

	/**
	 * 获得变更后的贷款
	 * 
	 * @param contract
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private double getNewLoanAmount(VehicleSaleContracts contract) {
		double dLoanAmount = 0.00D;
		List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql(
				"from VehicleSaleContractDetail  where contractNo = ? ", contract.getContractNo());

		for (VehicleSaleContractDetail contractDetail : contractDetailList) {
			List<Map<String, Object>> resultList = saleContractsVaryDao.getSumLoanAmount(contractDetail
					.getContractDetailId());
			if (resultList != null && resultList.size() > 0) {
				Double dLoanAmountD = (Double) resultList.get(0).get("loan_amount");
				dLoanAmount += Tools.toDouble(dLoanAmountD);
			}
		}

		return dLoanAmount;
	}

	/**
	 * 处理发票
	 * 
	 * @param contractsVery
	 */
	@SuppressWarnings({"unchecked" })
	private void dealInvoice(VehicleSaleContractsVary contractsVery, VehicleSaleContracts contract) {
		List<VehicleSaleContractDetail> abortContractDetailList = (List<VehicleSaleContractDetail>) dao
				.findByHql(
						"from VehicleSaleContractDetail  where contractNo = ? AND (abortStatus = 10 OR  ISNULL(vehicleVin,'')='') ",
						contract.getContractNo());
		for (VehicleSaleContractDetail abortContractDetail : abortContractDetailList) {
			// 如是终止或清空VIN的相关发票则删除
			List<VehicleInvoices> invoiceList = (List<VehicleInvoices>) dao.findByHql(
					"from VehicleInvoices where contractDetailId = ?", abortContractDetail.getContractDetailId());
			for (VehicleInvoices invoice : invoiceList) {
				List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql(
						"from FinanceDocumentEntries  where documentId = ? ", invoice.getInvoicesDetailId());
				for (FinanceDocumentEntries entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getAfterNo())) {
						throw new ServiceException(String.format("审批失败：车辆 VIN：%s已开发票，不能终止该车辆或清空VIN", abortContractDetail.getVehicleVin()));
					}
					List<FinanceDocumentEntries> sameEntryList = (List<FinanceDocumentEntries>) dao.findByHql(
							"from FinanceDocumentEntries  where documentType = ? and documentId = ?",
							entry.getDocumentType(), entry.getDocumentId());
					for (FinanceDocumentEntries sameEntry : sameEntryList) {
						dao.delete(sameEntry);
					}

				}
			}
		}
		
		List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql("from VehicleSaleContractDetail  where contractNo = ?", contract.getContractNo());
		for (VehicleSaleContractDetail contractDetail : contractDetailList) {
			// 如果为已终止的不处理,前面已经处理过
			if (STATUS_ABORT == Tools.toShort(contractDetail.getAbortStatus()) || StringUtils.isEmpty(contractDetail.getVehicleVin())) {
				continue;
			}

			List<VehicleInvoicesVary> invoicesVaryList = (List<VehicleInvoicesVary>) dao.findByHql(
					"from VehicleInvoicesVary where contractDetailId=? AND  detailVaryId in (select detailVaryId from VehicleSaleContractDetailVary  where  documentNo = ?)", 
					contractDetail.getContractDetailId(),contractsVery.getDocumentNo());
			for (VehicleInvoicesVary invoicesVary : invoicesVaryList) {
				List<FinanceDocumentEntries> entryList = (List<FinanceDocumentEntries>) dao.findByHql("from FinanceDocumentEntries  where documentId = ? ", invoicesVary.getInvoicesDetailId());

				if (entryList != null && entryList.size() > 0) {
					FinanceDocumentEntries entry = entryList.get(0);
					if (STATUS_ABORT == invoicesVary.getAbortStatus()) {
						if (StringUtils.isNotEmpty(entry.getAfterNo())) {
							throw new ServiceException(String.format("审批失败：车辆 VIN：%s的发票%s 已开具，不能终止该发票明细",
									contractDetail.getVehicleVin(), invoicesVary.getInvoiceType()));
						}
						List<FinanceDocumentEntries> sameEntryList = (List<FinanceDocumentEntries>) dao.findByHql(
								"from FinanceDocumentEntries  where documentType = ? and documentId = ?",
								entry.getDocumentType(), entry.getDocumentId());
						for (FinanceDocumentEntries sameEntry : sameEntryList) {
							logger.debug(String.format("合同变更%s,删除发票分录%s", contractsVery.getDocumentNo(),sameEntry.toString()));
							dao.delete(sameEntry);
							dao.flush();
						}
					} else {// 修改发票
						if (!("车辆-" + invoicesVary.getInvoiceType()).equals(entry.getDocumentType())
								|| !invoicesVary.getObjectId().equals(entry.getObjectId())) {
							if (StringUtils.isNotEmpty(entry.getAfterNo())) {
								throw new ServiceException(String.format("审批失败：车辆 VIN：%s的发票%s 已开具，不能更换发票对象和发票类型",
										contractDetail.getVehicleVin(), invoicesVary.getInvoiceType()));
							}
							dao.delete(entry);
							dao.flush();
							FinanceDocumentEntries newEntry = copyEntry(entry);
							newEntry.setDocumentType("车辆-" + invoicesVary.getInvoiceType());
							newEntry.setDocumentAmount(Tools.toDouble(invoicesVary.getInvoiceAmount()));
							newEntry.setLeftAmount(Tools.toDouble(invoicesVary.getInvoiceAmount()));
							newEntry.setObjectId(invoicesVary.getObjectId());
							newEntry.setObjectNo(invoicesVary.getObjectNo());
							newEntry.setObjectName(invoicesVary.getObjectName());
							dao.update(newEntry);
							
						} else if (Tools.toDouble(invoicesVary.getInvoiceAmount()) != Tools.toDouble(entry.getInvoiceAmount())) {
							if (Tools.toDouble(invoicesVary.getInvoiceAmount()) < Tools.toDouble(entry
									.getInvoiceAmount())) {
								throw new ServiceException(String.format("审批失败：车辆 VIN：%s的发票%s 已开具，修改后的发票金额不能小于已开金额",
										contractDetail.getVehicleVin(), invoicesVary.getInvoiceType()));
							}
							entry.setDocumentType("车辆-" + invoicesVary.getInvoiceType());
							entry.setDocumentAmount(Tools.toDouble(invoicesVary.getInvoiceAmount()));
							entry.setLeftAmount(Tools.toDouble(invoicesVary.getInvoiceAmount()));
							entry.setObjectId(invoicesVary.getObjectId());
							entry.setObjectNo(invoicesVary.getObjectNo());
							entry.setObjectName(invoicesVary.getObjectName());
							dao.update(entry);

						}
					}
				} else {
					// 如果没有发票则新增
					financeDocumentEntriesDao.insertEntry(contractsVery.getStationId(), 4, (short) 10, "车辆-"
							+ invoicesVary.getInvoiceType(), invoicesVary.getInvoicesDetailId(),
							invoicesVary.getObjectId(), invoicesVary.getObjectNo(), invoicesVary.getObjectName(),
							(short) 20, Tools.toDouble(invoicesVary.getInvoiceAmount()), contractDetail.getVehicleVin()
									+ "," + contractDetail.getContractNo(), null);
				}
			}
		}

	}

	/**
	 * 获取购车发票对象
	 * 
	 * @param contractsVery
	 */
	@SuppressWarnings("unchecked")
	private List<String> getVehicleInvoice(VehicleSaleContractDetailVary contractDetailVary) {
		List<String> lst = new ArrayList<String>();
		List<VehicleInvoicesVary> invoiceVaryList = (List<VehicleInvoicesVary>) dao
				.findByHql(
						"from VehicleInvoicesVary where contractDetailId = ? and invoiceType ='购车发票' AND isnull(abortStatus,20)<>10",
						contractDetailVary.getContractDetailId());
		if (invoiceVaryList != null && invoiceVaryList.size() > 0) {
			lst.add(invoiceVaryList.get(0).getObjectId());
			lst.add(invoiceVaryList.get(0).getObjectNo());
			lst.add(invoiceVaryList.get(0).getObjectName());
			return lst;
		}

		List<VehicleInvoices> invoiceList = (List<VehicleInvoices>) dao.findByHql(
				"from VehicleInvoices  where contractDetailId = ? and invoiceType ='购车发票' ",
				contractDetailVary.getContractDetailId());
		for (VehicleInvoices invoice : invoiceList) {
			List<VehicleInvoicesVary> abortInvoiceVaryList = (List<VehicleInvoicesVary>) dao
					.findByHql(
							"from VehicleInvoicesVary where invoicesDetailId = ? and invoiceType ='购车发票' AND isnull(abortStatus,20)=10",
							invoice.getInvoicesDetailId());
			if (abortInvoiceVaryList == null || abortInvoiceVaryList.size() == 0) {
				lst.add(invoice.getObjectId());
				lst.add(invoice.getObjectNo());
				lst.add(invoice.getObjectName());
				return lst;
			}
		}

		return lst;
	}

	/**
	 * 计算购置税
	 * 
	 * @param invoiceAmount
	 *            车价
	 * @return
	 */
	private double calcPurchaseTax(double invoiceAmount) {
		// 舍入规则（四舍五入到元、四舍五入到分、舍去角分）
		String sRoudingRule = getTaxRoundingRule();
		double mIncome = invoiceAmount / 11.7D; // 购置税
		BigDecimal incomBig = new BigDecimal(mIncome);
		if ("四舍五入到元".equals(sRoudingRule)) {
			incomBig = incomBig.setScale(0, RoundingMode.HALF_UP);
		} else if ("四舍五入到分".equals(sRoudingRule)) {
			incomBig = incomBig.setScale(2, RoundingMode.HALF_UP);
		} else if ("舍去角分".equals(sRoudingRule)) {
			incomBig = incomBig.setScale(0, RoundingMode.FLOOR);
		} else {
			incomBig = incomBig.setScale(2, RoundingMode.HALF_UP);
		}
		return incomBig.doubleValue();
	}

	/**
	 * 查找四舍五入规则
	 * 
	 * @return
	 */
	private String getTaxRoundingRule() {
		SysUsers user = HttpSessionStore.getSessionUser();
		List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(VEHICLE_PURCHASE_TAX_ROUNDING_RULE,
				user.getDefaulStationId());
		if (options != null && options.size() > 0) {
			return options.get(0).getOptionValue();
		}
		return "";
	}

	/**
	 * 根据VehicleSaleContractDetailVary 填充 VehicleSaleContractDetail
	 * 
	 * @param oriDetail
	 * @param contractDetailVary
	 */
	private VehicleSaleContractDetail FillVehicleSaleContractDetail(VehicleSaleContractDetail oriDetail,
			VehicleSaleContractDetailVary contractDetailVary,VehicleSaleContracts contracts) {
		oriDetail.setPresentIncome(contractDetailVary.getPresentIncome());
		oriDetail.setConversionIncome(contractDetailVary.getConversionIncome());
		oriDetail.setChargeIncome(contractDetailVary.getChargeIncome());
		oriDetail.setInsuranceIncome(contractDetailVary.getInsuranceIncome());
		
		oriDetail.setVehicleId(contractDetailVary.getVehicleId());
		oriDetail.setVnoId(contractDetailVary.getVnoId());
		oriDetail.setVnoIdNew(contractDetailVary.getVnoIdNew());
		oriDetail.setVehicleSalesCode(contractDetailVary.getVehicleSalesCode());
		oriDetail.setVehicleName(contractDetailVary.getVehicleName());
		oriDetail.setVehicleVno(contractDetailVary.getVehicleVno());
		oriDetail.setVehicleStrain(contractDetailVary.getVehicleStrain());
		oriDetail.setVehicleColor(contractDetailVary.getVehicleColor());
		oriDetail.setVehicleVin(contractDetailVary.getVehicleVin());
		oriDetail.setVehicleEngineType(contractDetailVary.getVehicleEngineType());
		oriDetail.setVehicleEngineNo(contractDetailVary.getVehicleEngineNo());
		oriDetail.setVehicleEligibleNo(contractDetailVary.getVehicleEligibleNo());
		oriDetail.setVehicleEligibleNoNew(contractDetailVary.getVehicleEligibleNoNew());
		oriDetail.setVehicleOutFactoryTime(contractDetailVary.getVehicleOutFactoryTime());
		oriDetail.setVehiclePrice(contractDetailVary.getVehiclePrice());
		oriDetail.setVehicleCost(contractDetailVary.getVehicleCost());
		oriDetail.setVehicleCarriage(contractDetailVary.getVehicleCarriage());
		oriDetail.setVehicleQuantity(contractDetailVary.getVehicleQuantity());
		oriDetail.setWarehouseId(contractDetailVary.getWarehouseId());
		oriDetail.setWarehouseName(contractDetailVary.getWarehouseName());
		oriDetail.setVehicleVinNew(contractDetailVary.getVehicleVinNew());
		oriDetail.setVehicleVnoNew(contractDetailVary.getVehicleVnoNew());
		oriDetail.setMinSalePrice(contractDetailVary.getMinSalePrice());
		oriDetail.setMinProfit(contractDetailVary.getMinProfit());
		oriDetail.setPresentPf(contractDetailVary.getPresentPf());
		oriDetail.setPresentCost(contractDetailVary.getPresentCost());
		oriDetail.setConversionCost(contractDetailVary.getConversionCost());
		oriDetail.setChargePf(contractDetailVary.getChargePf());
		oriDetail.setChargeCost(contractDetailVary.getChargeCost());
		oriDetail.setInsuranceCost(contractDetailVary.getInsuranceCost());
		oriDetail.setInsurancePf(contractDetailVary.getInsurancePf());
		oriDetail.setModifiedFee(contractDetailVary.getModifiedFee());
		oriDetail.setVehicleProfit(contractDetailVary.getVehicleProfit());
		oriDetail.setLargessAmount(contractDetailVary.getLargessAmount());
		oriDetail.setDeliverAddress(contractDetailVary.getDeliverAddress());
		oriDetail.setPlanDeliverTime(contractDetailVary.getPlanDeliverTime());
		oriDetail.setBestDeliverTime(contractDetailVary.getBestDeliverTime());
		oriDetail.setRealDeliverTime(contractDetailVary.getRealDeliverTime());
		oriDetail.setVehicleCardNo(contractDetailVary.getVehicleCardNo());
		oriDetail.setVehicleCardTime(contractDetailVary.getVehicleCardTime());
		oriDetail.setInsuranceNo(contractDetailVary.getInsuranceNo());
		oriDetail.setInsuranceCompanyId(contractDetailVary.getInsuranceCompanyId());
		oriDetail.setInsuranceCompanyNo(contractDetailVary.getInsuranceCompanyNo());
		oriDetail.setInsuranceCompanyName(contractDetailVary.getInsuranceCompanyName());
		oriDetail.setVehicleComment(contractDetailVary.getVehicleComment());
		oriDetail.setConversionPf(contractDetailVary.getConversionPf());
		oriDetail.setDriveRoomNo(contractDetailVary.getDriveRoomNo());
		oriDetail.setMaintainFee(contractDetailVary.getMaintainFee());
		oriDetail.setLargessPart(contractDetailVary.getLargessPart());
		oriDetail.setLargessService(contractDetailVary.getLargessService());
		oriDetail.setCustomerIdProfit(contractDetailVary.getCustomerIdProfit());
		oriDetail.setCustomerNameProfit(contractDetailVary.getCustomerNameProfit());
		oriDetail.setCustomerNoProfit(contractDetailVary.getCustomerNoProfit());
		oriDetail.setBelongToSupplierRebate(contractDetailVary.getBelongToSupplierRebate());
		oriDetail.setVehicleCostRef(contractDetailVary.getVehicleCostRef());
		oriDetail.setAbortStatus(contractDetailVary.getAbortStatus());
		oriDetail.setWaysPoint(contractDetailVary.getWaysPoint());
		oriDetail.setVehiclePriceTotal(contractDetailVary.getVehiclePriceTotal());
		oriDetail.setVehicleNameNew(contractDetailVary.getVehicleNameNew());
		oriDetail.setIsContainInsuranceCost(contractDetailVary.getIsContainInsuranceCost());
		oriDetail.setProfession(contractDetailVary.getProfession());
		oriDetail.setTicketOutStockFlag(contractDetailVary.getTicketOutStockFlag());
		oriDetail.setTicketOutStockAmount(contractDetailVary.getTicketOutStockAmount());
		oriDetail.setDiscountAmount(contractDetailVary.getDiscountAmount());
		oriDetail.setSubjectMatter(contractDetailVary.getSubjectMatter());
		oriDetail.setTaxationAmount(contractDetailVary.getTaxationAmount());
		oriDetail.setProfitReturn(contractDetailVary.getProfitReturn());
		oriDetail.setOtherCost(contractDetailVary.getOtherCost());
		oriDetail.setTransportRoutes(contractDetailVary.getTransportRoutes());
		oriDetail.setStartPoint(contractDetailVary.getStartPoint());
		oriDetail.setEndPoint(contractDetailVary.getEndPoint());
		oriDetail.setRelationDetailId(contractDetailVary.getRelationDetailId());
		oriDetail.setContractNo(contractDetailVary.getContractNo());
		oriDetail.setContractDetailId(contractDetailVary.getContractDetailId());
		
		//新增
		oriDetail.setVehicleOwnerId(contracts.getCustomerId());
		oriDetail.setVehicleOwnerName(contracts.getCustomerName());
		oriDetail.setBelongToSupplierId(contracts.getCustomerId());
		oriDetail.setBelongToSupplierNo(contracts.getCustomerNo());
		oriDetail.setBelongToSupplierName(contracts.getCustomerName());

		oriDetail.setDeposit(contractDetailVary.getDeposit());
		return oriDetail;
	}
	
	private VehicleSaleContractDetail FillVehicleSaleContractDetailNew (VehicleSaleContractDetail oriDetail,VehicleSaleContractDetailVary contractDetailVary) {
		Field[] oriDetailFileds = BeanUtil.getDeclaredFields(oriDetail.getClass());
		for(int i=0;i<oriDetailFileds.length;i++){
			Field oriFiled = oriDetailFileds[i];
			if("serialVersionUID".equals(oriFiled.getName())||"documentNo".equals(oriFiled.getName())){
				continue;
			}
			Field varyField = BeanUtil.getDeclaredField(contractDetailVary.getClass(), oriFiled.getName());
			if(varyField==null){
				continue;
			}
			Object varyValue = BeanUtil.getPropertyValue(contractDetailVary, oriFiled.getName());
			String setMehthodName = BeanUtil.getSetterMethod(oriFiled.getName());
			try {
				BeanUtil.invokeMethod(oriDetail, setMehthodName, varyValue);
				logger.debug(String.format("回填 VehicleSaleContractDetail 属性 %s", oriFiled.getName()));
			} catch (Exception e) {
				logger.error(String.format("合同车辆信息回填出错,属性 %s", oriFiled.getName()));
				//throw new ServiceException("合同车辆信息回填出错",e);
			}
		}
		return oriDetail;
	}
	
	
	private FinanceDocumentEntries  copyEntry(FinanceDocumentEntries entry){
		FinanceDocumentEntries newEntry = new FinanceDocumentEntries();
		newEntry.setEntryId(UUID.randomUUID().toString());
		
		
		newEntry.setSummary(entry.getSummary());
		newEntry.setPaidTime(entry.getPaidTime());
		newEntry.setWriteOffTime(entry.getWriteOffTime());
		newEntry.setLeftAmount(entry.getLeftAmount());
		newEntry.setDepartmentId(entry.getDepartmentId());
		newEntry.setInvoiceTime(entry.getInvoiceTime());
		newEntry.setDocumentType(entry.getDocumentType());
		newEntry.setAfterNo(entry.getAfterNo());
		newEntry.setOffsetAmount(entry.getOffsetAmount());
		newEntry.setEntryProperty(entry.getEntryProperty());
		newEntry.setWriteOffAmount(entry.getWriteOffAmount());
		newEntry.setDocumentTime(entry.getDocumentTime());
		newEntry.setSubDocumentNo(entry.getSubDocumentNo());
		newEntry.setUserId(entry.getUserId());
		newEntry.setStationId(entry.getStationId());
		newEntry.setPaidAmount(entry.getPaidAmount());
		newEntry.setDepartmentName(entry.getDepartmentName());
		newEntry.setOffsetTime(entry.getOffsetTime());
		newEntry.setDocumentNo(entry.getDocumentNo());
		newEntry.setAccountId(entry.getAccountId());
		newEntry.setDocumentId(entry.getDocumentId());
		newEntry.setUsedCredit(entry.getUsedCredit());
		newEntry.setArapTime(entry.getArapTime());
		newEntry.setObjectId(entry.getObjectId());
		newEntry.setEntryType(entry.getEntryType());
		newEntry.setUserNo(entry.getUserNo());
		newEntry.setInvoiceAmount(entry.getInvoiceAmount());
		newEntry.setDepartmentNo(entry.getDepartmentNo());
		newEntry.setDocumentAmount(entry.getDocumentAmount());
		newEntry.setUserName(entry.getUserName());
		newEntry.setObjectNo(entry.getObjectNo());
		newEntry.setAmountType(entry.getAmountType());
		newEntry.setObjectName(entry.getObjectName());
		return newEntry;
		
	}
	
	
	
	public static void main(String[] args) {
		String oldValue = "123";
		String newValue = "123";
		System.out.println(StringUtils.equals(oldValue, newValue));
	}

}
