package github.alex.retrofit.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class DiskUtil {
    private static File getDiskCacheDir(Context context, String cacheDir) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getAbsolutePath();
            } else {
                cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + cacheDir);
    }
}
