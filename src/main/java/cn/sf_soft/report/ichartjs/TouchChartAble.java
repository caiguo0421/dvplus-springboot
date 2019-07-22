package cn.sf_soft.report.ichartjs;
/**
 *  data: [
		      {name: 'pie one',   data1: 60, data2: 12, data3: 14, data4: 8,  data5: 13}
		  ]
 * 实现此接口可将数据转为TouchChart数据源
 * @author cw
 * @date 2014-8-15 下午4:49:25
 */
public interface TouchChartAble {

	/**
	 * 名称
	 * @return
	 */
	public String getTouchChartName();
	/**
	 * 值1
	 */
	public float getTouchChartValue();
	/**
	 * 值2
	 */
	public float getTouchChartValue2();
}
