package github.alex.dialog;

import android.content.Context;
import android.view.LayoutInflater;

import com.alex.app.R;

import github.alex.dialog.basedialog.BaseDialog;
import github.alex.dialog.basedialog.StatusBarUtils;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class LoadingDialog extends BaseDialog {
    public LoadingDialog(Context context) {
        super(context);
        initDialog();
    }
    public void initDialog()
    {
        dm = getContext().getResources().getDisplayMetrics();
        maxHeight = dm.heightPixels - StatusBarUtils.getHeight(context);
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading_circle_orange, null);
        setContentView(rootView);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

}
