package com.soecode.wxDemo.doAction;

import com.soecode.wxDemo.constants.Template;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.TemplateSender;
import com.soecode.wxtools.bean.result.TemplateSenderResult;
import com.soecode.wxtools.exception.WxErrorException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;

public class TemplateSenderAction {
    private String firstStr="";
    private String keyword1Str="";
    private String keyword2Str="";
    private String keyword3Str="";
    private String remarkStr="";
    private String colorStr="";

    public TemplateSenderAction(String firstStr, String keyword1Str,String keyword2Str,String keyword3Str,String remarkStr,String colorStr ){
        this.firstStr=firstStr;
        this.keyword1Str=keyword1Str;
        this.keyword2Str=keyword2Str;
        this.keyword3Str=keyword3Str;
        this.remarkStr=remarkStr;
        this.colorStr=colorStr;
    }

    public void templateSend()
    {
        Template.TemplateData  first= new Template.TemplateData(firstStr,colorStr);
        Template.TemplateData keyword1 = new Template.TemplateData(keyword1Str,colorStr);
        Template.TemplateData keyword2 = new Template.TemplateData(keyword2Str,colorStr);
        Template.TemplateData keyword3 = new Template.TemplateData(keyword3Str,colorStr);
        Template.TemplateData remark = new Template.TemplateData(remarkStr,colorStr);
        IService iService = new WxService();
        TemplateSender sender = new TemplateSender();
        sender.setTouser("or0bLwVzu8m6EZU6HOk_nn4cS_4Y");
        sender.setTemplate_id("B1XXw0se3I7KtPAk74Ka-hXTWtzdsjI_9Qhl3-tBTaQ");

        Template template = new Template(first,keyword1,keyword2,keyword3,remark);
        sender.setData(template);
        sender.setUrl("url");
        try {
            System.out.println(sender.toJson());
            TemplateSenderResult result = iService.templateSend(sender);
            System.out.println(result.toString());
        }
        catch (WxErrorException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFirstStr() {
        return firstStr;
    }

    public void setFirstStr(String firstStr) {
        this.firstStr = firstStr;
    }

    public String getKeyword1Str() {
        return keyword1Str;
    }

    public void setKeyword1Str(String keyword1Str) {
        this.keyword1Str = keyword1Str;
    }

    public String getKeyword2Str() {
        return keyword2Str;
    }

    public void setKeyword2Str(String keyword2Str) {
        this.keyword2Str = keyword2Str;
    }

    public String getKeyword3Str() {
        return keyword3Str;
    }

    public void setKeyword3Str(String keyword3Str) {
        this.keyword3Str = keyword3Str;
    }

    public String getRemarkStr() {
        return remarkStr;
    }

    public void setRemarkStr(String remarkStr) {
        this.remarkStr = remarkStr;
    }

    public String getColorStr() {
        return colorStr;
    }

    public void setColorStr(String colorStr) {
        this.colorStr = colorStr;
    }
}
