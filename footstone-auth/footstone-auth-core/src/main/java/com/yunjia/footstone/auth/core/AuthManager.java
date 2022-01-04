/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core;

import com.yunjia.footstone.auth.core.config.AuthConfigFactory;
import com.yunjia.footstone.auth.core.context.AuthContext;
import com.yunjia.footstone.auth.core.context.DefaultAuthContextImpl;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import com.yunjia.footstone.auth.core.repository.DefaultAuthRepositoryImpl;
import com.yunjia.footstone.auth.core.temp.DefaultTokenTempImpl;
import com.yunjia.footstone.auth.core.temp.TokenTempInterface;
import com.yunjia.footstone.auth.core.config.AuthConfig;
import com.yunjia.footstone.auth.core.listener.AuthListener;
import com.yunjia.footstone.auth.core.listener.DefaultAuthListenerImpl;
import com.yunjia.footstone.auth.core.stp.DefaultStpInterfaceImpl;
import com.yunjia.footstone.auth.core.stp.StpInterface;
import com.yunjia.footstone.auth.core.stp.StpLogic;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理 Footstone-Authority 所有全局组件
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/23 7:40 PM
 */
public class AuthManager {

    /**
     * 配置文件 Bean
     */
    public volatile static AuthConfig config;

    public static void setConfig(AuthConfig config) {
        AuthManager.config = config;
        // 调用一次StpUtil中的方法，保证其可以尽早的初始化 StpLogic
        //StpUtil.getLoginType();
    }

    public static AuthConfig getConfig() {
        if(config == null) {
            synchronized(AuthManager.class) {
                if(config == null) {
                    setConfig(AuthConfigFactory.createConfig());
                }
            }
        }
        return config;
    }

    /**
     * 持久化 Bean
     */
    private volatile static AuthRepository authRepository;

    public static void setAuthRepository(AuthRepository authRepository) {
        if((AuthManager.authRepository instanceof DefaultAuthRepositoryImpl)) {
            ((DefaultAuthRepositoryImpl) AuthManager.authRepository).endRefreshThread();
        }
        AuthManager.authRepository = authRepository;
    }

    public static AuthRepository getAuthRepository() {
        if(authRepository == null) {
            synchronized(AuthManager.class) {
                if(authRepository == null) {
                    setAuthRepository(new DefaultAuthRepositoryImpl());
                }
            }
        }
        return authRepository;
    }

    /**
     * 权限认证 Bean
     */
    private volatile static StpInterface stpInterface;
    public static void setStpInterface(StpInterface stpInterface) {
        AuthManager.stpInterface = stpInterface;
    }
    public static StpInterface getStpInterface() {
        if (stpInterface == null) {
            synchronized (AuthManager.class) {
                if (stpInterface == null) {
                    setStpInterface(new DefaultStpInterfaceImpl());
                }
            }
        }
        return stpInterface;
    }

    /**
     * 上下文Context Bean
     */
    private volatile static AuthContext authContext;
    public static void setAuthContext(AuthContext authContext) {
        AuthManager.authContext = authContext;
    }
    public static AuthContext getAuthContext() {
        if(authContext.isValid()) {
            return authContext;
        }
        return DefaultAuthContextImpl.defaultContext;
    }

    /**
     * 侦听器 Bean
     */
    private volatile static AuthListener authListener;

    public static void setAuthListener(AuthListener authListener) {
        AuthManager.authListener = authListener;
    }

    public static AuthListener getAuthListener() {
        if(authListener == null) {
            synchronized(AuthManager.class) {
                if(authListener == null) {
                    setAuthListener(new DefaultAuthListenerImpl());
                }
            }
        }
        return authListener;
    }

    /**
     * 临时令牌验证模块 Bean
     */
    private volatile static TokenTempInterface tokenTemp;
    public static void setTokenTemp(TokenTempInterface tokenTemp) {
        AuthManager.tokenTemp = tokenTemp;
    }
    public static TokenTempInterface getTokenTemp() {
        if (tokenTemp == null) {
            synchronized (AuthManager.class) {
                if (tokenTemp == null) {
                    setTokenTemp(new DefaultTokenTempImpl());
                }
            }
        }
        return tokenTemp;
    }

    /**
     * StpLogic集合, 记录框架所有成功初始化的StpLogic
     */
    public static Map<String, StpLogic> stpLogicMap = new HashMap<String, StpLogic>();

    /**
     * 向集合中 put 一个 StpLogic
     *
     * @param stpLogic StpLogic
     */
    public static void putStpLogic(StpLogic stpLogic) {
        stpLogicMap.put(stpLogic.getLoginType(), stpLogic);
    }

    /**
     * 根据 LoginType 获取对应的StpLogic，如果不存在则抛出异常
     *
     * @param loginType 对应的账号类型
     *
     * @return 对应的StpLogic
     */
    public static StpLogic getStpLogic(String loginType) {
        // 如果type为空则返回框架内置的
        if(loginType == null || loginType.isEmpty()) {
            return StpUtil.stpLogic;
        }

        // 从SaManager中获取
        StpLogic stpLogic = stpLogicMap.get(loginType);
        if(stpLogic == null) {
            /*
             * 此时有两种情况会造成 StpLogic == null
             * 1. loginType拼写错误，请改正 （建议使用常量）
             * 2. 自定义StpUtil尚未初始化（静态类中的属性至少一次调用后才会初始化），解决方法两种
             * 		(1) 从main方法里调用一次
             * 		(2) 在自定义StpUtil类加上类似 @Component 的注解让容器启动时扫描到自动初始化
             */
            throw new AuthException("未能获取对应StpLogic，type=" + loginType);
        }

        // 返回
        return stpLogic;
    }
}
