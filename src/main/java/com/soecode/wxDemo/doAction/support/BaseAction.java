package com.soecode.wxDemo.doAction.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soecode.wxDemo.utils.DbHelper;
import com.soecode.wxDemo.utils.HttpClientUtil;
import com.soecode.wxtools.api.WxConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BaseAction {
    private  static  String APPID= WxConfig.getInstance().getAppId();//"wx69bdca8aa4b1ee37";//填你自己的
    private  static  String APPSECRET=WxConfig.getInstance().getAppSecret();//"66cb9a324a7f20c828957e3467583e63";//填你自己的
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected DbHelper db;
    public BaseAction(HttpServletRequest vrequest, HttpServletResponse vresponse) throws IOException {
        request = vrequest;
        response = vresponse;
    }

    protected void openDb(){
        db=new DbHelper();
    }
    protected void closeDb() throws SQLException {
        db.close();
    }


    public JSONObject wxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        //获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + APPID +
                "&secret=" + APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";

        String result = HttpClientUtil.doGet(url);

        System.out.println("请求获取access_token:" + result);
        //返回结果的json对象
        JSONObject resultObject = JSON.parseObject(result);

        //请求获取userInfo
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=" + resultObject.getString("access_token") +
                "&openid=" + resultObject.getString("openid") +
                "&lang=zh_CN";

        String resultInfo = HttpClientUtil.doGet(infoUrl);
        JSONObject usderinfoJSON = JSON.parseObject(resultInfo);
        //此时已获取到userInfo，再根据业务进行处理
        System.out.println("请求获取userInfo:" + resultInfo);

        return  usderinfoJSON;
    }
}
