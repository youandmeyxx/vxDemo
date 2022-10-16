package com.soecode.wxDemo.doAction;

import com.alibaba.fastjson.JSONObject;
import com.soecode.wxDemo.doAction.support.BaseAction;
import com.soecode.wxDemo.telecom.chinatelecom.NtApi;
import com.soecode.wxDemo.telecom.chinatelecom.SdApi;
import com.soecode.wxDemo.utils.DbHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardQueryAction extends BaseAction {
    //需要查询
    //1.当前套餐
    //3.IMEI
    //4.剩余流量,已使用流量
    //5.到期时间
    static String openid = "";
    static String nickname = "";
    static String iccid = "";
    static String djpt = "";
    static String SDDX = "1"; //山东电信
    static String NTDX = "27";//南通电信
    static String packageName="";
    static String inValidTime="";
    static double usedFlow=0;
    static double leftFlow=0;
    static String imei="" ;
    static String msg="";

    private ResultSet getCardinfo(String iccid) throws SQLException {
        String sql="select * from card_info where iccid_code='" + iccid +  "'";
        ResultSet rs= db.executeQuery(sql);
        return rs;
    }
    public CardQueryAction(HttpServletRequest vrequest, HttpServletResponse vresponse,String vopenid,String vnickname) throws IOException {

        super(vrequest,vresponse);
        iccid = vrequest.getParameter("iccid");
        openid=vopenid;
        nickname=vnickname;
    }

    /**
     * 查询IMEI 通过api
     */
    public void getIMEI(String iccid) throws SQLException {
        //todo 查询IMSI 通过api
        //1.先建立电信api ok
        //2.根据iccid查询 access_code
        //3.根据access_code 调用api 查询 imei
        //4.保存到 request response中
        String accessCode="";
        ResultSet rs= getCardinfo(iccid);
        while (rs.next()) {
            accessCode = rs.getString("ACCESS_CODE");
        }
        if(!accessCode.equals("")){
           if( rs.getString("CARD_SOURCE").equals("1")){
               //山东电信
               SdApi tlApi = new SdApi();
               imei= tlApi.getOnlineStatus(accessCode);
           }else if(rs.getString("CARD_SOURCE").equals("27")){
               //南通电信
               NtApi tlApi = new NtApi();
               imei= tlApi.getOnlineStatus(accessCode);
           }
        }
    }

    /**
     * 查询剩余流量和已使用流量 和到期时间 和套餐信息
     */
    public void getFlowPackageInfo(String iccid) throws SQLException {
        //todo 查询剩余流量和已使用流量 和到期时间 和套餐信息
        //1.根据iccid得到cardid
        //2.根据cardid查询现有有效套餐
        //3.如果没有有效套餐查询过期套餐
        //4.如果没有过期套餐查询默认套餐
        //5.根据套餐编号查询套餐名称
        //6.得到套餐包后 得到有效期 和有效流量和 已使用流量。
        ResultSet cardinfoRs= getCardinfo(iccid);
        if(!cardinfoRs.next())
        {
            return;
        }
        String cardid=cardinfoRs.getString("CARD_ID");
        String sqlrs1 ="select * from card_pooled_quota where CARD_ID=" + cardid;
        ResultSet rs1 =db.executeQuery(sqlrs1);
        String sqlrs2 ="select * from card_pooled_quota_history where CARD_ID=" + cardid;
        ResultSet rs2;
        String sqlrs3="select * from card_info_base where CARD_ID=" + cardid;
        ResultSet rs3;
        int packageid ;
        if(rs1.next()){
            packageid= rs1.getInt("PACKAGE_ID");
             inValidTime=rs1.getString("INVALID_TIME");
             usedFlow= rs1.getDouble("USED_FLOW");
             leftFlow=rs1.getDouble("LEFT_FLOW");
        }else{
            rs2=db.executeQuery(sqlrs2);
            if(rs2.next()){
                packageid=rs2.getInt("PACKAGE_ID");
                inValidTime=rs2.getString("INVALID_TIME");

            }else{
                rs3=db.executeQuery(sqlrs3);
                packageid=rs3.getInt("INITIAL_FLOW_PACKAGE_ID");
            }
        }
        ResultSet rs4;
        String sqlrs4="select * from card_flow_package where PACKAGE_ID=" + packageid;
        rs4=db.executeQuery(sqlrs4);
        packageName=rs4.getString("PACKAGE_NAME");
    }

    /**
     * 执行action
     */
    public void doAction() throws SQLException, ServletException, IOException {
        //todo 执行action

        System.out.println(iccid);
        //跳转页面
//        String testUrl="/CardQuery.jsp?openid=" + openid + "&nickname="+ nickname+"&iccid=" + iccid + "&packageName=" +packageName+"&inValidTime="+inValidTime+"&usedFlow=" +usedFlow+ "&leftFlow=" +leftFlow + "&imei=" +imei ;
        String testUrl="/comment.jsp?openid=" + openid + "&nickname="+ nickname+"&iccid=" + iccid + "&packageName=" +packageName+"&inValidTime="+inValidTime+"&usedFlow=" +usedFlow+ "&leftFlow=" +leftFlow + "&imei=" +imei + "&msg=";
        System.out.println(testUrl);
        System.out.println("卡信息查询入口跳转");
        request.getRequestDispatcher(testUrl).forward(request,response);
    }


}
