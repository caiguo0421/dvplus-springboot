package cn.sf_soft.common.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	private static  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 金额的标准输出格式
	 */
	private  static  DecimalFormat currencyFormat = new DecimalFormat(",###.00");

	/**
	 * 专门针对销售合同变更的 金额输出：四舍五入到元
	 */
	private static DecimalFormat contractCurrencyFormat = new DecimalFormat(",###");
	/**
	 * 得到文件大小的不同单位表示
	 *
	 * @param bytes
	 * @return
	 */
	public static String formatFileSize(long bytes) {
		double baseKB = 1024;
		double baseMB = 1048576;
		if (bytes == 0) {
			return "-";
		}
		if (bytes < baseKB) {
			return bytes + "B";
		} else if (bytes < baseMB) {
			String size = bytes / baseKB + "";
			return size.substring(0, size.indexOf(".") + 2) + "KB";
		} else {
			String size = bytes / baseMB + "";
			return size.substring(0, size.indexOf(".") + 2) + "MB";
		}
	}

	/**
	 * 计算一个值向上取整的值
	 *
	 * @param maxmoney
	 * @return 向上取整的值，如果为0则返回5
	 */
	public static double computeValue(double maxmoney) {
		if (maxmoney < 5)
			return 5;
		String str = String.valueOf(maxmoney);
		int i = str.indexOf(".");
		if (i > 0) {
			str = str.substring(0, i);
		}
		char c = str.charAt(0);
		double b = Math.pow(10, str.length() - 1);
		maxmoney = b * (Integer.parseInt(c + "") + 1);
		return maxmoney;
	}

	/**
	 * 随机生成一个16进制格式的颜色,如"#1AFB89"
	 *
	 * @return
	 */
	public static String randomColor() {
		Random random = new Random();
		String r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		String g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		String b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		return "#" + r + g + b;
	}

	/**
	 * 字符串为空或长度为0
	 *
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * 当 d为null 时，默认为0
	 *
	 * @param d
	 * @return
	 */
	public static double toDouble(Number d) {
		return toDouble(d, 0.00D);
	}




	public static double objToDouble(Object val){
		return objToDouble(val,0.00D);
	}

	public static double objToDouble(Object val, double dEfault) {
		Double d = null;
		try {
			 d = Double.parseDouble(val.toString());
		}catch (Exception ex){
			return dEfault;
		}
		return d==null?dEfault:d;
	}


	/**
	 * 当 d为null 时，变成默认值
	 *
	 * @param d
	 * @param dEfault
	 * @return
	 */
	public static double toDouble(Number d, double dEfault) {
		if (d == null) {
			return dEfault;
		}
		return d.doubleValue();
	}

	public static boolean toBoolean(Boolean b) {
		return toBoolean(b, false);
	}


	public static boolean toBoolean(Boolean b, boolean dEfault) {
		if (b == null) {
			return dEfault;
		}
		return b;
	}

	public static short toShort(Number s){
		return toShort(s,(short)0);
	}

	/**
	 * 当 s为null 时，变成默认值
	 * @param s
	 * @param dEfault
	 * @return
	 */
	public static short toShort(Number s,short dEfault){
		if(s == null){
			return dEfault;
		}
		return s.shortValue();
	}

	/**
	 * 当 d为null 时，默认为0
	 *
	 * @param d
	 * @return
	 */
	public static int toInt(Number d) {
		if (d == null) {
			return 0;
		}
		return d.intValue();
	}

	public static BigDecimal toBigDecimal(Double input, BigDecimal nullDefault){
		if(null == input){
			return nullDefault;
		}else{
			return new BigDecimal(input.toString());
		}
	}

	public static BigDecimal toBigDecimal(Number input){
		if(null == input){
			return BigDecimal.ZERO;
		}if(input instanceof  BigDecimal){
			return (BigDecimal) input;
		}else{
			return new BigDecimal(input.toString());
		}
	}

	public static byte toByte(Number input){
		return toByte(input,(byte)0);
	}

	public static byte toByte(Number input,byte dEfaultVal){
		if(input==null){
			return dEfaultVal;
		}
		return input.byteValue();
	}


	public static long toLong(Number input){
		return toLong(input,0L);
	}
	public static long toLong(Number input,long dEfaultVal){
		if(input==null){
			return dEfaultVal;
		}
		return input.longValue();
	}


	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		if(date == null)
			return "";

		return dateFormat.format(date);
	}

	/**
	 * boolean 转换成字符串
	 * @param b
	 * @return
	 */
	public static String toBooleanStr(Boolean b) {
		if(b==null)
			return "否";
		return b?"是":"否";
	}

	/**
	 * 专门针对销售合同变更的 金额输出：四舍五入到元
	 * @param d
	 * @return
	 */
	public static String toCurrencyStr(Number d) {
		if(d==null||d.doubleValue()==0.00D){
			return "0";
		}
		return contractCurrencyFormat.format(d==null?0.00D:d);
	}

    static public String underline2SmallCamel(String line) {
        return underline2Camel(line, true);
    }
    static public String underline2Camel(String line, boolean smallCamel) {
        if (StringUtils.isEmpty(line)) {
            return StringUtils.EMPTY;
        }
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            buffer.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                buffer.append(word.substring(1, index));
            } else {
                buffer.append(word.substring(1));
            }
        }
        return buffer.toString();
    }

    static public String camel2Underline(String line) {
        if (StringUtils.isEmpty(line)) {
            return StringUtils.EMPTY;
        }
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("([a-z_\\d\\s]*)([A-Z]*)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group0 = matcher.group(1);
            String group1 = matcher.group(2);
            buffer.append(group0);
            if (group1 != null && group1.length() > 0) {
                buffer.append("_" + group1.toLowerCase());
            }
        }
        return buffer.toString();
    }

    public static String newGuid(){
		return UUID.randomUUID().toString();
	}

	public static String join(Iterable<?> iterable, String separator, String startString, String endString) {
		return iterable == null ? null : join(iterable.iterator(), separator, startString, endString);
	}

	public static String join(Iterator<?> iterator, String separator, String startString, String endString) {
		if (iterator == null) {
			return null;
		} else if (!iterator.hasNext()) {
			return "";
		} else {
			Object first = iterator.next();

			if (!iterator.hasNext()) {
				return union(first, startString, endString);
			} else {
				StringBuilder buf = new StringBuilder(256);
				if (first != null) {
					buf.append(union(first, startString, endString));
				}

				while(iterator.hasNext()) {
					if (separator != null) {
						buf.append(separator);
					}

					Object obj = iterator.next();
					if (obj != null) {
						buf.append(union(obj, startString, endString));
					}
				}

				return buf.toString();
			}
		}
	}

	public static String join(Object[] array, String separator, String startString, String endString) {
		return array == null ? null : join(array, separator, 0, array.length, startString, endString);
	}

	public static String join(Object[] array, String separator, int startIndex, int endIndex, String startString, String endString) {
		if (array == null) {
			return null;
		} else {
			int noOfItems = endIndex - startIndex;
			if (noOfItems <= 0) {
				return "";
			} else {
				StringBuilder buf = new StringBuilder(noOfItems * 16);

				for(int i = startIndex; i < endIndex; ++i) {
					if (i > startIndex) {
						buf.append(separator);
					}

					if (array[i] != null) {
						buf.append(union(array[i], startString, endString));
					}
				}

				return buf.toString();
			}
		}
	}

	private static String union(Object obj, String startString, String endString) {
		StringBuffer buffer = new StringBuffer();
		if(StringUtils.isNotEmpty(startString)) {
			buffer.append(startString);
		}
		buffer.append(ObjectUtils.toString(obj));
		if(StringUtils.isNotEmpty(endString)) {
			buffer.append(endString);
		}
		return buffer.toString();
	}

}
