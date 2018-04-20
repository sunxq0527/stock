package com.youzheng.mystock.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UpLoadFile {

    public Map<String,Object> upLoadFile(MultipartFile file, String path) {
        Map<String,Object> res = new HashMap<String,Object>();
        String  fileName = "";
        if (!file.isEmpty()) {
            try {
                String name = file.getOriginalFilename();
                String extName = name.substring(name.indexOf(".") + 1);
                if (!extName.toLowerCase().matches("xlsx")) {
                    res.put("code",1);
                    res.put("fileName",fileName);
                    return res;
                }
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String d = sdf.format(date);
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdir();
                }
                fileName = d + "." + extName;
                BufferedOutputStream out;
                out = new BufferedOutputStream(
                        new FileOutputStream(new File(f, fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                res.put("code",2);
                res.put("fileName",fileName);
                return res;
            } catch (IOException e) {
                res.put("code",3);
                res.put("fileName",fileName);
                return res;
            }
            res.put("code",0);
            res.put("fileName",fileName);
            return res;
        } else {
            res.put("code",4);
            res.put("fileName",fileName);
            return res;
        }
    }
}
