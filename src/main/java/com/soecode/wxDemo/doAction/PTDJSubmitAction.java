package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.utils.DbHelper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PTDJSubmitAction {
    static String  openid= "";
    static String  nickname="";
    static String  iccid="";
    static String  djpt="";
    HttpServletRequest request;
    HttpServletResponse response;

    public  PTDJSubmitAction(HttpServletRequest vrequest, HttpServletResponse vresponse)
    {
        openid=vrequest.getParameter("openid");
        nickname=vrequest.getParameter("nickname");
        iccid=vrequest.getParameter("iccid");
        djpt=vrequest.getParameter("djpt");
        request=vrequest;
        response=vresponse;

    }

    public String doAction(){
        try {

            String str = dealICCIDString(iccid, openid, nickname, djpt);
            //提示后跳转登记页面

            String testUrl="/test.jsp?openid=" + openid + "&nickname="+ nickname + "&msg=" + str;
            System.out.println(testUrl);
            RequestDispatcher dispatcher = request.getRequestDispatcher(testUrl);
            request.setCharacterEncoding ("UTF-8");
            response.setCharacterEncoding ("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            dispatcher.forward(request,response);

            return str;
        }catch(SQLException se)
        {
            System.out.println(se.toString());
            return "SQLERROR";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOServletERROR";
        } catch (ServletException e) {
            e.printStackTrace();
            return "ServletError";
        }
    }

    /**
     *
     * @param ICCID
     * @param openid
     * @param nickname
     * @param DJPT
     * @return
     */
    private String dealICCIDString(String ICCID,String openid,String nickname,String DJPT) throws SQLException {
        String[] ICCIDArray=ICCID.split("\n");
        String info="";
        for (String strICCID : ICCIDArray){
            int sICCID,bICCID;

            if(strICCID.contains("#")) //#
            {
                System.out.println(strICCID);
                int b=0;
                int e=0;
                String[]tpstr = strICCID.split("#");
                sICCID=Integer.parseInt(tpstr[0].substring(15)) ;
                bICCID=Integer.parseInt(tpstr[1].substring(15)) ;
                String tp1=tpstr[0].substring(0,15);
                String tp2=tpstr[1].substring(0,15);

                System.out.println(sICCID + "  " + bICCID);
                System.out.println(tp1 + "  " + tp2);
                System.out.println(Math.abs(sICCID-bICCID));
                if(Math.abs(sICCID-bICCID)<2000 && tp1.equalsIgnoreCase(tp2) ) //判断前后字串大小
                {
                    if(sICCID>bICCID) {
                        b = bICCID;
                        e = sICCID;
                        System.out.println(b + " 1 " + e);
                    }
                    else{
                        b = sICCID;
                        e = bICCID;
                        System.out.println(b + " 2 " + e);
                    }
                }else {
                    System.out.println(strICCID + " over number");
                    info = strICCID + " over number";
                    return info;
                }

                for(int i=b;i<=e;i++)
                {
                    info = strICCID + " " + saveICCID(tp1+String.valueOf(i), openid, nickname, DJPT);
                }
            } else {
                info = strICCID + " " + saveICCID(strICCID, openid, nickname, DJPT);
            }
        }
        return info;
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

    /**
     * 保存ICCID信息到数据库
     * @param ICCID
     * @param openid
     * @param nickname
     * @return
     */
    private String saveICCID(String ICCID,String openid,String nickname,String DJPT) throws SQLException {
        if(!checkICCID(ICCID))
        {
            System.out.println("ICCIDI_INVALID");
            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }
        DbHelper db= new DbHelper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.format(new Date());
        //先查询数据库是否有这个ICCID 底下是否有此卡
        String sql="select * from card_ptdj where ICCID_CODE='" + ICCID + "'";
        ResultSet rs= db.executeQuery(sql);
        int rowCount = 0;
        while (rs.next()) { //如果有则只想while内的命令
            System.out.println("input Openid=" + openid);
            System.out.println("database OPEN_ID=" + rs.getString("OPEN_ID").toString());
            System.out.println(rs.getString("OPEN_ID").toString().equals(openid));
            if(rs.getString("OPEN_ID").toString().equals(openid))
            {
                //TODO 这个openid名下 只要更新
                sql="update card_ptdj set PTID='" + DJPT + "', GXRQ='" + df.format(new Date())  + "' where ICCID_CODE='" + ICCID + "'";
                System.out.println(sql);
                db.execute(sql);
                System.out.println("SUCCESS");
                return "SUCCESS";
            }
            else
            {
                //TODO 不在这个openid名下 返回保存失败信息
                System.out.println("OPENID_ERROR mytest");
                return "该卡号不在您的名下";
            }
        }//退出while 的话说明ICCID 不在库中

        sql="insert into card_ptdj (ICCID_CODE,OPEN_ID,NICK_NAME,DJPT,DJRQ,GXRQ) values ('" + ICCID + "','" + openid + "','" + nickname + "','" + DJPT + "','" + df + "','" + df + "')";
        db.execute(sql);
        System.out.println("SUCCESS");
        return "SUCCESS";
    }
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.format(new Date());
        System.out.println(df.format(new Date()));
    }


}
