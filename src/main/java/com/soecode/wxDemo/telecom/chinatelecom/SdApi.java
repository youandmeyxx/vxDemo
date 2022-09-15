//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.soecode.wxDemo.telecom.chinatelecom;

import com.soecode.wxDemo.pojo.CardInfo;
import com.soecode.wxDemo.telecom.chinatelecom.util.JSONUtils;
import com.soecode.wxDemo.utils.Log4jV2Util;

import java.util.HashMap;
import java.util.Map;

public class SdApi extends ChinaTelApi {
    public SdApi() {
    }

    @Override
    String userId() {
        return "shjj021";
    }

    @Override
    String password() {
        return "qwQqSEv7cFSH4YM4";
    }

    @Override
    String key1() {
        return "e2b";
    }

    @Override
    String key2() {
        return "hh6";
    }

    @Override
    String key3() {
        return "KVH";
    }

    public static void main(String... strings) throws Exception {
        Log4jV2Util.initLog4jV2TestEnv();
        CardInfo cardinfo = new CardInfo();
//        String iccid_number="8986111920403305468";
        String accessCode="1410419685269";
        cardinfo.setAccessCode(accessCode);
//        System.out.println((new SdApi()).queryCardMainStatus(accessCode));
        String onlineStatus=(new SdApi()).getOnlineStatus(accessCode);
//        String onlineStatus="{\"resultCode\":\"0\",\"resultMsg\":\"处理成功！\",\"groupTransactionId\":\"1000000190202208310180522281\",\"description\":{\"result\":\"-1\",\"msisdn\":\"861410419685269\",\"imsi\":\"460113422075161\",\"netModel\":\"2\",\"acctStatusType\":\"stop\",\"eventTimestamp\":\"2022-08-27 17:13:48\",\"framedIpAddress\":\"10.11.9.102\",\"framedIpv6Prefix\":\"\",\"framedInterfaceId\":\"\",\"apn\":\"shjj4.grevpdn.sd\",\"rattype\":\"6\",\"imei\":\"8637630516906323\",\"provname\":\"山东\",\"duration\":\"83316\",\"stopTime\":\"2022-08-27 17:13:48\",\"startTime\":\"\"}}";
//        Map<String, Object> onlineStatusMap = (HashMap<String, Object>) JSONUtils.jsonParse(onlineStatus);
        System.out.println(onlineStatus);
        //String re = (new SdApi()).queryTraffic(access_number, false);
//        long a = (new SdApi()).getOffNetQuota(access_number);
//        boolean isOFF = (new SdApi()).isOffNet(access_number);
//        String status = (new SdApi()).getCardStatusName(access_number);
//        long ab = (new SdApi()).getOffNetQuota(access_number);
        //System.out.println(a);
        //System.out.println((new SdApi()).queryCardMainStatus("1410367391865"));
    }
}
