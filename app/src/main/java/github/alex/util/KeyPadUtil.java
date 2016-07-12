package github.alex.util;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**软键盘工具类
 * @author Alex
 * @time 2016-0712-1857
 */
public class KeyPadUtil
{
	/**
	 * 强制显示输入法
	 */
	public void openKeyBord(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(activity.getWindow().getCurrentFocus(), InputMethodManager.SHOW_FORCED);
	}
	/**
	 * 打卡软键盘
	 *
	 */
	public static void openKeyBord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 *
	 */
	public static void closeKeyBord(Activity activity)
	{
		View view = activity.getCurrentFocus();
		if(view!=null){
			((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
			hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
