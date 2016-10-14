package com.solodream;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.HttpUtils;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.JobRequestParam;
import com.solodream.spring.vertx.req.client.UserCommonRequestParam;
import com.solodream.spring.vertx.req.client.UserProfileRequestParam;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 16/10/10.
 */
public class ProfileServerTest {
    @Test
    public void testProfile() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserProfileRequestParam> request = new BaseReq<UserProfileRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserProfileRequestParam param = new UserProfileRequestParam();
        param.setAge("28");
        param.setBirthday("1987-10-23");
        param.setDisable("1");
        param.setEmail("cywhoyi@126.com");
        param.setExpectSalary("50000元");
        param.setId(1);
        param.setIdNo("33018519288231231232");
        param.setMobile("13857175401");
        param.setSex("0");
        param.setUserName("陈洋");
        param.setWorkArea("杭州");
        param.setWorkTime("2009-06-01");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/profile", headers, parameter));
    }

    @Test
    public void testProfileDetail() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserCommonRequestParam> request = new BaseReq<UserCommonRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserCommonRequestParam param = new UserCommonRequestParam();
        param.setUserId(1);
        param.setUserMobile("13857175491");
        param.setUserName("陈洋");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://114.55.178.173:18080/profile/detail", headers, parameter));
    }

    @Test
    public void testProfileDetail2() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserCommonRequestParam> request = new BaseReq<UserCommonRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserCommonRequestParam param = new UserCommonRequestParam();
        param.setUserId(1);
        param.setUserMobile("13857175491");
        param.setUserName("陈洋");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://localhost:18080/profile/detail", headers, parameter));
    }


}
