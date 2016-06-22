package github.alex.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alex.mvpapp.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import github.alex.model.StatusLayoutModel;
import github.alex.mvpview.IBaseHttpView;

/**
 * Created by Alex on 2016/6/20.
 */
public class StatusLayoutHelper {
    private IBaseHttpView iBaseHttpView;
    private Map<String, View> layoutMap;

    private static final String sDefaultLayout = "sDefaultLayout";
    private static final String sLoadingLayout = "sLoadingLayout";
    private static final String sSuccessLayout = "sSuccessLayout";
    private static final String sFailLayout = "sFailLayout";
    private static final String sEmptyLayout = "sEmptyLayout";

    public StatusLayoutHelper(IBaseHttpView iBaseHttpView) {
        this.iBaseHttpView = iBaseHttpView;
    }

    public void initMultiModeBodyLayout(Context context, int bodyLayoutId) {
        if (bodyLayoutId == StatusLayoutModel.layoutResIdNo) {
            return;
        }
        layoutMap = new HashMap<>();
        View bodyLayout = iBaseHttpView.findView(bodyLayoutId);
        ViewGroup.LayoutParams bodyParentParams = bodyLayout.getLayoutParams();
        ViewGroup bodyParentLayout = (ViewGroup) bodyLayout.getParent();
        int index = bodyParentLayout.indexOfChild(bodyLayout);
        /*view只能有一个Parent，先移除后添加*/
        bodyParentLayout.removeView(bodyLayout);
        final FrameLayout bodyFrameLayout = new FrameLayout(context);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        /*FrameLayout 第一层视图*/
        bodyFrameLayout.addView(bodyLayout);
        layoutMap.put(sSuccessLayout, bodyLayout);
        /*FrameLayout 第二层视图*/

        /**默认 布局*/
        if (iBaseHttpView.onGetStatusLayoutModel().defaultLayoutId != StatusLayoutModel.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(iBaseHttpView.onGetStatusLayoutModel().defaultLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layoutMap.put(sDefaultLayout, layout);
        }

        /**loading布局*/
        if (iBaseHttpView.onGetStatusLayoutModel().loadingLayoutId != StatusLayoutModel.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(iBaseHttpView.onGetStatusLayoutModel().loadingLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layoutMap.put(sLoadingLayout, layout);
        }

        /**空数据 布局*/
        if (iBaseHttpView.onGetStatusLayoutModel().emptyLayoutId != StatusLayoutModel.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(iBaseHttpView.onGetStatusLayoutModel().emptyLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sEmptyLayout));
            layoutMap.put(sEmptyLayout, layout);

        }

        /**加载出错 布局*/
        if (iBaseHttpView.onGetStatusLayoutModel().failLayoutId != StatusLayoutModel.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(iBaseHttpView.onGetStatusLayoutModel().failLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sFailLayout));
            context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, new TypedValue(), true);
            layoutMap.put(sFailLayout, layout);
        }
        bodyParentLayout.addView(bodyFrameLayout, index, bodyParentParams);
        bodyParentLayout.invalidate();
        return;
    }

    /**
     * 展示出错消息
     */
    public void setFailMessage(String message) {
        TextView textView = iBaseHttpView.findView(iBaseHttpView.onGetStatusLayoutModel().failTextViewId);
        if ((textView == null) || TextUtils.isEmpty(message)) {
            return;
        }
        textView.setText(message);
    }

    /**
     * 展示默认布局
     */
    public void showDefaultLayout() {
        switchLayout(sDefaultLayout);
    }

    /**
     * 展示延时对话框
     */
    public void showLoadingLayout() {
        switchLayout(sLoadingLayout);
    }

    /**
     * 展示请求成功后的布局
     */
    public void showSuccessLayout() {
        switchLayout(sSuccessLayout);
    }

    /**
     * 展示请求空数据的布局
     */
    public void showEmptyLayout() {
        switchLayout(sEmptyLayout);
    }

    /**
     * 展示加载失败的布局
     */
    public void showFailLayout() {
        switchLayout(sFailLayout);
    }

    private void switchLayout(String layoutName) {
        Set<Map.Entry<String, View>> entrySet = layoutMap.entrySet();
        for (Iterator<Map.Entry<String, View>> ir = entrySet.iterator(); ir.hasNext(); ) {
            Map.Entry<String, View> mapEntry = ir.next();
            String key = mapEntry.getKey();

            View view = mapEntry.getValue();
            if (layoutName.equals(key)) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }

        }
        if (sLoadingLayout.equals(layoutName)) {
            layoutMap.get(sDefaultLayout).setVisibility(View.VISIBLE);
        }
    }

    private final class MyOnClickListener implements View.OnClickListener {
        private String layoutName;

        public MyOnClickListener(String layoutName) {
            this.layoutName = layoutName;
        }

        @Override
        public void onClick(View view) {
            if (sFailLayout.equals(layoutName)) {
                iBaseHttpView.onStatusLayoutClick(StatusLayoutModel.layoutStatusFail);
            } else if (sEmptyLayout.equals(layoutName)) {
                iBaseHttpView.onStatusLayoutClick(StatusLayoutModel.layoutStatusEmpty);
            }
        }
    }
}
