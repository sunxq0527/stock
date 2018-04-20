package com.youzheng.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * 商品
 */
@Entity
public class Y_Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String state;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy = "goods")
    @JsonIgnore
    private List<Y_Vendor> vendor;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Y_Vendor> getVendor() {
        return vendor;
    }

    public void setVendor(List<Y_Vendor> vendor) {
        this.vendor = vendor;
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
        return "Y_Goods{" +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
