package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.util.Constant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ServiceWorkListConsigns;
import cn.sf_soft.office.approval.model.ServiceWorkListItems;
import cn.sf_soft.office.approval.model.ServiceWorkListParts;
import cn.sf_soft.office.approval.model.ServiceWorkListProposedItems;
import cn.sf_soft.office.approval.model.ServiceWorkLists;
import cn.sf_soft.office.approval.model.ServiceWorkListsCharge;

/**
 * 折扣审批
 */
@Service
public class ServiceWorkAgiokApprove extends BaseApproveProcess {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceWorkAgiokApprove.class);

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "20102110";

    @Autowired
    private BaseDao baseDao;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<ServiceWorkLists> list = (List<ServiceWorkLists>) baseDao
                .findByHql("from ServiceWorkLists where documentNo=?", documentNo);
        if (null == list || list.isEmpty()) {
            return null;
        }
        ServiceWorkLists serviceWork = list.get(0);
        serviceWork.setItems((List<ServiceWorkListItems>)baseDao.findByHql("from ServiceWorkListItems where taskNo=?", serviceWork.getTaskNo()));
        serviceWork.setParts((List<ServiceWorkListParts>)baseDao.findByHql("from ServiceWorkListParts where taskNo=?", serviceWork.getTaskNo()));
        serviceWork.setConsigns((List<ServiceWorkListConsigns>)baseDao.findByHql("from ServiceWorkListConsigns where taskNo=?", serviceWork.getTaskNo()));
        serviceWork.setCharges((List<ServiceWorkListsCharge>)baseDao.findByHql("from ServiceWorkListsCharge where taskNo=?", serviceWork.getTaskNo()));
        serviceWork.setProposedItems((List<ServiceWorkListProposedItems>)baseDao.findByHql("from ServiceWorkListProposedItems where taskNo=?", serviceWork.getTaskNo()));
        return serviceWork;
    }

    @Override
    protected String getApprovalPopedomId() {
        return this.approvalPopedomId;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

}