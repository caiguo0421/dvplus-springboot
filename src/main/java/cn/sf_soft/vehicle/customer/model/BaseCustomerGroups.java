package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 客户群-主表
 *
 * @author caigx
 */
@Entity
@Table(name = "base_customer_groups", schema = "dbo")
public class BaseCustomerGroups implements java.io.Serializable {

    private String customerGroupId;
    private String customerGroupNo;
    private String customerGroupName;
    private String stationId;
    private String province;
    private String city;
    private String area;
    private String address;
    private String subjectMatter;
    private String profitModel;
    private String strain;
    private String marketSegment;
    private String customerRelations;
    private String leaderInfluence;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;

    // Constructors
    public BaseCustomerGroups() {
    }


    @Id
    @Column(name = "customer_group_id", unique = true, nullable = false, length = 40)
    public String getCustomerGroupId() {
        return this.customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    @Column(name = "customer_group_no", length = 100)
    public String getCustomerGroupNo() {
        return this.customerGroupNo;
    }

    public void setCustomerGroupNo(String customerGroupNo) {
        this.customerGroupNo = customerGroupNo;
    }

    @Column(name = "customer_group_name", nullable = false, length = 200)
    public String getCustomerGroupName() {
        return this.customerGroupName;
    }

    public void setCustomerGroupName(String customerGroupName) {
        this.customerGroupName = customerGroupName;
    }

    @Column(name = "station_id", length = 40)
    public String getStationId() {
        return this.stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "province", length = 20)
    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city", length = 20)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "area", length = 20)
    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "address", length = 100)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "subject_matter", length = 100)
    public String getSubjectMatter() {
        return this.subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    @Column(name = "profit_model", length = 100)
    public String getProfitModel() {
        return this.profitModel;
    }

    public void setProfitModel(String profitModel) {
        this.profitModel = profitModel;
    }

    @Column(name = "strain", length = 100)
    public String getStrain() {
        return this.strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    @Column(name = "market_segment", length = 100)
    public String getMarketSegment() {
        return this.marketSegment;
    }

    public void setMarketSegment(String marketSegment) {
        this.marketSegment = marketSegment;
    }

    @Column(name = "customer_relations", length = 40)
    public String getCustomerRelations() {
        return this.customerRelations;
    }

    public void setCustomerRelations(String customerRelations) {
        this.customerRelations = customerRelations;
    }

    @Column(name = "leader_influence", length = 40)
    public String getLeaderInfluence() {
        return this.leaderInfluence;
    }

    public void setLeaderInfluence(String leaderInfluence) {
        this.leaderInfluence = leaderInfluence;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "creator", length = 50)
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "create_time", length = 23)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "modifier", length = 50)
    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Column(name = "modify_time", length = 23)
    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
}