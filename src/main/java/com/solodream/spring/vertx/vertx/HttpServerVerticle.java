package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hazelcast.util.MD5Util;
import com.solodream.spring.vertx.auth.SoloAuthProvider;
import com.solodream.spring.vertx.common.DateUtil;
import com.solodream.spring.vertx.common.ResultCode;
import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.SmsRequestParam;
import com.solodream.spring.vertx.req.client.TokenRequestParam;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.login.LoginResponse;
import com.solodream.spring.vertx.resp.login.SmsResponse;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Autowired
    private RedisCacheService redisCacheService;


    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route("/version").handler(
                req -> {
                    LOGGER.info("Received a http request to /client/getLastVersion");
                   // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("version", "", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        vertx.createHttpServer().requestHandler(router::accept).listen(7070);
        LOGGER.info("Started HttpServer(port=7070).");
    }
}