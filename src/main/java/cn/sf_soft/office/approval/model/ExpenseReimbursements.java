package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * 费用报销 OfficeExpenseReimbursements entity.
 *
 * @author caigx
 */
public class ExpenseReimbursements extends ApproveDocuments<ExpenseReimbursementsDetails> implements java.io.Serializable {

    private static final long serialVersionUID = -628865789473143830L;
    // Fields

    private String expenseId;
    private String expenseNo;
    private String expenseName;
    private Double reimburseAmount;
    private Timestamp reimburseTime;
    private Short apportionMode;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    // private Timestamp modifyTime;

    // private Set<ExpenseReimbursementsApportionments> apportionment;//分摊部门
    private List<ExpenseReimbursementAdditional> additionalInfo;// 附加信息：分费用类型，分摊部门，站点，的预算和已报金额

    private String apportionModeMean;// 费用分摊方式

    private Double paidAmount;
    private Timestamp paidTime;

    private String fileUrls;

    // Constructors
    public ExpenseReimbursements() {
    }


    // Property accessors

    @Override
    public String getDocumentNo() {
        return this.documentNo;
    }

    @Override
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Override
    public String getStationId() {
        return this.stationId;
    }

    @Override
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
    public Short getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Short status) {
        this.status = status;
    }

    public String getExpenseId() {
        return this.expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseNo() {
        return this.expenseNo;
    }

    public void setExpenseNo(String expenseNo) {
        this.expenseNo = expenseNo;
    }

    public String getExpenseName() {
        return this.expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Double getReimburseAmount() {
        return this.reimburseAmount;
    }

    public void setReimburseAmount(Double reimburseAmount) {
        this.reimburseAmount = reimburseAmount;
    }

    public Timestamp getReimburseTime() {
        return this.reimburseTime;
    }

    public void setReimburseTime(Timestamp reimburseTime) {
        this.reimburseTime = reimburseTime;
    }

    public Short getApportionMode() {
        return this.apportionMode;
    }

    public void setApportionMode(Short apportionMode) {
        this.apportionMode = apportionMode;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserNo() {
        return this.userNo;
    }

    @Override
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getDepartmentId() {
        return this.departmentId;
    }

    @Override
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String getDepartmentNo() {
        return this.departmentNo;
    }

    @Override
    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Override
    public String getDepartmentName() {
        return this.departmentName;
    }

    @Override
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String getSubmitStationId() {
        return this.submitStationId;
    }

    @Override
    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Override
    public String getSubmitStationName() {
        return this.submitStationName;
    }

    @Override
    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /*
     * @Override public Timestamp getModifyTime() { return this.modifyTime; }
     *
     * public void setModifyTime(Timestamp modifyTime) { this.modifyTime =
     * modifyTime; }
     */

    @Override
    public Timestamp getSubmitTime() {
        return this.submitTime;
    }

    @Override
    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String getApproverId() {
        return this.approverId;
    }

    @Override
    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Override
    public String getApproverNo() {
        return this.approverNo;
    }

    @Override
    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Override
    public String getApproverName() {
        return this.approverName;
    }

    @Override
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    // public void setApportionment(Set<ExpenseReimbursementsApportionments>
    // apportionment) {
    // this.apportionment = apportionment;
    // }
    //
    // public Set<ExpenseReimbursementsApportionments> getApportionment() {
    // return apportionment;
    // }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (chargeDetail != null) {
            Iterator<ExpenseReimbursementsDetails> it = chargeDetail.iterator();
            while (it.hasNext()) {
                sb.append("\n\t" + it.next().toString());
            }
            // for(ExpenseReimbursementsApportionments supporter:apportionment){
            // sb.append("\n\t"+supporter.toString());
            // }
        }
        if (additionalInfo != null && additionalInfo.size() > 0) {
            for (ExpenseReimbursementAdditional a : additionalInfo) {
                sb.append("\n\t" + a.toString());
            }
        }
        return "ApproveDocuments [approverId=" + approverId + ", approverName="
                + approverName + ", approverNo=" + approverNo
                + ", departmentId=" + departmentId + ", departmentName="
                + departmentName + ", departmentNo=" + departmentNo
                + ", documentNo=" + documentNo + ", stationId=" + stationId
                + ", status=" + status + ", submitStationId=" + submitStationId
                + ", submitStationName=" + submitStationName + ", submitTime="
                + submitTime + ", userId=" + userId + ", userName=" + userName
                + ", userNo=" + userNo + ", apportionModeMean="
                + apportionModeMean + "]" + sb.toString();
    }

    public void setAdditionalInfo(
            List<ExpenseReimbursementAdditional> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<ExpenseReimbursementAdditional> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setApportionModeMean(String apportionModeMean) {
        this.apportionModeMean = apportionModeMean;
    }

    public String getApportionModeMean() {
        return apportionModeMean;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Timestamp getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Timestamp paidTime) {
        this.paidTime = paidTime;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }
}
