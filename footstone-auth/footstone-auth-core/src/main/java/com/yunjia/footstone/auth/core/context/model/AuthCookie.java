/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context.model;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.exception.AuthException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * Cookie Model
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/24 6:46 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthCookie {

    /**
     * 写入响应头时使用的key
     */
    public static final String HEADER_NAME = "Set-Cookie";

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 有效时长 （单位：秒），-1:代表为临时Cookie 浏览器关闭后自动删除
     */
    private long maxAge = -1;

    /**
     * 域
     */
    private String domain;

    /**
     * 路径
     */
    private String path;

    /**
     * 是否只在 https 协议下有效
     */
    private Boolean secure = false;

    /**
     * 是否禁止 js 操作 Cookie
     */
    private Boolean httpOnly = false;

    /**
     * 第三方限制级别（Strict=完全禁止，Lax=部分允许，None=不限制）
     */
    private String sameSite;

    /**
     * 构建一下
     */
    public void builderWithPath() {
        if(path == null) {
            path = "/";
        }
    }

    /**
     * 转换为响应头 Set-Cookie 参数需要的值
     *
     * @return /
     */
    public String toHeaderValue() {
        this.builderWithPath();

        if(StrUtil.isBlank(name)) {
            throw new AuthException("name不能为空");
        }
        if(value != null && StrUtil.contains(value, AuthConstant.SEMICOLON)) {
            throw new AuthException("无效Value：" + value);
        }

        // Set-Cookie: name=value; Max-Age=100000; Expires=Tue, 05-Oct-2021 20:28:17 GMT; Domain=localhost; Path=/; Secure; HttpOnly; SameSite=Lax

        StringBuffer sb = new StringBuffer();
        sb.append(name + "=" + value);

        if(maxAge >= 0) {
            sb.append("; Max-Age=" + maxAge);
            String expires;
            if(maxAge == 0) {
                expires = Instant.EPOCH.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);
            } else {
                expires = OffsetDateTime.now().plusSeconds(maxAge).format(DateTimeFormatter.RFC_1123_DATE_TIME);
            }
            sb.append("; Expires=" + expires);
        }
        if(StrUtil.isBlank(domain) == false) {
            sb.append("; Domain=" + domain);
        }
        if(StrUtil.isBlank(path) == false) {
            sb.append("; Path=" + path);
        }
        if(secure) {
            sb.append("; Secure");
        }
        if(httpOnly) {
            sb.append("; HttpOnly");
        }
        if(StrUtil.isBlank(sameSite) == false) {
            sb.append("; sameSite=" + sameSite);
        }

        return sb.toString();
    }
}
