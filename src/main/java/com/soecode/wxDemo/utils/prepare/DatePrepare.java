package com.soecode.wxDemo.utils.prepare;


import java.text.SimpleDateFormat;

public class DatePrepare {
    public static int DEFAULT = 9;
    public static int YYMM = 1;
    public static int YYMMDD = 2;
    public static int YYYYMMDD_WITH = 3;
    public static int YYYYMMDDHHMMSS = 4;
    public static int YYYYMMDD = 5;
    public static final String DATE_BEGIN_POSTFIX = " 00:00:00";
    public static final String DATE_END_POSTFIX = " 23:59:59";
    public static transient int gregorianCutoverYear = 1582;

    public DatePrepare() {
    }

    public static boolean isCorrectSimpleDateFormat(String dataformat) {
        boolean correct = true;
        if (dataformat.contains("Y") || dataformat.contains("h") || dataformat.contains("D")) {
            correct = false;
        }

        return correct;
    }

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public static SimpleDateFormat formatter0() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat formatter1() {
        return new SimpleDateFormat("yyMM");
    }

    public static SimpleDateFormat formatter2() {
        return new SimpleDateFormat("yyMMdd");
    }

    public static SimpleDateFormat formatter3() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat formatter4() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static SimpleDateFormat formatter5() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static SimpleDateFormat formatter6() {
        return new SimpleDateFormat("yyyy");
    }

    public static SimpleDateFormat formatter7() {
        return new SimpleDateFormat("yyyyMM");
    }

    public static SimpleDateFormat formatter8() {
        return new SimpleDateFormat("yyyy-MM");
    }

    public static SimpleDateFormat formatterFull() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS");
    }
}
