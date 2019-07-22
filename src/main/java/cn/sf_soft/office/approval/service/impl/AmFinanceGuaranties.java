package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dao.VwFinanceGuarantorsDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceGuaranties;
import cn.sf_soft.office.approval.model.VwFinanceGuarantors;
import cn.sf_soft.office.approval.model.VwFinanceGuarantorsId;

/**
 * 应收担保管理实现类
 * 
 * @author king
 * @created 2013-02-27
 */
@Service("financeGuarantiesManager")
public class AmFinanceGuaranties extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40452020";
	
	@Autowired
	@Qualifier("vwFinanceGuarantorDao")
	private VwFinanceGuarantorsDao vwFinanceGuarantorsDao;// 担保人 DAO

	public void setVwFinanceGuarantorsDao(
			VwFinanceGuarantorsDao vwFinanceGuarantorsDao) {
		this.vwFinanceGuarantorsDao = vwFinanceGuarantorsDao;
	}
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		FinanceGuaranties guaranty = getDocumentDetail(approveDocument
				.getDocumentNo());
		if (guaranty == null) {
			// 找不到担保单
			// return MessageResult.APPROVE_ERROR_GUARANTIES_NOT_FOUND;
			throw new ServiceException("审批失败:找不到该担保单");
		}
		List<VwFinanceGuarantors> guarantors = vwFinanceGuarantorsDao
				.findByProperty("id.userId", guaranty.getUserId());
		if (guarantors != null && guarantors.size() > 0) {
			VwFinanceGuarantorsId guarantorsId = guarantors.get(0).getId();
			double guarantyAmount = guaranty.getGuarantyAmount();
			double validAmount = guarantorsId.getGuarantyLimit()
					- guarantorsId.getGuaranteedAmount();// 担保人的可担保额度
			if (guarantyAmount > validAmount) {
				// 担保额超过该担保人可担保额度
				// return MessageResult.APPROVE_ERROR_GUARANTY_AMOUNT_TOO_BIG;
				throw new ServiceException("审批失败：担保额度超过该担保人的可担保额度");
			}
		} else {
			// 未找到该担保人的信息
			// return MessageResult.APPROVE_ERROR_GUARANTOR_NOT_FUND;
			throw new ServiceException("审批失败：找不到该担保人的信息");
		}

		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public FinanceGuaranties getDocumentDetail(String documentNo) {
		return dao.get(FinanceGuaranties.class, documentNo);

	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		FinanceGuaranties financeGuaranties = dao.get(FinanceGuaranties.class,
				documentNo);

		Timestamp lastModifyTime = financeGuaranties.getModifyTime();
		if (lastModifyTime == null) {
			return false;
		}
		if (null != modifyTime && !"".equals(modifyTime)) {

			Timestamp timestamp = Timestamp.valueOf(modifyTime);
			return compareTime(timestamp, lastModifyTime);
		}
		return true;
	}**/

}
