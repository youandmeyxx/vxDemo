import com.soecode.wxDemo.telecom.chinatelecom.NtApi;
import com.soecode.wxDemo.telecom.chinatelecom.SdApi;
import com.soecode.wxDemo.utils.Log4jV2Util;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;

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
 * @date 2021/12/15
 *
 */
@WebServlet("/api/Telecom/*")
public class apiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //实例化 统一业务API入口
    private IService iService = new WxService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 验证服务器的有效性
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的
        if ( baseUri.endsWith("/chongzhi") ) {
            //如果是登入请求，则进行什么操作
            System.out.println("这是充值url");
            return;
        }
        if ( baseUri.endsWith("/jiebang") ) {
            //如果是上传请求，则进行什么操作
            System.out.println("这是解绑url");
            return;
        }
    }


    //处理机卡解绑的post
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
//        request.setCharacterEncoding ("UTF-8");
//        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的
        PrintWriter out = response.getWriter();
        if ( baseUri.endsWith("/IMEIReRecord") ) {
            //如果是登入请求，则进行什么操作
            String  textcard=request.getParameter("textcard");
            Log4jV2Util.initLog4jV2TestEnv();

            try {
                String IMEIReRecordstr = (new SdApi()).IMEIReRecord(textcard);//电信解绑api
                Map<String, Object> IMEIReRecordstrMapsd = (HashMap<String, Object>)com.soecode.wxDemo.telecom.chinatelecom.util.JSONUtils.jsonParse(IMEIReRecordstr);
                String str;
                if(IMEIReRecordstrMapsd.get("RspType").toString().equals("0")) {
                    //处理完之后重定向
                    str = "/IMEIReRecordback.jsp?IMEIReRecordstrback= 你好 " + IMEIReRecordstrMapsd.get("RspDesc").toString();
                }else //山东通道解绑失败继续从南通通道处理
                {
                    IMEIReRecordstr = (new NtApi()).IMEIReRecord(textcard);//电信解绑api
                    Map<String, Object> IMEIReRecordstrMapnt = (HashMap<String, Object>)com.soecode.wxDemo.telecom.chinatelecom.util.JSONUtils.jsonParse(IMEIReRecordstr);
                    str = "/IMEIReRecordback.jsp?IMEIReRecordstrback= 你好 " + IMEIReRecordstrMapnt.get("RspDesc").toString();
                }
                System.out.println(str);
                str = new String(str.getBytes("utf-8"), "ISO-8859-1");
//                response.setContentType("text/html");
//                response.setCharacterEncoding("UTF-8");
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", str);


            }catch(RuntimeException de) {
                out.println(de.toString());
            }
            //writer.println("/api/Telecom/IMEIReRecordurl/" + textcard);
            //writer.flush();
            System.out.println("/api/Telecom/IMEIReRecordurl");
            return;
        }
        if ( baseUri.endsWith("/jiebang") ) {
            //如果是上传请求，则进行什么操作
            System.out.println("这是解绑url");
            return;
        }

        if(baseUri.endsWith("/pingtaidengji"))


        //如果它请求了一个咱们没有的接口，就返回404
        writer.println("Error: 404");
        writer.flush();
        return;
        //暂时省略，后面会讲到。
    }
}