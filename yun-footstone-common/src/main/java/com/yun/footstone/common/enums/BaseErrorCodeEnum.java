package com.yun.footstone.common.enums;

import com.yun.footstone.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p>
 *     基础异常码定义
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 10:57
 */
@AllArgsConstructor
@Getter
public enum BaseErrorCodeEnum {

    OK(200,"操作成功"),
    FAIL(999,"操作失败"),
    UNDEFINED(-1,"未定义");

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
