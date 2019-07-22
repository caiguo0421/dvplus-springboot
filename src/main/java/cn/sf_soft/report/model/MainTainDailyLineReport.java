package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 维修业务，日(产值/进场台次)曲线
 * @author king
 * @create 2013-7-24上午9:24:22
 */
public class MainTainDailyLineReport implements Serializable,LineAble,TouchChartAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9057042594848514411L;
	
	private String date;	//日期
	private Integer num;	//台次
	private Double money;	//结果
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public MainTainDailyLineReport(){
		
	}
	
	public MainTainDailyLineReport(String date, Double result){
		this.date = date;
		this.money =result;
	}
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	/**
	 * 重写equal()方法,当两个对象的日期一致时就认为是相同的
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof MainTainDailyLineReport){
			if(((MainTainDailyLineReport) obj).date != null && ((MainTainDailyLineReport) obj).date.equals(this.date)){
				return true;
			}
		}
		return false; 
	}
	public float getLineValue(int line) {
		switch (line) {
			case 0:
				return num == null ? 0: num;
			case 1:
				Double a = money == null ? 0 : money;
				return Float.valueOf(a.toString());
		}
		return 0;
	}
	public String getLabel() {
		return date.substring(5);
	}
	
	public String getTouchChartName() {
		return date.substring(5);
	}
	public float getTouchChartValue() {
		return num == null ? 0: num;
	}
	public float getTouchChartValue2() {
		Double a = money == null ? 0 : money;
		return Float.valueOf(a.toString());
	}
	
}
