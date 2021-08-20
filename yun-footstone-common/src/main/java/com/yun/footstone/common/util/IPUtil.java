package com.yun.footstone.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * <p>
 *     获取IP地址
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:44
 */
public class IPUtil {

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    public static final String LOCALHOST = "127.0.0.1";
    public static final String ANYHOST = "0.0.0.0";

    private IPUtil() {
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String name = address.getHostAddress();
            return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
        } else {
            return false;
        }
    }

    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;

        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress)addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable var5) {
                                    var5.printStackTrace();
                                }
                            }
                        }
                    } catch (Throwable var7) {
                        var7.printStackTrace();
                    }
                }
            }
        } catch (Throwable var8) {
            var8.printStackTrace();
        }

        return localAddress;
    }
}
