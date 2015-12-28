package com.solodream.spring.vertx.auth.impl;

import com.solodream.spring.vertx.jpa.model.Contract;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.handler.impl.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by young on 15/12/1.
 */
public class SoloAuthProviderImpl implements UserSessionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoloAuthProviderImpl.class);

    private static final String SESSION_USER_HOLDER_KEY = "__vertx.userHolder";

    private Vertx vertx;

    private RedisCacheService redisCacheService;

    public SoloAuthProviderImpl(Vertx vertx, RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
        this.vertx = vertx;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        LOGGER.info(">>>>>>>>>>>>>>>  Any request have to be intercept");

        Contract result=(Contract)redisCacheService.getObject("123");
        System.out.println(result.getName());

        Session session = routingContext.session();
        if (session != null) {
            LOGGER.info("session is not exit ,please u have to login");
            User user = null;
            UserHolder holder = session.get(SESSION_USER_HOLDER_KEY);
            if (holder != null) {
                RoutingContext prevContext = holder.context;
                if (prevContext != null) {
                    user = prevContext.user();
                } else if (holder.user != null) {
                    user = holder.user;
                    // user.setAuthProvider(authProvider);
                    holder.context = routingContext;
                    holder.user = null;
                }
                holder.context = routingContext;
            } else {
                session.put(SESSION_USER_HOLDER_KEY, new UserHolder(routingContext));
            }
            if (user != null) {
                routingContext.setUser(user);
            }
        }
        routingContext.next();
    }

}

