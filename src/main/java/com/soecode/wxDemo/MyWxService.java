package com.soecode.wxDemo;


import com.soecode.wxDemo.pojo.WechatToken;
import com.soecode.wxDemo.utils.DateUtil;
import com.soecode.wxDemo.utils.DbHelper;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConfig;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.TemplateSender;
import com.soecode.wxtools.bean.result.TemplateSenderResult;
import com.soecode.wxtools.exception.WxErrorException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author zhangcao
 */
public class MyWxService extends WxService {



    public MyWxService(){
        super();
    }
    protected DbHelper db;

    protected void openDb(){
        db=new DbHelper();
    }
    protected void closeDb() throws SQLException {
        if(db!=null) {
            db.close();
        }
    }
    @Override
    public String getAccessToken() throws WxErrorException {
        String token="";
        try {
        token= reflashAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("从服务器提取 access_token:"+ token);
        return token;
    }


    @Override
    public TemplateSenderResult templateSend(TemplateSender sender) throws WxErrorException {
        TemplateSenderResult result = null;
        String postResult = null;
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", this.getAccessToken());

        try {
            postResult = this.post(url, sender.toJson());
            result = TemplateSenderResult.fromJson(postResult);
            return result;
        } catch (IOException var6) {

            throw new WxErrorException("[wx-tools]templateSend failure.");
        }
    }

    //刷新公共token
    private String reflashAccessToken() throws WxErrorException, SQLException {
        openDb();
        WechatToken wechatToken = new WechatToken();
        String sql ="select TOKEN_ID,APP_ID,APP_SECRET,ACCESS_TOKEN,GET_TIME,EXPIRES_IN,VERSION,DESC_CNT from wechat_token where APP_ID='APPID'".replace("APPID",WxConfig.getInstance().getAppId());
        ResultSet rs= db.executeQuery(sql);
        //如果为空 或者超过1小时 则刷新token 保存token
        String token="";
        if(rs.next()) {
            token = rs.getString("ACCESS_TOKEN");
        }
        closeDb();
        return token;
    }

//    private WechatToken getAndFlashAccessToken() throws WxErrorException {
//        WechatToken wechatToken= new WechatToken();
//        //刷新Access_token
//        IService iService = new WxService();
//        wechatToken.setACCESS_TOKEN(iService.getAccessToken());
//        wechatToken.setAPP_ID(WxConfig.getInstance().getAppId());
//        wechatToken.setAPP_SECRET(WxConfig.getInstance().getAppSecret());
//        wechatToken.setGET_TIME(DateUtil.getDateStr(100));
//        wechatToken.setEXPIRES_IN(DateUtil.format(DateUtil.dateTimeAddSecond(DateUtil.parse(DateUtil.getDateStr(100)),7100),0));
//        wechatToken.setVERSION(1);
//        wechatToken.setDESC_CNT("对讲机公众号token");
//        cardPtdjMapper.insertWechatToken(wechatToken);
//        return wechatToken;
//    }
}
