package com.solodream.spring.vertx.vertx;

import com.solodream.spring.vertx.req.ReqHandle;
import com.solodream.spring.vertx.req.RequestThreadLocal;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    public void start() {
        LOGGER.info("start.");
        Router router = Router.router(vertx);
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router.route().handler(BodyHandler.create());


        router.route("/login").handler(
                req -> {
                    LOGGER.info("Received a http request");
                    System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>");
                    System.out.println(req.session().id());
                    LOGGER.info(req.session().id());
                    System.out.println("<<<<<<<<<<<>>>>>>>>>>>>>");
                    String name = req.request().getParam("username");
                    String password = req.request().getParam("password");
                    vertx.eventBus().send("login", name, ar -> {
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