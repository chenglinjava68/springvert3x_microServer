package com.solodream;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.HttpUtils;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.UserCommonRequestParam;
import com.solodream.spring.vertx.req.client.UserProfileRequestParam;
import com.solodream.spring.vertx.req.client.UserQuestionRequestParam;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 16/10/10.
 */
public class UserServerTest {
    @Test
    public void testRecords() throws Exception {
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

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/records", headers, parameter));
    }


    @Test
    public void testFavorites() throws Exception {
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

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/questions", headers, parameter));
    }
    @Test
    public void testUserQuestionRequestParam() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserQuestionRequestParam> request = new BaseReq<UserQuestionRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserQuestionRequestParam param = new UserQuestionRequestParam();
        param.setUserId(1);
        param.setUserMobile("13857175491");
        param.setUserName("陈洋");
        param.setQuestion("毛泽东是独裁?");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

//        System.out.println(HttpUtils.post("http://127.0.0.1:18080/questions", headers, parameter));
    }

}
