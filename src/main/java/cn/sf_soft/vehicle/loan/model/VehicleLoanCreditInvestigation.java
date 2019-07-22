package cn.sf_soft.vehicle.loan.model;

import cn.sf_soft.office.approval.model.ApproveDocuments;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2017/12/1.
 */
@Entity
@Table(name = "vehicle_loan_credit_investigation", schema = "dbo")
public class VehicleLoanCreditInvestigation extends ApproveDocuments {

    private static final long serialVersionUID = -152233323269622037L;

    private String documentNo;
    private String stationId;
    private String arrangeNo;
    private Short status;
    private Byte goodBuyingRecord;
    private String fistPaySource;
    private String hrProvince;
    private String hrCity;
    private String hrArea;
    private String hrAddress;
    private String liveProvince;
    private String liveCity;
    private String liveArea;
    private String liveAddress;
    private String vnoId;
    private String purchaseUse;
    private String familySituation;
    private Byte mainCustomerFlag;
    private Byte drugTakingFlag;
    private Byte gambleFlag;
    private Byte delictFlag;
    private Byte debtFlag;
    private Byte housingLoanFlag;
    private String workHistory;
    private String economicCondition;
    private String certificateSituation;
    private String investigateId;
    private String investigateSuggestion;
    private String investigateMan;
    private String approveComment;
    private String auditComment;
    private String abortReason;
    private String warranterId;
    private String affiliatedCompanyId;
    private String remark;
    private String userId;
    private String userNo;
    private String userName;
    private String departmentId;
    private String departmentNo;
    private String departmentName;
    private String submitStationId;
    private String submitStationName;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private Timestamp submitTime;
    private String approverId;
    private String approverNo;
    private String approverName;
    private Timestamp approveTime;
    private String abortPersonId;
    private String abortPersonName;
    private Timestamp abortTime;
    private String budgetNo;
    private String warranter2Id;

    @Id
    @Column(name = "document_no", nullable = false, length = 40)
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 10)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "arrange_no", nullable = true, length = 40)
    public String getArrangeNo() {
        return arrangeNo;
    }

    public void setArrangeNo(String arrangeNo) {
        this.arrangeNo = arrangeNo;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "good_buying_record", nullable = true)
    public Byte getGoodBuyingRecord() {
        return goodBuyingRecord;
    }

    public void setGoodBuyingRecord(Byte goodBuyingRecord) {
        this.goodBuyingRecord = goodBuyingRecord;
    }

    @Basic
    @Column(name = "fist_pay_source", nullable = true, length = 40)
    public String getFistPaySource() {
        return fistPaySource;
    }

    public void setFistPaySource(String fistPaySource) {
        this.fistPaySource = fistPaySource;
    }

    @Basic
    @Column(name = "hr_province", nullable = true, length = 40)
    public String getHrProvince() {
        return hrProvince;
    }

    public void setHrProvince(String hrProvince) {
        this.hrProvince = hrProvince;
    }

    @Basic
    @Column(name = "hr_city", nullable = false, length = 40)
    public String getHrCity() {
        return hrCity;
    }

    public void setHrCity(String hrCity) {
        this.hrCity = hrCity;
    }

    @Basic
    @Column(name = "hr_area", nullable = false, length = 40)
    public String getHrArea() {
        return hrArea;
    }

    public void setHrArea(String hrArea) {
        this.hrArea = hrArea;
    }

    @Basic
    @Column(name = "hr_address", nullable = false, length = 100)
    public String getHrAddress() {
        return hrAddress;
    }

    public void setHrAddress(String hrAddress) {
        this.hrAddress = hrAddress;
    }

    @Basic
    @Column(name = "live_province", nullable = false, length = 40)
    public String getLiveProvince() {
        return liveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        this.liveProvince = liveProvince;
    }

    @Basic
    @Column(name = "live_city", nullable = false, length = 40)
    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity;
    }

    @Basic
    @Column(name = "live_area", nullable = false, length = 40)
    public String getLiveArea() {
        return liveArea;
    }

    public void setLiveArea(String liveArea) {
        this.liveArea = liveArea;
    }

    @Basic
    @Column(name = "live_address", nullable = false, length = 100)
    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    @Basic
    @Column(name = "vno_id", nullable = true, length = 40)
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Basic
    @Column(name = "purchase_use", nullable = true, length = 40)
    public String getPurchaseUse() {
        return purchaseUse;
    }

    public void setPurchaseUse(String purchaseUse) {
        this.purchaseUse = purchaseUse;
    }

    @Basic
    @Column(name = "family_situation", nullable = true, length = 2147483647)
    public String getFamilySituation() {
        return familySituation;
    }

    public void setFamilySituation(String familySituation) {
        this.familySituation = familySituation;
    }

    @Basic
    @Column(name = "main_customer_flag", nullable = true)
    public Byte getMainCustomerFlag() {
        return mainCustomerFlag;
    }

    public void setMainCustomerFlag(Byte mainCustomerFlag) {
        this.mainCustomerFlag = mainCustomerFlag;
    }

    @Basic
    @Column(name = "drug_taking_flag", nullable = true)
    public Byte getDrugTakingFlag() {
        return drugTakingFlag;
    }

    public void setDrugTakingFlag(Byte drugTakingFlag) {
        this.drugTakingFlag = drugTakingFlag;
    }

    @Basic
    @Column(name = "gamble_flag", nullable = true)
    public Byte getGambleFlag() {
        return gambleFlag;
    }

    public void setGambleFlag(Byte gambleFlag) {
        this.gambleFlag = gambleFlag;
    }

    @Basic
    @Column(name = "delict_flag", nullable = true)
    public Byte getDelictFlag() {
        return delictFlag;
    }

    public void setDelictFlag(Byte delictFlag) {
        this.delictFlag = delictFlag;
    }

    @Basic
    @Column(name = "debt_flag", nullable = true)
    public Byte getDebtFlag() {
        return debtFlag;
    }

    public void setDebtFlag(Byte debtFlag) {
        this.debtFlag = debtFlag;
    }

    @Basic
    @Column(name = "housing_loan_flag", nullable = true)
    public Byte getHousingLoanFlag() {
        return housingLoanFlag;
    }

    public void setHousingLoanFlag(Byte housingLoanFlag) {
        this.housingLoanFlag = housingLoanFlag;
    }

    @Basic
    @Column(name = "work_history", nullable = true, length = 2147483647)
    public String getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(String workHistory) {
        this.workHistory = workHistory;
    }

    @Basic
    @Column(name = "economic_condition", nullable = true, length = 2147483647)
    public String getEconomicCondition() {
        return economicCondition;
    }

    public void setEconomicCondition(String economicCondition) {
        this.economicCondition = economicCondition;
    }

    @Basic
    @Column(name = "certificate_situation", nullable = true, length = 40)
    public String getCertificateSituation() {
        return certificateSituation;
    }

    public void setCertificateSituation(String certificateSituation) {
        this.certificateSituation = certificateSituation;
    }

    @Basic
    @Column(name = "investigate_id", nullable = true, length = 2147483647)
    public String getInvestigateId() {
        return investigateId;
    }

    public void setInvestigateId(String investigateId) {
        this.investigateId = investigateId;
    }

    @Basic
    @Column(name = "investigate_suggestion", nullable = true, length = 2147483647)
    public String getInvestigateSuggestion() {
        return investigateSuggestion;
    }

    public void setInvestigateSuggestion(String investigateSuggestion) {
        this.investigateSuggestion = investigateSuggestion;
    }

    @Basic
    @Column(name = "investigate_man", nullable = true, length = 2147483647)
    public String getInvestigateMan() {
        return investigateMan;
    }

    public void setInvestigateMan(String investigateMan) {
        this.investigateMan = investigateMan;
    }

    @Basic
    @Column(name = "approve_comment", nullable = true, length = 2147483647)
    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }

    @Basic
    @Column(name = "audit_comment", nullable = true, length = 2147483647)
    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    @Basic
    @Column(name = "abort_reason", nullable = true, length = 2147483647)
    public String getAbortReason() {
        return abortReason;
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }

    @Basic
    @Column(name = "warranter_id", nullable = true, length = 40)
    public String getWarranterId() {
        return warranterId;
    }

    public void setWarranterId(String warranterId) {
        this.warranterId = warranterId;
    }

    @Basic
    @Column(name = "affiliated_company_id", nullable = true, length = 40)
    public String getAffiliatedCompanyId() {
        return affiliatedCompanyId;
    }

    public void setAffiliatedCompanyId(String affiliatedCompanyId) {
        this.affiliatedCompanyId = affiliatedCompanyId;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 2147483647)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 40)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_no", nullable = true, length = 10)
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 10)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "department_id", nullable = true, length = 40)
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_no", nullable = true, length = 40)
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Basic
    @Column(name = "department_name", nullable = false, length = 40)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "submit_station_id", nullable = true, length = 40)
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Basic
    @Column(name = "submit_station_name", nullable = false, length = 10)
    public String getSubmitStationName() {
        return submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Basic
    @Column(name = "creator", nullable = true, length = 20)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modifier", nullable = true, length = 20)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "modify_time", nullable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "approver_id", nullable = true, length = 2147483647)
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no", nullable = true, length = 2147483647)
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver_name", nullable = true, length = 2147483647)
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Basic
    @Column(name = "approve_time", nullable = true)
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Basic
    @Column(name = "abort_person_id", nullable = true, length = 40)
    public String getAbortPersonId() {
        return abortPersonId;
    }

    public void setAbortPersonId(String abortPersonId) {
        this.abortPersonId = abortPersonId;
    }

    @Basic
    @Column(name = "abort_person_name", nullable = true, length = 20)
    public String getAbortPersonName() {
        return abortPersonName;
    }

    public void setAbortPersonName(String abortPersonName) {
        this.abortPersonName = abortPersonName;
    }

    @Basic
    @Column(name = "abort_time", nullable = true)
    public Timestamp getAbortTime() {
        return abortTime;
    }

    public void setAbortTime(Timestamp abortTime) {
        this.abortTime = abortTime;
    }

    @Basic
    @Column(name = "budget_no", nullable = true, length = 40)
    public String getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    @Basic
    @Column(name = "warranter2_id", nullable = true, length = 40)
    public String getWarranter2Id() {
        return warranter2Id;
    }

    public void setWarranter2Id(String warranter2Id) {
        this.warranter2Id = warranter2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleLoanCreditInvestigation that = (VehicleLoanCreditInvestigation) o;

        if (status != that.status) return false;
        if (documentNo != null ? !documentNo.equals(that.documentNo) : that.documentNo != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (arrangeNo != null ? !arrangeNo.equals(that.arrangeNo) : that.arrangeNo != null) return false;
        if (goodBuyingRecord != null ? !goodBuyingRecord.equals(that.goodBuyingRecord) : that.goodBuyingRecord != null)
            return false;
        if (fistPaySource != null ? !fistPaySource.equals(that.fistPaySource) : that.fistPaySource != null)
            return false;
        if (hrProvince != null ? !hrProvince.equals(that.hrProvince) : that.hrProvince != null) return false;
        if (hrCity != null ? !hrCity.equals(that.hrCity) : that.hrCity != null) return false;
        if (hrArea != null ? !hrArea.equals(that.hrArea) : that.hrArea != null) return false;
        if (hrAddress != null ? !hrAddress.equals(that.hrAddress) : that.hrAddress != null) return false;
        if (liveProvince != null ? !liveProvince.equals(that.liveProvince) : that.liveProvince != null) return false;
        if (liveCity != null ? !liveCity.equals(that.liveCity) : that.liveCity != null) return false;
        if (liveArea != null ? !liveArea.equals(that.liveArea) : that.liveArea != null) return false;
        if (liveAddress != null ? !liveAddress.equals(that.liveAddress) : that.liveAddress != null) return false;
        if (vnoId != null ? !vnoId.equals(that.vnoId) : that.vnoId != null) return false;
        if (purchaseUse != null ? !purchaseUse.equals(that.purchaseUse) : that.purchaseUse != null) return false;
        if (familySituation != null ? !familySituation.equals(that.familySituation) : that.familySituation != null)
            return false;
        if (mainCustomerFlag != null ? !mainCustomerFlag.equals(that.mainCustomerFlag) : that.mainCustomerFlag != null)
            return false;
        if (drugTakingFlag != null ? !drugTakingFlag.equals(that.drugTakingFlag) : that.drugTakingFlag != null)
            return false;
        if (gambleFlag != null ? !gambleFlag.equals(that.gambleFlag) : that.gambleFlag != null) return false;
        if (delictFlag != null ? !delictFlag.equals(that.delictFlag) : that.delictFlag != null) return false;
        if (debtFlag != null ? !debtFlag.equals(that.debtFlag) : that.debtFlag != null) return false;
        if (housingLoanFlag != null ? !housingLoanFlag.equals(that.housingLoanFlag) : that.housingLoanFlag != null)
            return false;
        if (workHistory != null ? !workHistory.equals(that.workHistory) : that.workHistory != null) return false;
        if (economicCondition != null ? !economicCondition.equals(that.economicCondition) : that.economicCondition != null)
            return false;
        if (certificateSituation != null ? !certificateSituation.equals(that.certificateSituation) : that.certificateSituation != null)
            return false;
        if (investigateId != null ? !investigateId.equals(that.investigateId) : that.investigateId != null)
            return false;
        if (investigateSuggestion != null ? !investigateSuggestion.equals(that.investigateSuggestion) : that.investigateSuggestion != null)
            return false;
        if (investigateMan != null ? !investigateMan.equals(that.investigateMan) : that.investigateMan != null)
            return false;
        if (approveComment != null ? !approveComment.equals(that.approveComment) : that.approveComment != null)
            return false;
        if (auditComment != null ? !auditComment.equals(that.auditComment) : that.auditComment != null) return false;
        if (abortReason != null ? !abortReason.equals(that.abortReason) : that.abortReason != null) return false;
        if (warranterId != null ? !warranterId.equals(that.warranterId) : that.warranterId != null) return false;
        if (affiliatedCompanyId != null ? !affiliatedCompanyId.equals(that.affiliatedCompanyId) : that.affiliatedCompanyId != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userNo != null ? !userNo.equals(that.userNo) : that.userNo != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (departmentNo != null ? !departmentNo.equals(that.departmentNo) : that.departmentNo != null) return false;
        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        if (submitStationId != null ? !submitStationId.equals(that.submitStationId) : that.submitStationId != null)
            return false;
        if (submitStationName != null ? !submitStationName.equals(that.submitStationName) : that.submitStationName != null)
            return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (submitTime != null ? !submitTime.equals(that.submitTime) : that.submitTime != null) return false;
        if (approverId != null ? !approverId.equals(that.approverId) : that.approverId != null) return false;
        if (approverNo != null ? !approverNo.equals(that.approverNo) : that.approverNo != null) return false;
        if (approverName != null ? !approverName.equals(that.approverName) : that.approverName != null) return false;
        if (approveTime != null ? !approveTime.equals(that.approveTime) : that.approveTime != null) return false;
        if (abortPersonId != null ? !abortPersonId.equals(that.abortPersonId) : that.abortPersonId != null)
            return false;
        if (abortPersonName != null ? !abortPersonName.equals(that.abortPersonName) : that.abortPersonName != null)
            return false;
        if (abortTime != null ? !abortTime.equals(that.abortTime) : that.abortTime != null) return false;
        if (budgetNo != null ? !budgetNo.equals(that.budgetNo) : that.budgetNo != null) return false;
        if (warranter2Id != null ? !warranter2Id.equals(that.warranter2Id) : that.warranter2Id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentNo != null ? documentNo.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (arrangeNo != null ? arrangeNo.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + (goodBuyingRecord != null ? goodBuyingRecord.hashCode() : 0);
        result = 31 * result + (fistPaySource != null ? fistPaySource.hashCode() : 0);
        result = 31 * result + (hrProvince != null ? hrProvince.hashCode() : 0);
        result = 31 * result + (hrCity != null ? hrCity.hashCode() : 0);
        result = 31 * result + (hrArea != null ? hrArea.hashCode() : 0);
        result = 31 * result + (hrAddress != null ? hrAddress.hashCode() : 0);
        result = 31 * result + (liveProvince != null ? liveProvince.hashCode() : 0);
        result = 31 * result + (liveCity != null ? liveCity.hashCode() : 0);
        result = 31 * result + (liveArea != null ? liveArea.hashCode() : 0);
        result = 31 * result + (liveAddress != null ? liveAddress.hashCode() : 0);
        result = 31 * result + (vnoId != null ? vnoId.hashCode() : 0);
        result = 31 * result + (purchaseUse != null ? purchaseUse.hashCode() : 0);
        result = 31 * result + (familySituation != null ? familySituation.hashCode() : 0);
        result = 31 * result + (mainCustomerFlag != null ? mainCustomerFlag.hashCode() : 0);
        result = 31 * result + (drugTakingFlag != null ? drugTakingFlag.hashCode() : 0);
        result = 31 * result + (gambleFlag != null ? gambleFlag.hashCode() : 0);
        result = 31 * result + (delictFlag != null ? delictFlag.hashCode() : 0);
        result = 31 * result + (debtFlag != null ? debtFlag.hashCode() : 0);
        result = 31 * result + (housingLoanFlag != null ? housingLoanFlag.hashCode() : 0);
        result = 31 * result + (workHistory != null ? workHistory.hashCode() : 0);
        result = 31 * result + (economicCondition != null ? economicCondition.hashCode() : 0);
        result = 31 * result + (certificateSituation != null ? certificateSituation.hashCode() : 0);
        result = 31 * result + (investigateId != null ? investigateId.hashCode() : 0);
        result = 31 * result + (investigateSuggestion != null ? investigateSuggestion.hashCode() : 0);
        result = 31 * result + (investigateMan != null ? investigateMan.hashCode() : 0);
        result = 31 * result + (approveComment != null ? approveComment.hashCode() : 0);
        result = 31 * result + (auditComment != null ? auditComment.hashCode() : 0);
        result = 31 * result + (abortReason != null ? abortReason.hashCode() : 0);
        result = 31 * result + (warranterId != null ? warranterId.hashCode() : 0);
        result = 31 * result + (affiliatedCompanyId != null ? affiliatedCompanyId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (departmentNo != null ? departmentNo.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (submitStationId != null ? submitStationId.hashCode() : 0);
        result = 31 * result + (submitStationName != null ? submitStationName.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (submitTime != null ? submitTime.hashCode() : 0);
        result = 31 * result + (approverId != null ? approverId.hashCode() : 0);
        result = 31 * result + (approverNo != null ? approverNo.hashCode() : 0);
        result = 31 * result + (approverName != null ? approverName.hashCode() : 0);
        result = 31 * result + (approveTime != null ? approveTime.hashCode() : 0);
        result = 31 * result + (abortPersonId != null ? abortPersonId.hashCode() : 0);
        result = 31 * result + (abortPersonName != null ? abortPersonName.hashCode() : 0);
        result = 31 * result + (abortTime != null ? abortTime.hashCode() : 0);
        result = 31 * result + (budgetNo != null ? budgetNo.hashCode() : 0);
        result = 31 * result + (warranter2Id != null ? warranter2Id.hashCode() : 0);
        return result;
    }
}
