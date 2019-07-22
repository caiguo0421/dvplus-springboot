package cn.sf_soft.vehicle.contract.model;

import cn.sf_soft.support.LogProperty;
import cn.sf_soft.support.LogPropertyType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/2.
 */
@Entity
@Table(name = "vehicle_invoices_groups", schema = "dbo")
public class VehicleInvoicesGroups {
    private String invoiceGroupId;
    private String groupId;
    @LogProperty()
    private String invoiceType;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL)
    private BigDecimal invoiceAmount = BigDecimal.ZERO;
    private String objectId;
    private String objectNo;
    @LogProperty(name="invoiceObject")
    private String objectName;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String contractNo;

    @Id
    @Column(name = "invoice_group_id")
    public String getInvoiceGroupId() {
        return invoiceGroupId;
    }

    public void setInvoiceGroupId(String invoiceGroupId) {
        this.invoiceGroupId = invoiceGroupId;
    }

    @Basic
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "invoice_type")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Basic
    @Column(name = "invoice_amount")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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
    @Column(name = "contract_no")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleInvoicesGroups that = (VehicleInvoicesGroups) o;

        if (invoiceGroupId != null ? !invoiceGroupId.equals(that.invoiceGroupId) : that.invoiceGroupId != null)
            return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (invoiceType != null ? !invoiceType.equals(that.invoiceType) : that.invoiceType != null) return false;
        if (invoiceAmount != null ? !invoiceAmount.equals(that.invoiceAmount) : that.invoiceAmount != null)
            return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (objectNo != null ? !objectNo.equals(that.objectNo) : that.objectNo != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (contractNo != null ? !contractNo.equals(that.contractNo) : that.contractNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceGroupId != null ? invoiceGroupId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (invoiceType != null ? invoiceType.hashCode() : 0);
        result = 31 * result + (invoiceAmount != null ? invoiceAmount.hashCode() : 0);
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (objectNo != null ? objectNo.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (contractNo != null ? contractNo.hashCode() : 0);
        return result;
    }
}
