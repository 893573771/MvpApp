package github.alex.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class FontUtil {
    public static final String TAG = "FontUtil";
    public static Typeface typeface;

    public static void initFormAssets(Context context, String fontPath) {
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
        } catch (Exception e) {
            Log.w(TAG, "初始化失败，请检查fontsPath是否错误");
        }
    }

    /**
     * 初始化
     *
     * @param fontPath 字体包存放路径（例如：sdcard/font.ttf）
     */
    public static void initFormFile(String fontPath) {
        try {
            typeface = Typeface.createFromFile(fontPath);
        } catch (Exception e) {
            Log.w(TAG, "初始化失败，请检查fontsPath是否错误");
        }
    }

    /**
     * 初始化
     *
     * @param fontFile 字体包文件
     */
    public static void initFormFile(File fontFile) {
        try {
            typeface = Typeface.createFromFile(fontFile);
        } catch (Exception e) {
            Log.w(TAG, "初始化失败，请检查fontFile是否是字体文件");
        }
    }

    /**
     * 更改字体
     *
     * @param view View
     */
    public static void setFontTypeface(View view) {
        setFontTypeface(view, typeface);
    }

    /**
     * 更改字体
     *
     * @param view View
     */
    public static void setFontTypeface(View view, Typeface typeface) {
        if (typeface == null) {
            Log.w(TAG, "字体为空");
            return;
        }
        if (view == null) {
            Log.w(TAG, "控件为空");
            return;
        }
        try {
            if (view instanceof ViewGroup) {
                setFontTypeface((ViewGroup) view, typeface);
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }
    }

    /**
     * 更换字体
     *
     * @param viewGroup ViewGroup
     * @param typeface  字体
     */
    public static void setFontTypeface(ViewGroup viewGroup, Typeface typeface) {
        try {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setFontTypeface((ViewGroup) v, typeface);
                } else if ((v != null) && (v instanceof View)) {
                    setFontTypeface((View) v, typeface);
                }
            }
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }
    }

    public void detachView() {

    }
}
