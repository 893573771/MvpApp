package com.alex.app.config;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class Cache
{
    private static final String split = File.separator;
    private static final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+split;
    public static final String cacheDir = rootPath+"钱罐儿"+split;
    public static final String crashLogPath = cacheDir+"crash.Log";
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    public static String getCacheTotalSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            Log.e(getTag(), "有异常："+e);
        }
        return size;
    }
    /**保存在 附近逛/tmp.java 下*/
    public static void saveTmpFile(String result){
        saveFile(result.getBytes(), cacheDir+"tmp.java", false);
    }
    /**保存文件
     * @param filePath 全路径: sdcard/cache/mainCache.bak
     * @param append true-追加|false-清空*/
    public static boolean saveFile(byte byteAry[], String filePath, boolean append)
    {
        FileOutputStream output = null;
        try
        {
            File file = new File(filePath);
            String parentStr = file.getParent();
            boolean createSDCardDir = createSDCardDir(parentStr);
            if(!createSDCardDir){
                return false;
            }
            output = new FileOutputStream(new File(filePath),append);
            output.write(byteAry);
            output.flush();
            return true;
        } catch (Exception e){
            Log.e(getTag(),"有异常："+e);
        }finally{
            try
            {
                if(output!=null){
                    output.close();
                }
            } catch (IOException e){
                Log.e(getTag(), "有异常："+e);
            }
        }
        return false;
    }
    private static boolean createSDCardDir(String newPath) {
        if (getSdCardRootAbsolutePath() == null) {
            Log.e("SDCardutil","SD卡不可用");
            return false;
        }
        //得到一个路径，内容是sdcard的文件夹路径和名字
        File path1 = new File(newPath);
        if (!path1.exists()) {
            //若不存在，创建目录，可以在应用启动的时候创建
            boolean mkdirs = path1.mkdir();
            //LogUtils.e("创建"+path1.getAbsolutePath()+" 成功 = "+mkdirs);
            return mkdirs;
        }
        return true;
    }
    /**获得SD卡的根目录的绝对路径*/
    private static String getSdCardRootAbsolutePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    private static String getTag(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //" getClassName = "++" getMethodName = "++" getLineNumber = "+stackTrace[i].getLineNumber());
        String className = stackTrace[3].getFileName();
        String methodName = stackTrace[3].getMethodName();
        int lineNumber = stackTrace[3].getLineNumber();
        return "["+className+":"+lineNumber+"]"+" # "+methodName;
    }
}