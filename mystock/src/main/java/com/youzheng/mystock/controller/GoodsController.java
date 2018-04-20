package com.youzheng.mystock.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @RequestMapping(value = "/goods/list")
    public String list() {
        return "/pages/goods/goodsList";
    }

    @RequestMapping(value = "/goods/goodsList")
    @ResponseBody
    public String vendorList(int page, int rows, String name, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, rows, null);
        Page<Y_Goods> goods = goodsRepository.findByState(pageable, "1");
        map.put("total", goods.getTotalElements());
        map.put("rows", goods.getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/goods/vendor")
    @ResponseBody
    public String vendorList() throws JsonProcessingException {
        List<Y_Vendor> list =vendorRepository.findByState("1");
        List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", 0);
        map1.put("text", "");
        li.add(map1);
        for (Y_Vendor vendor : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", vendor.getId());
            map.put("text", vendor.getName());
            li.add(map);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(li);
    }

    @RequestMapping(value = "/goods/vendorCase")
    @ResponseBody
    public String vendorList(Long id) throws JsonProcessingException {

        Y_Goods goods = goodsRepository.findById(id);
        List<Y_Vendor> list = vendorRepository.findByGoodsAndState(goods, "1");
        List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", 0);
        map1.put("text", "");
        li.add(map1);
        for (Y_Vendor vendor : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", vendor.getId());
            map.put("text", vendor.getBrand() + "-" + vendor.getName());
            li.add(map);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(li);
    }

    @RequestMapping(value = "/goods/addGoods")
    @ResponseBody
    public String goodsAdd(@ModelAttribute("Y_Goods") Y_Goods goods, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        goods.setState("1");
        goodsRepository.save(goods);
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/goods/delGoods")
    @ResponseBody
    public String goodsDel(Long id, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Y_Goods goods = goodsRepository.findById(id);
        goods.setState("0");
        goodsRepository.save(goods);
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
}
