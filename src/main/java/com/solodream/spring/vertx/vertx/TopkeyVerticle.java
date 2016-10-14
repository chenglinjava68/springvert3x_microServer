package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.ProfileResponse;
import com.solodream.spring.vertx.resp.job.TopkeyResponse;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * VersionVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class TopkeyVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopkeyVerticle.class);

//    @Autowired
//    private ClientService clientService;


    public void start() {
        LOGGER.info("start... check topkey");

        vertx.eventBus().consumer("topkey", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                System.out.println("<<<<<<<<<<<<<<<<<<<<<<[zzzzzzzzzzzzzzzzzz]>>>>>>>>>>>>>>>>>>");

                BaseResp<TopkeyResponse> resp = new BaseResp<TopkeyResponse>();
                TopkeyResponse response = new TopkeyResponse();
                response.setTopkey("123,333,222");
                resp.setData(response);
                resp.setCode(0);
                message.reply(JSON.toJSONString(resp));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }

}
