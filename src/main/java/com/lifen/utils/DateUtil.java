package com.lifen.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.StringUtils;


/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
    private static Log                   log                      = LogFactory
                                                                          .getLog(DateUtil.class);
    private static final String          TIME_PATTERN             = "HH:mm";
    public static final String           DATE_PATTERN_YYYY_MM_DD  = "yyyy-MM-dd";
    public static final String           DATE_PATTERN_YYYYMMDD    = "yyyyMMdd";
    public static final String           YYYY_MM_DD_HH_MM_SS      = "yyyy-MM-dd HH:mm:ss";
    public static final String           YYYY_MM_DD_HH            = "yyyy-MM-dd HH";
    public static final String           CHINESE_YYYY_MM_DD_HH_MM = "yyyy年MM月dd日  HH点mm分";
    public static final String           YYYY_MM_DD_HH_00         = "yyyy-MM-dd HH:00";
    public static final String           YYYYMMDDHH               = "yyyyMMddHH";
    public static final String           YYYYMMDDHHMMSS           = "yyyyMMddHHmmss";
    public static final String           YYYYMMDDHHMM             = "yyMdHHmmss";
    public static final String           MMDDHHMMSS               = "MMddHHmmss";
    public static final SimpleDateFormat CHINESE_DATE_FORMAT      = new SimpleDateFormat(
                                                                          CHINESE_YYYY_MM_DD_HH_MM); 
	private static final Map<String, SimpleDateFormat> sdfMap = new HashMap<String, SimpleDateFormat>();

    private static SimpleDateFormat getDateFormat(String pattern) {
		if (!sdfMap.containsKey(pattern)) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			synchronized (sdfMap) {
				sdfMap.put(pattern, format);
			}
		}
		return sdfMap.get(pattern);
	}
    /**
     * 转换字符串为时间类型
     * 
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date convertStringTODate(String dateStr, String pattern) throws ParseException {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.parse(dateStr);
    }

    /**
     * 装换时间类型为字符窜类型
     * 
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String convertDateToString(Date date, String pattern) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        sf.setLenient(false);
        return sf.format(date);
    }

    /**
     * 得到当前的日期时间，默认格式为 yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */

    public static Date getCurrentDateTime() {
        Calendar calNow = Calendar.getInstance();
        Date dtNow = calNow.getTime();
        return dtNow;
    }

    /**
     * 得到当前的日期时间
     * 
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getCurrentDateTime(String pattern) throws ParseException {
        return convertStringTODate(getCurrentDateString(pattern), pattern);
    }

    
    /**
     * 得到当前的日期 默认格式为 yyyy-MM-dd
     * 
     * @return
     * @throws ParseException
     */
    public static String getCurrentDateString() throws ParseException {
        return getCurrentDateString(DATE_PATTERN_YYYY_MM_DD);
    }

    /**
     * 得到当前的日期 默认格式为 yyyy-MM-dd
     * 
     * @return
     * @throws ParseException
     */
    public static String getCurrentDateString(String pattern) throws ParseException {
        return convertDateToString(getCurrentDateTime(), pattern);
    }

    /**
     * 得到后一天
     * 
     * @return
     * @throws ParseException
     */

    public static Date getAfterDate(String date) throws ParseException {
        return getAfterDate(convertStringTODate(date, DateUtil.DATE_PATTERN_YYYY_MM_DD));
    }

    /**
     * 得到前一天
     * 
     * @return
     * @throws ParseException
     */

    public static Date getBeforeDate(String date) throws ParseException {
        return getBeforeDate(convertStringTODate(date, DateUtil.DATE_PATTERN_YYYY_MM_DD));
    }

    public static Date getBeforeDate(Date date) throws ParseException {
        return new Date(date.getTime() - 1000 * 3600 * 24);
    }

    public static Date getAfterDate(Date date) {
        return new Date(date.getTime() + 1000 * 3600 * 24);
    }

    public static Date addDate(Date date, int interval) {
        return new Date(date.getTime() + ((1000 * 3600 * 24) * interval));
    }

    /**
     * 加减分钟
     * 
     * @param startTime
     * @param interval
     * @return
     */
    public static Date addMinutes(Date startTime, int interval) {
        if (startTime == null) {
            return null;
        }
        long ms = startTime.getTime() + interval * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        return calendar.getTime();
    }

    /**
     * 得到下个时间整点
     * 
     * @param time
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getNextHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(calendar.HOUR_OF_DAY, +1);
        return calendar.getTime();
    }

    /**
     * 得到下个时间整点
     * 
     * @param time
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getPreviousHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

    /**
     * 得到下个时间半点
     * 
     * @param time
     * @return
     */
    public static Date getNextHalfHour(Date time) {
        Date result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int minute = calendar.get(Calendar.MINUTE);
        if (minute >= 30) {
            result = getNextHour(time);
        } else {
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            result = calendar.getTime();
        }
        return result;
    }

    public static String getCurrentTimeChineseStr() {

        return CHINESE_DATE_FORMAT.format(getCurrentDateTime());
    }

    /**
     * 校验日期格式
     * 
     * @param strDate
     * @return
     */
    public static boolean isDateFormat(String strDate) {
        String eL = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(strDate);
        boolean b = m.matches();

        return b;
    }

    /**
     * 获得两个日期之前相差的月份<br>
     * 
     * @param start
     * @param e
     * @return
     */
    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {

            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    /**
     * 获得两个日期之前相差的天数<br>
     * 
     * @param start
     * @param e
     * @return
     */
    public static long differ(Date fromDate, Date toDate) {
        //return date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000);
        return toDate.getTime() / 86400000 - fromDate.getTime() / 86400000; //用立即数，减少乘法计算的开销
    }

    /**
     * 获取小时
     * 
     * @param date Date
     * @return int
     */
    public static int getHourNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     * 
     * @param date Date
     * @return int
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    public DateUtil() {
    }

    //Timestamp和String之间转换的函数：
    public static String getTimestampToString(Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(obj);
        return str;
    }

    /*
     * 自定义 转换模式将Timestamp 输出
     */
    public static String getTimestampToString(String formatPattern, Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat(formatPattern);
        String str = df.format(obj);
        return str;
    }

    //String转化为Timestamp:
    public static Timestamp getStringToTimestamp(String str) {
        Timestamp ts = Timestamp.valueOf(str);
        return ts;
    }

    public static Date strToDate(String str, String pattern) {
        Date dateTemp = null;
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        try {
            dateTemp = formater2.parse(str);
        } catch (Exception e) {
            log.error("exception in convert string to date!");
        }
        return dateTemp;
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     * 
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle("ApplicationResources", locale).getString(
                    "date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }


    public static String getDate(Date aDate) {
		String returnValue = "";
		if (aDate != null) {
			returnValue = getDateFormat(DATE_PATTERN_YYYY_MM_DD).format(aDate);
		}
		return returnValue;
	}
    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see SimpleDateFormat
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     * 
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     * 
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    public static Calendar getCurrentDay() throws ParseException {
        Calendar cal = Calendar.getInstance();
        return cal;

    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     * 
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            log.error(pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     * @param aDate
     * @return
     */
    public static String convertDateToString(String pattern, Date aDate) {
        return getDateTime(pattern, aDate);
    }

    /**
     * 取得从startDate开始的前(正)/后(负)day天
     * 
     * @param startDate
     * @param day
     * @return
     */
    public static Date getRelativeDate(Date startDate, int day) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            log.error(e);
            return startDate;
        }
    }

    /**
     * 请注意这个方法,它增加或者减少的日期是相对new Date()来说的，而不是相对startDate;为了避免风险，这里不做修改
     * 
     * @param startdate
     * @param days
     * @return
     */
    @Deprecated
    public static Date getDate(Date startdate, int days) {
        Date dateresult = startdate;
        try {
            GregorianCalendar cal = new GregorianCalendar();

            cal.setTime(new Date());
            cal.add(GregorianCalendar.DAY_OF_MONTH, days);
            dateresult = cal.getTime();
        } catch (Exception e) {
            log.error(e);
        }
        return dateresult;
    }

    /**
     * 根据日期获取星期几
     * 
     * @param date java.util.Date对象,不能为null
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
     * 
     * @param begin 开始时间
     * @param end
     * @return
     */
    public static int countDays(String beginStr, String endStr, String Foramt) {
        Date end = strToDate(endStr, Foramt);
        Date begin = strToDate(beginStr, Foramt);
        long times = end.getTime() - begin.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }

    /**
     * 获取年份
     * 
     * @param date Date
     * @return int
     */
    public static final int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取年份
     * 
     * @param millis long
     * @return int
     */
    public static final int getYear(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * 
     * @param date Date
     * @return int
     */
    public static final int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取月份
     * 
     * @param millis long
     * @return int
     */
    public static final int getMonth(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期
     * 
     * @param millis long
     * @return int
     */
    public static final int getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取小时
     * 
     * @param date Date
     * @return int
     */
    public static final int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取小时
     * 
     * @param millis long
     * @return int
     */
    public static final int getHour(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得某年的某月有多少天
     * 
     * @return
     */
    public static final int getMonthNumberByYear() {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, getYear(new Date()));
        time.set(Calendar.MONTH, getMonth(new Date()) - 1);//Calendar对象默认一月为0 
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * 当月的最后一天
     * 
     * @param sDate1
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(Date sDate) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(sDate);
        final int lastDay = cDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay.getTime();
        lastDate.setDate(lastDay);
        //System.out.print(StringUtil.equals(String.valueOf(DateUtil.getDate(lastDate)), String.valueOf(DateUtil.getDate(new Date()))));
        return lastDate;
    }

    public static void main(String args[]) {
        String s = "12312231321316546464613215674879";
        if (s.length() % 6 == 0) {
            for (int i = 0; i < s.length() / 6; i++)
                System.out.println(s.substring(i * 6, i * 6 + 6));
        } else {
            for (int i = 0; i < s.length() / 6; i++)
                System.out.println(s.substring(i * 6, i * 6 + 6));
            System.out.println(s.substring(s.length() / 6 * 6));
        }

    }

    public static String[] splitStringBySix(String str) {
        if (null == str || "".equals(str)) {
            return null;
        }
        // 计算存放数据的数组的长度
        int arrLength = (str.length() % 6 == 0) ? str.length() / 6 : str.length() / 6 + 1;
        String[] arr = new String[arrLength];

        for (int i = 0; i < arr.length; i++) {
            // 主要是处理，最后的几位不够6位的情况
            if (i == arr.length - 1) {
                arr[i] = str.substring(i * 6);
            }
            arr[i] = str.substring(i * 6, (i + 1) * 6);
        }
        return arr;
    }
    
    /**
     * 判断传入的时间字符串是不是合法的时间(必须是yyyyMMdd)
     * @param date
     * @return
     */
    public static boolean isDate(String date)
    {
      /**
       * 判断日期格式和范围
       */      
      String rexp1="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29)"; 
      Pattern pat = Pattern.compile(rexp1);
      Matcher mat = pat.matcher(date);
      boolean dateType = mat.matches();
      return dateType;
    }
   
}
