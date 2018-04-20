package com.youzheng.mystock.entity;

import javax.persistence.*;

@Entity
public class Y_Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //名称
    private String name;
    //型号
    @OneToOne
    private Y_Vendor vendor;
    //商品
    @OneToOne
    private Y_Goods goods;
    //数量
    private Integer num;
    //入库时间
    private String createTime;
    //操作人
    private Long createUser;
    //使用部门
    private String depart;
    //使用单位
    private String company;
    //资产编码
    private String assetCoding;
    //设备编码
    private String deviceCoding;
    //入库说明
    private String context;
    //状态
    private String state;
    //资产类型
    private String assetType;
    //报废日期
    private String discardedDate;
    //
    private String configure;

    public String getConfigure() {
        return configure;
    }

    public void setConfigure(String configure) {
        this.configure = configure;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Y_Vendor getVendor() {
        return vendor;
    }

    public Y_Goods getGoods() {
        return goods;
    }

    public Integer getNum() {
        return num;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public String getDepart() {
        return depart;
    }

    public String getCompany() {
        return company;
    }

    public String getAssetCoding() {
        return assetCoding;
    }

    public String getDeviceCoding() {
        return deviceCoding;
    }

    public String getContext() {
        return context;
    }

    public String getState() {
        return state;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getDiscardedDate() {
        return discardedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVendor(Y_Vendor vendor) {
        this.vendor = vendor;
    }

    public void setGoods(Y_Goods goods) {
        this.goods = goods;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAssetCoding(String assetCoding) {
        this.assetCoding = assetCoding;
    }

    public void setDeviceCoding(String deviceCoding) {
        this.deviceCoding = deviceCoding;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public void setDiscardedDate(String discardedDate) {
        this.discardedDate = discardedDate;
    }

    @Override
    public String toString() {
        return "Y_Stock{" +
                ", name='" + name + '\'' +
                ", vendor=" + vendor +
                ", goods=" + goods +
                ", num=" + num +
                ", createTime='" + createTime + '\'' +
                ", createUser=" + createUser +
                ", depart='" + depart + '\'' +
                ", company='" + company + '\'' +
                ", assetCoding='" + assetCoding + '\'' +
                ", deviceCoding='" + deviceCoding + '\'' +
                ", context='" + context + '\'' +
                ", state='" + state + '\'' +
                ", assetType='" + assetType + '\'' +
                ", discardedDate='" + discardedDate + '\'' +
                '}';
    }
}
