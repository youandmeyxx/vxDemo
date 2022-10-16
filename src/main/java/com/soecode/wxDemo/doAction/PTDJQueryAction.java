package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.doAction.support.BaseAction;
import com.soecode.wxDemo.pojo.CardInfo;
import com.soecode.wxDemo.telecom.chinatelecom.NtApi;
import com.soecode.wxDemo.telecom.chinatelecom.SdApi;
import com.soecode.wxDemo.utils.DateUtil;
import com.soecode.wxDemo.utils.DbHelper;
import com.soecode.wxDemo.utils.TemporyTaskCaller;
import com.soecode.wxDemo.utils.TemporyTaskThread;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class PTDJQueryAction extends BaseAction {

    static String openid = "";
    static String nickname = "";
    static String iccid = "";
    static String djpt = "";
    static String SDDX="1"; //山东电信
    static String NTDX="27";//南通电信

    static String packageName=" ";
    static String inValidTime="";
    static double usedFlow=0;
    static double leftFlow=0;
    static double usedRate=0;
    static String imei="" ;
    static String msg="";

    public String doAction() throws Exception {
        clearAll();
        openDb();
        System.out.println("doAction" + iccid);
        msg="";
        if(!checkICCID(iccid))
        {
            System.out.println("ICCIDI_INVALID");
            if (msg.equals("")) {
                msg = "ICCIDI_INVALID";
            }

//            return "ICCIDI_INVALID"; //如过iccid非法直接返回
        }else {
            msg="";
            (new PTDJQueryAction.RunCaller()).call(3, new Object[]{iccid, this});
        }
        closeDb();
        //跳转页面
        String testUrl="/comment.jsp?openid=" + openid + "&nickname="+ nickname+"&iccid=" + iccid + "&packageName=" +packageName+"&inValidTime="+inValidTime+"&usedFlow=" +usedFlow+ "&leftFlow=" +leftFlow + "&imei=" +imei + "&usedRate=" + usedRate + "&msg=" + msg ;
        System.out.println(testUrl);
        System.out.println("卡信息查询入口跳转");
        request.getRequestDispatcher(testUrl).forward(request,response);
        return "SUCCESS";
    }

    private ResultSet getCardinfo(String iccid) throws SQLException {
        String sql="select * from card_info where iccid_code='" + iccid +  "'";
        ResultSet rs= db.executeQuery(sql);
        return rs;
    }
    public PTDJQueryAction(){
        super();
    }

    //查询拿到iccid后先查accessNo，再调用api 查询 群组好  然后根据群组号 得到 平台号 再查询平台号
    public PTDJQueryAction(HttpServletRequest vrequest, HttpServletResponse vresponse,String vopenid , String vnickname) throws IOException {
        super(vrequest,vresponse);
        iccid = vrequest.getParameter("iccid");
        System.out.println("vrequest.getParametericcid" + iccid);
        openid=vopenid;
        nickname=vnickname;
    }

    public void clearAll(){
        packageName=" ";
        inValidTime="";
        usedFlow=0;
        leftFlow=0;
        usedRate=0;
        imei="" ;
    }


    public String getUsedFlow(String iccid) throws Exception{
        //todo 读取当月使用量
        String accessCode="";
        String sourceId = "";
        //查询拿到iccid后先查accessNo
        String sql="select * from card_Info_ptdj where ICCID_CODE='" + iccid + "'";
        System.out.println("tarafficSQL:"+ sql);
        ResultSet rs= db.executeQuery(sql);
        String taraffic="";
        //要先判断哪个卡源的
        while (rs.next()){
            accessCode = rs.getString("ACCESS_CODE");
            sourceId = rs.getString("SOURCE_ID");
        }
        if(sourceId.equals(SDDX)) {
            taraffic = (new SdApi()).queryTraffic(accessCode,false);
        }else if (sourceId.equals(NTDX)){
            taraffic = (new NtApi()).queryTraffic(accessCode,false);
        }
        System.out.println("taraffic:"+ taraffic);
        return taraffic;
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
        if(proNetNo==null)
        {
            String updateSql="update card_Info_ptdj set PTID =0, PRO_NET_NO=null where ICCID_CODE ='" + iccid + "'";
            System.out.println(updateSql);
            db.execute(updateSql);
        }else {
            if(rs1.next()) {//如果群号
                ptid = rs1.getString("PTID");
                //更新 card_info_ptdj 的群组号 平台号
                String updateSql = "update card_Info_ptdj set PTID = " + ptid + ", PRO_NET_NO='" + proNetNo + "' where ICCID_CODE ='" + iccid + "'";
                System.out.println(updateSql);
                db.execute(updateSql);
            }else{
                String updateSql="update card_Info_ptdj set PTID =0, PRO_NET_NO=null where ICCID_CODE ='" + iccid + "'";
                System.out.println(updateSql);
                db.execute(updateSql);
            }

        }
        System.out.println("**********************ptid:" +ptid );
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
    public void getFlowPackageInfo(String iccid) throws Exception {
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
            return ;
        }
        String cardid=cardinfoRs.getString("CARD_ID");
        String sqlrs1 ="select * from card_pooled_quota where CARD_ID=" + cardid;
        ResultSet rs1 =db.executeQuery(sqlrs1);
        String sqlrs2 ="select * from card_pooled_quota_history where CARD_ID=" + cardid;
        ResultSet rs2;
        String sqlrs3="select * from card_info_base where CARD_ID=" + cardid;
        ResultSet rs3;
        int packageid ;
        String  chengmoDate="";
        if(rs1.next()){
            System.out.println("sqlrs1"+sqlrs1);
            packageid= rs1.getInt("PACKAGE_ID");
            inValidTime=rs1.getString("INVALID_TIME");
//            usedFlow= rs1.getDouble("USED_FLOW");
//            leftFlow=rs1.getDouble("LEFT_FLOW");
        }else{
            rs2=db.executeQuery(sqlrs2);
            if(rs2.next()){
                System.out.println("sqlrs2"+sqlrs2);
                packageid=rs2.getInt("PACKAGE_ID");
                inValidTime= rs2.getString("INVALID_TIME");
            }else{
                System.out.println("sqlrs3"+sqlrs3);
                rs3=db.executeQuery(sqlrs3);
                if(rs3.next()) {
                    packageid = rs3.getInt("INITIAL_FLOW_PACKAGE_ID");
                    chengmoDate = rs3.getString("SILENT_LAST_DATE");
                }else
                { packageid=0;}
            }
        }
        String taraffic="";
        try {
            taraffic = getUsedFlow(iccid);
        }catch(Exception e){

        }
        if (taraffic.equals("")){
            taraffic="0";
        }

        ResultSet rs4;
        String sqlrs4="select * from card_flow_package where PACKAGE_ID=" + packageid;System.out.println("UsedFlow and leftFlow :" + usedFlow +  "and " +leftFlow);
        rs4 = db.executeQuery(sqlrs4);
        if(rs4.next()) {
            packageName = rs4.getString("PACKAGE_NAME");
            //判断是否有有有效期再判断是否有沉默期。
            if(inValidTime.equals("")) {
                inValidTime =DateUtil.formatter0().format(DateUtil.addMonth(DateUtil.parse(chengmoDate),rs4.getInt("PACKAGE_CONTINUE_PERIODS")));
            }
            usedFlow= Double.parseDouble(taraffic);
            leftFlow= (rs4.getDouble("FLOW_QUOTA")/rs4.getDouble("PACKAGE_CONTINUE_PERIODS"))/1024 - usedFlow;
            usedRate= (usedFlow/(rs4.getDouble("FLOW_QUOTA")/rs4.getDouble("PACKAGE_CONTINUE_PERIODS"))/1024)*100;
            System.out.println("UsedFlow and leftFlow :" + usedFlow +  "and " +leftFlow);
        }

        System.out.println("packageName  :" + packageName );
    }


    /**
     * 判断是否是ICCID 号码
     * @param ICCID
     * @return
     */

    private boolean checkICCID(String ICCID) throws SQLException {
        if(ICCID.trim().length()==19 || ICCID.trim().length()==20) {
            System.out.println(ICCID);
        }
        else {
            System.out.println(ICCID);
            return false;
        }
        ResultSet cardinfoRs= getCardinfo(ICCID);
        if(!cardinfoRs.next())
        {
            msg="该卡片不存在或未登记！";
            return false;
        }
        return true;
    }

    public static void main(String... strings) throws Exception {
//        PTDJQueryAction ptdj = new PTDJQueryAction();
//        ptdj.openDb();
//        String iccid="8986112222204929852";
//        System.out.println("开始运行主线程" + iccid);
//        (new PTDJQueryAction.RunCaller()).call(3, new Object[]{iccid, ptdj});
//        System.out.println("结束运行主线程");
//        ptdj.closeDb();

    }

    public static class RunCaller extends TemporyTaskCaller {
        public RunCaller() {
        }


        @Override
        public void createThreadToExecutionTask(int threadNum, ExecutorService executor, CountDownLatch currentMainThreadLatch, Object[] callerTranferdBusinessObjects) throws Exception {
                //读取群号
                for(int i = 1; i < threadNum+1 ; ++i) {
                    Runnable task1 = new PTDJQueryAction.RunThread(currentMainThreadLatch, this.appendToEndOfCallerTranferdBusinessObjects(i));
                    executor.execute(task1);
                }
            }
    }

    public static class RunThread extends TemporyTaskThread {
        public RunThread(CountDownLatch callerCountDownLatch, Object[] callerTranferdBusinessObjects) {
            super(callerCountDownLatch, callerTranferdBusinessObjects);
        }

        @Override
        public void doBusinessInThread(Object[] callerTranferdBusinessObjects) throws Exception {
            long doneSize=0;
            String iccid =(String)this.getBusinessObject(0);
            PTDJQueryAction ptdj=(PTDJQueryAction)this.getBusinessObject(1);
            int functNo=(int )this.getBusinessObject(2);
            this.logger.error("{} iccid",iccid);
            this.logger.error("{}开始运行", Thread.currentThread().getName());
            if(functNo==1){
                ptdj.getProNetNo(iccid);
            }else if(functNo==2){
//                ptdj.getIMEI(iccid);
            }else if(functNo==3){
                ptdj.getFlowPackageInfo(iccid);
            }
            this.logger.error("{}完成运行", Thread.currentThread().getName());
        }

        private long getDatePoorHour(Date nowDate, Date endDate) {
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = endDate.getTime() - nowDate.getTime();
            // 计算差多少小时
            long hour = diff % nd / nh;
            return hour;
        }

    }


}
