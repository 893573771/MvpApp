package github.aspsine.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alex.app.R;

import github.aspsine.swipetoloadlayout.callback.SwipeLoadMoreTrigger;
import github.aspsine.swipetoloadlayout.callback.SwipeTrigger;
import github.aspsine.view.drawable.google.RingProgressDrawable;


/**
 * Created by Aspsine on 2015/11/5.
 */
public class GoogleLoadMoreFooterView extends FrameLayout implements SwipeTrigger, SwipeLoadMoreTrigger {
    private ImageView ivLoadMore;

    private int mTriggerOffset;

    private RingProgressDrawable ringProgressDrawable;
    private int schemeColors[];
    public GoogleLoadMoreFooterView(Context context) {
        this(context, null);
        initView();
    }

    public GoogleLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public GoogleLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        ringProgressDrawable = new RingProgressDrawable(context);
        ringProgressDrawable.setColors(schemeColors);

    }
    private void initView() {
        schemeColors = new int[]{Color.parseColor("#FF5722")};
        mTriggerOffset = (int) ThemeUtils.dp2Px(getContext(), 60);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivLoadMore = (ImageView) findViewById(R.id.ivLoadMore);
        ivLoadMore.setBackgroundDrawable(ringProgressDrawable);
    }

    @Override
    public void onLoadMore() {
        ringProgressDrawable.start();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ringProgressDrawable.setPercent(-y / (float) mTriggerOffset);
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        ringProgressDrawable.stop();
    }

    @Override
    public void onReset() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(ringProgressDrawable!=null){
            ringProgressDrawable.stop();
        }
        ringProgressDrawable = null;
    }
}
