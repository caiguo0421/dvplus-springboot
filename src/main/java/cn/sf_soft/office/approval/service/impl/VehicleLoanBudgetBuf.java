package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dao.BaseRelatedObjectsDao;
import cn.sf_soft.office.approval.dao.SaleContractsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.ui.vo.VehicleLoanBudgetView;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.loan.dao.IVehicleLoanBudgetDao;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudget;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetails;
import cn.sf_soft.vehicle.loan.service.impl.LoanBudgetService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消贷费用预算
 *
 * @author caigx
 */
@Service("vehicleLoanBudgetBuf")
public class VehicleLoanBudgetBuf extends DocumentBufferService {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10180320";

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleLoanBudgetBuf.class);

    //是否要求销售合同车辆已审批
    private static final String VEHICLE_BUDGET_NEED_SVC_APPROVED = "VEHICLE_BUDGET_NEED_SVC_APPROVED";

    private static final String SALE_DOCUMENT_TYPE = "车辆-销售合同";
    private static final String SALE_DOCUMENT_TYPE_VARY = "车辆-销售合同变更";
    private static final String SALE_LOAN = "消贷-客户贷款";

    @Autowired
    private IVehicleLoanBudgetDao vehicleLoanBudgetDao;

    @Autowired
    private SaleContractsDao saleContractsDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private LoanBudgetService loanBudgetService;

    @Autowired
    private BaseDao baseDao;

    @Override
    @SuppressWarnings("rawtypes")
    public ApproveDocuments getDocumentDetail(String documentNo) {
        if (StringUtils.isEmpty(documentNo)) {
            throw new ServiceException("审批单号为空");
        }
        VehicleLoanBudget bugdet = dao.get(VehicleLoanBudget.class, documentNo);
        if (bugdet == null) {
            throw new ServiceException("根据审批单号：" + documentNo + "未找到消贷费用预算的记录");
        }

        return bugdet;
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Autowired
    @Qualifier("vehicleLoanBudgetBufferCalc")
    public void setDocBuffer(DocumentBufferCalc docBuffer) {
        super.docBuffer = docBuffer;
    }


    /**
     * 处理审批列表中的审批单据信息
     *
     * @param vwOfficeApproveDocuments
     * @return
     */
    @SuppressWarnings({"unchecked", "unused"})
    @Override
    public VehicleLoanBudgetView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
        VehicleLoanBudgetView view = new VehicleLoanBudgetView(vwOfficeApproveDocuments);
        try {
            VwVehicleLoanBudget vwLoanBudget = dao.get(VwVehicleLoanBudget.class, vwOfficeApproveDocuments.getDocumentNo());
            view.setLoanObjectName(vwLoanBudget.getLoanObjectName());

            //计算贷款总金额
            StringBuffer buf = new StringBuffer();
            buf.append("select  sum(round(vehiclePriceTotal,0)) as vehiclePriceTotal,").append("\r\n");
            buf.append("sum(round(firstPayTot,0)) as firstPayTot,").append("\r\n");
            buf.append("sum(round(loanAmount,0)) as loanAmount,").append("\r\n");
            buf.append("sum(round(chargeLoanAmount,0)) as chargeLoanAmount,").append("\r\n");
            buf.append("sum(round(agentAmount,0)) as agentAmount").append("\r\n");
            buf.append("from VwVehicleLoanBudgetDetails  where documentNo=?")
                    .append("\r\n");

            List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(), vwOfficeApproveDocuments.getDocumentNo());
            double vehiclePriceTotal = 0, firstPayTot = 0, loanAmount = 0, chargeLoanAmount = 0, agentAmount = 0;
            if (data1 != null && data1.size() == 1) {
                Object[] row = (Object[]) data1.get(0);
                vehiclePriceTotal = row[0] != null ? Tools.toDouble((Double) row[0]) : 0;
                firstPayTot = row[1] != null ? Tools.toDouble((Double) row[1]) : 0;
                loanAmount = row[2] != null ? Tools.toDouble((Double) row[2]) : 0;
                chargeLoanAmount = row[3] != null ? Tools.toDouble((Double) row[3]) : 0;
                agentAmount = row[4] != null ? Tools.toDouble((Double) row[4]) : 0;
            }
            view.setAmountTotal(loanAmount + chargeLoanAmount + agentAmount);

        } catch (Exception ex) {
            logger.warn(String.format("消贷费用预算 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);
        }

        return view;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        return this.checkData(approveDocument, approveStatus, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus, Boolean useException) {
        if (StringUtils.isEmpty(approveDocument.getDocumentNo())) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:审批单号不能为空");
        }
        VehicleLoanBudget bugdet = dao.get(VehicleLoanBudget.class, approveDocument.getDocumentNo());
        if (bugdet == null) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:根据审批单号：" + approveDocument.getDocumentNo() + "未找到消贷费用预算的记录");
        }
        List<VehicleLoanBudgetDetails> budgetDetailList = vehicleLoanBudgetDao.getBudgetDetailList(approveDocument.getDocumentNo());
        if (budgetDetailList == null || budgetDetailList.size() == 0) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:没有预算明细");
        }

        if (bugdet.getLoanMode() == null) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:贷款模式不能为空");
        }
        if (StringUtils.isEmpty(bugdet.getCustomerId())) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:购车客户不能为空");
        }
        if (StringUtils.isEmpty(bugdet.getLoanObjectId())) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:贷款人不能为空");
        }
        if (Tools.toShort(bugdet.getLoanMode()) == 20 && StringUtils.isEmpty(bugdet.getAgentId())) {
            if (!useException) {
                return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;
            }
            throw new ServiceException("审批失败:贷款模式为代理消贷时，代理商不能为空");
        }

        //这个检查销售合同车辆是否已审批的校验 ,放在LAST_APPROVE
        if (approveStatus == ApproveStatus.LAST_APPROVE) {
            checkVSCDStatus(bugdet, budgetDetailList);
        }

//		VehicleSaleContractDetail detail = saleContractsDao.getDetailByContractNo(bugdet.getSaleContractNo());
//		if (detail.getStatus() != Constant.DocumentStatus.APPROVED) {
//			throw new ServiceException("审批失败:销售合同未审批通过");
//		}
//		if (detail.getApproveStatus() != 1) {
//			if(!useException){return ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND;}
//			throw new ServiceException("审批失败:销售合同未审批通过");
//		}

        //校验明细
        validateBudgetDetail(bugdet, budgetDetailList);


        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    private void checkVSCDStatus(VehicleLoanBudget bugdet, List<VehicleLoanBudgetDetails> budgetDetailList) {
        //代理消贷时，不检查直接通过
        if (Tools.toShort(bugdet.getLoanMode()) == 20)
            return;
        for (VehicleLoanBudgetDetails detail : budgetDetailList) {
            if (StringUtils.isNotEmpty(detail.getSaleContractDetailId())) {
                VehicleSaleContractDetail vehicleDetail = dao.get(VehicleSaleContractDetail.class, detail.getSaleContractDetailId());
                if (vehicleDetail == null)
                    continue;

                if (Tools.toShort(vehicleDetail.getApproveStatus()) != 1) {
                    if (StringUtils.isEmpty(detail.getVehicleVin())) {
                        throw new ServiceException("审批失败:销售合同明细状态不正确，请确认车辆销售合同明细是否已审批");
                    } else {
                        throw new ServiceException(String.format("审批失败:车辆【%s】对应的销售合同明细状态不正确，请确认车辆销售合同明细是否已审批", detail.getVehicleVin()));
                    }
                }
            }
        }
    }

    /**
     * 校验明细
     *
     * @param bugdet
     * @param budgetDetailList
     */
    @SuppressWarnings("unchecked")
    private void validateBudgetDetail(VehicleLoanBudget bugdet, List<VehicleLoanBudgetDetails> budgetDetailList) {
        for (VehicleLoanBudgetDetails detail : budgetDetailList) {

            if (StringUtils.isNotEmpty(detail.getSaleContractDetailId())) {
                VehicleSaleContractDetail vehicleDetail = dao.get(VehicleSaleContractDetail.class, detail.getSaleContractDetailId());
                if (vehicleDetail == null)
                    continue;
                if (sysOptionsDao.getOptionForBoolean(VEHICLE_BUDGET_NEED_SVC_APPROVED)) {
                    if (Tools.toShort(vehicleDetail.getApproveStatus()) != 1 && (Tools.toShort(vehicleDetail.getApproveStatus())) != 2) {
                        if (StringUtils.isEmpty(detail.getVehicleVin())) {
                            throw new ServiceException("审批失败:预算明细对应的销售明细未审批");
                        } else {
                            throw new ServiceException(String.format("审批失败:车辆【%s】对应的销售明细未审批", detail.getVehicleVin()));
                        }

                    }
                }
            }

            if (StringUtils.isNotEmpty(detail.getVehicleVin())) {
                List<VehicleLoanBudgetDetails> sameVinDetailList = (List<VehicleLoanBudgetDetails>) dao.findByHql("from VehicleLoanBudgetDetails where vehicleVin = ? and selfId<>? and documentNo = ?", detail.getVehicleVin(), detail.getSelfId(), detail.getDocumentNo());
                if (sameVinDetailList != null && sameVinDetailList.size() > 0) {
                    throw new ServiceException("审批失败:同一张预算单中VIN不能重复：" + detail.getVehicleVin());
                }
            }

            VwVehicleLoanBudgetDetails vwDetail = dao.get(VwVehicleLoanBudgetDetails.class, detail.getSelfId());
            if (vwDetail == null)
                continue;
            if (StringUtils.isEmpty(vwDetail.getVehicleSalesCode())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车型型号不能为空");
            }

            if (StringUtils.isEmpty(vwDetail.getVehicleBrand())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆品牌不能为空,请检查车辆基础数据");
            }
            if (StringUtils.isEmpty(vwDetail.getVehicleStrain())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆品系不能为空,请检查车辆基础数据");
            }
            if (StringUtils.isEmpty(vwDetail.getVehicleName())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆名称不能为空,请检查车辆基础数据");
            }

            if (Tools.toDouble(vwDetail.getVehiclePriceTotal()) <= 0) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆售价必须大于0");
            }
            if (Tools.toDouble(vwDetail.getLoanAmount()) <= 0) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆贷款必须大于0");
            }

            if (Tools.toDouble(vwDetail.getLoanAmount()) > Tools.toDouble(vwDetail.getVehiclePriceTotal())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆贷款不能大于车辆售价");
            }

            if (Tools.toDouble(vwDetail.getAgentAmount()) < 0) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "代理贷款不能小于0");
            }
            //TODO 辆明细中，计息方式不能为空
			/*if(null == vwDetail.getPeriodNumber()){
				throw new ServiceException(getDetailErrorMsg(vwDetail)+"辆明细中，计息方式不能为空");
			}*/
//			if(null != vwDetail.getRateType()){
//				throw new ServiceException(getDetailErrorMsg(vwDetail)+"车辆明细中，计息方式不能为空");
//			}
//			if(null == vwDetail.getPeriodNumber()){
//				throw new ServiceException(getDetailErrorMsg(vwDetail)+"车辆明细中，期数不能为空");
//			}
//			if(vwDetail.getPeriodNumber() <= 0){
//				throw new ServiceException(getDetailErrorMsg(vwDetail)+"车辆明细中，期数必须为大于0的数字");
//			}
            short loanMode = null == bugdet.getLoanMode() ? (short) 10 : bugdet.getLoanMode();
            if (loanMode == 20 && StringUtils.isEmpty(vwDetail.getVehicleVin())) {
                throw new ServiceException(getDetailErrorMsg(vwDetail) + "车辆明细中，当为代理消贷时车辆VIN不能为空");
            }

            if (StringUtils.isNotEmpty(detail.getVehicleVin())) {
                String hql = "from VehicleLoanBudgetDetails where documentNo in (select documentNo from VehicleLoanBudget  b  where b.status<=50) AND vehicleVin = ? AND documentNo<>?";
                List<VehicleLoanBudgetDetails> otherDetailList = (List<VehicleLoanBudgetDetails>) dao.findByHql(hql, detail.getVehicleVin(), detail.getDocumentNo());
                if (otherDetailList != null && otherDetailList.size() > 0) {
                    //检查是否是二手车
                    List<SecondhandVehicleStocks> secondHandList = (List<SecondhandVehicleStocks>) dao.findByHql("from SecondhandVehicleStocks where vehicleVin = ?", detail.getVehicleVin());
                    if (secondHandList != null && secondHandList.size() > 0) {
                        continue;
                    } else {
                        throw new ServiceException(String.format("审批失败:车辆【%s】已做过预算，预算单号%s", detail.getVehicleVin(), otherDetailList.get(0).getDocumentNo()));
                    }
                }
            }

            List<Map<String, Object>> resultList = vehicleLoanBudgetDao.getLoanAmount(vwDetail.getSelfId());
            if (resultList != null && resultList.size() > 0) {
                double loanAmount = (double) resultList.get(0).get("loan_amount");
                double dAmount = Tools.toDouble(vwDetail.getLoanAmount()) + Tools.toDouble(vwDetail.getChargeLoanAmount()) + Tools.toDouble(vwDetail.getAgentAmount());
                if (loanAmount > dAmount) {
                    String.format("%s,车辆贷款+费用贷款+代理贷款之和%3.2f小于该车已建立贷款合同的贷款金额%3.2f", getDetailErrorMsg(vwDetail), dAmount, loanAmount);
                }
            }

            validateBudgetCharge(bugdet, vwDetail);
        }
    }

    /**
     * 校验费用
     *
     * @param bugdet
     * @param vwDetail
     */
    private void validateBudgetCharge(VehicleLoanBudget bugdet, VwVehicleLoanBudgetDetails vwDetail) {
        List<VwVehicleLoanBudgetCharge> chargeList = vehicleLoanBudgetDao.getBudgetCharge(vwDetail.getSelfId());
        for (VwVehicleLoanBudgetCharge charge : chargeList) {
            if (null == charge.getMoneyType()) {
                throw new ServiceException(String.format("%s,【%s】款项类型不能为空", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }
            if (null == charge.getIncome() && null == charge.getExpenditure()) {
                throw new ServiceException(String.format("%s,【%s】收入和支也不能同时为空", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }
            if (null != charge.getIncome() && charge.getIncome().compareTo(0d) == -1) {
                throw new ServiceException(String.format("%s,【%s】收入不能为负数", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }
            if (null != charge.getExpenditure() && charge.getExpenditure().compareTo(0d) == -1) {
                throw new ServiceException(String.format("%s,【%s】支出不能为负数", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }
            if (Tools.toDouble(charge.getLoanAmount()) < 0) {
                throw new ServiceException(String.format("%s,【%s】费用贷款不能小于0", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }
            //20为保证金，40为保险押金
            if (Tools.toShort(charge.getMoneyType()) == 20 || Tools.toShort(charge.getMoneyType()) == 40) {
                if (StringUtils.isEmpty(charge.getObjectId())) {
                    throw new ServiceException(String.format("%s,【%s】款项类型为保证金或保险押金时，费用对象不能为空", getDetailErrorMsg(vwDetail), charge.getChargeName()));
                }
                if (null == charge.getPaymentDate()) {
                    throw new ServiceException(String.format("%s,【%s】款项类型为保证金或保险押金时，应付日期不能为空", getDetailErrorMsg(vwDetail), charge.getChargeName()));
                }

            }

            if (Tools.toDouble(charge.getIncome()) < Tools.toDouble(charge.getLoanAmount())) {
                throw new ServiceException(String.format("%s,【%s】的收入不能小于费用贷款", getDetailErrorMsg(vwDetail), charge.getChargeName()));
            }

        }

    }

    private String getDetailErrorMsg(VwVehicleLoanBudgetDetails vwDetail) {
        if (StringUtils.isEmpty(vwDetail.getVehicleVin())) {
            return "审批失败：车辆明细中，";
        } else {
            return String.format("审批失败：车辆【%s】中", vwDetail.getVehicleVin());
        }
    }


    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        VehicleLoanBudget bugdet = dao.get(VehicleLoanBudget.class, approveDocument.getDocumentNo());
        bugdet.setFlowStatus((short) 1);

        SysUsers user = HttpSessionStore.getSessionUser();
        List<VehicleLoanBudgetDetails> budgetDetailList = vehicleLoanBudgetDao.getBudgetDetailList(approveDocument.getDocumentNo());
        Timestamp approveTime = new Timestamp(System.currentTimeMillis());
        for (VehicleLoanBudgetDetails budgetDetail : budgetDetailList) {
            if (Tools.toShort(budgetDetail.getStatus()) == 0) {//如果未审批的明细，更新为已审批
                budgetDetail.setStatus((short) 1);
                budgetDetail.setApproverId(user.getUserId());
                budgetDetail.setApproveTime(approveTime);
                dao.update(budgetDetail);
            }
        }
        //处理单据分录
        dealBillDocument(bugdet, false);
        //更新销售合同付款状态
        updateBuyType(bugdet);
        //同步挂靠单位
        syncAffiliatedCompany(bugdet);

        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * 同步挂靠单位信息
     *
     * @param bugdet
     */
    private void syncAffiliatedCompany(VehicleLoanBudget bugdet) {
        List<VehicleLoanBudgetDetails> budgetDetailList = vehicleLoanBudgetDao.getBudgetDetailList(bugdet.getDocumentNo());
        for (VehicleLoanBudgetDetails budgetDetail : budgetDetailList) {
            if (budgetDetail.getAffiliatedCompanyId() != null) {
                BaseRelatedObjects baseRelatedObjects = dao.get(BaseRelatedObjects.class, budgetDetail.getAffiliatedCompanyId());
                if (baseRelatedObjects != null) {
                    VehicleSaleContractDetail contractDetail = saleContractsDao.getContractDetailByDetailId(budgetDetail.getSaleContractDetailId());
                    if (contractDetail != null) {
                        if (contractDetail.getBelongToSupplierId() != baseRelatedObjects.getObjectId()) {
                            contractDetail.setBelongToSupplierId(baseRelatedObjects.getObjectId());
                            contractDetail.setBelongToSupplierNo(baseRelatedObjects.getObjectNo());
                            contractDetail.setBelongToSupplierName(baseRelatedObjects.getObjectName());
                            dao.update(contractDetail);
                        }
                        if (contractDetail.getVehicleId() != null) {
                            VehicleArchives vehicleArchives = dao.get(VehicleArchives.class, contractDetail.getVehicleId());
                            if (vehicleArchives != null && vehicleArchives.getBelongToSupplierId() != baseRelatedObjects.getObjectId()) {
                                vehicleArchives.setBelongToSupplierId(baseRelatedObjects.getObjectId());
                                vehicleArchives.setBelongToSupplierNo(baseRelatedObjects.getObjectNo());
                                vehicleArchives.setBelongToSupplierName(baseRelatedObjects.getObjectName());
                                dao.update(vehicleArchives);
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * 更新销售合同付款状态
     *
     * @param bugdet
     */
    private void updateBuyType(VehicleLoanBudget bugdet) {
		/*if (StringUtils.isEmpty(bugdet.getSaleContractNo()))
			return;
		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, bugdet.getSaleContractNo());*/
//		if (contract != null) {
//			short buyType = Tools.toShort(contract.getBuyType(), (short) 10);
//			// 审批更新销售合同付款方式为消贷
//			if (buyType != 20) {
//				contract.setBuyType((short) 20);
//				dao.update(contract);
//			}
//		}
    }

    /**
     * 更新销售合同付款状态(反审)
     *
     * @param bugdet
     */
    private void updateByTypeWithSab(VehicleLoanBudget bugdet) {
        if (StringUtils.isEmpty(bugdet.getSaleContractNo()))
            return;
        VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, bugdet.getSaleContractNo());
        if (null != contract) {
            Short buyType = contract.getBuyType();
            if (null != buyType && buyType != (short) 5) {
                List<List<Object>> list = baseDao.findBySql("SELECT a.document_no FROM dbo.vehicle_loan_budget_details a\n" +
                                "INNER JOIN dbo.vehicle_sale_contract_detail b ON a.sale_contract_detail_id=b.contract_detail_id\n" +
                                "INNER JOIN dbo.vehicle_loan_budget c ON c.document_no = a.document_no\n" +
                                "WHERE a.status<>2 AND a.status<>4 AND b.approve_status IN (0,1,2,20) AND a.document_no<>? AND c.sale_contract_no=?",
                        bugdet.getDocumentNo(), bugdet.getSaleContractNo());
                if (null == list || list.isEmpty()) {
                    contract.setBuyType((short) 5);
                }
            }
        }
    }

    /**
     * 处理单据分录
     *
     * @param bugdet
     * @param isSab  是否是反审
     */
    private void dealBillDocument(VehicleLoanBudget bugdet, boolean isSab) {
        double dLoanAmountVehicle = getLoanAmountVehicle(bugdet);//取差额
        double dLoanAmountCharge = getLoanAmountCharge(bugdet);//取差额
        String sContractNo = bugdet.getSaleContractNo();

        if (isSab) {
            loanBudgetService.dealBillDocumentForContractEntryWithSab(sContractNo, dLoanAmountVehicle, dLoanAmountCharge);
        } else {
            //如果为正常消贷，需处理原销售合同单据，并生成挂客户的贷款
            loanBudgetService.dealBillDocumentForContractEntry(sContractNo, dLoanAmountVehicle, dLoanAmountCharge);
        }


//		if(StringUtils.isNotEmpty(sContractNo)){
//			//判断是否有变更单
//			List<FinanceDocumentEntries> saleEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo,SALE_DOCUMENT_TYPE);
//			if(saleEntryList!=null && saleEntryList.size()>0){
//				FinanceDocumentEntries saleEntry = saleEntryList.get(0);
//				 double dLeftAmount = Tools.toDouble(saleEntry.getLeftAmount());
//				 double dLeftAmountCanUse = 0;//可用剩余金额（算上授信）
//				 double dPaidAmount = 0;//已使用金额（包括授信担保）
//				 double dDocumentAmount =Tools.toDouble(saleEntry.getDocumentAmount()) ;
//				 double dUsedCredit = Tools.toDouble(saleEntry.getUsedCredit());//授信(担保)金额
//                 String sStationId = saleEntry.getStationId();
//                 String sObjectId = saleEntry.getObjectId();
//                 String sObjectNo =saleEntry.getObjectNo();
//                 String sObjectName =saleEntry.getObjectName();
//                 dPaidAmount = dDocumentAmount - dLeftAmount + dUsedCredit;
//                 if (dPaidAmount > dDocumentAmount)
//                 {
//                     dPaidAmount = dDocumentAmount;
//                 }
//                 dLeftAmountCanUse = dDocumentAmount - dPaidAmount;
//
//                 if (dLeftAmountCanUse >= dLoanAmountVehicle)//如果剩余金额大于当次贷款金额，直接更新原单
//                 {
//                	   if (dLoanAmountVehicle < 0)//如果贷款金额变小，说明原单据金额将增大
//                       {
//                		   List<FinanceDocumentEntries> saleVaryEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo,SALE_DOCUMENT_TYPE_VARY);
//                		   if(saleVaryEntryList!=null && saleVaryEntryList.size()>0){//存在变更单时优先处理变更单（删除或金额减小）
//                			   FinanceDocumentEntries saleVaryEntry = saleVaryEntryList.get(0);
//                			   double dLeftAmountVary =Tools.toDouble(saleVaryEntry.getLeftAmount());
//                			   double dDocumentAmountVary = Tools.toDouble(saleVaryEntry.getDocumentAmount());
//                			   double dRequestAmountVary = getRequestAmout(saleVaryEntry.getEntryId());
//                               String sDocumentIdVary = saleVaryEntry.getDocumentId();
//                               String sAfter_no = saleVaryEntry.getAfterNo();
//                			   if(StringUtils.isEmpty(sAfter_no)){
//                				   if ( 0>= dDocumentAmountVary+dLoanAmountVehicle)//如果变更单不足于处理
//                				   {
//                					   dao.delete(saleVaryEntry);
//                					   saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle - dDocumentAmountVary);
//                					   saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle - dDocumentAmountVary);
//                					   dao.update(saleEntry);
//                				   }else{
//                					   saleVaryEntry.setDocumentAmount(dDocumentAmountVary + dLoanAmountVehicle);
//                					   saleVaryEntry.setLeftAmount(dLeftAmountVary + dLoanAmountVehicle);
//                					   dao.update(saleVaryEntry);
//                				   }
//                			   }else{//如果变更单有被处理过
//                				   if (dDocumentAmountVary - dRequestAmountVary >= -dLoanAmountVehicle)//如果变更单剩余可用金额大于贷款增减量
//                				   {
//                					   saleVaryEntry.setDocumentAmount(dDocumentAmountVary + dLoanAmountVehicle);
//                					   saleVaryEntry.setLeftAmount(dLeftAmountVary + dLoanAmountVehicle );
//                					   dao.update(saleVaryEntry);
//                				   }else{//如果不足处理，更新变更单的同时要更新原单据分录
//                					   saleVaryEntry.setDocumentAmount(dRequestAmountVary);
//                					   saleVaryEntry.setLeftAmount( dLeftAmountVary - (dDocumentAmountVary - dRequestAmountVary));
//                					   dao.update(saleVaryEntry);
//
//                					   saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle - (dDocumentAmountVary - dRequestAmountVary));
//                					   saleEntry.setLeftAmount( dLeftAmount - dLoanAmountVehicle - (dDocumentAmountVary - dRequestAmountVary));
//                					   dao.update(saleEntry);
//                				   }
//                			   }
//                		   }else{//无变更单则直接更新
//                			   saleEntry.setDocumentAmount( dDocumentAmount - dLoanAmountVehicle);
//                			   saleEntry.setLeftAmount(  dLeftAmount - dLoanAmountVehicle);
//        					   dao.update(saleEntry);
//                		   }
//
//                       }else{//如果是增加贷款金额，因原单据可用剩余金额大于增减量，所以可直接更新原单
//                    	   saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle);
//            			   saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle);
//    					   dao.update(saleEntry);
//                       }
//                 }else{//如果可用金额不足于处理，则有可能产生变更单
//                	 if (dPaidAmount > 0)//更新原单据分录为的单据金额为已收金额（包括授信）
//                     {
//                		saleEntry.setDocumentAmount(dPaidAmount);
//           			   	saleEntry.setLeftAmount(dPaidAmount - (dDocumentAmount - dLeftAmount));
//           			   	dao.update(saleEntry);
//                     }
//
//                	  //多出的金额产生预收款
//          		   List<FinanceDocumentEntries> saleVaryEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo,SALE_DOCUMENT_TYPE_VARY);
//          		   if(saleVaryEntryList!=null && saleVaryEntryList.size()>0){
//          			 FinanceDocumentEntries saleVaryEntry = saleVaryEntryList.get(0);
//          			 double dLeftAmountVary =Tools.toDouble(saleVaryEntry.getLeftAmount());
//          			 double dDocumentAmountVary = Tools.toDouble(saleVaryEntry.getDocumentAmount());
//          			 double dRequestAmountVary = getRequestAmout(saleVaryEntry.getEntryId());
//          			 saleVaryEntry.setDocumentAmount(dDocumentAmountVary + (dLoanAmountVehicle - dLeftAmountCanUse));
//					 saleVaryEntry.setLeftAmount(dLeftAmountVary + (dLoanAmountVehicle - dLeftAmountCanUse) );
//					 dao.update(saleVaryEntry);
//
//          		   }else{
//          			 if (dLoanAmountVehicle - dLeftAmountCanUse > 0)
//                     {
//          				financeDocumentEntriesDao.insertEntryEx(sStationId, 19, (short)70, SALE_DOCUMENT_TYPE_VARY, sContractNo,
//                                                 sObjectId, sObjectNo, sObjectName, (short)10, dLoanAmountVehicle - dLeftAmountCanUse, null, null, new Timestamp(System.currentTimeMillis()));
//                     }
//          		   }
//                 }
//
//
//                 //判断原来是否已经存在客户贷款，有则在原单上修改，否则新增
//                 List<FinanceDocumentEntries> loanEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo,SALE_LOAN);
//                 if(loanEntryList!=null &&loanEntryList.size()>0){
//                	 FinanceDocumentEntries  loanEntry = loanEntryList.get(0);
//                	   double dLeftAmountCL = Tools.toDouble(loanEntry.getLeftAmount());
//                	   double dDocumentAmountCL = Tools.toDouble(loanEntry.getDocumentAmount());
//                	   double dUsedCreditCL = Tools.toDouble(loanEntry.getUsedCredit());
//                	   double dPaid = (dDocumentAmountCL - dLeftAmountCL + dUsedCreditCL) > dDocumentAmountCL ? dDocumentAmountCL : (dDocumentAmountCL - dLeftAmountCL + dUsedCreditCL);
//                	   double dUsableAmount = dDocumentAmountCL - dPaid;
//
//                       if (dUsableAmount + dLoanAmountVehicle + dLoanAmountCharge < 0){
//                    	   throw new ServiceException("销售合同的客户贷款已被处理，客户贷款可用剩余金额不足于当次处理");
//                       }
//                       //判断原来的客户贷款是否足于处理
//                       if (dLeftAmountCL + dLoanAmountVehicle + dLoanAmountCharge >= 0)
//                       {
//                           if (dDocumentAmountCL + dLoanAmountVehicle + dLoanAmountCharge < dUsedCreditCL)
//                           {
//                        	   throw new ServiceException("单据【消贷-客户贷款】使用了授信或担保收款，请冲红授信或担保的收款结算单后再试");
//
//                           }
//                           loanEntry.setDocumentAmount(dDocumentAmountCL + dLoanAmountVehicle + dLoanAmountCharge);
//                           loanEntry.setLeftAmount(dLeftAmountCL + dLoanAmountVehicle + dLoanAmountCharge);
//                           dao.update(loanEntry);
//                       }
//                       else
//                       {
//                    	   throw new ServiceException("销售合同的客户贷款已被处理，剩余客户贷款额不足于处理");
//                       }
//                 }else{//原来不存在客户贷款，新增
//                	 financeDocumentEntriesDao.insertEntryEx( sStationId,
//                              19, (short)10, "消贷-客户贷款", sContractNo,
//                              sObjectId, sObjectNo, sObjectName, (short)20, dLoanAmountVehicle + dLoanAmountCharge,
//                              sContractNo, sContractNo, new Timestamp(System.currentTimeMillis()));
//                 }
//			}else{
//				throw new ServiceException("审批失败：找不到销售合同单据分录");
//			}
//		}
        //处理消贷的分录
        if (isSab) {
            dealLoanDocumentWithSab(bugdet);
        } else {
            dealLoanDocment(bugdet);
        }


        //处理代理贷款，对贷款人产生应付款
        dealBillDocumentForAgent(bugdet);
    }


    /**
     * 处理代理贷款，对贷款人产生应付款
     *
     * @param bugdet
     */
    @SuppressWarnings("unchecked")
    private void dealBillDocumentForAgent(VehicleLoanBudget bugdet) {
        //代理贷款金额（差额）
        double dAgentDiff = getAgentAmount(bugdet);
        if (dAgentDiff == 0.00D) {
            return;
        }
        VwVehicleLoanBudget vwBudget = dao.get(VwVehicleLoanBudget.class, bugdet.getDocumentNo());
        if (vwBudget == null) {
            throw new ServiceException("审批失败：根据documentNo" + bugdet.getDocumentNo() + "未找到VwVehicleLoanBudget数据");
        }

        List<FinanceDocumentEntries> agentEntryList = (List<FinanceDocumentEntries>) dao.findByHql("from FinanceDocumentEntries WHERE documentType IN ('消贷-代理贷款') AND documentId=?", bugdet.getDocumentNo());
        if ((agentEntryList == null || agentEntryList.size() == 0) && dAgentDiff > 0) { //如果没有单据分录则生成
            financeDocumentEntriesDao.insertEntryEx(bugdet.getStationId(), 19, (short) 65, "消贷-代理贷款", bugdet.getDocumentNo(),
                    vwBudget.getLoanObjectId(), vwBudget.getLoanObjectNo(), vwBudget.getLoanObjectName(), (short) 70, dAgentDiff);
            return;
        }
        FinanceDocumentEntries agentEntry = agentEntryList.get(0);
        double dDocuemntAmount = Tools.toDouble(agentEntry.getDocumentAmount());//单据金额
        double dLeftAmount = Tools.toDouble(agentEntry.getLeftAmount());//单据金额
        double dRequestAmount = getRequestAmout(agentEntry.getEntryId());//请款金额
        double dLeftCanUse = dDocuemntAmount - dRequestAmount;

        if (dDocuemntAmount + dAgentDiff == 0 && dLeftAmount + dAgentDiff == 0 && StringUtils.isEmpty(agentEntry.getAfterNo())) {
            dao.delete(agentEntry);
            return;
        }
        if (dLeftCanUse + dAgentDiff >= 0) {
            agentEntry.setDocumentAmount(dDocuemntAmount + dAgentDiff);
            agentEntry.setLeftAmount(dDocuemntAmount + dAgentDiff);
            dao.update(agentEntry);
        } else {
            throw new ServiceException("【消贷-代理贷款】已经请款,修改后的代理贷款金额不能小于已请款金额");
        }

    }

    //获得代理金额合计
    @SuppressWarnings("unchecked")
    private double getAgentAmount(VehicleLoanBudget bugdet) {
        double dAmount = 0.00D;
        List<Double> resultList = (List<Double>) dao.findByHql("select SUM(agentAmount) as agentAmount from VehicleLoanBudgetDetails  where documentNo = ?", bugdet.getDocumentNo());
        if (resultList != null && resultList.size() > 0) {
            dAmount = Tools.toDouble(resultList.get(0));
        }
        return dAmount;
    }

    /**
     * 处理消贷的分录(反审)
     *
     * @param bugdet
     */
    private void dealLoanDocumentWithSab(VehicleLoanBudget bugdet) {
        List<List<Object>> details = baseDao.findBySql("SELECT charge.self_id FROM vehicle_loan_budget_charge charge \n" +
                "LEFT JOIN vehicle_loan_budget_details details ON charge.budget_detail_id=details.self_id\n" +
                "WHERE details.document_no=?", bugdet.getDocumentNo());
        if (null != details && !details.isEmpty()) {
            for (List<Object> detail : details) {
                List<FinanceDocumentEntries> entries = (List<FinanceDocumentEntries>) baseDao.findByHql(
                        "from FinanceDocumentEntries where documentType LIKE '消贷-%' and (documentId=? or documentId=?)",
                        detail.get(0), detail.get(0) + "_1");
                if (null != entries && !entries.isEmpty()) {
                    for (FinanceDocumentEntries entry : entries) {
                        if (StringUtils.isNotEmpty(entry.getAfterNo())) {
                            throw new ServiceException(String.format("%s已被后续处理，不能反审", entry.getDocumentType()));
                        }
                        baseDao.delete(entry);
                    }
                }
            }
        }
    }

    /**
     * 处理消贷的分录
     *
     * @param bugdet
     */
    private void dealLoanDocment(VehicleLoanBudget bugdet) {
        String sContractNo = bugdet.getSaleContractNo();
        if (Tools.toShort(bugdet.getLoanMode()) == 20) {
            sContractNo = bugdet.getDocumentNo();
        } else {
            sContractNo = bugdet.getDocumentNo() + "," + sContractNo;
        }
        VwVehicleLoanBudget vwBudget = dao.get(VwVehicleLoanBudget.class, bugdet.getDocumentNo());

        List<VehicleLoanBudgetDetails> budgetDetailList = vehicleLoanBudgetDao.getBudgetDetailList(bugdet.getDocumentNo());
        for (VehicleLoanBudgetDetails budgetDetail : budgetDetailList) {
            List<VwVehicleLoanBudgetCharge> budgetChargeList = vehicleLoanBudgetDao.getBudgetCharge(budgetDetail.getSelfId());
            String sVehicleVin = "";
            sVehicleVin = "," + (StringUtils.isEmpty(budgetDetail.getVehicleVin()) ? "" : budgetDetail.getVehicleVin());
            short nDetailStatus = Tools.toShort(budgetDetail.getStatus());
            for (VwVehicleLoanBudgetCharge budgetCharge : budgetChargeList) {
                short nMoneyType = Tools.toShort(budgetCharge.getMoneyType());
                int nEntryProperty = 19;
                double dIncome = Tools.toDouble(budgetCharge.getIncome()); //收入
                double dExpenditure = Tools.toDouble(budgetCharge.getExpenditure()); //支出
                double dLoanPrice = Tools.toDouble(budgetCharge.getLoanAmount());//费用的贷款金额

                //应收对象默认=购车客户 ADM19010008
                BaseRelatedObjects arObject = baseDao.get(BaseRelatedObjects.class, vwBudget.getCustomerId());
                //如果有应收对象，默认给应收对象
                if (StringUtils.isNotBlank(budgetCharge.getArObjectId())) {
                    arObject = baseDao.get(BaseRelatedObjects.class, budgetCharge.getArObjectId());
                }

                short bytAmountType = 20;//应收款
                if (Tools.toShort(budgetCharge.getMoneyType(), (short) 10) == 60)//60为“预收款”类型
                {
                    bytAmountType = 10;//预收款类型
                }

                if (dIncome - dLoanPrice <= 0 && dExpenditure <= 0)
                    continue;
                if (dIncome - dLoanPrice > 0) {
                    //调了下默认值，张宇航说保证金和押金默认为购车客户，而不是贷款人 20180731
                    VehicleLoanBudgetCharge vehicleLoanBudgetCharge = baseDao.get(VehicleLoanBudgetCharge.class, budgetCharge.getSelfId());
                    financeDocumentEntriesDao.insertEntryEx(
                            bugdet.getStationId(), 19, (short) 10,
                            "消贷-" + budgetCharge.getChargeName(), budgetCharge.getSelfId(),
                            arObject.getObjectId(), arObject.getObjectNo(),
                            arObject.getObjectName(), bytAmountType, dIncome - dLoanPrice, sContractNo + sVehicleVin,
                            sContractNo, vehicleLoanBudgetCharge == null ? new Timestamp(System.currentTimeMillis()) : vehicleLoanBudgetCharge.getArDate()); //ADM19010056
                }
                if (dExpenditure > 0 && StringUtils.isNotEmpty(budgetCharge.getObjectId())) {
                    //调了下默认值，张宇航说保证金和押金默认为购车客户，而不是贷款人 20180731
                    //支出的对象是费用明细上所录的对象 20181108
                    financeDocumentEntriesDao.insertEntryEx(bugdet.getStationId(), nEntryProperty, (short) 65,
                            "消贷-" + budgetCharge.getChargeName(),
                            budgetCharge.getSelfId() + "_1",
                            budgetCharge.getObjectId(), budgetCharge.getObjectNo(), budgetCharge.getObjectName(),
                            (short) 70, dExpenditure, sContractNo + "," + arObject.getObjectName() + sVehicleVin,
                            sContractNo, budgetCharge.getPaymentDate());
                }

            }

        }
    }

    //根据entryId 查找请款金额合计
    private double getRequestAmout(String entryId) {
        double request = 0.00D;
        List<Map<String, Object>> resultList = vehicleLoanBudgetDao.getRequestAmountByEntryId(entryId);
        if (resultList != null && resultList.size() > 0) {
            request = Tools.toDouble((Double) resultList.get(0).get("request_amount"));
        }
        return request;
    }


    @SuppressWarnings("unchecked")
    private double getLoanAmountCharge(VehicleLoanBudget bugdet) {
        double dAmount = 0.00D;
        String hql = "select SUM(loanAmount) as loanAmount from  VehicleLoanBudgetCharge  where budgetDetailId IN (select selfId from VehicleLoanBudgetDetails  where documentNo = ?) AND ISNULL(status,0)<>30";
        List<Double> resultList = (List<Double>) dao.findByHql(hql, bugdet.getDocumentNo());
        if (resultList != null && resultList.size() > 0) {
            dAmount = Tools.toDouble(resultList.get(0));
        }
        return dAmount;
    }

    @SuppressWarnings("unchecked")
    private double getLoanAmountVehicle(VehicleLoanBudget bugdet) {
        double dAmount = 0.00D;
        List<Double> resultList = (List<Double>) dao.findByHql("select SUM(loanAmount) as loanAmount from VehicleLoanBudgetDetails  where documentNo = ?", bugdet.getDocumentNo());
        if (resultList != null && resultList.size() > 0) {
            dAmount = Tools.toDouble(resultList.get(0));
        }
        return dAmount;
    }

    //提交
    @Override
    public ApproveResult submitRecord(boolean agree, String documentNo, String moduleId, String comment, String modifyTime, Constant.OSType osType) {
        VehicleLoanBudget vehicleLoanBudget = this.getVehicleLoanBudget(documentNo);
        //校验数据版本
        this.validateModifyTime(modifyTime, vehicleLoanBudget.getModifyTime());

        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        vehicleLoanBudget.setModifyTime(now);
        vehicleLoanBudget.setModifierId(user.getUserId());
        vehicleLoanBudget.setSubmitStationId(user.getLoginStationId());
        vehicleLoanBudget.setSubmitStationName(user.getStationName());
        vehicleLoanBudget.setSubmitTime(now);
        return super.submitRecord(agree, documentNo, moduleId, comment, modifyTime, osType);
    }

    //撤销
    @Override
    public void revokingRecord(String documentNo, String modifyTime) {
        VehicleLoanBudget vehicleLoanBudget = this.getVehicleLoanBudget(documentNo);
        //撤销的校验
        if (vehicleLoanBudget.getStatus() >= 40) {
            throw new ServiceException("撤销失败:单据处于不可撤销状态");
        }
        //校验数据版本
        this.validateModifyTime(modifyTime, vehicleLoanBudget.getModifyTime());
        short status = null == vehicleLoanBudget.getStatus() ? Constant.DocumentStatus.IN_MAKING : vehicleLoanBudget.getStatus();
        if (status < Constant.DocumentStatus.SUBMITED) {
            vehicleLoanBudget.setFlowStatus((short) 2);
            List<VehicleLoanBudgetDetails> details = vehicleLoanBudgetDao.getBudgetDetailList(documentNo);
            if (null != details && !details.isEmpty()) {
                for (VehicleLoanBudgetDetails detail : details) {
                    detail.setStatus((short) 2);
                }
            }
            this.updateByTypeWithSab(vehicleLoanBudget);
        }
        logger.debug(String.format("消贷预算单：%s,状态：%s", documentNo, status));
        vehicleLoanBudget.setStatus(status < 20 ? Constant.DocumentStatus.REVOKED : Constant.DocumentStatus.IN_MAKING);

        vehicleLoanBudget.setRevokeTime(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public Object getReturnDataWithRevoke(String documentNo) {
        baseDao.flush();
        return loanBudgetService.convertReturnData(documentNo);
    }

    @Override
    public Object getReturnDataWithForbid(String documentNo) {
        baseDao.flush();
        return loanBudgetService.convertReturnData(documentNo);
    }

    //作废
    @Override
    public void forbiddingRecord(String documentNo, String modifyTime) {
        VehicleLoanBudget vehicleLoanBudget = this.getVehicleLoanBudget(documentNo);
        //校验数据版本
        this.validateModifyTime(modifyTime, vehicleLoanBudget.getModifyTime());
        this.doForbidRecord(vehicleLoanBudget);
        vehicleLoanBudget.setStatus(Constant.DocumentStatus.OBSOLETED);
        vehicleLoanBudget.setInvalidTime(new Timestamp(System.currentTimeMillis()));
    }

    private void doForbidRecord(VehicleLoanBudget vehicleLoanBudget) {
        Short status = vehicleLoanBudget.getStatus();
        if (null == status || status != Constant.DocumentStatus.AGREED) {
            throw new ServiceException(String.format("预算单审批状态(%s)不正确", null == status ? "空" : status.toString()));
        }
        List<List<Object>> arrangeNoArray = baseDao.findBySql("SELECT arrange_no FROM vehicle_loan_investigation_arrange WHERE status IN (0,1,10,20) AND budget_no=?", vehicleLoanBudget.getDocumentNo());
        if (null != arrangeNoArray && arrangeNoArray.size() > 0) {
            throw new ServiceException(String.format("已经指派征信工作单[%s]，不能作废", arrangeNoArray.get(0).get(0)));
        }
        //贷前管理是否需要征信安排
        boolean m_bNeedArrange = !StringUtils.equals("否", baseDao.getOptionValue("VEHICLE_LOAN_NEED_ARRANGE"));
        //消贷业务是否需要征信
        boolean m_bLoanNeedCredit = !StringUtils.equals("否", baseDao.getOptionValue("VEHICLE_LOAN_NEED_CREDIT"));

        if (!m_bNeedArrange) { //不需要征信安排时需要验证征信调查单是否已做
            List<List<Object>> documentNoArray = baseDao.findBySql("SELECT document_no FROM dbo.vehicle_loan_credit_investigation WHERE budget_no=? AND status=50", vehicleLoanBudget.getDocumentNo());
            if (null != documentNoArray && documentNoArray.size() > 0) {
                throw new ServiceException(String.format("已经建立征信调查单[%s]且已审批通过，不能作废！", documentNoArray.get(0).get(0)));
            }
        }

        List<VehicleLoanBudgetDetails> details = (List<VehicleLoanBudgetDetails>) baseDao.findByHql("from VehicleLoanBudgetDetails where documentNo=?", vehicleLoanBudget.getDocumentNo());

        if (!m_bLoanNeedCredit) {//不需要征信时，需要验证是否有建立贷款合同*/
            List<String> selfIdArray = new ArrayList<String>();
            for (VehicleLoanBudgetDetails detail : details) {
                selfIdArray.add("'" + detail.getSelfId() + "'");
            }
            List<List<Object>> budgetDetailIdArray = baseDao.findBySql("SELECT a.budget_detail_id FROM dbo.vehicle_loan_contracts_vehicles a\n" +
                    "LEFT JOIN dbo.vehicle_loan_contracts b ON b.slc_no = a.slc_no\n" +
                    "WHERE b.status IN (10,20,30,35) AND a.budget_detail_id IN (" + StringUtils.join(selfIdArray, ',') + ")");
            if (null != budgetDetailIdArray && !budgetDetailIdArray.isEmpty()) {
                throw new ServiceException("该预算单中已有车辆已建立贷款合同，不能作废");
            }
        }

        List<List<Object>> chargeIdArray = baseDao.findBySql("SELECT charge_id FROM dbo.vehicle_loan_budget_charge WHERE ISNULL(is_reimbursed,0)=1\n" +
                "AND budget_detail_id IN (SELECT x.self_id FROM dbo.vehicle_loan_budget_details x INNER JOIN \n" +
                "dbo.vehicle_loan_budget y ON y.document_no = x.document_no WHERE y.document_no=?)", vehicleLoanBudget.getDocumentNo());
        if (null != chargeIdArray && !chargeIdArray.isEmpty()) {
            throw new ServiceException("该预算单中已有费用已报销，不能作废");
        }

        vehicleLoanBudget.setFlowStatus((short) 2);

        this.dealBillDocument(vehicleLoanBudget, true);
        this.updateByTypeWithSab(vehicleLoanBudget);

        for (VehicleLoanBudgetDetails detail : details) {
            detail.setApproverId(null);
            detail.setStatus((short) 2);
            detail.setApproveTime(null);
        }
    }

    private VehicleLoanBudget getVehicleLoanBudget(String documentNo) {
        if (StringUtils.isEmpty(documentNo)) {
            throw new ServiceException("消贷费用预算单编号不能为空");
        }
        VehicleLoanBudget vehicleLoanBudget = baseDao.get(VehicleLoanBudget.class, documentNo);
        if (null == vehicleLoanBudget) {
            throw new ServiceException(String.format("未找到消贷费用预算单(%s)", documentNo));
        }
        return vehicleLoanBudget;
    }

    public static void main(String[] args) {
        double dAmount = 1234567.21000, loanAmount = 12345677.2345;
        String s = String.format("车辆贷款+费用贷款+代理贷款之和%3.2f小于该车已建立贷款合同的贷款金额%3.2f", dAmount, loanAmount);
        System.out.println(s);
    }

}
