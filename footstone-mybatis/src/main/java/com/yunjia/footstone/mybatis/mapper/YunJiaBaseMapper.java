/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 基于mybatis-plus的BaseMapper做扩展
 * </p>
 * @author sunkaiyun
 */
public interface YunJiaBaseMapper<T> extends BaseMapper<T> {


    /**
     * 根据逻辑编号查询
     */
    T selectByLogicCode(Serializable logicCode);

    /**
     * 根据逻辑编号查询集合
     */
    List<T> selectBatchLogicCodes(@Param("coll") Collection<? extends Serializable> logicCodeList);

    /**
     * 根据逻辑编号更新
     */
    int updateByLogicCode(@Param("et") T entity);

    /**
     * 根据逻辑编号删除
     */
    int deleteByLogicCode(Serializable id);

    /**
     * 根据逻辑编号集合删除
     */
    int deleteBatchLogicCodes(@Param("coll") Collection<? extends Serializable> logicCodeList);
}
