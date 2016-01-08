package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.GetRouteDetailReq;
import com.solodream.spring.vertx.resp.poi.GetRouteList4JobResp;
import com.solodream.spring.vertx.service.SearchService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by young on 16/1/1.
 */
public class SearchVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchVerticle.class);

    @Autowired
    private SearchService searchService;

    public void start() {
        vertx.eventBus().consumer("from", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {
                JsonObject jsonString = (JsonObject) message.body();
                JsonReq<GetRouteDetailReq> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<GetRouteDetailReq>>() {
                });
                GetRouteList4JobResp response = searchService.search(obj.getParam());
                message.reply(JSON.toJSONString(response));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
