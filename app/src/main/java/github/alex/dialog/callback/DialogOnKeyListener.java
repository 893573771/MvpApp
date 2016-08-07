package github.alex.dialog.callback;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class DialogOnKeyListener  implements OnKeyListener
{
	private Activity activity;
	private DialogOnKeyType onKeyType;
	public DialogOnKeyListener(Activity activity, DialogOnKeyType dialogOnKeyType) {
		this.onKeyType = dialogOnKeyType;
		this.activity = activity;
	}
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(DialogOnKeyType.dismissKillActivity == onKeyType){
				dialog.dismiss();
				activity.finish();
			}else if(DialogOnKeyType.dismissNotKillActivity == onKeyType){
				dialog.dismiss();
			}
		}
		return true;
	}
	public enum DialogOnKeyType{
		/**隐藏对话框 - 结束Activity*/
		dismissKillActivity,
		/**隐藏对话框 - 不结束Activity*/
		dismissNotKillActivity,
		/**不隐藏对话框 - 不结束Activity*/
		notDismissNotKillActivity
	}
}