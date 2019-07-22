package cn.sf_soft.vehicle.certificate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 合格证出库明细
 *
 * @author caigu
 */
@Entity
@Table(name = "vehicle_certificate_out_stocks_detail", schema = "dbo")
public class VehicleCertificateOutStocksDetail implements java.io.Serializable {


    // Fields
    private String outStockDetailId;
    private String outStockNo;
    private String certificateId;
    private String remark;


    // Constructors
    public VehicleCertificateOutStocksDetail() {
    }


    @Id
    @Column(name = "out_stock_detail_id", unique = true, nullable = false)

    public String getOutStockDetailId() {
        return this.outStockDetailId;
    }

    public void setOutStockDetailId(String outStockDetailId) {
        this.outStockDetailId = outStockDetailId;
    }

    @Column(name = "out_stock_no")
    public String getOutStockNo() {
        return this.outStockNo;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    @Column(name = "certificate_id")
    public String getCertificateId() {
        return this.certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}