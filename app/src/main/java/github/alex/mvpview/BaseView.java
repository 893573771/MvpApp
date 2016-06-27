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
public interface BaseView {

    /**
     * 获取bodyView的 资源id
     */
    @IdRes
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
    void showToast(String text);

    /**
     * 多状态布局的 点击事件
     */
    @LayoutStatus
    void onStatusLayoutClick(int status);

    /**
     * 给文本控件设置文本
     */
    void setText(View view, String text);

    /**
     * 给文本控件设置文本
     */
    void setText(@IdRes int id, String text);

    @IdRes
    int getLeftTitleViewId();

    /**
     * 处理点击标题的左部按钮，大多数情况下为返回 按钮
     */
    void onClickLeftTitleView(@IdRes int id);

    @IdRes
    int getRightTitleViewId();
    /**
     * 处理点击标题的右部按钮，大多数情况下为分享 更多等功能 按钮
     */
    void onClickRightTitleView(@IdRes int id);

    /**
     * 扩展的findViewById
     */
    <T extends View> T findView(@IdRes int id);

}
