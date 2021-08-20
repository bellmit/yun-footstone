package com.yun.footstone.common.exception;

/**
 * <p>
 *     Json转换异常
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 15:34
 */
public class JsonTransformException extends RuntimeException {

    private static final long serialVersionUID = -3010456487227762210L;

    /**
     * 构造器
     */
    public JsonTransformException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message
     *         异常详细信息
     * @param cause
     *         异常原因
     */
    public JsonTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message
     *         异常详细信息
     */
    public JsonTransformException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause
     *         异常原因
     */
    public JsonTransformException(Throwable cause) {
        super(cause);
    }
}
