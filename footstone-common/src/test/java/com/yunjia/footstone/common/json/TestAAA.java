/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.json;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunkaiyun
 * @version 1.0.0
 * @date 2021/9/3 10:42 AM
 * @since JDK1.8
 */
public class TestAAA {




    public static Integer find(Integer[] numArr, Integer target){
        if(numArr == null || numArr.length == 0) {
            return  null;
        }

        int len = numArr.length;
        int mid = len / 2;

        while(mid < len) {
            if(numArr[mid].equals(target)) {
                return mid;
            } else if(numArr[mid] < target){
                mid = mid + (len - mid) / 2;
            } else {
                len = mid;
                mid = len / 2;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Integer[] numArr = new Integer[]{1,2,3,4,5,6,7,8};
        int target = 7;
        System.out.println(find(numArr, target));
    }
}
