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
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;

/**
 * <p>
 * Redisson Cache工具类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 5:24 PM
 */
public class CacheUtil {

    private static Cache cache;

    public static void setCache(RedissonCache cache) {
        CacheUtil.cache = cache;
    }

    /**
     * 加入缓存
     *
     * @param key        Cache Key
     * @param object     Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> void set(String key, V object, long timeToLive, TimeUnit timeUnit) {
        cache.set(key, object, timeToLive, timeUnit);
    }

    /**
     * 查询缓存
     *
     * @param key Cache Key
     *
     * @return Cache Value
     */
    public static <V> V get(String key, Class<V> clazz) {
        return cache.get(key, clazz);
    }

    /**
     * 缓存Map值
     *
     * @param cacheKey   Cache Key
     * @param mapValue   Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <K, V> void putMap(String cacheKey, Map<K, V> mapValue, long timeToLive, TimeUnit timeUnit) {
        cache.putMap(cacheKey, mapValue, timeToLive, timeUnit);
    }

    /**
     * 缓存Map中某个值
     *
     * @param cacheKey   Cache Key
     * @param mapKey     Map Key
     * @param mapValue   Map Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <K, V> V putMap(String cacheKey, K mapKey, V mapValue, long timeToLive, TimeUnit timeUnit) {
        return cache.putMap(cacheKey, mapKey, mapValue, timeToLive, timeUnit);
    }

    /**
     * 获取RMap
     *
     * @param cacheKey   Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     *
     * @return RMap
     */
    public static <K, V> RMap<K, V> getRMap(String cacheKey, Class<K> clazzK, Class<V> clazzV, long timeToLive, TimeUnit timeUnit) {
        return cache.getRMap(cacheKey, clazzK, clazzV, timeToLive, timeUnit);
    }

    /**
     * 获取缓存Map
     *
     * @param cacheKey Cache Key
     */
    public static <K, V> Map<K, V> getMap(String cacheKey, Class<K> clazzK, Class<V> clazzV) {
        return cache.getMap(cacheKey, clazzK, clazzV);
    }

    /**
     * 根据指定mapKey的集合获取Map
     *
     * @param cacheKey Cache Key
     * @param mapKeys  mapKey 集合
     */
    public static <K, V> Map<K, V> getMap(String cacheKey, Set<K> mapKeys, Class<K> clazzK, Class<V> clazzV) {
        return cache.getMap(cacheKey, mapKeys, clazzK, clazzV);
    }

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKey   Map Key
     */
    public static <K, V> V removeMap(String cacheKey, K mapKey, Class<V> clazzV) {
        return cache.removeMap(cacheKey, mapKey, clazzV);
    }

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKeys  Map Key集合
     */
    public static <K, V> long removeMap(String cacheKey, K... mapKeys) {
        return cache.removeMap(cacheKey, mapKeys);
    }

    /**
     * 清空Map缓存
     *
     * @param cacheKey Cache Key
     */
    public static <K, V> void clearMap(String cacheKey) {
        cache.clearMap(cacheKey);
    }

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param list       Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> boolean addList(String key, List<V> list, long timeToLive, TimeUnit timeUnit) {
        return cache.addList(key, list, timeToLive, timeUnit);
    }

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param value      List Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> boolean addList(String key, V value, long timeToLive, TimeUnit timeUnit) {
        return cache.addList(key, value, timeToLive, timeUnit);
    }

    /**
     * 获取RList
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> RList<V> getRList(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit) {
        return cache.getRList(key, clazz, timeToLive, timeUnit);
    }

    /**
     * 获取List
     *
     * @param key Cache Key
     */
    public static <V> List<V> getList(String key, Class<V> clazz) {
        return cache.getList(key, clazz);
    }

    /**
     * 删除List
     *
     * @param key Cache Key
     */
    public static <V> void clearList(String key) {
        cache.clearList(key);
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> boolean addSet(String key, V value, long timeToLive, TimeUnit timeUnit) {
        return cache.addSet(key, value, timeToLive, timeUnit);
    }

    /**
     * 缓存Set
     *
     * @param key        Cache Key
     * @param setValue   Set
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> boolean addSet(String key, Set<V> setValue, long timeToLive, TimeUnit timeUnit) {
        return cache.addSet(key, setValue, timeToLive, timeUnit);
    }

    /**
     * 获取缓存RSet
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static <V> RSet<V> getRSet(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit) {
        return cache.getRSet(key, clazz, timeToLive, timeUnit);
    }

    /**
     * 获取缓存Set
     *
     * @param key Cache Key
     */
    public static <V> Set<V> getSet(String key, Class<V> clazz) {
        return cache.getSet(key, clazz);
    }

    /**
     * 清除Set缓存
     *
     * @param key Cache Key
     */
    public static <V> void clearSet(String key) {
        cache.clearSet(key);
    }

    /**
     * 缓存Set元素
     *
     * @param key   Cache Key
     * @param value Set Value
     */
    public static <V> boolean addSortedSet(String key, V value) {
        return cache.addSortedSet(key, value);
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param comparator 比较器
     */
    public static <V> boolean addSortedSet(String key, V value, Comparator comparator) {
        return cache.addSortedSet(key, value, comparator);
    }

    /**
     * 缓存Set元素
     *
     * @param key      Cache Key
     * @param setValue Set Value
     */
    public static <V> boolean addSortedSet(String key, Set<V> setValue) {
        return cache.addSortedSet(key, setValue);
    }

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param setValue   Set Value
     * @param comparator 比较器
     */
    public static <V> boolean addSortedSet(String key, Set<V> setValue, Comparator comparator) {
        return cache.addSortedSet(key, setValue, comparator);
    }

    /**
     * 获取RSortedSet
     *
     * @param key Cache Key
     */
    public <V> RSortedSet getRSortedSet(String key, Class<V> clazz) {
        return cache.getRSortedSet(key, clazz);
    }

    /**
     * 获取RSortedSet
     *
     * @param key        Cache Key
     * @param comparator 比较器
     */
    public <V> RSortedSet getRSortedSet(String key, Comparator comparator, Class<V> clazz) {
        return cache.getRSortedSet(key, comparator, clazz);
    }

    /**
     * 删除缓存元素
     *
     * @param key   Cache Key
     * @param value SortedSet Entity
     */
    public static <V> boolean removeSortedSet(String key, V value) {
        return cache.removeSortedSet(key, value);
    }

    /**
     * 删除缓存元素
     *
     * @param key      Cache Key
     * @param setValue SortedSet Entity Collection
     */
    public static <V> boolean removeSortedSet(String key, Set<V> setValue) {
        return cache.removeSortedSet(key, setValue);
    }

    /**
     * 删除缓存
     *
     * @param key Cache Key
     */
    public static <V> void clearSortedSet(String key) {
        cache.clearSortedSet(key);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitIndex   bitIndex
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static void setBitSet(String key, long bitIndex, long timeToLive, TimeUnit timeUnit) {
        cache.setBitSet(key, bitIndex, timeToLive, timeUnit);
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
    public static void setBitSet(String key, long bitIndex, boolean value, long timeToLive, TimeUnit timeUnit) {
        cache.setBitSet(key, bitIndex, value, timeToLive, timeUnit);
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
    public static void setBitSet(String key, long fromIndex, long toIndex, long timeToLive, TimeUnit timeUnit) {
        cache.setBitSet(key, fromIndex, toIndex, timeToLive, timeUnit);
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
    public static void setBitSet(String key, long fromIndex, long toIndex, boolean value, long timeToLive, TimeUnit timeUnit) {
        cache.setBitSet(key, fromIndex, toIndex, value, timeToLive, timeUnit);
    }

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitSet     BitSet
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    public static void setBitSet(String key, BitSet bitSet, long timeToLive, TimeUnit timeUnit) {
        cache.setBitSet(key, bitSet, timeToLive, timeUnit);
    }

    /**
     * 清除BitSet
     *
     * @param key Cache Key
     */
    public static void clearBitSet(String key) {
        cache.clearBitSet(key);
    }

    /**
     * 清除BitSet
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    public static void clearBitSet(String key, long bitIndex) {
        cache.clearBitSet(key, bitIndex);
    }

    /**
     * 清除BitSet
     *
     * @param key       Cache Key
     * @param fromIndex 起始位（包含）
     * @param toIndex   结束位（不包含）
     */
    public static void clearBitSet(String key, long fromIndex, long toIndex) {
        cache.clearBitSet(key, fromIndex, toIndex);
    }

    /**
     * 获取BitSet指定位置的值
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    public static boolean getBitSet(String key, long bitIndex) {
        return cache.getBitSet(key, bitIndex);
    }

    /**
     * 获取BitSet
     *
     * @param key Cache Key
     *
     * @return BitSet
     */
    public static BitSet getBitSet(String key) {
        return cache.getBitSet(key);
    }
}
