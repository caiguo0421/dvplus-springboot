package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "vehicle_conversion_master", schema = "dbo")
public class VehicleConversionMaster extends ApproveDocuments<VehicleConversion> {
    private String vnoId;
    private String vehicleVno;
    private String vehicleName;
    private String vehicleStrain;
    private Short vehicleKind;
    private String saleContractNo;
    private Boolean isExists;
    private Timestamp submitDate;
    private Timestamp endDate;
    private String remark;
    private String confirmMan;
    private Timestamp confirmTime;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private String refDocumentNo;

    private Double conversionAmt;
    private Double inConversionAmt;
    private Double inConversionAmtReal;
    private Double outConversionAmt;

    private List<VehicleConversion> conversions;

    private String fileUrls;

    @Basic
    @Column(name = "vno_id")
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Basic
    @Column(name = "vehicle_vno")
    public String getVehicleVno() {
        return vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    @Basic
    @Column(name = "vehicle_name")
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "vehicle_strain")
    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
    }

    @Basic
    @Column(name = "vehicle_kind")
    public Short getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(Short vehicleKind) {
        this.vehicleKind = vehicleKind;
    }


    @Basic
    @Column(name = "sale_contract_no")
    public String getSaleContractNo() {
        return saleContractNo;
    }

    public void setSaleContractNo(String saleContractNo) {
        this.saleContractNo = saleContractNo;
    }

    @Basic
    @Column(name = "is_exists")
    public Boolean getIsExists() {
        return isExists;
    }

    public void setIsExists(Boolean exists) {
        isExists = exists;
    }

    @Basic
    @Column(name = "submit_date")
    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Timestamp submitDate) {
        this.submitDate = submitDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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
    @Column(name = "confirm_man")
    public String getConfirmMan() {
        return confirmMan;
    }

    public void setConfirmMan(String confirmMan) {
        this.confirmMan = confirmMan;
    }

    @Basic
    @Column(name = "confirm_time")
    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
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
    @Column(name = "ref_document_no")
    public String getRefDocumentNo() {
        return refDocumentNo;
    }

    public void setRefDocumentNo(String refDocumentNo) {
        this.refDocumentNo = refDocumentNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleConversionMaster that = (VehicleConversionMaster) o;

        if (documentNo != null ? !documentNo.equals(that.documentNo) : that.documentNo != null) return false;
        if (vnoId != null ? !vnoId.equals(that.vnoId) : that.vnoId != null) return false;
        if (vehicleVno != null ? !vehicleVno.equals(that.vehicleVno) : that.vehicleVno != null) return false;
        if (vehicleName != null ? !vehicleName.equals(that.vehicleName) : that.vehicleName != null) return false;
        if (vehicleStrain != null ? !vehicleStrain.equals(that.vehicleStrain) : that.vehicleStrain != null)
            return false;
        if (vehicleKind != null ? !vehicleKind.equals(that.vehicleKind) : that.vehicleKind != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (saleContractNo != null ? !saleContractNo.equals(that.saleContractNo) : that.saleContractNo != null)
            return false;
        if (isExists != null ? !isExists.equals(that.isExists) : that.isExists != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (submitDate != null ? !submitDate.equals(that.submitDate) : that.submitDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (approverName != null ? !approverName.equals(that.approverName) : that.approverName != null) return false;
        if (approveTime != null ? !approveTime.equals(that.approveTime) : that.approveTime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (confirmMan != null ? !confirmMan.equals(that.confirmMan) : that.confirmMan != null) return false;
        if (confirmTime != null ? !confirmTime.equals(that.confirmTime) : that.confirmTime != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userNo != null ? !userNo.equals(that.userNo) : that.userNo != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (departmentNo != null ? !departmentNo.equals(that.departmentNo) : that.departmentNo != null) return false;
        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        if (submitStationId != null ? !submitStationId.equals(that.submitStationId) : that.submitStationId != null)
            return false;
        if (submitStationName != null ? !submitStationName.equals(that.submitStationName) : that.submitStationName != null)
            return false;
        if (approverId != null ? !approverId.equals(that.approverId) : that.approverId != null) return false;
        if (approverNo != null ? !approverNo.equals(that.approverNo) : that.approverNo != null) return false;
        if (submitTime != null ? !submitTime.equals(that.submitTime) : that.submitTime != null) return false;
        if (refDocumentNo != null ? !refDocumentNo.equals(that.refDocumentNo) : that.refDocumentNo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentNo != null ? documentNo.hashCode() : 0;
        result = 31 * result + (vnoId != null ? vnoId.hashCode() : 0);
        result = 31 * result + (vehicleVno != null ? vehicleVno.hashCode() : 0);
        result = 31 * result + (vehicleName != null ? vehicleName.hashCode() : 0);
        result = 31 * result + (vehicleStrain != null ? vehicleStrain.hashCode() : 0);
        result = 31 * result + (vehicleKind != null ? vehicleKind.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (saleContractNo != null ? saleContractNo.hashCode() : 0);
        result = 31 * result + (isExists != null ? isExists.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (submitDate != null ? submitDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (approverName != null ? approverName.hashCode() : 0);
        result = 31 * result + (approveTime != null ? approveTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (confirmMan != null ? confirmMan.hashCode() : 0);
        result = 31 * result + (confirmTime != null ? confirmTime.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (departmentNo != null ? departmentNo.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (submitStationId != null ? submitStationId.hashCode() : 0);
        result = 31 * result + (submitStationName != null ? submitStationName.hashCode() : 0);
        result = 31 * result + (approverId != null ? approverId.hashCode() : 0);
        result = 31 * result + (approverNo != null ? approverNo.hashCode() : 0);
        result = 31 * result + (submitTime != null ? submitTime.hashCode() : 0);
        result = 31 * result + (refDocumentNo != null ? refDocumentNo.hashCode() : 0);
        return result;
    }

    public Double getConversionAmt() {
        return conversionAmt;
    }

    public Double getInConversionAmt() {
        return inConversionAmt;
    }

    public Double getInConversionAmtReal() {
        return inConversionAmtReal;
    }

    public Double getOutConversionAmt() {
        return outConversionAmt;
    }

    public List<VehicleConversion> getConversions() {
        return conversions;
    }

    public void setConversions(List<VehicleConversion> conversions) {
        this.conversions = conversions;
        if(conversions != null && conversions.size()>0){
            conversionAmt = 0.0;
            inConversionAmt = 0.0;
            inConversionAmtReal = 0.0;
            outConversionAmt = 0.0;
            for(VehicleConversion conversion: conversions){
                if(conversion.getConversionAmt() != null){
                    conversionAmt += conversion.getConversionAmt();
                }
                if(conversion.getInConversionAmt() != null){
                    inConversionAmt += conversion.getInConversionAmt();
                }
                if(conversion.getInConversionAmtReal() != null){
                    inConversionAmtReal += conversion.getInConversionAmtReal();
                }
                if(conversion.getOutConversionAmt() != null){
                    outConversionAmt += conversion.getOutConversionAmt();
                }
            }
        }
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }
}
