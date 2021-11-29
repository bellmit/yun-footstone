/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.yunjia.footstone.mybatis.enums.CommonDbColumn;
import com.yunjia.footstone.mybatis.enums.CommonSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * <p>
 * 根据逻辑编号查询
 * </p>
 * @author sunkaiyun
 */
public class SelectByLogicCode extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        CommonSqlMethod sqlMethod = CommonSqlMethod.SELECT_BY_LOGIC_CODE;
        SqlSource sqlSource = new RawSqlSource(this.configuration,
                String.format(sqlMethod.getSql(), this.sqlSelectColumns(tableInfo, false), tableInfo.getTableName(),
                        CommonDbColumn.LOGIC_CODE.getDbFieldName(), CommonDbColumn.LOGIC_CODE.getEntityFieldName(),
                        tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
    }


}
