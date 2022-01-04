/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.persistent.redis;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import com.yunjia.footstone.redisson.cache.CacheUtil;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Auth 持久层接口 [Redis版]
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 4:23 PM
 */
@Component
public class RedisAuthRepository implements AuthRepository {

    private static final String AUTH_KEY_PREFIX = "FOOTSTONE:AUTH";

    @Resource
    private RedissonClient redissonClient;

    /**
     * 组装缓存Key，添加前缀，防止可以重复
     */
    private String parseCacheKey(String key) {
        return StrUtil.join(StrUtil.COLON, AUTH_KEY_PREFIX, key);
    }

    /**
     * 获取Value，如无返空
     *
     * @param key 键名称
     *
     * @return value
     */
    @Override
    public String get(String key) {
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        return bucket.get();
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param value   值
     * @param timeout 过期时间(值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if(timeout == 0 || timeout <= AuthRepository.NOT_VALUE_EXPIRE) {
            return;
        }
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        // 如果为永不过期，存储一年(365天)
        if(timeout == AuthRepository.NEVER_EXPIRE) {
            bucket.set(value, 365, TimeUnit.DAYS);
        } else {
            bucket.set(value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新Value (过期时间不变)
     *
     * @param key   键名称
     * @param value 值
     */
    @Override
    public void update(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        bucket.setIfExists(value);
    }

    /**
     * 删除Value
     *
     * @param key 键名称
     */
    @Override
    public void delete(String key) {
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        bucket.delete();
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     *
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getTimeout(String key) {
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        return bucket.getIdleTime();
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        RBucket<String> bucket = redissonClient.getBucket(parseCacheKey(key));
        // 判断是否想要设置为永久
        if(timeout == AuthRepository.NEVER_EXPIRE) {
            // 如果尚未被设置为永久，那么存储一年(365天)
            bucket.expire(365, TimeUnit.DAYS);
        } else {
            bucket.expire(timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取Object，如无返空
     *
     * @param key 键名称
     *
     * @return object
     */
    @Override
    public Object getObject(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        return bucket.get();
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param object  值
     * @param timeout 存活时间 (值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if(timeout == 0 || timeout <= AuthRepository.NOT_VALUE_EXPIRE) {
            return;
        }
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        // 如果为永不过期，存储一年(365天)
        if(timeout == AuthRepository.NEVER_EXPIRE) {
            bucket.set(object, 365, TimeUnit.DAYS);
        } else {
            bucket.set(object, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新Object (过期时间不变)
     *
     * @param key    键名称
     * @param object 值
     */
    @Override
    public void updateObject(String key, Object object) {
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        bucket.setIfExists(object);
    }

    /**
     * 删除Object
     *
     * @param key 键名称
     */
    @Override
    public void deleteObject(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        bucket.delete();
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     *
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getObjectTimeout(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        return bucket.getIdleTime();
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        RBucket<Object> bucket = redissonClient.getBucket(parseCacheKey(key));
        // 判断是否想要设置为永久
        if(timeout == AuthRepository.NEVER_EXPIRE) {
            // 如果尚未被设置为永久，那么存储一年(365天)
            bucket.expire(365, TimeUnit.DAYS);
        } else {
            bucket.expire(timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 搜索数据
     *
     * @param prefix  前缀
     * @param keyword 关键字
     * @param start   开始处索引 (-1代表查询所有)
     * @param size    获取数量
     *
     * @return 查询到的数据集合
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size) {
        return ListUtil.empty();
    }
}
