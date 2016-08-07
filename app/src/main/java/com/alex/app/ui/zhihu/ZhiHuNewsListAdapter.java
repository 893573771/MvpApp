package com.alex.app.ui.zhihu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.config.AppCon;
import com.alex.app.model.zhihu.NewsListBean;
import com.alex.app.ui.WebViewActivity;
import com.bumptech.glide.Glide;

import github.alex.adapter.BaseRecyclerAdapter;
import github.alex.adapter.RecyclerViewHolder;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class ZhiHuNewsListAdapter extends BaseRecyclerAdapter<NewsListBean.StoriesBean> {
    public ZhiHuNewsListAdapter(Context context) {
        super(context, R.layout.item_index_movie_list);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     *  holder.setText(R.id.tv_right, bean.money);
     *  holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    @Override
    public void convert(RecyclerViewHolder holder, int position) {
        NewsListBean.StoriesBean bean = list.get(position);
        holder.setText(R.id.tv, bean.title);
        String imgUrl = "https://www.baidu.com/";
        if ((bean.images != null) && (bean.images.size() >= 0) && (!TextUtils.isEmpty(bean.images.get(0)))) {
            imgUrl = bean.images.get(0);
        }
        Glide.with(context).load(imgUrl).placeholder(R.drawable.logo_empty).error(R.drawable.logo_empty).into((ImageView) holder.findView(R.id.iv_logo));
        MyOnClickListener onClickListener = new MyOnClickListener(position);
        holder.findView(R.id.layout_body).setOnClickListener(onClickListener);
    }

    private final class MyOnClickListener implements View.OnClickListener{
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            NewsListBean.StoriesBean bean = list.get(position);
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(AppCon.h5Title, bean.title);
            context.startActivity(intent);
        }
    }
}
