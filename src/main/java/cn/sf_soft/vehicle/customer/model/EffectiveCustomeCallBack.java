package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2017/9/7.
 */
public class EffectiveCustomeCallBack {
    private String backId;
    private String customerId;
    private String backPurpose;
    private String backWay;
    private String backerId;
    private String backer;
    private Timestamp planBackTime;
    private Timestamp realBackTime;
    private String backContent;
    private String checkerId;
    private String checker;
    private Timestamp checkTime;
    private String checkContent;
    private String pics;
    private String address;
    private Float longitude;
    private Float latitude;

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBackPurpose() {
        return backPurpose;
    }

    public void setBackPurpose(String backPurpose) {
        this.backPurpose = backPurpose;
    }

    public String getBackWay() {
        return backWay;
    }

    public void setBackWay(String backWay) {
        this.backWay = backWay;
    }

    public String getBackerId() {
        return backerId;
    }

    public void setBackerId(String backerId) {
        this.backerId = backerId;
    }

    public String getBacker() {
        return backer;
    }

    public void setBacker(String backer) {
        this.backer = backer;
    }

    public Timestamp getPlanBackTime() {
        return planBackTime;
    }

    public void setPlanBackTime(Timestamp planBackTime) {
        this.planBackTime = planBackTime;
    }

    public Timestamp getRealBackTime() {
        return realBackTime;
    }

    public void setRealBackTime(Timestamp realBackTime) {
        this.realBackTime = realBackTime;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EffectiveCustomeCallBack that = (EffectiveCustomeCallBack) o;

        if (backId != null ? !backId.equals(that.backId) : that.backId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (backPurpose != null ? !backPurpose.equals(that.backPurpose) : that.backPurpose != null) return false;
        if (backWay != null ? !backWay.equals(that.backWay) : that.backWay != null) return false;
        if (backerId != null ? !backerId.equals(that.backerId) : that.backerId != null) return false;
        if (backer != null ? !backer.equals(that.backer) : that.backer != null) return false;
        if (planBackTime != null ? !planBackTime.equals(that.planBackTime) : that.planBackTime != null) return false;
        if (realBackTime != null ? !realBackTime.equals(that.realBackTime) : that.realBackTime != null) return false;
        if (backContent != null ? !backContent.equals(that.backContent) : that.backContent != null) return false;
        if (checkerId != null ? !checkerId.equals(that.checkerId) : that.checkerId != null) return false;
        if (checker != null ? !checker.equals(that.checker) : that.checker != null) return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (checkContent != null ? !checkContent.equals(that.checkContent) : that.checkContent != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = backId != null ? backId.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (backPurpose != null ? backPurpose.hashCode() : 0);
        result = 31 * result + (backWay != null ? backWay.hashCode() : 0);
        result = 31 * result + (backerId != null ? backerId.hashCode() : 0);
        result = 31 * result + (backer != null ? backer.hashCode() : 0);
        result = 31 * result + (planBackTime != null ? planBackTime.hashCode() : 0);
        result = 31 * result + (realBackTime != null ? realBackTime.hashCode() : 0);
        result = 31 * result + (backContent != null ? backContent.hashCode() : 0);
        result = 31 * result + (checkerId != null ? checkerId.hashCode() : 0);
        result = 31 * result + (checker != null ? checker.hashCode() : 0);
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (checkContent != null ? checkContent.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        return result;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
