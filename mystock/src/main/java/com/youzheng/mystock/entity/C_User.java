package com.youzheng.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class C_User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    private String name;

    private String sex;

    private String iPhone;

    private String isadmin;

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY,mappedBy = "users")
    @JsonIgnore
    private List<C_Role> roles;

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setiPhone(String iPhone) {
        this.iPhone = iPhone;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public void setRoles(List<C_Role> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getiPhone() {
        return iPhone;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public List<C_Role> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<C_Role> roles = this.getRoles();
        for (C_Role role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getText()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "C_User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", iPhone='" + iPhone + '\'' +
                ", isadmin='" + isadmin + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
