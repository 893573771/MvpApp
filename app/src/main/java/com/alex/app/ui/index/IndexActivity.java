package com.alex.app.ui.index;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;

import github.alex.mvp.CancelablePresenter;

/**
 * Created by Alex on 2016/6/28.
 */
public class IndexActivity extends BaseActivity {
    /**
     * 创建 Presenter
     */
    @Override
    protected CancelablePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_index;
    }

}
