package com.soecode.wxDemo;

import com.soecode.wxDemo.constants.MenuKey;
import com.soecode.wxDemo.constants.Template;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.TemplateSender;
import com.soecode.wxtools.bean.WxMenu;
import com.soecode.wxtools.bean.result.TemplateSenderResult;
import com.soecode.wxtools.exception.WxErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    public static void main(String[] args) {
//        addMenu();
        templateSenderTest();
    }

    private static void templateSenderTest()
    {
        String first="对讲机平台切换";
        String keyword1="受理";
        String keyword2="88888888";
        String keyword3="正常";
        String remark="谢谢合作！";
        IService iService = new WxService();
        TemplateSender sender = new TemplateSender();
        sender.setTouser("or0bLwVzu8m6EZU6HOk_nn4cS_4Y");
        sender.setTemplate_id("B1XXw0se3I7KtPAk74Ka-hXTWtzdsjI_9Qhl3-tBTaQ");

        Template template = new Template(first,keyword1,keyword2,keyword3,remark);
        sender.setData(template);
        sender.setUrl("url");
        try {
            TemplateSenderResult result = iService.templateSend(sender);
            System.out.println(result.toString());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }



    private static  void addMenu()
    {
        IService iService = new WxService();
        WxMenu menu = new WxMenu();
        List<WxMenu.WxMenuButton> btnList = new ArrayList<>();

        //飙升功能
        WxMenu.WxMenuButton btn1 = new WxMenu.WxMenuButton();
        btn1.setName("充值解绑");
        List<WxMenu.WxMenuButton> subList = new ArrayList<>();
        WxMenu.WxMenuButton btn1_1 = new WxMenu.WxMenuButton();
        btn1_1.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1_1.setKey(MenuKey.HOT_SONG);
        btn1_1.setUrl("http://wxkd.iot-chuanglin.com/iot/iotCeslogin.html");
        //btn1_1.setUrl("http://3813t60g36.zicp.vip/wx/chongzhi");
        btn1_1.setName("查询充值");

        WxMenu.WxMenuButton btn1_2 = new WxMenu.WxMenuButton();
        btn1_2.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1_2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx271bedf2ce298d26&redirect_uri=http://3813t60g36.zicp.vip/ptdj&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect");
        btn1_2.setKey(MenuKey.HUAYU_SONG);
        btn1_2.setName("平台登记5");

        WxMenu.WxMenuButton btn1_3 = new WxMenu.WxMenuButton();
        btn1_3.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1_3.setUrl("http://wxdj.iot-chuanglin.com/");
        btn1_3.setKey(MenuKey.NET_HOT_SONG);
        btn1_3.setName("电信解绑");

        WxMenu.WxMenuButton btn1_4 = new WxMenu.WxMenuButton();
        btn1_4.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1_4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx271bedf2ce298d26&redirect_uri=http://3813t60g36.zicp.vip/ptdj/CardQuery&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect");
        btn1_4.setKey(MenuKey.PTDJ_CARDQUERY);
        btn1_4.setName("卡平台信息查询");



        WxMenu.WxMenuButton btn2 = new WxMenu.WxMenuButton();
        btn2.setType(WxConsts.MENU_BUTTON_CLICK);
        btn2.setKey(MenuKey.CHANGE_NEWS);
        btn2.setName("快速实名");
        List<WxMenu.WxMenuButton> subList2 = new ArrayList<>();

        WxMenu.WxMenuButton btn2_1 = new WxMenu.WxMenuButton();
        btn2_1.setType(WxConsts.MENU_BUTTON_VIEW);
        btn2_1.setKey(MenuKey.DIANXIN_SHIMIN);
        btn2_1.setUrl("http://uniteapp.ctwing.cn:9090/uapp/certifhtml/certif_entry.html?userCode=r/s9Tc00hjiKcR13MIjZHg==&passWord=9u8VuY1xbez6r6k/BBnLlw==&tmstemp=1626336529278");
        btn2_1.setName("电信实名");

        WxMenu.WxMenuButton btn2_2 = new WxMenu.WxMenuButton();
        btn2_2.setType(WxConsts.MENU_BUTTON_VIEW);
        btn2_2.setKey(MenuKey.YIDONG_SHIMIN);
        btn2_2.setUrl("https://ec.iot.10086.cn/service/wx/person/index");
        btn2_2.setName("移动实名");


        subList.addAll(Arrays.asList(btn1_1,btn1_2,btn1_3,btn1_4));
        btn1.setSub_button(subList);
        subList2.addAll(Arrays.asList(btn2_1,btn2_2));
        btn2.setSub_button(subList2);

        //将三个按钮设置进btnList
        btnList.add(btn1);
        btnList.add(btn2);
        //设置进菜单类
        menu.setButton(btnList);
        //调用API即可
        try {
            //参数1--menu  ，参数2--是否是个性化定制。如果是个性化菜单栏，需要设置MenuRule
            iService.createMenu(menu, false);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
    private static void delMenu()
    {
        IService iService = new WxService();
        try {
            iService.deleteMenu();
        } catch (WxErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        //如果是删除个性化菜单栏，需要传入MenuID
//        try {
//            iService.deleteMenu("MenuID");
//        } catch (WxErrorException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
