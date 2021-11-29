/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.yunjia.footstone.mybatis.method.DeleteBatchLogicCodes;
import com.yunjia.footstone.mybatis.method.DeleteByLogicCode;
import com.yunjia.footstone.mybatis.method.SelectBatchLogicCodes;
import com.yunjia.footstone.mybatis.method.SelectByLogicCode;
import com.yunjia.footstone.mybatis.method.UpdateByLogicCode;
import java.util.List;

/**
 * <p>
 * 注入LogicCode相关SQL
 * </p>
 * @author sunkaiyun
 */
public class YunJiaLogicSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法 可以super.getMethodList() 再add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectByLogicCode());
        methodList.add(new SelectBatchLogicCodes());
        methodList.add(new UpdateByLogicCode());
        methodList.add(new DeleteByLogicCode());
        methodList.add(new DeleteBatchLogicCodes());
        return methodList;
    }

}
