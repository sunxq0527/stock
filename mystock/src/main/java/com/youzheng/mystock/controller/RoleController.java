package com.youzheng.mystock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzheng.mystock.entity.C_Menu;
import com.youzheng.mystock.entity.C_Role;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.inteface.MenuRepository;
import com.youzheng.mystock.inteface.RoleRepository;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/base/role/list")
    public String list() {
        return "/base/role/roleList";
    }

    @RequestMapping(value = "/base/role/menuList")
    public String menuList() {
        return "/base/role/menuList";
    }

    @RequestMapping(value = "/base/role")
    @ResponseBody
    public String roleList(String id) throws JsonProcessingException {
        List<C_Role> roleList = new ArrayList<C_Role>();
        List<C_Role> menus = new ArrayList<C_Role>();
        if (null == id) {
            id = "0";
            C_Role role = roleRepository.findById(new Long(id));
            roleList.add(role);
        } else {
            C_Role role = roleRepository.findById(new Long(id));
            menus = roleRepository.findByPidAndIsValid(role, "1");
            roleList.addAll(menus);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(roleList);
    }

    @RequestMapping(value = "/base/roleSave")
    public String saveRole(@ModelAttribute("C_Role") C_Role role, HttpServletRequest request) {
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yy/MM/dd HH:mm");
        C_Role roleParent = role.getPid();
        roleParent.setIsLeaf("0");
        roleParent.setState("closed");
        role.setPid(roleParent);
        Date now = new Date();
        HttpSession htteSession = request.getSession();
        C_User user = (C_User) htteSession.getAttribute("UserInfo");
        if (null == role.getPid()) {
            C_Role m = new C_Role();
            m.setId(0L);
            role.setPid(m);
        }
        role.setCreate_time(myFmt1.format(now));
        role.setCreate_user(user.getId());
        role.setState("open");
        role.setIsLeaf("1");
        role.setIsValid("1");
        roleRepository.save(role);
        return "redirect:/base/role/list";
    }

    @RequestMapping(value = "/base/validRole")
    @ResponseBody
    public String validRole(String id, HttpServletRequest request) throws JsonProcessingException {
        C_Role role = roleRepository.findById(new Long(id));
        List<C_Role> li = roleRepository.findByPidAndIsValid(role, "1");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("res", li.size());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(res);
    }

    @RequestMapping(value = "/base/delRole")
    @ResponseBody
    public String delRole(String id, HttpServletRequest request) throws JsonProcessingException {
        C_Role role = roleRepository.findById(new Long(id));
        role.setIsValid("0");
        roleRepository.save(role);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("res", 1);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(res);
    }

    @RequestMapping(value = "/base/roleMenu")
    @ResponseBody
    public String roleMenu(String role, String[] menus, HttpServletRequest request) throws JsonProcessingException {
        C_Role cRole = roleRepository.findById(new Long(role));
        List<Long> menu = new ArrayList<Long>();
        for (int i = 0; i < menus.length; i++) {
            menu.add(new Long(menus[i]));
        }
        List<C_Menu> list = new ArrayList<C_Menu>();

        List<C_Menu> li = menuRepository.findByIdIn(menu);
        Set<Long> set = this.getParent(li);
        List<Long> ms = new ArrayList<Long>(set);
        List<C_Menu> list1 = menuRepository.findByIdIn(ms);
        cRole.setMenus(list1);
        roleRepository.save(cRole);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("res", 1);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(res);
    }

    private Set<Long> getParent(List<C_Menu> menus){
        Set<Long> hs = new HashSet<Long>();
        for (C_Menu menu:menus) {
           if("1".equals(menu.getIsLeaf())) {
               if (menu.getPid().getId() == 0) {
                   hs.add(menu.getId());
               } else {
                   hs.add(menu.getId());
                   getParentId(menu.getPid(), hs);
               }
           }else{
               hs.add(menu.getId());
               List<C_Menu> childer = menuRepository.findByPid(menu);
               getChilderId(childer,hs);

           }
        }
        return hs;
    }

    private void getChilderId(List<C_Menu> childer,Set<Long> hs){
        for (C_Menu menu:childer) {
            if("1".equals(menu.getIsLeaf())){
                hs.add(menu.getId());
            }else{
                hs.add(menu.getId());
                List<C_Menu> childers = menuRepository.findByPid(menu);
                this.getChilderId(childers,hs);
            }
        }

    }
    private void getParentId(C_Menu menu,Set<Long> hs){
        if(menu.getPid().getId()==0){
            hs.add(menu.getId());
        }else{
            hs.add(menu.getId());
            getParentId(menu.getPid(),hs);
        }
    }
    @RequestMapping(value = "/role/userList")
    @ResponseBody
    public String userList(HttpServletRequest request, int page, int rows, String name) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, rows, null);
        Page<C_User> userList = userRepository.findAll(pageable);
        map.put("total", userList.getTotalElements());
        map.put("rows", userList.getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value="/role/roleUser")
    @ResponseBody
    public String userRole(String role, String[] users, HttpServletRequest request) throws JsonProcessingException {
        C_Role role1 = roleRepository.findById(new Long(role));
        List<Long> usersId = new ArrayList<Long>();
        for (String id:users) {
            usersId.add(new Long(id));
        }
        List<C_User> userList = userRepository.findByIdIn(usersId);
        role1.setUsers(userList);
        roleRepository.save(role1);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("res", 1);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(res);
    }
}
