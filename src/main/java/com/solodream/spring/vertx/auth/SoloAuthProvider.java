package com.solodream.spring.vertx.auth;

import com.solodream.spring.vertx.auth.impl.SoloAuthProviderImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.UserSessionHandler;

/**
 * Created by young on 15/12/1.
 */
@VertxGen
@ProxyGen
public interface SoloAuthProvider extends Handler<RoutingContext>{
    // A couple of factory methods to create an instance and a proxy

    static UserSessionHandler create(AuthProvider authProvider) {
        return new SoloAuthProviderImpl(authProvider);
    }


}
