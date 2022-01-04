/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.router;

import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.context.AuthHolder;
import com.yunjia.footstone.auth.core.function.VoidFunction;
import com.yunjia.footstone.auth.core.exception.BackResultException;
import com.yunjia.footstone.auth.core.exception.StopMatchException;
import com.yunjia.footstone.auth.core.function.VoidParamFunction;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 路由匹配操作工具类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:25 PM
 */
public class AuthRouter {

    // -------------------- 路由匹配相关 -------------------- 

    /**
     * 路由匹配
     *
     * @param pattern 路由匹配符
     * @param path    被匹配的路由
     *
     * @return 是否匹配成功
     */
    public static boolean isMatch(String pattern, String path) {
        return AuthManager.getAuthContext().matchPath(pattern, path);
    }

    /**
     * 路由匹配
     *
     * @param patterns 路由匹配符集合
     * @param path     被匹配的路由
     *
     * @return 是否匹配成功
     */
    public static boolean isMatch(List<String> patterns, String path) {
        if(patterns == null) {
            return false;
        }
        for(String pattern : patterns) {
            if(isMatch(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 路由匹配
     *
     * @param patterns 路由匹配符数组
     * @param path     被匹配的路由
     *
     * @return 是否匹配成功
     */
    public static boolean isMatch(String[] patterns, String path) {
        if(patterns == null) {
            return false;
        }
        for(String pattern : patterns) {
            if(isMatch(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Http请求方法匹配
     *
     * @param methods      Http请求方法断言数组
     * @param methodString Http请求方法
     *
     * @return 是否匹配成功
     */
    public static boolean isMatch(HttpMethod[] methods, String methodString) {
        if(methods == null) {
            return false;
        }
        for(HttpMethod method : methods) {
            if(method == HttpMethod.ALL || (method != null && method.toString().equalsIgnoreCase(methodString))) {
                return true;
            }
        }
        return false;
    }

    // ------ 使用当前URI匹配 

    /**
     * 路由匹配 (使用当前URI)
     *
     * @param pattern 路由匹配符
     *
     * @return 是否匹配成功
     */
    public static boolean isMatchCurrURI(String pattern) {
        return isMatch(pattern, AuthHolder.getRequest().getRequestPath());
    }

    /**
     * 路由匹配 (使用当前URI)
     *
     * @param patterns 路由匹配符集合
     *
     * @return 是否匹配成功
     */
    public static boolean isMatchCurrURI(List<String> patterns) {
        return isMatch(patterns, AuthHolder.getRequest().getRequestPath());
    }

    /**
     * 路由匹配 (使用当前URI)
     *
     * @param patterns 路由匹配符数组
     *
     * @return 是否匹配成功
     */
    public static boolean isMatchCurrURI(String[] patterns) {
        return isMatch(patterns, AuthHolder.getRequest().getRequestPath());
    }

    /**
     * Http请求方法匹配 (使用当前请求方式)
     *
     * @param methods Http请求方法断言数组
     *
     * @return 是否匹配成功
     */
    public static boolean isMatchCurrMethod(HttpMethod[] methods) {
        return isMatch(methods, AuthHolder.getRequest().getMethod());
    }

    // -------------------- 开始匹配 --------------------

    /**
     * 初始化一个AuthRouterStaff，开始匹配
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff newMatch() {
        return new AuthRouterStaff();
    }

    // ----------------- path匹配 

    /**
     * 路由匹配
     *
     * @param patterns 路由匹配符集合
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff match(String... patterns) {
        return new AuthRouterStaff().match(patterns);
    }

    /**
     * 路由匹配排除
     *
     * @param patterns 路由匹配符排除数组
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff notMatch(String... patterns) {
        return new AuthRouterStaff().notMatch(patterns);
    }

    /**
     * 路由匹配
     *
     * @param patterns 路由匹配符集合
     *
     * @return 对象自身
     */
    public static AuthRouterStaff match(List<String> patterns) {
        return new AuthRouterStaff().match(patterns);
    }

    /**
     * 路由匹配排除
     *
     * @param patterns 路由匹配符排除集合
     *
     * @return 对象自身
     */
    public static AuthRouterStaff notMatch(List<String> patterns) {
        return new AuthRouterStaff().notMatch(patterns);
    }

    // ----------------- Method匹配 

    /**
     * Http请求方式匹配 (Enum)
     *
     * @param methods Http请求方法断言数组
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff match(HttpMethod... methods) {
        return new AuthRouterStaff().match(methods);
    }

    /**
     * Http请求方法匹配排除 (Enum)
     *
     * @param methods Http请求方法断言排除数组
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff notMatch(HttpMethod... methods) {
        return new AuthRouterStaff().notMatch(methods);
    }

    /**
     * Http请求方法匹配 (String)
     *
     * @param methods Http请求方法断言数组
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff matchMethod(String... methods) {
        return new AuthRouterStaff().matchMethod(methods);
    }

    /**
     * Http请求方法匹配排除 (String)
     *
     * @param methods Http请求方法断言排除数组
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff notMatchMethod(String... methods) {
        return new AuthRouterStaff().notMatchMethod(methods);
    }

    // ----------------- 条件匹配 

    /**
     * 根据 boolean 值进行匹配
     *
     * @param flag boolean值
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff match(boolean flag) {
        return new AuthRouterStaff().match(flag);
    }

    /**
     * 根据 boolean 值进行匹配排除
     *
     * @param flag boolean值
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff notMatch(boolean flag) {
        return new AuthRouterStaff().notMatch(flag);
    }

    /**
     * 根据自定义方法进行匹配 (lazy)
     *
     * @param fun 自定义方法
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff match(Function<Object, Boolean> fun) {
        return new AuthRouterStaff().match(fun);
    }

    /**
     * 根据自定义方法进行匹配排除 (lazy)
     *
     * @param fun 自定义排除方法
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff notMatch(Function<Object, Boolean> fun) {
        return new AuthRouterStaff().notMatch(fun);
    }

    // -------------------- 直接指定check函数 --------------------

    /**
     * 路由匹配，如果匹配成功则执行认证函数
     *
     * @param pattern 路由匹配符
     * @param fun     要执行的校验方法
     *
     * @return /
     */
    public static AuthRouterStaff match(String pattern, VoidFunction fun) {
        return new AuthRouterStaff().match(pattern, fun);
    }

    /**
     * 路由匹配，如果匹配成功则执行认证函数
     *
     * @param pattern 路由匹配符
     * @param fun     要执行的校验方法
     *
     * @return /
     */
    public static AuthRouterStaff match(String pattern, VoidParamFunction<AuthRouterStaff> fun) {
        return new AuthRouterStaff().match(pattern, fun);
    }

    /**
     * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数
     *
     * @param pattern        路由匹配符
     * @param excludePattern 要排除的路由匹配符
     * @param fun            要执行的方法
     *
     * @return /
     */
    public static AuthRouterStaff match(String pattern, String excludePattern, VoidFunction fun) {
        return new AuthRouterStaff().match(pattern, excludePattern, fun);
    }

    /**
     * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数
     *
     * @param pattern        路由匹配符
     * @param excludePattern 要排除的路由匹配符
     * @param fun            要执行的方法
     *
     * @return /
     */
    public static AuthRouterStaff match(String pattern, String excludePattern, VoidParamFunction<AuthRouterStaff> fun) {
        return new AuthRouterStaff().match(pattern, excludePattern, fun);
    }

    // -------------------- 提前退出 --------------------

    /**
     * 停止匹配，跳出函数 (在多个匹配链中一次性跳出Auth函数)
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff stop() {
        throw new StopMatchException();
    }

    /**
     * 停止匹配，结束执行，向前端返回结果
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff back() {
        throw new BackResultException("");
    }

    /**
     * 停止匹配，结束执行，向前端返回结果
     *
     * @param result 要输出的结果
     *
     * @return AuthRouterStaff
     */
    public static AuthRouterStaff back(Object result) {
        throw new BackResultException(result);
    }

    // -------------------- 历史API兼容 --------------------

    /**
     * <h1>本函数设计已过时，请更换为：SaRouter.match(path...).ckeck(fun) </h1>
     * 路由匹配，如果匹配成功则执行认证函数
     *
     * @param patterns 路由匹配符集合
     * @param function 要执行的方法
     */
    @Deprecated
    public static void match(List<String> patterns, VoidFunction function) {
        if(isMatchCurrURI(patterns)) {
            function.run();
        }
    }

    /**
     * <h1>本函数设计已过时，请更换为：SaRouter.match(path...).notMatch(path...).ckeck(fun) </h1>
     * 路由匹配 (并指定排除匹配符)，如果匹配成功则执行认证函数
     *
     * @param patterns        路由匹配符集合
     * @param excludePatterns 要排除的路由匹配符集合
     * @param function        要执行的方法
     */
    @Deprecated
    public static void match(List<String> patterns, List<String> excludePatterns, VoidFunction function) {
        if(isMatchCurrURI(patterns)) {
            if(isMatchCurrURI(excludePatterns) == false) {
                function.run();
            }
        }
    }
}
