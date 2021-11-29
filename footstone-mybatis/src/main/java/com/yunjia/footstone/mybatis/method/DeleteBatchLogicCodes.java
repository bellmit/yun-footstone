/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.yunjia.footstone.mybatis.enums.CommonDbColumn;
import com.yunjia.footstone.mybatis.enums.CommonSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * <p>
 * 根据逻辑编号批量删除
 * </p>
 * @author sunkaiyun
 */
public class DeleteBatchLogicCodes extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        CommonSqlMethod sqlMethod = CommonSqlMethod.LOGIC_DELETE_BATCH_BY_LOGIC_CODES;
        String sql;
        SqlSource sqlSource;
        if(tableInfo.isWithLogicDelete()) {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlLogicSet(tableInfo),
                    CommonDbColumn.LOGIC_CODE.getDbFieldName(),
                    SqlScriptUtils.convertForeach("#{item}", "coll", (String) null, "item", ","),
                    tableInfo.getLogicDeleteSql(true, true));
            sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        } else {
            sqlMethod = CommonSqlMethod.DELETE_BATCH_BY_LOGIC_CODES;
            sql = String
                    .format(sqlMethod.getSql(), tableInfo.getTableName(), CommonDbColumn.LOGIC_CODE.getDbFieldName(),
                            SqlScriptUtils.convertForeach("#{item}", "coll", (String) null, "item", ","));
            sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
        }
    }

}
