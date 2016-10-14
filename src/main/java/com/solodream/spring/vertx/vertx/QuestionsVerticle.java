package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.jpa.domain.JobInfoDto;
import com.solodream.spring.vertx.jpa.domain.QuestionInfoDto;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.JobResponse;
import com.solodream.spring.vertx.resp.job.QuestionResponse;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LoginVerticle
 *
 * @author Young
 * @date 2015/11/24 0024
 */
@Component
public class QuestionsVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionsVerticle.class);

    public void start() {
        vertx.eventBus().consumer("questions", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                List<QuestionInfoDto> list=new ArrayList<QuestionInfoDto>();
                QuestionInfoDto dto1=new QuestionInfoDto();
                dto1.setQuestion("陈洋是帅哥?");
                dto1.setAnswer("yes,毋庸置疑");
                dto1.setAccountId(1);
                dto1.setAccountName("FBI情报局");
                dto1.setUserId(1);
                dto1.setUserMobile("1234444444");
                dto1.setUserName("aobama");


                QuestionInfoDto dto2=new QuestionInfoDto();
                dto2.setQuestion("陈洋是大帅哥?");
                dto2.setAnswer("yes,毋庸置疑");
                dto2.setAccountId(1);
                dto2.setAccountName("FBI情报局");
                dto2.setUserId(1);
                dto2.setUserMobile("1234444444");
                dto2.setUserName("aobama2");

                list.add(dto1);
                list.add(dto2);

                BaseResp<QuestionResponse> resp=new BaseResp<QuestionResponse>();
                QuestionResponse response = new QuestionResponse();
                resp.setData(response);
                resp.setCode(0);
                response.setTotal(list.size());
                response.setDataList(list);
                message.reply(JSON.toJSONString(resp));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
