package github.alex.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * mvx 模式开发的 接口
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public class ToastHelper {
    private Toast toast;
    private TextView toastTextView;
    /**
     * 展示吐司, 默认居中偏上
     *
     * @param text 吐司内容
     */
    public void toast(Context context, String text) {
        if(context == null){
            return ;
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 100);
        }
        if (toastTextView == null) {
            int padding = (int) dp2Px(context, 8);
            toastTextView = new TextView(context);
            toastTextView.setTextColor(Color.parseColor("#FFFFFF"));
            toastTextView.setPadding(padding, padding, padding, padding);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            toastTextView.setLayoutParams(params);
            GradientDrawable gradientDrawableNormal = new GradientDrawable();
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.setColor(Color.parseColor("#99353535"));
            float radius = dp2Px(context, 4);
            gradientDrawableNormal.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
            toastTextView.setBackgroundDrawable(gradientDrawableNormal);
        }
        if (!TextUtils.isEmpty(text)) {
            toastTextView.setText(text);
        } else {
            toastTextView.setText("  ");
        }
        toast.setView(toastTextView);
        toast.show();
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2Px(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void onDetachView() {
        if(toastTextView !=null){
            toastTextView.destroyDrawingCache();
            toastTextView = null;
        }
        if(toast !=null){
            toast.cancel();
            toast = null;
        }
    }
}
