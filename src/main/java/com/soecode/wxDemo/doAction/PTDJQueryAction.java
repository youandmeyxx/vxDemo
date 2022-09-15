package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.doAction.support.BaseAction;
import com.soecode.wxDemo.pojo.CardInfo;
import com.soecode.wxDemo.telecom.chinatelecom.NtApi;
import com.soecode.wxDemo.telecom.chinatelecom.SdApi;
import com.soecode.wxDemo.utils.DbHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PTDJQueryAction extends BaseAction {

    static String openid = "";
    static String nickname = "";
    static String iccid = "";
    static String djpt = "";
    static String SDDX="1"; //山东电信
    static String NTDX="27";//南通电信

    static String packageName="";
    static String inValidTime="";
    static double usedFlow=0;
    static double leftFlow=0;
    static String imei="" ;


    private ResultSet getCardinfo(String iccid) throws SQLException {
        String sql="select * from card_info where iccid_code='" + iccid +  "'";
        ResultSet rs= db.executeQuery(sql);
        return rs;
    }


    //查询拿到iccid后先查accessNo，再调用api 查询 群组好  然后根据群组号 得到 平台号 再查询平台号
    public PTDJQueryAction(HttpServletRequest vrequest, HttpServletResponse vresponse,String vopenid , String vnickname) throws IOException {
        super(vrequest,vresponse);
        iccid = vrequest.getParameter("iccid");
        System.out.println("vrequest.getParametericcid" + iccid);
        openid=vopenid;
        nickname=vnickname;

    }

    public String doAction() throws Exception {
        openDb();
        System.out.println("doAction" + iccid);
        if(!checkICCID(iccid))
        {
            System.out.println("ICCIDI_INVALID");
            closeDb();
            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }
        getProNetNo(iccid);
        getIMEI(iccid);
        getFlowPackageInfo(iccid);
        closeDb();
        //跳转页面
        String testUrl="/CardQuery.jsp?openid=" + openid + "&nickname="+ nickname+"&iccid=" + iccid + "&packageName=" +packageName+"&inValidTime="+inValidTime+"&usedFlow=" +usedFlow+ "&leftFlow=" +leftFlow + "&imei=" +imei ;
        System.out.println(testUrl);
        System.out.println("卡信息查询入口跳转");
        request.getRequestDispatcher(testUrl).forward(request,response);
        return "SUCCESS";
    }

    public void getProNetNo(String iccid) throws Exception {
        String accessCode="";
        String sourceId = "";
        //查询拿到iccid后先查accessNo
        String sql="select * from card_Info_ptdj where ICCID_CODE='" + iccid + "'";
        ResultSet rs= db.executeQuery(sql);
        int rowCount = 0;
        while (rs.next()){
            accessCode = rs.getString("ACCESS_CODE");
            sourceId = rs.getString("SOURCE_ID");
        }
        //根据accessCode 查询群组号
        String proNetNo="";
        if(!accessCode.equals("")){
            CardInfo cardinfo= new CardInfo();
            cardinfo.setAccessCode(accessCode);
            //要先判断哪个卡源的
            if(sourceId.equals(SDDX)) {
                proNetNo = (new SdApi()).getCardIpPomainName(cardinfo);
            }else if (sourceId.equals(NTDX)){
                proNetNo = (new NtApi()).getCardIpPomainName(cardinfo);
            }
            System.out.println("群组号：" + proNetNo);
        }
        //根据群组号查询平台编号
        String ptid="";
        String sql1="select * from card_ptdj_pronetno where SOURCE_ID='" + sourceId + "' and  PRO_NET_NO='" + proNetNo + "'";
        System.out.println(sql1);
        ResultSet rs1= db.executeQuery(sql1);
        while (rs1.next()){
            ptid= rs1.getString("PTID");
            System.out.println(ptid);
            //更新 card_info_ptdj 的群组号 平台号
            String updateSql="update card_Info_ptdj set PTID = " + ptid + ", PRO_NET_NO='" + proNetNo + "' where ICCID_CODE ='" + iccid + "'";
            System.out.println(updateSql);
            db.execute(updateSql);
        }
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
            System.out.println("sqlrs1"+sqlrs1);
            packageid= rs1.getInt("PACKAGE_ID");
            inValidTime=rs1.getString("INVALID_TIME");
            usedFlow= rs1.getDouble("USED_FLOW");
            leftFlow=rs1.getDouble("LEFT_FLOW");
        }else{
            rs2=db.executeQuery(sqlrs2);
            if(rs2.next()){
                System.out.println("sqlrs2"+sqlrs2);
                packageid=rs2.getInt("PACKAGE_ID");
                inValidTime=rs2.getString("INVALID_TIME");
            }else{
                System.out.println("sqlrs3"+sqlrs3);
                rs3=db.executeQuery(sqlrs3);
                if(rs3.next()) {
                    packageid = rs3.getInt("INITIAL_FLOW_PACKAGE_ID");
                }else
                { packageid=0;}
            }
        }
        ResultSet rs4;
        String sqlrs4="select * from card_flow_package where PACKAGE_ID=" + packageid;
        rs4 = db.executeQuery(sqlrs4);
        if(rs4.next()) {
            packageName = rs4.getString("PACKAGE_NAME");
        }
    }








    /**
     * 判断是否是ICCID 号码
     * @param ICCID
     * @return
     */

    private boolean checkICCID(String ICCID){
        if(ICCID.trim().length()==19) {
            System.out.println(ICCID);
            return true;
        }
        else {
            System.out.println(ICCID);
            return false;
        }
    }

    public static void main(String... strings) throws Exception {
//        PTDJQueryAction ptdjQueryAction = new PTDJQueryAction("8986032141200956540");
//        String result = ptdjQueryAction.doAction();
//        System.out.println(result);
    }
}
