package com.solodream.spring.vertx.vertx;

import com.solodream.spring.vertx.jpa.domain.ClientVersionInfoDto;
import com.solodream.spring.vertx.service.ClientService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * VersionVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class VersionVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionVerticle.class);

    @Autowired
    private ClientService clientService;


    public void start() {
        LOGGER.info("start... check Version");

        vertx.eventBus().consumer("version", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                //semd message
                ClientVersionInfoDto infoDto = clientService.getLastClientVersionInfoDto((Integer) message.body());
                message.reply(infoDto);
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }

}
