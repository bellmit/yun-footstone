package com.yun.footstone.common.domain;

import com.yun.footstone.common.enums.BaseErrorCodeEnum;
import com.yun.footstone.common.exception.BaseException;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 * 公共返回包装类
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 11:43
 */
@Data
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -8530035770637837583L;

    /**
     * 返回码
     */
    private Integer code = BaseErrorCodeEnum.OK.getCode();

    /**
     * 返回描述信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public CommonResponse(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Boolean isOK() {
        if (code == null) {
            return false;
        }
        return code.equals(BaseErrorCodeEnum.OK.getCode());
    }

    public static <T> CommonResponse<T> responseOk() {
        CommonResponse<T> commonResponse = new CommonResponse<T>(BaseErrorCodeEnum.OK.getCode(), BaseErrorCodeEnum.OK.getDescribe());
        return commonResponse;
    }

    public static <T> CommonResponse<T> responseOkMsg(String msg) {
        CommonResponse<T> commonResponse = new CommonResponse<T>(BaseErrorCodeEnum.OK.getCode(), msg);
        return commonResponse;
    }

    public static <T> CommonResponse<T> responseOk(T obj) {
        CommonResponse<T> commonResponse = new CommonResponse<T>(BaseErrorCodeEnum.OK.getCode(),
                BaseErrorCodeEnum.OK.getDescribe(), obj);
        return commonResponse;
    }

    public static <T> CommonResponse<T> responseFail() {
        CommonResponse<T> commonResponse = new CommonResponse<T>(BaseErrorCodeEnum.FAIL.getCode(), BaseErrorCodeEnum.FAIL.getDescribe());
        return commonResponse;
    }

    public static <T> CommonResponse<T> responseFail(String msg) {
        CommonResponse<T> commonResponse = new CommonResponse<T>(BaseErrorCodeEnum.FAIL.getCode(), msg);
        return commonResponse;
    }

    public static <T> CommonResponse<T> responseFail(Integer code, String msg) {
        CommonResponse<T> commonResponse = new CommonResponse<T>(code, msg);
        return commonResponse;
    }

    /**
     * 获取返回值
     * 当返回码为非请求成功码时，抛出异常
     * @return
     */
    public T get() {
        if (!isOK()) {
            throw new BaseException(this.getCode(), this.getMessage());
        }
        return this.getData();
    }

    /**
     * 获取返回值
     * 当返回码为非请求成功码时，抛出异常
     * 当值为null时，返回默认值
     * 示例：BaseResponse<Integer> baseResponse = BaseResponse.responseOk(null);
     *      Integer aDefault = baseResponse.getDefault(() -> 0);
     * @param defaultData
     * @return
     */
    public T getDefault(@Nonnull Supplier<? extends T> defaultData) {
        if (!isOK()) {
            throw new BaseException(this.getCode(), this.getMessage());
        }
        return Optional.ofNullable(this.getData()).orElseGet(defaultData);
    }

    /**
     * 获取返回值
     * 当返回码为非请求成功码时，返回默认值
     * 当值为null时，返回默认值
     * 示例：BaseResponse<Integer> baseResponse = BaseResponse.responseFail();
     *      Integer aDefault = baseResponse.getIgnoreException(() -> 0);
     * @param defaultData
     * @return
     */
    public T getIgnoreException(@Nonnull Supplier<? extends T> defaultData) {
        if (!isOK()) {
            return defaultData.get();
        }
        return Optional.ofNullable(this.getData()).orElseGet(defaultData);
    }

}
