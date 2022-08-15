//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.soecode.wxDemo.telecom.chinatelecom;

import com.soecode.wxDemo.pojo.CardInfo;
import com.soecode.wxDemo.utils.Log4jV2Util;

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
        String accessCode="1064910654198";
        cardinfo.setAccessCode(accessCode);
//        System.out.println((new SdApi()).queryCardMainStatus(accessCode));
        String proNetNo=(new SdApi()).getCardIpPomainName(cardinfo);
        System.out.println(proNetNo);
        //String re = (new SdApi()).queryTraffic(access_number, false);
//        long a = (new SdApi()).getOffNetQuota(access_number);
//        boolean isOFF = (new SdApi()).isOffNet(access_number);
//        String status = (new SdApi()).getCardStatusName(access_number);
//        long ab = (new SdApi()).getOffNetQuota(access_number);
        //System.out.println(a);
        //System.out.println((new SdApi()).queryCardMainStatus("1410367391865"));
    }
}
