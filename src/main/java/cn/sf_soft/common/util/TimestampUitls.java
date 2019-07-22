package cn.sf_soft.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * get current time and format it
 * @author minggo
 *
 */
public class TimestampUitls {

	public static Timestamp getTime(){
		return new Timestamp(new Date().getTime());
	}

	public static String getTimeOfString(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return formatter.format(currentTime);
	}

	public static Date formatDate(String date) throws ParseException{
		SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return spf.parse(date);
	}

	public static Date formatDate(String date, String format) throws ParseException{
		SimpleDateFormat spf = new SimpleDateFormat(format);
		return spf.parse(date);
	}


	public static Date formatDate2(String date) throws ParseException{
		SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
		return spf.parse(date);
	}

	public static String format(Timestamp timestamp, String format){
		SimpleDateFormat spf = new SimpleDateFormat(format);
		return spf.format(timestamp);
	}

	public static String getHoldingTime(Timestamp before, Timestamp end){
		if(null != before && null != end){
			long t = (end.getTime() - before.getTime())/1000/60;
			long d = t/(60*24);
			long h = (t%(60*24))/60;
			long m = t%(60);
			String str;
			if(d>0){
				str = d + "天";
			}else{
				str = "";
			}
			if(h>0){
				str += h + "小时";
			}
			if(m>0){
				str += m + "分";
			}
			if(StringUtils.isNotEmpty(str)){
				return str;
			}
		}
		return StringUtils.EMPTY;
	}

	public static int getElapsedMinutes(Date date1, Date date2, int type) {
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
		long minutes;
		Date d1 = gc1.getTime();
		Date d2 = gc2.getTime();
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long difference = Math.abs(l1 - l2) / 1000;
		/**
		 * 1表示天，2表示小时，3表示分钟，4表示秒.
		 */
		if (type == 1) {
			minutes = difference / 3600;
			minutes = (int) minutes / 24;
		} else if (type == 2) {
			minutes = difference / 3600;
			minutes = (int) minutes;
		} else if (type == 3) {
			minutes = difference / 60;
			minutes = (int) minutes;
		} else {
			minutes = (int) difference;
		}
		return (int) minutes;
	}

	public static int compareWithDate(Timestamp t1, Timestamp t2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(t1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(t2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

		long time1 = c1.getTime().getTime();
		long time2 = c2.getTime().getTime();

		if(time1 > time2){
			return 1;
		}else if(time1 < time2){
			return -1;
		}else{
			return 0;
		}
	}

}
