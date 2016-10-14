package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.jpa.domain.JobInfoDto;
import com.solodream.spring.vertx.resp.BaseResp;
import com.solodream.spring.vertx.resp.job.JobResponse;
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
public class UserRecordVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRecordVerticle.class);

    public void start() {
        vertx.eventBus().consumer("records", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
            try {

                JobInfoDto dto = new JobInfoDto();
                dto.setId(1);
                dto.setCompanyId(1);
                dto.setCompanyName("alibaba");
                dto.setJobAddress("文一西路");
                dto.setJobDatail("java开发工程师,有架构经验");
                dto.setJobExperience("3~5年");
                dto.setJobLocation("杭州");
                dto.setJobTags("高福利,高期权,高薪酬");
                dto.setJobTitle("java工程师");
                dto.setReleaseDate(new Date());
                dto.setSalaryRange("300000元");



                JobInfoDto dto2 = new JobInfoDto();
                dto2.setId(2);
                dto2.setCompanyId(2);
                dto2.setCompanyName("华为");
                dto2.setJobAddress("文一西路");
                dto2.setJobDatail("云计算开发,有架构经验");
                dto2.setJobExperience("1~5年");
                dto2.setJobLocation("杭州");
                dto2.setJobTags("高福利,高期权,高薪酬");
                dto2.setJobTitle("云计算工程师");
                dto2.setReleaseDate(new Date());
                dto2.setSalaryRange("250000元");


                List<JobInfoDto> list=new ArrayList<>();
                list.add(dto);
                list.add(dto2);

                BaseResp<JobResponse> resp=new BaseResp<JobResponse>();
                JobResponse response = new JobResponse();
                resp.setData(response);
                response.setTotal(list.size());
                response.setDataList(list);
                resp.setCode(0);
                message.reply(JSON.toJSONString(resp));
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}
