package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hazelcast.util.MD5Util;
import com.solodream.spring.vertx.common.DateUtil;
import com.solodream.spring.vertx.common.ResultCode;
import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;
import com.solodream.spring.vertx.req.JsonReq;
import com.solodream.spring.vertx.req.client.SmsRequestParam;
import com.solodream.spring.vertx.req.client.TokenRequestParam;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.ProfileResponse;
import com.solodream.spring.vertx.resp.job.ResumeResponse;
import com.solodream.spring.vertx.resp.login.LoginResponse;
import com.solodream.spring.vertx.resp.login.SmsResponse;
import com.solodream.spring.vertx.service.RedisCacheService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
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
        JWTAuth jwt = JWTAuth.create(vertx, new JsonObject()
                .put("keyStore", new JsonObject()
                        .put("type", "jceks")
                        .put("path", "keystore.jceks")
                        .put("password", "secret")));


        router.route("/upload").handler(
                req -> {
                    System.out.println("1233333");
                    req.request().setExpectMultipart(true);
                    req.request().uploadHandler(upload -> {
                        upload.exceptionHandler(cause -> {
                            req.response().setChunked(true).end("Upload failed");
                        });

                        upload.endHandler(v -> {
                            req.response().setChunked(true).end("Successfully uploaded to " + upload.filename());
                        });
                        upload.streamToFileSystem(upload.filename());

                    });

                });
        router.route("/sms").handler(
                req -> {
                    LOGGER.info("Received a http request,enter into /client/getSmsCode");
                    JsonObject jsonString = req.getBodyAsJson();
                    JsonReq<SmsRequestParam> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<SmsRequestParam>>() {
                    });
                    String mobile = obj.getParam().getMobile();
                    BaseResp<SmsResponse> response = new BaseResp<SmsResponse>();
                    SmsResponse resp = new SmsResponse();
                    vertx.eventBus().send("sms", mobile, ar -> {
                        if (ar.succeeded()) {
                            response.setData(resp);
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end(JSON.toJSONString(response));
                        }

                    });
                });


        router.post("/accessToken").handler(ctx -> {
            LOGGER.info("Received a http request,enter into /client/getAccessToken");
            JsonObject jsonString = ctx.getBodyAsJson();
            JsonReq<TokenRequestParam> obj = JSON.parseObject(jsonString.toString(), new TypeReference<JsonReq<TokenRequestParam>>() {
            });
            String refreshToken = obj.getParam().getRefreshToken();
            ctx.response().putHeader("Content-Type", "text/plain");
            String token = redisCacheService.get(refreshToken);

            ctx.response().end(token);
        });


        router.route("/user/job").handler(
                req -> {
                    LOGGER.info("Received a http request to jobs");
//                    JsonObject product = req.getBodyAsJson();

                    vertx.eventBus().send("jobs", "", ar -> {
                        if (ar.succeeded()) {

                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body(), "UTF-8");
                        }
                    });
                });
        router.route("/job/detail").handler(
                req -> {
                    LOGGER.info("Received a http request to /client/getLastVersion");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("jobdetail", "", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body(), "UTF-8");
                        }
                    });
                });


        router.route("/user/login").handler(
                req -> {

                    System.out.println(req.getBodyAsString());
                    JsonObject jsonString = req.getBodyAsJson();
                    LOGGER.info("Received a http request,enter into /client/login");
                    BaseResp<LoginResponse> response = new BaseResp<LoginResponse>();
                    LoginResponse resp = new LoginResponse();
                    vertx.eventBus().send("login", jsonString, ar -> {
                        if (ar.succeeded()) {
                            String dto = (String) ar.result().body();
                            LOGGER.info("Received reply: " + ar.result().body());
                            ClientAccountInfoDto accountInfoDto = JSON.parseObject(dto, new TypeReference<ClientAccountInfoDto>() {
                            });
                            Date date = new Date();
                            String accessTokenExpire = DateUtil.formatDateTime(DateUtil.addDateMinu(date, 10080));
                            String refreshTokenExpire = DateUtil.formatDateTime(DateUtil.addDateMinu(date, 20160));
                            req.response().putHeader("Content-Type", "text/plain");
                            String generateToken = jwt.generateToken(new JsonObject(), new JWTOptions().setSubject(accountInfoDto.getAccount()).setAlgorithm("HS256").setExpiresInSeconds(10080 * 60));
                            //save one week
                            redisCacheService.put("token", generateToken, 10080 * 60);
                            //save two week
                            String refreshToken = MD5Util.toMD5String(generateToken);
                            redisCacheService.put(refreshToken, generateToken, 20160 * 60);
                            resp.setAccessToken(generateToken);
                            resp.setRefreshToken(refreshToken);
                            resp.setAccessTokenExpire(accessTokenExpire);
                            resp.setRefreshTokenExpire(refreshTokenExpire);
                            resp.setUserId(1);
                            resp.setPhotoUrl("http://www.mmfenqi.com/static/mmfq_pc/build/static/images/common/icon-police.png");
                            resp.setUserMobile("13857175401");
                            resp.setState("1");
                            response.setData(resp);
                            req.response().end(JSON.toJSONString(response));

                        } else {
                            resp.setCode(ResultCode.USER_NOT_EXIST.code());
                            resp.setMsg("this username or password is wrong");
                            req.response().end(JSON.toJSONString(resp));
                        }
                    });
                });


        router.route("/register").handler(
                req -> {
                    LOGGER.info("Received a http request to /client/getLastVersion");

                    JsonObject reqBodyAsJson = req.getBodyAsJson();
                    vertx.eventBus().send("register", reqBodyAsJson, ar -> {
                        if (ar.succeeded()) {
                            BaseResp<LoginResponse> response = new BaseResp<LoginResponse>();
                            Date date = new Date();
                            String accessTokenExpire = DateUtil.formatDateTime(DateUtil.addDateMinu(date, 10080));
                            String refreshTokenExpire = DateUtil.formatDateTime(DateUtil.addDateMinu(date, 20160));
                            req.response().putHeader("Content-Type", "text/plain");
                            String generateToken = jwt.generateToken(new JsonObject(), new JWTOptions().setSubject("chenyang").setAlgorithm("HS256").setExpiresInSeconds(10080 * 60));
                            //save one week
                            redisCacheService.put("token", generateToken, 10080 * 60);
                            //save two week
                            String refreshToken = MD5Util.toMD5String(generateToken);
                            LoginResponse resp = new LoginResponse();
//                            redisCacheService.put(refreshToken, generateToken, 20160 * 60);
                            resp.setAccessToken(generateToken);
                            resp.setRefreshToken(refreshToken);
                            resp.setAccessTokenExpire(accessTokenExpire);
                            resp.setRefreshTokenExpire(refreshTokenExpire);
                            resp.setUserId(1);
                            resp.setPhotoUrl("http://www.mmfenqi.com/static/mmfq_pc/build/static/images/common/icon-police.png");
                            resp.setUserMobile("13857175401");
                            resp.setState("1");
                            response.setData(resp);
                            response.setCode(0);
                            req.response().end(JSON.toJSONString(response));
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        // protect the API
//        router.route("/*").handler(SoloAuthProvider.create(vertx, redisCacheService));
//        router.route("/*").handler(JWTAuthHandler.create(jwt, "/user/login"));


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


//        router.route("/favorites").handler(req -> {
//            LOGGER.info("Received a http request to /client/getLastVersion");
//            // JsonObject product = req.getBodyAsJson();
//            vertx.eventBus().send("favorites", "", ar -> {
//                if (ar.succeeded()) {
//                    LOGGER.info("Received reply: " + ar.result().body());
//                    req.response().end((String) ar.result().body());
//                }
//            });
//        });


        router.route("/favorites").handler(req -> {
            LOGGER.info("Received a http request to /client/getLastVersion");
            // JsonObject product = req.getBodyAsJson();
            vertx.eventBus().send("favorites", "", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        });

        router.route("/records").handler(req -> {
            LOGGER.info("Received a http request to /client/getLastVersion");
            // JsonObject product = req.getBodyAsJson();
            vertx.eventBus().send("records", "", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        });

        router.route("/ask").handler(req -> {
            LOGGER.info("Received a http request to /ask");
            // JsonObject product = req.getBodyAsJson();
            vertx.eventBus().send("ask", "", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        });

        router.route("/questions").handler(req -> {
            LOGGER.info("Received a http request to /questions");
            // JsonObject product = req.getBodyAsJson();
            vertx.eventBus().send("questions", "", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        });


        router.route("/topkey").handler(req -> {
            LOGGER.info("Received a http request to /topkey");
            // JsonObject product = req.getBodyAsJson();
            vertx.eventBus().send("topkey", "", ar -> {
                if (ar.succeeded()) {
                    LOGGER.info("Received reply: " + ar.result().body());
                    req.response().end((String) ar.result().body());
                }
            });
        });


        router.route("/profile").handler(
                req -> {
                    LOGGER.info("Received a http request to /profile");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("profile", "", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });


        router.route("/profile/detail").handler(
                req -> {
                    LOGGER.info("Received a http request to /profile/detail");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("profiledetail", "", ar -> {
                        if (ar.succeeded()) {

                            BaseResp<ProfileResponse> response = new BaseResp<ProfileResponse>();

                            ProfileResponse param = new ProfileResponse();
                            param.setAge("28");
                            param.setBirthday("1987-10-23");
                            param.setDisable("1");
                            param.setEmail("cywhoyi@126.com");
                            param.setExpectSalary("50000元");
                            param.setUserid(1);
                            param.setIdNo("33018519288231231232");
                            param.setMobile("13857175401");
                            param.setSex("0");
                            param.setUserName("陈洋");
                            param.setWorkArea("杭州");
                            param.setWorkTime("2009-06-01");


                            response.setData(param);
                            response.setCode(0);
                            LOGGER.info("Received reply: " + JSON.toJSONString(response));
                            req.response().end(JSON.toJSONString(response), "GBK");
                        }
                    });
                });


        router.route("/resume").handler(
                req -> {
                    LOGGER.info("Received a http request to /resume");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("resume", "", ar -> {
                        if (ar.succeeded()) {
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end((String) ar.result().body());
                        }
                    });
                });

        router.route("/resume/detail").handler(
                req -> {
                    LOGGER.info("Received a http request to /resume/detail");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("resumedetail", "", ar -> {
                        if (ar.succeeded()) {
                            BaseResp<ProfileResponse> response = new BaseResp<ProfileResponse>();

                            ProfileResponse profileparam = new ProfileResponse();
                            profileparam.setAge("28");
                            profileparam.setBirthday("1987-10-23");
                            profileparam.setDisable("1");
                            profileparam.setEmail("cywhoyi@126.com");
                            profileparam.setExpectSalary("50000元");
                            profileparam.setUserid(1);
                            profileparam.setIdNo("33018519288231231232");
                            profileparam.setMobile("13857175401");
                            profileparam.setSex("0");
                            profileparam.setUserName("陈洋");
                            profileparam.setWorkArea("杭州");
                            profileparam.setWorkTime("2009-06-01");


                            ResumeResponse param = new ResumeResponse();

                            param.setUserId(1);
                            param.setCompany1("华为");
                            param.setDescription1("java初级工程师,负责手机阅读客户端");
                            param.setFunction1("java工程师");
                            param.setWorkTime1("2009-10-01");
                            param.setIndustry1("电信行业");

                            param.setCompany2("医惠科技");
                            param.setDescription2("药品o2o");
                            param.setFunction2("部门经理");
                            param.setWorkTime2("2011-10-01");
                            param.setIndustry2("医疗行业");


                            param.setEducation1("余杭高级中学");
                            param.setEducationTime1("2005-09-01");
                            param.setEducationMajor1("理科");

                            param.setEducation2("杭州电子科技大学");
                            param.setEducationTime2("2009-09-01");
                            param.setEducationMajor2("信息管理与信息系统");


                            param.setCredential1("PMP");
                            param.setCredentialDesc1("项目管理,经过ppp");

                            param.setCredential2("本科毕业证书");
                            param.setCredentialDesc2("毕业证书,拿到了");
                            param.setIsDefault("1");

                            profileparam.setResume(param);

                            response.setData(profileparam);
                            LOGGER.info("Received reply: " + ar.result().body());
                            req.response().end(JSON.toJSONString(response));
                        }
                    });
                });


        router.route("/collect").handler(
                req -> {
                    LOGGER.info("Received a http request to apply");
                    // JsonObject product = req.getBodyAsJson();
                    vertx.eventBus().send("collect", "", ar -> {
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