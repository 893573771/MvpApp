package com.alex.app.ui.zhihu;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.app.R;
import com.alex.app.config.AppCon;
import com.alex.app.model.zhihu.NewsListBean;
import com.alex.app.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.liaoinstan.springview.RefreshLayout;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class ZhiHuFragment extends BaseFragment<ZhiHuPresenter> implements ZhiHuContract.View {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.rl)
    RefreshLayout refreshLayout;
    private ZhiHuNewsListAdapter adapter;

    public static ZhiHuFragment getInstance(int index) {
        ZhiHuFragment homeFragment = new ZhiHuFragment();
        homeFragment.index = index;
        return homeFragment;
    }

    /**
     * 创建 Presenter
     */
    @Override
    protected ZhiHuPresenter createPresenter() {
        return new ZhiHuPresenter(this);
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.frag_zhihu;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findViewById 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        ButterKnife.bind(this, rootView);
        adapter = new ZhiHuNewsListAdapter(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setOnRefreshListener(new MyOnSwipeToLoadListener());
        if (index == 0) {
            loadJsonData(AppCon.loadFirst);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (!isVisibleToUser) {
            return;
        }
        if (canLoad) {
            canLoad = false;
            loadJsonData(AppCon.loadFirst);
        }
    }

    private final class MyOnSwipeToLoadListener implements RefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            loadJsonData(AppCon.loadRefresh);
        }

        @Override
        public void onLoadMore() {
            loadJsonData(AppCon.loadMore);
        }
    }

    private void loadJsonData(String loadType) {
        if (presenter != null) {
            presenter.loadNewsList(loadType, index);
        }
    }

    /**
     * 展示 movie 列表
     *
     * @param loadType
     * @param bean
     */
    @Override
    public void onShowNewsList(String loadType, NewsListBean bean) {
        if (AppCon.loadFirst.equals(loadType)) {
            adapter.refreshItem(bean.stories);
        } else if (AppCon.loadRefresh.equals(loadType)) {
            adapter.refreshItem(bean.stories);
        } else if (AppCon.loadMore.equals(loadType)) {
            adapter.addItem(bean.stories);
        }
        refreshLayout.stopRefreshLayout();
    }

    /**
     * 下拉刷新 或 加载 完成
     */
    @Override
    public void onRefreshFinish() {
        super.onRefreshFinish();
        refreshLayout.stopRefreshLayout();
    }
}
