package com.youzheng.mystock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.youzheng.mystock.entity.C_Role;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.inteface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/list")
    public String list(HttpServletRequest request) {
        return "/base/user/userList";
    }

    @RequestMapping(value = "/user/userList")
    @ResponseBody
    public String userList(HttpServletRequest request, int page, int rows, String name) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, rows, null);
        Page<C_User> userList = null;
        if("".equals(name)||null==name) {
            userList = userRepository.findByState(pageable, "1");
        }else{
            userList = userRepository.findByStateAndName(pageable, "1",name);
        }
        map.put("total", userList.getTotalElements());
        map.put("rows", userList.getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/user/add")
    public String addUser(@ModelAttribute("C_User") C_User user) {
        user.setState("1");
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/delUser")
    @ResponseBody
    public String userDel(Long id, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        C_User user = userRepository.findById(id);
        user.setState("0");
        userRepository.save(user);
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
    @RequestMapping(value = "/user/editPassword")
    @ResponseBody
    public String usuePassUpdate(String password,String agPassword, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        C_User user = (C_User) session.getAttribute("UserInfo");
        if(password.equals(agPassword)){
            C_User user1 = userRepository.findById(user.getId());
            List<C_Role> li = user1.getRoles();
            user1.setRoles(li);
            user1.setPassword(password);
            userRepository.save(user1);
            map.put("res", "1");
        }else{
            map.put("res",0);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

}
