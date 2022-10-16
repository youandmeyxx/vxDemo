package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.doAction.support.BaseAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JieBangAction extends BaseAction {
    static String openid = "";
    static String nickname = "";
    static String iccid = "";
    static String djpt = "";
    static String SDDX="1"; //山东电信
    static String NTDX="27";//南通电信
    static String inValidTime="";
    static double usedFlow=0;
    static double leftFlow=0;
    static double usedRate=0;
    static String msg="";
    static String workOrderNo="";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    private ExecutorService executor = Executors.newCachedThreadPool();

    public JieBangAction(HttpServletRequest vrequest, HttpServletResponse vresponse){
        openid=vrequest.getParameter("openid");
        nickname=vrequest.getParameter("nickname");
        iccid=vrequest.getParameter("iccid");
        request=vrequest;
        response=vresponse;
        workOrderNo=getWorkOrderNo();
    }

        public String getWorkOrderNo()
        {
            return String.valueOf(System.currentTimeMillis());
        }

    public String doAction() throws Exception {
        executor.submit(new Runnable(){
            @Override
            public void run() {
                try {
                    //要执行的业务代码，我们这里没有写方法，可以让线程休息几秒进行测试
                    String str = dealICCIDString(iccid, openid, nickname,workOrderNo);
                    System.out.print("异步处理iccid列表~");
                }catch(Exception e) {
                    System.out.print("报错啦！！");
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
        //生成工单号
        String url="http://iotv3.iot-chuanglin.com/WorkOrderQuery.jsp?workOrderNo=" + workOrderNo;
        TemplateSenderAction templateSenderAction = new TemplateSenderAction("运营商解绑","受理",workOrderNo,"开始受理","谢谢合作！","#173177",openid,url);
        templateSenderAction.templateSend();
        //提示后跳转登记页面
        String str = "业务提交成功，开始受理！";
        String testUrl="/test.jsp?openid=" + openid + "&nickname="+ nickname + "&msg=" + str;
        System.out.println(testUrl);
        RequestDispatcher dispatcher = request.getRequestDispatcher(testUrl);
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        dispatcher.forward(request,response);
        return str;



    }

    public String dealICCIDString(String ICCID,String openid,String nickname,String workOrderNo) throws SQLException {
        openDb();
        String[] ICCIDArray=ICCID.split("\n");
        String info="";
        try {
            for (String strICCID : ICCIDArray) {
                int sICCID, bICCID;

                if (strICCID.contains("#")) {
                    System.out.println(strICCID);
                    int b = 0;
                    int e = 0;
                    String[] tpstr = strICCID.split("#");
                    sICCID = Integer.parseInt(tpstr[0].substring(15));
                    bICCID = Integer.parseInt(tpstr[1].substring(15));
                    String tp1 = tpstr[0].substring(0, 15);
                    String tp2 = tpstr[1].substring(0, 15);

                    System.out.println(sICCID + "  " + bICCID);
                    System.out.println(tp1 + "  " + tp2);
                    System.out.println(Math.abs(sICCID - bICCID));
                    //判断前后字串大小
                    if (Math.abs(sICCID - bICCID) < 100 && tp1.equalsIgnoreCase(tp2)) {
                        if (sICCID > bICCID) {
                            b = bICCID;
                            e = sICCID;
                            System.out.println(b + " 1 " + e);
                        } else {
                            b = sICCID;
                            e = bICCID;
                            System.out.println(b + " 2 " + e);
                        }
                    } else {
                        System.out.println(strICCID + " over number");
                        info = "数量过多！";
                        break;
                    }

                    for (int i = b; i <= e; i++) {
                        info = saveICCID(tp1 + String.valueOf(i), openid, nickname, workOrderNo);
                    }
                } else {
                    System.out.println("spliticcid:" + strICCID);
                    info = saveICCID(strICCID, openid, nickname, workOrderNo);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            info ="数据格式错误，请检查！";
//            return info ;
        }
//        templateSenderAction.templateSenderTest();
        if(info.equals("SUCCESS")){
            //todo 保存工单信息 发送受理模板信息
            String sql= " insert into card_ptdj_work_order (WORK_ORDER_NO,CREATE_TIME,STATE,FINISH_TIME,UPDATE_TIME,OPEN_ID,ORDER_TYPE,VERSION,DESC_CNT) values (";
            sql=sql+workOrderNo + ","; //WORK_ORDER_NO
            sql=sql+"'"+ df.format(new Date()) +"',"; //CREATE_TIME
            sql=sql+"0,"; //STATE
            sql=sql+"'',"; //FINISH_TIME
            sql=sql+"'" + df.format(new Date()) + "',"; //UPDATE_TIME
            sql=sql+"'" + openid + "',";
            sql=sql+"2,"; //ORDER_TYPE
            sql=sql+"0,"; //VERSION
            sql=sql+"''"; //DESC_CNT
            sql=sql+")";
            System.out.println(sql);
            try {
                db.execute(sql);
            }catch(Exception e){
                e.printStackTrace();
                info = "出现异常，执行失败！";
                closeDb();
//                return  info ;
            }
        }
        String url = "http://iotv3.iot-chuanglin.com/WorkOrderQuery.jsp?workOrderNo=" + workOrderNo;
        if(info.equals("SUCCESS")) {
            TemplateSenderAction templateSenderAction = new TemplateSenderAction("运营商解绑", "受理", workOrderNo, "受理成功", "谢谢合作！", "#173177", openid, url);
            templateSenderAction.templateSend();
        }else
        {
            TemplateSenderAction templateSenderAction = new TemplateSenderAction("运营商解绑", "受理", workOrderNo, "受理失败 " + info, "谢谢合作！", "#173177", openid, url);
            templateSenderAction.templateSend();
        }
        return info;
    }


    /**
     * 保存ICCID信息到数据库
     * @param ICCID
     * @param openid
     * @param nickname
     * @return
     */
    private String saveICCID(String ICCID,String openid,String nickname,String workOrderNo) throws SQLException {
        String info="";

        ICCID=ICCID.replaceAll("\n|\t|\r","");
        if(!checkICCID(ICCID))
        {
            System.out.println("ICCIDI_INVALID");
            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }

        df.format(new Date());
        int rowCount = 0;

        System.out.println("input Openid=" + openid);
                //TODO

        String sql="insert into card_jiebang (ICCID_CODE,OPEN_ID,NICK_NAME,JIEBANG_TIME,JIEBANG_SOURCE,IS_SUCCESS,REASON,WORK_ORDER_NO,VERSION,DESC_CNT) values ('" + ICCID + "','" + openid + "','" + nickname + "','" + JIEBANG_TIME + "'," + JIEBANG_SOURCE + ",0,'" + REASON + "','"+ workOrderNo +"',1,'" + DESC_CNT + "')";
        System.out.println(sql);
        try {
            db.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
            return "出现异常，执行失败！";
        }
        System.out.println("SUCCESS");
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
}
