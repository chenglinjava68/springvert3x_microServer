package com.solodream;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.HttpUtils;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.SmsRequestParam;
import com.solodream.spring.vertx.req.client.TokenRequestParam;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 15/12/31.
 */
public class LoginServerTest {
    @Test
    public void testRegister() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserLoginRequestParam> request = new BaseReq<UserLoginRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserLoginRequestParam param = new UserLoginRequestParam();
        param.setDeviceId("123456788");
        param.setUsername("13857185401");
//        param.setPassword("password");//
//        param.setSmsCode("12345");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://114.55.178.173:18080/register", headers, parameter));
    }


    @Test
    public void testLogin() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        BaseReq<UserLoginRequestParam> request = new BaseReq<UserLoginRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserLoginRequestParam param = new UserLoginRequestParam();
        param.setDeviceId("123456788");
        param.setUsername("13857175401");
//        param.setPassword("password");//
//        param.setSmsCode("12345");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://114.55.178.173:18080/user/login", headers, parameter));
    }

    @Test
    public void testSMS() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<SmsRequestParam> request = new BaseReq<SmsRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        SmsRequestParam param = new SmsRequestParam();
        param.setDeviceId("123456788");
        param.setMobile("13857175121");
//        param.setPassword("password");//
//        param.setSmsCode("12345");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://114.55.178.173:18080/sms", headers, parameter));
    }

    @Test
    public void testTopkey() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<SmsRequestParam> request = new BaseReq<SmsRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        SmsRequestParam param = new SmsRequestParam();
        param.setDeviceId("123456788");
        param.setMobile("13857175121");
//        param.setPassword("password");//
//        param.setSmsCode("12345");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://114.55.178.173:18080/topkey", headers, parameter));
    }




    @Test
    public void testGetAccessToken()throws  Exception{
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        BaseReq<TokenRequestParam> request = new BaseReq<TokenRequestParam>();
        request.setToken("");

        TokenRequestParam param = new TokenRequestParam();
        param.setRefreshToken("403b87da81e25c5e9cf8b091481464bb");

        request.setParam(param);
        String parameter = JSON.toJSONString(request);


        System.out.println(parameter);

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/client/getAccessToken/", headers, parameter));
    }
}
