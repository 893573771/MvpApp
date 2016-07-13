package github.aspsine.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.alex.app.R;

import github.aspsine.swipetoloadlayout.callback.SwipeRefreshTrigger;
import github.aspsine.swipetoloadlayout.callback.SwipeTrigger;


/**
 * Created by aspsine on 15/11/7.
 */
public class GoogleCircleHookRefreshHeaderView extends FrameLayout implements SwipeTrigger, SwipeRefreshTrigger {
    private GoogleCircleProgressView progressView;

    private int mTriggerOffset;

    private int mFinalOffset;
    private int schemeColors[];


    public GoogleCircleHookRefreshHeaderView(Context context) {
        this(context, null);
        initView();
    }

    private void initView() {
        schemeColors = new int[]{Color.parseColor("#FF5722")};
        mTriggerOffset = (int) ThemeUtils.dp2Px(getContext(), 100);
        mFinalOffset = (int) ThemeUtils.dp2Px(getContext(), 150);
    }

    public GoogleCircleHookRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public GoogleCircleHookRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        progressView = (GoogleCircleProgressView) findViewById(R.id.googleProgress);
        progressView.setColorSchemeColors(schemeColors);
        progressView.setStartEndTrim(0, (float) 0.75);
    }

    @Override
    public void onRefresh() {
        progressView.start();
    }

    @Override
    public void onPrepare() {
        progressView.setStartEndTrim(0, (float) 0.75);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        float alpha = y / (float) mTriggerOffset;
        ViewCompat.setAlpha(progressView, alpha);
        progressView.setProgressRotation(y / (float) mFinalOffset);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        progressView.stop();
    }

    @Override
    public void onReset() {
        ViewCompat.setAlpha(progressView, 1f);
    }

}
