/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package github.alex.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class NetUtil {
    /**
     * Context.
     */
    private static Application application;
    public enum NetType {
        Any,

        Wifi,

        Mobile
    }

    /**
     * Class name of the {@link android.provider.Settings}.
     */
    private static final String ANDROID_PROVIDER_SETTINGS = "android.provider.Settings";
    public static void init(Application application) {
        NetUtil.application = application;
    }
    /**
     * 打开网络设置页面
     */
    public static void openSetting() {
        if (Build.VERSION.SDK_INT > AndroidVersion.GINGERBREAD_MR1)
            openSetting("ACTION_WIFI_SETTINGS");
        else
            openSetting("ACTION_WIRELESS_SETTINGS");
    }

    private static void openSetting(String ActionName) {
        try {
            Class<?> settingsClass = Class.forName(ANDROID_PROVIDER_SETTINGS);
            Field actionWifiSettingsField = settingsClass.getDeclaredField(ActionName);
            Intent settingIntent = new Intent(actionWifiSettingsField.get(null).toString());
            settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(settingIntent);
        } catch (Throwable e) {
            LogUtil.w(e);
        }
    }

    /**
     * 检测网络是否 可用
     *
     * @return Available returns true, unavailable returns false.
     */
    public static boolean isNetworkAvailable() {
        return isNetworkAvailable(NetType.Any);
    }

    /**
     *WiFi网络是否可用。
     * @return Open return true, close returns false.
     */
    public static boolean isWifiConnected() {
        return isNetworkAvailable(NetType.Wifi);
    }

    /**
     *手机网络是否可用
     *
     * @return Open return true, close returns false.
     */
    public static boolean isMobileConnected() {
        return isNetworkAvailable(NetType.Mobile);
    }

    /**
     *根据不同类型的网络，以确定是否该网络的连接。
     * @param netType from {@link NetType}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isNetworkAvailable(NetType netType) {
        if(application == null){
            LogUtil.e("请在Application 中调用 NetUtil.init(this); ");
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> connectivityManagerClass = connectivity.getClass();
        if (Build.VERSION.SDK_INT >= AndroidVersion.LOLLIPOP) {
            try {
                Method getAllNetworksMethod = connectivityManagerClass.getMethod("getAllNetworks");
                getAllNetworksMethod.setAccessible(true);
                Object[] networkArray = (Object[]) getAllNetworksMethod.invoke(connectivity);
                for (Object network : networkArray) {
                    Method getNetworkInfoMethod = connectivityManagerClass.getMethod("getNetworkInfo", Class.forName("android.net.Network"));
                    getNetworkInfoMethod.setAccessible(true);
                    NetworkInfo networkInfo = (NetworkInfo) getNetworkInfoMethod.invoke(connectivity, network);
                    if (isConnected(netType, networkInfo))
                        return true;
                }
            } catch (Throwable e) {
            }
        } else {
            try {
                Method getAllNetworkInfoMethod = connectivityManagerClass.getMethod("getAllNetworkInfo");
                getAllNetworkInfoMethod.setAccessible(true);
                Object[] networkInfoArray = (Object[]) getAllNetworkInfoMethod.invoke(connectivity);
                for (Object object : networkInfoArray) {
                    if (isConnected(netType, (NetworkInfo) object))
                        return true;
                }
            } catch (Throwable e) {
            }
        }
        return false;
    }

    /**
     * 根据不同类型的网络，以确定是否该网络的连接。
     *
     * @param netType     from {@link NetType}.
     * @param networkInfo from {@link NetworkInfo}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isConnected(NetType netType, NetworkInfo networkInfo) {
        if (netType == NetType.Any && networkInfo != null && isConnected(networkInfo))
            return true;
        else if (netType == NetType.Wifi && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && isConnected(networkInfo))
            return true;
        else if (netType == NetType.Mobile && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && isConnected(networkInfo))
            return true;
        return false;
    }

    /**
     * 判断网络的连接。
     *
     * @param networkInfo from {@link NetworkInfo}.
     * @return Connection state return true, otherwise it returns false.
     */
    public static boolean isConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * 检查是否GPRS可用。
     *
     * @return Open return true, close returns false.
     */
    public static boolean isGPRSOpen() {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = connectivityManager.getClass();
        try {
            Method getMobileDataEnabledMethod = cmClass.getMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(connectivityManager);
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 打开或关闭GPRS。
     *
     * @param isEnable Open to true, close to false.
     */
    public static void setGPRSEnable(boolean isEnable) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> cmClass = connectivityManager.getClass();
        try {
            Method setMobileDataEnabledMethod = cmClass.getMethod("setMobileDataEnabled", boolean.class);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(connectivityManager, isEnable);
        } catch (Throwable e) {
        }
    }

    /**
     * 获取ip 地址
     *
     * @return Such as: {@code 192.168.1.1}.
     */
    public static String getLocalIPAddress() {
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            LogUtil.w(e);
        }
        if (enumeration != null) {
            // 遍历所用的网络接口
            while (enumeration.hasMoreElements()) {
                NetworkInterface nif = enumeration.nextElement();// 得到每一个网络接口绑定的地址
                Enumeration<InetAddress> inetAddresses = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                if (inetAddresses != null)
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress ip = inetAddresses.nextElement();
                        if (!ip.isLoopbackAddress() && isIPv4Address(ip.getHostAddress())) {
                            return ip.getHostAddress();
                        }
                    }
            }
        }
        return "";
    }

    /**
     * Ipv4 address check.
     */
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    /**
     *检查是否有效的IPv4地址。
     * @param input the address string to check for validity.
     * @return True if the input parameter is a valid IPv4 address.
     */
    public static boolean isIPv4Address(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

	/* ===========以下是IPv6的检查，暂时用不到========== */

    // 未压缩过的IPv6地址检查
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");
    // 压缩过的IPv6地址检查
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)" +                                                              // 0-6
            "::" + "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$");// 0-6 hex fields

    /**
     * 是否为有效 ipv6地址
     *
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6HexCompressedAddress(String)
     */
    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    /**
     *检查参数是否是有效的压缩IPv6地址。
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6StdAddress(String)
     */
    public static boolean isIPv6HexCompressedAddress(final String input) {
        int colonCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                colonCount++;
            }
        }
        return colonCount <= 7 && IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    /**
     *
     *检查是否压缩或未压缩的IPv6地址。
     * @param input IPV6 address.
     * @return True or false.
     * @see #isIPv6HexCompressedAddress(String)
     * @see #isIPv6StdAddress(String)
     */
    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public class AndroidVersion{
        /**
         * The value is {@value #BASE}
         */
        public static final int BASE = 1;

        /**
         * The value is {@value #BASE_1_1}
         */
        public static final int BASE_1_1 = 2;

        /**
         * The value is {@value #CUPCAKE}
         */
        public static final int CUPCAKE = 3;

        /**
         * The value is {@value #DONUT}
         */
        public static final int DONUT = 4;

        /**
         * The value is {@value #ECLAIR}
         */
        public static final int ECLAIR = 5;

        /**
         * The value is {@value #ECLAIR_0_1}
         */
        public static final int ECLAIR_0_1 = 6;

        /**
         * The value is {@value #ECLAIR_MR1}
         */
        public static final int ECLAIR_MR1 = 7;

        /**
         * The value is {@value #FROYO}
         */
        public static final int FROYO = 8;

        /**
         * The value is {@value #GINGERBREAD}
         */
        public static final int GINGERBREAD = 9;

        /**
         * The value is {@value #GINGERBREAD_MR1}
         */
        public static final int GINGERBREAD_MR1 = 10;

        /**
         * The value is {@value #HONEYCOMB}
         */
        public static final int HONEYCOMB = 11;

        /**
         * The value is {@value #HONEYCOMB_MR1}
         */
        public static final int HONEYCOMB_MR1 = 12;

        /**
         * The value is {@value #HONEYCOMB_MR2}
         */
        public static final int HONEYCOMB_MR2 = 13;

        /**
         * The value is {@value #ICE_CREAM_SANDWICH}
         */
        public static final int ICE_CREAM_SANDWICH = 14;

        /**
         * The value is {@value #ICE_CREAM_SANDWICH_MR1}
         */
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;

        /**
         * The value is {@value #JELLY_BEAN}
         */
        public static final int JELLY_BEAN = 16;

        /**
         * The value is {@value #JELLY_BEAN_MR1}
         */
        public static final int JELLY_BEAN_MR1 = 17;

        /**
         * The value is {@value #JELLY_BEAN_MR2}
         */
        public static final int JELLY_BEAN_MR2 = 18;

        /**
         * The value is {@value #KITKAT}
         */
        public static final int KITKAT = 19;

        /**
         * The value is {@value #KITKAT_WATCH}
         */
        public static final int KITKAT_WATCH = 20;

        /**
         * The value is {@value #LOLLIPOP}
         */
        public static final int LOLLIPOP = 21;

        /**
         * The value is {@value #LOLLIPOP_MR1}
         */
        public static final int LOLLIPOP_MR1 = 22;

        /**
         * The value is {@value #M}
         */
        public static final int M = 23;
    }
}
