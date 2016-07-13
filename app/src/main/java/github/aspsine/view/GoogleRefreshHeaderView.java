package github.aspsine.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alex.app.R;

import github.aspsine.swipetoloadlayout.callback.SwipeRefreshTrigger;
import github.aspsine.swipetoloadlayout.callback.SwipeTrigger;
import github.aspsine.view.drawable.google.RingProgressDrawable;


/**
 * Created by aspsine on 15/9/10.
 */
public class GoogleRefreshHeaderView extends FrameLayout implements SwipeTrigger, SwipeRefreshTrigger {
    private ImageView ivRefresh;

    private int mTriggerOffset;

    private RingProgressDrawable ringProgressDrawable;
    private int schemeColors[];
    public GoogleRefreshHeaderView(Context context) {
        this(context, null);
        initView();
    }

    public GoogleRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public GoogleRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        ringProgressDrawable = new RingProgressDrawable(context);
        ringProgressDrawable.setColors(schemeColors);
        mTriggerOffset = (int) ThemeUtils.dp2Px(getContext(), 100);
    }
    private void initView() {
        schemeColors = new int[]{Color.parseColor("#FF5722")};
        mTriggerOffset = (int) ThemeUtils.dp2Px(getContext(), 60);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        ivRefresh.setBackgroundDrawable(ringProgressDrawable);
    }

    @Override
    public void onRefresh() {
        ringProgressDrawable.start();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ringProgressDrawable.setPercent(y / (float) mTriggerOffset);
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
