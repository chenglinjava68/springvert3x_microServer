package com.solodream.spring.vertx.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
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


    class MyFormLoginHandler implements Handler<RoutingContext> {
        public void handle(RoutingContext routingContext) {
            HttpServerResponse response = routingContext.response();
            Session session = routingContext.session();
            System.out.println("session start");
            System.out.println(session.id());
            System.out.println("session end");
            routingContext.request().bodyHandler(new Handler<Buffer>() {
                public void handle(Buffer buf) {

                    response.setStatusCode(204).end("User Authenticated");
                }

            });
        }
    }

    ;
    public void start() {
        LOGGER.info("start.");


        Router router = Router.router(vertx);

//        router.route("/").handler(new MyFormLoginHandler());
//
//        router.route().handler(CookieHandler.create());
//        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
//
//        router.route().handler(routingContext -> {
//
//            Session session = routingContext.session();
//
//            Integer cnt = session.get("hitcount");
//            cnt = (cnt == null ? 0 : cnt) + 1;
//
//            session.put("hitcount", cnt);
//
//            routingContext.response().putHeader("content-type", "text/html")
//                    .end("<html><body><h1>Hitcount: " + cnt + "</h1></body></html>");
//        });

        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router.route().handler(BodyHandler.create());
        router.route("/customer").handler(
//                vertx.createHttpServer().requestHandler(
                req -> {

                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("customer", "findAll req", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        router.route("/customer2").handler(
//                vertx.createHttpServer().requestHandler(
                req -> {

                    LOGGER.info("Received a http request");
                    vertx.eventBus().send("customer2", "findAll req", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        vertx.createHttpServer().requestHandler(router::accept).listen(8080);


        LOGGER.info("Started HttpServer(port=18080).");
    }
}