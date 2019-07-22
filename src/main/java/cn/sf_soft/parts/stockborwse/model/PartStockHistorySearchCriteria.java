package cn.sf_soft.parts.stockborwse.model;

import java.util.Date;

/**
 * 配件库存出入库历史查询条件
 * @author cw
 * @create 2013-9-5上午10:27:57
 */
public class PartStockHistorySearchCriteria {
	
	private String stockId;
	//private String businessType;//业务类型
	private Integer outType;//0 出库     1入库
	private String partTo;//配件来源或去向
	private String startTime;	//起始时间
	private String endTime;	//截止时间
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	public Integer getOutType() {
		return outType;
	}
	public void setOutType(Integer outType) {
		this.outType = outType;
	}
	public String getPartTo() {
		return partTo;
	}
	public void setPartTo(String partTo) {
		this.partTo = partTo;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "PartStockHistorySearchCriteria [stockId=" + stockId
				+ ", outType=" + outType + ", partTo=" + partTo
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
