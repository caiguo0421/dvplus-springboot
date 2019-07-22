package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/1/1.
 */
@Entity
@Table(name = "presell_visitors_repeat_check", schema = "dbo")
public class PresellVisitorsRepeatCheck {
    private String selfId;
    private String customerName;
    private String customerMobile;
    private String repeatCustomerName;
    private String repeatCustomerMobile;
    private String seller;
    private String creator;
    private Timestamp createTime;
    private String customerId;

    @Id
    @Column(name = "self_id", nullable = false, length = 40)
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "customer_name", nullable = true, length = 40)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    @Column(name = "customer_mobile", nullable = true, length = 40)
    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    @Basic
    @Column(name = "repeat_customer_name", nullable = true, length = 40)
    public String getRepeatCustomerName() {
        return repeatCustomerName;
    }

    public void setRepeatCustomerName(String repeatCustomerName) {
        this.repeatCustomerName = repeatCustomerName;
    }

    @Basic
    @Column(name = "repeat_customer_mobile", nullable = true, length = 40)
    public String getRepeatCustomerMobile() {
        return repeatCustomerMobile;
    }

    public void setRepeatCustomerMobile(String repeatCustomerMobile) {
        this.repeatCustomerMobile = repeatCustomerMobile;
    }

    @Basic
    @Column(name = "seller", nullable = true, length = 40)
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Basic
    @Column(name = "creator", nullable = true, length = 40)
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
    @Column(name = "customer_id", nullable = true, length = 40)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PresellVisitorsRepeatCheck that = (PresellVisitorsRepeatCheck) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (customerMobile != null ? !customerMobile.equals(that.customerMobile) : that.customerMobile != null)
            return false;
        if (repeatCustomerName != null ? !repeatCustomerName.equals(that.repeatCustomerName) : that.repeatCustomerName != null)
            return false;
        if (repeatCustomerMobile != null ? !repeatCustomerMobile.equals(that.repeatCustomerMobile) : that.repeatCustomerMobile != null)
            return false;
        if (seller != null ? !seller.equals(that.seller) : that.seller != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (customerMobile != null ? customerMobile.hashCode() : 0);
        result = 31 * result + (repeatCustomerName != null ? repeatCustomerName.hashCode() : 0);
        result = 31 * result + (repeatCustomerMobile != null ? repeatCustomerMobile.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
    }
}
