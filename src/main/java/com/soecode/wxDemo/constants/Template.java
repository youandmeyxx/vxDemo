package com.soecode.wxDemo.constants;

public class Template{
    private String first;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String remark;

    public Template(String first,String keyword1,String keyword2,String keyword3, String remark) {
        this.first = first;
        this.keyword1=keyword1;
        this.keyword2=keyword2;
        this.keyword3=keyword3;
        this.remark=remark;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}