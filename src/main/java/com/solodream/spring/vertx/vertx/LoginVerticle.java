package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.service.ClientService;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.AbstractVerticle;
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

    @Autowired
    private RedisCacheService redisCacheService;

    public void start() {
        LOGGER.info("start.");

        vertx.eventBus().consumer("login", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                redisCacheService.addMessage("cyp", "223");
                String account = (String) message.body();
                ClientAccountInfoDto result = new ClientAccountInfoDto();
                result.setAccount(account);

                boolean isExit = clientService.login(account);

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
