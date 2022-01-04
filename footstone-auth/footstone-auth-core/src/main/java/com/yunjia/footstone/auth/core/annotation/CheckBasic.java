/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.annotation;

import com.yunjia.footstone.auth.core.basic.BasicTemplate;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Http Basic 认证：只有通过 Basic 认证后才能进入该方法
 * 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 7:14 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface CheckBasic {

    /**
     * 领域
     * @return see note
     */
    String realm() default BasicTemplate.DEFAULT_REALM;

    /**
     * 需要校验的账号密码，格式形如 sa:123456
     * @return see note
     */
    String account() default "";
}
