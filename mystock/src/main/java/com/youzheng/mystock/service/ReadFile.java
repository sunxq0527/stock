package com.youzheng.mystock.service;

import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.entity.Y_Stock;
import com.youzheng.mystock.entity.Y_Vendor;
import com.youzheng.mystock.inteface.GoodsRepository;
import com.youzheng.mystock.inteface.VendorRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReadFile {

    public Map<String, Object> readFile(File file,GoodsRepository goodsRepository,VendorRepository vendorRepository) throws IOException {
        List<Y_Stock> list = new ArrayList<Y_Stock>();
        Map<String, Object> res = new HashMap<String, Object>();
        FileInputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        if (null == xssfSheet) {
            res.put("code", 4);
            res.put("data", list);
            return res;
        }
        int isNull = 0;
        System.out.println("--------------------ReadFile.readFile----------------------"+  xssfSheet.getLastRowNum() +"-------------------------------");
        for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
            Y_Stock stock = new Y_Stock();
            XSSFRow xssfRow = xssfSheet.getRow(i);
            String goods = xssfRow.getCell(0).toString().trim();
            SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date now = new java.util.Date();
            if (!"".equals(goods) || null != goods) {
                Y_Goods yGoods = goodsRepository.findByName(goods);
                stock.setGoods(yGoods);
            }else{
                res.put("code",5);
                res.put("data", list);
                return res;
            }
            String vendor = xssfRow.getCell(1).toString().trim();
            if (!"".equals(vendor) || null != vendor) {
                Y_Vendor yVendor = vendorRepository.findByName(vendor);
                stock.setVendor(yVendor);
            }else{
                res.put("code", 5);
                res.put("data", list);
                return res;
            }
            String name = xssfRow.getCell(2).toString().trim();
            if (!"".equals(name) || null != name) {
                stock.setName(name);
            }else{
                res.put("code", 5);
                res.put("data", list);
                return res;
            }
            String assetCoding = xssfRow.getCell(3).toString().trim();
            if (!"".equals(assetCoding) || null != assetCoding) {
                stock.setAssetCoding(assetCoding);
            }else{
                res.put("code", 5);
                res.put("data", list);
                return res;
            }
            String assetType = xssfRow.getCell(4).toString().trim();
            if (!"".equals(assetType) || null != assetType) {
                stock.setAssetType(assetType);
            }else{
                res.put("code", 5);
                res.put("data", list);
                return res;
            }
            String deviceCoding = xssfRow.getCell(5).toString().trim();
            if (!"".equals(deviceCoding) || null != deviceCoding) {
                stock.setDeviceCoding(deviceCoding);
            }else{
                res.put("code", 5);
                res.put("data", list);
                return res;
            }
            String discardedDate = xssfRow.getCell(6).toString().trim();
            if (!"".equals(discardedDate) || null != discardedDate) {
                stock.setDiscardedDate(discardedDate);
            }else{
                stock.setDiscardedDate("");
            }
            String company = xssfRow.getCell(7).toString().trim();
            if (!"".equals(company) || null != company) {
                stock.setCompany(company);
                stock.setState("0");
            }else{
                stock.setCompany("");
            }
            String depart = xssfRow.getCell(8).toString().trim();
            if (!"".equals(depart) || null != depart) {
                stock.setDepart(depart);
            }else{
                stock.setDepart("");
            }
            String configure = xssfRow.getCell(9).toString().trim();
            if (!"".equals(configure) || null != configure) {
                stock.setConfigure(configure);
            }else{
                stock.setConfigure("");
            }
            String context = xssfRow.getCell(10).toString().trim();
            if (!"".equals(context) || null != context) {
                stock.setContext(context);
            }else{
                stock.setContext("");
            }
            stock.setNum(1);
            list.add(stock);
        }
        is.close();
        res.put("code", 0);
        res.put("data", list);
        return res;
    }
}
