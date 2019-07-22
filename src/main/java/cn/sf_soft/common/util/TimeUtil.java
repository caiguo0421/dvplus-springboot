package cn.sf_soft.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	/**
	 * 检查开始时间是否小于结束时间，以及开始时间与结束时间之间相差的天数是否超过限制
	 * @param beginTime
	 * @param endTime
	 * @return	null,如果检查通过； 否则返回检查不通过的原因
	 */
	public static String compareTimes(String beginTime,String endTime){
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;
		try {
			start = sdf.parse(beginTime);
			end = sdf.parse(endTime);
			startDate.setTime(start);
			endDate.setTime(end);
			if(endDate.before(startDate))
			{
				return "截止日期不能小于开始时间";
			}
			startDate.add(Calendar.DATE, 31);
			if(!startDate.after(endDate)){
				return "开始日期和截止日期之间最多只能相差31天";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	
	}
}
