package com.alex.app.ui.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import github.alex.annotation.Status;
import github.alex.callback.OnHttpCallback;
import github.alex.model.StatusLayoutModel;
import github.alex.mvp.BaseHttpContract;

/**
 * Created by Alex on 2016/6/29.
 */
public abstract class BaseFragment extends Fragment implements BaseHttpContract.View{
    @Override
    public void onHttpRequest(OnHttpCallback onHttpCallback) {

    }

    @Override
    public void onInitLoadingDialog() {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public StatusLayoutModel onGetStatusLayoutModel() {
        return null;
    }

    @Override
    public void showDefaultLayout() {

    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void showSuccessLayout() {

    }

    @Override
    public void showEmptyLayout() {

    }

    @Override
    public void setFailMessage(String message) {

    }

    @Override
    public void showFailLayout() {

    }

    @Override
    public int getBodyViewId() {
        return 0;
    }

    @Override
    public void onCreateData() {

    }

    @Override
    public void showToast(@NonNull String text) {

    }

    @Override
    public void onStatusLayoutClick(@Status int status) {

    }

    @Override
    public void setText(@NonNull View view, @NonNull String text) {

    }

    @Override
    public void setText(@IdRes int id, @NonNull String text) {

    }

    @Override
    public int getLeftTitleViewId() {
        return 0;
    }

    @Override
    public void onClickLeftTitleView(@IdRes int id) {

    }

    @Override
    public int getRightTitleViewId() {
        return 0;
    }

    @Override
    public void onClickRightTitleView(@IdRes int id) {

    }

    @Override
    public <T extends View> T findView(@IdRes int id) {
        return null;
    }
}
