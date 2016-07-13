package com.alex.app.ui.index;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.config.Key;
import com.alex.app.model.MovieListBean;
import com.alex.app.ui.WebViewActivity;
import com.bumptech.glide.Glide;

import github.alex.adapter.BaseRecyclerAdapter;
import github.alex.adapter.RecyclerViewHolder;

/**
 * Created by alex on 2016/7/13.
 */
public class IndexAdapter extends BaseRecyclerAdapter<MovieListBean.SubjectsBean> {
    public IndexAdapter(Context context) {
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
        Glide.with(context).load(bean.images.large).placeholder(R.drawable.logo_data_empty).error(R.drawable.logo_data_empty).into((ImageView) holder.findViewById(R.id.iv_logo));
        MyOnClickListener onClickListener = new MyOnClickListener(position);
        holder.findViewById(R.id.layout_body).setOnClickListener(onClickListener);
    }

    private final class MyOnClickListener implements View.OnClickListener{
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            MovieListBean.SubjectsBean bean = list.get(position);
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(Key.h5Url, bean.alt);
            intent.putExtra(Key.h5Title, bean.title);
            context.startActivity(intent);
        }
    }
}
