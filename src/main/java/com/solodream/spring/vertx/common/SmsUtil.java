package com.solodream.spring.vertx.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class SmsUtil {
    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public static String sendSms(String mobile, String msg, String needstatus) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            // 目标地址

            HttpPost httppost = new HttpPost("http://sz.ipyy.com/sms.aspx?action=send&userid=&account=dddd&password=123456&mobile=13857175401&content=您好，您的验证码是111456【技协帮&sendTime=&extno=");
            // 构造最简单的字符串数据
            StringEntity reqEntity = new StringEntity("&msg=" + msg, "UTF-8");
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            reqEntity.setContentEncoding("UTF-8");
            // 设置请求的数据
            httppost.setEntity(reqEntity);
            // 执行
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity entity = httpresponse.getEntity();

        } catch (Exception e) {
            logger.error("sorry,the sms platform maybe is close.please contract the administrator");
        }
        return "";
    }

    public static void main(String args[]) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //sendSms("stlc_sms","Txb123456","13868031625","登陆验证码：","true");
        sendSms("13857175401", "heeloo", null);
        //System.out.println("dsfd");
    }
}
