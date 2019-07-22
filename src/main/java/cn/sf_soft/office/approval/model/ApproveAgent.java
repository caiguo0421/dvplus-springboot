package cn.sf_soft.office.approval.model;
// default package

import java.sql.Timestamp;


/**
 * ApproveAgent entity. @author MyEclipse Persistence Tools
 */

public class ApproveAgent  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -4811060228478356140L;
	// Fields    

     private String agentId;
     private String stationId;
     private Short status;
     private String originApprover;
     private String agent;
     private String moduleId;
     private String moduleName;
     private Timestamp beginTime;
     private Timestamp endTime;
     private String remark;
     private String creator;
     private Timestamp createTime;
     private String modifier;
     private Timestamp modifyTime;
     private String confirmor;
     private Timestamp confirmTime;


    // Constructors

    /** default constructor */
    public ApproveAgent() {
    }

	/** minimal constructor */
    public ApproveAgent(String agentId, String stationId, Short status, String originApprover, String agent, String moduleId, String moduleName, Timestamp beginTime, Timestamp endTime) {
        this.agentId = agentId;
        this.stationId = stationId;
        this.status = status;
        this.originApprover = originApprover;
        this.agent = agent;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    
    /** full constructor */
    public ApproveAgent(String agentId, String stationId, Short status, String originApprover, String agent, String moduleId, String moduleName, Timestamp beginTime, Timestamp endTime, String remark, String creator, Timestamp createTime, String modifier, Timestamp modifyTime, String confirmor, Timestamp confirmTime) {
        this.agentId = agentId;
        this.stationId = stationId;
        this.status = status;
        this.originApprover = originApprover;
        this.agent = agent;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.remark = remark;
        this.creator = creator;
        this.createTime = createTime;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
        this.confirmor = confirmor;
        this.confirmTime = confirmTime;
    }

   
    // Property accessors

    public String getAgentId() {
        return this.agentId;
    }
    
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getStationId() {
        return this.stationId;
    }
    
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }

    public String getOriginApprover() {
        return this.originApprover;
    }
    
    public void setOriginApprover(String originApprover) {
        this.originApprover = originApprover;
    }

    public String getAgent() {
        return this.agent;
    }
    
    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getModuleId() {
        return this.moduleId;
    }
    
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return this.moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Timestamp getBeginTime() {
        return this.beginTime;
    }
    
    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return this.creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return this.modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getConfirmor() {
        return this.confirmor;
    }
    
    public void setConfirmor(String confirmor) {
        this.confirmor = confirmor;
    }

    public Timestamp getConfirmTime() {
        return this.confirmTime;
    }
    
    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }
   








}
