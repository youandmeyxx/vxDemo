package com.soecode.wxDemo.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardInfo implements Serializable {
    private Long cardId;
    private String iccidCode;
    private String accessCode;
    private String cardSource;
    private String customer;
    private String cardType;
    private String salesman;
    private BigDecimal cost;
    private BigDecimal salePrice;
    private String defPackage;
    private String salePackage;
    private String inputDate;
    private String createDate;
    private String outputDate;
    private String expiredDate;
    private Boolean ispayed;
    private String cardIp;
    private String apn;
    private String imei;
    private String proNetNo;
    private String descCnt;
    private String payDate;
    private String payCompany;
    private int sourceId;


    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getIccidCode() {
        return iccidCode;
    }

    public void setIccidCode(String iccidCode) {
        this.iccidCode = iccidCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getCardSource() {
        return cardSource;
    }

    public void setCardSource(String cardSource) {
        this.cardSource = cardSource;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getDefPackage() {
        return defPackage;
    }

    public void setDefPackage(String defPackage) {
        this.defPackage = defPackage;
    }

    public String getSalePackage() {
        return salePackage;
    }

    public void setSalePackage(String salePackage) {
        this.salePackage = salePackage;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getIspayed() {
        return ispayed;
    }

    public void setIspayed(Boolean ispayed) {
        this.ispayed = ispayed;
    }

    public String getCardIp() {
        return cardIp;
    }

    public void setCardIp(String cardIp) {
        this.cardIp = cardIp;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProNetNo() {
        return proNetNo;
    }

    public void setProNetNo(String proNetNo) {
        this.proNetNo = proNetNo;
    }
}