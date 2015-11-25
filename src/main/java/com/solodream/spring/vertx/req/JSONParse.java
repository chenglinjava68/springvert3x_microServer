package com.solodream.spring.vertx.req;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.req.client.DeviceRequestParam;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;

/**
 * Created by young on 15/11/25.
 */
public class JSONParse {
    public static void main(String[] args) {
        JsonReq<UserLoginRequestParam> reqparam = new JsonReq<UserLoginRequestParam>();
        reqparam.setToken("TOKEN12312");
        UserLoginRequestParam param = new UserLoginRequestParam();
        param.setUsername("chenyang");
        param.setPassword("123213");
        reqparam.setParam(param);
        String jsonString = JSON.toJSONString(reqparam);
        System.out.println(jsonString);


        JsonReq<DeviceRequestParam> obj = JSON.parseObject(jsonString, new TypeReference<JsonReq<DeviceRequestParam>>() {
        });

        System.out.println(obj.getParam().getImei());
    }
}
