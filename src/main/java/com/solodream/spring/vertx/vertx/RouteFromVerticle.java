package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.GetRouteDetailReq;
import com.solodream.spring.vertx.resp.poi.GetPoiListResp;
import com.solodream.spring.vertx.service.RouteService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by young on 16/1/1.
 */
@Component
public class RouteFromVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteFromVerticle.class);

    @Autowired
    private RouteService routeService;

    public void start() {
        vertx.eventBus().consumer("from", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                JsonObject jsonString = (JsonObject) message.body();
                BaseReq<GetRouteDetailReq> obj = JSON.parseObject(jsonString.toString(), new TypeReference<BaseReq<GetRouteDetailReq>>() {
                });


                GetPoiListResp response = routeService.from(obj.getParam());
                message.reply(JSON.toJSONString(response));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
