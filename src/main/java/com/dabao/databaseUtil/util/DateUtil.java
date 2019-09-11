package com.dabao.databaseUtil.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 日期操作类，主要是提供一些静态方法实现String与Date对象的转换，以及String在不同格式之间的转换。 y--years M--months
 * d--days H--hours m--minutes s--seconds S--milliseconds
 * 
 * @author WRF
 * @version 1.0
 */
public class DateUtil {
	/**
	 * 转换格式为yyyy-MM-dd HH:mm:ss:SSS。
	 */
	public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	/**
	 * 转换格式为yyyy-MM-dd HH:mm:ss。
	 */
	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 转换格式为yyyy-MM-dd-HH-mm-ss。
	 */
	public final static String yyyy_MM_dd_HH_mm_ss_2 = "yyyy-MM-dd-HH-mm-ss";

	/**
	 * 转换格式为yyyy-MM-dd HH:mm。
	 */
	public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

	/**
	 * 转换格式为yyyy-MM-dd。
	 */
	public final static String yyyy_MM_dd = "yyyy-MM-dd";

	/**
	 * 转换格式为yyyy-M-d H:m:s:S
	 */
	public final static String yyyy_M_d_H_m_s_S = "yyyy-M-d H:m:s:S";

	/**
	 * 转换格式为yyyy-M-d H:m:s
	 */
	public final static String yyyy_M_d_H_m_s = "yyyy-M-d H:m:s";

	/**
	 * 转换格式为yyyy-M-d H:m
	 */
	public final static String yyyy_M_d_H_m = "yyyy-M-d H:m";

	/**
	 * 转换成格式为yyyy-M-d
	 */
	public final static String yyyy_M_d = "yyyy-M-d";

	/**
	 * 转换格式为yyyyMMdd HH:mm:ss:SSS。
	 */
	public final static String yyyyMMdd_HH_mm_ss_SSS = "yyyyMMdd HH:mm:ss:SSS";

	/**
	 * 转换格式为yyyyMMdd HH:mm:ss。
	 */
	public final static String yyyyMMdd_HH_mm_ss = "yyyyMMdd HH:mm:ss";

	/**
	 * 转换格式为yyyyMMdd HH:mm。
	 */
	public final static String yyyyMMdd_HH_mm = "yyyyMMdd HH:mm";

	/**
	 * 转换格式为yyyyMMdd。
	 */
	public final static String yyyyMMdd = "yyyyMMdd";

	/**
	 * 转换格式为yyyyMMddHHmmssSSS。
	 */
	public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 转换格式为yyyyMMddHHmmss。
	 */
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	/**
	 * 转换格式为yyyyMMddHHmm。
	 */
	public final static String yyyyMMddHHmm = "yyyyMMddHHmm";

	/**
	 * 转换格式为yyyyMMddHHmm。
	 */
	public final static String yyyyMMddHHmmSSS = "yyyyMMddHHmmSSS";

	/**
	 * 转换格式为yyyyMM。
	 */
	public final static String yyyyMM = "yyyyMM";

	/**
	 * 转换格式为yyyy_MM。
	 */
	public final static String yyyy_MM = "yyyy-MM";

	/**
	 * 转换格式为yyyy。
	 */
	public final static String yyyy = "yyyy";

	/**
	 * 转换格式为MM。
	 */
	public final static String MM = "MM";

	/**
	 * 转换格式为yyyy年M月d日H点m分。
	 */
	public final static String yyyyMdHm_zh = "yyyy年M月d日H点m分";
	
	private static final long ONE_MINUTE = 60;//一分钟60秒
	private static final long ONE_HOUR = 3600;//一小时3600秒
	private static final long ONE_DAY = 86400;//一天86400秒
	private static final long ONE_MONTH = 2592000;//一个月2592000秒
	private static final long ONE_YEAR = 31104000;//一年32204000秒

	/**
	 * 将String类型的日期转换为Date对象。
	 * 
	 * @param dateString 代表日期的字符串。
	 * @param style      转换格式。
	 * @return 日期对象。
	 */
	public static Date stringToDate(String dateString, String style) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINESE);
			return format.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将Date转换成为指定格式的String。
	 * 
	 * @param date  日期对象。
	 * @param style 转换格式。
	 * @return 代表日期的字符串。
	 */
	public static String dateToString(Date date, String style) {
		SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINESE);
		return format.format(date);
	}

	/**
	 * 将日期字符串从源格式转换成为目标格式。
	 * 
	 * @param dateString  日期字符串。
	 * @param sourceStyle 源格式。
	 * @param tagetStyle  目标格式。
	 * @return 转换成为目标格式后的字符串。
	 */
	public static String stringToString(String dateString, String sourceStyle, String tagetStyle) {
		Date date = stringToDate(dateString, sourceStyle);
		return dateToString(date, tagetStyle);
	}

	/**
	 * 获取当前系统时间。
	 * 
	 * @return 当前系统时间。
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 获取从开始年到当前年的年份列表
	 * 
	 * @param startYear 开始年
	 * @return
	 */
	public static List<String> getYearList(int startYear) {
		List<String> yearSelectList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(startYear, 0, 5);
		while (DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy)
				.compareTo(DateUtil.dateToString(DateUtil.getCurrentDate(), DateUtil.yyyy)) <= 0) {
			yearSelectList.add(DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy));
			calendar.add(Calendar.YEAR, 1);
		}
		return yearSelectList;
	}
	
	
	
	/**
	 * 距离今天多久
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String fromToday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		long time = date.getTime() / 1000;
		long now = new Date().getTime() / 1000;
		long ago = now - time;
		if (ago <= ONE_HOUR)
			return ago / ONE_MINUTE + "分钟前";
		else if (ago <= ONE_DAY)
			return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟前";
		else if (ago <= ONE_DAY * 2)
			return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
		else if (ago <= ONE_DAY * 3)
			return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
		else if (ago <= ONE_MONTH) {
			long day = ago / ONE_DAY;
			return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
		} else if (ago <= ONE_YEAR) {
			long month = ago / ONE_MONTH;
			long day = ago % ONE_MONTH / ONE_DAY;
			return month + "个月" + day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE)
					+ "分";
		} else {
			long year = ago / ONE_YEAR;
			int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0
															// so month+1
			return year + "年前" + month + "月" + calendar.get(Calendar.DATE) + "日";
		}

	}

	/**
	 * 距离截止日期还有多长时间
	 * 
	 * @param date
	 * @return
	 */
	public static String fromDeadline(Date date) {
		long deadline = date.getTime() / 1000;
		long now = (new Date().getTime()) / 1000;
		long remain = deadline - now;
		if (remain <= ONE_HOUR)
			return "仅剩余" + remain / ONE_MINUTE + "分钟";
		else if (remain <= ONE_DAY)
			return "仅剩余" + remain / ONE_HOUR + "小时" + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
		else {
			long day = remain / ONE_DAY;
			long hour = remain % ONE_DAY / ONE_HOUR;
			long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
			return "剩余" + day + "天" + hour + "小时" + minute + "分钟";
		}

	}

	/**
	 * 距离今天的绝对时间
	 * 
	 * @param date
	 * @return
	 */
	public static String toToday(Date date) {
		long time = date.getTime() / 1000;
		long now = (new Date().getTime()) / 1000;
		long ago = now - time;
		
		if(ago<=ONE_MINUTE){
			
			String str=ago+"秒前";
			
		   if(ago<=3){
			   str= "刚刚";
		   }
			return str;
		}
		
		else if (ago <= ONE_HOUR)
			return ago / ONE_MINUTE + "分钟前";
		else if (ago <= ONE_DAY)
			return ago / ONE_HOUR + "小时前" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟";
		else if (ago <= ONE_DAY * 2)
			return "昨天" + (ago - ONE_DAY) / ONE_HOUR + "点" + (ago - ONE_DAY) % ONE_HOUR / ONE_MINUTE + "分";
		else if (ago <= ONE_DAY * 3) {
			long hour = ago - ONE_DAY * 2;
			return "前天" + hour / ONE_HOUR + "点" + hour % ONE_HOUR / ONE_MINUTE + "分";
		} else if (ago <= ONE_MONTH) {
			long day = ago / ONE_DAY;
			long hour = ago % ONE_DAY / ONE_HOUR;
			long minute = ago % ONE_DAY % ONE_HOUR / ONE_MINUTE;
			return day + "天前" + hour + "点" + minute + "分";
		} else if (ago <= ONE_YEAR) {
			long month = ago / ONE_MONTH;
			long day = ago % ONE_MONTH / ONE_DAY;
			long hour = ago % ONE_MONTH % ONE_DAY / ONE_HOUR;
			long minute = ago % ONE_MONTH % ONE_DAY % ONE_HOUR / ONE_MINUTE;
			return month + "个月" + day + "天" + hour + "点" + minute + "分前";
		} else {
			long year = ago / ONE_YEAR;
			long month = ago % ONE_YEAR / ONE_MONTH;
			long day = ago % ONE_YEAR % ONE_MONTH / ONE_DAY;
			return year + "年前" + month + "月" + day + "天";
		}

	}
	
	/**
	 * 距离今天的绝对时间
	 * 
	 * @param date
	 * @return
	 */
	public static String toTodayBak(Date date) {
		long time = date.getTime() / 1000;
		long now = (new Date().getTime()) / 1000;
		long ago = now - time;
		if (ago <= ONE_HOUR)
			return ago / ONE_MINUTE + "分钟前";
		else if (ago <= ONE_DAY)
			return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟";
		else if (ago <= ONE_DAY * 2)
			return "昨天" + (ago - ONE_DAY) / ONE_HOUR + "点" + (ago - ONE_DAY) % ONE_HOUR / ONE_MINUTE + "分";
		else if (ago <= ONE_DAY * 3) {
			long hour = ago - ONE_DAY * 2;
			return "天前" + hour / ONE_HOUR + "点" + hour % ONE_HOUR / ONE_MINUTE + "分";
		} else if (ago <= ONE_MONTH) {
			long day = ago / ONE_DAY;
			long hour = ago % ONE_DAY / ONE_HOUR;
			long minute = ago % ONE_DAY % ONE_HOUR / ONE_MINUTE;
			return day + "天前" + hour + "点" + minute + "分";
		} else if (ago <= ONE_YEAR) {
			long month = ago / ONE_MONTH;
			long day = ago % ONE_MONTH / ONE_DAY;
			long hour = ago % ONE_MONTH % ONE_DAY / ONE_HOUR;
			long minute = ago % ONE_MONTH % ONE_DAY % ONE_HOUR / ONE_MINUTE;
			return month + "个月" + day + "天" + hour + "点" + minute + "分";
		} else {
			long year = ago / ONE_YEAR;
			long month = ago % ONE_YEAR / ONE_MONTH;
			long day = ago % ONE_YEAR % ONE_MONTH / ONE_DAY;
			return year + "年前" + month + "月" + day + "天";
		}

	}
	
	/**
	 *    转化前端时间为mysql datetime 类型 
	 * @param strDate
	 * @return
	 */
	public static Date formatterDatime( ) throws ParseException {
	     	java.util.Date date = new java.util.Date();          // 获取一个Date对象
		    Timestamp timeStamp = new Timestamp(date.getTime());
		   return timeStamp;
	}

}
