package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.jpa.domain.QuestionInfoDto;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.ProfileResponse;
import com.solodream.spring.vertx.resp.job.QuestionResponse;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class AskVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(AskVerticle.class);

    public void start() {
        vertx.eventBus().consumer("ask", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                BaseResp<ProfileResponse> resp = new BaseResp<ProfileResponse>();
                ProfileResponse response = new ProfileResponse();
                response.setUserid(1);
                resp.setData(response);
                resp.setCode(0);
                message.reply(JSON.toJSONString(resp));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
