package cn.sf_soft.report.ichartjs;
/**
 * 多组柱形并列 的数据源转换
 * @author king
 * @create 2013-11-12上午9:53:32
 */
public interface ColumnMultipleAble {
	/**
	 * 获得每组柱形的值
	 * @param column	第几组柱形
	 * @return
	 */
	public float getValue(int column);
	
	public String getLabel();
}
