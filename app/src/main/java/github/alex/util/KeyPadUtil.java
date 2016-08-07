package github.alex.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class KeyPadUtil {
    private  static KeyPadUtil instance;

    private KeyPadUtil() {
    }
    public static void detachView(){
        instance = null;
    }
    public static KeyPadUtil instance(){
        if(instance == null){
            synchronized (KeyPadUtil.class){
                instance = (instance==null) ? new KeyPadUtil():instance;
            }
        }
        return instance;
    }

    /**
     * 可以隐藏输入法
     */
    public boolean canHiddenKeyPad(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        }
        return false;
    }
    /**
     * 强制显示输入法
     */
    public void openKeyPad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(activity.getWindow().getCurrentFocus(), InputMethodManager.SHOW_FORCED);
    }
    /**
     * 打卡软键盘
     *
     */
    public void openKeyPad(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public void closeKeyPad(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
