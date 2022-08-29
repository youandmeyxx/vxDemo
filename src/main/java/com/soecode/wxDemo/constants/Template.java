package com.soecode.wxDemo.constants;

public class Template{
    private TemplateData first;
    private TemplateData keyword1;
    private TemplateData keyword2;
    private TemplateData keyword3;
    private TemplateData remark;

    public Template(TemplateData first,TemplateData keyword1,TemplateData keyword2,TemplateData keyword3, TemplateData remark) {
        this.first = first;
        this.keyword1=keyword1;
        this.keyword2=keyword2;
        this.keyword3=keyword3;
        this.remark=remark;
    }

    public TemplateData getFirst() {
        return first;
    }

    public void setFirst(TemplateData first) {
        this.first = first;
    }

    public TemplateData getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(TemplateData keyword1) {
        this.keyword1 = keyword1;
    }

    public TemplateData getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(TemplateData keyword2) {
        this.keyword2 = keyword2;
    }

    public TemplateData getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(TemplateData keyword3) {
        this.keyword3 = keyword3;
    }

    public TemplateData getRemark() {
        return remark;
    }

    public void setRemark(TemplateData remark) {
        this.remark = remark;
    }


    public static class TemplateData{
        private String value;
        private String color;

        public TemplateData(String value,String color){
            this.value=value;
            this.color=color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

}