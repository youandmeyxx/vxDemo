package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.constants.Template;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.TemplateSender;
import com.soecode.wxtools.bean.result.TemplateSenderResult;
import com.soecode.wxtools.exception.WxErrorException;

public class TemplateSenderAction {
    public void templateSenderTest()
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
}
