package com.yun.footstone.common.util;

import com.google.common.base.Joiner;
import com.yun.footstone.common.algorithms.SnowFlakeIDGenerator;
import com.yun.footstone.common.constants.CommonConstants;
import com.yun.footstone.common.date.DateFormatter;

import java.util.UUID;

/**
 * <p>
 *     编码生成器
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:24
 */
public class CodeGeneratorUtils {

    private CodeGeneratorUtils() {

    }

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    /**
     * 支持最大的机器数量
     */
    private static final int MAX_WORKER_ID = 15;

    private static SnowFlakeIDGenerator snowFlakeIDGenerator;

    static {
        // workerId 取值范围为 0至15
        int workerId = Math.abs(IPUtil.getLocalAddress().hashCode()) % (MAX_WORKER_ID + 1);
        snowFlakeIDGenerator = new SnowFlakeIDGenerator(null, workerId);
    }

    public static String nextTraceId(){
        return nextUUIdWithDate(CommonConstants.MDC_TRACE_ID_PREFIX);
    }

    public static String nextUUIdWithDate(String prefix){
        String dateStr = DateFormatter.format(System.currentTimeMillis(), "yyyyMMdd");
        if(prefix == null){
            return dateStr + generateShortUuid();
        }
        return prefix + dateStr + generateShortUuid();
    }

    /**
     * @return
     */
    public static String nextId(String prefix) {
        if (Check.isNullOrEmpty(prefix)) {
            return snowFlakeIDGenerator.nextId();
        }
        return Joiner.on("").join(prefix, snowFlakeIDGenerator.nextId());
    }

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString().toUpperCase();
    }

}
