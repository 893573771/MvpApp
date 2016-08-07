package github.alex.util;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class ClassUtil
{
	public static String TAG = "#ClassUtil#";
	/**获取 JavaBean的成员变量的 个数  成员修饰符 必须是 public
	 * @param bean 例如 MessageType.class
	 * @return  成员变量的 个数*/
	public static int getFieldsCount(Class<?> bean)
	{
		Field[] fields = bean.getFields();
		if(fields!=null){
			return fields.length;
		}
		return 0;
	}
	/**获取 JavaBean的成员变量的 个数
	 * @param bean 例如 MessageType.class
	 * @return  成员变量的 个数*/
	public static List<String> getFieldsList(Class<?> bean)
	{
		List<String> list = new ArrayList<String>();
		Field[] fields = bean.getFields();
		if(fields!=null){
			for (int i = 0; i < fields.length; i++)
			{
				list.add(fields[i].getName());
			}
		}
		return list;
	}
	public static Class<?> forName(String className){
		try
		{
			return Class.forName(className);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
			LogUtil.e(e);
		}
		return null;
	}
	public static String getOuterMostClassName(){
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[3].getClassName();
		int indexOf = className.indexOf("$");
		return className.substring(0, indexOf);
	}

	/**
	 * 页面跳转
	 */
	public static void startActivity(Context context, @NonNull Class clazz) {
		if (clazz == null) {
			Log.e(TAG, "当前Class为空");
			return;
		}
		try {
			Activity activity = (Activity) clazz.newInstance();
		} catch (Exception e) {
			Log.e(TAG, clazz.getSimpleName() + " 不能转换成 Activity");
			return;
		}
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}
}
