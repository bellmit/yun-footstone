package com.yun.footstone.common.domain;

/**
 * <p>
 *     分页请求基类
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 10:53
 */
public class PageRequest extends OperatorRequest {

    /**
     * 当前页
     */
    private long currentPage = 1;

    /**
     * 页大小
     */
    private long pageSize = 10;

}
