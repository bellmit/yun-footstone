/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.core;

import com.yunjia.footstone.auth.core.context.AuthHolder;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.auth.oauth2.OAuth2Manager;
import com.yunjia.footstone.auth.oauth2.config.OAuth2Config;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Constants.Api;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Constants.GrantType;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Constants.Param;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Constants.ResponseType;
import com.yunjia.footstone.auth.oauth2.exception.OAuth2Exception;
import com.yunjia.footstone.auth.oauth2.model.AccessTokenModel;
import com.yunjia.footstone.auth.oauth2.model.ClientTokenModel;
import com.yunjia.footstone.auth.oauth2.model.CodeModel;
import com.yunjia.footstone.auth.oauth2.model.RequestAuthModel;
import com.yunjia.footstone.common.domain.CommonResponse;

/**
 * <p>
 * OAuth2 请求处理类封装
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/30 9:29 AM
 */
public class OAuth2Handle {

    /**
     * 处理Server端请求， 路由分发
     * @return 处理结果
     */
    public static Object serverRequest() {

        // 获取变量
        AuthRequest req = AuthHolder.getRequest();
        AuthResponse res = AuthHolder.getResponse();
        OAuth2Config cfg = OAuth2Manager.getConfig();

        // ------------------ 路由分发 ------------------

        // 模式一：Code授权码
        if(req.isPath(Api.authorize) && req.isParam(Param.response_type, ResponseType.code) && cfg.isCode) {
            return authorize(req, res, cfg);
        }

        // Code授权码 获取 Access-Token
        if(req.isPath(Api.token) && req.isParam(Param.grant_type, GrantType.authorization_code)) {
            return token(req, res, cfg);
        }

        // Refresh-Token 刷新 Access-Token
        if(req.isPath(Api.refresh) && req.isParam(Param.grant_type, GrantType.refresh_token)) {
            return refreshToken(req);
        }

        // 回收 Access-Token
        if(req.isPath(Api.revoke)) {
            return revokeToken(req);
        }

        // doLogin 登录接口
        if(req.isPath(Api.doLogin)) {
            return doLogin(req, res, cfg);
        }

        // doConfirm 确认授权接口
        if(req.isPath(Api.doConfirm)) {
            return doConfirm(req);
        }

        // 模式二：隐藏式
        if(req.isPath(Api.authorize) && req.isParam(Param.response_type, ResponseType.token) && cfg.isImplicit) {
            return authorize(req, res, cfg);
        }

        // 模式三：密码式
        if(req.isPath(Api.token) && req.isParam(Param.grant_type, GrantType.password) && cfg.isPassword) {
            return password(req, res, cfg);
        }

        // 模式四：凭证式
        if(req.isPath(Api.client_token) && req.isParam(Param.grant_type, GrantType.client_credentials) && cfg.isClient) {
            return clientToken(req, res, cfg);
        }

        // 默认返回
        return OAuth2Constants.NOT_HANDLE;
    }

    /**
     * 模式一：Code授权码 / 模式二：隐藏式
     * @param req 请求对象
     * @param res 响应对象
     * @param cfg 配置对象
     * @return 处理结果
     */
    public static Object authorize(AuthRequest req, AuthResponse res, OAuth2Config cfg) {

        // 1、如果尚未登录, 则先去登录
        if(StpUtil.isLogin() == false) {
            return cfg.notLoginView.get();
        }

        // 2、构建请求Model
        RequestAuthModel ra = OAuth2Util.generateRequestAuth(req, StpUtil.getLoginId());

        // 3、校验：重定向域名是否合法
        OAuth2Util.checkRightUrl(ra.clientId, ra.redirectUri);

        // 4、校验：此次申请的Scope，该Client是否已经签约
        OAuth2Util.checkContract(ra.clientId, ra.scope);

        // 5、判断：如果此次申请的Scope，该用户尚未授权，则转到授权页面
        boolean isGrant = OAuth2Util.isGrant(ra.loginId, ra.clientId, ra.scope);
        if(isGrant == false) {
            return cfg.confirmView.apply(ra.clientId, ra.scope);
        }

        // 6、判断授权类型
        // 如果是 授权码式，则：开始重定向授权，下放code
        if(ResponseType.code.equals(ra.responseType)) {
            CodeModel codeModel = OAuth2Util.generateCode(ra);
            String redirectUri = OAuth2Util.buildRedirectUri(ra.redirectUri, codeModel.code, ra.state);
            return res.redirect(redirectUri);
        }
        // 如果是 隐藏式，则：开始重定向授权，下放 token
        if(ResponseType.token.equals(ra.responseType)) {
            AccessTokenModel at = OAuth2Util.generateAccessToken(ra, false);
            String redirectUri = OAuth2Util.buildImplicitRedirectUri(ra.redirectUri, at.accessToken, ra.state);
            return res.redirect(redirectUri);
        }

        // 默认返回
        throw new OAuth2Exception("无效response_type: " + ra.responseType);
    }

    /**
     * Code授权码 获取 Access-Token
     * @param req 请求对象
     * @param res 响应对象
     * @param cfg 配置对象
     * @return 处理结果
     */
    public static Object token(AuthRequest req, AuthResponse res, OAuth2Config cfg) {
        // 获取参数
        String code = req.getParamNotNull(Param.code);
        String clientId = req.getParamNotNull(Param.client_id);
        String clientSecret = req.getParamNotNull(Param.client_secret);
        String redirectUri = req.getParam(Param.redirect_uri);

        // 校验参数
        OAuth2Util.checkGainTokenParam(code, clientId, clientSecret, redirectUri);

        // 构建 Access-Token
        AccessTokenModel token = OAuth2Util.generateAccessToken(code);

        // 返回
        return CommonResponse.responseOk(token.toLineMap());
    }

    /**
     * Refresh-Token 刷新 Access-Token
     * @param req 请求对象
     * @return 处理结果
     */
    public static Object refreshToken(AuthRequest req) {
        // 获取参数
        String clientId = req.getParamNotNull(Param.client_id);
        String clientSecret = req.getParamNotNull(Param.client_secret);
        String refreshToken = req.getParamNotNull(Param.refresh_token);

        // 校验参数
        OAuth2Util.checkRefreshTokenParam(clientId, clientSecret, refreshToken);

        // 获取新Token返回
        Object data = OAuth2Util.refreshAccessToken(refreshToken).toLineMap();
        return CommonResponse.responseOk(data);
    }

    /**
     * 回收 Access-Token
     * @param req 请求对象
     * @return 处理结果
     */
    public static Object revokeToken(AuthRequest req) {
        // 获取参数
        String clientId = req.getParamNotNull(Param.client_id);
        String clientSecret = req.getParamNotNull(Param.client_secret);
        String accessToken = req.getParamNotNull(Param.access_token);

        // 如果 Access-Token 不存在，直接返回
        if(OAuth2Util.getAccessToken(accessToken) == null) {
            return CommonResponse.responseOkMsg("access_token不存在：" + accessToken);
        }

        // 校验参数
        OAuth2Util.checkAccessTokenParam(clientId, clientSecret, accessToken);

        // 获取新Token返回
        OAuth2Util.revokeAccessToken(accessToken);
        return CommonResponse.responseOk();
    }

    /**
     * doLogin 登录接口
     * @param req 请求对象
     * @param res 响应对象
     * @param cfg 配置对象
     * @return 处理结果
     */
    public static Object doLogin(AuthRequest req, AuthResponse res, OAuth2Config cfg) {
        return cfg.doLoginHandle.apply(req.getParamNotNull(Param.name), req.getParamNotNull("pwd"));
    }

    /**
     * doConfirm 确认授权接口
     * @param req 请求对象
     * @return 处理结果
     */
    public static Object doConfirm(AuthRequest req) {
        String clientId = req.getParamNotNull(Param.client_id);
        String scope = req.getParamNotNull(Param.scope);
        Object loginId = StpUtil.getLoginId();
        OAuth2Util.saveGrantScope(clientId, loginId, scope);
        return CommonResponse.responseOk();
    }

    /**
     * 模式三：密码式
     * @param req 请求对象
     * @param res 响应对象
     * @param cfg 配置对象
     * @return 处理结果
     */
    public static Object password(AuthRequest req, AuthResponse res, OAuth2Config cfg) {

        // 1、获取请求参数
        String username = req.getParamNotNull(Param.username);
        String password = req.getParamNotNull(Param.password);
        String clientId = req.getParamNotNull(Param.client_id);

        // 2、校验client_id
        OAuth2Util.checkClientModel(clientId);

        // 3、防止因前端误传token造成逻辑干扰
        AuthHolder.getStorage().set(StpUtil.stpLogic.splicingKeyJustCreatedSave(), "no-token");

        // 4、调用API 开始登录，如果没能成功登录，则直接退出
        Object retObj = cfg.doLoginHandle.apply(username, password);
        if(StpUtil.isLogin() == false) {
            return retObj;
        }

        // 5、构建 ra对象
        RequestAuthModel ra = new RequestAuthModel();
        ra.clientId = clientId;
        ra.loginId = StpUtil.getLoginId();
        ra.scope = req.getParam(Param.scope, "");

        // 6、校验 ClientScope
        OAuth2Util.checkContract(clientId, ra.scope);

        // 7、生成 Access-Token
        AccessTokenModel at = OAuth2Util.generateAccessToken(ra, true);

        // 8、返回 Access-Token
        return CommonResponse.responseOk(at.toLineMap());
    }

    /**
     * 模式四：凭证式
     * @param req 请求对象
     * @param res 响应对象
     * @param cfg 配置对象
     * @return 处理结果
     */
    public static Object clientToken(AuthRequest req, AuthResponse res, OAuth2Config cfg) {

        // 获取参数
        String clientId = req.getParamNotNull(Param.client_id);
        String clientSecret = req.getParamNotNull(Param.client_secret);
        String scope = req.getParam(Param.scope);

        //校验 ClientScope
        OAuth2Util.checkContract(clientId, scope);

        // 校验 ClientSecret
        OAuth2Util.checkClientSecret(clientId, clientSecret);

        // 返回 Client-Token
        ClientTokenModel ct = OAuth2Util.generateClientToken(clientId, scope);

        // 返回 Client-Token
        return CommonResponse.responseOk(ct.toLineMap());
    }
}
