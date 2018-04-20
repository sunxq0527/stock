package com.youzheng.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * 厂商
 */
@Entity
public class Y_Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*
    * 型号
    * */
    private String name;

    /*
    *厂商
    **/
    private String addr;

    private String brand;

    private String state;

    @ManyToOne
    private Y_Goods goods;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Y_Goods getGoods() {
        return goods;
    }

    public void setGoods(Y_Goods goods) {
        this.goods = goods;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Y_Vendor{" +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", brand='" + brand + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
