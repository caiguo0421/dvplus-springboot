package cn.sf_soft.vehicle.df.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/2 10:40
 * @Description: 在途费用确认表
 */
@Entity
@Table(name = "vehicle_onway_charge")
public class VehicleOnwayCharge {
    private String selfId;
    private String vocdId;
    private String chargeId;
    private String chargeName;
    private BigDecimal chargePf;
    private BigDecimal chargeCost;
    private Boolean costStatus = false;
    private Byte status = 0;
    private String remark;
    private String inDetailId;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "vocd_id")
    public String getVocdId() {
        return vocdId;
    }

    public void setVocdId(String vocdId) {
        this.vocdId = vocdId;
    }

    @Basic
    @Column(name = "charge_id")
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    @Basic
    @Column(name = "charge_name")
    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    @Basic
    @Column(name = "charge_pf")
    public BigDecimal getChargePf() {
        return chargePf;
    }

    public void setChargePf(BigDecimal chargePf) {
        this.chargePf = chargePf;
    }

    @Basic
    @Column(name = "charge_cost")
    public BigDecimal getChargeCost() {
        return chargeCost;
    }

    public void setChargeCost(BigDecimal chargeCost) {
        this.chargeCost = chargeCost;
    }

    @Basic
    @Column(name = "cost_status")
    public Boolean getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Boolean costStatus) {
        this.costStatus = costStatus;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    @Column(name = "in_detail_id")
    public String getInDetailId() {
        return inDetailId;
    }

    public void setInDetailId(String inDetailId) {
        this.inDetailId = inDetailId;
    }
}
