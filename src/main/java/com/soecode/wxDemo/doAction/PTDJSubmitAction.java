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
    static String workOrderNo="";
    HttpServletRequest request;
    HttpServletResponse response;
    DbHelper db= new DbHelper();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    public PTDJSubmitAction(){}

    public  PTDJSubmitAction(HttpServletRequest vrequest, HttpServletResponse vresponse)
    {
        openid=vrequest.getParameter("openid");
        nickname=vrequest.getParameter("nickname");
        iccid=vrequest.getParameter("iccid");
        djpt=vrequest.getParameter("djpt");
        request=vrequest;
        response=vresponse;
        workOrderNo=getWorkOrderNo();

    }

    public String doAction(){
        try {
            //生成工单号
            String str = dealICCIDString(iccid, openid, nickname, djpt,workOrderNo);
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
    public String dealICCIDString(String ICCID,String openid,String nickname,String DJPT,String workOrderNo) throws SQLException {
        String[] ICCIDArray=ICCID.split("\n");
        String info="";
        for (String strICCID : ICCIDArray){
            int sICCID,bICCID;

            if(strICCID.contains("#"))
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
                //判断前后字串大小
                if(Math.abs(sICCID-bICCID)<250 && tp1.equalsIgnoreCase(tp2) )
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
                    info =  "数量过多！";
                    return info;
                }

                for(int i=b;i<=e;i++)
                {
                    info = saveICCID(tp1+String.valueOf(i), openid, nickname, DJPT,workOrderNo);
                }
            } else {
                System.out.println("spliticcid:" + strICCID);
                info = saveICCID(strICCID, openid, nickname, DJPT,workOrderNo);
            }
        }
//        templateSenderAction.templateSenderTest();
        if(info.equals("SUCCESS")){
            //todo 保存工单信息 发送受理模板信息
            DbHelper db= new DbHelper();
            String sql= " insert into card_ptdj_work_order (WORK_ORDER_NO,CREATE_TIME,STATE,FINISH_TIME,UPDATE_TIME,VERSION,DESC_CNT) values (";
            sql=sql+workOrderNo + ","; //WORK_ORDER_NO
            sql=sql+"'"+ df.format(new Date()) +"',"; //CREATE_TIME
            sql=sql+"0,"; //STATE
            sql=sql+"'',"; //FINISH_TIME
            sql=sql+"'" + df.format(new Date()) + "',"; //UPDATE_TIME
            sql=sql+"0,"; //VERSION
            sql=sql+"''"; //DESC_CNT
            sql=sql+")";
            db.execute(sql);
            db.close();
            TemplateSenderAction templateSenderAction = new TemplateSenderAction("平台变更登记","受理",workOrderNo,"受理成功","谢谢合作！","#173177" );
            templateSenderAction.templateSend();
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
    private String saveICCID(String ICCID,String openid,String nickname,String PTID,String workOrderNo) throws SQLException {
        String info="";

        ICCID=ICCID.replaceAll("\n|\t|\r","");
        if(!checkICCID(ICCID))
        {
            System.out.println("ICCIDI_INVALID");
            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }
        if(PTID.equals("0")){
            System.out.println("平台选择错误!");
            return "平台选择错误!"; //如过iccid非法直接返回
        }

        df.format(new Date());
        //先查询数据库是否有这个ICCID 底下是否有此卡
        String sql="select * from card_info_ptdj where ICCID_CODE='" + ICCID + "'";
        ResultSet rs= db.executeQuery(sql);
        int rowCount = 0;
        while (rs.next()) { //如果有则只想while内的命令

            if(!(null ==rs.getString("OPEN_ID")))
            {
                System.out.println("input Openid=" + openid);
                System.out.println("database OPEN_ID=" + rs.getString("OPEN_ID").toString());
                System.out.println(rs.getString("OPEN_ID").toString().equals(openid));
                //TODO
                String  PTID_OLD="0";
                if(rs.getString("PTID")!=null)
                {PTID_OLD = rs.getString("PTID").toString();}
                String  SOURCE_ID= rs.getString("SOURCE_ID").toString();
                sql="insert into card_ptdj (ICCID_CODE,OPEN_ID,NICK_NAME,PTID,PTID_OLD,DJRQ,GXRQ,SOURCE_ID,IS_OK,VERSION,WORK_ORDER_NO) values ('" + ICCID + "','" + openid + "','" + nickname + "'," + PTID + "," + PTID_OLD + ",'" + df.format(new Date()) + "','" + df.format(new Date()) + "','" + SOURCE_ID + "',0,0,'"+ workOrderNo +"')";
                System.out.println(sql);
                db.execute(sql);
                System.out.println("SUCCESS");

                db.close();
                return "SUCCESS";
            }
            else if(rs.getString("OPEN_ID")==null || rs.getString("OPEN_ID").equals("") ) //未登记用户，登记一下
            {
                System.out.println("input Openid=" + openid);
                System.out.println("database OPEN_ID =null");
                //未登记用户，登记一下
                String updateSql="update card_info_ptdj set OPEN_ID='"+ openid + "' , NICK_NAME='" + nickname + "' where ICCID_CODE='" + ICCID + "'";
                System.out.println("updateSql=" + updateSql);
                db.execute(updateSql);
                //插入变更记录
                String  PTID_OLD="0";
                if(rs.getString("PTID")!=null) {PTID_OLD = rs.getString("PTID").toString();}
                String  SOURCE_ID= rs.getString("SOURCE_ID").toString();
                sql="insert into card_ptdj (ICCID_CODE,OPEN_ID,NICK_NAME,PTID,PTID_OLD,DJRQ,GXRQ,SOURCE_ID,IS_OK,VERSION) values ('" + ICCID + "','" + openid + "','" + nickname + "'," + PTID + "," + PTID_OLD + ",'" + df.format(new Date()) + "','" + df.format(new Date()) + "','" + SOURCE_ID + "',0,0)";
                System.out.println(sql);
                db.execute(sql);
                System.out.println("SUCCESS");
                db.close();
                return "SUCCESS";
            }
            else
            {
                //TODO 不在这个openid名下 返回保存失败信息
                System.out.println("OPENID_ERROR ");
                db.close();
                return "该卡号不在您的名下";
            }

        }//退出while 的话说明ICCID 不在库中
        db.close();
        System.out.println("该卡号不是本方运营卡号请和客服联系确认！");
        return "该卡号不是本方运营卡号请和客服联系确认！";
    }


    public String getWorkOrderNo()
    {
        return String.valueOf(System.currentTimeMillis());
    }

    public static void main(String[] args) throws SQLException {
        PTDJSubmitAction ptdjSubmitAction = new PTDJSubmitAction();
        ptdjSubmitAction.dealICCIDString("8986032143200865457","or0bLwVzu8m6EZU6HOk_nn4cS_4Y","张操","1",ptdjSubmitAction.getWorkOrderNo());
    }


}
