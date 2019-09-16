package com.jksk.commonutil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2017-11-27
 **/
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("设置redis出现异常。", e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("设置redis出现异常。", e);
        }
        return result;
    }

    
    public int getExpireTime(final String key) {
        int expireTime = 0;
        try {
            String value = redisTemplate.boundValueOps(key).get().toString();
            if (StringUtils.isNotEmpty(value) && StringUtils.isNotBlank(value)) {
                return Integer.valueOf(value);
            }
        } catch (Exception e) {
            log.error("获取剩余时间.", e);
        }
        return expireTime;
    }

    /**
     * 自增加一
     *
     * @param key
     * @param
     * @return
     */
    public void incr(final String key) {
        Integer i = 1;
        redisTemplate.opsForValue().increment(key.toString(), i);
    }

    /**
     * 设置一个key存放的时间
     *
     * @param key
     * @param
     * @return
     */
    public void expire(final String key, Long expireTime) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

}