package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.auth.SoloAuthProvider;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.ReqHandle;
import com.solodream.spring.vertx.req.RequestThreadLocal;
import com.solodream.spring.vertx.req.client.UserLoginRequestParam;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
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


        router.route("/*").handler(SoloAuthProvider.create(vertx,redisCacheService));

        router.route("/*").handler(req -> {
           LOGGER.info("Any requests to URI starting '/' require login");
            // No auth required
            req.next();
        });

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
            ctx.response().end(jwt.generateToken(new JsonObject(), new JWTOptions().setExpiresInSeconds(60)));
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



        router.route("/login").handler(
                req -> {
                    JsonReq<UserLoginRequestParam> reqparam = new JsonReq<UserLoginRequestParam>();
                    reqparam.setToken("TOKEN12312");
                    UserLoginRequestParam param = new UserLoginRequestParam();
                    param.setUsername("chenyang");
                    param.setPassword("password");
                    reqparam.setParam(param);
                    String jsonString = JSON.toJSONString(reqparam);
                    LOGGER.info("Received a http request");
                    LOGGER.info(req.session().id());
                    vertx.eventBus().send("login", jsonString, ar -> {
                        if (ar.succeeded()) {
                            ReqHandle.setOperator(req.session(), (String) ar.result().body());
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        router.route("/sms").handler(
                req -> {
                    RequestThreadLocal reqThreadLocal = ReqHandle.bindRequest(req.session());
                    if (reqThreadLocal.getUser() != null) {
                        LOGGER.info("U can access into our website");
                    } else {
                        LOGGER.info("sorry,U have no permission to visit our website");
                    }
                    LOGGER.info("Received a http request");
                    String name = req.request().getParam("username");
                    String password = req.request().getParam("password");
                    vertx.eventBus().send("sms", name, ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        router.route("/version").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    String version = req.request().getParam("version");
                    vertx.eventBus().send("version", version, ar -> {
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