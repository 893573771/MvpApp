package github.alex.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
/**设备相关工具类
 * @author Alex
 * @time 2016-0712-1857
 */
public class DeviceUtil {
    /**
     * 获取手机DEVICE_ID | IMEI参数
     */
    public static String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static String getIp(Context context)
    {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 获取MAC 地址
     * <pre> 可以使用手机WiFi或蓝牙的MAC地址作为设备标识，但是并不推荐这么做，原因有以下两点：
     * 硬件限制：并不是所有的设备都有WiFi和蓝牙硬件，硬件不存在自然也就得不到这一信息。
     * 获取的限制：如果WiFi没有打开过，是无法获取其Mac地址的；而蓝牙是只有在打开的时候才能获取到其Mac地址。
     * </pre>
     */
    public static String getMacAddr(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取 ANDROID_ID
     * ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置。
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取手机生产商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机号码，可能为 null
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getLine1Number();
    }

    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" +
                // 主板
                Build.BOARD.length() % 10 +
                // android系统定制商
                Build.BRAND.length() % 10 +
                // cpu指令集
                Build.CPU_ABI.length() % 10 +
                // 设备参数
                Build.DEVICE.length() % 10 +
                // 显示屏参数
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                // 修订版本列表
                Build.ID.length() % 10 +
                // 硬件制造商
                Build.MANUFACTURER.length() % 10 +
                // 版本
                Build.MODEL.length() % 10 +
                // 手机制造商
                Build.PRODUCT.length() % 10 +
                // 描述build的标签
                Build.TAGS.length() % 10 +
                // builder类型
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位

        //A hardware serial number, if available. Alphanumeric only, case-insensitive.
        String serial = Build.SERIAL;
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 安全滴 获取 设备唯一标识
     */
    public static String getSafeDeviceSoleId(Context context) {
        StringBuilder builder = new StringBuilder();
        String androidId = getAndroidId(context);
        String imei = getIMEI(context);
        String macAddr = getMacAddr(context);
        String manufacturer = getManufacturer();
        String uniquePsuedoID = getUniquePsuedoID();
        if (androidId != null) {
            builder.append(androidId);
        }
        if (imei != null) {
            builder.append(imei);
        }
        if (macAddr != null) {
            builder.append(macAddr);
        }
        if (manufacturer != null) {
            builder.append(manufacturer);
        }
        if (uniquePsuedoID != null) {
            builder.append(uniquePsuedoID);
        }

        return stringToMD5(builder.toString());
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    private static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否手机设备
     *
     * @param context
     * @return true:手机,false:平板
     */
    public static boolean isPhoneDevice(Context context) {
        return !isTabletDevice(context);
    }

    /**
     * 获取渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Activity ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
