package cn.sf_soft.report.ichartjs;
/**
 * 实现此接口可将数据转为IChartJs折线图数据源
 * @author king
 * @create 2013-11-11下午5:56:22
 */
public interface LineAble {
	
	public float getLineValue(int line);
	
	public String getLabel();
}
