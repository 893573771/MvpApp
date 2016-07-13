package github.alex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewSparseArray;
    private View itemView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    private RecyclerViewHolder(Context context, View itemView, ViewGroup parent) {
        this(itemView);
        this.viewSparseArray = new SparseArray<View>();
        //this.itemView.setTag(this);
        this.itemView = itemView;
    }

    public static RecyclerViewHolder getViewHolder(Context context, ViewGroup parent, int layoutId, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView, parent);
        return holder;
    }

    /**
     * 通过id获取控件
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        View view = viewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }

    public View getItemView() {
        return itemView;
    }

    /**
     * 给TextView 设置文本
     */
    public RecyclerViewHolder setText(int id, String text) {
        TextView textView = findViewById(id);
        if ((textView != null) && (!TextUtils.isEmpty(text))) {
            textView.setText(text);
        }
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int id, View.OnClickListener listener) {
        View view = findViewById(id);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setVisibility(int id, int visibility) {
        View view = findViewById(id);
        if (view == null) {
            return this;
        }
        if ((visibility == View.VISIBLE) || (visibility == View.GONE) || (visibility == View.INVISIBLE)) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public RecyclerViewHolder setTag(int id, Object tag) {
        View view = findViewById(id);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int id, int key, Object tag) {
        View view = findViewById(id);
        if (view == null) {
            return this;
        }
        view.setTag(key, tag);
        return this;
    }
}
