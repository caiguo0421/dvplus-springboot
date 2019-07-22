package cn.sf_soft.report.ichartjs;

import java.util.List;

import cn.sf_soft.common.util.Tools;
/**
 * IChartJs JSON格式数据源转换工具
 * @author king
 * @create 2013-11-11下午5:57:36
 */
public class IchartJsHelper {
	/**
	 * 颜色种子，若随机生成颜色，则从种子颜色中取
	 */
	private static final String[] COLOR_SEED = new String[]{"#1f7e92", "#D3B45D", "#B4CCDB", "#89B784", "#B08B74", "#B482A0"};
	/**
	 * 柱状图柱形的默认颜色
	 */
	private static final String DEFAULE_COLUMN_COLOR = "#1f7e92";
	/**
	 * 折线图折线的默认颜色
	 */
	private static final String DEFAULE_LINE_COLOR = "red";
	/**
	 * 折线图折线默认的线宽度
	 */
	private static final int DEFAULE_LINE_WIDTH = 1;
	
	/**
	 * 将数据源转成 IChartJs柱形图数据源的JSON格式, 柱形使用默认的颜色
	 * @param dataSource
	 * @return
	 */
	public static String toColumnJson(List<? extends ColumnAble> dataSource){
		return toColumnJson(dataSource, DEFAULE_COLUMN_COLOR);
	}
	/**
	 * 将数据源转成 IChartJs柱形图数据源的JSON格式
	 * @param dataSource
	 * @param columnColor	每根柱形的颜色
	 * @return
	 */
	public static String toColumnJson(List<? extends ColumnAble> dataSource, String columnColor){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(ColumnAble data:dataSource){
			sb.append("{name:'" + data.getName() + "', value:" + data.getColumnValue() + ", color:'" + columnColor + "'}, ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 将数据源转成 Toucha Chart 组合图的JSON格式
	 * @return
	 */
	public static String toTouchChartJson(List<? extends TouchChartAble> dataSource){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(TouchChartAble data:dataSource){
			sb.append("{name:'" + data.getTouchChartName() + "', value:" + data.getTouchChartValue() + ", value2:" + data.getTouchChartValue2() + "}, ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	
	
	/**
	 * 将数据源转成 IChartJs 多条柱形图数据源的JSON格式，
	 * @param dataSource
	 * @param names		每组柱形的名称
	 * @param colors	每组柱形的颜色，格式："#1f7e92"
	 * @return 长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toMutipleColumnJson(List<? extends ColumnMultipleAble> dataSource, String[] names, String[] colors){
		if(names == null || colors == null || names.length != colors.length){
			throw new RuntimeException("names参数与colors参数长度需一致且>0");
		}
		StringBuilder sb = new StringBuilder("[");
		for(int i=0; i<names.length; i++){
			sb.append("{name:'" + names[i] + "', color:'" + colors[i] + "', value:[");
			for(ColumnMultipleAble data:dataSource){
				sb.append(data.getValue(i) + ",");
			}
			if(sb.charAt(sb.length() - 1) != '[')
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		StringBuilder labels = new StringBuilder("[");
		for(ColumnMultipleAble data:dataSource){
			labels.append("'" + data.getLabel() + "',");
		}
		if(labels.charAt(labels.length() - 1) != '[')
			labels.deleteCharAt(labels.length() - 1);
		labels.append("]");
		
		return new String[]{sb.toString(), labels.toString()};
	}
	
	/**
	 * 将数据源转成 IChartJs 多条柱形图数据源的JSON格式，并使用默认的柱形颜色
	 * @param dataSource
	 * @param names 每组柱形的名称
	 * @return 长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toMutipleColumnJson(List<? extends ColumnMultipleAble> dataSource, String[] names){
		return toMutipleColumnJson(dataSource, names, getColor(names.length));
	}
	
	/**
	 * 将数据源转成 IChartJs折线图数据源的JSON格式，并使用默认的线宽及颜色
	 * @param name			折线的名称
	 * @param dataSource
	 * @return 长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toLineJson(String name, List<? extends LineAble> dataSource){
		return toLineJson(dataSource, new String[]{name}, new String[]{DEFAULE_LINE_COLOR});
	}
	/**
	 * 将数据源转成 IChartJs折线图数据源的JSON格式，并使用默认的线宽及颜色
	 * @param dataSource
	 * @param names
	 * @return 长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toLineJson(List<? extends LineAble> dataSource, String[] names){
		return toLineJson(dataSource, names, getColor(names.length));
	}
	/**
	 * 将数据源转成 IChartJs折线图数据源的JSON格式，并使用默认的线宽
	 * @param dataSource
	 * @param names
	 * @param colors
	 * @return 长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toLineJson(List<? extends LineAble> dataSource, String[] names, String[] colors){
		return toLineJson(dataSource, names, colors, DEFAULE_LINE_WIDTH);
	}
	
	/**
	 * 将数据源转成 IChartJs折线图数据源的JSON格式
	 * @param dataSource
	 * @param names		折线的名称
	 * @param colors	折线的颜色，格式："#1f7e92"
	 * @param lineWidth	折线的宽度
	 * @return  长度为2的数组，分别为JSON格式数据源、JSON格式Label
	 */
	public static String[] toLineJson(List<? extends LineAble> dataSource, String[] names, String[] colors, int lineWidth){
		if(names == null || colors == null || names.length != colors.length){
			throw new RuntimeException("names参数与colors参数长度需一致且>0");
		}
		StringBuilder sb = new StringBuilder("[");
		for(int i=0; i<names.length; i++){
			sb.append("{ name:'" + names[i] + "', color:'" + colors[i] + "', line_width:" + lineWidth + ", value:[");
			for(LineAble data:dataSource){
				sb.append(data.getLineValue(i) + ",");
			}
			if(sb.charAt(sb.length() - 1) != '[')
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		StringBuilder labels = new StringBuilder("[");
		for(LineAble data:dataSource){
			labels.append("'" + data.getLabel() + "',");
		}
		if(labels.charAt(labels.length() - 1) != '[')
			labels.deleteCharAt(labels.length() - 1);
		labels.append("]");
		return new String[]{sb.toString(), labels.toString()};
	}
	
	private static String[] getColor(int length){
		String[] colors = new String[length];
		int len = COLOR_SEED.length;
		//从种子颜色中取
		for(int i=0; i<colors.length; i++){
			colors[i] = COLOR_SEED[i];
		}
		//超出长度随机生成颜色
		for(int i=len; i<colors.length; i++){
			colors[i] = Tools.randomColor();
		}
		return colors;
	}
}
