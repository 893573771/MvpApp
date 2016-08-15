package github.alex.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class AppUtil {
    private static ArrayList<Activity> activityList;
    private AppUtil( ) {
        this.activityList = new ArrayList<>();
    }
    public static void addActivity(Activity activity){
        activityList = (activityList == null) ? new ArrayList<Activity>() : activityList;
        if( activity!=null){
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity){
        if((activityList!=null) && (activity!=null)){
            activity.finish();
            activityList.remove(activity);
            activity = null;
        }
    }

    public static void finishActivity(Activity ... activity){
        if((activityList!=null) && (activity!=null)){
            for (int i = 0; i < activity.length; i++) {
                Activity act = activity[i];
                if(act==null){
                    return ;
                }
                act.finish();
            }
        }
    }

    /**
     * 清除 activityName 之上 的 所有Activity， activityName 保留状态
     * @param activityName Activity 的名字 Activity.getClass().getSimpleName()
     * */
    public static void finishActivityAbove(String activityName){
        if((activityName == null) || (activityList == null)|| (activityList.size() <= 0)){
            return ;
        }
        for (int i = (activityList.size()-1); i>=0; i--){
            if(!activityName.equals(activityList.get(i).getClass().getSimpleName())){
                activityList.get(i).finish();
            }else{
                break;
            }
        }
    }

    /**
     * 杀死 所有Activity
     * */
    public static void clearActivityList(){
        if( (activityList==null)  ||  (activityList.size() <=0)){
            activityList = null;
            return ;
        }
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            activity.finish();
            activityList.remove(i);
            activity = null;
        }
        activityList.clear();
        activityList = null;
    }
    /**获取 App 的任务栈中  所有的Activity的 名字*/
    public static List<String> getActivityNameList(){
        List<String> nameList = new ArrayList<String>();
        for (int i = 0; (activityList!=null) && (i<activityList.size()); i++)
        {
            String name = activityList.get(i).getClass().getSimpleName();
            nameList.add(name);
        }
        return nameList;
    }

    /**判断 Activity 是否在 任务栈中*/
    public static boolean isActivityInTask(@NonNull  String activityName){
        if((activityList == null) || TextUtils.isEmpty(activityName)){
            return false;
        }
        for (int i = 0; (activityList!=null) && (i<activityList.size()); i++){
            String name = activityList.get(i).getClass().getSimpleName();
            if(name.equals(activityName)){
                return true;
            }
        }
        return false;
    }

    /**根据 名字 获取 Activity对象*/
    @Nullable
    public static Activity getActivity(@NonNull  String activityName){
        if((activityList == null) || TextUtils.isEmpty(activityName)){
            return null;
        }
        for (int i = 0; (activityList!=null) && (i<activityList.size()); i++){
            String name = activityList.get(i).getClass().getSimpleName();
            if(name.equals(activityName)){
                return activityList.get(i);
            }
        }
        return null;
    }

    /**
     * 获取栈顶的 Activity
     * */
    @Nullable
    public static Activity getTopActivity() {
        if ((activityList == null) || (activityList.size()<=0)) {
            return null;
        }
        return activityList.get(activityList.size()-1);
    }

    /**
     * 获取栈顶的 Activity的名字
     * */
    @Nullable
    public static String getTopActivityName() {
        if ((activityList == null) || (activityList.size()<=0)) {
            return null;
        }
        return activityList.get(activityList.size()-1).getClass().getSimpleName();
    }
    /**
     * 获取渠道名
     *
     * @param context 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
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
