/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.jwt;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.auth.core.exception.NotLoginException;
import com.yunjia.footstone.auth.core.repository.AuthRepository;

/**
 * <p>
 * jwt操作工具类封装
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 5:04 PM
 */
public class AuthJwtUtil {

    /**
     * key：账号类型
     */
    public static final String LOGIN_TYPE = "loginType";

    /**
     * key：账号id
     */
    public static final String LOGIN_ID = "loginId";

    /**
     * key：登录设备
     */
    public static final String DEVICE = "device";

    /**
     * key：有效截止期 (时间戳)
     */
    public static final String EFF = "eff";

    /**
     * 当有效期被设为此值时，代表永不过期
     */
    public static final long NEVER_EXPIRE = AuthRepository.NEVER_EXPIRE;


    /**
     * 创建 jwt （简单方式）
     *
     * @param loginId 账号id
     * @param key     秘钥
     *
     * @return jwt-token
     */
    public static String createToken(Object loginId, String key) {

        // 秘钥不可以为空
        AuthException.throwByNull(key, "请配置jwt秘钥");

        // 构建
        String token = JWT.create().setPayload(LOGIN_ID, loginId)
                // 混入随机字符
                .setPayload("rn", RandomUtil.randomString(AuthConstant.BASE_CHAR_NUMBER, 32)).setKey(key.getBytes())
                .sign();

        // 返回
        return token;
    }

    /**
     * 创建 jwt （全参数方式）
     *
     * @param loginType 账号类型
     * @param loginId   账号id
     * @param device    设备标识
     * @param timeout   token有效期 (单位 秒)
     * @param key       秘钥
     *
     * @return jwt-token
     */
    public static String createToken(String loginType, Object loginId, String device, long timeout, String key) {

        // 秘钥不可以为空
        AuthException.throwByNull(key, "请配置jwt秘钥");

        // 计算有效期
        long effTime = timeout;
        if(timeout != NEVER_EXPIRE) {
            effTime = timeout * 1000 + System.currentTimeMillis();
        }

        // 构建
        String token = JWT.create().setPayload(LOGIN_TYPE, loginType).setPayload(LOGIN_ID, loginId)
                .setPayload(DEVICE, device).setPayload(EFF, effTime).setKey(key.getBytes()).sign();

        // 返回
        return token;
    }

    /**
     * jwt 解析（校验签名和密码）
     *
     * @param token Jwt-Token值
     * @param key   秘钥
     *
     * @return 解析后的jwt 对象
     */
    public static JWT parseToken(String token, String key) {

        // 如果token为null
        if(token == null) {
            throw NotLoginException.newInstance(null, NotLoginException.NOT_TOKEN);
        }

        // 解析
        JWT jwt = null;
        try {
            jwt = JWT.of(token);
        } catch(JWTException e) {
            // 解析失败
            throw NotLoginException.newInstance(null, NotLoginException.INVALID_TOKEN, token);
        }
        JSONObject payloads = jwt.getPayloads();

        // 校验 Token 签名
        boolean verify = jwt.setKey(key.getBytes()).verify();
        if(verify == false) {
            throw NotLoginException.newInstance(payloads.getStr(LOGIN_TYPE), NotLoginException.INVALID_TOKEN, token);
        }
        ;

        // 校验 Token 有效期
        Long effTime = payloads.getLong(EFF, 0L);
        if(effTime != NEVER_EXPIRE) {
            if(effTime == null || effTime < System.currentTimeMillis()) {
                throw NotLoginException.newInstance(payloads.getStr(LOGIN_TYPE), NotLoginException.TOKEN_TIMEOUT,
                        token);
            }
        }

        // 返回
        return jwt;
    }

    /**
     * 获取 jwt 数据载荷 （校验签名和密码）
     *
     * @param token token值
     * @param key   秘钥
     *
     * @return 载荷
     */
    public static JSONObject getPayloads(String token, String key) {
        return parseToken(token, key).getPayloads();
    }

    /**
     * 获取 jwt 数据载荷 （不校验签名和密码）
     *
     * @param token token值
     * @param key   秘钥
     *
     * @return 载荷
     */
    public static JSONObject getPayloadsNotCheck(String token, String key) {
        try {
            JWT jwt = JWT.of(token);
            JSONObject payloads = jwt.getPayloads();
            return payloads;
        } catch(JWTException e) {
            return new JSONObject();
        }
    }

    /**
     * 获取 jwt 代表的账号id
     *
     * @param token Token值
     * @param key   秘钥
     *
     * @return 值
     */
    public static Object getLoginId(String token, String key) {
        return getPayloads(token, key).get(LOGIN_ID);
    }

    /**
     * 获取 jwt 代表的账号id (未登录时返回null)
     *
     * @param token Token值
     * @param key   秘钥
     *
     * @return 值
     */
    public static Object getLoginIdOrNull(String token, String key) {
        try {
            return getPayloads(token, key).get(LOGIN_ID);
        } catch(NotLoginException e) {
            return null;
        }
    }

    /**
     * 获取 jwt 剩余有效期
     *
     * @param token JwtToken值
     * @param key   秘钥
     *
     * @return 值
     */
    public static long getTimeout(String token, String key) {

        // 如果token为null
        if(token == null) {
            return AuthRepository.NOT_VALUE_EXPIRE;
        }

        // 取出数据
        JWT jwt = null;
        try {
            jwt = JWT.of(token);
        } catch(JWTException e) {
            // 解析失败
            return AuthRepository.NOT_VALUE_EXPIRE;
        }
        JSONObject payloads = jwt.getPayloads();

        // 如果签名无效
        boolean verify = jwt.setKey(key.getBytes()).verify();
        if(verify == false) {
            return AuthRepository.NOT_VALUE_EXPIRE;
        }
        ;

        // 如果被设置为：永不过期
        Long effTime = payloads.get(EFF, Long.class);
        if(effTime == NEVER_EXPIRE) {
            return NEVER_EXPIRE;
        }
        // 如果已经超时
        if(effTime == null || effTime < System.currentTimeMillis()) {
            return AuthRepository.NOT_VALUE_EXPIRE;
        }

        // 计算timeout (转化为以秒为单位的有效时间)
        return (effTime - System.currentTimeMillis()) / 1000;
    }

}
