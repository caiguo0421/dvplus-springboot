package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/9.
 */
@Entity
@Table(name = "charge_catalog", schema = "dbo")
public class ChargeCatalog {
    private String chargeId;
    private String chargeName;
    private String accountNo;
    private Boolean forbidFlag;
    private String remark;
    private Byte chargeType;
    private Byte defaultFlag;
    private String objectId;
    private String objectNo;
    private String objectName;
    private BigDecimal defaultMoney;
    private Byte moneyType;
    private Byte direction;
    private Byte accruedType;
    private BigDecimal accruedValue;
    private BigDecimal defaultCost;

    @Id
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
    @Column(name = "account_no")
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Basic
    @Column(name = "forbid_flag")
    public Boolean getForbidFlag() {
        return forbidFlag;
    }

    public void setForbidFlag(Boolean forbidFlag) {
        this.forbidFlag = forbidFlag;
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
    @Column(name = "charge_type")
    public Byte getChargeType() {
        return chargeType;
    }

    public void setChargeType(Byte chargeType) {
        this.chargeType = chargeType;
    }

    @Basic
    @Column(name = "default_flag")
    public Byte getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Byte defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    @Basic
    @Column(name = "object_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "object_no")
    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Basic
    @Column(name = "object_name")
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Basic
    @Column(name = "default_money")
    public BigDecimal getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(BigDecimal defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    @Basic
    @Column(name = "money_type")
    public Byte getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Byte moneyType) {
        this.moneyType = moneyType;
    }

    @Basic
    @Column(name = "direction")
    public Byte getDirection() {
        return direction;
    }

    public void setDirection(Byte direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "accrued_type")
    public Byte getAccruedType() {
        return accruedType;
    }

    public void setAccruedType(Byte accruedType) {
        this.accruedType = accruedType;
    }

    @Basic
    @Column(name = "accrued_value")
    public BigDecimal getAccruedValue() {
        return accruedValue;
    }

    public void setAccruedValue(BigDecimal accruedValue) {
        this.accruedValue = accruedValue;
    }

    @Basic
    @Column(name = "default_cost")
    public BigDecimal getDefaultCost() {
        return defaultCost;
    }

    public void setDefaultCost(BigDecimal defaultCost) {
        this.defaultCost = defaultCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChargeCatalog that = (ChargeCatalog) o;

        if (chargeId != null ? !chargeId.equals(that.chargeId) : that.chargeId != null) return false;
        if (chargeName != null ? !chargeName.equals(that.chargeName) : that.chargeName != null) return false;
        if (accountNo != null ? !accountNo.equals(that.accountNo) : that.accountNo != null) return false;
        if (forbidFlag != null ? !forbidFlag.equals(that.forbidFlag) : that.forbidFlag != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (chargeType != null ? !chargeType.equals(that.chargeType) : that.chargeType != null) return false;
        if (defaultFlag != null ? !defaultFlag.equals(that.defaultFlag) : that.defaultFlag != null) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (objectNo != null ? !objectNo.equals(that.objectNo) : that.objectNo != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (defaultMoney != null ? !defaultMoney.equals(that.defaultMoney) : that.defaultMoney != null) return false;
        if (moneyType != null ? !moneyType.equals(that.moneyType) : that.moneyType != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (accruedType != null ? !accruedType.equals(that.accruedType) : that.accruedType != null) return false;
        if (accruedValue != null ? !accruedValue.equals(that.accruedValue) : that.accruedValue != null) return false;
        if (defaultCost != null ? !defaultCost.equals(that.defaultCost) : that.defaultCost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chargeId != null ? chargeId.hashCode() : 0;
        result = 31 * result + (chargeName != null ? chargeName.hashCode() : 0);
        result = 31 * result + (accountNo != null ? accountNo.hashCode() : 0);
        result = 31 * result + (forbidFlag != null ? forbidFlag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (chargeType != null ? chargeType.hashCode() : 0);
        result = 31 * result + (defaultFlag != null ? defaultFlag.hashCode() : 0);
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (objectNo != null ? objectNo.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (defaultMoney != null ? defaultMoney.hashCode() : 0);
        result = 31 * result + (moneyType != null ? moneyType.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (accruedType != null ? accruedType.hashCode() : 0);
        result = 31 * result + (accruedValue != null ? accruedValue.hashCode() : 0);
        result = 31 * result + (defaultCost != null ? defaultCost.hashCode() : 0);
        return result;
    }
}
