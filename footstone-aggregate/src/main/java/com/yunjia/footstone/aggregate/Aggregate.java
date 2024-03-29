/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 聚合根容器
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 4:30 PM
 */
public class Aggregate<R extends Versionable> {

    /**
     * 聚合根
     */
    private R root;

    /**
     * 聚合根快照
     */
    private R snapshot;

    /**
     * 深度克隆器
     */
    private DeepCopier deepCopier;

    /**
     * 深度比较器
     */
    private DeepComparator deepComparator;

    /**
     * 构造器
     */
    public Aggregate(R root, DeepCopier deepCopier, DeepComparator deepComparator) {
        this.root = root;
        this.deepCopier = deepCopier;
        this.deepComparator = deepComparator;
        this.snapshot = deepCopier.deepClone(root);
    }

    /**
     * 构造器
     */
    public Aggregate(R root, R snapshot, DeepCopier deepCopier, DeepComparator deepComparator) {
        this.root = root;
        this.snapshot = snapshot;
        this.deepCopier = deepCopier;
        this.deepComparator = deepComparator;
    }

    /**
     * 获取聚合根
     */
    public R getRoot() {
        return root;
    }

    /**
     * snapshot赋值
     */
    public void setSnapshot(R snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * 获取聚合根快照
     */
    public R getSnapshot() {
        return snapshot;
    }

    /**
     * 聚合根实体是否为新建实体
     */
    public boolean isNew() {
        if(null == root.getRootVersion()) {
            return Boolean.FALSE;
        }
        return root.getRootVersion().intValue() == Versionable.ROOT_NEW_VERSION;
    }

    /**
     * 聚合根是否已发生变化
     */
    public boolean isChanged() {
        return !deepComparator.deepEquals(root, snapshot);
    }

    /**
     * 设置聚合根值对象
     */
    public <T> void setObjectValue(BiConsumer<R, T> setObjectValue, T objectValue, boolean isNewObjectValue) {
        setObjectValue.accept(root, objectValue);
        if(!isNewObjectValue) {
            setObjectValue.accept(snapshot, deepCopier.deepClone(objectValue));
        }
    }

    /**
     * 根据给定过滤条件查询聚合根中的值对象属性值
     */
    public <T, M> M findObjectValue(Function<R, T> getObjectValue, DataFilter<T, M> dataFilter) {
        T object = getObjectValue.apply(root);
        return dataFilter.filter(object);
    }

    /**
     * 根据给定过滤条件查询聚合根中的值对象属性值
     */
    public <T, U, M> M findObjectValue(Function<R, T> getObjectValue, BiDataFilter<T, U, M> biDataFilter, U condition) {
        T object = getObjectValue.apply(root);
        return biDataFilter.filter(object, condition);
    }

    /**
     * 判断值对象是否为新增值对象
     */
    public <T> boolean isNewObjectValue(Function<R, T> getObjectValue) {
        T currentObjectValue = getObjectValue.apply(root);
        T snapshotObjectValue = getObjectValue.apply(snapshot);
        // 原来没有值，现在有值，则值对象为新增值对象
        if(null == snapshotObjectValue && null != currentObjectValue) {
            return true;
        }
        return false;
    }

    /**
     * 判断值对象是否变更
     */
    public <T> boolean isChangedObjectValue(Function<R, T> getObjectValue) {
        T currentObjectValue = getObjectValue.apply(root);
        T snapshotObjectValue = getObjectValue.apply(snapshot);
        if(null == currentObjectValue || null == snapshotObjectValue) {
            return false;
        }
        return !deepComparator.deepEquals(snapshotObjectValue, currentObjectValue);
    }

    /**
     * 判断值对象是否为已删除的值对象
     */
    public <T> boolean isDeletedObjectValue(Function<R, T> getObjectValue) {
        T currentObjectValue = getObjectValue.apply(root);
        T snapshotObjectValue = getObjectValue.apply(snapshot);
        // 原来有值，现在没有值，则值对象为已删除的值对象
        if(null != snapshotObjectValue && null == currentObjectValue) {
            return true;
        }
        return false;
    }

    /**
     * 查询聚合根中的值对象（集合）中新增的属性值
     */
    public <T, ID> List<T> findNewObjectValueList(Function<R, Collection<T>> getObjectCollection,
            Function<T, ID> getId) {
        if(isNew()) {
            return (List<T>) getObjectCollection.apply(root);
        } else {
            return getNewObjectValueList(getObjectCollection, getId);
        }
    }

    /**
     * 查询聚合根中的值对象（集合）中更新的属性值
     */
    public <T, ID> List<T> findChangedObjectValueList(Function<R, Collection<T>> getObjectCollection,
            Function<T, ID> getId) {
        Collection<T> currentObjectList = getObjectCollection.apply(root);
        Collection<T> snapshotObjectList = getObjectCollection.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectList)) {
            return ListUtil.empty();
        }

        Map<ID, T> currentObjectValueMap = currentObjectList.stream().collect(Collectors.toMap(getId, item -> item));
        Map<ID, T> snapshotObjectValueMap = snapshotObjectList.stream().collect(Collectors.toMap(getId, item -> item));
        // 快照与当前值的交集
        snapshotObjectValueMap.keySet().retainAll(currentObjectValueMap.keySet());
        List<T> resultList = new ArrayList<>();
        snapshotObjectValueMap.forEach((key, value) -> {
            T currentObjectValue = currentObjectValueMap.get(key);
            // 当前值与快照对比，不同则为已更新值对象
            if(null != currentObjectValue && !deepComparator.deepEquals(currentObjectValue, value)) {
                resultList.add(currentObjectValue);
            }
        });
        return resultList;
    }

    /**
     * 查询聚合根中的值对象（集合）中更新的属性值，携带原值
     */
    public <T, ID> List<ChangedObjectValue<T>> findChangedObjectValueListWithOldValue(
            Function<R, Collection<T>> getObjectCollection, Function<T, ID> getId) {
        Collection<T> currentObjectList = getObjectCollection.apply(root);
        Collection<T> snapshotObjectList = getObjectCollection.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectList)) {
            return ListUtil.empty();
        }

        Map<ID, T> currentObjectValueMap = currentObjectList.stream().collect(Collectors.toMap(getId, item -> item));
        Map<ID, T> snapshotObjectValueMap = snapshotObjectList.stream().collect(Collectors.toMap(getId, item -> item));
        // 快照与当前值的交集
        snapshotObjectValueMap.keySet().retainAll(currentObjectValueMap.keySet());
        List<ChangedObjectValue<T>> resultList = new ArrayList<>();
        snapshotObjectValueMap.forEach((key, value) -> {
            T currentObjectValue = currentObjectValueMap.get(key);
            // 当前值与快照对比，不同则为已更新值对象
            if(null != currentObjectValue && !deepComparator.deepEquals(currentObjectValue, value)) {
                resultList.add(new ChangedObjectValue(value, currentObjectValue));
            }
        });
        return resultList;
    }

    /**
     * 查询聚合根中的值对象（集合）中已删除的属性值
     */
    public <T, ID> List<T> findDeletedObjectValueList(Function<R, Collection<T>> getObjectCollection,
            Function<T, ID> getId) {
        Collection<T> currentObjectList = getObjectCollection.apply(root);
        Collection<T> snapshotObjectList = getObjectCollection.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectList)) {
            return ListUtil.empty();
        }

        Set<ID> currentObjectValueIds = getObjectValueIds(currentObjectList, getId);
        Set<ID> snapshotObjectValueIds = getObjectValueIds(snapshotObjectList, getId);

        // 删除当前集合的ID，剩下的为已删除的ID集合
        snapshotObjectValueIds.removeAll(currentObjectValueIds);
        return snapshotObjectList.stream().filter(item -> snapshotObjectValueIds.contains(getId.apply(item)))
                .collect(Collectors.toList());
    }

    /**
     * 查询聚合根中的值对象（Map）中新增的属性值
     */
    public <K, T> Map<K, T> findNewObjectValueMap(Function<R, Map<K, T>> getObjectValueMap) {
        if(isNew()) {
            return getObjectValueMap.apply(root);
        } else {
            return getNewObjectValueMap(getObjectValueMap);
        }
    }

    /**
     * 查询聚合根中的值对象（Map）中更新后的属性值
     */
    public <K, T> Map<K, T> findChangedObjectValueMap(Function<R, Map<K, T>> getObjectValueMap) {
        Map<K, T> currentObjectMap = getObjectValueMap.apply(root);
        Map<K, T> snapshotObjectMap = getObjectValueMap.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectMap)) {
            return MapUtil.empty();
        }
        // 快照与当前值的交集
        snapshotObjectMap.keySet().retainAll(currentObjectMap.keySet());
        Map<K, T> resultMap = new HashMap<>();
        snapshotObjectMap.forEach((key, value) -> {
            T currentObjectValue = currentObjectMap.get(key);
            // 当前值与快照对比，不同则为已更新值对象
            if(null != currentObjectValue && !deepComparator.deepEquals(currentObjectValue, value)) {
                resultMap.put(key, currentObjectValue);
            }
        });
        return resultMap;
    }

    /**
     * 查询聚合根中的值对象（Map）中更新的属性值，携带原值
     */
    public <K, T> Map<K, ChangedObjectValue<T>> findChangedObjectValueMapWithOldValue(
            Function<R, Map<K, T>> getObjectValueMap) {
        Map<K, T> currentObjectMap = getObjectValueMap.apply(root);
        Map<K, T> snapshotObjectMap = getObjectValueMap.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectMap)) {
            return MapUtil.empty();
        }
        // 快照与当前值的交集
        snapshotObjectMap.keySet().retainAll(currentObjectMap.keySet());
        Map<K, ChangedObjectValue<T>> resultMap = new HashMap<>();
        snapshotObjectMap.forEach((key, value) -> {
            T currentObjectValue = currentObjectMap.get(key);
            // 当前值与快照对比，不同则为已更新值对象
            if(null != currentObjectValue && !deepComparator.deepEquals(currentObjectValue, value)) {
                resultMap.put(key, new ChangedObjectValue(value, currentObjectValue));
            }
        });
        return resultMap;
    }

    /**
     * 查询聚合根中的值对象（Map）中已删除的属性值，携带原值
     */
    public <K, T> Map<K, T> findDeletedObjectValueMap(Function<R, Map<K, T>> getObjectValueMap) {
        Map<K, T> currentObjectMap = getObjectValueMap.apply(root);
        Map<K, T> snapshotObjectMap = getObjectValueMap.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectMap)) {
            return MapUtil.empty();
        }
        // 快照中删除当前集合的值，剩下的为已删除的值
        snapshotObjectMap.keySet().removeAll(currentObjectMap.keySet());
        return snapshotObjectMap;
    }

    /**
     * 当前值对象集合与快照比较，返回新增加的值对象集合
     */
    private <T, ID> List<T> getNewObjectValueList(Function<R, Collection<T>> getObjectCollection,
            Function<T, ID> getId) {
        Collection<T> currentObjectList = getObjectCollection.apply(root);
        Collection<T> snapshotObjectList = getObjectCollection.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectList)) {
            return (List<T>) currentObjectList;
        }

        Set<ID> currentObjectValueIds = getObjectValueIds(currentObjectList, getId);
        Set<ID> snapshotObjectValueIds = getObjectValueIds(snapshotObjectList, getId);
        // 当前集合删除快照集合，即为新增的数据
        currentObjectValueIds.removeAll(snapshotObjectValueIds);
        return currentObjectList.stream().filter(item -> currentObjectValueIds.contains(getId.apply(item)))
                .collect(Collectors.toList());
    }

    /**
     * 遍历值对象集合，并返回值对象ID集合
     */
    private <T, ID> Set<ID> getObjectValueIds(Collection<T> objectValueList, Function<T, ID> getId) {
        return objectValueList.stream().map(item -> getId.apply(item)).collect(Collectors.toSet());
    }

    /**
     * 当前值对象Map与快照比较，返回新增加的值对象Map
     */
    private <K, T> Map<K, T> getNewObjectValueMap(Function<R, Map<K, T>> getObjectValueMap) {
        Map<K, T> currentObjectMap = getObjectValueMap.apply(root);
        Map<K, T> snapshotObjectMap = getObjectValueMap.apply(snapshot);
        if(CollUtil.isEmpty(snapshotObjectMap)) {
            return currentObjectMap;
        }

        Set<K> currentObjectValueKeys = currentObjectMap.keySet();
        Set<K> snapshotObjectValueKeys = snapshotObjectMap.keySet();
        // 当前集合删除快照集合，即为新增的数据
        currentObjectValueKeys.removeAll(snapshotObjectValueKeys);
        Map<K, T> resultMap = new HashMap<>();
        for(K key : currentObjectValueKeys) {
            resultMap.put(key, currentObjectMap.get(key));
        }
        return resultMap;
    }

}
