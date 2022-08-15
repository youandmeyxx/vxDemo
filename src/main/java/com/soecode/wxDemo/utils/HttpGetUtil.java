//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.soecode.wxDemo.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpGetUtil {
    protected static final Logger logger = LoggerFactory.getLogger(HttpGetUtil.class);
    private static RequestConfig requestConfig;
    private static String defaultUserAgent = "@uniframework";
    private static int responseTimeout = 60;
    private static int connectTimeout = 60;
    private static int connectionRequestTimeout = 60;

    public HttpGetUtil() {
    }

    private static RequestConfig requestConfig() {
        if (requestConfig == null) {
            requestConfig = RequestConfig.custom().setCookieSpec("strict").setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList("NTLM", "Digest")).setProxyPreferredAuthSchemes(Arrays.asList("Basic")).setConnectionRequestTimeout(Timeout.ofSeconds((long)connectTimeout)).setConnectTimeout(Timeout.ofSeconds((long)connectionRequestTimeout)).setResponseTimeout(Timeout.ofSeconds((long)responseTimeout)).build();
        }

        return requestConfig;
    }

    public static String visitUrlByGet(String userAgent, final String url, boolean trace) throws Exception {
        BasicCookieStore cookieStore = new BasicCookieStore();
        Throwable var4 = null;
        Object var5 = null;

        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setUserAgent(StringUtil.isEmpty(userAgent) ? defaultUserAgent : userAgent).setDefaultRequestConfig(requestConfig()).build();

            String var10000;
            try {
                HttpGet getMethod = new HttpGet(url);
                HttpClientContext context = HttpClientContext.create();
                context.setCookieStore(cookieStore);
                HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
                    @Override
                    public String handleResponse(ClassicHttpResponse response) throws IOException {
                        int status = response.getCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();

                            try {
                                return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                            } catch (ParseException var5) {
//                                if (Constants.IS_NOW_RUN_IN_SERVER) {
//                                    UniframeworkLogUtil.logGetVisitException(var5, url);
//                                }

                                throw new ClientProtocolException(var5);
                            }
                        } else {
                            throw new ClientProtocolException("错误的http请求返回状态" + status);
                        }
                    }
                };
                String responseBody = (String)httpclient.execute(getMethod, responseHandler);
//                if (trace && Constants.IS_NOW_RUN_IN_SERVER && Uniframework.optimizeKeepVisitDays > 0L) {
//                    Visit visit = new Visit();
//                    visit.setRESPONSE_BODY(responseBody);
//                    visit.setVISIT_METHOD(1L);
//                    visit.setVISIT_PARAMETERS("");
//                    visit.setVISIT_TIME(DateUtil.getDateStr(100));
//                    visit.setVISIT_URL(url);
//                    visit.setVISIT_IP(Uniframework.serverIp);
//                    ServiceAnyWare.serviceLocator.getIndependantTransactionalDao().add(visit);
//                }

                var10000 = responseBody;
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }

            }

            return var10000;
        } catch (Throwable var17) {
            if (var4 == null) {
                var4 = var17;
            } else if (var4 != var17) {
                var4.addSuppressed(var17);
            }

            throw var17;
        }
    }

    public static String visitUrlByGet(String userAgent, String url, Map paramMap, boolean trace) throws Exception {
        return visitUrlByGet(userAgent, processUrl4GetMethod(url, paramMap), trace);
    }

    private static String processUrl4GetMethod(String url, Map params) {
        Object key;
        Object val;
        if (params != null) {
            for(Iterator iter = params.entrySet().iterator(); iter.hasNext(); url = url + (url.contains("?") ? "&" : "?") + key + "=" + val) {
                Entry entry = (Entry)iter.next();
                key = entry.getKey();
                val = entry.getValue();
            }
        }

        return url;
    }

    public static void main(String... strings) throws Exception {
       // Log4jV2Util.initLog4jV2TestEnv();
        String serviceUrl = "http://api.weifenxiao.com/user/pointLogs";
        Map<String, String> paramap = new HashMap();
        paramap.put("access_token", "49ff8616f884cb531dddf135a6f8b6c6a9d8d78784127e68335131347773cf9a");
        paramap.put("user_id", "1570538");
        String result = null;

        try {
            result = visitUrlByGet("", serviceUrl, paramap, true);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        String token = "49ff8616f884cb531dddf135a6f8b6c6a9d8d78784127e68335131347773cf9a";
//        Logger logger = LoggerFactory.getLogger(Sample.class);
//        logger.warn(GsonParser.formatJson(result));
    }
}
