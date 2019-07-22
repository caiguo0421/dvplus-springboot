package cn.sf_soft.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataHelper {

	
	public static List<Map<String, Object>> selectBy(
			List<Map<String, Object>> dataSource, String[] groupByFields,
			String[] sumFields) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>(dataSource.size());
		Map<String, Map<String, Object>> selectd = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> map:dataSource){
			
			//groupField
			StringBuilder sb = new StringBuilder();
			for(String column:groupByFields){
				sb.append(map.get(column) + "_");
			}
			String key = sb.toString();
			
			if(selectd.containsKey(key)){
				//SUM求和操作
				Map<String, Object> m = selectd.get(key);
				for(String column:sumFields){
					Float a = null;
					try {
						Object value = map.get(column);
						a = value == null ? 0 : Float.parseFloat(value.toString());
					} catch (NumberFormatException e) {
						throw new RuntimeException( "列[" + column + "]的值：" + map.get(column) + "不能进行SUM求和操作");
					}
					Float b = null;
					try {
						Object value = m.get(column);
						b = value == null ? 0 : Float.parseFloat(value.toString());
					} catch (NumberFormatException e) {
						throw new RuntimeException( "列[" + column + "]的值：" + m.get(column) + "不能进行SUM求和操作");
					}
					m.put(column, a + b);
				}
			}else{
				//插入记录
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				for(String column:groupByFields){
					tmpMap.put(column, map.get(column));
				}
				for(String column:sumFields){
					tmpMap.put(column, map.get(column));
				}
				selectd.put(key, tmpMap);
			}
		}
		for(Map<String, Object> map:selectd.values()){
			resultList.add(map);
		}
		return resultList;
	}
	
	
	/**
	 * 获得一个Class的所有属性列表，并将【aBbCc】形式的名称转为【a_bb_cc】形式的名称
	 * @param aClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getClassFieldColumn(Class aClass) {
		Field[] dataSourceFields = aClass.getDeclaredFields();
		List<String> dataSourceFieldNames = new ArrayList<String>(dataSourceFields.length);
		for(Field field:dataSourceFields){
			dataSourceFieldNames.add(getColumnName(field.getName()));
		}
		return dataSourceFieldNames;
	}
	/**
	 * 根据属性名，得到obj的属性值
	 * @param obj
	 * @param propertyName 数据库中的属性名
	 * @return
	 */
	public static Object getPropperty(Object obj, String columnName){
		String propertyName = getPropertyName(columnName);
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			Object value = field.get(obj);
			return value;
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("数据源中不存在属性列:" + columnName);
		}catch (IllegalAccessException e) {
			throw new RuntimeException("不能访问数据源的" + columnName +"属性");
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
	}
	/**
	 * 将属性名转为列名，即：将 aBbCc 转为 a_bb_cc
	 * @param propertyName
	 * @return
	 */
	public static String getColumnName(String propertyName){
		char[] chars = propertyName.toCharArray();
		StringBuilder sb = new StringBuilder(chars.length + 5);
		for(char c:chars){
			if(c > 64 && c < 91 ){
				sb.append("_").append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将列名转换为属性名，即将 a_bb_cc 转为 aBbCc
	 * @param columnName
	 * @return
	 */
	public static String getPropertyName(String columnName){
		String[] array = columnName.split("_");
		StringBuilder sb = new StringBuilder(array[0]);
		for(int i=1; i<array.length; i++){
			String s = array[i];
			sb.append(s.substring(0, 1).toUpperCase());
			sb.append(s.substring(1));
		}
		return sb.toString();
	}
	
	
	public static void setFieldValue(Object obj, String fieldName, Object fieldValue){
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (SecurityException e) {
//			e.printStackTrace();
			throw new RuntimeException("禁止访问" + obj.getClass().getName() + "的属性:[" + fieldName + "]", e);
		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
			throw new RuntimeException(obj.getClass().getCanonicalName() + "中不存在:[" + fieldName + "]属性");
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
			throw new RuntimeException("设置" + obj.getClass().getName() + "的属性:[" + fieldName + "]出错，无效的参数:" + fieldValue, e);
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
			throw new RuntimeException("访问" + obj.getClass().getName() + "的属性:[" + fieldName + "]出错", e);
		}
	}
	
	public static void parseWhereExpression(String whereExpression){
//		whereExpression = whereExpression.toLowerCase();
//		String[] conditions = whereExpression.split("(\\s+and\\s+|\\s+or\\s+)");
//		String regex = "";
//		for(String s:conditions){
//			System.out.println(s.trim());
//		}
		
		String regex = "(\\w+[\\W|\\w]\\w+){1}(\\s+and\\s+|\\s+or\\s+)?";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher mat = pattern.matcher(whereExpression);
		while(mat.find()){
			System.out.println(mat.group(1) + "; " + mat.group(2));
		}
		
//		String regex = "(\\w+)\\s*(=|>|<|>=|<=|LIKE|IS)+\\s*(\\w+)";
//		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//		Matcher mat = pattern.matcher(whereExpression);
//		while(mat.find()){
//			System.out.println(mat.group(1) + "  " + mat.group(2) + "  " + mat.group(3));
//		}
		
	}

	public static void main(String args[]){
//		parseWhereExpression("name=11 and  mark=2 or h like 3");
		String s = "借[part_full_name]\\[supplier_name][pis_no]";
		System.out.println(s.replace("\\", "\\\\"));
	}
}
