package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.ProfileResponse;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by young on 16/10/3.
 */
@Component
public class ProfileDetailVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDetailVerticle.class);

    public void start() {
        vertx.eventBus().consumer("profiledetail", message -> {
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
