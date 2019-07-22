package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.ColumnMultipleAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 保险购险统计
 * @author king
 * @create 2013-11-8上午10:57:20
 */
public class InsurancePurchaseReport implements Serializable, ColumnAble, ColumnMultipleAble,TouchChartAble{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1578975517987616526L;
	private String stationName;//站点名称
	private String statContent;//统计内容
	private int firstInsurance;//首保数量
	private int continueInsurance;//续保数量
	private int transferInsurance;//转保数量
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStatContent() {
		return statContent;
	}
	public void setStatContent(String statContent) {
		this.statContent = statContent;
	}
	public int getFirstInsurance() {
		return firstInsurance;
	}
	public void setFirstInsurance(int firstInsurance) {
		this.firstInsurance = firstInsurance;
	}
	public int getContinueInsurance() {
		return continueInsurance;
	}
	public void setContinueInsurance(int continueInsurance) {
		this.continueInsurance = continueInsurance;
	}
	public int getTransferInsurance() {
		return transferInsurance;
	}
	public void setTransferInsurance(int transferInsurance) {
		this.transferInsurance = transferInsurance;
	}
	@Override
	public String toString() {
		return "InsurancePurchaseReport [stationName=" + stationName
				+ ", statContent=" + statContent + ", firstInsurance="
				+ firstInsurance + ", continueInsurance=" + continueInsurance
				+ ", transferInsurance=" + transferInsurance + "]";
	}
	
	
	public String getName() {
		return statContent;
	}
	public float getColumnValue() {
		return firstInsurance;
	}
	
	public float getValue(int column) {
		switch (column) {
		case 0:
			return firstInsurance;
		case 1:
			return continueInsurance;
		case 2:
			return transferInsurance;
		}
		return 0;
	}
	public String getLabel() {
		return statContent;
	}
	public String getTouchChartName() {
		return stationName+statContent;
	}
	public float getTouchChartValue() {
		return firstInsurance;
	}
	public float getTouchChartValue2() {
		return continueInsurance;
	}
}
