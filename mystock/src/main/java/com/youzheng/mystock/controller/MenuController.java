package com.youzheng.mystock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzheng.mystock.entity.C_Menu;
import com.youzheng.mystock.entity.C_Role;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.inteface.MenuRepository;
import com.youzheng.mystock.inteface.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/base/list")
    public String list() {
        return "/base/menu/menuList";
    }


    @RequestMapping(value = "/base/menu")
    @ResponseBody
    public String menuList(String id,String sourcePage,HttpServletRequest request) throws JsonProcessingException {
        List<C_Menu> menuList = new ArrayList<C_Menu>();
        List<C_Menu> menus = new ArrayList<C_Menu>();
        HttpSession htteSession = request.getSession();
        C_User user = (C_User) htteSession.getAttribute("UserInfo");

        if(null==id&&null==sourcePage){
            id="0";
            if("1".equals(user.getIsadmin())) {
                C_Menu menu = menuRepository.findById(new Long(id));
                menus = menuRepository.findByPid(menu);
            }else{
                List<C_Role> roleList = roleRepository.findByUsersId(user.getId());
                for (C_Role role:roleList) {
                    List<C_Menu> rList =role.getMenus();
                    for (C_Menu menu:rList) {
                        System.out.println("--------------------MenuController.menuList----------------------"+ menu.getPid().getId().toString() +"-------------------------------");
                        if(id.equals(menu.getPid().getId().toString())){
                            menus.add(menu);
                        }

                    }
                }

            }

            menuList.addAll(menus);
        }else if(null==id){
            id="0";
            C_Menu menu = null;

            menu = menuRepository.findById(new Long(id));

            menuList.add(menu);
        }else{
            if("1".equals(user.getIsadmin())) {
                C_Menu menu = menuRepository.findById(new Long(id));
                menus = menuRepository.findByPid(menu);
            }else{
                List<C_Role> roleList = roleRepository.findByUsersId(user.getId());
                for (C_Role role:roleList) {
                    List<C_Menu> rList =role.getMenus();
                    for (C_Menu menu:rList) {
                        System.out.println("--------------------MenuController.menuList----------------------"+ menu.getPid().getId().toString() +"-------------------------------");
                        if(id.equals(menu.getPid().getId().toString())){
                            menus.add(menu);
                        }

                    }
                }
            }
            menuList.addAll(menus);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(menuList);
    }

    @RequestMapping(value = "/base/menuSave")
    public String saveMenu(@ModelAttribute("C_Menu") C_Menu menu,HttpServletRequest request){
        SimpleDateFormat myFmt1=new SimpleDateFormat("yy/MM/dd HH:mm");
        C_Menu menuParent = menu.getPid();
        menuParent.setIsLeaf("0");
        menuParent.setState("closed");
        menu.setPid(menuParent);
        Date now=new Date();
        HttpSession htteSession = request.getSession();
        C_User user = (C_User) htteSession.getAttribute("UserInfo");
        if(null==menu.getPid()){
            C_Menu m = new C_Menu();
            m.setId(0L);
            menu.setPid(m);
        }
        menu.setCreate_time(myFmt1.format(now));
        menu.setCreate_user(user.getId());
        menu.setState("open");
        menu.setIsLeaf("1");
        menuRepository.save(menu);
        return "redirect:/base/list";
    }
}
