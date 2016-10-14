package com.solodream.spring.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solodream.spring.vertx.vertx.*;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.*;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    protected Environment environment;

    @Bean
    protected ObjectMapper objectMapper() {
        return new ObjectMapper().disable(INDENT_OUTPUT)
                                 .disable(WRITE_EMPTY_JSON_ARRAYS)
                                 .disable(WRITE_NULL_MAP_VALUES)
                                 .disable(FAIL_ON_EMPTY_BEANS)
                                 .enable(WRITE_DATES_AS_TIMESTAMPS)
                                 .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                                 .enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                                 .setSerializationInclusion(NON_NULL);
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .registerShutdownHook(true)
                .logStartupInfo(true)
                .showBanner(true)
                .run(args);

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(context.getBean(LoginVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(SmsVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(VersionVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(RouteFromVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(RouteToVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(JobVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(ProfileVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(ResumeVerticle.class), new DeploymentOptions().setWorker(true));

        vertx.deployVerticle(context.getBean(CollectVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(ProfileDetailVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(ResumeDetailVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(UserFavoritesVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(UserRecordVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(TopkeyVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(AskVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(QuestionsVerticle.class), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(context.getBean(RegisterVerticle.class), new DeploymentOptions().setWorker(true));


        vertx.deployVerticle(context.getBean(HttpServerVerticle.class));
    }
}
