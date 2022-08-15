//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.soecode.wxDemo.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import com.soecode.wxDemo.utils.StringTokenAnaly;

public final class StringUtil extends StringUtils {
    private static Map<Integer, String> NumStringMapping = new HashMap();
    public static final String DEFAULT_PATH_SEPARATOR = ",";

    static {
        NumStringMapping.put(2, "0");
        NumStringMapping.put(3, "00");
        NumStringMapping.put(4, "000");
        NumStringMapping.put(5, "0000");
        NumStringMapping.put(6, "00000");
        NumStringMapping.put(7, "000000");
        NumStringMapping.put(8, "0000000");
        NumStringMapping.put(9, "00000000");
        NumStringMapping.put(10, "000000000");
        NumStringMapping.put(11, "0000000000");
        NumStringMapping.put(12, "00000000000");
        NumStringMapping.put(13, "000000000000");
        NumStringMapping.put(14, "0000000000000");
        NumStringMapping.put(15, "00000000000000");
    }

    public StringUtil() {
    }

    public static String getString(String string) {
        return string == null ? "" : string.trim();
    }

    public static String getString(Object string) {
        return string == null ? "" : string.toString().trim();
    }

    public static String getStringByNum(int digNum) {
        return isEmpty((String)NumStringMapping.get(digNum)) ? "0000" : (String)NumStringMapping.get(digNum);
    }

    public static String getStringByLong(long num, int digNum) {
        String numStr = String.valueOf(num);
        numStr = getStringByNum(digNum) + numStr;
        numStr = numStr.substring(numStr.length() - digNum);
        return numStr;
    }

    public static String getUTF8String(String srcStr) {
        try {
            return isEmpty(srcStr) ? srcStr : new String(srcStr.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String replace(String src, String symbol, String value) {
        int oriLength = src.length();
        int symboIndex = src.indexOf(symbol);
        int symboLength = symbol.length();
        StringBuffer sb = new StringBuffer();
        sb.append(src.substring(0, symboIndex)).append(value).append(src.subSequence(symboIndex + symboLength, oriLength));
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isEmptyStrict(String str) {
        return str == null || str.equals("") || str.equalsIgnoreCase("null");
    }

    public static boolean isEmptyStrict(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return isEmpty((String)obj);
        } else if (obj instanceof StringBuffer) {
            return isEmpty(obj.toString());
        } else if (!(obj instanceof String[])) {
            return false;
        } else {
            String[] var4;
            int var3 = (var4 = (String[])obj).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                String s = var4[var2];
                if (!isEmpty(s)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return isEmpty((String)obj);
        } else if (obj instanceof StringBuffer) {
            return isEmpty(obj.toString());
        } else if (!(obj instanceof String[])) {
            if (obj instanceof Double) {
                return (Double)obj == 0.0D;
            } else if (obj instanceof Long) {
                return (Long)obj == 0L;
            } else if (obj instanceof Integer) {
                return (Integer)obj == 0;
            } else if (obj instanceof Float) {
                return (Float)obj == 0.0F;
            } else {
                return false;
            }
        } else {
            String[] var4;
            int var3 = (var4 = (String[])obj).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                String s = var4[var2];
                if (!isEmpty(s)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String fromLongarrayToString(long[] src) {
        if (isEmpty((Object)src)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            long[] var6 = src;
            int var5 = src.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                long srId = var6[var4];
                sb.append(srId).append(",");
            }

            return sb.substring(0, sb.length() - 1);
        }
    }

    public static String fromStringarrayToString(String[] src) {
        if (isEmpty((Object)src)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            String[] var5 = src;
            int var4 = src.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                String srId = var5[var3];
                if (isNotEmpty(srId)) {
                    sb.append(srId).append(",");
                }
            }

            return sb.substring(0, sb.length() - 1);
        }
    }

    public static String[] processDifferentStrings(String[] str1, String[] str2) {
        Assert.isNotSameLengthArray(str1, str2, "两个数组长度不一致!");
        Set<String> list = new HashSet();

        for(int i = 0; i < str1.length; ++i) {
            String s1 = str1[i];
            String s2 = str2[i];
            list.add(s1);
            if (!s1.equals(s2)) {
                list.add(s2);
            }
        }

        return (String[])list.toArray(new String[0]);
    }

    public static boolean isMobileNoValid(String mobileNo) {
        Pattern p = Pattern.compile("^1[3,5]{1}[0-9]{1}[0-9]{8}$");
        Matcher matcher = p.matcher(mobileNo);
        return mobileNo != null && !"".equals(mobileNo) && matcher.find();
    }

    public static boolean isMacAddrValid(String mac) {
        Pattern p = Pattern.compile("^[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}$");
        Matcher matcher = p.matcher(mac);
        return mac != null && !"".equals(mac) && matcher.find();
    }

    public static String constructQueryStrByList(List idList) {
        StringBuffer idStr = new StringBuffer("(");
        if (idList != null && idList.size() > 0) {
            for(int i = 0; i < idList.size(); ++i) {
                idStr.append(idList.get(i)).append(",");
            }

            idStr.deleteCharAt(idStr.length() - 1).append(")");
        } else {
            idStr.append("-1)");
        }

        return idStr.toString();
    }

    public static String replaceDelimiter(String src, String oriDeli, String destDeli) {
        List<String> list = StringTokenAnaly.getStringList(src, oriDeli);
        StringBuffer ret = new StringBuffer();

        for(int i = 0; i < list.size(); ++i) {
            String str = (String)list.get(i);
            if (i != 0) {
                ret.append(destDeli);
            }

            ret.append(str);
        }

        return ret.toString();
    }

    public static String toFirstUpperCase(String str) {
        return str != null && str.length() != 0 ? str.substring(0, 1).toUpperCase() + str.substring(1) : str;
    }

    public static String getCodeByNativeId(long id, int digNum) {
        String str = getStringByNum(digNum);
        String ids = str + String.valueOf(id);
        return ids.substring(ids.length() - digNum);
    }

    public static String getShortStrWhileOthercharReplaceWith3Dots(String srcStr, int maxWidth) {
        return StringUtils.abbreviate(srcStr, maxWidth);
    }

    public static String reverseStrings(String srcStr) {
        return StringUtils.reverse(srcStr);
    }

    public static boolean isNumeric(String srcStr) {
        return StringUtils.isNumeric(srcStr);
    }

    public static boolean isAlpha(String srcStr) {
        return StringUtils.isAlpha(srcStr);
    }

    public static int countMatches(String srcStr, String destStr) {
        return StringUtils.countMatches(srcStr, destStr);
    }

    public static String[] strToStrArray(String str) {
        return strToStrArrayManager(str, ",");
    }

    public static String[] strToStrArray(String str, String separator) {
        return strToStrArrayManager(str, separator);
    }

    private static String[] strToStrArrayManager(String str, String separator) {
        StringTokenizer strTokens = new StringTokenizer(str, separator);
        String[] strArray = new String[strTokens.countTokens()];

        for(int i = 0; strTokens.hasMoreTokens(); ++i) {
            strArray[i] = strTokens.nextToken().trim();
        }

        return strArray;
    }

    public static String trimLeft(String value) {
        String result = value;
        if (value == null) {
            return value;
        } else {
            char[] ch = value.toCharArray();
            int index = -1;

            for(int i = 0; i < ch.length && Character.isWhitespace(ch[i]); index = i++) {
            }

            if (index != -1) {
                result = value.substring(index + 1);
            }

            return result;
        }
    }

    public static String trimRight(String value) {
        String result = value;
        if (value == null) {
            return value;
        } else {
            char[] ch = value.toCharArray();
            int endIndex = -1;

            for(int i = ch.length - 1; i > -1 && Character.isWhitespace(ch[i]); endIndex = i--) {
            }

            if (endIndex != -1) {
                result = value.substring(0, endIndex);
            }

            return result;
        }
    }

    public static String delString(String inputString, String delStrs) {
        if (inputString != null && inputString.length() != 0) {
            String[] strs = strToStrArray(delStrs);

            for(int i = 0; i < strs.length; ++i) {
                if (strs[i].equals("")) {
                    inputString = inputString.replaceAll(" ", "");
                } else if (strs[i].compareTo("a") >= 0) {
                    inputString = replace(inputString, strs[i], "");
                } else {
                    inputString = inputString.replaceAll("\\\\" + strs[i], "");
                }
            }

            if (subCount(delStrs, ",") > strs.length) {
                inputString = inputString.replaceAll("\\,", "");
            }

            return inputString;
        } else {
            return inputString;
        }
    }

    public static int subCount(String parentStr, String subStr) {
        int count = 0;

        for(int i = 0; i < parentStr.length() - subStr.length(); ++i) {
            String tempString = parentStr.substring(i, i + subStr.length());
            if (subStr.equals(tempString)) {
                ++count;
            }
        }

        return count;
    }

    public static boolean isInteger(String str) {
        if (str != null && !"".equals(str)) {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }

    public static boolean containsString(String string) {
        return Pattern.compile("(?i)[a-z]").matcher(string).find();
    }

    public static boolean isDouble(String str) {
        return NumberUtil.isDouble(str);
    }

    public static boolean isStringMatchesPattern(String string, String pattern) {
        return Pattern.compile(pattern).matcher(string).matches();
    }

    public static String processStringAsInClause(String inData) {
        return inData.endsWith(",") ? inData.substring(0, inData.length() - 1) : inData;
    }

    public static String[] splitEnhanced(String srcString, char delimiter) {
        StringBuffer processAfterString = new StringBuffer();
        boolean dlimiterOcur = false;

        for(int i = 0; i < srcString.length(); ++i) {
            char c = srcString.charAt(i);
            if (c == delimiter && dlimiterOcur) {
                dlimiterOcur = true;
                processAfterString.append(" ").append(c);
            } else if (c == delimiter) {
                dlimiterOcur = true;
                processAfterString.append(c);
            } else {
                dlimiterOcur = false;
                processAfterString.append(c);
            }
        }

        return StringTokenAnaly.getStringArray(processAfterString.toString(), String.valueOf(delimiter));
    }

    public static boolean isThisClassContainsInScanPackages(String clazzName) {
//        List<String> packagesList = StringTokenAnaly.getStringList(Constants.SCAN_PACKAGE_PATH, "|");
//        Iterator var3 = packagesList.iterator();
//
//        while(var3.hasNext()) {
//            String packageName = (String)var3.next();
//            if (clazzName.startsWith(packageName)) {
//                return true;
//            }
//        }

        return false;
    }

    public static boolean isFristDigitalStringGreaterThanSecond(String first, String second) {
        if (first != null && second != null) {
            String[] s1 = first.split("\\.");
            String[] s2 = second.split("\\.");
            int l1 = s1.length;
            int l2 = s2.length;

            for(int i = 0; i < l1 || i < l2; ++i) {
                int n1 = i < l1 ? Integer.parseInt(s1[i]) : 0;
                int n2 = i < l2 ? Integer.parseInt(s2[i]) : 0;
                if (n1 < n2) {
                    return false;
                }

                if (n1 > n2) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static String getStringWithLengthLimit(String srcStr, int maxLength) {
        return srcStr.length() <= maxLength ? srcStr : srcStr.substring(0, maxLength);
    }

    public static void main(String[] args) {
        System.out.println(isDouble("1.1"));
        System.out.println(isInteger(""));
    }
}
