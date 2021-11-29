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
 * @date 2021/8/31 3:25 PM
 * @since JDK1.8
 */
public class StringTest {


    public static String add(String num1, String num2) {
        int numlen1 = num1.length();
        int numlen2 = num2.length();
        if(numlen1 < numlen2) {
            return add(num2, num1);
        }
        int temp = 0;
        int mod = numlen1 - numlen2;
        String[] str = new String[numlen1 + 1];
        for(int i = numlen1 - 1; i >=0; i--) {
            int a = num1.charAt(i) - 48;
            int b = 0;
            if(i - mod >= 0) {
                b = num2.charAt(i - mod) - 48;
            }
            int result = a + b + temp;
            if(result > 9) {
                temp = result / 10;
                result = result % 10;
            }else{
                temp = 0;
            }
            str[i + 1] = result + "";
        }
        if(temp > 0) {
            str[0] = temp + "";
        } else {
            str[0] = "";
        }

        StringBuffer sb = new StringBuffer();
        for(String s : str) {
            if(!s.equals("")){
                sb.append(s);
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


    public static void main(String[] args) {
        String a = "12345";
        String b = "345";
        String result = add(a, b);
        System.out.println(result);
    }
}
