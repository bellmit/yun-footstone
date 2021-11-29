/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yunjia.footstone.mybatis.mapper.YunJiaBaseMapper;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 基于Mybatis-Plus的IService扩展
 * </p>
 * @author sunkaiyun
 */
public interface YunJiaService<T> extends IService<T> {

    YunJiaBaseMapper<T> getYunJiaBaseMapper();

    /**
     * 根据逻辑编号查询
     */
    default T getByLogicCode(Serializable logicCode) {
        return this.getYunJiaBaseMapper().selectByLogicCode(logicCode);
    }

    /**
     * 根据逻辑编号查询集合
     */
    default List<T> listByLogicCodes(Collection<? extends Serializable> logicCodeList) {
        return this.getYunJiaBaseMapper().selectBatchLogicCodes(logicCodeList);
    }

    /**
     * 根据逻辑编号更新
     */
    default boolean updateByLogicCode(T entity) {
        return SqlHelper.retBool(this.getYunJiaBaseMapper().updateByLogicCode(entity));
    }

    /**
     * 根据逻辑编号批量更新
     */
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean updateBatchByLogicCode(Collection<T> entityList) {
        return this.updateBatchByLogicCode(entityList, 1000);
    }

    /**
     * 根据逻辑编号批量更新
     */
    boolean updateBatchByLogicCode(Collection<T> entityList, int batchSize);

    /**
     * 根据逻辑编号删除
     */
    default boolean removeByLogicCode(Serializable logicCode) {
        return SqlHelper.retBool(this.getYunJiaBaseMapper().deleteByLogicCode(logicCode));
    }

    /**
     * 根据逻辑编号集合删除
     */
    default boolean removeByLogicCodes(Collection<? extends Serializable> logicCodeList) {
        return CollectionUtils.isEmpty(logicCodeList) ? false
                : SqlHelper.retBool(this.getYunJiaBaseMapper().deleteBatchLogicCodes(logicCodeList));
    }
}
