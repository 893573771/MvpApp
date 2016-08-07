package github.alex.dialog.callback;

import android.app.Dialog;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface OnWait2DismissListener{
	/**@param second 为零的时候  对话框结束*/
	public void onDismiss(Dialog dialog, int second);
}
