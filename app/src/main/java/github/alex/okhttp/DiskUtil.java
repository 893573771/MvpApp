package github.alex.okhttp;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Alex on 2016/7/1.
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
