package cn.sf_soft.vehicle.loan.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "vw_vehicle_loan_budget_details_group")
@org.hibernate.annotations.Entity(mutable = false)
public class VwVehicleLoanBudgetDetailsGroup implements java.io.Serializable {

    private String groupId;
    private String documentNo;
    private Byte status;
    private String statusMeaning;
    private String vnoId;
    private String vehicleSalesCode;
    private String vehicleName;
    private String vehicleStrain;
    private String vehicleBrand;
    private BigDecimal vehiclePriceTotal;
    private BigDecimal chargeAmount;
    private BigDecimal firstPayTot;
    private BigDecimal loanAmountTot;
    private BigDecimal loanAmountAll;
    private Integer periodNumber;
    private BigDecimal interestRate;
    private Byte rateType;
    private String rateTypeMeaning;
    private BigDecimal monthPay;
    private BigDecimal interest;
    private BigDecimal vehicleFirstPay;
    private BigDecimal chargeFirstPay;
    private BigDecimal loanAmount;
    private BigDecimal chargeLoanAmount;
    private BigDecimal expenditureTot;
    private BigDecimal agentAmount;
    private String remark;
    private String loanObjectId;
    private String loanObjectNo;
    private String loanObjectName;
    private Byte loanType;
    private String loanTypeMeaning;
    private String approverId;
    private String approverNo;
    private String approver;
    private Timestamp approveTime;
    private Integer vehicleQuantity;

    //挂靠公司
    private String affiliatedCompanyId;

    @Id
    @Column(name = "group_id", nullable = false, length = 40)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "document_no", nullable = false, length = 40)
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status_meaning", nullable = true, length = 10)
    public String getStatusMeaning() {
        return statusMeaning;
    }

    public void setStatusMeaning(String statusMeaning) {
        this.statusMeaning = statusMeaning;
    }


    @Basic
    @Column(name = "vno_id", nullable = false, length = 40)
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Basic
    @Column(name = "vehicle_sales_code", nullable = true, length = 100)
    public String getVehicleSalesCode() {
        return vehicleSalesCode;
    }

    public void setVehicleSalesCode(String vehicleSalesCode) {
        this.vehicleSalesCode = vehicleSalesCode;
    }

    @Basic
    @Column(name = "vehicle_name", nullable = true, length = 30)
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "vehicle_strain", nullable = true, length = 30)
    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
    }

    @Basic
    @Column(name = "vehicle_brand", nullable = true, length = 30)
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Basic
    @Column(name = "vehicle_price_total", nullable = false)
    public BigDecimal getVehiclePriceTotal() {
        return vehiclePriceTotal;
    }

    public void setVehiclePriceTotal(BigDecimal vehiclePriceTotal) {
        this.vehiclePriceTotal = vehiclePriceTotal;
    }

    @Basic
    @Column(name = "charge_amount", nullable = true)
    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Basic
    @Column(name = "first_pay_tot", nullable = true)
    public BigDecimal getFirstPayTot() {
        return firstPayTot;
    }

    public void setFirstPayTot(BigDecimal firstPayTot) {
        this.firstPayTot = firstPayTot;
    }

    @Basic
    @Column(name = "loan_amount_tot", nullable = true)
    public BigDecimal getLoanAmountTot() {
        return loanAmountTot;
    }

    public void setLoanAmountTot(BigDecimal loanAmountTot) {
        this.loanAmountTot = loanAmountTot;
    }

    @Basic
    @Column(name = "loan_amount_all", nullable = true, precision = 4)
    public BigDecimal getLoanAmountAll() {
        return loanAmountAll;
    }

    public void setLoanAmountAll(BigDecimal loanAmountAll) {
        this.loanAmountAll = loanAmountAll;
    }

    @Basic
    @Column(name = "period_number", nullable = true)
    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    @Basic
    @Column(name = "interest_rate", nullable = true, precision = 6)
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "rate_type", nullable = true)
    public Byte getRateType() {
        return rateType;
    }

    public void setRateType(Byte rateType) {
        this.rateType = rateType;
    }

    @Basic
    @Column(name = "rate_type_meaning", nullable = true, length = 10)
    public String getRateTypeMeaning() {
        return rateTypeMeaning;
    }

    public void setRateTypeMeaning(String rateTypeMeaning) {
        this.rateTypeMeaning = rateTypeMeaning;
    }

    @Basic
    @Column(name = "month_pay", nullable = true)
    public BigDecimal getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
    }

    @Basic
    @Column(name = "interest", nullable = true, precision = 2)
    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    @Basic
    @Column(name = "vehicle_first_pay", nullable = true)
    public BigDecimal getVehicleFirstPay() {
        return vehicleFirstPay;
    }

    public void setVehicleFirstPay(BigDecimal vehicleFirstPay) {
        this.vehicleFirstPay = vehicleFirstPay;
    }

    @Basic
    @Column(name = "charge_first_pay", nullable = true)
    public BigDecimal getChargeFirstPay() {
        return chargeFirstPay;
    }

    public void setChargeFirstPay(BigDecimal chargeFirstPay) {
        this.chargeFirstPay = chargeFirstPay;
    }

    @Basic
    @Column(name = "loan_amount", nullable = false)
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Basic
    @Column(name = "charge_loan_amount", nullable = true)
    public BigDecimal getChargeLoanAmount() {
        return chargeLoanAmount;
    }

    public void setChargeLoanAmount(BigDecimal chargeLoanAmount) {
        this.chargeLoanAmount = chargeLoanAmount;
    }

    @Basic
    @Column(name = "expenditure_tot", nullable = true)
    public BigDecimal getExpenditureTot() {
        return expenditureTot;
    }

    public void setExpenditureTot(BigDecimal expenditureTot) {
        this.expenditureTot = expenditureTot;
    }

    @Basic
    @Column(name = "agent_amount", nullable = true, precision = 2)
    public BigDecimal getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(BigDecimal agentAmount) {
        this.agentAmount = agentAmount;
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
    @Column(name = "loan_object_id", nullable = true, length = 40)
    public String getLoanObjectId() {
        return loanObjectId;
    }

    public void setLoanObjectId(String loanObjectId) {
        this.loanObjectId = loanObjectId;
    }

    @Basic
    @Column(name = "loan_object_no", nullable = true, length = 40)
    public String getLoanObjectNo() {
        return loanObjectNo;
    }

    public void setLoanObjectNo(String loanObjectNo) {
        this.loanObjectNo = loanObjectNo;
    }

    @Basic
    @Column(name = "loan_object_name", nullable = true, length = 40)
    public String getLoanObjectName() {
        return loanObjectName;
    }

    public void setLoanObjectName(String loanObjectName) {
        this.loanObjectName = loanObjectName;
    }

    @Basic
    @Column(name = "loan_type", nullable = true)
    public Byte getLoanType() {
        return loanType;
    }

    public void setLoanType(Byte loanType) {
        this.loanType = loanType;
    }

    @Basic
    @Column(name = "loan_type_meaning", nullable = true, length = 10)
    public String getLoanTypeMeaning() {
        return loanTypeMeaning;
    }

    public void setLoanTypeMeaning(String loanTypeMeaning) {
        this.loanTypeMeaning = loanTypeMeaning;
    }

    @Basic
    @Column(name = "approver_id", nullable = true, length = 40)
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no", nullable = true, length = 10)
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver", nullable = true, length = 22)
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
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
    @Column(name = "vehicle_quantity", nullable = true)
    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }


    @Column(name = "affiliated_company_id")
    public String getAffiliatedCompanyId() {
        return this.affiliatedCompanyId;
    }

    public void setAffiliatedCompanyId(String affiliatedCompanyId) {
        this.affiliatedCompanyId = affiliatedCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VwVehicleLoanBudgetDetailsGroup that = (VwVehicleLoanBudgetDetailsGroup) o;
        return status == that.status && Objects.equals(documentNo, that.documentNo) && Objects.equals(statusMeaning, that.statusMeaning) && Objects.equals(groupId, that.groupId) && Objects.equals(vnoId, that.vnoId) && Objects.equals(vehicleSalesCode, that.vehicleSalesCode) && Objects.equals(vehicleName, that.vehicleName) && Objects.equals(vehicleStrain, that.vehicleStrain) && Objects.equals(vehicleBrand, that.vehicleBrand) && Objects.equals(vehiclePriceTotal, that.vehiclePriceTotal) && Objects.equals(chargeAmount, that.chargeAmount) && Objects.equals(firstPayTot, that.firstPayTot) && Objects.equals(loanAmountTot, that.loanAmountTot) && Objects.equals(loanAmountAll, that.loanAmountAll) && Objects.equals(periodNumber, that.periodNumber) && Objects.equals(interestRate, that.interestRate) && Objects.equals(rateType, that.rateType) && Objects.equals(rateTypeMeaning, that.rateTypeMeaning) && Objects.equals(monthPay, that.monthPay) && Objects.equals(interest, that.interest) && Objects.equals(vehicleFirstPay, that.vehicleFirstPay) && Objects.equals(chargeFirstPay, that.chargeFirstPay) && Objects.equals(loanAmount, that.loanAmount) && Objects.equals(chargeLoanAmount, that.chargeLoanAmount) && Objects.equals(expenditureTot, that.expenditureTot) && Objects.equals(agentAmount, that.agentAmount) && Objects.equals(remark, that.remark) && Objects.equals(loanObjectId, that.loanObjectId) && Objects.equals(loanObjectNo, that.loanObjectNo) && Objects.equals(loanObjectName, that.loanObjectName) && Objects.equals(loanType, that.loanType) && Objects.equals(loanTypeMeaning, that.loanTypeMeaning) && Objects.equals(approverId, that.approverId) && Objects.equals(approverNo, that.approverNo) && Objects.equals(approver, that.approver) && Objects.equals(approveTime, that.approveTime) && Objects.equals(vehicleQuantity, that.vehicleQuantity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(documentNo, status, statusMeaning, groupId, vnoId, vehicleSalesCode, vehicleName, vehicleStrain, vehicleBrand, vehiclePriceTotal, chargeAmount, firstPayTot, loanAmountTot, loanAmountAll, periodNumber, interestRate, rateType, rateTypeMeaning, monthPay, interest, vehicleFirstPay, chargeFirstPay, loanAmount, chargeLoanAmount, expenditureTot, agentAmount, remark, loanObjectId, loanObjectNo, loanObjectName, loanType, loanTypeMeaning, approverId, approverNo, approver, approveTime, vehicleQuantity);
    }
}
