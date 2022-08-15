//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.soecode.wxDemo.telecom.chinatelecom;

import com.soecode.wxDemo.utils.Log4jV2Util;

public class SdApi extends ChinaTelApi {
    public SdApi() {
    }

    String userId() {
        return "shjj021";
    }

    String password() {
        return "qwQqSEv7cFSH4YM4";
    }

    String key1() {
        return "e2b";
    }

    String key2() {
        return "hh6";
    }

    String key3() {
        return "KVH";
    }

    public static void main(String... strings) throws Exception {
        Log4jV2Util.initLog4jV2TestEnv();
       // CardInfo cardinfo = new CardInfo();
        String iccid_number="8986111920403305468";

        System.out.println((new SdApi()).queryCardMainStatus(iccid_number));
        String IMEIReRecordstr=(new SdApi()).IMEIReRecord(iccid_number);
        System.out.println(IMEIReRecordstr);
        //String re = (new SdApi()).queryTraffic(access_number, false);
//        long a = (new SdApi()).getOffNetQuota(access_number);
//        boolean isOFF = (new SdApi()).isOffNet(access_number);
//        String status = (new SdApi()).getCardStatusName(access_number);
//        long ab = (new SdApi()).getOffNetQuota(access_number);
        //System.out.println(a);
        //System.out.println((new SdApi()).queryCardMainStatus("1410367391865"));
    }
}
