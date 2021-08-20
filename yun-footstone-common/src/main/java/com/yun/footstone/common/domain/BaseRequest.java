package com.yun.footstone.common.domain;

import com.yun.footstone.common.util.Check;
import org.slf4j.MDC;

import java.io.Serializable;

import static com.yun.footstone.common.constants.CommonConstants.TRACE_ID;

/**
 * <p>
 * API请求基类，携带traceId
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-04 16:58
 */
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -2044917175621275470L;

    /**
     * 日志追踪标识
     */
    private String traceId;

    protected BaseRequest() {
        if (!Check.isNullOrEmpty(MDC.get(TRACE_ID))) {
            setTraceId(MDC.get(TRACE_ID));
        } else if (!Check.isNullOrEmpty(getTraceId())) {
            setTraceId(getTraceId());
        }
    }

    public String getTraceId() {
        return this.traceId;
    }

    protected void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
        this.traceId = traceId;
    }

    public void setTraceId() {
        MDC.put(TRACE_ID, this.traceId);
    }
}
