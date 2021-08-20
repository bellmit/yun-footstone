package com.yun.footstone.common.algorithms;

/**
 * <p>
 *     ID生成器抽象
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:29
 */
public abstract class AbstractIDGenerator implements IDGenerator {

    /**
     * ID前缀
     */
    private String identity;

    /**
     * 机器编号
     */
    private int workerId;

    protected AbstractIDGenerator() {

    }

    protected AbstractIDGenerator(String identity, int workerId) {
        this.workerId = workerId;
        this.identity = identity;
    }

    @Override
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getIdentity() {
        return identity;
    }

    public int getWorkerId() {
        return workerId;
    }

    @Override
    public abstract String nextId();
}
