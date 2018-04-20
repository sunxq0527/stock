package com.youzheng.mystock.service;

public class ResultMess {

    public static String resultMess(int code) {
        String message = "";
        switch (code) {
            case 0:
                message = "成功！";
                break;
            case 1:
                message = "文件不符！";
                break;
            case 2:
                message = "文件没有找到！";
                break;
            case 3:
                message = "读写错误！";
                break;
            case 4:
                message = "文件为空！";
                break;
            case 5:
                message = "数据不全！";
                break;
            default:
                message = "无结果";
                break;
        }
        return message;
    }
}
