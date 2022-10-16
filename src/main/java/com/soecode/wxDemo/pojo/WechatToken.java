package com.soecode.wxDemo.pojo;



/**
 * @author zhangcao
 */

public class WechatToken {
    private long TOKEN_ID;
    private String APP_ID;
    private String APP_SECRET;
    private String ACCESS_TOKEN;
    private String GET_TIME;
    private String EXPIRES_IN;
    private long VERSION;
    private String DESC_CNT;

    public long getTOKEN_ID() {
        return TOKEN_ID;
    }

    public void setTOKEN_ID(long TOKEN_ID) {
        this.TOKEN_ID = TOKEN_ID;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPP_SECRET() {
        return APP_SECRET;
    }

    public void setAPP_SECRET(String APP_SECRET) {
        this.APP_SECRET = APP_SECRET;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public void setACCESS_TOKEN(String ACCESS_TOKEN) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
    }

    public String getGET_TIME() {
        return GET_TIME;
    }

    public void setGET_TIME(String GET_TIME) {
        this.GET_TIME = GET_TIME;
    }

    public String getEXPIRES_IN() {
        return EXPIRES_IN;
    }

    public void setEXPIRES_IN(String EXPIRES_IN) {
        this.EXPIRES_IN = EXPIRES_IN;
    }

    public long getVERSION() {
        return VERSION;
    }

    public void setVERSION(long VERSION) {
        this.VERSION = VERSION;
    }

    public String getDESC_CNT() {
        return DESC_CNT;
    }

    public void setDESC_CNT(String DESC_CNT) {
        this.DESC_CNT = DESC_CNT;
    }
}
