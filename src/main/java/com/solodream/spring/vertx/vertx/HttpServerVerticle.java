package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.hazelcast.util.MD5Util;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
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

@Component
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Autowired
    private RedisCacheService redisCacheService;


    public void start() {

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>");
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

//        router.route("/*").handler(SoloAuthProvider.create(vertx, redisCacheService));
//        router.route("/*").handler(req -> {
//            LOGGER.info("Any requests to URI starting '/' require login");
//            // No auth required
//            req.next();
//        });

        // Create a JWT Auth Provider
        JWTAuth jwt = JWTAuth.create(vertx, new JsonObject()
                .put("keyStore", new JsonObject()
                        .put("type", "jceks")
                        .put("path", "keystore.jceks")
                        .put("password", "secret")));

        // protect the API
        router.route("/api/*").handler(JWTAuthHandler.create(jwt, "/api/newToken"));

        // this route is excluded from the auth handler
        router.get("/api/newToken").handler(ctx -> {
            ctx.response().putHeader("Content-Type", "text/plain");
            String generateToken = jwt.generateToken(new JsonObject(), new JWTOptions().setExpiresInSeconds(1 * 60));
            ctx.response().end(generateToken);
            redisCacheService.put("token", generateToken, 1 * 60);
        });
//        router.route().handler(CookieHandler.create());
//        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
//        router.route().handler(BodyHandler.create());
//        router.route("/*").handler(SoloAuthProvider.create(vertx));

//        router.route("/*").handler(req -> {
//           LOGGER.info("Any requests to URI starting '/' require login");
//            // No auth required
//            req.next();
//        });

        router.route("/client/login").handler(
                req -> {
                    JsonReq<UserLoginRequestParam> reqparam = new JsonReq<UserLoginRequestParam>();
                    reqparam.setToken("TOKEN12312");
                    UserLoginRequestParam param = new UserLoginRequestParam();
                    param.setUsername("chenyang");
                    param.setPassword("password");
                    reqparam.setParam(param);
                    String jsonString = JSON.toJSONString(reqparam);
                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("login", jsonString, ar -> {
                        if (ar.succeeded()) {
                            String userName = (String) ar.result().body();
//                            ReqHandle.setOperator(req.session(), (String) ar.result().body());
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().putHeader("Content-Type", "text/plain");
                            String generateToken = jwt.generateToken(new JsonObject(), new JWTOptions().setSubject(userName).setAlgorithm("HS256").setExpiresInSeconds(1 * 60));
                            req.response().end(generateToken);
                            //save one min
                            redisCacheService.put("token", generateToken, 1 * 60);
                            //save ten min
                            String refreshToken = MD5Util.toMD5String(generateToken);
                            redisCacheService.put(refreshToken, generateToken, 10 * 60);
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        router.get("/client/getAccessToken").handler(ctx -> {
            LOGGER.info("Received a http request,enter into /client/getAccessToken");
            String refreshToken = ctx.request().getParam("refreshToken");
            ctx.response().putHeader("Content-Type", "text/plain");
            String token = redisCacheService.get(refreshToken);
            if (token != null) {
                redisCacheService.put("token", token, 1 * 60);
                redisCacheService.put(refreshToken, refreshToken, 10 * 60);
            }
            ctx.response().end(token);
        });

        router.route("/client/getSmsCode").handler(
                req -> {
                    LOGGER.info("Received a http request,enter into /client/getSmsCode");
                    String name = req.request().getParam("username");
                    vertx.eventBus().send("sms", name, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        router.route("/client/getLastVersion").handler(
                req -> {
                    LOGGER.info("Received a http request to /client/getLastVersion");
                    JsonObject product = req.getBodyAsJson();
                    String version = "";
                    vertx.eventBus().send("version", product, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        router.route("/client/getDeviceId").handler(
                req -> {
                    LOGGER.info("Received a http request to /client/getDeviceId");
                    JsonObject device = req.getBodyAsJson();
                    vertx.eventBus().send("device", device, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        router.route("/token").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    String version = req.request().getParam("token");
                    vertx.eventBus().send("version", version, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        vertx.createHttpServer().requestHandler(router::accept).listen(18080);
        LOGGER.info("Started HttpServer(port=18080).");
    }
}