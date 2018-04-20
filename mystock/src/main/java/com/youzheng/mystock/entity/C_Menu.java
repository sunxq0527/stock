package com.youzheng.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.*;

@Entity
public class C_Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String url;

    private String create_time;

    private Long create_user;

    private String state;

    private String isValid;
    @Value("${tree.isLeaf:0}")
    private String isLeaf;

    @ManyToOne
    @JoinColumn(name="menu_parent")
    @JsonIgnore
    private C_Menu pid;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="pid")
    private List<C_Menu> children = new ArrayList<C_Menu>();

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY, mappedBy = "menus")
    @JsonIgnore
    private List<C_Role> roles = new ArrayList<C_Role>();

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public C_Menu getPid() {
        return pid;
    }

    public List<C_Menu> getChildren() {
        return children;
    }

    public List<C_Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }

    public void setPid(C_Menu pid) {
        this.pid = pid;
    }

    public void setChildren(List<C_Menu> children) {
        this.children = children;
    }

    public void setRoles(List<C_Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "C_Menu{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", create_time='" + create_time + '\'' +
                ", create_user=" + create_user +
                ", state='" + state + '\'' +
                ", isValid='" + isValid + '\'' +
                ", isLeaf='" + isLeaf + '\'' +
                ", pid=" + pid +
                ", children=" + children +
                ", roles=" + roles +
                '}';
    }
}
