package github.alex.circleimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 作者：Alex
 * 时间：2016年08月06日    07:34
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class CircleImageView extends ImageView {
    /**
     * 绘图的Paint
     */
    private Paint paint;
    /**
     * 圆角的半径
     */
    private int radius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix matrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader bitmapShader;
    /**
     * view的宽度
     */
    private int width;

    private String cropType;

    public CircleImageView(Context context) {
        super(context);
        initView();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化 视图
     */
    private void initView() {
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
        radius = (int) dp2Px(10);
        cropType = CropType.centerTop;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        initBitmapShader();
        canvas.drawCircle(radius, radius, radius, paint);
    }

    /**
     * 初始化BitmapShader
     */
    private void initBitmapShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawable2Bitmap(drawable);
        bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        float scale = width * 1.0f / bSize;
        matrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        if ((w >= h) && CropType.centerTop.equals(cropType)) {
            canvas.translate((h - w) * 0.5F, 0);
        } else if ((w >= h) && CropType.center.equals(cropType)) {
            canvas.translate((h - w) * 0.5F, 0);
        } else if ((w < h) && CropType.center.equals(cropType)) {
            canvas.translate(0, (w - h) * 0.5F);
        }
        drawable.draw(canvas);
        return bitmap;
    }

    public static final class CropType {
        public static final String leftTop = "起点在左上角";
        public static final String centerTop = "起点水平居中&垂直置顶";
        public static final String center = "起点在图片中心";
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2Px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
