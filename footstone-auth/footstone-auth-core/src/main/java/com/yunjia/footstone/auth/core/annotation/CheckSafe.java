/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 二级认证校验：必须二级认证之后才能进入该方法
 * 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 3:55 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface CheckSafe {

    /**
     * 多账号体系下所属的账号体系标识
     * @return see note
     */
    String type() default "";

}
