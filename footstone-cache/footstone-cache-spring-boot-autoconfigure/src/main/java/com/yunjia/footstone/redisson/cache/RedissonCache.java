/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.cache;

import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;

/**
 * <p>
 * Redisson Cache
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 5:26 PM
 */
public class RedissonCache implements Cache {

    private RedissonClient redissonClient;

    public RedissonCache(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 加入缓存
     *
     * @param key        Cache Key
     * @param object     Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> void set(String key, V object, long timeToLive, TimeUnit timeUnit) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(object, timeToLive, timeUnit);
    }

    /**
     * 查询缓存
     *
     * @param key Cache Key
     *
     * @return Cache Value
     */
    @Override
    public <V> V get(String key, Class<V> clazz) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 缓存Map值
     *
     * @param cacheKey   Cache Key
     * @param mapValue   Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     * @param <K>        mapValue key的类型
     * @param <V>        mapValue value的类型
     */
    @Override
    public <K, V> void putMap(String cacheKey, Map<K, V> mapValue, long timeToLive, TimeUnit timeUnit) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        rMap.expireAsync(timeToLive, timeUnit);
        rMap.putAll(mapValue);
    }

    /**
     * 缓存Map中某个值
     *
     * @param cacheKey   Cache Key
     * @param mapKey     Map Key
     * @param mapValue   Map Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     * @param <K>        Cache Value key的类型
     * @param <V>        Cache Value value的类型
     */
    @Override
    public <K, V> V putMap(String cacheKey, K mapKey, V mapValue, long timeToLive, TimeUnit timeUnit) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        rMap.expireAsync(timeToLive, timeUnit);
        return rMap.put(mapKey, mapValue);
    }

    /**
     * 获取RMap
     *
     * @param cacheKey   Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     * @param <K>        mapValue key的类型
     * @param <V>        mapValue value的类型
     *
     * @return RMap
     */
    @Override
    public <K, V> RMap<K, V> getRMap(String cacheKey, Class<K> clazzK, Class<V> clazzV, long timeToLive,
            TimeUnit timeUnit) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        rMap.expireAsync(timeToLive, timeUnit);
        return rMap;
    }

    /**
     * 获取缓存Map
     *
     * @param cacheKey Cache Key
     */
    @Override
    public <K, V> Map<K, V> getMap(String cacheKey, Class<K> clazzK, Class<V> clazzV) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        return rMap.readAllMap();
    }

    /**
     * 根据指定mapKey的集合获取Map
     *
     * @param cacheKey Cache Key
     * @param mapKeys  mapKey 集合
     */
    @Override
    public <K, V> Map<K, V> getMap(String cacheKey, Set<K> mapKeys, Class<K> clazzK, Class<V> clazzV) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        return rMap.getAll(mapKeys);
    }

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKey   Map Key
     */
    @Override
    public <K, V> V removeMap(String cacheKey, K mapKey, Class<V> clazzV) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        return rMap.remove(mapKey);
    }

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKeys  Map Key集合
     */
    @Override
    public <K, V> long removeMap(String cacheKey, K... mapKeys) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        return rMap.fastRemove(mapKeys);
    }

    /**
     * 清空Map缓存
     *
     * @param cacheKey Cache Key
     */
    @Override
    public <K, V> void clearMap(String cacheKey) {
        RMap<K, V> rMap = redissonClient.getMap(cacheKey);
        rMap.clear();
    }

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param list       Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> boolean addList(String key, List<V> list, long timeToLive, TimeUnit timeUnit) {
        RList<V> rList = redissonClient.getList(key);
        rList.expireAsync(timeToLive, timeUnit);
        return rList.addAll(list);
    }

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param value      List Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> boolean addList(String key, V value, long timeToLive, TimeUnit timeUnit) {
        RList<V> rList = redissonClient.getList(key);
        rList.expireAsync(timeToLive, timeUnit);
        return rList.add(value);
    }

    /**
     * 获取RList
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> RList<V> getRList(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit) {
        RList<V> rList = redissonClient.getList(key);
        rList.expireAsync(timeToLive, timeUnit);
        return rList;
    }

    /**
     * 获取List
     *
     * @param key Cache Key
     */
    @Override
    public <V> List<V> getList(String key, Class<V> clazz) {
        RList<V> rList = redissonClient.getList(key);
        return rList.readAll();
    }

    /**
     * 删除List
     *
     * @param key Cache Key
     */
    @Override
    public <V> void clearList(String key) {
        RList<V> rList = redissonClient.getList(key);
        rList.clear();
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> boolean addSet(String key, V value, long timeToLive, TimeUnit timeUnit) {
        RSet<V> rSet = redissonClient.getSet(key);
        rSet.expireAsync(timeToLive, timeUnit);
        return rSet.add(value);
    }

    /**
     * 缓存Set
     *
     * @param key        Cache Key
     * @param setValue   Set
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> boolean addSet(String key, Set<V> setValue, long timeToLive, TimeUnit timeUnit) {
        RSet<V> rSet = redissonClient.getSet(key);
        rSet.expireAsync(timeToLive, timeUnit);
        return rSet.addAll(setValue);
    }

    /**
     * 获取缓存RSet
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public <V> RSet<V> getRSet(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit) {
        RSet<V> rSet = redissonClient.getSet(key);
        rSet.expireAsync(timeToLive, timeUnit);
        return rSet;
    }

    /**
     * 获取缓存Set
     *
     * @param key Cache Key
     */
    @Override
    public <V> Set<V> getSet(String key, Class<V> clazz) {
        RSet<V> rSet = redissonClient.getSet(key);
        return rSet.readAll();
    }

    /**
     * 清除Set缓存
     *
     * @param key Cache Key
     */
    @Override
    public <V> void clearSet(String key) {
        RSet<V> rSet = redissonClient.getSet(key);
        rSet.clear();
    }

    /**
     * 缓存Set元素
     *
     * @param key   Cache Key
     * @param value Set Value
     */
    @Override
    public <V> boolean addSortedSet(String key, V value) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.add(value);
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param comparator 比较器
     */
    @Override
    public <V> boolean addSortedSet(String key, V value, Comparator comparator) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if(!rSortedSet.isExists()) {
            rSortedSet.trySetComparator(comparator);
        }
        return rSortedSet.add(value);
    }

    /**
     * 缓存Set元素
     *
     * @param key      Cache Key
     * @param setValue Set Value
     */
    @Override
    public <V> boolean addSortedSet(String key, Set<V> setValue) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.addAll(setValue);
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param setValue   Set Value
     * @param comparator 比较器
     */
    @Override
    public <V> boolean addSortedSet(String key, Set<V> setValue, Comparator comparator) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if(!rSortedSet.isExists()) {
            rSortedSet.trySetComparator(comparator);
        }
        return rSortedSet.addAll(setValue);
    }

    /**
     * 获取RSortedSet
     *
     * @param key Cache Key
     */
    @Override
    public <V> RSortedSet getRSortedSet(String key, Class<V> clazz) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet;
    }

    /**
     * 获取RSortedSet
     *
     * @param key        Cache Key
     * @param comparator 比较器
     */
    @Override
    public <V> RSortedSet getRSortedSet(String key, Comparator comparator, Class<V> clazz) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        if(!rSortedSet.isExists()) {
            rSortedSet.trySetComparator(comparator);
        }
        return rSortedSet;
    }

    /**
     * 删除缓存元素
     *
     * @param key   Cache Key
     * @param value SortedSet Entity
     */
    @Override
    public <V> boolean removeSortedSet(String key, V value) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.remove(value);
    }

    /**
     * 删除缓存元素
     *
     * @param key      Cache Key
     * @param setValue SortedSet Entity Collection
     */
    @Override
    public <V> boolean removeSortedSet(String key, Set<V> setValue) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        return rSortedSet.removeAll(setValue);
    }

    /**
     * 删除缓存
     *
     * @param key Cache Key
     */
    @Override
    public <V> void clearSortedSet(String key) {
        RSortedSet<V> rSortedSet = redissonClient.getSortedSet(key);
        rSortedSet.clear();
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitIndex   bitIndex
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public void setBitSet(String key, long bitIndex, long timeToLive, TimeUnit timeUnit) {
        this.setBitSet(key, bitIndex, true, timeToLive, timeUnit);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitIndex   bitIndex
     * @param value      bit value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public void setBitSet(String key, long bitIndex, boolean value, long timeToLive, TimeUnit timeUnit) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.expireAsync(timeToLive, timeUnit);
        bitSet.set(bitIndex, value);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param fromIndex  起始位（包含）
     * @param toIndex    结束位（不包含）
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public void setBitSet(String key, long fromIndex, long toIndex, long timeToLive, TimeUnit timeUnit) {
        this.setBitSet(key, fromIndex, toIndex, true, timeToLive, timeUnit);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param fromIndex  起始位（包含）
     * @param toIndex    结束位（不包含）
     * @param value      bit value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public void setBitSet(String key, long fromIndex, long toIndex, boolean value, long timeToLive, TimeUnit timeUnit) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.expireAsync(timeToLive, timeUnit);
        bitSet.set(fromIndex, toIndex, value);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitSet     BitSet
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    @Override
    public void setBitSet(String key, BitSet bitSet, long timeToLive, TimeUnit timeUnit) {
        RBitSet rBitSet = redissonClient.getBitSet(key);
        rBitSet.expireAsync(timeToLive, timeUnit);
        rBitSet.set(bitSet);
    }

    /**
     * 清除BitSet
     *
     * @param key Cache Key
     */
    @Override
    public void clearBitSet(String key) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.clear();
    }

    /**
     * 清除BitSet
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    @Override
    public void clearBitSet(String key, long bitIndex) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.clear(bitIndex);
    }

    /**
     * 清除BitSet
     *
     * @param key       Cache Key
     * @param fromIndex 起始位（包含）
     * @param toIndex   结束位（不包含）
     */
    @Override
    public void clearBitSet(String key, long fromIndex, long toIndex) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.clear(fromIndex, toIndex);
    }

    /**
     * 获取BitSet指定位置的值
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    @Override
    public boolean getBitSet(String key, long bitIndex) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        return bitSet.get(bitIndex);
    }

    /**
     * 获取BitSet
     *
     * @param key Cache Key
     *
     * @return BitSet
     */
    @Override
    public BitSet getBitSet(String key) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        BitSet asBitSet = bitSet.asBitSet();
        return asBitSet;
    }
}
