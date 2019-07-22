package cn.sf_soft.office.approval.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Auther: caigx
 * @Date: 2018/11/21 12:31
 * @Description:营销活动报销-明细
 */
@Entity
@Table(name = "office_sale_marketing_reimbursements_details", schema = "dbo")
public class OfficeSaleMarketingReimbursementsDetails {
    private String osmrdId;
    private String documentNo;
    private String vsmNo;
    private String vsmcId;
    private String chargeName;
    private BigDecimal chargeAmount;
    private BigDecimal estimateMoney;
    private String differenceReason;
    //活动名称（计算字段）
    private String marketingActivityName;

    @Id
    @Column(name = "osmrd_id")
    public String getOsmrdId() {
        return osmrdId;
    }

    public void setOsmrdId(String osmrdId) {
        this.osmrdId = osmrdId;
    }

    @Basic
    @Column(name = "document_no")
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "vsm_no")
    public String getVsmNo() {
        return vsmNo;
    }

    public void setVsmNo(String vsmNo) {
        this.vsmNo = vsmNo;
    }

    @Basic
    @Column(name = "vsmc_id")
    public String getVsmcId() {
        return vsmcId;
    }

    public void setVsmcId(String vsmcId) {
        this.vsmcId = vsmcId;
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
    @Column(name = "charge_amount")
    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Basic
    @Column(name = "estimate_money")
    public BigDecimal getEstimateMoney() {
        return estimateMoney;
    }

    public void setEstimateMoney(BigDecimal estimateMoney) {
        this.estimateMoney = estimateMoney;
    }

    @Basic
    @Column(name = "difference_reason")
    public String getDifferenceReason() {
        return differenceReason;
    }

    public void setDifferenceReason(String differenceReason) {
        this.differenceReason = differenceReason;
    }

    @Formula("(select l.marketing_activity_name from vehicle_sale_marketing_activity l where l.document_no = vsm_no)")
    public String getMarketingActivityName() {
        return marketingActivityName;
    }

    public void setMarketingActivityName(String marketingActivityName) {
        this.marketingActivityName = marketingActivityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeSaleMarketingReimbursementsDetails that = (OfficeSaleMarketingReimbursementsDetails) o;
        return Objects.equals(osmrdId, that.osmrdId) &&
                Objects.equals(documentNo, that.documentNo) &&
                Objects.equals(vsmNo, that.vsmNo) &&
                Objects.equals(vsmcId, that.vsmcId) &&
                Objects.equals(chargeName, that.chargeName) &&
                Objects.equals(chargeAmount, that.chargeAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(osmrdId, documentNo, vsmNo, vsmcId, chargeName, chargeAmount);
    }
}
