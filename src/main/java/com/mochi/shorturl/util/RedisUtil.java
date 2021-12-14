package com.mochi.shorturl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
}
