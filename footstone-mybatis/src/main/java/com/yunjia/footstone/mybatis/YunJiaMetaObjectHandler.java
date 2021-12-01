/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yunjia.footstone.common.contxt.LocalContextHolder;
import com.yunjia.footstone.common.contxt.LoginUser;
import com.yunjia.footstone.common.util.CodeGeneratorUtils;
import com.yunjia.footstone.mybatis.enums.CommonDbColumn;
import com.yunjia.footstone.mybatis.enums.YesOrNo;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 注入默认属性值
 * </p>
 *
 * @author sunkaiyun
 */
@Slf4j
@Component
public class YunJiaMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = LocalContextHolder.get().getLoginUser();
        Date now = new Date();
        // 逻辑ID
        if(canSet(metaObject, CommonDbColumn.LOGIC_CODE.getEntityFieldName())) {
            this.strictInsertFill(metaObject, CommonDbColumn.LOGIC_CODE.getEntityFieldName(), String.class,
                    CodeGeneratorUtils.nextId(null));
        }
        // 是否有效
        if(canSet(metaObject, CommonDbColumn.IS_VALID.getEntityFieldName())) {
            this.strictInsertFill(metaObject, CommonDbColumn.IS_VALID.getEntityFieldName(), Integer.class,
                    YesOrNo.YES.getCode());
        }
        // 是否删除
        if(canSet(metaObject, CommonDbColumn.IS_DELETE.getEntityFieldName())) {
            this.strictInsertFill(metaObject, CommonDbColumn.IS_DELETE.getEntityFieldName(), Integer.class,
                    YesOrNo.NO.getCode());
        }
        // 创建时间
        if(canSet(metaObject, CommonDbColumn.CREATE_TIME.getEntityFieldName())) {
            this.strictInsertFill(metaObject, CommonDbColumn.CREATE_TIME.getEntityFieldName(), Date.class, now);
        }
        // 更新时间
        if(canSet(metaObject, CommonDbColumn.UPDATE_TIME.getEntityFieldName())) {
            this.strictInsertFill(metaObject, CommonDbColumn.UPDATE_TIME.getEntityFieldName(), Date.class, now);
        }
        if(ObjectUtil.isNotNull(loginUser)) {
            // 创建人ID
            if(canSet(metaObject, CommonDbColumn.CREATE_BY_CODE.getEntityFieldName())) {
                this.strictInsertFill(metaObject, CommonDbColumn.CREATE_BY_CODE.getEntityFieldName(), String.class,
                        loginUser.getUserCode());
            }
            // 创建人姓名
            if(canSet(metaObject, CommonDbColumn.CREATE_BY_NAME.getEntityFieldName())) {
                this.strictInsertFill(metaObject, CommonDbColumn.CREATE_BY_NAME.getEntityFieldName(), String.class,
                        loginUser.getNickname());
            }
            // 修改人ID
            if(canSet(metaObject, CommonDbColumn.UPDATE_BY_CODE.getEntityFieldName())) {
                this.strictInsertFill(metaObject, CommonDbColumn.UPDATE_BY_CODE.getEntityFieldName(), String.class,
                        loginUser.getUserCode());
            }
            // 修改人姓名
            if(canSet(metaObject, CommonDbColumn.UPDATE_BY_NAME.getEntityFieldName())) {
                this.strictInsertFill(metaObject, CommonDbColumn.UPDATE_BY_NAME.getEntityFieldName(), String.class,
                        loginUser.getNickname());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = LocalContextHolder.get().getLoginUser();
        Date now = new Date();
        if(metaObject.hasSetter(CommonDbColumn.UPDATE_TIME.getEntityFieldName())) {
            this.setFieldValByName(CommonDbColumn.UPDATE_TIME.getEntityFieldName(), now, metaObject);
        }
        if(ObjectUtil.isNotNull(loginUser)) {
            // 修改人ID
            if(metaObject.hasSetter(CommonDbColumn.UPDATE_BY_CODE.getEntityFieldName())) {
                this.setFieldValByName(CommonDbColumn.UPDATE_BY_CODE.getEntityFieldName(), loginUser.getUserCode(),
                        metaObject);
            }
            // 修改人姓名
            if(metaObject.hasSetter(CommonDbColumn.UPDATE_BY_NAME.getEntityFieldName())) {
                this.setFieldValByName(CommonDbColumn.UPDATE_BY_NAME.getEntityFieldName(), loginUser.getNickname(),
                        metaObject);
            }
        }
    }

    private boolean canSet(MetaObject metaObject, String columnName) {
        if(StrUtil.isNotBlank(columnName)) {
            if(metaObject.hasSetter(columnName) && ObjectUtil.isNull(getFieldValByName(columnName, metaObject))) {
                return true;
            }
        }
        return false;
    }

}
