package github.alex.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public abstract class BaseObjectAdapter<T> extends BaseAdapter implements IHandData<T> {
    protected List<T> list;
    protected Context context;
    public int layoutId;

    public BaseObjectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<T>();
    }

    public BaseObjectAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        list = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return (list == null) ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取数据集合
     */
    @Override
    public List getList() {
        return list;
    }

    /**
     * 在List最后一条进行追加
     *
     * @param bean
     */
    @Override
    public void addItem(T bean) {
        if ((list == null) || (bean == null)) {
            return;
        }
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 在List最后一条进行追加
     *
     * @param list
     */
    @Override
    public void addItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 在List的position之后进行追加
     *
     * @param list
     * @param position
     */
    @Override
    public void addItem(List list, int position) {
        if ((this.list == null) || (list == null) || (list.size() <= position)) {
            return;
        }
        this.list.addAll(position, list);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    @Override
    public void clearItem() {
        if (list == null) {
            return;
        }
        list.removeAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空 并 刷新 list
     *
     * @param list
     */
    @Override
    public void refreshItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 移除一条数据
     *
     * @param bean
     */
    @Override
    public void removeItem(T bean) {
        if (list == null) {
            return;
        }
        list.remove(bean);
        notifyDataSetChanged();
    }

    /**
     * 移除多条数据
     *
     * @param list
     */
    @Override
    public void removeItem(List list) {
        if ((this.list == null) || (list == null)) {
            return;
        }
        this.list.removeAll(list);
        notifyDataSetChanged();
    }

    /**
     * 移除一条数据
     *
     * @param position
     */
    @Override
    public void removeItem(int position) {
        if (list == null) {
            return;
        }
        list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 更新一条数据
     *
     * @param bean
     * @param position
     */
    @Override
    public void updateItem(T bean, int position) {
        if ((list == null) || (list.size() <= position)) {
            return;
        }
        list.set(position, bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        convert(holder, position);
        return holder.getConvertView();
    }

    /**
     * 关联 View 和 Bean 以及 处理点击事件
     * </br>holder.setText(R.id.tv_right, bean.money);
     * </br>holder.setOnClickListener(R.id.layout_body, new MyOnClickListener(holder.position));
     */
    public abstract void convert(BaseViewHolder holder, int position);

}
