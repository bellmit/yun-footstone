/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 工具类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 10:47 AM
 */
public class AuthFoxUtil {

    /**
     * 从集合里查询数据
     *
     * @param dataList 数据集合
     * @param prefix   前缀
     * @param keyword  关键字
     * @param start    起始位置 (-1代表查询所有)
     * @param size     获取条数
     *
     * @return 符合条件的新数据集合
     */
    public static List<String> searchList(Collection<String> dataList, String prefix, String keyword, int start,
            int size) {
        if(prefix == null) {
            prefix = "";
        }
        if(keyword == null) {
            keyword = "";
        }
        // 挑选出所有符合条件的
        List<String> list = new ArrayList<String>();
        Iterator<String> keys = dataList.iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            if(key.startsWith(prefix) && key.indexOf(keyword) > -1) {
                list.add(key);
            }
        }
        // 取指定段数据
        return searchList(list, start, size);
    }

    /**
     * 从集合里查询数据
     *
     * @param list  数据集合
     * @param start 起始位置 (-1代表查询所有)
     * @param size  获取条数
     *
     * @return 符合条件的新数据集合
     */
    public static List<String> searchList(List<String> list, int start, int size) {
        // 取指定段数据
        if(start < 0) {
            return list;
        }
        int end = start + size;
        List<String> list2 = new ArrayList<String>();
        for(int i = start; i < end; i++) {
            if(i >= list.size()) {
                return list2;
            }
            list2.add(list.get(i));
        }
        return list2;
    }

    /**
     * 字符串模糊匹配
     * <p>example:
     * <p> user* user-add   --  true
     * <p> user* art-add    --  false
     *
     * @param patt 表达式
     * @param str  待匹配的字符串
     *
     * @return 是否可以匹配
     */
    public static boolean vagueMatch(String patt, String str) {
        // 如果表达式不带有*号，则只需简单equals即可 (速度提升200倍)
        if(patt.indexOf("*") == -1) {
            return patt.equals(str);
        }
        return Pattern.matches(patt.replaceAll("\\*", ".*"), str);
    }

    /**
     * 在url上拼接上kv参数并返回
     *
     * @param url       url
     * @param parameStr 参数, 例如 id=1001
     *
     * @return 拼接后的url字符串
     */
    public static String joinParam(String url, String parameStr) {
        // 如果参数为空, 直接返回
        if(parameStr == null || parameStr.length() == 0) {
            return url;
        }
        if(url == null) {
            url = "";
        }
        int index = url.lastIndexOf('?');
        // ? 不存在
        if(index == -1) {
            return url + '?' + parameStr;
        }
        // ? 是最后一位
        if(index == url.length() - 1) {
            return url + parameStr;
        }
        // ? 是其中一位
        if(index > -1 && index < url.length() - 1) {
            String separatorChar = "&";
            // 如果最后一位是 不是&, 且 parameStr 第一位不是 &, 就增送一个 &
            if(url.lastIndexOf(separatorChar) != url.length() - 1 && parameStr.indexOf(separatorChar) != 0) {
                return url + separatorChar + parameStr;
            } else {
                return url + parameStr;
            }
        }
        // 正常情况下, 代码不可能执行到此
        return url;
    }

    /**
     * 在url上拼接上kv参数并返回
     *
     * @param url   url
     * @param key   参数名称
     * @param value 参数值
     *
     * @return 拼接后的url字符串
     */
    public static String joinParam(String url, String key, Object value) {
        // 如果参数为空, 直接返回
        if(isEmpty(url) || isEmpty(key) || isEmpty(value)) {
            return url;
        }
        return joinParam(url, key + "=" + value);
    }

    /**
     * 在url上拼接锚参数
     *
     * @param url       url
     * @param parameStr 参数, 例如 id=1001
     *
     * @return 拼接后的url字符串
     */
    public static String joinSharpParam(String url, String parameStr) {
        // 如果参数为空, 直接返回
        if(parameStr == null || parameStr.length() == 0) {
            return url;
        }
        if(url == null) {
            url = "";
        }
        int index = url.lastIndexOf('#');
        // ? 不存在
        if(index == -1) {
            return url + '#' + parameStr;
        }
        // ? 是最后一位
        if(index == url.length() - 1) {
            return url + parameStr;
        }
        // ? 是其中一位
        if(index > -1 && index < url.length() - 1) {
            String separatorChar = "&";
            // 如果最后一位是 不是&, 且 parameStr 第一位不是 &, 就增送一个 &
            if(url.lastIndexOf(separatorChar) != url.length() - 1 && parameStr.indexOf(separatorChar) != 0) {
                return url + separatorChar + parameStr;
            } else {
                return url + parameStr;
            }
        }
        // 正常情况下, 代码不可能执行到此
        return url;
    }

    /**
     * 在url上拼接锚参数
     *
     * @param url   url
     * @param key   参数名称
     * @param value 参数值
     *
     * @return 拼接后的url字符串
     */
    public static String joinSharpParam(String url, String key, Object value) {
        // 如果参数为空, 直接返回
        if(isEmpty(url) || isEmpty(key) || isEmpty(value)) {
            return url;
        }
        return joinSharpParam(url, key + "=" + value);
    }

    /**
     * 指定元素是否为null或者空字符串
     * @param str 指定元素
     * @return 是否为null或者空字符串
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 验证URL的正则表达式
     */
    public static final String URL_REGEX = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    /**
     * 使用正则表达式判断一个字符串是否为URL
     * @param str 字符串
     * @return 拼接后的url字符串
     */
    public static boolean isUrl(String str) {
        if(str == null) {
            return false;
        }
        return str.toLowerCase().matches(URL_REGEX);
    }
}
