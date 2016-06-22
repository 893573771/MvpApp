package github.alex.presenter;

import github.alex.mvpview.IBaseView;

/**
 * Created by Alex on 2016/6/20.
 */
public abstract class BaseHttpPresenter<T extends IBaseView> implements IPresenter{

    @Override
    public void onLog(String message) {

    }
}
