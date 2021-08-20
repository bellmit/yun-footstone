package com.yun.footstone.common.exception;

import com.yun.footstone.common.enums.BaseErrorCodeEnum;

/**
 * <p>
 * 异常基类
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 14:25
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 237828625839843189L;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 构造器
     */
    public BaseException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     * @Param code 异常编码
     */
    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BaseException(String message) {
        super(message);
        this.code = BaseErrorCodeEnum.FAIL.getCode();
    }


    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public BaseException(Throwable cause) {
        super(cause);
    }
}
