/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 基于logic_code字段的SQL定义
 * </p>
 * @author sunkaiyun
 */
@AllArgsConstructor
@Getter
public enum CommonSqlMethod {

    DELETE_BY_LOGIC_CODE("deleteByLogicCode", "根据逻辑编号 删除一条数据", "<script>\nDELETE FROM %s WHERE %s=#{%s}\n</script>"),
    DELETE_BATCH_BY_LOGIC_CODES("deleteBatchLogicCodes", "根据逻辑编号集合，批量删除数据",
                                "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),
    LOGIC_DELETE_BY_LOGIC_CODE("deleteByLogicCode", "根据逻辑编号 逻辑删除一条数据",
                               "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
    LOGIC_DELETE_BATCH_BY_LOGIC_CODES("deleteBatchLogicCodes", "根据逻辑编号集合，批量逻辑删除数据",
                                      "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),
    UPDATE_BY_LOGIC_CODE("updateByLogicCode", "根据逻辑编号 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
    SELECT_BY_LOGIC_CODE("selectByLogicCode", "根据逻辑编号 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s"),
    SELECT_BATCH_BY_LOGIC_CODES("selectBatchLogicCodes", "根据逻辑编号集合，批量查询数据",
                                "<script>SELECT %s FROM %s WHERE %s IN (%s) %s </script>");

    private final String method;
    private final String desc;
    private final String sql;
}
