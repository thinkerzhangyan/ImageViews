package tk.thinkerzhangyan.imageviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CursorAdapter;
import android.widget.ImageView;


/**
 * 圆形控件
 *
 * @author zhangyan
 *
 */
public class RoundImageViewD extends ImageView {

	private Paint paint = new Paint();

	public RoundImageViewD(Context context) {
		super(context);
	}

	public RoundImageViewD(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageViewD(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap rawBitmap =((BitmapDrawable)drawable).getBitmap();

			//处理Bitmap 转成正方形
			Bitmap newBitmap = dealRawBitmap(rawBitmap);
			//将newBitmap 转换成圆形
			Bitmap circleBitmap = toRoundCorner(newBitmap, 14);

			final Rect rectSrc = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
			final Rect rectDes = new Rect(0, 0, getWidth(), getHeight());

			paint.reset();
			//绘制到画布上
			canvas.drawBitmap(circleBitmap, rectSrc, rectDes, paint);
		} else {
			super.onDraw(canvas);
		}
	}


	//将原始图像裁剪成正方形
	private Bitmap dealRawBitmap(Bitmap bitmap){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		//获取宽度
		int minWidth = width > height ?  height:width ;
		//计算正方形的范围
		int leftTopX = (width - minWidth)/2;
		int leftTopY = (height - minWidth)/2;
		//裁剪成正方形
		Bitmap newBitmap = Bitmap.createBitmap(bitmap,leftTopX,leftTopY,minWidth,minWidth,null,false);
		return  newBitmap;
	}

	private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		// 初始化绘制纹理图
		BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		//指定为 ARGB_4444 可以减小图片大小
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(output);

		// 初始化画笔
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(bitmapShader);

		// 利用画笔将纹理图绘制到画布上面
		canvas.drawCircle(bitmap.getWidth()/2,bitmap.getWidth()/2,bitmap.getWidth()/2,paint);

		return output;

	}



}
