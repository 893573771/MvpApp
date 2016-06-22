package github.alex.dialog.basedialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alex.mvpapp.R;


public class BaseDialog extends Dialog
{
	/**对话框 根视图*/
	protected View rootView;

	/** dialog width scale(宽度比例)*/
	protected float scaleWidth = 1;
	/**dialog height scale(高度比例)*/
	protected float scaleHeight;
	/**  max height(最大高度)*/
	protected float maxHeight;
	/** (DisplayMetrics)设备密度*/
	protected DisplayMetrics dm;
	protected Context context;
	private int gravity;
	private AnimType animType;
	public BaseDialog(Context context) {
		super(context, R.style.alex_dialog_common);
		this.context = context;
		initBaseDialogTheme();
	}
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		initBaseDialogTheme();
	}
	/**设置 对话框 高度比 (0, 1]*/
	public void setScaleHeight(float scaleHeight){
		this.scaleHeight = scaleHeight;
	}
	/**设置 对话框 宽度比 (0, 1]*/
	public void setScaleWidth(float scaleWidth){
		this.scaleWidth = scaleWidth;
	}
	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		int width;
		if (scaleWidth == 0) {
			width = ViewGroup.LayoutParams.WRAP_CONTENT;
		} else {
			width = (int) (dm.widthPixels * scaleWidth);
		}
		int height;
		if (scaleHeight == 0) {
			height = ViewGroup.LayoutParams.WRAP_CONTENT;
		} else if (scaleHeight == 1) {
			height = ViewGroup.LayoutParams.MATCH_PARENT;
		} else {
			height = (int) (maxHeight * scaleHeight);
		}
		rootView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
	}
	/**
	 * set dialog theme(设置对话框主题)
	 */
	protected void initBaseDialogTheme() {

		animType = AnimType.centerNormal;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	/**设置对话框的显示位置
	 * */
	public void setGravity(int gravity)
	{
		this.gravity = gravity;
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(gravity);
	}
	/**设置对话框的显示位置，以及Y轴的向下偏移量（单位 dp）
	 * */
	public void setGravity(int gravity, int yDP)
	{
		this.gravity = gravity;
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = (int) (yDP * context.getResources().getDisplayMetrics().density);
		dialogWindow.setGravity(gravity);
	}
	/**显示对话框，无动画*/
	public void show(){
		if(Gravity.BOTTOM == gravity){
			show(AnimType.bottom2top);
		}else if(Gravity.TOP == gravity){
			show(AnimType.top2bottom);
		}else if(Gravity.CENTER == gravity){
			show(animType);
		}else{
			super.show();
		}
	}
	/**显示对话框，强制转换对话框的动画类型
	 * */
	public void show(AnimType animType)
	{
		Window window = getWindow();
		/*如果根据  AnimType 的类型，强制选择Dialog出现的位置*/
		if(AnimType.bottom2top == animType){
			setGravity(Gravity.BOTTOM);
			window.setWindowAnimations(R.style.alex_dialog_anim_bottom2top);
		}else if(AnimType.top2bottom == animType){
			setGravity(Gravity.TOP);
			window.setWindowAnimations(R.style.alex_dialog_anim_top2bottom);
		}else if(AnimType.centerScale == animType){
			setGravity(Gravity.CENTER);
			window.setWindowAnimations(R.style.alex_dialog_anim_scale);
		}else if(AnimType.centerNormal == animType){
			setGravity(Gravity.CENTER);
			window.setWindowAnimations(R.style.alex_dialog_anim_alpha);
		}
		super.show();
	}
	public AnimType getAnimType(){
		return animType;
	}
	public void setAnimType(AnimType animType){
		this.animType = animType;
		/*如果根据  AnimType 的类型，强制选择Dialog出现的位置*/
		if(AnimType.bottom2top == animType){
			setGravity(Gravity.BOTTOM);
		}else if(AnimType.top2bottom == animType){
			setGravity(Gravity.TOP);
		}else if(AnimType.centerScale == animType){
			setGravity(Gravity.CENTER);
		}else if(AnimType.centerNormal == animType){
			setGravity(Gravity.CENTER);
		}
	}
	public void attachView(View view){
		int[] position = new int[2];  
		view.getLocationInWindow(position);
		
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		
		window.setGravity(Gravity.LEFT | Gravity.TOP);
		params.x = position[0]+getViewWidth(view)/2;
		params.y = position[1]+getViewHeight(view)/2;

		window.setAttributes(params);
	}
	/**
	 * 测量这个view
	 * 最后通过getMeasuredWidth()获取宽度和高度.
	 * @param view 要测量的view
	 * @return 测量过的view
	 */
	protected void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 获得这个View的宽度
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度.
	 * @param view 要测量的view
	 * @return 测量过的view的宽度
	 */
	protected int getViewWidth(View view) {
		measureView(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 获得这个View的高度
	 * 测量这个view，最后通过getMeasuredHeight()获取高度.
	 * @param view 要测量的view
	 * @return 测量过的view的高度
	 */
	protected int getViewHeight(View view) {
		measureView(view);
		return view.getMeasuredHeight();
	}

	public enum AnimType{
		/**底部进入
		 * 进入：从底部，到顶部
		 * 退出：从顶部，到底部*/
		bottom2top,
		/**顶部进入
		 * 进入：从顶部，到底部
		 * 退出：从底部，到顶部*/
		top2bottom,
		/**居中缩放
		 * 进入：居中，由小变大
		 * 退出：居中，由大变小*/	
		centerScale,
		/**居中 无效果*/	
		centerNormal
	}
}
