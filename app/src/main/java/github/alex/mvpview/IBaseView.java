package github.alex.mvpview;

import android.support.annotation.IdRes;
import android.view.View;

import github.alex.annotation.LayoutStatus;

/**
 * mvx 模式开发的 接口
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public interface IBaseView {

    /**
     * 获取bodyView的 资源id
     */
    int getBodyViewId();

    /**
     * 执行在 onCreateView 中
     * 通过 findViewById 初始主视图化控件
     * 初始化所有基础数据，
     */
    void onCreateData();

    /**
     * 展示吐司
     *
     * @param text 吐司内容
     */
    void onShowToast(String text);

    /**
     * 多状态布局的 点击事件
     */
    @LayoutStatus
    public void onStatusLayoutClick(int status);

    /**
     * 扩展的findViewById
     */
    <T extends View> T findView(@IdRes int id);

}
