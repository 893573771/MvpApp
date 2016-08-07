package github.alex.util;

import android.text.TextUtils;
import android.util.Log;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class LogUtil {
    /**
     * 开启打印日志
     */
    public static boolean IS_SHOW_LOG = true;

    public static void v(Object msg) {
        printLog(1, (String) null, new Object[]{msg});
    }

    public static void d(Object msg) {
        printLog(2, (String) null, new Object[]{msg});
    }

    public static void i(Object msg) {
        printLog(3, (String) null, new Object[]{msg});
    }

    public static void w(Object msg) {
        printLog(4, (String) null, new Object[]{msg});
    }

    public static void e(Object msg) {
        printLog(5, (String) null, new Object[]{msg});
    }

    public static void wtf(Object msg) {
        printLog(6, (String) null, new Object[]{msg});
    }

    private static void printLog(int type, String tagStr, Object... objects) {
        if (IS_SHOW_LOG) {
            String[] contents = wrapperContent(tagStr, objects);
            String tag = contents[0];
            String msg = contents[2]+contents[1];
            short maxLength = 4000;
            int countOfSub = msg.length() / maxLength;
            int index = 0;
            if (countOfSub > 0) {
                for (int i = 0; i < countOfSub; ++i) {
                    String sub = msg.substring(index, index + maxLength);
                    printSub(type, tag, sub);
                    index += maxLength;
                }
                printSub(type, tag, msg.substring(index, msg.length()));
            } else {
                printSub(type, tag, msg);
            }
        }
    }

    private static String[] wrapperContent(String tagStr, Object... objects) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + ".java";
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName + methodName.substring(1);
        String tag = tagStr == null ? className : tagStr;
        if (TextUtils.isEmpty(tag)) {
            tag = "LogUtil";
        }

        String msg = objects == null ? "Log with null object" : getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {
        if (objects.length > 1) {
            StringBuilder var4 = new StringBuilder();
            var4.append("\n");

            for (int i = 0; i < objects.length; ++i) {
                Object object1 = objects[i];
                if (object1 == null) {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    var4.append("Param").append("[").append(i).append("]").append(" = ").append(object1.toString()).append("\n");
                }
            }

            return var4.toString();
        } else {
            Object object = objects[0];
            return object == null ? "null" : object.toString();
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case 1:
                Log.v(tag, sub);
                break;
            case 2:
                Log.d(tag, sub);
                break;
            case 3:
                Log.i(tag, sub);
                break;
            case 4:
                Log.w(tag, sub);
                break;
            case 5:
                Log.e(tag, sub);
                break;
            case 6:
                Log.wtf(tag, sub);
        }

    }
}