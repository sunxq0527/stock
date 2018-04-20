package com.youzheng.mystock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.entity.Y_Stock;
import com.youzheng.mystock.entity.Y_Vendor;
import com.youzheng.mystock.inteface.GoodsRepository;
import com.youzheng.mystock.inteface.StockRepository;
import com.youzheng.mystock.inteface.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/stock/list")
    public String list(HttpServletRequest request) {
        return "/pages/stock/list";
    }

    @RequestMapping(value = "/stock/stock")
    public String stock(HttpServletRequest request) {
        return "/pages/stock/stockList";
    }

    @RequestMapping(value = "/stock/stockInfo")
    @ResponseBody
    public String outStock(int page, int rows, final String goods, final String vendor, final String startCreateTime, final String endCreateTime, final String deviceCoding, final String assetCodeing, final String assetType, final String name, final String state) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();

        Page<Y_Stock> pasgs = stockRepository.findAll(new Specification<Y_Stock>() {
            @Override
            public Predicate toPredicate(Root<Y_Stock> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (null != goods&&!"".equals(goods)) {
                    final Y_Goods good = goodsRepository.findById(new Long(goods));
                    predicates.add(criteriaBuilder.equal(root.get("goods"), good.getId()));
                }
                if (null != vendor&&!"".equals(vendor)) {
                    final Y_Vendor ven = vendorRepository.findById(new Long(vendor));
                    predicates.add(criteriaBuilder.equal(root.get("vendor"), ven.getId()));
                }
                if (null != startCreateTime&&!"".equals(startCreateTime)) {

                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), startCreateTime));
                }
                if (null != endCreateTime&&!"".equals(endCreateTime)) {
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime").as(String.class), endCreateTime));
                }
                if (null != deviceCoding&&!"".equals(deviceCoding)) {
                    predicates.add(criteriaBuilder.equal(root.get("deviceCoding"), deviceCoding));
                }
                if (null != assetCodeing&&!"".equals(assetCodeing)) {
                    predicates.add(criteriaBuilder.equal(root.get("assetCodeing"), assetCodeing));
                }
                if (null != assetType&&!"".equals(assetType)) {
                    predicates.add(criteriaBuilder.equal(root.get("assetType"), assetType));
                }
                if (null != name&&!"".equals(name)) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), name));
                }
                if (null != state&&!"".equals(state)) {
                    predicates.add(criteriaBuilder.equal(root.get("state"), state));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        }, new PageRequest(page - 1, rows, null));
        map.put("total", pasgs.getTotalElements());
        map.put("rows", pasgs.getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/stock/stockList")
    @ResponseBody
    public String stockList(HttpServletRequest request, int page, int rows) throws JsonProcessingException {
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        Query query = entityManager.createNativeQuery("select goods_id,count(id) as \"tot\",(select count(id) from y_stock where state ='2') as \"out\",(select count(id) from y_stock where state='1') as \"in\" from y_stock");
        List rowList = query.getResultList();
        Page<Y_Goods> pages = goodsRepository.findByState(new PageRequest(page - 1, rows, null), "1");
        List<Y_Goods> list = pages.getContent();
        for (Object row : rowList) {
            Object[] cells = (Object[]) row;
            for (Y_Goods goods : list) {
                if (cells[0].toString().equals(goods.getId().toString())) {
                    Map<String, Object> resMap = new HashMap<String, Object>();
                    resMap.put("id", goods.getId());
                    resMap.put("name", goods.getName());
                    resMap.put("tot", cells[1]);
                    resMap.put("out", cells[2]);
                    resMap.put("in", cells[3]);
                    res.add(resMap);
                }

            }
        }
        map.put("total", pages.getTotalElements());
        map.put("rows", res);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/stock/addStock")
    @ResponseBody
    public String addStock(@ModelAttribute("Y_Stock") Y_Stock stock,HttpServletRequest request) throws JsonProcessingException {
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date(stock.getCreateTime());
        java.util.Date device = new java.util.Date(stock.getDeviceCoding());
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession htteSession = request.getSession();
        C_User user = (C_User) htteSession.getAttribute("UserInfo");
        int num = stock.getNum();
        for (int i = 0; i < num; i++) {
            Y_Stock yStock = new Y_Stock();
            yStock.setNum(1);
            yStock.setGoods(stock.getGoods());
            yStock.setVendor(stock.getVendor());
            yStock.setAssetCoding(stock.getAssetCoding());
            yStock.setAssetType(stock.getAssetType());
            yStock.setCompany(stock.getCompany());
            yStock.setCreateTime(myFmt1.format(now));
            yStock.setCreateUser(stock.getCreateUser());
            yStock.setName(stock.getName());
            yStock.setContext(stock.getContext());
            yStock.setDeviceCoding(myFmt1.format(device));
            yStock.setState(stock.getState());
            yStock.setCreateUser(user.getId());
            stockRepository.save(yStock);

        }
        map.put("res", "1");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
}
