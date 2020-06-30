package com.example.global.util.sms;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SmsUtil {

    public static void main(String[] args) {
        sendSMS("15152609738", "example", "333", "5");
    }

    public static boolean sendSMS(String mobile, String platform, String code, String timeout) {
        boolean tag = false;
        String result;
        StringBuilder PATH = new StringBuilder(
                "http://api.sms.cn/sms/?ac=send&uid=localusername&pwd=3839ec5a418206dbf306c12c3d39e57a"
                        + "&template=100002&mobile=");
        PATH.append(mobile + "&content={\"code\":\"");
//        PATH.append(platform + "\",\"code\":\"");
//        PATH.append(code + "\",\"timeout\":\"");
//        PATH.append(timeout + "\"}");
        PATH.append(code + "\"}");
        try {
            URL url = new URL(PATH.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(9000);
            connection.setReadTimeout(9000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                InputStream isStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int len = isStream.read(buffer, 0, 1024);
                if (len != -1) {
                    result = new String(buffer, 0, len).trim();
                    JSONObject jsonResult = JSONObject.parseObject(result);
                    if (jsonResult.get("stat").equals("100")) {
                        System.out.println("发送成功");
                        tag = true;
                        return tag;
                    } else {
                        System.out.println("发送失败");
                    }
                }
            }
        } catch (IOException e) {
            //网络连接超时
            e.printStackTrace();
        }
        return tag;
    }
}
