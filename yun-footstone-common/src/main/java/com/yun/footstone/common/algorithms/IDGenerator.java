package com.yun.footstone.common.algorithms;

/**
 * <p>
 *     ID生成器接口
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:28
 */
public interface IDGenerator {

    /**
     * 设置id前缀
     *
     * @param identity
     * @return
     */
    void setIdentity(String identity);

    /**
     * 设置机器的id
     *
     * @param workerId
     * @return
     */
    void setWorkerId(int workerId);

    /**
     * 返回id
     *
     * @return
     */
    String nextId();
}
