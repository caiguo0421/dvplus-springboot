package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 18-5-17.
 */
@Entity
@Table(name = "vehicle_loan_contracts", schema = "dbo")
public class VehicleLoanContracts {
    private String slcNo;
    private String slcCode;
    private String stationId;
    private String customerId;
    private String loanObjectId;
    private String agentId;
    private byte loanMode;
    private String loanUnitId;
    private BigDecimal loanAmount4s;
    private Byte loanType;
    private Byte payType;
    private String warranter1Id;
    private String warranter2Id;
    private String dunningPeopleId;
    private byte status;
    private Integer arPeriodNumber;
    private String arRateTypeUsed;
    private BigDecimal arInterestRate;
    private Byte arRateType;
    private Timestamp arFirstPayDate;
    private Byte arConfirmStatus;
    private String arConfirmerId;
    private String arConfirmerDepartmentId;
    private Timestamp arConfirmDate;
    private Timestamp arAppropriationDate;
    private BigDecimal apLoanAmount;
    private Integer apPeriodNumber;
    private String apRateTypeUsed;
    private BigDecimal apInterestRate;
    private Byte apRateType;
    private Timestamp apFirstPayDate;
    private Byte apConfirmStatus;
    private String apConfirmerId;
    private String apConfirmerDepartmentId;
    private Timestamp apConfirmDate;
    private Timestamp apAppropriationDate;
    private String remark;
    private String relationNo;
    private String creatorId;
    private Timestamp createTime;
    private String modifierId;
    private Timestamp modifyTime;
    private String approverId;
    private String approverDepartmentId;
    private Timestamp approveTime;
    private Timestamp abortTime;
    private String abortPersonId;
    private String errorCode;
    private String errorMsg;
    private String affiliatedCompanyId;
    private String saleContractNo;
    private String creditNo;
    private String budgetNo;
    private String vehicleVin;

    @Id
    @Column(name = "slc_no")
    public String getSlcNo() {
        return slcNo;
    }

    public void setSlcNo(String slcNo) {
        this.slcNo = slcNo;
    }

    @Basic
    @Column(name = "slc_code")
    public String getSlcCode() {
        return slcCode;
    }

    public void setSlcCode(String slcCode) {
        this.slcCode = slcCode;
    }

    @Basic
    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "loan_object_id")
    public String getLoanObjectId() {
        return loanObjectId;
    }

    public void setLoanObjectId(String loanObjectId) {
        this.loanObjectId = loanObjectId;
    }

    @Basic
    @Column(name = "agent_id")
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Basic
    @Column(name = "loan_mode")
    public byte getLoanMode() {
        return loanMode;
    }

    public void setLoanMode(byte loanMode) {
        this.loanMode = loanMode;
    }

    @Basic
    @Column(name = "loan_unit_id")
    public String getLoanUnitId() {
        return loanUnitId;
    }

    public void setLoanUnitId(String loanUnitId) {
        this.loanUnitId = loanUnitId;
    }

    @Basic
    @Column(name = "loan_type")
    public Byte getLoanType() {
        return loanType;
    }

    public void setLoanType(Byte loanType) {
        this.loanType = loanType;
    }

    @Basic
    @Column(name = "pay_type")
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "warranter1_id")
    public String getWarranter1Id() {
        return warranter1Id;
    }

    public void setWarranter1Id(String warranter1Id) {
        this.warranter1Id = warranter1Id;
    }

    @Basic
    @Column(name = "warranter2_id")
    public String getWarranter2Id() {
        return warranter2Id;
    }

    public void setWarranter2Id(String warranter2Id) {
        this.warranter2Id = warranter2Id;
    }

    @Basic
    @Column(name = "dunning_people_id")
    public String getDunningPeopleId() {
        return dunningPeopleId;
    }

    public void setDunningPeopleId(String dunningPeopleId) {
        this.dunningPeopleId = dunningPeopleId;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ar_period_number")
    public Integer getArPeriodNumber() {
        return arPeriodNumber;
    }

    public void setArPeriodNumber(Integer arPeriodNumber) {
        this.arPeriodNumber = arPeriodNumber;
    }

    @Basic
    @Column(name = "ar_rate_type_used")
    public String getArRateTypeUsed() {
        return arRateTypeUsed;
    }

    public void setArRateTypeUsed(String arRateTypeUsed) {
        this.arRateTypeUsed = arRateTypeUsed;
    }

    @Basic
    @Column(name = "ar_interest_rate")
    public BigDecimal getArInterestRate() {
        return arInterestRate;
    }

    public void setArInterestRate(BigDecimal arInterestRate) {
        this.arInterestRate = arInterestRate;
    }

    @Basic
    @Column(name = "ar_rate_type")
    public Byte getArRateType() {
        return arRateType;
    }

    public void setArRateType(Byte arRateType) {
        this.arRateType = arRateType;
    }

    @Basic
    @Column(name = "ar_first_pay_date")
    public Timestamp getArFirstPayDate() {
        return arFirstPayDate;
    }

    public void setArFirstPayDate(Timestamp arFirstPayDate) {
        this.arFirstPayDate = arFirstPayDate;
    }

    @Basic
    @Column(name = "ar_confirm_status")
    public Byte getArConfirmStatus() {
        return arConfirmStatus;
    }

    public void setArConfirmStatus(Byte arConfirmStatus) {
        this.arConfirmStatus = arConfirmStatus;
    }

    @Basic
    @Column(name = "ar_confirmer_id")
    public String getArConfirmerId() {
        return arConfirmerId;
    }

    public void setArConfirmerId(String arConfirmerId) {
        this.arConfirmerId = arConfirmerId;
    }

    @Basic
    @Column(name = "ar_confirmer_department_id")
    public String getArConfirmerDepartmentId() {
        return arConfirmerDepartmentId;
    }

    public void setArConfirmerDepartmentId(String arConfirmerDepartmentId) {
        this.arConfirmerDepartmentId = arConfirmerDepartmentId;
    }

    @Basic
    @Column(name = "ar_confirm_date")
    public Timestamp getArConfirmDate() {
        return arConfirmDate;
    }

    public void setArConfirmDate(Timestamp arConfirmDate) {
        this.arConfirmDate = arConfirmDate;
    }

    @Basic
    @Column(name = "ar_appropriation_date")
    public Timestamp getArAppropriationDate() {
        return arAppropriationDate;
    }

    public void setArAppropriationDate(Timestamp arAppropriationDate) {
        this.arAppropriationDate = arAppropriationDate;
    }

    @Basic
    @Column(name = "ap_period_number")
    public Integer getApPeriodNumber() {
        return apPeriodNumber;
    }

    public void setApPeriodNumber(Integer apPeriodNumber) {
        this.apPeriodNumber = apPeriodNumber;
    }

    @Basic
    @Column(name = "ap_rate_type_used")
    public String getApRateTypeUsed() {
        return apRateTypeUsed;
    }

    public void setApRateTypeUsed(String apRateTypeUsed) {
        this.apRateTypeUsed = apRateTypeUsed;
    }

    @Basic
    @Column(name = "ap_interest_rate")
    public BigDecimal getApInterestRate() {
        return apInterestRate;
    }

    public void setApInterestRate(BigDecimal apInterestRate) {
        this.apInterestRate = apInterestRate;
    }

    @Basic
    @Column(name = "ap_rate_type")
    public Byte getApRateType() {
        return apRateType;
    }

    public void setApRateType(Byte apRateType) {
        this.apRateType = apRateType;
    }

    @Basic
    @Column(name = "ap_first_pay_date")
    public Timestamp getApFirstPayDate() {
        return apFirstPayDate;
    }

    public void setApFirstPayDate(Timestamp apFirstPayDate) {
        this.apFirstPayDate = apFirstPayDate;
    }

    @Basic
    @Column(name = "ap_confirm_status")
    public Byte getApConfirmStatus() {
        return apConfirmStatus;
    }

    public void setApConfirmStatus(Byte apConfirmStatus) {
        this.apConfirmStatus = apConfirmStatus;
    }

    @Basic
    @Column(name = "ap_confirmer_id")
    public String getApConfirmerId() {
        return apConfirmerId;
    }

    public void setApConfirmerId(String apConfirmerId) {
        this.apConfirmerId = apConfirmerId;
    }

    @Basic
    @Column(name = "ap_confirmer_department_id")
    public String getApConfirmerDepartmentId() {
        return apConfirmerDepartmentId;
    }

    public void setApConfirmerDepartmentId(String apConfirmerDepartmentId) {
        this.apConfirmerDepartmentId = apConfirmerDepartmentId;
    }

    @Basic
    @Column(name = "ap_confirm_date")
    public Timestamp getApConfirmDate() {
        return apConfirmDate;
    }

    public void setApConfirmDate(Timestamp apConfirmDate) {
        this.apConfirmDate = apConfirmDate;
    }

    @Basic
    @Column(name = "ap_appropriation_date")
    public Timestamp getApAppropriationDate() {
        return apAppropriationDate;
    }

    public void setApAppropriationDate(Timestamp apAppropriationDate) {
        this.apAppropriationDate = apAppropriationDate;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "relation_no")
    public String getRelationNo() {
        return relationNo;
    }

    public void setRelationNo(String relationNo) {
        this.relationNo = relationNo;
    }

    @Basic
    @Column(name = "creator_id")
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modifier_id")
    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    @Basic
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "approver_id")
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_department_id")
    public String getApproverDepartmentId() {
        return approverDepartmentId;
    }

    public void setApproverDepartmentId(String approverDepartmentId) {
        this.approverDepartmentId = approverDepartmentId;
    }

    @Basic
    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Basic
    @Column(name = "abort_time")
    public Timestamp getAbortTime() {
        return abortTime;
    }

    public void setAbortTime(Timestamp abortTime) {
        this.abortTime = abortTime;
    }

    @Basic
    @Column(name = "abort_person_id")
    public String getAbortPersonId() {
        return abortPersonId;
    }

    public void setAbortPersonId(String abortPersonId) {
        this.abortPersonId = abortPersonId;
    }

    @Basic
    @Column(name = "error_code")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_msg")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Basic
    @Column(name = "affiliated_company_id")
    public String getAffiliatedCompanyId() {
        return affiliatedCompanyId;
    }

    public void setAffiliatedCompanyId(String affiliatedCompanyId) {
        this.affiliatedCompanyId = affiliatedCompanyId;
    }

    @Basic
    @Column(name = "sale_contract_no")
    public String getSaleContractNo() {
        return saleContractNo;
    }

    public void setSaleContractNo(String saleContractNo) {
        this.saleContractNo = saleContractNo;
    }

    @Basic
    @Column(name = "credit_no")
    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    @Basic
    @Column(name = "budget_no")
    public String getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    @Basic
    @Column(name = "vehicle_vin")
    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    @Basic
    @Column(name = "ap_loan_amount")
    public BigDecimal getApLoanAmount() {
        return apLoanAmount;
    }

    public void setApLoanAmount(BigDecimal apLoanAmount) {
        this.apLoanAmount = apLoanAmount;
    }

    @Basic
    @Column(name = "loan_amount_4s")
    public BigDecimal getLoanAmount4s() {
        return loanAmount4s;
    }

    public void setLoanAmount4s(BigDecimal loanAmount4s) {
        this.loanAmount4s = loanAmount4s;
    }
}
