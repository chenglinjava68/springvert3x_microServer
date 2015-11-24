package com.solodream.spring.vertx.vertx;

import com.solodream.spring.vertx.service.ClientService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TokenVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class TokenVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsVerticle.class);

    @Autowired
    private ClientService clientService;

    public void start() {
        LOGGER.info("start.");

        vertx.eventBus().consumer("token", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                //semd message

                message.reply("success");
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }

}