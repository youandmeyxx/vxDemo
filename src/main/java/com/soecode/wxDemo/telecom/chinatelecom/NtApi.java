//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.soecode.wxDemo.telecom.chinatelecom;

import com.soecode.wxDemo.utils.Log4jV2Util;

public class NtApi extends ChinaTelApi { //南通电信
    public NtApi() {
    }

    String userId() {
        return "w0sw0Uq3cPnXt1wQfcI36amy0FBE162N";
    }

    String password() {
        return "5hB5w24zzQmCxWfu";
    }
//in8l97dAP
    String key1() {
        return "in8";
    }

    String key2() {
        return "l97";
    }

    String key3() {
        return "dAP";
    }

    int initPackageNo(){return 67 ;}


    public static void main(String... strings) throws Exception {
        Log4jV2Util.initLog4jV2TestEnv();
        // CardInfo cardinfo = new CardInfo();
        String iccid_number="8986112124403424538";

//        System.out.println((new NtApi()).queryCardMainStatus(iccid_number));
        String IMEIReRecordstr=(new NtApi()).IMEIReRecord(iccid_number);
        System.out.println("解绑返回：" + IMEIReRecordstr);

        // Log4jV2Util.initLog4jV2TestEnv();
//        CardInfo cardinfo = new CardInfo();
//        cardinfo.setCARD_SOURCE(CardSource.CHINA_TELECOM_SD);
//        Calendar cal=Calendar.getInstance();
//        int h=cal.get(Calendar.HOUR_OF_DAY);
//        cardinfo.setACCESS_CODE("1410366401670");
//
//        System.out.println(h);
//        System.out.println((new NtApi()).queryCardMainStatus("1410379947847"));
        //finallyCheckStopResume(cardDao,cardinfo);
        //String re = (new SdApi()).queryTraffic("1410366401710", false);
        //long offnet=ApiFacade.getOffNetQuota(cardinfo);
//        long sl=(new SdApi()).getOffNetQuota(cardinfo.getACCESS_CODE());
//        MonthMetaData result = (new SdApi()).queryMonthData(cardinfo);
//        long a = (new SdApi()).getOffNetQuota(cardinfo.getACCESS_CODE());
//        boolean isOFF = (new SdApi()).isOffNet(cardinfo.getACCESS_CODE());
//        String status = (new SdApi()).getCardStatusName(cardinfo.getACCESS_CODE());
//        long ab = (new SdApi()).getOffNetQuota(cardinfo.getACCESS_CODE());
//        Calendar cal=Calendar.getInstance();
//        int h=cal.get(Calendar.HOUR_OF_DAY);
//        System.out.println(gettoken());

    }



}
