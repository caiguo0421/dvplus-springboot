package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/9.
 */
@Entity
@Table(name = "base_service_consigns", schema = "dbo")
public class BaseServiceConsigns {
    private String consignId;
    private String consignName;
    private String consignPinyin;
    private String consignNo;
    private Byte consignCategory;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private String consignType;
    private String errorCode;
    private String errorMsg;

    @Id
    @Column(name = "consign_id")
    public String getConsignId() {
        return consignId;
    }

    public void setConsignId(String consignId) {
        this.consignId = consignId;
    }

    @Basic
    @Column(name = "consign_name")
    public String getConsignName() {
        return consignName;
    }

    public void setConsignName(String consignName) {
        this.consignName = consignName;
    }

    @Basic
    @Column(name = "consign_pinyin")
    public String getConsignPinyin() {
        return consignPinyin;
    }

    public void setConsignPinyin(String consignPinyin) {
        this.consignPinyin = consignPinyin;
    }

    @Basic
    @Column(name = "consign_no")
    public String getConsignNo() {
        return consignNo;
    }

    public void setConsignNo(String consignNo) {
        this.consignNo = consignNo;
    }

    @Basic
    @Column(name = "consign_category")
    public Byte getConsignCategory() {
        return consignCategory;
    }

    public void setConsignCategory(Byte consignCategory) {
        this.consignCategory = consignCategory;
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
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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
    @Column(name = "consign_type")
    public String getConsignType() {
        return consignType;
    }

    public void setConsignType(String consignType) {
        this.consignType = consignType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseServiceConsigns that = (BaseServiceConsigns) o;

        if (consignId != null ? !consignId.equals(that.consignId) : that.consignId != null) return false;
        if (consignName != null ? !consignName.equals(that.consignName) : that.consignName != null) return false;
        if (consignPinyin != null ? !consignPinyin.equals(that.consignPinyin) : that.consignPinyin != null)
            return false;
        if (consignNo != null ? !consignNo.equals(that.consignNo) : that.consignNo != null) return false;
        if (consignCategory != null ? !consignCategory.equals(that.consignCategory) : that.consignCategory != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (consignType != null ? !consignType.equals(that.consignType) : that.consignType != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = consignId != null ? consignId.hashCode() : 0;
        result = 31 * result + (consignName != null ? consignName.hashCode() : 0);
        result = 31 * result + (consignPinyin != null ? consignPinyin.hashCode() : 0);
        result = 31 * result + (consignNo != null ? consignNo.hashCode() : 0);
        result = 31 * result + (consignCategory != null ? consignCategory.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (consignType != null ? consignType.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        return result;
    }
}
