package github.alex.helper;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import github.alex.annotation.Status;
import github.alex.mvp.BaseHttpContract;

/**
 * Created by Alex on 2016/6/20.
 */
public class ViewHelper {
    private BaseHttpContract.View view;
    private Map<String, View> layoutMap;

    private static final String sDefaultLayout = "sDefaultLayout";
    private static final String sLoadingLayout = "sLoadingLayout";
    private static final String sSuccessLayout = "sSuccessLayout";
    private static final String sFailLayout = "sFailLayout";
    private static final String sEmptyLayout = "sEmptyLayout";

    public ViewHelper(BaseHttpContract.View view) {
        this.view = view;

    }

    public void initMultiModeBodyLayout(Context context, int bodyLayoutId) {
        if (bodyLayoutId == Status.layoutResIdNo) {
            return;
        }
        layoutMap = new HashMap<>();
        View bodyLayout = view.findView(bodyLayoutId);
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
        if (view.onGetStatusLayoutModel().defaultLayoutId != Status.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(view.onGetStatusLayoutModel().defaultLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setVisibility(View.GONE);
            layoutMap.put(sDefaultLayout, layout);
        }

        /**loading布局*/
        if (view.onGetStatusLayoutModel().loadingLayoutId != Status.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(view.onGetStatusLayoutModel().loadingLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setVisibility(View.GONE);
            layoutMap.put(sLoadingLayout, layout);
        }

        /**空数据 布局*/
        if (view.onGetStatusLayoutModel().emptyLayoutId != Status.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(view.onGetStatusLayoutModel().emptyLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sEmptyLayout));
            layout.setVisibility(View.GONE);
            layoutMap.put(sEmptyLayout, layout);
        }

        /**加载出错 布局*/
        if (view.onGetStatusLayoutModel().failLayoutId != Status.layoutResIdNo) {
            View layout = LayoutInflater.from(context).inflate(view.onGetStatusLayoutModel().failLayoutId, null);
            bodyFrameLayout.addView(layout, layoutParams);
            layout.setOnClickListener(new MyOnClickListener(sFailLayout));
            context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, new TypedValue(), true);
            layout.setVisibility(View.GONE);
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
        TextView textView = (TextView) layoutMap.get(sFailLayout).findViewById(view.onGetStatusLayoutModel().failTextViewId);
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

    /**给文本控件设置文本*/
    public void setText(View view, String text){
        if(view == null){
            Log.e(getClass().getSimpleName(), "view 为空 ");
            return ;
        }
        if(view instanceof TextView){
            TextView textView = (TextView)view;
            if(!TextUtils.isEmpty(text) && (!"null".equalsIgnoreCase(text))){
                textView.setText(text);
            }
        }else  if(view instanceof Button){
            Button button = (Button)view;
            if((button!=null) && (!TextUtils.isEmpty(text)) && (!"null".equalsIgnoreCase(text))){
                button.setText(text);
            }
        }else{
            Log.e(BaseActivity.class.getSimpleName(), "view 不能被强转成 TextView  或 Button");
        }
    }
    /**给文本控件设置文本*/
    public void setText(@IdRes int id, String text){
        setText(view.findView(id), text);
    }

    /**
     * 展示加载失败的布局
     */
    public void showFailLayout() {
        switchLayout(sFailLayout);
    }

    private void switchLayout(String layoutName) {
        if(layoutMap == null){
            return ;
        }
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

    public void setOnLeftTitleViewClickListener(@IdRes int id){
        View view = this.view.findView(id);
        if(view != null){
            view.setOnClickListener(new MyOnClickListener("左部"));
        }
    }
    public void setOnRightTitleViewClickListener(@IdRes int id){
        View view = this.view.findView(id);
        if(view != null){
            view.setOnClickListener(new MyOnClickListener("右部"));
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
                ViewHelper.this.view.onStatusLayoutClick(Status.FAIL);
            } else if (sEmptyLayout.equals(layoutName)) {
                ViewHelper.this.view.onStatusLayoutClick(Status.EMPTY);
            }else if("左部".equals(layoutName)){
                ViewHelper.this.view.onClickLeftTitleView(view.getId());
            }else if("右部".equals(layoutName)){
                ViewHelper.this.view.onClickRightTitleView(view.getId());
            }
        }
    }

}
