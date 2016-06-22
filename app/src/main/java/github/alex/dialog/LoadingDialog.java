package github.alex.dialog;

import android.content.Context;
import android.view.LayoutInflater;

import com.alex.mvpapp.R;

import github.alex.dialog.basedialog.BaseDialog;
import github.alex.dialog.basedialog.StatusBarUtils;

/**
 * Created by Alex on 2016/5/27.
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
