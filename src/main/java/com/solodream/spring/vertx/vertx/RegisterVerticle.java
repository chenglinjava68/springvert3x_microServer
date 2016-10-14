package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.ProfileResponse;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * SmsVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class RegisterVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterVerticle.class);

    @Autowired
    private RedisCacheService redisCacheService;


    public void start() {
        LOGGER.info("start.");

        vertx.eventBus().consumer("register", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                //semd message
                JsonObject jsonString = (JsonObject) message.body();
                JsonReq<UserLoginRequestParam> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<UserLoginRequestParam>>() {
                });
                String username = obj.getParam().getUsername();
                String password = obj.getParam().getPassword();
                String smsCode = obj.getParam().getSmsCode();
                LOGGER.info("username is {},password is {}", username, password);

                BaseResp<ProfileResponse> resp = new BaseResp<ProfileResponse>();
                ProfileResponse response = new ProfileResponse();
                response.setUserid(1);
                resp.setData(response);
                resp.setCode(0);
                message.reply(JSON.toJSONString(resp));

//                if (redisCacheService.get("SMS_CODE_" + username).equals(smsCode)) {
//                    message.reply("success");
//                }
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }


}
