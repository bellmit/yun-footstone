/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunjia.footstone.mybatis.enums.CommonSqlMethod;
import com.yunjia.footstone.mybatis.mapper.YunJiaBaseMapper;
import java.util.Collection;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 基于Mybatis-Plus的IService扩展
 * </p>
 * @author sunkaiyun
 */
public class YunJiaServiceImpl<M extends YunJiaBaseMapper<T>, T> extends ServiceImpl<M, T> implements YunJiaService<T> {

    @Override
    public YunJiaBaseMapper<T> getYunJiaBaseMapper() {
        return this.getBaseMapper();
    }

    /**
     * 根据逻辑编号批量更新
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean updateBatchByLogicCode(Collection<T> entityList, int batchSize) {
        String sqlStatement = this.getSqlStatement(CommonSqlMethod.UPDATE_BY_LOGIC_CODE);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            ParamMap<T> param = new ParamMap();
            param.put("et", entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    protected String getSqlStatement(CommonSqlMethod sqlMethod) {
        return StrUtil.join(StrUtil.DOT, this.mapperClass.getName(), sqlMethod.getMethod());
    }


}
