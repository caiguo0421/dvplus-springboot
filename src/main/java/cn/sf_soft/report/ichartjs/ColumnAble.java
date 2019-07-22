package cn.sf_soft.report.ichartjs;
/**
 * 实现此接口可将数据转为IChartJs柱状图数据源
 * @author king
 * @create 2013-11-11下午5:57:11
 */
public interface ColumnAble {
	
	/**
	 * 柱子的名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 柱子的值
	 * @return
	 */
	public float getColumnValue();
}
