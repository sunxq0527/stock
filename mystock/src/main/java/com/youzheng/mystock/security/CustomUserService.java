package com.youzheng.mystock.security;

import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.inteface.LoginRepository;
import com.youzheng.mystock.inteface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        C_User user = loginRepository.findByUsername(s);
        if(null==user){
            throw new UsernameNotFoundException("用户不存在！");
        }
        System.out.println("--------------------CustomUserService.loadUserByUsername----------------------"+ s +"-------------------------------");
        System.out.println("--------------------CustomUserService.loadUserByUsername----------------------username="+ user.getUsername()  +"-------------------------------password=" + user.getPassword());
        return user;
    }
}
