package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;
/**
 * 
 * @Title: 维修三包报表
 * @date 2013-8-29 上午10:08:54 
 * @author cw
 */

public class MaintainClaimsReport implements Serializable,LineAble,ColumnAble,TouchChartAble {
	private String appraisalModeMeaning;
	private Integer appraisalQty;
	private Double appraisalMoney;
	
	public MaintainClaimsReport(){
		
	}
	
	public MaintainClaimsReport(String appraisalModeMeaning,
			Integer appraisalQty, Double appraisalMoney) {
		super();
		this.appraisalModeMeaning = appraisalModeMeaning;
		this.appraisalQty = appraisalQty;
		this.appraisalMoney = appraisalMoney;
	}
	public String getAppraisalModeMeaning() {
		return appraisalModeMeaning;
	}
	public void setAppraisalModeMeaning(String appraisalModeMeaning) {
		this.appraisalModeMeaning = appraisalModeMeaning;
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
	
	public String toChartData(){
		return "{name:'" + (appraisalModeMeaning == null ? "未定义":appraisalModeMeaning) + "', value:" + appraisalQty + ", color:'#1f7e92'}";
	}

	public String getName() {
		return appraisalModeMeaning == null ? "未定义":appraisalModeMeaning;
	}

	public float getColumnValue() {
		return appraisalQty == null ? 0 :appraisalQty;
	}

	public float getLineValue(int line) {
		Double result = appraisalMoney == null ? 0:appraisalMoney;
		return Float.valueOf(result.toString());
	}

	public String getLabel() {
		return appraisalModeMeaning == null ? "未定义":appraisalModeMeaning;
	}

	public String getTouchChartName() {
		return appraisalModeMeaning == null ? "未定义":appraisalModeMeaning;
	}

	public float getTouchChartValue() {
		return appraisalQty == null ? 0 :appraisalQty;
	}

	public float getTouchChartValue2() {
		Double result = appraisalMoney == null ? 0:appraisalMoney;
		return Float.valueOf(result.toString());
	}
	

}
