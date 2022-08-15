
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soecode.wxDemo.doAction.PTDJSubmitAction;
import com.soecode.wxDemo.utils.HttpClientUtil;
import com.soecode.wxtools.api.WxConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    private  static  String APPID= WxConfig.getInstance().getAppId();//"wx69bdca8aa4b1ee37";//填你自己的
    private  static  String APPSECRET=WxConfig.getInstance().getAppSecret();//"66cb9a324a7f20c828957e3467583e63";//填你自己的

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 验证服务器的有效性
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的
        if ( baseUri.endsWith("/ptdj") ) {
            //如果是登入请求，则进行什么操作
            JSONObject usderinfoJSON =wxCallBack(request,response);
            String testUrl="/test.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&msg=";
            request.getRequestDispatcher(testUrl).forward(request,response);
            System.out.println(testUrl);
            return;
        }
        if ( baseUri.endsWith("/submit") ) {
            //TODO 如果是上传请求，则进行什么操作
            PTDJSubmitAction ptdjAction= new PTDJSubmitAction(request,response);
            ptdjAction.doAction();
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
            JSONObject usderinfoJSON =wxCallBack(request,response);
            String testUrl="/CardQuery.jsp?openid=" + usderinfoJSON.getString("openid") + "&nickname="+ usderinfoJSON.getString("nickname")+"&ICCID=";
            request.getRequestDispatcher(testUrl).forward(request,response);
            System.out.println(testUrl);
            System.out.println("卡信息查询入口跳转");
            return;
        }
        if(baseUri.endsWith("/CardQueryInfo")) //卡信息查询
        {
            //JSONObject usderinfoJSON =wxCallBack(request,response);

            request.setCharacterEncoding ("UTF-8");
            response.setCharacterEncoding ("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String openid=request.getParameter("openid");
            String nickname=request.getParameter("nickname");
            String ICCID=request.getParameter("ICCID");
            String testUrl="/CardQuery.jsp?openid=" + openid + "&nickname="+ nickname + "&ICCID=" + ICCID ;
            RequestDispatcher dispatcher = request.getRequestDispatcher(testUrl);
            dispatcher.forward(request,response);
            System.out.println(testUrl);
            System.out.println("卡信息查询");
        }

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