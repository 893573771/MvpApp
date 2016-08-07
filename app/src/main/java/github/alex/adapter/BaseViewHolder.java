package github.alex.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import github.alex.util.font.FontUtil;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class BaseViewHolder {
    private SparseArray<View> viewSparseArray;
    private View convertView;

    private BaseViewHolder(Context context, View itemView, ViewGroup parent) {
        this.convertView = itemView;
        this.viewSparseArray = new SparseArray<View>();
        this.convertView.setTag(this);
    }

    public static BaseViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new BaseViewHolder(context, itemView, parent);
            return holder;
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        FontUtil.setFontTypeface(holder.getConvertView());
        return holder;
    }

    /**
     * 通过id获取控件
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        View view = viewSparseArray.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 给TextView 设置文本
     */
    public BaseViewHolder setText(int id, String text) {
        TextView textView = findView(id);
        if ((textView != null) && (!TextUtils.isEmpty(text))) {
            textView.setText(text);
        }
        return this;
    }

    public BaseViewHolder setOnClickListener(int id, View.OnClickListener listener) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setVisibility(int id, int visibility) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        if ((visibility == View.VISIBLE) || (visibility == View.GONE) || (visibility == View.INVISIBLE)) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public BaseViewHolder setTag(int id, Object tag) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int id, int key, Object tag) {
        View view = findView(id);
        if (view == null) {
            return this;
        }
        view.setTag(key, tag);
        return this;
    }
}
