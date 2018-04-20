package com.youzheng.mystock.controller;

import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.inteface.LoginRepository;
import com.youzheng.mystock.inteface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("msg", "首页");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String msg = "";
        if("".equals(username)||null==username||"".equals(password)||null==password){
            msg = "用户名或密码为空！";
            model.addAttribute("msg", msg);
            return "redirect:/";
        }
        C_User user = loginRepository.findByUsername(username, password);
        if (null != user) {
            HttpSession session = request.getSession();
            session.setAttribute("UserInfo",user);
            return "index_easyui";
        }else{
            msg = "用户名或密码错误！";
            model.addAttribute("msg", msg);
            return "redirect:/";
        }
    }

    @RequestMapping(value="/loginOut",method=RequestMethod.GET)
    public String loginOut(HttpServletRequest request, Model model){
//        HttpSession session = request.getSession();
        C_User user = (C_User) request.getSession().getAttribute("UserInfo");
        if(null!=user){
            request.getSession().removeAttribute("UserInfo");
        }
        request.getSession().invalidate();
        return "redirect:/";
    }

}
