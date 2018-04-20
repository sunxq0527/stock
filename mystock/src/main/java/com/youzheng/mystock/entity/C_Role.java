package com.youzheng.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class C_Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<C_User> users;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<C_Menu> menus;
    @ManyToOne
    @JoinColumn(name = "role_parent")
    private C_Role pid;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private List<C_Role> children = new ArrayList<C_Role>();


    private String create_time;

    private Long create_user;

    private String state;

    private String isValid;

    @Value("${tree.isLeaf:0}")
    private String isLeaf;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<C_User> getUsers() {
        return users;
    }

    public List<C_Menu> getMenus() {
        return menus;
    }

    public C_Role getPid() {
        return pid;
    }

    public List<C_Role> getChildren() {
        return children;
    }

    public String getCreate_time() {
        return create_time;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public String getState() {
        return state;
    }

    public String getIsValid() {
        return isValid;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUsers(List<C_User> users) {
        this.users = users;
    }

    public void setMenus(List<C_Menu> menus) {
        this.menus = menus;
    }

    public void setPid(C_Role pid) {
        this.pid = pid;
    }

    public void setChildren(List<C_Role> children) {
        this.children = children;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    @Override
    public String toString() {
        return "C_Role{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", users=" + users +
                ", menus=" + menus +
                ", pid=" + pid +
                ", children=" + children +
                ", create_time='" + create_time + '\'' +
                ", create_user=" + create_user +
                ", state='" + state + '\'' +
                ", isValid='" + isValid + '\'' +
                ", isLeaf='" + isLeaf + '\'' +
                '}';
    }
}
