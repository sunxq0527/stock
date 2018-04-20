package com.youzheng.mystock.controller;

import com.youzheng.mystock.entity.Y_Stock;
import com.youzheng.mystock.inteface.BatchStock;
import com.youzheng.mystock.inteface.GoodsRepository;
import com.youzheng.mystock.inteface.VendorRepository;
import com.youzheng.mystock.service.ReadFile;
import com.youzheng.mystock.service.ResultMess;
import com.youzheng.mystock.service.UpLoadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {


    @Autowired
    private ReadFile readFile;

    @Autowired
    private UpLoadFile upLoadFile;

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private BatchStock batchStock;

    @RequestMapping(value = "/uploadList")
    public String upLoad(HttpServletRequest request) {
        return "/pages/batchStock/fileupload";
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "file";
        Map<String, Object> res = upLoadFile.upLoadFile(file, path);
        int code = (int) res.get("code");
        if (0 == code) {
            String fileName = (String) res.get("fileName");
            try {
                Map<String, Object> map = readFile.readFile(new File(path, fileName), goodsRepository, vendorRepository);
                code = (int) map.get("code");
                if (0 == code) {
                    List<Y_Stock> li = (List<Y_Stock>) map.get("data");
                    batchStock.save(li);
                    this.outPut("导入完成！", out);
                } else {
                    this.outPut(ResultMess.resultMess(code), out);
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.outPut(ResultMess.resultMess(3), out);
            }
        } else {
            this.outPut(ResultMess.resultMess(code), out);
        }

    }

    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletRequest request, HttpServletResponse res) {
        String fileName = "20180703.xlsx";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(FileUploadController.class.getResource("/").getPath() + "/templates/pages/batchStock/"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }

    private void outPut(String mes, PrintWriter out) {
        out.println("<script type='text/javascript'>");
        out.println("parent.callback('loadingDiv' ,'"+ mes+"');");
        out.println("</script>");
        out.flush();
        out.close();
    }
}

