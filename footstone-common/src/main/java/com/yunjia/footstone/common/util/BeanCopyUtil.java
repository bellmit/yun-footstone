/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.util;

import com.yunjia.footstone.common.exception.BaseException;
import com.yunjia.footstone.common.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 *     Bean复制
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:14
 */
@Slf4j
public class BeanCopyUtil extends BeanUtils {

    /**
     * 深拷贝
     * @param s
     * @param clazz
     * @return
     */
    public static <S, T> T copyPropertiesDeep(S s, Class<T> clazz){
        T t = Json.parseObject(Json.toJsonString(s), clazz);
        return t;
    }

    /**
     * 深拷贝
     * @param s
     * @param clazz
     * @return
     */
    public static <S, T> T copyProperties(S s, Class<T> clazz){
        try {
            T t = clazz.newInstance();
            copyProperties(s, t);
            return t;
        } catch (InstantiationException e) {
            log.error("s:{} clazz:{}", s, clazz, e);
            throw new BaseException("系统异常", e);
        } catch (IllegalAccessException e) {
            log.error("s:{} clazz:{}", s, clazz, e);
            throw new BaseException("系统异常", e);
        }
    }

    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * 例：List<SopInfoQueryResponse> result = BeanCopyUtil.copyListProperties(page.getRecords(), SopInfoQueryResponse:: new);
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * 例： List<UserVO> userVOList = BeanCopyUtil.copyListProperties(userDOList, UserVO::new, (userDO, userVO) ->{
     *         // 这里可以定义特定的转换规则
     *         userVO.setSex(SexEnum.getDescByCode(userDO.getSex()).getDesc());
     *     });
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }
}
