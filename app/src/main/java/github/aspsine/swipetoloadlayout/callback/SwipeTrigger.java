package github.aspsine.swipetoloadlayout.callback;

/**
 * Created by Aspsine on 2015/8/13.
 *
 * @version 1.0.3
 * @link https://github.com/Aspsine/SwipeToLoadLayout
 */
public interface SwipeTrigger {
    void onPrepare();

    void onMove(int y, boolean isComplete, boolean automatic);

    void onRelease();

    void onComplete();

    void onReset();
}
