
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soecode.wxDemo.doAction.*;
import com.soecode.wxDemo.doAction.support.BaseAction;
import com.soecode.wxDemo.utils.HttpClientUtil;
import com.soecode.wxtools.api.WxConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * apiServlet
 *
 * 处理电信移动的api接口
 * 因为对于全局是唯一的。采用单例模式。
 * </pre>
 *
 * @author zhangcao
 * @date 2021/12/18
 *
 */
@WebServlet("/ptdj/*")
public class routeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BaseAction baseAction= null ;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 验证服务器的有效性
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的

        if ( baseUri.endsWith("/jiebang") ) {
            //如果是登入请求，则进行什么操作
            baseAction= new BaseAction(request,response);
            JSONObject usderinfoJSON =baseAction.wxCallBack(request,response);
            String jiebangUrl="/index.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&msg=";
            request.getRequestDispatcher(jiebangUrl).forward(request,response);
            System.out.println(jiebangUrl);
            return;
        }

        if(baseUri.endsWith("/jiebangsubmit")){
            JieBangAction jieBangAction = new JieBangAction(request,response);
            try {
                jieBangAction.doAction();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //提示后跳转回登记页面
            System.out.println("平台登记");
            return;
        }

        if ( baseUri.endsWith("/ptdj") ) {
            //如果是登入请求，则进行什么操作

            baseAction= new BaseAction(request,response);
            JSONObject usderinfoJSON =baseAction.wxCallBack(request,response);
            String testUrl="/test.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&msg=";
            request.getRequestDispatcher(testUrl).forward(request,response);
            System.out.println(testUrl);
            return;
        }

        if (baseUri.endsWith("/directorderflow")){
            //TODO 直接充值通道，根据iccid 跳转充值通道
            String iccid=request.getParameter("iccid");
            String testUrl="http://front.iot-chuanglin.com/#/noBindInput";
//            request.getRequestDispatcher(testUrl).forward(request,response);
            System.out.println(testUrl);
        }
        if ( baseUri.endsWith("/submit") ) {
            //TODO 如果是上传请求，则进行什么操作
            PTDJSubmitAction ptdjAction= new PTDJSubmitAction(request,response);
            try {
                ptdjAction.doAction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //提示后跳转回登记页面
            System.out.println("平台登记");
            return;
        }

        if(baseUri.endsWith("/Query"))
        {
            request.setCharacterEncoding ("UTF-8");
            response.setCharacterEncoding ("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String openid=request.getParameter("openid");
            String nickname=request.getParameter("nickname");
            String testUrl="/Query.jsp?openid=" + openid + "&nickname="+ nickname;
            RequestDispatcher dispatcher = request.getRequestDispatcher(testUrl);
            dispatcher.forward(request,response);
            System.out.println(testUrl);
            System.out.println("平台查询");
            return;
        }
        if(baseUri.endsWith("/CardQuery")) //卡信息查询入口
        {
            //需要查询 当前套餐，IMEI，剩余流量，已使用流量，到期时间
            baseAction= new BaseAction(request,response);
            JSONObject usderinfoJSON =baseAction.wxCallBack(request,response);
            CardQueryAction cardQueryAction = new CardQueryAction(request,response ,usderinfoJSON.getString("openid"),usderinfoJSON.getString("nickname"));
            try {
                cardQueryAction.doAction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            String testUrl="/CardQuery.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&ICCID=";
//            request.getRequestDispatcher(testUrl).forward(request,response);
//            System.out.println(testUrl);
//            System.out.println("卡信息查询入口跳转");
            return;
        }

        if(baseUri.endsWith("/WorkOrderQuery"))
        {
            baseAction= new BaseAction(request,response);
            JSONObject usderinfoJSON =baseAction.wxCallBack(request,response);
            String testUrl="/WorkOrderQuery.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&ICCID=";
            request.getRequestDispatcher(testUrl).forward(request,response);
            System.out.println(testUrl);
            System.out.println("卡信息查询入口跳转");
            return;
        }

        if(baseUri.endsWith("/CardQueryInfo")) //卡信息查询
        {
            //JSONObject usderinfoJSON =wxCallBack(request,response);
            //取得 iccid 号 后需要查询 接入号然后

            request.setCharacterEncoding ("UTF-8");
            response.setCharacterEncoding ("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String ICCID=request.getParameter("iccid");
            System.out.println("CardQueryInfo iccid" + ICCID);
            //从运营商查询更新平台群组号。
            PTDJQueryAction ptdjQueryAction = new PTDJQueryAction(request,response ,request.getParameter("openid"),request.getParameter("nickname"));
            try {
                String result = ptdjQueryAction.doAction();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            String testUrl="/CardQuery.jsp?openid=" + openid + "&nickname="+ nickname + "&ICCID=" + ICCID ;
//            RequestDispatcher dispatcher = request.getRequestDispatcher(testUrl);
//            dispatcher.forward(request,response);
//            System.out.println(testUrl);
//            System.out.println("卡信息查询");
        }

//        if(baseUri.endsWith("/templatesender")) //发送模板信息
//        {
//            TemplateSenderAction templateSenderAction = new TemplateSenderAction("平台变更登记","受理","工单编号","服务情况","谢谢合作！","#173177" );
//            templateSenderAction.templateSend();
//        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的
            //如果它请求了一个咱们没有的接口，就返回404
            writer.println("Error: 404");
        writer.flush();
        return;
        //暂时省略，后面会讲到。
    }



}