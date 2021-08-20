package com.yun.footstone.common.enums;

import com.yun.footstone.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p>
 * 操作人类型
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 09:57
 */
@AllArgsConstructor
@Getter
public enum OperatorTypeEnum {

    CUSTOMER(1, "客户"),
    STAFF(5, "内部员工"),
    SUPPLIER(10, "第三方供应商"),
    WORKER(15, "服务者");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 释义
     */
    private String describe;

    /**
     * 根据编码查询枚举
     *
     * @param code
     * @return
     */
    public static OperatorTypeEnum getByCode(Integer code) {
        if (Check.isNull(code)) {
            return null;
        }
        Optional<OperatorTypeEnum> optional = Arrays.stream(OperatorTypeEnum.values()).filter(t -> t.getCode().equals(code)).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 根据编码查询释义
     *
     * @param code
     * @return
     */
    public static String getDesByCode(Integer code) {
        OperatorTypeEnum operatorTypeEnum = getByCode(code);
        return null == operatorTypeEnum ? "" : operatorTypeEnum.getDescribe();
    }
}
