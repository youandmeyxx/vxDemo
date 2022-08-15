package com.soecode.wxDemo.utils;



import java.util.List;
import com.soecode.wxDemo.utils.NumberUtil;

public final class Assert extends org.springframework.util.Assert {
    public Assert() {
    }

    public static void isTrue(boolean expression, String errMsg) {
        if (!expression) {
            throw new RuntimeException();
        }
    }

    public static void isFalse(boolean expression, String errMsg) {
        if (expression) {
            throw new RuntimeException();
        }
    }

    public static void isNull(Object object, String errMsg) {
        if (object != null) {
            throw new RuntimeException();
        }
    }

    public static void isNotNull(Object object, String errMsg) {
        if (object == null) {
            throw new RuntimeException();
        }
    }

    public static void isEmpty(Object object, String errMsg) {
        if (!NumberUtil.isEmpty(object)) {
            throw new RuntimeException();
        }
    }

    public static void isNotEmpty(Object object, String errMsg) {
        if (NumberUtil.isEmpty(object)) {
            throw new RuntimeException();
        }
    }

    public static void isNotEmptyList(List list, String errMsg) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException();
        }
    }

    public static void isNotEmptyArray(Object[] arr, String errMsg) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException();
        }
    }

    public static void isNotSameLengthArray(Object[] arr1, Object[] arr2, String errMsg) {
        if (arr1 != null && arr1.length != 0 && arr2 != null && arr2.length != 0) {
            if (arr1.length != arr2.length) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }
}
