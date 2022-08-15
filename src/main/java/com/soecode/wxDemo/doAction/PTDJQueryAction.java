package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.pojo.CardInfo;
import com.soecode.wxDemo.telecom.chinatelecom.NtApi;
import com.soecode.wxDemo.telecom.chinatelecom.SdApi;
import com.soecode.wxDemo.utils.DbHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PTDJQueryAction {

    static String openid = "";
    static String nickname = "";
    static String iccid = "";
    static String djpt = "";
    static String SDDX="1"; //山东电信
    static String NTDX="27";//南通电信
    HttpServletRequest request;
    HttpServletResponse response;

    //查询拿到iccid后先查accessNo，再调用api 查询 群组好  然后根据群组号 得到 平台号 再查询平台号
    public PTDJQueryAction(HttpServletRequest vrequest, HttpServletResponse vresponse) {
        openid = vrequest.getParameter("openid");
        nickname = vrequest.getParameter("nickname");
        iccid = vrequest.getParameter("iccid");
        request = vrequest;
        response = vresponse;

    }
    public PTDJQueryAction(String varIccid){
        iccid = varIccid;
    }


    public String doAction() throws Exception {

        if(!checkICCID(iccid))
        {
            System.out.println("ICCIDI_INVALID");
            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }
        String accessCode="";
        String sourceId = "";
        DbHelper db= new DbHelper();
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

        return "SUCCESS";
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
        PTDJQueryAction ptdjQueryAction = new PTDJQueryAction("8986032141200956540");
        String result = ptdjQueryAction.doAction();
        System.out.println(result);
    }
}
