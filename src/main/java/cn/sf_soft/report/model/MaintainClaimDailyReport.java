package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;
/**
 * 维修索赔日报
 * @author king
 * @create 2013-8-1上午9:21:37
 */
public class MaintainClaimDailyReport implements Serializable,LineAble,ColumnAble,TouchChartAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6137059592209043153L;
	private String date;			//日期
	private String supplierName;	//厂家
	private Integer appraisalQty;	//台次
	private Double appraisalMoney;	//产值
	private Integer monthAppraisalQty;//月累计台次
	private Double monthAppraisalMoney;//月累计产值
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getAppraisalQty() {
		return appraisalQty;
	}
	public void setAppraisalQty(Integer appraisalQty) {
		this.appraisalQty = appraisalQty;
	}
	public Double getAppraisalMoney() {
		return appraisalMoney;
	}
	public void setAppraisalMoney(Double appraisalMoney) {
		this.appraisalMoney = appraisalMoney;
	}
	public Integer getMonthAppraisalQty() {
		return monthAppraisalQty;
	}
	public void setMonthAppraisalQty(Integer monthAppraisalQty) {
		this.monthAppraisalQty = monthAppraisalQty;
	}
	public Double getMonthAppraisalMoney() {
		return monthAppraisalMoney;
	}
	public void setMonthAppraisalMoney(Double monthAppraisalMoney) {
		this.monthAppraisalMoney = monthAppraisalMoney;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MaintainClaimDailyReport [date=" + date + ", supplierName="
				+ supplierName + ", appraisalQty=" + appraisalQty
				+ ", appraisalMoney=" + appraisalMoney + ", monthAppraisalQty="
				+ monthAppraisalQty + ", monthAppraisalMoney="
				+ monthAppraisalMoney + "]";
	}
	
	public String toChartData(){
		return "{name:'" + (supplierName == null ? "未定义":supplierName) + "', value:" + appraisalQty + ", color:'#1f7e92'}";
	}
	public String getName() {
		return supplierName == null ? "未定义":supplierName;
	}
	public float getColumnValue() {
		return appraisalQty == null ? 0:appraisalQty;
	}
	public float getLineValue(int line) {
		 	Double result = appraisalMoney == null ? 0:appraisalMoney;
		 return Float.valueOf(result.toString());
	}
	public String getLabel() {
		return supplierName == null ? "未定义":supplierName;
	}
	
	public String getTouchChartName() {
		return supplierName == null ? "未定义":supplierName;
	}
	public float getTouchChartValue() {
		return appraisalQty == null ? 0:appraisalQty;
	}
	public float getTouchChartValue2() {
		Double result = appraisalMoney == null ? 0:appraisalMoney;
		 return Float.valueOf(result.toString());
	}
}
