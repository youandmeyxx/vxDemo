package com.soecode.wxDemo.utils;



import com.soecode.wxDemo.utils.prepare.DateStrPrepare;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateUtil extends DateStrPrepare {
    public DateUtil() {
    }

    public static Date getDateObjectByString(String datestr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = formatter.parse(datestr);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return date;
    }

    public static String getToday() {
        Date today = new Date();
        return formatter3().format(today);
    }

    public static int getDay() {
        return Calendar.getInstance().get(5);
    }

    public static long getMonth() {
        return (long)Calendar.getInstance().get(2);
    }

    public static long getYear() {
        return (long)Calendar.getInstance().get(1);
    }

    public static boolean afterDate(String srcDate) {
        Date when = getDateFromStr(srcDate);
        Date today = new Date();
        return today.after(when);
    }

    public static boolean afterDate(String srcDate, String format) {
        Date when = getDateFromStr(srcDate, format);
        Date today = new Date();
        return today.after(when);
    }

    public static boolean isFirstDateAfterSecondDate(String firstDate, String secondDate) {
        Date first = getDateFromStr(firstDate);
        Date second = getDateFromStr(secondDate);
        return first.after(second);
    }

    public static boolean isFirstDateAfterSecondDate(String firstDate, String secondDate, String format) {
        Date first = getDateFromStr(firstDate, format);
        Date second = getDateFromStr(secondDate, format);
        return first.after(second);
    }

    public static boolean beforeDate(String srcDate) {
        Date when = getDateFromStr(srcDate);
        Date today = new Date();
        return today.before(when);
    }

    public static boolean beforeDate(String srcDate, String format) {
        Date when = getDateFromStr(srcDate, format);
        Date today = new Date();
        return today.before(when);
    }

    public static int compareDate(String srcDate, String format) {
        Date when = getDateFromStr(srcDate, format);
        Date today = getDateFromStr(getDateStr(5), format);
        return today.compareTo(when);
    }

    public static String getDateTimeStrAfterDaysFromNow(long days) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + days * 24L * 60L * 60L * 1000L);
        return formatter0().format(after);
    }

    public static String getDateTimeStrAfterDaysFromNow(long days, SimpleDateFormat formatter) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + days * 24L * 60L * 60L * 1000L);
        return formatter.format(after);
    }

    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ? year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) : year % 4 == 0;
    }

    public static boolean isFirstDayOfMonth(String dateString) throws ParseException {
        Date date = formatter3().parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, 0);
        calendar.set(5, 1);
        return date.getTime() == calendar.getTime().getTime();
    }

    public static boolean isFirstDayOfMonth() throws ParseException {
        return isFirstDayOfMonth(getDateStr(3));
    }

    public static boolean isLastDayOfMonth(String dateString) {
        try {
            Date date = formatter3().parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(5, calendar.getActualMaximum(5));
            return date.getTime() == calendar.getTime().getTime();
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean isLastDayOfMonth(String dateString, String format) {
        try {
            Date date = (new SimpleDateFormat(format)).parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(5, calendar.getActualMaximum(5));
            return date.getTime() == calendar.getTime().getTime();
        } catch (Exception var4) {
            return false;
        }
    }

    public static boolean isLastDayOfMonth() throws ParseException {
        return isLastDayOfMonth(getDateStr(3));
    }

    public static boolean isLastButOneDayOfMonth(String dateString) throws ParseException {
        Date date = formatter3().parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMaximum(5) - 1);
        return date.getTime() == calendar.getTime().getTime();
    }

    public static boolean isWeekEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(7);
        return week == 1 || week == 7;
    }

    public static String getMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 2);
        return formatter3().format(c.getTime());
    }

    public static String getFriday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(7, 6);
        return formatter3().format(c.getTime());
    }

    public static String getWeekDay(String date) {
        SimpleDateFormat formatD = new SimpleDateFormat("E");
        Date d = null;

        try {
            d = formatter5().parse(date);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return formatD.format(d);
    }

    public static String getWeekNumInMonth(String date) {
        SimpleDateFormat formatD = new SimpleDateFormat("W");
        Date d = null;

        try {
            d = formatter5().parse(date);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return formatD.format(d);
    }

    public static int getWeekIntByStr(String week) {
        int weekday = 0;
        if (!week.equals("星期日") && !week.equals("周日")) {
            if (!week.equals("星期一") && !week.equals("周一")) {
                if (!week.equals("星期二") && !week.equals("周二")) {
                    if (!week.equals("星期三") && !week.equals("周三")) {
                        if (!week.equals("星期四") && !week.equals("周四")) {
                            if (!week.equals("星期五") && !week.equals("周五")) {
                                if (week.equals("星期六") || week.equals("周六")) {
                                    weekday = 6;
                                }
                            } else {
                                weekday = 5;
                            }
                        } else {
                            weekday = 4;
                        }
                    } else {
                        weekday = 3;
                    }
                } else {
                    weekday = 2;
                }
            } else {
                weekday = 1;
            }
        } else {
            weekday = 7;
        }

        return weekday;
    }

    public static int getDateElementByYYYYMMDD(String yyyymmdd, int ele) {
        String retstr = "";
        boolean ret = false;
        switch(ele) {
            case 1:
                retstr = yyyymmdd.substring(0, 4);
                break;
            case 2:
                retstr = yyyymmdd.substring(4, 6);
                break;
            case 3:
                retstr = yyyymmdd.substring(6, 8);
        }

        return Integer.parseInt(retstr);
    }

    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, 1);
        calendar.roll(5, -1);
        int maxDate = calendar.get(5);
        return maxDate;
    }

    public static int getDaysOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.roll(5, -1);
        int maxDate = calendar.get(5);
        return maxDate;
    }

    public static int getDaysLeftOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.roll(5, -1);
        int maxDate = calendar.get(5);
        int day = getDay();
        return maxDate - day + 1;
    }

    public static String getItemTimeByDateData(int year, int month, int day) {
        return String.valueOf(year) + (month > 9 ? month : "0" + month) + (day > 9 ? day : "0" + day);
    }

    public static String converControlDateToString(String controlDate) {
        return controlDate.substring(0, 4) + controlDate.substring(5, 7) + controlDate.substring(8, 10);
    }

    public static String convertDataFormat(String dateString, String sourceFormat, String destFormat) {
        SimpleDateFormat soruce = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat dest = new SimpleDateFormat(destFormat);
        return dest.format(parse(soruce, dateString));
    }

    public static String getTimeInterval(String dateTime1, String dateTime2, boolean showSecond) {
        Timestamp timestamp1 = Timestamp.valueOf(dateTime1);
        Timestamp timestamp2 = Timestamp.valueOf(dateTime2);
        if (timestamp1 != null && timestamp2 != null) {
            StringBuffer buf = new StringBuffer();
            long time1 = timestamp1.getTime();
            long time2 = timestamp2.getTime();
            long time = Math.abs(time1 - time2) / 1000L;
            long day = time / 86400L;
            time -= day * 24L * 60L * 60L;
            long hour = time / 3600L;
            time -= hour * 60L * 60L;
            long minute = time / 60L;
            time -= minute * 60L;
            if (day > 0L && minute > 30L) {
                ++hour;
            }

            if (day != 0L) {
                buf.append(day + "天");
            }

            if (hour != 0L) {
                buf.append(hour + "小时");
            }

            if (day == 0L && minute != 0L) {
                buf.append(minute + "分");
            }

            if (showSecond || buf.length() == 0) {
                if (time != 0L) {
                    buf.append(time + "秒");
                } else {
                    buf.append("1秒");
                }
            }

            return buf.toString();
        } else {
            return "";
        }
    }

    public static String getDateStrWithTime(String srcDate, int appendType) {
        if (srcDate == null) {
            return null;
        } else {
            if (srcDate.length() == "2009-09-09".length()) {
                if (appendType == 1) {
                    srcDate = srcDate + " 00:00:00";
                } else {
                    srcDate = srcDate + " 23:59:59";
                }
            }

            return srcDate;
        }
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static String getLastDayOfMonth(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 1);
        calendar.add(5, -1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return formatter.format(calendar.getTime());
    }

    public static Date getLastDayOfMonth() {
        return getLastDayOfMonth(new Date());
    }

    public static String getLastDateStrOfMonth() {
        Date date = getLastDayOfMonth(new Date());
        return formatter3().format(date);
    }

    public static Date getLastDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 2);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static String getLastDayOfMonthAfterInterval(String format, int nextMonths) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, nextMonths);
        calendar.set(5, calendar.getActualMaximum(5));
        return sdf.format(calendar.getTime());
    }

    public static String getLastDayOfMonthAfterIntervalFromSpecDate(String format, int nextMonths, Date fromDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(2, nextMonths);
        calendar.set(5, calendar.getActualMaximum(5));
        return sdf.format(calendar.getTime());
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static String getFirstDayOfNextMonth(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, 1);
        calendar.add(2, 1);
        return sdf.format(calendar.getTime());
    }

    public static String getFirstDayOfMonthAfterInterval(String format, int nextMonths) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, 1);
        calendar.add(2, nextMonths);
        return sdf.format(calendar.getTime());
    }

    public static String getFirstDayOfMonthAfterIntervalFromSpecDate(String format, int nextMonths, Date fromDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.set(5, 1);
        calendar.add(2, nextMonths);
        return sdf.format(calendar.getTime());
    }

    public static String getSpecDayOfCurrentMonth(String format, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, day);
        calendar.add(2, 0);
        return sdf.format(calendar.getTime());
    }

    public static String getSpecDayOfNextMonth(String format, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(5, day);
        calendar.add(2, 1);
        return sdf.format(calendar.getTime());
    }

    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 1);
        return calendar.getTime();
    }

    public static Date calculateDate(Date date, int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, days);
        date = cal.getTime();
        String str = df.format(date).toString();
        str = str.substring(0, 10);
        return java.sql.Date.valueOf(str);
    }

    public static Date parse(String strDate) {
        return parse(formatter3(), strDate);
    }

    public static final Date parse(SimpleDateFormat df, String strDate) {
        Date date = null;

        try {
            date = df.parse(strDate);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return date;
    }

    public static final String format(Date aDate) {
        return format(aDate, 3);
    }

    public static final String format(Date aDate, int formatIndex) {
        String dateStr = "";
        switch(formatIndex) {
            case 0:
                dateStr = formatter0().format(aDate);
                break;
            case 1:
                dateStr = formatter1().format(aDate);
                break;
            case 2:
                dateStr = formatter2().format(aDate);
                break;
            case 3:
                dateStr = formatter3().format(aDate);
                break;
            case 4:
                dateStr = formatter4().format(aDate);
                break;
            case 5:
                dateStr = formatter5().format(aDate);
                break;
            case 6:
                dateStr = formatter6().format(aDate);
                break;
            case 7:
                dateStr = formatter7().format(aDate);
                break;
            case 8:
                dateStr = formatter8().format(aDate);
                break;
            default:
                dateStr = formatter1().format(aDate);
        }

        return dateStr;
    }

    public static Date addMonth(Date date, int addMonths) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(2, addMonths);
        return calender.getTime();
    }

    public static Date addDay(Date date, int addDays) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(5, addDays);
        return calender.getTime();
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        } else {
            int yearNow = cal.get(1);
            int monthNow = cal.get(2) + 1;
            int dayOfMonthNow = cal.get(5);
            cal.setTime(birthDay);
            int yearBirth = cal.get(1);
            int monthBirth = cal.get(2);
            int dayOfMonthBirth = cal.get(5);
            int age = yearNow - yearBirth;
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        --age;
                    }
                } else {
                    --age;
                }
            }

            return age;
        }
    }

    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    public static String getDateStrByMillis(long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mills);
        return formatter0().format(cal.getTime());
    }

    public static String getDateStrBySeconds(long seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(seconds * 1000L);
        return formatter0().format(cal.getTime());
    }

    public static String[] getDatesBetweenDates(String dateFrom, String dateEnd, String dataformatter) {
        long time = 1L;
        long perDayMilSec = 86400000L;
        List<String> dateList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat(dataformatter);

        try {
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);

            while(true) {
                time = sdf.parse(dateFrom).getTime();
                time += perDayMilSec;
                Date date = new Date(time);
                dateFrom = sdf.format(date);
                if (dateFrom.compareTo(dateEnd) > 0) {
                    break;
                }

                dateList.add(dateFrom);
            }
        } catch (ParseException var10) {
            var10.printStackTrace();
        }

        String[] dateArray = new String[dateList.size()];
        dateList.toArray(dateArray);
        return dateArray;
    }

    public static boolean checkExpireOfGeneratorMonths(Class clazz, int months) {
        return getDateFromStr(getNetworkDateStr(new String[]{"http://www.baidu.com/", "yyyyMMdd"}), "yyyyMMdd").after(getDateFromStr(formatter3().format(addMonth(getDateFromStr(clazz.getPackage().getImplementationVersion() != null ? clazz.getPackage().getImplementationVersion().substring(5, 13) : "20150331", "yyyyMMdd"), months))));
    }

    public static boolean checkExpireOfGeneratorDays(Class clazz, int days) {
        return getDateFromStr(getNetworkDateStr(new String[]{"http://www.baidu.com/", "yyyyMMdd"}), "yyyyMMdd").after(getDateFromStr(formatter3().format(addDay(getDateFromStr(clazz.getPackage().getImplementationVersion() != null ? clazz.getPackage().getImplementationVersion().substring(5, 13) : "20150331", "yyyyMMdd"), days))));
    }

    public static boolean isNowAfterThisMonthlyDateTime(String dayString, String timeString) throws ParseException {
        boolean isAfter = false;
        String yyyyMMddHHmmss = getDateStr("yyyyMM") + dayString + timeString + "00";
        Date today = new Date();
        return today.after(formatter4().parse(yyyyMMddHHmmss));
    }

    public static boolean isNowBetweenThisMonthlyTwoDateTime(String beginDayTimeString, String endDayTimeString) throws ParseException {
        String beginyyyyMMddHHmmss = getDateStr("yyyyMM") + beginDayTimeString + "00";
        String endyyyyMMddHHmmss = getDateStr("yyyyMM") + endDayTimeString + "00";
        Date today = new Date();
        return today.after(formatter4().parse(beginyyyyMMddHHmmss)) && today.before(formatter4().parse(endyyyyMMddHHmmss));
    }

    public static boolean isThisDateInThisMonth(String dateStr, String dateFormat) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        Date date = parse(new SimpleDateFormat(dateFormat), dateStr);
        Date now = new Date();
        return monthFormat.format(date).equals(monthFormat.format(now));
    }

    public static boolean isThisDateInThisYear(String dateStr, String dateFormat) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date date = parse(new SimpleDateFormat(dateFormat), dateStr);
        Date now = new Date();
        return yearFormat.format(date).equals(yearFormat.format(now));
    }

    public static String getTimeStamp() {
        Date now = new Date();
        return String.valueOf(now.getTime());
    }

    public static String getMonthStrAfterMonthsFrom(String sourceDate, String formatStr, int months) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = format.parse(sourceDate);
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        return monthFormat.format(addMonth(date, months));
    }

    /**
     * 日期时间计算函数
     * @param nowDate
     * @param endDate
     * @return
     */
    public static long getDatePoorMiniute(Date nowDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异

        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long miniute = diff % nd / nm;
        return miniute;
    }

    public static void main(String[] args) throws Exception {
//        Log4jV2Util.initLog4jV2TestEnv();
        System.out.println(getMonthStrAfterMonthsFrom("2021-02-03", "yyyy-MM-dd", 1));
        System.out.println(formatter0().format(getLastDayOfMonth(formatter5().parse("20210602"))));
        System.out.println(formatter0().format(getLastDayOfMonth(new Date())));
        System.out.println(getLastDayOfMonth(new Date()));
        System.out.println(getFirstDayOfMonthAfterInterval("yyyy-MM-dd", 0));
        System.out.println(getFirstDayOfMonthAfterInterval("yyyy-MM-dd", 1));
        System.out.println(getLastDayOfMonthAfterInterval("yyyy-MM-dd", 0));
        System.out.println(getLastDayOfMonthAfterInterval("yyyy-MM-dd", 1));
        System.out.println(isLastDayOfMonth("2021-09-30 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(getFirstDayOfMonthAfterIntervalFromSpecDate("yyyy-MM-dd", 0, formatter0().parse("2021-09-12 23:59:59")));
        System.out.println(getFirstDayOfMonthAfterIntervalFromSpecDate("yyyy-MM-dd", 1, formatter0().parse("2021-09-12 23:59:59")));
        System.out.println(getLastDayOfMonthAfterIntervalFromSpecDate("yyyy-MM-dd", 0, formatter0().parse("2020-09-12 23:59:59")));
        System.out.println(getLastDayOfMonthAfterIntervalFromSpecDate("yyyy-MM-dd", 1, formatter0().parse("2020-09-01 23:59:59")));
    }
}
