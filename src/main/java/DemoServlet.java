import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 * Demo Servlet
 *
 * 注意：WxConfig请调用getInstance()
 * 因为对于全局是唯一的。采用单例模式。
 * </pre>
 *
 * @author antgan
 * @date 2016/12/15
 *
 */
@WebServlet("/wx/*")
public class DemoServlet extends HttpServlet {
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
            //如果是登入请求，则进行什么操作D
            System.out.println("这是充值url");
            return;
        }
        if ( baseUri.endsWith("/jiebang") ) {
            //如果是上传请求，则进行什么操作
            System.out.println("这是解绑url");
            return;
        }
        if ( baseUri.endsWith("/ptdj") ) {
            //如果是登入请求，则进行什么操作
            request.getRequestDispatcher("/test.jsp").forward(request, response);
            System.out.println("这是平台等级url");
        }

        PrintWriter out = response.getWriter();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            out.print(echostr);
        }
    }
//解绑url
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        String baseUri = request.getRequestURI();//这个位置进行url判别，看到底是请求什么功能的
        if ( baseUri.endsWith("/IMEIReRecord") ) {
            //如果是登入请求，则进行什么操作
            System.out.println("IMEIReRecordurl");
            return;
        }
        if ( baseUri.endsWith("/jiebang") ) {
            //如果是上传请求，则进行什么操作
            System.out.println("这是解绑url");
            return;
        }

        //如果它请求了一个咱们没有的接口，就返回404
        PrintWriter writer = response.getWriter();
        writer.println("Error: 404");
        writer.flush();
        return;
        //暂时省略，后面会讲到。
    }
    public static void main(String[] args) {
        //测试链接数据库
//        delMenu();
    }


}