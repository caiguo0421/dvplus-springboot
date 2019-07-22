package cn.sf_soft.office.postaudit.model;

import java.util.Date;
import java.util.List;

/**
 * 事后审核查询条件
 * @author king
 * @create 2013-9-25上午9:45:25
 */
public class PostAuditSearchCriteria {
	private String documentNo; 
	private String operator;//操作人
	private String beginTime;//操作起始时间
	private String endTime;//操作截止时间
	private List<String> stationIds;
	
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<String> getStationIds() {
		return stationIds;
	}
	public void setStationIds(List<String> stationIds) {
		this.stationIds = stationIds;
	}
	@Override
	public String toString() {
		return "PostAuditSearchCriteria [documentNo=" + documentNo
				+ ", operator=" + operator + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", stationIds=" + stationIds + "]";
	}
}
