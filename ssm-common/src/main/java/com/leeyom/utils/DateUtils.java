package com.leeyom.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理工具类
 *
 * @author leeyom
 * @ClassName: DateUtil
 * @Description: TODO
 * @company: leaderment.com
 * @date: 2017年4月6日 上午10:13:34
 */
public class DateUtils {
    /**
     * 将日期转化为指定格式的字符串
     *
     * @param date    Date 时间对象
     * @param pattern String 时间格式，可以是年月日时分秒的任意有效组合，如yyyy-MM-dd HH:mm 年：yyyy 月：MM
     *                日：dd 时：HH�?24小时制）或hh�?12小时制） 分：mm 秒：ss
     * @return String 日期字符�?
     */
    public static String getString(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 将日期转化为默认的yyyy-MM-dd格式的字符串
     *
     * @param date Date 时间对象
     * @return String 日期字符�?
     */
    public static String getString(Date date) {
        return getString(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 将日期字符串转换为对应格式的日期对象
     *
     * @param strDate String 日期字符�?,�?2007-02-14 14:25
     * @param format  String 日期格式，与日期字符串的格式对应，如yyyy-MM-dd HH:mm 年：yyyy 月：MM 日：dd
     *                时：HH�?24小时制）或hh�?12小时制） 分：mm 秒：ss
     * @return Date 日期对象
     */
    public static Date getDate(String strDate, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {

            throw new IllegalArgumentException("Can't parse " + strDate + " using " + format);
        }

    }

    /**
     * 将格式为yyyy-MM-dd的日期字符串转换为日期对�?
     *
     * @param strDate String 日期字符�?,�?2007-02-14
     * @return Date 日期对象
     */
    public static Date getDate(String strDate) {
        return getDate(strDate, "yyyy-MM-dd");
    }

    /**
     * 获得两个日期对象之间相差的天�?
     *
     * @param beginDater Date �?始日�?
     * @param endDate    Date 结束日期
     * @return long 相差天数，如果beginDate>endDate，返回负�?
     */
    public static long getDays(Date beginDate, Date endDate) {
        long days = -1;
        long beginMillisecond = beginDate.getTime();
        long endMillisecond = endDate.getTime();
        long millisecondForDay = 24 * 60 * 60 * 1000;
        days = (long) ((endMillisecond - beginMillisecond) / millisecondForDay);
        return days;
    }

    /**
     * 获得两个日期对象之间相差的小时数�?
     *
     * @param beginDater Date �?始日�?
     * @param endDate    Date 结束日期
     * @return long 相差小时数目，如果beginDate>endDate，返回负�?
     */
    public static long getTimes(Date beginDate, Date endDate) {
        long time = -1;
        long beginMillisecond = beginDate.getTime();
        long endMillisecond = endDate.getTime();
        long millisecondForDay = 60 * 60 * 1000;
        time = (long) ((endMillisecond - beginMillisecond) / millisecondForDay);
        return time;
    }

    /**
     * 在指定的日期加上或减去天�?
     *
     * @param date Date 日期
     * @param day  int 天数(正数-�?;负数-�?)
     * @return Date 修改后的日期
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static String getAMOrPM(Date date) {
        String str = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = calendar.get(Calendar.AM_PM);
        if (num == 0) {
            str = "上午";
        } else if (num == 1) {
            str = "下午";
        }
        return str;
    }

    /**
     * 获取制定时间的星期数
     *
     * @param date
     * @return String 例如：星期一
     */
    public static String getStrWeek(Date date) {
        String strWeek = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekNum == 1)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 2)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 3)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 4)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 5)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 6)// 星期�?
        {
            strWeek = "星期�?";
        } else if (weekNum == 7)// 星期�?
        {
            strWeek = "星期�?";
        }
        return strWeek;
    }

    /**
     * 获取制定时间的星期数
     *
     * @param date
     * @return String 例如�?1
     */
    public static int getIntWeek(Date date) {
        int intWeek = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekNum == 1)// 星期�?
        {
            intWeek = 7;
        } else if (weekNum == 2)// 星期�?
        {
            intWeek = 1;
        } else if (weekNum == 3)// 星期�?
        {
            intWeek = 2;
        } else if (weekNum == 4)// 星期�?
        {
            intWeek = 3;
        } else if (weekNum == 5)// 星期�?
        {
            intWeek = 4;
        } else if (weekNum == 6)// 星期�?
        {
            intWeek = 5;
        } else if (weekNum == 7)// 星期�?
        {
            intWeek = 6;
        }
        return intWeek;
    }

    /**
     * 将日期转换为中文大些格式
     *
     * @param d
     * @return
     */
    public static String getChineseDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "�?" + monthToUppder(month) + "�?" + dayToUppder(day) + "�?";
    }

    // 将数字转化为大写
    public static String numToUpper(int num) {
        // String u[] = {"�?","�?","�?","�?","�?","�?","�?","�?","�?","�?"};
        // String u[] = { "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?",
        // "�?" };
        String u[] = {"�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "�?";
        } else {
            return "�?" + numToUpper(month - 10);
        }
    }

    // 日转化为大写
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "�?";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "�?" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    /**
     * GMT date to local date
     *
     * @param date
     * @return
     */
    public static Date GMTDatetoLocalDate(Date date) {
        return GMTDatetoLocalDate(date, null);
    }

    public static Date GMTDatetoLocalDate(Date date, Integer offset) {
        if (offset == null) {
            offset = TimeZone.getDefault().getRawOffset();
        }
        return new Date(date.getTime() + offset);
    }

    /**
     * GMT date to local date
     *
     * @param date
     * @return
     */
    public static Date GMTDatetoLocalDate(Long date) {
        return GMTDatetoLocalDate(date, null);
    }

    public static Date GMTDatetoLocalDate(Long date, Integer offset) {
        if (offset == null) {
            offset = TimeZone.getDefault().getRawOffset();
        }
        return new Date(date.longValue() + offset);
    }

    /**
     * Local date to GMT date
     */
    public static Date localDateToGMTDate(Date date) {
        return localDateToGMTDate(date, null);
    }

    public static Date localDateToGMTDate(Date date, Integer offset) {
        if (offset == null) {
            offset = TimeZone.getDefault().getRawOffset();
        }
        return new Date(date.getTime() - offset);
    }

    public static Date localDateToGMTDate(Long date) {
        return localDateToGMTDate(date, null);
    }

    public static Date localDateToGMTDate(Long date, Integer offset) {
        if (offset == null) {
            offset = TimeZone.getDefault().getRawOffset();
        }
        return new Date(date.longValue() - offset);
    }

    /**
     * 获取两个时间串时间的差�?�，单位为秒
     *
     * @param startTime �?始时�? yyyy-MM-dd HH:mm:ss
     * @param endTime   结束时间 yyyy-MM-dd HH:mm:ss
     * @return 两个时间的差�?(�?)
     */
    public static long getDiff(String startTime, String endTime) {
        long diff = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = ft.parse(startTime);
            Date endDate = ft.parse(endTime);
            diff = startDate.getTime() - endDate.getTime();
            diff = diff / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    /**
     * 获取两个时间串时间的差�?�，单位为分�?
     *
     * @param startTime �?始时�? yyyy-MM-dd HH:mm:ss
     * @param endTime   结束时间 yyyy-MM-dd HH:mm:ss
     * @return 两个时间的差�?(�?)
     */
    public static int getDiffMinute(String startTime, String endTime) {
        long diff = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = ft.parse(startTime);
            Date endDate = ft.parse(endTime);
            diff = startDate.getTime() - endDate.getTime();
            diff = diff / (1000 * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Long(diff).intValue();
    }

    /**
     * 取得当前日期的年份，以yyyy格式返回.
     *
     * @return 当年 yyyy
     */
    public static String getCurrentYear() {
        return getFormatCurrentTime("yyyy");
    }

    /**
     * 取得当前日期的年份，以yyyy格式返回.
     *
     * @return 当年 yyyy
     */
    public static String getCurrentYearYY() {
        return getFormatCurrentTime("yy");
    }

    /**
     * 取得指定日期的年份，以yyyy格式返回.
     *
     * @return 当前天年�? yyyy
     */
    public static int getCurrentDayYY(Date date) {
        return new Integer(getFormatDateTime(date, "yyyy")).intValue();
    }

    /**
     * 取得当前日期的月份，以MM格式返回.
     *
     * @return 当前月份 MM
     */
    public static String getCurrentMonth() {
        return getFormatCurrentTime("MM");
    }

    /**
     * 取得指定日期的月份，以MM格式返回.
     *
     * @return 当前天年�? MM
     */
    public static int getCurrentDayMM(Date date) {
        return new Integer(getFormatDateTime(date, "MM")).intValue();
    }

    /**
     * 取得当前日期的天数，以dd格式返回.
     *
     * @return 当前天数 dd
     */
    public static int getCurrentDay() {
        return new Integer(getFormatCurrentTime("dd")).intValue();
    }

    /**
     * 取得指定日期的天数，以dd格式返回.
     *
     * @return 当前天数 dd
     */
    public static int getCurrentDayDD(Date date) {
        return new Integer(getFormatDateTime(date, "dd")).intValue();
    }

    /**
     * 根据给定的格式，返回时间字符串�??
     * <p>
     * 格式参照类描绘中说明.和方法getFormatDate是一样的�?
     *
     * @param format 日期格式字符�?
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatCurrentTime(String format) {
        return getFormatDateTime(new Date(), format);
    }

    /**
     * 根据给定的格式与时间(Date类型�?)，返回时间字符串。最为�?�用�?<br>
     *
     * @param date   指定的日�?
     * @param format 日期格式字符�?
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 当前月份有多少天
     */
    public static int getDaysOfCurMonth() {
        int curyear = new Integer(getCurrentYear()).intValue(); // 当前年份
        int curMonth = new Integer(getCurrentMonth()).intValue();// 当前月份
        int mArray[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // 判断闰年的情�? �?2月份�?29天；
        if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
            mArray[1] = 29;
        }
        return mArray[curMonth - 1];
        // 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界�?
        // 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界�?
    }

    /**
     * 指定月份有多少天
     *
     * @return
     */
    public static int getDaysOfCurMonth(int yue) {
        int curyear = new Integer(getCurrentYear()).intValue(); // 当前年份
        int curMonth = yue;// 指定月份
        int mArray[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // 判断闰年的情�? �?2月份�?29天；
        if ((curyear % 400 == 0) || ((curyear % 100 != 0) && (curyear % 4 == 0))) {
            mArray[1] = 29;
        }
        return mArray[curMonth - 1];
        // 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界�?
        // 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界�?
    }

    /**
     * 自动返回上一年�?�例如当前年份是2007年，那么就自动返�?2006
     *
     * @return 返回结果的格式为 yyyy
     */
    public static String getBeforeYear() {
        String currentYear = getFormatCurrentTime("yyyy");
        int beforeYear = Integer.parseInt(currentYear) - 1;
        return "" + beforeYear;
    }

    /**
     * 自动返回下一年�?�例如当前年份是2007年，那么就自动返�?2008
     *
     * @return 返回结果的格式为 yyyy
     */
    public static String getLastYear() {
        String currentYear = getFormatCurrentTime("yyyy");
        int lastYear = Integer.parseInt(currentYear) + 1;
        return "" + lastYear;
    }

    /**
     * 获得当前日期的下�?个星期一
     */
    public static Date getNextMonday() {
        Date date = new Date();
        int w = getIntWeek(date);
        if (w == 1) {
            return addDays(date, 7);
        }
        if (w == 2) {
            return addDays(date, 6);
        }
        if (w == 3) {
            return addDays(date, 5);
        }
        if (w == 4) {
            return addDays(date, 4);
        }
        if (w == 5) {
            return addDays(date, 3);
        }
        if (w == 6) {
            return addDays(date, 2);
        }
        return null;
    }

    public static boolean getWeekDay(Date date) {
        if (getStrWeek(date).equals("星期�?") || getStrWeek(date).equals("星期�?")) {
            return true;
        }
        return false;
    }

    /**
     * description: 获取某段时间内的所有日期
     * author: leeyom
     * company: leaderment.com
     * date: 2017-06-14 04:09
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
//        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后  
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    public static void main(String[] args) {
        // System.out.println(addDays(new Date(), -1));
        System.out.println(getNextMonday());
    }
}
