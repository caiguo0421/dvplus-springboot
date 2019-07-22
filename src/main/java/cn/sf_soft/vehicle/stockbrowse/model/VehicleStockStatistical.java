package cn.sf_soft.vehicle.stockbrowse.model;

/**
 * 车辆库存统计信息
 * @Title: 车辆总台数  订购台数 可售台数  总成本
 * @date 2013-9-3 上午10:42:41 
 * @author cw
 */
public class VehicleStockStatistical {

	private long totalCount;
	private long orderCount;
	private long vendibleCount;
	private double  totalCost;
	
	public VehicleStockStatistical(){}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}

	public long getVendibleCount() {
		return vendibleCount;
	}

	public void setVendibleCount(long vendibleCount) {
		this.vendibleCount = vendibleCount;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	
	
	
}
