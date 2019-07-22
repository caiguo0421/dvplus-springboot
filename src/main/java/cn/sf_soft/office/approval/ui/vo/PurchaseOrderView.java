package cn.sf_soft.office.approval.ui.vo;

import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

/**
 * 车辆采购订单的view
 */
public class PurchaseOrderView extends VwOfficeApproveDocuments {

    private static final long serialVersionUID = 4049934305103010654L;



    public PurchaseOrderView() {

    }


    public static PurchaseOrderView fillDataByVwDocuments(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
        PurchaseOrderView view = new PurchaseOrderView();
        view.setDocumentId(vwOfficeApproveDocuments.getDocumentId());
        view.setStationId(vwOfficeApproveDocuments.getStationId());
        view.setStatus(vwOfficeApproveDocuments.getStatus());
        view.setModuleId(vwOfficeApproveDocuments.getModuleId());
        view.setModuleName(vwOfficeApproveDocuments.getModuleName());
        view.setDocumentNo(vwOfficeApproveDocuments.getDocumentNo());
        view.setUserId(vwOfficeApproveDocuments.getUserId());
        view.setUserNo(vwOfficeApproveDocuments.getUserNo());
        view.setUserName(vwOfficeApproveDocuments.getUserName());
        view.setDepartmentId(vwOfficeApproveDocuments.getDepartmentId());
        view.setDepartmentNo(vwOfficeApproveDocuments.getDepartmentNo());
        view.setDepartmentName(vwOfficeApproveDocuments.getDepartmentName());
        view.setSubmitStationId(vwOfficeApproveDocuments.getSubmitStationId());
        view.setSubmitStationName(vwOfficeApproveDocuments.getSubmitStationName());
        view.setSubmitTime(vwOfficeApproveDocuments.getSubmitTime());
        view.setApproveLevel(vwOfficeApproveDocuments.getApproveLevel());
        view.setApproveName(vwOfficeApproveDocuments.getApproveName());
        view.setApproverId(vwOfficeApproveDocuments.getApproverId());
        view.setApproverNo(vwOfficeApproveDocuments.getApproverNo());
        view.setApproverName(vwOfficeApproveDocuments.getApproverName());
        view.setRevokeTime(vwOfficeApproveDocuments.getRevokeTime());
        view.setInvalidTime(vwOfficeApproveDocuments.getInvalidTime());
        view.setDocumentAmount(vwOfficeApproveDocuments.getDocumentAmount());
        return view;
    }


}