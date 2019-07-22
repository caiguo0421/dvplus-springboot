package cn.sf_soft.report.model;

import cn.sf_soft.report.ichartjs.ColumnMultipleAble;

/**
 * 
 * @Title: 销售回访
 * @date 2013-11-9 上午10:20:42 
 * @author cw
 */
public class SaleCallBackReport implements ColumnMultipleAble{
	
	private String  stationId;
	private String  stationName;
	private Integer callBack;//已回访
	private Integer noCallBack;//未回访
	private Integer complainCount;//投诉数量
	
	public SaleCallBackReport(){
		
	}
	public SaleCallBackReport(String stationId, String stationName,
			Integer callBack, Integer noCallBack, Integer complainCount) {
		super();
		this.stationId = stationId;
		this.stationName = stationName;
		this.callBack = callBack;
		this.noCallBack = noCallBack;
		this.complainCount = complainCount;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getCallBack() {
		return callBack;
	}

	public void setCallBack(Integer callBack) {
		this.callBack = callBack;
	}

	public Integer getNoCallBack() {
		return noCallBack;
	}

	public void setNoCallBack(Integer noCallBack) {
		this.noCallBack = noCallBack;
	}

	public Integer getComplainCount() {
		return complainCount;
	}

	public void setComplainCount(Integer complainCount) {
		this.complainCount = complainCount;
	}
	
	
	public float getValue(int column) {
		switch (column) {
		case 0:
			return callBack == null ? 0:callBack;
		case 1:
			return complainCount== null ? 0:complainCount;
		}
		return 0;
	}
	public String getLabel() {
		return stationName;
	}
	
}
