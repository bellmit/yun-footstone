/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import com.yunjia.footstone.auth.core.exception.AuthException;

/**
 * <p>
 * 上下文处理器 [ThreadLocal版本] ---- 对象存储器
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:28 PM
 */
public class AuthContextForThreadLocalStorage {

    /**
     * 基于 ThreadLocal 的 [Box存储器]
     */
    public static ThreadLocal<Box> boxThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 初始化 [Box存储器]
     *
     * @param request  {@link AuthRequest}
     * @param response {@link AuthResponse}
     * @param storage  {@link AuthStorage}
     */
    public static void setBox(AuthRequest request, AuthResponse response, AuthStorage storage) {
        Box bok = new Box(request, response, storage);
        boxThreadLocal.set(bok);
    }

    /**
     * 清除 [Box存储器]
     */
    public static void clearBox() {
        boxThreadLocal.remove();
    }

    /**
     * 获取 [Box存储器]
     *
     * @return see note
     */
    public static Box getBox() {
        return boxThreadLocal.get();
    }

    /**
     * 获取 [Box存储器], 如果为空则抛出异常
     *
     * @return see note
     */
    public static Box getBoxNotNull() {
        Box box = boxThreadLocal.get();
        if(box == null) {
            throw new AuthException("未成功初始化上下文");
        }
        return box;
    }

    /**
     * 在 [Box存储器] 获取 [Request] 对象
     *
     * @return see note
     */
    public static AuthRequest getRequest() {
        return getBoxNotNull().getRequest();
    }

    /**
     * 在 [Box存储器] 获取 [Response] 对象
     *
     * @return see note
     */
    public static AuthResponse getResponse() {
        return getBoxNotNull().getResponse();
    }

    /**
     * 在 [Box存储器] 获取 [存储器] 对象
     *
     * @return see note
     */
    public static AuthStorage getStorage() {
        return getBoxNotNull().getStorage();
    }

    /**
     * 临时内部类，用于存储[request、response、storage]三个对象
     * @author kong
     */
    /**
     * @author kong
     */
    public static class Box {

        public AuthRequest request;

        public AuthResponse response;

        public AuthStorage storage;

        public Box(AuthRequest request, AuthResponse response, AuthStorage storage) {
            this.request = request;
            this.response = response;
            this.storage = storage;
        }

        public AuthRequest getRequest() {
            return request;
        }

        public void setRequest(AuthRequest request) {
            this.request = request;
        }

        public AuthResponse getResponse() {
            return response;
        }

        public void setResponse(AuthResponse response) {
            this.response = response;
        }

        public AuthStorage getStorage() {
            return storage;
        }

        public void setStorage(AuthStorage storage) {
            this.storage = storage;
        }

        @Override
        public String toString() {
            return "Box [request=" + request + ", response=" + response + ", storage=" + storage + "]";
        }

    }

}
