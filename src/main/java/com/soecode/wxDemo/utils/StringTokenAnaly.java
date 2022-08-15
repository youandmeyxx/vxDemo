package com.soecode.wxDemo.utils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;
import com.soecode.wxDemo.utils.StringUtil;

public final class StringTokenAnaly {
    public StringTokenAnaly() {
    }

    public static List<String> getStringList(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<String> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(cur.trim());
            }

            return ret;
        }
    }

    public static Set<String> getStringSet(String src, String delimiter) {
        Set ret = new LinkedHashSet();
        StringTokenizer stz = new StringTokenizer(src, delimiter);

        while(stz.hasMoreTokens()) {
            String cur = stz.nextToken();
            ret.add(cur.trim());
        }

        return ret;
    }

    public static String[] getStringArray(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<String> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(cur.trim());
            }

            return (String[])ret.toArray(new String[0]);
        }
    }

    public static List<Long> getLongList(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<Long> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(Long.parseLong(cur.trim()));
            }

            return ret;
        }
    }

    public static Set<Long> getLongSet(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            Set<Long> ret = new HashSet();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(Long.parseLong(cur.trim()));
            }

            return ret;
        }
    }

    public static Long[] getLongArray(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<Long> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(Long.parseLong(cur.trim()));
            }

            return (Long[])ret.toArray(new Long[0]);
        }
    }

    public static List<Integer> getIntegerList(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<Integer> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(Integer.parseInt(cur.trim()));
            }

            return ret;
        }
    }

    public static Integer[] getIntegerArray(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<Integer> ret = new ArrayList();
            StringTokenizer stz = new StringTokenizer(src, delimiter);

            while(stz.hasMoreTokens()) {
                String cur = stz.nextToken();
                ret.add(Integer.parseInt(cur.trim()));
            }

            return (Integer[])ret.toArray(new Integer[0]);
        }
    }

    public static String getStrByFullStringAndDeleteStringWithDelimiter(String fullStr, String todelStr, String delimiter) {
        List<Long> fulllist = getLongList(fullStr, delimiter);
        List<Long> todellist = getLongList(todelStr, delimiter);
        fulllist.removeAll(todellist);
        StringBuffer str = new StringBuffer();
        Iterator var7 = fulllist.iterator();

        while(var7.hasNext()) {
            Long id = (Long)var7.next();
            str.append(id).append(delimiter);
        }

        String strvall = str.toString();
        if (strvall.endsWith(",")) {
            strvall = strvall.substring(0, str.length() - 1);
        }

        if (strvall.startsWith(",")) {
            strvall = strvall.substring(1);
        }

        return strvall;
    }

    public static String[] getStringArraySeperateBySpecToken(String src, String token, boolean tokenIncluded) {
        String[] strarray = null;
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            strarray = StringUtils.splitByWholeSeparator(src, token);
            if (tokenIncluded) {
                List<String> tokeanIncludedList = new ArrayList();
                String[] var8 = strarray;
                int var7 = strarray.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    String str = var8[var6];
                    tokeanIncludedList.add(token.concat(str));
                }

                strarray = (String[])tokeanIncludedList.toArray(new String[0]);
            }

            return strarray;
        }
    }

    public static List<String> getStringListWithDelimiterCouple(String src, String delimiter) {
        if (com.soecode.wxDemo.utils.StringUtil.isEmpty(src)) {
            return null;
        } else {
            List<String> ret = new ArrayList();

            int endIndex;
            for(int startIndex = 0; src.indexOf(delimiter, startIndex) != -1; startIndex = endIndex + 1) {
                startIndex = src.indexOf(delimiter, startIndex);
                endIndex = src.indexOf(delimiter, startIndex + 1);
                ret.add(src.substring(startIndex + 1, endIndex));
            }

            return ret;
        }
    }

    public static void main(String[] args) {
        getStringListWithDelimiterCouple("#STAFF_NAME#(#STAFF_CODE#)", "#");
        getStringListWithDelimiterCouple("a#STAFF_NAME##STAFF_CODE#", "#");
        String str = "http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017109http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017291http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017284http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017100http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017099http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017105http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017103http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017116http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017287http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000382http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000016927http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000395http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000394http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000029608http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017115http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000015685http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000650http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007608http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007599http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007586http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007598http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007607http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000017247http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007606http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000016204http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000691http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000384http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000786http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000007597http://www.fjhfzy.cn/nav.whtml?_a=queryqr&qridstr=000000788";
        String[] s = getStringArraySeperateBySpecToken(str, "http", true);
        System.out.println(getStringArray("2,3,4,5,6,7, ,", ","));
        System.out.println(StringUtil.split("2,3,4,5,6,7, ,", ","));
    }
}
