package github.alex.retrofit.util;

import android.util.Log;

import java.net.URLEncoder;

/**
 * 作者：Alex
 * 时间：2016年08月14日    10:00
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class Util {

    /**编码*/
    public static String encode(String strUnCode) {
        try
        {
            return URLEncoder.encode(strUnCode, "UTF-8");
        } catch (Exception e){
            Log.e("Url","有异常："+e);
        }
        return strUnCode;
    }
}
