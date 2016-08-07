package com.alex.app.ui.index;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.app.R;
import com.alex.app.config.AppCon;
import com.alex.app.model.MovieListBean;
import com.alex.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.liaoinstan.springview.RefreshLayout;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class IndexActivity extends BaseActivity<IndexPresenter> implements IndexContract.View {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.rl)
    RefreshLayout refreshLayout;
    private IndexAdapter adapter;

    /**
     * 创建 Presenter
     */
    @Override
    protected IndexPresenter createPresenter() {
        return new IndexPresenter(this);
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_index;
    }

    /**
     * 获取标题的左部按钮，大多数情况下为 返回 按钮
     */
    @Override
    public int getLeftFinishViewId() {
        return R.id.iv_back;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ButterKnife.bind(this);
        setText(R.id.tv_title, "豆瓣电影");
        adapter = new IndexAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setOnRefreshListener(new MyOnSwipeToLoadListener());
        loadJsonData(AppCon.loadFirst);
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
        presenter.loadMovieList(loadType);
    }

    /**
     * 展示 movie 列表
     *
     * @param bean
     */
    @Override
    public void onShowMovieList(String loadType, MovieListBean bean) {
        if(AppCon.loadFirst.equals(loadType)){
            adapter.refreshItem(bean.subjects);
        }else if(AppCon.loadRefresh.equals(loadType)){
            adapter.refreshItem(bean.subjects);
        }else if(AppCon.loadMore.equals(loadType)){
            adapter.addItem(bean.subjects);
        }
        refreshLayout.stopRefreshLayout();
    }
}
