package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//    @Autowired
//    private ClientService clientService;


    public void start() {
        LOGGER.info("start... check Version");

        vertx.eventBus().consumer("version", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                System.out.println("<<<<<<<<<<<<<<<<<<<<<<[zzzzzzzzzzzzzzzzzz]>>>>>>>>>>>>>>>>>>");
                //semd message
               // JsonObject version = (JsonObject) message.body();
                //LOGGER.info("VERSION > " + version);
               // ClientVersionInfoDto infoDto = clientService.getLastClientVersionInfoDto(Integer.valueOf(1));
                message.reply(JSON.toJSONString("陈洋：19871014"));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }

}
