package com.soecode.wxDemo.telecom.chinatelecom;


public interface TelecomApi {
//    boolean initOperation(CardInfo var1) throws Exception;
//
//    boolean activateCard(CardInfo var1) throws Exception;
//
//    boolean disabledNumber(CardInfo var1) throws Exception;
//
//    boolean enableNumber(CardInfo var1) throws Exception;
//
//    boolean suspendAndKeepNumber(CardInfo var1) throws Exception;
//
//
//
//    long getStatus(CardInfo var1);
//
//    String locationRealTime(CardInfo var1) throws Exception;
//
//    String locationLastTime(CardInfo var1) throws Exception;

    String offNetAction(String var1, long var2);

    long getOffNetQuota(String var1) throws Exception;
}
