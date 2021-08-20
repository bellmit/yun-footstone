package com.yun.footstone.common.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     TODO：类功能描述
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 16:02
 */
public class JsonTest {

    @Test
    public void testDate() {
        Map<String, Date> map = new HashMap<>();
        map.put("dateTime", new Date());
        System.out.println(Json.toJsonString(map));

        String json = "{\"dateTime\":\"2021-08-05T16:10:01.453+0800\"}";
        Map map1 = Json.parseObject(json, Map.class);
        System.out.println(map1);

        TDate date = new TDate(new Date());
        json = Json.toJsonString(date);
        System.out.println(json);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class TDate{
        private Date dateTime;
    }

    @Test
    public void getDate() {
        System.out.println(System.currentTimeMillis());
    }
}