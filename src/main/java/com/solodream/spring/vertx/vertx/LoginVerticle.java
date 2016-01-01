package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
import com.solodream.spring.vertx.service.ClientService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void start() {
        vertx.eventBus().consumer("login", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                JsonObject jsonString = (JsonObject) message.body();
                JsonReq<UserLoginRequestParam> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<UserLoginRequestParam>>() {
                });
                String username = obj.getParam().getUsername();
                String password = obj.getParam().getPassword();
                LOGGER.info("username is {},password is {}", username, password);
                ClientAccountInfoDto dto = clientService.login(username);
                if (dto != null && dto.getPassword().equals(password)) {
                    LOGGER.info("this {} login ", username);
                    message.reply(JSON.toJSONString(dto));
                } else {
                    LOGGER.info("this username or password is wrong");
                    message.fail(1201, "this username or password is wrong");
                }
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
