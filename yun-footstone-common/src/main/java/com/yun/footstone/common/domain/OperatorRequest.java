package com.yun.footstone.common.domain;

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
public class OperatorRequest extends BaseRequest {

    /**
     * 操作人员ID
     */
    @NotBlank(message = "操作人系统号不能为空")
    protected String operatorCode;

    /**
     * 操作人员姓名
     */
    @NotBlank(message = "操作人姓名不能为空")
    protected String operatorName;

    /**
     *  处理人类型 OperatorTypeEnum 1：客户 5：内部员工 10：供应商 15：服务者"
     */
    protected Integer operatorType = 5;

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
