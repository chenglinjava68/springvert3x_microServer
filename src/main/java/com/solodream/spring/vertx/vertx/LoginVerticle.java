package com.solodream.spring.vertx.vertx;

import java.util.List;

import com.solodream.spring.vertx.common.SerializeUtil;
import com.solodream.spring.vertx.jpa.model.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
import com.solodream.spring.vertx.service.ClientService;
import com.solodream.spring.vertx.service.RedisCacheService;

import io.vertx.core.AbstractVerticle;

/**
 * LoginVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class LoginVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginVerticle.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private RedisCacheService redisCacheService;

    public void start() {
        vertx.eventBus().consumer("login", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                Contract contract=new Contract();
                contract.setId(1);
                contract.setName("Young");
                redisCacheService.putObject("123", contract);


                Contract result=(Contract)redisCacheService.getObject("123");
                System.out.println(result.getName());
                String jsonString = (String) message.body();

                JsonReq<UserLoginRequestParam> obj = JSON.parseObject(jsonString, new TypeReference<JsonReq<UserLoginRequestParam>>() {
                });
                LOGGER.info("username is {},password is {}", obj.getParam().getUsername(), obj.getParam().getPassword());
                LOGGER.info(">>>>>>>>>>>"+redisCacheService.get("admin")+"<<<<<<<<<<<");
                redisCacheService.put(obj.getParam().getUsername(), obj.getParam().getPassword());
                String account = (String) message.body();
                ClientAccountInfoDto resultval = new ClientAccountInfoDto();
                resultval.setAccount(account);
                boolean isExit = clientService.login(account);
                redisCacheService.addMessage(account, account);

                List<String> list = redisCacheService.listMessages(account);
                for (String str : list) {
                    LOGGER.info("value is " + str);
                }
                String json = json = "failure";
                if (isExit) {
                    json = JSON.toJSONString(result);
                }

                message.reply(json);
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
