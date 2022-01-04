/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.strategy;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.annotation.CheckBasic;
import com.yunjia.footstone.auth.core.annotation.CheckLogin;
import com.yunjia.footstone.auth.core.annotation.CheckPermission;
import com.yunjia.footstone.auth.core.annotation.CheckRole;
import com.yunjia.footstone.auth.core.annotation.CheckSafe;
import com.yunjia.footstone.auth.core.basic.BasicUtil;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.session.AuthSession;
import com.yunjia.footstone.auth.core.util.AuthFoxUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 * 策略对象
 * 此类统一定义框架内的一些关键性逻辑算法，方便开发者进行按需重写，例：
 * // SaStrategy全局单例，所有方法都用以下形式重写
 * SaStrategy.INSTANCE.setCreateToken((loginId, loginType) -> {
 * // 自定义Token生成的算法
 * return "xxxx";
 * });
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 2:38 PM
 */
public final class AuthStrategy {

    private AuthStrategy() {
    }

    /**
     * 获取 AuthStrategy 对象的单例引用
     */
    public static final AuthStrategy INSTANCE = new AuthStrategy();

    //
    // 所有策略
    //

    /**
     * 创建 Token 的策略
     * <p> 参数 [账号id, 账号类型]
     */
    public BiFunction<Object, String, String> createToken = (loginId, loginType) -> {
        // 根据配置的tokenStyle生成不同风格的token
        String tokenStyle = AuthManager.getConfig().getTokenStyle();
        // uuid
        if(AuthConstant.TOKEN_STYLE_UUID.equals(tokenStyle)) {
            return UUID.randomUUID().toString();
        }
        // 简单uuid (不带下划线)
        if(AuthConstant.TOKEN_STYLE_SIMPLE_UUID.equals(tokenStyle)) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
        // 32位随机字符串
        if(AuthConstant.TOKEN_STYLE_RANDOM_32.equals(tokenStyle)) {
            return RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 32);
        }
        // 64位随机字符串
        if(AuthConstant.TOKEN_STYLE_RANDOM_64.equals(tokenStyle)) {
            return RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 64);
        }
        // 128位随机字符串
        if(AuthConstant.TOKEN_STYLE_RANDOM_128.equals(tokenStyle)) {
            return RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 128);
        }
        // tik风格 (2_14_16)
        if(AuthConstant.TOKEN_STYLE_TIK.equals(tokenStyle)) {
            return StrUtil.concat(Boolean.TRUE, RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 2), "_",
                    RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 14), "_",
                    RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 16), "__");
        }
        // 默认，还是uuid
        return UUID.randomUUID().toString();
    };

    /**
     * 创建 Session 的策略
     * <p> 参数 [SessionId]
     */
    public Function<String, AuthSession> createSession = (sessionId) -> new AuthSession(sessionId);

    /**
     * 判断：集合中是否包含指定元素（模糊匹配）
     * <p> 参数 [集合, 元素]
     */
    public BiFunction<List<String>, String, Boolean> hasElement = (list, element) -> {
        // 空集合直接返回false
        if(list == null || list.size() == 0) {
            return false;
        }

        // 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配
        if(list.contains(element)) {
            return true;
        }

        // 开始模糊匹配
        for(String patt : list) {
            if(AuthFoxUtil.vagueMatch(patt, element)) {
                return true;
            }
        }

        // 走出for循环说明没有一个元素可以匹配成功
        return false;
    };

    /**
     * 对一个 [Method] 对象进行注解校验 （注解鉴权内部实现）
     * <p> 参数 [Method句柄]
     */
    public Consumer<Method> checkMethodAnnotation = (method) -> {

        // 先校验 Method 所属 Class 上的注解
        INSTANCE.checkElementAnnotation.accept(method.getDeclaringClass());

        // 再校验 Method 上的注解
        INSTANCE.checkElementAnnotation.accept(method);
    };

    /**
     * 对一个 [元素] 对象进行注解校验 （注解鉴权内部实现）
     * <p> 参数 [element元素]
     */
    public Consumer<AnnotatedElement> checkElementAnnotation = (element) -> {
        // 校验 @CheckLogin 注解
        CheckLogin checkLogin = (CheckLogin) AuthStrategy.INSTANCE.getAnnotation.apply(element, CheckLogin.class);
        if(checkLogin != null) {
            AuthManager.getStpLogic(checkLogin.type()).checkByAnnotation(checkLogin);
        }

        // 校验 @CheckRole 注解
        CheckRole checkRole = (CheckRole) AuthStrategy.INSTANCE.getAnnotation.apply(element, CheckRole.class);
        if(checkRole != null) {
            AuthManager.getStpLogic(checkRole.type()).checkByAnnotation(checkRole);
        }

        // 校验 @CheckPermission 注解
        CheckPermission checkPermission = (CheckPermission) AuthStrategy.INSTANCE.getAnnotation.apply(element,
                CheckPermission.class);
        if(checkPermission != null) {
            AuthManager.getStpLogic(checkPermission.type()).checkByAnnotation(checkPermission);
        }

        // 校验 @CheckSafe 注解
        CheckSafe checkSafe = (CheckSafe) AuthStrategy.INSTANCE.getAnnotation.apply(element, CheckSafe.class);
        if(checkSafe != null) {
            AuthManager.getStpLogic(checkSafe.type()).checkByAnnotation(checkSafe);
        }

        // 校验 @CheckBasic 注解
        CheckBasic checkBasic = (CheckBasic) AuthStrategy.INSTANCE.getAnnotation.apply(element, CheckBasic.class);
        if(checkBasic != null) {
            BasicUtil.check(checkBasic.realm(), checkBasic.account());
        }
    };

    /**
     * 从元素上获取注解（注解鉴权内部实现）
     * <p> 参数 [element元素，要获取的注解类型]
     */
    public BiFunction<AnnotatedElement, Class<? extends Annotation>, Annotation> getAnnotation = (element, annotationClass) -> {
        // 默认使用jdk的注解处理器
        return element.getAnnotation(annotationClass);
    };

    //
    // 重写策略 set连缀风格
    //

    /**
     * 重写创建 Token 的策略
     * <p> 参数 [账号id, 账号类型]
     *
     * @param createToken /
     *
     * @return 对象自身
     */
    public AuthStrategy setCreateToken(BiFunction<Object, String, String> createToken) {
        this.createToken = createToken;
        return this;
    }

    /**
     * 重写创建 Session 的策略
     * <p> 参数 [SessionId]
     *
     * @param createSession /
     *
     * @return 对象自身
     */
    public AuthStrategy setCreateSession(Function<String, AuthSession> createSession) {
        this.createSession = createSession;
        return this;
    }

    /**
     * 判断：集合中是否包含指定元素（模糊匹配）
     * <p> 参数 [集合, 元素]
     *
     * @param hasElement /
     *
     * @return 对象自身
     */
    public AuthStrategy setHasElement(BiFunction<List<String>, String, Boolean> hasElement) {
        this.hasElement = hasElement;
        return this;
    }

    /**
     * 对一个 [Method] 对象进行注解校验 （注解鉴权内部实现）
     * <p> 参数 [Method句柄]
     *
     * @param checkMethodAnnotation /
     *
     * @return 对象自身
     */
    public AuthStrategy setCheckMethodAnnotation(Consumer<Method> checkMethodAnnotation) {
        this.checkMethodAnnotation = checkMethodAnnotation;
        return this;
    }

    /**
     * 对一个 [元素] 对象进行注解校验 （注解鉴权内部实现）
     * <p> 参数 [element元素]
     *
     * @param checkElementAnnotation /
     *
     * @return 对象自身
     */
    public AuthStrategy setCheckElementAnnotation(Consumer<AnnotatedElement> checkElementAnnotation) {
        this.checkElementAnnotation = checkElementAnnotation;
        return this;
    }

    /**
     * 从元素上获取注解（注解鉴权内部实现）
     * <p> 参数 [element元素，要获取的注解类型]
     *
     * @param getAnnotation /
     *
     * @return 对象自身
     */
    public AuthStrategy setGetAnnotation(
            BiFunction<AnnotatedElement, Class<? extends Annotation>, Annotation> getAnnotation) {
        this.getAnnotation = getAnnotation;
        return this;
    }

}
