/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.domain;

import com.yunjia.footstone.common.util.Check;
import com.yunjia.footstone.common.constants.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * <p>
 * API请求基类，携带traceId
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-04 16:58
 */
@Schema
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -2044917175621275470L;

    @Schema(description = "日志追踪标识")
    private String traceId;

    protected BaseRequest() {
        if (!Check.isNullOrEmpty(MDC.get(CommonConstants.TRACE_ID))) {
            setTraceId(MDC.get(CommonConstants.TRACE_ID));
        } else if (!Check.isNullOrEmpty(getTraceId())) {
            setTraceId(getTraceId());
        }
    }

    public String getTraceId() {
        return this.traceId;
    }

    protected void setTraceId(String traceId) {
        MDC.put(CommonConstants.TRACE_ID, traceId);
        this.traceId = traceId;
    }

    public void setTraceId() {
        MDC.put(CommonConstants.TRACE_ID, this.traceId);
    }
}
