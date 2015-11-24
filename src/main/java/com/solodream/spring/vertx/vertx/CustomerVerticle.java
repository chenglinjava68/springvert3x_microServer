package com.solodream.spring.vertx.vertx;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.jpa.domain.Contract;
import com.solodream.spring.vertx.service.UserService;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerVerticle.class);

    @Autowired
    private UserService userService;

    public void start() {
        LOGGER.info("start.");

        vertx.eventBus().consumer("customer", message -> {
            LOGGER.info("Received a message: {}, {}", message.body(), message.headers());
//            final Iterable<Customer> all = this.sqlTemplateService.findAll();

            userService.say();
            try {
                List<Contract> list = new ArrayList<Contract>();
                Contract contract = new Contract();
                contract.setId(1);
                contract.setName("劳动合同");
                contract.setCreateDate(new Date());

                Contract contract1 = new Contract();
                contract1.setId(2);
                contract1.setName("HZ TO Singapore");
                list.add(contract);
                list.add(contract1);
                String json = JSON.toJSONString(list);
                System.out.println("json is " + json);
                message.reply(json);
            } catch (Exception e) {
                LOGGER.error("convert error.", e);
            }
        });
    }
}