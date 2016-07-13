package com.alex.app.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.alex.app.R;
import com.alex.app.config.Key;
import com.alex.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.alex.mvp.CancelablePresenter;
import github.alex.view.ClickWebView;

/**
 * Created by hasee on 2016/7/13.
 */
public class WebViewActivity extends BaseActivity<CancelablePresenter> {
    @BindView(R.id.wv)
    ClickWebView webView;
    @BindView(R.id.pb)
    ProgressBar progressBar;

    private String url;
    private String title;

    /**
     * 创建 Presenter
     */
    @Override
    protected CancelablePresenter createPresenter() {
        return null;
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onGetIntentData() {
        super.onGetIntentData();
        Intent intent = getIntent();
        title = intent.getStringExtra(Key.h5Title);
        url = intent.getStringExtra(Key.h5Url);
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ButterKnife.bind(this);
        setText(R.id.tv_title, (title==null) ? "详情" : title);
        findViewById(R.id.iv_back).setOnClickListener(this);
        webView.applyNoCache();
        webView.loadUrl(url);
		/*背景透明*/
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
		/*背景透明*/
        webView.setBackgroundColor(0);
        webView.setOnProgressChangedListener(new MyOnProgressChangedListener());
    }
    private final class MyOnProgressChangedListener implements ClickWebView.OnProgressChangedListener
    {
        @Override
        public void onProgressChanged(WebView view, int progress)
        {
            progressBar.setProgress(progress);
            if (progress < 80) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(R.id.iv_back == v.getId()){
            if(webView.canGoBack()){
                webView.goBack();// 返回前一个页面
            }else{
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()){
                webView.goBack();// 返回前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
