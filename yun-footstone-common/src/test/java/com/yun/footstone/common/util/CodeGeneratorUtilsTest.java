package com.yun.footstone.common.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * <p>
 *     TODO：类功能描述
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:49
 */
public class CodeGeneratorUtilsTest {

    @Test
    public void testCode() {
        List<String> arr = new ArrayList<>();
        for(int i =0 ;i<100000;i++){
            String id = CodeGeneratorUtils.nextUUIdWithDate("SR");
            System.out.println(id + "---" + i);
            if(arr.contains(id)){
                System.out.println("卧槽，重复了 重复了奥");
            }
        }
    }

    @Test
    public void testNull() {
        System.out.println(Optional.ofNullable(null).orElseGet(() -> "空"));
    }
}