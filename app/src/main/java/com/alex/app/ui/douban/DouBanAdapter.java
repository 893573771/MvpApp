package com.alex.app.ui.douban;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.model.MovieListBean;
import com.alex.app.ui.WebViewActivity;
import com.squareup.picasso.Picasso;

import github.alex.adapter.BaseRecyclerAdapter;
import github.alex.adapter.RecyclerViewHolder;
import github.alex.model.ParcelableMap;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class DouBanAdapter extends BaseRecyclerAdapter<MovieListBean.SubjectsBean> {
    public DouBanAdapter(Context context) {
        super(context, R.layout.item_index_movie_list);
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     *  holder.setText(R.id.tv_right, bean.money);
     *  holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    @Override
    public void convert(RecyclerViewHolder holder, int position) {
        MovieListBean.SubjectsBean bean = list.get(position);
        holder.setText(R.id.tv, bean.title);
        Picasso.with(context).load(bean.images.large).placeholder(R.drawable.logo_data_empty).error(R.drawable.logo_data_empty).into((ImageView) holder.findView(R.id.iv_logo));
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
            MovieListBean.SubjectsBean bean = list.get(position);
            ParcelableMap map = new ParcelableMap().put("url", bean.alt).put("title", bean.title);
            startActivity(WebViewActivity.class, map);
        }
    }
}
