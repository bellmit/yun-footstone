/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.domain;

import com.yunjia.footstone.common.contxt.LocalContextHolder;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *     操作请求，携带操作人编码、姓名、类型
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-04 20:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema
public class OperatorRequest extends BaseRequest {

    @NotBlank(message = "操作人系统号不允许为空")
    @Schema(description = "操作人员ID")
    protected String operatorCode = LocalContextHolder.get().getLoginUser().getUserCode();

    @NotBlank(message = "操作人姓名不允许为空")
    @Schema(description = "操作人员姓名")
    protected String operatorName = LocalContextHolder.get().getLoginUser().getNickname();

    @NotNull(message = "操作人类型不允许为空")
    @Schema(description = "处理人类型 LoginUserTypeEnum 1：客户 5：内部员工 10：供应商 15：服务者")
    protected Integer operatorType = LocalContextHolder.get().getLoginUser().getUserType();

    public OperatorRequest(String operatorCode,String operatorName,String traceId){
        this.operatorCode = operatorCode;
        this.operatorName = operatorName;
        this.setTraceId(traceId);
    }

    public OperatorRequest(String operatorCode,String operatorName){
        this.operatorCode = operatorCode;
        this.operatorName = operatorName;
    }
}
