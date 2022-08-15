package com.soecode.wxDemo.telecom.chinatelecom.util.category;


import com.soecode.wxDemo.telecom.chinatelecom.util.DesUtils;
import  com.soecode.wxDemo.telecom.chinatelecom.util.category.CommonShare;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServiceActApiPrepare extends CommonShare {
    public ServiceActApiPrepare() {
    }

    public static String[] getServiceActPasswordAndSign(String access_number, String methodName, String flowValue) {
        String[] result = new String[2];
        String[] arr = new String[]{access_number, user_id, password, flowValue, methodName};
        new DesUtils();
        String passWord = DesUtils.strEnc(password, key1, key2, key3);
        String sign = DesUtils.strEnc(DesUtils.naturalOrdering(arr), key1, key2, key3);
        result[0] = passWord;
        result[1] = sign;
        return result;
    }

    public static String getServiceActRequestUrl(String access_number, String methodName, String flowValue, Map<String, String> extraParameterMap) {
        String[] result = getServiceActPasswordAndSign(access_number, methodName, flowValue);
        StringBuffer url = new StringBuffer(api_serviceactaware_prefix);
        url.append(methodName);
        url.append("&access_number=").append(access_number);
        url.append("&user_id=").append(user_id);
        url.append("&passWord=").append(result[0]);
        url.append("&sign=").append(result[1]);
        if (extraParameterMap != null) {
            Iterator extraparamiterator = extraParameterMap.entrySet().iterator();

            while(extraparamiterator.hasNext()) {
                Entry entity = (Entry)extraparamiterator.next();
                url.append("&").append(entity.getKey()).append("=").append(entity.getValue());
            }
        }

        return url.toString();
    }
}
