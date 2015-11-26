package com.solodream.spring.vertx.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Created by young on 15/11/26.
 */
@Service
public class RedisCacheService {
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> messageList;
    @Resource(name = "redisTemplate")
    private RedisOperations<String, String> latestMessageExpiration;

    public void addMessage(String user, String message) {


        messageList.leftPush(user, message);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Date date = Date.from(zonedDateTime.plus(1, ChronoUnit.MINUTES).toInstant());
        latestMessageExpiration.expireAt(user, date);
    }

    public List<String> listMessages(String user) {
        return messageList.range(user, 0, -1);
    }
}