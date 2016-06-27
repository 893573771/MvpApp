package github.alex.dialog.callback;

import android.app.Dialog;

public interface OnWait2DismissListener{
	/**@param second 为零的时候  对话框结束*/
	public void onDismiss(Dialog dialog, int second);
}
