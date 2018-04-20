package com.youzheng.mystock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.entity.Y_Vendor;
import com.youzheng.mystock.inteface.GoodsRepository;
import com.youzheng.mystock.inteface.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厂商
 */
@Controller
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @RequestMapping(value = "/vendor/list",method = RequestMethod.GET)
    public String list(){
        return "/pages/vendor/vendorList";
    }

    @RequestMapping(value = "/vendor/vendorList")
    @ResponseBody
    public String vendorList(int page, int rows, String name, HttpServletRequest request) throws JsonProcessingException {
         Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, rows, null);
        Page<Y_Vendor> vendors = null;
        if("".equals(name)||null==name) {
            vendors = vendorRepository.findByState(pageable, "1");
        }else{
            vendors = vendorRepository.findByStateAndName(pageable, "1",name);
        }
        map.put("total", vendors.getTotalElements());
        map.put("rows", vendors.getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value="/vendor/addVendor")
    @ResponseBody
    public String vendorAdd(@ModelAttribute("Y_Vendor") Y_Vendor vendor,HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        vendor.setState("1");
        vendorRepository.save(vendor);
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);


    }

    @RequestMapping(value="/vendor/delVendor")
    @ResponseBody
    public String vendorDel(Long id,HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Y_Vendor vendor = vendorRepository.findById(id);
        vendor.setState("0");
        vendorRepository.save(vendor);
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value="/vendor/goods")
    @ResponseBody
    public String goods(HttpServletRequest request) throws JsonProcessingException {
        List<Y_Goods> list = goodsRepository.findByState("1");
        List<Map<String,Object>> li = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("id",0);
        map1.put("text","");
        li.add(map1);
        for (Y_Goods goods:list) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",goods.getId());
            map.put("text",goods.getName());
            li.add(map);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(li);
    }
}
