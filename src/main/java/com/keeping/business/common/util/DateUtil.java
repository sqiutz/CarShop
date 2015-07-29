package com.keeping.business.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	/**
	 * 取当前的Date对象的long值秒数
	 * @return Long
	 */
	public static long getCurrentTime()	{
		GregorianCalendar calendar = new GregorianCalendar();
		return calendar.getTime().getTime()/1000;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentTime());
	}
	
	/**
	 * 取当前的日期时间
	 * @return 返回格式：字符串 yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static String getCurrentDateTime()
	{
		SimpleDateFormat bartDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		GregorianCalendar calendar = new GregorianCalendar();
		return  bartDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前时间 传入所需的时间格式时间
	 * ：yyyyMMddHHmmssSSS or yyyyMMdd or yyyy-MM-dd
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime1(String format)
	{
		SimpleDateFormat bartDateFormat =  new SimpleDateFormat(format);
		GregorianCalendar calendar = new GregorianCalendar();
		return  bartDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前时间 传入所需的时间格式时间
	 * ：
	 * @param yyyyMMddHHmmss or yyyyMMdd
	 * @return yyyy.MM.dd HH:mm:ss or yyyy.MM.dd
	 */
	public static String formatDateTime(long dt) {
		String dtStr = String.valueOf(dt);
		String formatStr = "";
		if (dtStr.length() >= 8) {
			formatStr = dtStr.substring(0, 4) + "." 
					+ dtStr.substring(4, 6) + "."
					+ dtStr.substring(6, 8);
		}
		if (dtStr.length() == 14) {
			formatStr = formatStr + " " + dtStr.substring(8, 2) + ":" 
					+ dtStr.substring(10, 12) + ":"
					+ dtStr.substring(12, 14);
		}
		return formatStr;
	}

	/**
	 * 获取当前时间 传入所需的时间格式时间
	 * ：
	 * @param Date
	 * @return yyyyMMddHHmmss
	 */
	public static long formatDateTime(Date dt) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return Long.parseLong(format.format(dt));
	}
	
	/**
	 * 比较两个timestamp类型的日期，格式：yyyy-mm-dd hh:mm:ss
	 * @param val1
	 * @param val2
	 * @return
	 * @throws ParseException 
	 */
	public static int compareTo(String val1, String val2) throws ParseException{
		String format = "yyyy-mm-dd hh:mm:ss";
		return compareTo(val1, val2, format);
	}
	
	/**
	 * 比较两个日期，
	 * @param val1
	 * @param val2
	 * @param format 格式如：yyyy-mm-dd hh:mm:ss，yyyymmdd,yyyymmddhhmmssSSS....
	 * @return
	 * @throws ParseException 
	 */
	public static int compareTo(String val1, String val2, String format) 
			throws ParseException{
		SimpleDateFormat TimestampDF =  new SimpleDateFormat(format);
		Calendar calendar1 =Calendar.getInstance();
		Calendar calendar2 =Calendar.getInstance();
		
		Date date1 = TimestampDF.parse(val1);
		calendar1.setTime(date1);
		Date date2 = TimestampDF.parse(val2);
		calendar2.setTime(date2);
		
		return calendar1.compareTo(calendar2);
	}
	
	/**
	 * 返回所在月的最后一天或第一天
	 * @param formatStr 所需要的时间格式 (yyyyMMdd, yyyy-MM-dd ,yyyyMM or yyyy ....)
	 * @param timeType所需要时间 （YEAR :1, MONTH:2 ,DAY_OF_MONTH:5,DAY_OF_WEEK:7）
	 * @param num 整数 0为当前日期；负数为前n天、月或前n年；正数为后n天月或后n年
	 * @param dateType 1=所在月的第一天；否则为：所在月的最后一天
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getLastOrFirstDayOfMonth(String formatStr,int timeType,int num,int dateType){
		try{
			SimpleDateFormat bartDateFormat =  new SimpleDateFormat(formatStr);
			Calendar calendar =Calendar.getInstance();
			if(num!=0) calendar.add(timeType, num);
	        int retDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        if(dateType==1){
	        	retDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
	        }
	        calendar.set(calendar.DATE, retDay);
	        return bartDateFormat.format(calendar.getTime());
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * 返回传入日期的最后一天或第一天
	 * @param timeStr 传入时间(格式为：yyyyMM或yyyyMMdd)
	 * @param formatStr 所需要的时间格式 (yyyyMMdd, yyyy-MM-dd ,yyyyMM or yyyy ....)
	 * @param timeType所需要时间 （YEAR :1, MONTH:2 ,DAY_OF_MONTH:5,DAY_OF_WEEK:7）
	 * @param num 整数 0为当前日期；负数为前n天、月或前n年；正数为后n天月或后n年
	 * @param dateType 1=所在月的第一天；否则为：所在月的最后一天
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getLastOrFirstDayOfMonth1(String timeStr,String formatStr,int timeType,int num,int dateType){
		try{
			SimpleDateFormat bartDateFormat =  new SimpleDateFormat(formatStr);
			Calendar calendar = stringDateTimeToCalendar(timeStr);
			if(num!=0) calendar.add(timeType, num);
	        int retDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        if(dateType==1){
	        	retDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
	        }
	        calendar.set(calendar.DATE, retDay);
	        return bartDateFormat.format(calendar.getTime());
		}catch(Exception e){
			return "";
		}
	}
	

	
	/**
	 * 把字符串格式的日期，时间转换成日历类型
	 * @param dateTime	格式:yyyyMMddHHmmssSSS 或者yyyyMMdd 或者HHmmssSSS 或者yyyyMM  或者yyyy
	 * @return 日历
	 * @throws ParseException 
	 */
	public static GregorianCalendar stringDateTimeToCalendar(String dateTime) 
			throws ParseException {
		SimpleDateFormat dateFormat =null;
		if (dateTime.length()==4)//yyyy
			dateFormat =  new SimpleDateFormat("yyyy");
		else if (dateTime.length()==6)//yyyyMM
			dateFormat =  new SimpleDateFormat("yyyyMM");
		else if (dateTime.length()==8)//yyyyMMdd
			dateFormat =  new SimpleDateFormat("yyyyMMdd");
		else if (dateTime.length()==9)//HHmmssSSS
			dateFormat =  new SimpleDateFormat("HHmmssSSS");
		else if (dateTime.length()==17)//yyyyMMddHHmmssSSS
			dateFormat =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
		GregorianCalendar calendar=new GregorianCalendar();
		
		Date date = dateFormat.parse(dateTime);
		calendar.setTime(date);
		return calendar;
	}
	
	/**
	 * 把日历类型转换成字符串格式的日期时间
	 * @param 日历 
	 * @return 格式:yyyyMMddHHmmssSSS
	 */
	public static String calendarToStringDateTime(GregorianCalendar dateTime)
	{
		SimpleDateFormat bartDateFormat =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return  bartDateFormat.format(dateTime.getTime());
	}

	/**
	 * 返回的格式和dateTime的格式一样
	 * @param dateTime 格式:yyyyMMddHHmmssSSS 或者yyyyMMdd 或者yyyyMMddHHmmss等，天以后的都可缺少
	 * @param timeType Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
	 * @param timeType 所需要时间 （YEAR :1, MONTH:2 ,DAY_OF_MONTH:5,DAY_OF_WEEK:7）
	 * @param amount 要加减的值，加为正，减为负
	 * @return 对应部分加减后的字符串,格式对应 传入dateTime的格式  
	 */
	public static String dateTimeAdd(String dateTime,String formatStr, int timeType, int amount)	{
		try{
			SimpleDateFormat bartDateFormat =  new SimpleDateFormat(formatStr);
			Date date = bartDateFormat.parse(dateTime);
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(timeType,amount);
	        return bartDateFormat.format(calendar.getTime());
		}catch(Exception e){
			return formatStr;
		}
	}
	
	/**
	 * 把字段串形的日期去掉分隔符返回
	 * @param date (2005-08-23)
	 * @return  (20050823)
	 */
	public static String stringDateDeleteSign(String date)
	{
		if (date==null || date.trim().length()<10)
			return date;
		String rDate=date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
		return rDate;
	}
	
	/**
	 * 把字段串形的时间加上分隔符返回
	 * @param date (154123456) 或者 (154123)
	 * @return  (15:41:23:456)		 (15-41-23)
	 */
	public static String stringTimeAddSign(String date)
	{
		if (date==null || date.trim().length()<6)
			return date;
		String rDate=date.substring(0,2)+":"+date.substring(2,4)+":"+date.substring(4,6);
		if (date.length()==9)
			rDate = rDate+":"+date.substring(6,9);
		return rDate;
	}
	
	/**
	 * 把字段串形的时间去掉分隔符返回
	 * @param date (15:41:23:456) 或者 (15:41:23)
	 * @return  (154123456)			(154123)
	 */
	public static String stringTimeDeleteSign(String date)
	{
		if (date==null || date.trim().length()<8)
			return date;
		String rDate=date.substring(0,2)+date.substring(3,5)+date.substring(6,8);
		if (date.length()==12)
			rDate =rDate+date.substring(9,12);
		return rDate;
	}
	
	/**
	 * 检查年月的正确性
	 * @param date	格式:yyyyMM
	 * @return 正确为 true, 否则为false
	 */
	public static boolean isYearMonth(String ym)
	{
		boolean result = false;
		if (ym.length()==6)//yyyyMMdd
		{
			try
			{
				int year = Integer.valueOf(ym.substring(0,4)).intValue();
				if (year<1990)
					return false;
				int month = Integer.valueOf(ym.substring(4,6)).intValue();
				if (month>0 && month <=12)//月正确
					result = true;
			}
			catch (Exception e)//用try保存取的是传入的是数值
			{
				
			}
		}
		return result;
	}
	/**
	 * 把字段串形的日期时间去掉分隔符返回
	 * @param date (2005-10-12 15:41:23:456)
	 * @return  (154123456)			(154123)
	 */
	public static String stringDateTimeDeleteSign(String date)
	{
		if (date==null || date.trim().length()<23)
			return date;
		String rDate=date.substring(0,4)+date.substring(5,7)+date.substring(8,10)
					+date.substring(11,13)+date.substring(14,16)+date.substring(17,19)+date.substring(20,23);
		return rDate;
	}
	
	/**
	 * 判断所输入的年是否为润年
	 * @param year 年
	 * @return 是润年 为true,否则为false
	 */
	public static boolean RunNian(int year)//取得是否为润年
	{
		if ((year%400==0) || ((year%4==0) && (year%100!=0)))
			return true;
		else
			return false;
	}

	/**
	 * 日期减法
	 * @param dateStr1	时间1字符串
	 * @param dateStr2	时间2字符串
	 * @param formatStr 时间1及时间2的格式
	 * @return dateStr1－dateStr2的间隔秒数
	 */
	public static BigDecimal dateSubtract(String dateStr1,String dateStr2,String formatStr){
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		try {
		   java.util.Date d1 = df.parse(dateStr1);
		   java.util.Date d2 = df.parse(dateStr2);
		   //1小时=60分钟=3600秒=3600000
		   double mins = 1000L;
		   //long day= 24L * 60L * 60L * 1000L;计算天数之差
		   BigDecimal bf = new BigDecimal((d1.getTime() - d2.getTime()) / mins);
		   return bf.setScale(0, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
		   //Log.error(e.toString());
			return new BigDecimal(0);
		}
	}

	/**
	 * 日期减法
	 * @param dateStr1	时间1字符串
	 * @param dateStr2	时间2字符串
	 * @param formatStr 时间1及时间2的格式
	 * @return dateStr1－dateStr2的间隔天数
	 */
	public static int dateSubtract2(String dateStr1,String dateStr2,String formatStr){
		return Double.valueOf((dateSubtract(dateStr1,dateStr2,formatStr)).doubleValue()/(24* 60 * 60 )).intValue();
	}
	
	/**
	 * 日期减法 
	 * @param dateStr1	时间1字符串，格式为："yyyy-MM-dd HH:mm:ss:SSS"
	 * @param dateStr2	时间2字符串，格式为："yyyy-MM-dd HH:mm:ss:SSS"
	 * @return ?时?分?秒
	 */
	public static String dateSubtract(String dateStr1,String dateStr2){
		double dd = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		try {
		   java.util.Date d1 = df.parse(dateStr1);
		   java.util.Date d2 = df.parse(dateStr2);
		   //1小时=60分钟=3600秒=3600000
		   double mins = 1000L;
		   //long day= 24L * 60L * 60L * 1000L;计算天数之差
		   BigDecimal bf = new BigDecimal((d1.getTime() - d2.getTime()) / mins);
		   dd=bf.doubleValue();
		} catch (Exception e) {
		   //Log.error(e.toString());
		}
		long day= 60L * 60L;
		int hour = (int) (dd/day);
		dd = dd- hour*day;
		day= 60L;
		int minu = (int) (dd/day);
		int secd = (int) (dd-day*minu);
		return hour+"时"+minu+"分"+secd+"秒";
	}
}
