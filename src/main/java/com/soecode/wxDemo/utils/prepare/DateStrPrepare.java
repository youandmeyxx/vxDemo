package com.soecode.wxDemo.utils.prepare;



import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateStrPrepare extends DatePrepare {
    public DateStrPrepare() {
    }

    public static String getDateStr() {
        return getDateStr(4);
    }

    public static String getDateStrFull() {
        Date now = new Date();
        return formatterFull().format(now);
    }

    public static String getDateStr(int format) {
        Date now = new Date();
        switch(format) {
            case 1:
                return formatter1().format(now);
            case 2:
                return formatter2().format(now);
            case 3:
                return formatter3().format(now);
            case 4:
                return formatter4().format(now);
            case 5:
                return formatter5().format(now);
            case 6:
                return formatter6().format(now);
            case 7:
                return formatter7().format(now);
            case 8:
                return formatter8().format(now);
            default:
                return formatter0().format(now);
        }
    }

    public static String getDateStr(String format) {
        if (!isCorrectSimpleDateFormat(format)) {
           return"日期格式错误!";
        }

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(now);
    }

    public static String getNetworkDateStr(String... para) {
        String dateTime = "N/A";
        SimpleDateFormat dateFormat = new SimpleDateFormat(para != null && para.length > 1 ? para[1] : "yyyy-MM-dd HH:mm:ss");

        try {
            URL url = new URL(para != null && para.length > 0 ? para[0] : "http://www.baidu.com/");
            URLConnection conn = url.openConnection();
            conn.connect();
            Date date = new Date(conn.getDate());
            dateTime = dateFormat.format(date);
        } catch (Exception var6) {
            Date date = new Date();
            dateTime = dateFormat.format(date);
        }

        return dateTime;
    }

    public static Date getDateFromStr(String srcDate, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(srcDate);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromStr(String srcDate) {
        try {
            return formatter3().parse(srcDate);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getDateStrAfterDaysFromNow(long days) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + days * 24L * 60L * 60L * 1000L);
        return formatter3().format(after);
    }

    public static String getDateStrAfterSecondsFromNow(long seconds) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + seconds * 1000L);
        return formatter0().format(after);
    }

    public static String getDateStrAfterDaysFromNow(long days, SimpleDateFormat formatter) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + days * 24L * 60L * 60L * 1000L);
        return formatter.format(after);
    }

    public static String getDateStrFromSpecDate(SimpleDateFormat formattersrc, SimpleDateFormat formatterdest, String dateStr) {
        Date date = null;

        try {
            date = formattersrc.parse(dateStr);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return formatterdest.format(date);
    }

    public static String getDateStrAfterDaysFrom(String fromDateStr, long days) {
        Date frmDate = getDateFromStr(fromDateStr);
        long nowTime = frmDate.getTime();
        Date after = new Date();
        after.setTime(nowTime + days * 24L * 60L * 60L * 1000L);
        return formatter3().format(after);
    }

    public static String getDateStrBeforeDaysFromNow(long days) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime - days * 24L * 60L * 60L * 1000L);
        return formatter3().format(after);
    }

    public static String getDateStrBeforeDaysFromNow(long days, String format) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime - days * 24L * 60L * 60L * 1000L);
        return (new SimpleDateFormat(format)).format(after);
    }

    public static String getDateStrBeforeDaysFromNowyyyyMMddHHmmss(long days) {
        Date now = new Date();
        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime - days * 24L * 60L * 60L * 1000L);
        return formatter4().format(after);
    }

    public static String getDateStrBeforeDaysFrom(String fromDateStr, long days) {
        Date frmDate = getDateFromStr(fromDateStr);
        long nowTime = frmDate.getTime();
        Date after = new Date();
        after.setTime(nowTime - days * 24L * 60L * 60L * 1000L);
        return formatter3().format(after);
    }

    public static String getDateStrAfterHoursFromNow(String srcTime, long hours) {
        Date now = null;

        try {
            now = formatter0().parse(srcTime);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + hours * 60L * 60L * 1000L);
        return formatter0().format(after);
    }

    public static String getDateStrBeforeHoursFromNow(String srcTime, long hours) {
        Date now = null;

        try {
            now = formatter0().parse(srcTime);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime - hours * 60L * 60L * 1000L);
        return formatter0().format(after);
    }

    public static String getDateStrAfterMinutesFromNow(String srcTime, long minutes) {
        Date now = null;

        try {
            now = formatter0().parse(srcTime);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime + minutes * 60L * 1000L);
        return formatter0().format(after);
    }

    public static String getDateStrBeforeMinutesFromNow(String srcTime, long minutes) {
        Date now = null;

        try {
            now = formatter0().parse(srcTime);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        long nowTime = now.getTime();
        Date after = new Date();
        after.setTime(nowTime - minutes * 60L * 1000L);
        return formatter0().format(after);
    }

    public static String getDateStrOfNextMonth(String formatter) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, 2);
        calendar.set(5, 0);
        return (new SimpleDateFormat(formatter)).format(calendar.getTime());
    }

    public static String getDateStrOfLastMonth(String formatter) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, -1);
        return (new SimpleDateFormat(formatter)).format(calendar.getTime());
    }

    public static String getDateStrOfNextMonth(String formatter, int nextMonthNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, 1 + nextMonthNum);
        calendar.set(5, 0);
        return (new SimpleDateFormat(formatter)).format(calendar.getTime());
    }
}
