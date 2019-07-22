package cn.sf_soft.vehicle.purchase.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/9/25 16:30
 * @Description:
 */
@Entity
@Table(name = "vehicle_purchase_contract_detail")
public class VehiclePurchaseContractDetail {
    private String selfId;
    private String contractId;
    private String contractNo;
    private String orderId;
    private Integer declareQuantity;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "contract_id")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "contract_no")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Basic
    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "declare_quantity")
    public Integer getDeclareQuantity() {
        return declareQuantity;
    }

    public void setDeclareQuantity(Integer declareQuantity) {
        this.declareQuantity = declareQuantity;
    }

}
