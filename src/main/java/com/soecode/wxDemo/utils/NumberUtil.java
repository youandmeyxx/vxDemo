package com.soecode.wxDemo.utils;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {
    private static final Logger logger = LoggerFactory.getLogger(com.soecode.wxDemo.utils.NumberUtil.class);

    public NumberUtil() {
    }

    public static boolean isThisDoubleObjectHasIntegerValue(Double dblObj) {
        boolean isIntegerValue = false;
        if (dblObj != null && (double)dblObj.longValue() == dblObj) {
            isIntegerValue = true;
        }

        return isIntegerValue;
    }

    public static boolean isThisFloatObjectHasIntegerValue(Float floatObj) {
        boolean isIntegerValue = false;
        if (floatObj != null && (float)floatObj.longValue() == floatObj) {
            isIntegerValue = true;
        }

        return isIntegerValue;
    }

    public static float getFloatValueOfFloatObject(Float floatobj) {
        return floatobj == null ? 0.0F : floatobj;
    }

    public static double getDoubleValueOfDoubleObject(Double doubleobj) {
        return doubleobj == null ? 0.0D : doubleobj;
    }

    public static long getLongValueOfLongObject(Long longobj) {
        return longobj == null ? 0L : longobj;
    }

    public static long getLongValueOfIntegerObject(Integer intobj) {
        return intobj == null ? 0L : intobj.longValue();
    }

    public static int getIntValueOfIntObject(Integer intobj) {
        return intobj == null ? 0 : intobj;
    }

    public static int getIntValueFromLong(long longobj) {
        return Long.valueOf(longobj).intValue();
    }

    public static String getDoubleByScientificValue(String value) {
        BigDecimal bdValue = new BigDecimal(value);
        return bdValue.stripTrailingZeros().toPlainString();
    }

    public static String getNonScientificValueDouble(String value) {
        BigDecimal bdValue = new BigDecimal(value);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(20);
        nf.setGroupingUsed(false);
        return nf.format(bdValue);
    }

    public static double getDoubleValueByObject(Object number) {
        double value = 0.0D;
        if (!com.soecode.wxDemo.utils.StringUtil.isEmpty(number)) {
            if (number instanceof Double) {
                value = (Double)number;
            } else if (number instanceof Long) {
                value = ((Long)number).doubleValue();
            } else if (number instanceof Integer) {
                value = ((Integer)number).doubleValue();
            } else if (number instanceof Float) {
                value = ((Float)number).doubleValue();
            } else if (number instanceof String) {
                value = Double.parseDouble((String)number);
            }
        }

        return value;
    }

    public static float getFlowValueByObject(Object number) {
        float value = 0.0F;
        if (!com.soecode.wxDemo.utils.StringUtil.isEmpty(number)) {
            if (number instanceof Double) {
                value = ((Double)number).floatValue();
            } else if (number instanceof Long) {
                value = ((Long)number).floatValue();
            } else if (number instanceof Integer) {
                value = ((Integer)number).floatValue();
            } else if (number instanceof Float) {
                value = (Float)number;
            } else if (number instanceof String) {
                value = Float.parseFloat((String)number);
            }
        }

        return value;
    }

    public static long getLongValueByNumberObject(Object number) {
        long value = 0L;
        if (number != null) {
            if (number instanceof Double) {
                value = ((Double)number).longValue();
                if (!isThisDoubleObjectHasIntegerValue((Double)number)) {
                    throw new RuntimeException("当前浮点数值" + number + "不是整型数值,转换为整型将丢失精度");
                }
            } else if (number instanceof Long) {
                value = (Long)number;
            } else if (number instanceof Integer) {
                value = ((Integer)number).longValue();
            } else if (number instanceof String) {
                value = Long.parseLong(String.valueOf(number));
            } else if (number instanceof Float) {
                value = ((Float)number).longValue();
                if (!isThisFloatObjectHasIntegerValue((Float)number)) {
                    throw new RuntimeException("当前浮点数值" + number + "不是整型数值,转换为整型将丢失精度");
                }
            }
        }

        return value;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Double) {
            return (Double)obj == 0.0D;
        } else if (obj instanceof Long) {
            return (Long)obj == 0L;
        } else if (obj instanceof Integer) {
            return (Integer)obj == 0;
        } else if (obj instanceof Float) {
            return (Float)obj == 0.0F;
        } else {
            return obj instanceof String ? StringUtil.isEmpty(obj) : false;
        }
    }

    public static boolean greater(Double dbl1, Double dbl2) {
        return getDoubleValueOfDoubleObject(dbl1) > getDoubleValueOfDoubleObject(dbl2);
    }

    public static boolean greaterOrEquals(Double dbl1, Double dbl2) {
        return getDoubleValueOfDoubleObject(dbl1) >= getDoubleValueOfDoubleObject(dbl2);
    }

    public static boolean greater(Long long1, Long long2) {
        return getLongValueOfLongObject(long1) > getLongValueOfLongObject(long2);
    }

    public static boolean greaterOrEquals(Long long1, Long long2) {
        return getLongValueOfLongObject(long1) >= getLongValueOfLongObject(long2);
    }

    public static boolean less(Double dbl1, Double dbl2) {
        return getDoubleValueOfDoubleObject(dbl1) < getDoubleValueOfDoubleObject(dbl2);
    }

    public static boolean lessOrEquals(Double dbl1, Double dbl2) {
        return less(dbl1, dbl2) || equals(dbl1, dbl2);
    }

    public static boolean equals(Double dbl1, Double dbl2) {
        return getDoubleValueOfDoubleObject(dbl1) == getDoubleValueOfDoubleObject(dbl2);
    }

    public static boolean equals(Float float1, Float float2) {
        return getFloatValueOfFloatObject(float1) == getFloatValueOfFloatObject(float2);
    }

    public static boolean equals(Long long1, Long long2) {
        return getLongValueOfLongObject(long1) == getLongValueOfLongObject(long2);
    }

    public static boolean equals(Integer int1, Integer int2) {
        return getIntValueOfIntObject(int1) == getIntValueOfIntObject(int2);
    }

    public static boolean equals(Long long1, Integer int2) {
        return getLongValueOfLongObject(long1) == getLongValueOfIntegerObject(int2);
    }

    public static boolean isFloat(String str) {
        if (str != null && !"".equals(str)) {
            Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }

    public static boolean isLong(String str) {
        if (str != null && !"".equals(str)) {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(getDoubleByScientificValue("3.145728E7"));
        System.out.println(getNonScientificValueDouble("3.145728E7"));
    }

    public static boolean isDouble(String str) {
        return true;
    }
}
