/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

/**
 * <p>
 * 聚合根工厂
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 3:36 PM
 */
public class AggregateFactory {

    private AggregateFactory() {
        throw new IllegalStateException("A factory class, please use static method");
    }

    private static DeepCopier copier = new SerializableDeepCopier();

    /**
     * The factory method.
     *
     * @param root The aggregate root
     * @param <R> The type of aggregate root
     * @return the aggregate object
     */
    public static <R extends BaseRoot> Aggregate<R> createAggregate(R root) {
        return new Aggregate<R>(root, copier, new JavaUtilDeepComparator());
    }

    /**
     * set deep copier.
     * @param copier the deepcopier object
     */
    public static void setCopier(DeepCopier copier) {
        AggregateFactory.copier = copier;
    }
}
