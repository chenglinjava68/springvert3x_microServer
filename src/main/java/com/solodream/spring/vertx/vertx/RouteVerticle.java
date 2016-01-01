package com.solodream.spring.vertx.vertx;

import com.solodream.spring.vertx.service.RouteService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by young on 16/1/1.
 */
public class RouteVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteVerticle.class);

    @Autowired
    private RouteService routeService;

    public void start() {
        vertx.eventBus().consumer("from", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
//                JsonObject jsonString = (JsonObject) message.body();
//                JsonReq<UserLoginRequestParam> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<UserLoginRequestParam>>() {
//                });
//                String username = obj.getParam().getUsername();
//                String password = obj.getParam().getPassword();
//                LOGGER.info("username is {},password is {}", username, password);
//                ClientAccountInfoDto dto = clientService.login(username);
//                if (dto != null && dto.getPassword().equals(password)) {
//                    LOGGER.info("this {} login ", username);
//                    message.reply(JSON.toJSONString(dto));
//                } else {
//                    message.fail(1201, "this username or password is wrong");
//                }
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
