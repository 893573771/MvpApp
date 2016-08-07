package com.alex.app.ui.zhihu;

import com.alex.app.httpman.HttpMan;
import com.alex.app.model.zhihu.NewsListBean;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.BaseSubscriber;
import github.alex.rxjava.RxUtil;
import rx.Observable;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class ZhiHuPresenter extends CancelablePresenter<ZhiHuContract.View> implements ZhiHuContract.Presenter {
    public static String baseUrl = "http://news-at.zhihu.com";

    public ZhiHuPresenter(ZhiHuContract.View view) {
        super(view);
    }

    @Override
    public void loadNewsList(String loadType, int index) {
        this.loadType = loadType;

        Observable<NewsListBean> newsListBeanObservable = null;
        HttpMan httpMan = new RetrofitClient.Builder()
                .baseUrl(baseUrl)
                .build().create(HttpMan.class);
        if (0 == index) {
            newsListBeanObservable = httpMan.loadZhiHuLatestNews();
        } else if (1 == index) {
            newsListBeanObservable = httpMan.loadZhiHuSafety();
        } else if (2 == index) {
            newsListBeanObservable = httpMan.loadZhiHuInterest();
        } else if (3 == index) {
            newsListBeanObservable = httpMan.loadZhiHuSport();
        }
        newsListBeanObservable.compose(RxUtil.<NewsListBean>rxHttp())
                .lift(new AddSubscriberOperator())
                .subscribe(new MyBaseSubscriber());
    }

    private final class MyBaseSubscriber extends BaseSubscriber<NewsListBean> {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        /**
         * 网络请求失败
         *
         * @param message 请求失败的消息
         */
        @Override
        public void onError(String message) {
            view.toast(message);
            view.dismissLoadingDialog();
            view.onRefreshFinish();
        }

        /**
         * 网络请求成功
         *
         * @param result 网络请求的结果
         */
        @Override
        public void onSuccess(NewsListBean result) {
            view.dismissLoadingDialog();
            view.onShowNewsList(loadType, result);
        }
    }
}
