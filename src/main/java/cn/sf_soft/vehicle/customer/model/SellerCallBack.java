package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 17-9-10.
 */
@Entity
@Table(name = "seller_call_back", schema = "dbo")
public class SellerCallBack {
    private String backId;
    private String customerId;
    private String backPurpose;
    private String backWay;
    private String backer;
    private Timestamp planBackTime;
    private Timestamp realBackTime;
    private String backContent;
    private String checker;
    private Timestamp checkTime;
    private String checkContent;
    private String outStockNo;
    private Boolean backFlag;

    @Id
    @Column(name = "back_id")
    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
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
    @Column(name = "back_purpose")
    public String getBackPurpose() {
        return backPurpose;
    }

    public void setBackPurpose(String backPurpose) {
        this.backPurpose = backPurpose;
    }

    @Basic
    @Column(name = "back_way")
    public String getBackWay() {
        return backWay;
    }

    public void setBackWay(String backWay) {
        this.backWay = backWay;
    }

    @Basic
    @Column(name = "backer")
    public String getBacker() {
        return backer;
    }

    public void setBacker(String backer) {
        this.backer = backer;
    }

    @Basic
    @Column(name = "plan_back_time")
    public Timestamp getPlanBackTime() {
        return planBackTime;
    }

    public void setPlanBackTime(Timestamp planBackTime) {
        this.planBackTime = planBackTime;
    }

    @Basic
    @Column(name = "real_back_time")
    public Timestamp getRealBackTime() {
        return realBackTime;
    }

    public void setRealBackTime(Timestamp realBackTime) {
        this.realBackTime = realBackTime;
    }

    @Basic
    @Column(name = "back_content")
    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    @Basic
    @Column(name = "checker")
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Basic
    @Column(name = "check_time")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "check_content")
    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    @Basic
    @Column(name = "out_stock_no")
    public String getOutStockNo() {
        return outStockNo;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    @Basic
    @Column(name = "back_flag")
    public Boolean getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(Boolean backFlag) {
        this.backFlag = backFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerCallBack that = (SellerCallBack) o;

        if (backId != null ? !backId.equals(that.backId) : that.backId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (backPurpose != null ? !backPurpose.equals(that.backPurpose) : that.backPurpose != null) return false;
        if (backWay != null ? !backWay.equals(that.backWay) : that.backWay != null) return false;
        if (backer != null ? !backer.equals(that.backer) : that.backer != null) return false;
        if (planBackTime != null ? !planBackTime.equals(that.planBackTime) : that.planBackTime != null) return false;
        if (realBackTime != null ? !realBackTime.equals(that.realBackTime) : that.realBackTime != null) return false;
        if (backContent != null ? !backContent.equals(that.backContent) : that.backContent != null) return false;
        if (checker != null ? !checker.equals(that.checker) : that.checker != null) return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (checkContent != null ? !checkContent.equals(that.checkContent) : that.checkContent != null) return false;
        if (outStockNo != null ? !outStockNo.equals(that.outStockNo) : that.outStockNo != null) return false;
        if (backFlag != null ? !backFlag.equals(that.backFlag) : that.backFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = backId != null ? backId.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (backPurpose != null ? backPurpose.hashCode() : 0);
        result = 31 * result + (backWay != null ? backWay.hashCode() : 0);
        result = 31 * result + (backer != null ? backer.hashCode() : 0);
        result = 31 * result + (planBackTime != null ? planBackTime.hashCode() : 0);
        result = 31 * result + (realBackTime != null ? realBackTime.hashCode() : 0);
        result = 31 * result + (backContent != null ? backContent.hashCode() : 0);
        result = 31 * result + (checker != null ? checker.hashCode() : 0);
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (checkContent != null ? checkContent.hashCode() : 0);
        result = 31 * result + (outStockNo != null ? outStockNo.hashCode() : 0);
        result = 31 * result + (backFlag != null ? backFlag.hashCode() : 0);
        return result;
    }
}
