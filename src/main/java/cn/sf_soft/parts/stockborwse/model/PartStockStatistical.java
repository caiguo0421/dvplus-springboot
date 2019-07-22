package cn.sf_soft.parts.stockborwse.model;

/**
 * 车辆库存统计信息
 * @Title: 车辆总台数  订购台数 可售台数  总成本
 * @date 2017-4-28 下午16:42:41
 * @author henry
 */
public class PartStockStatistical {

	private long totalCount;
	private double  totalCost;
	
	public PartStockStatistical(){}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}
