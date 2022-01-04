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
 * 缓存接口定义
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 8:10 PM
 */
public interface Cache {

    /**
     * 加入缓存
     *
     * @param key        Cache Key
     * @param object     Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> void set(String key, V object, long timeToLive, TimeUnit timeUnit);

    /**
     * 查询缓存
     *
     * @param key Cache Key
     *
     * @return Cache Value
     */
    <V> V get(String key, Class<V> clazz);

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
    <K, V> void putMap(String cacheKey, Map<K, V> mapValue, long timeToLive, TimeUnit timeUnit);

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
    <K, V> V putMap(String cacheKey, K mapKey, V mapValue, long timeToLive, TimeUnit timeUnit);

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
    <K, V> RMap<K, V> getRMap(String cacheKey, Class<K> clazzK, Class<V> clazzV, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取缓存Map
     *
     * @param cacheKey Cache Key
     */
    <K, V> Map<K, V> getMap(String cacheKey, Class<K> clazzK, Class<V> clazzV);

    /**
     * 根据指定mapKey的集合获取Map
     *
     * @param cacheKey Cache Key
     * @param mapKeys  mapKey 集合
     */
    <K, V> Map<K, V> getMap(String cacheKey, Set<K> mapKeys, Class<K> clazzK, Class<V> clazzV);

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKey   Map Key
     */
    <K, V> V removeMap(String cacheKey, K mapKey, Class<V> clazzV);

    /**
     * 删除Map中的指定值
     *
     * @param cacheKey Cache Key
     * @param mapKeys  Map Key集合
     */
    <K, V> long removeMap(String cacheKey, K... mapKeys);

    /**
     * 清空Map缓存
     *
     * @param cacheKey Cache Key
     */
    <K, V> void clearMap(String cacheKey);

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param list       Cache Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> boolean addList(String key, List<V> list, long timeToLive, TimeUnit timeUnit);

    /**
     * 缓存List
     *
     * @param key        Cache Key
     * @param value      List Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> boolean addList(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取RList
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> RList<V> getRList(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取List
     *
     * @param key Cache Key
     */
    <V> List<V> getList(String key, Class<V> clazz);

    /**
     * 删除List
     *
     * @param key Cache Key
     */
    <V> void clearList(String key);

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> boolean addSet(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 缓存Set
     *
     * @param key        Cache Key
     * @param setValue   Set
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> boolean addSet(String key, Set<V> setValue, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取缓存RSet
     *
     * @param key        Cache Key
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    <V> RSet<V> getRSet(String key, Class<V> clazz, long timeToLive, TimeUnit timeUnit);

    /**
     * 获取缓存Set
     *
     * @param key Cache Key
     */
    <V> Set<V> getSet(String key, Class<V> clazz);

    /**
     * 清除Set缓存
     *
     * @param key Cache Key
     */
    <V> void clearSet(String key);

    /**
     * 缓存Set元素
     *
     * @param key   Cache Key
     * @param value Set Value
     */
    <V> boolean addSortedSet(String key, V value);

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param value      Set Value
     * @param comparator 比较器
     */
    <V> boolean addSortedSet(String key, V value, Comparator comparator);

    /**
     * 缓存Set元素
     *
     * @param key      Cache Key
     * @param setValue Set Value
     */
    <V> boolean addSortedSet(String key, Set<V> setValue);

    /**
     * 缓存Set元素
     *
     * @param key        Cache Key
     * @param setValue   Set Value
     * @param comparator 比较器
     */
    <V> boolean addSortedSet(String key, Set<V> setValue, Comparator comparator);

    /**
     * 获取RSortedSet
     *
     * @param key Cache Key
     */
    <V> RSortedSet getRSortedSet(String key, Class<V> clazz);

    /**
     * 获取RSortedSet
     *
     * @param key        Cache Key
     * @param comparator 比较器
     */
    <V> RSortedSet getRSortedSet(String key, Comparator comparator, Class<V> clazz);

    /**
     * 删除缓存元素
     *
     * @param key   Cache Key
     * @param value SortedSet Entity
     */
    <V> boolean removeSortedSet(String key, V value);

    /**
     * 删除缓存元素
     *
     * @param key      Cache Key
     * @param setValue SortedSet Entity Collection
     */
    <V> boolean removeSortedSet(String key, Set<V> setValue);

    /**
     * 删除缓存
     *
     * @param key Cache Key
     */
    <V> void clearSortedSet(String key);

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitIndex   bitIndex
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    void setBitSet(String key, long bitIndex, long timeToLive, TimeUnit timeUnit);

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitIndex   bitIndex
     * @param value      bit value
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    void setBitSet(String key, long bitIndex, boolean value, long timeToLive, TimeUnit timeUnit);

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param fromIndex  起始位（包含）
     * @param toIndex    结束位（不包含）
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    void setBitSet(String key, long fromIndex, long toIndex, long timeToLive, TimeUnit timeUnit);

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
    void setBitSet(String key, long fromIndex, long toIndex, boolean value, long timeToLive, TimeUnit timeUnit);

    /**
     * BitSet操作
     *
     * @param key        Cache Key
     * @param bitSet     BitSet
     * @param timeToLive 缓存时长
     * @param timeUnit   时长单位
     */
    void setBitSet(String key, BitSet bitSet, long timeToLive, TimeUnit timeUnit);

    /**
     * 清除BitSet
     *
     * @param key Cache Key
     */
    void clearBitSet(String key);

    /**
     * 清除BitSet
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    void clearBitSet(String key, long bitIndex);

    /**
     * 清除BitSet
     *
     * @param key       Cache Key
     * @param fromIndex 起始位（包含）
     * @param toIndex   结束位（不包含）
     */
    void clearBitSet(String key, long fromIndex, long toIndex);

    /**
     * 获取BitSet指定位置的值
     *
     * @param key      Cache Key
     * @param bitIndex bitIndex
     */
    boolean getBitSet(String key, long bitIndex);

    /**
     * 获取BitSet
     *
     * @param key Cache Key
     *
     * @return BitSet
     */
    BitSet getBitSet(String key);
}
