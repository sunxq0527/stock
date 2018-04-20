package com.youzheng.mystock.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.entity.Y_Stock;
import com.youzheng.mystock.entity.Y_Vendor;
import com.youzheng.mystock.inteface.GoodsRepository;
import com.youzheng.mystock.inteface.SeachRepository;
import com.youzheng.mystock.inteface.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.imageio.plugins.jpeg.JPEG.vendor;

@Controller
public class SeachController {

    @Autowired
    private SeachRepository seachRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;
    @RequestMapping(value = "/seach/innerList")
    public String innerList() {
        return "/pages/seach/innerStockList";
    }
    @RequestMapping(value = "/seach/outList")
    public String outList() {
        return "/pages/seach/outStockList";
    }


    @RequestMapping(value = "/seach/innerStock")
    @ResponseBody
    public String innerStock(int page, int rows, final String goods, final String vendor, final String startCreateTime, final String endCreateTime, final String deviceCoding, final String assetCodeing, final String assetType, final String name, final String state) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();

        Page<Y_Stock> pasgs = seachRepository.findAll(new Specification<Y_Stock>() {
            @Override
            public Predicate toPredicate(Root<Y_Stock> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (null != goods && !"".equals(goods)) {
                    final Y_Goods good = goodsRepository.findById(new Long(goods));
                    predicates.add(criteriaBuilder.equal(root.get("goods"), good.getId()));
                }
                if (null != vendor && !"".equals(vendor)) {
                    final Y_Vendor ven = vendorRepository.findById(new Long(vendor));
                    predicates.add(criteriaBuilder.equal(root.get("vendor"), ven.getId()));
                }
                if (null != startCreateTime && !"".equals(startCreateTime)) {

                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), startCreateTime));
                }
                if (null != endCreateTime && !"".equals(endCreateTime)) {
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime").as(String.class), endCreateTime));
                }
                if (null != deviceCoding && !"".equals(deviceCoding)) {
                    predicates.add(criteriaBuilder.equal(root.get("deviceCoding"), deviceCoding));
                }
                if (null != assetCodeing && !"".equals(assetCodeing)) {
                    predicates.add(criteriaBuilder.equal(root.get("assetCodeing"), assetCodeing));
                }
                if (null != assetType && !"".equals(assetType)) {
                    predicates.add(criteriaBuilder.equal(root.get("assetType"), assetType));
                }
                if (null != name && !"".equals(name)) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), name));
                }
                if (null != state && !"".equals(state)) {
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
}
