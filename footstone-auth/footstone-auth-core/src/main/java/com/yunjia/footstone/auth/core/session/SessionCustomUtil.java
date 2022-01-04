/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.session;

import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.strategy.AuthStrategy;

/**
 * <p>
 * 自定义 Session 工具类
 * </p>
 * <p>样例：
 * <pre>
 *  	// 在一处代码写入数据
 *  	AuthSession session = SessionCustomUtil.getSessionById("role-" + 1001);
 *  	session.set("count", 1);
 *
 *      // 在另一处代码获取数据
 *  	AuthSession session = SessionCustomUtil.getSessionById("role-" + 1001);
 *  	int count = session.getInt("count");
 *      System.out.println("count=" + count);
 * </pre>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:53 PM
 */
public class SessionCustomUtil {

    /**
     * 添加上指定前缀，防止恶意伪造Session
     */
    public static String sessionKey = "custom";

    /**
     * 拼接Key: 自定义Session的Id
     *
     * @param sessionId 会话id
     *
     * @return sessionId
     */
    public static String splicingSessionKey(String sessionId) {
        return AuthManager.getConfig().getTokenName() + ":" + sessionKey + ":session:" + sessionId;
    }

    /**
     * 指定key的Session是否存在
     *
     * @param sessionId Session的id
     *
     * @return 是否存在
     */
    public static boolean isExists(String sessionId) {
        return AuthManager.getAuthRepository().getSession(splicingSessionKey(sessionId)) != null;
    }

    /**
     * 获取指定key的Session
     *
     * @param sessionId key
     * @param isCreate  如果此Session尚未在DB创建，是否新建并返回
     *
     * @return SaSession
     */
    public static AuthSession getSessionById(String sessionId, boolean isCreate) {
        AuthSession session = AuthManager.getAuthRepository().getSession(splicingSessionKey(sessionId));
        if(session == null && isCreate) {
            session = AuthStrategy.INSTANCE.createSession.apply(splicingSessionKey(sessionId));
            AuthManager.getAuthRepository().setSession(session, AuthManager.getConfig().getTimeout());
        }
        return session;
    }

    /**
     * 获取指定key的Session, 如果此Session尚未在DB创建，则新建并返回
     *
     * @param sessionId key
     *
     * @return session对象
     */
    public static AuthSession getSessionById(String sessionId) {
        return getSessionById(sessionId, true);
    }

    /**
     * 删除指定key的Session
     *
     * @param sessionId 指定key
     */
    public static void deleteSessionById(String sessionId) {
        AuthManager.getAuthRepository().deleteSession(splicingSessionKey(sessionId));
    }

}
