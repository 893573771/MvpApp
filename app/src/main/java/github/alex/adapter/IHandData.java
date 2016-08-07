package github.alex.adapter;

import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface IHandData<T> {

    /**
     * 获取数据集合
     */
    public List<T> getList();

    /**
     * 在List最后一条进行追加
     */
    public void addItem(T bean);

    /**
     * 更新一条数据
     */
    public void updateItem(T bean, int position);

    /**
     * 在List最后一条进行追加
     */
    public void addItem(List<T> list);

    /**
     * 在List的position之后进行追加
     */
    public void addItem(List<T> list, int position);


    /**
     * 移除一条数据
     *
     * @param position
     */
    public void removeItem(int position);

    /**
     * 移除一条数据
     */
    public void removeItem(T bean);

    /**
     * 移除多条数据
     */
    public void removeItem(List<T> list);

    /**
     * 清空 并 刷新 list
     */
    public void refreshItem(List<T> list);

    /**
     * 清空数据
     */
    public void clearItem();

}
