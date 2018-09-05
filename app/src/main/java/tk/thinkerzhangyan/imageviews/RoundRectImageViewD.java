package tk.thinkerzhangyan.imageviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 圆形控件
 *
 * @author ljnalex
 *
 */
public class RoundRectImageViewD extends ImageView {

	private Paint paint = new Paint();

	public RoundRectImageViewD(Context context) {
		super(context);
	}

	public RoundRectImageViewD(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundRectImageViewD(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap rawBitmap =((BitmapDrawable)drawable).getBitmap();

			Bitmap circleBitmap = toRoundCorner(rawBitmap, 30);

			final Rect rectSrc = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());

			final Rect rectDes = new Rect(0, 0, getWidth(), getHeight());

			paint.reset();
			
			//绘制到画布上
			canvas.drawBitmap(circleBitmap, rectSrc, rectDes, paint);
		} else {
			super.onDraw(canvas);
		}
	}



	private Bitmap toRoundCorner(Bitmap bitmap, int radius) {

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
		canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), radius, radius, paint);

		return output;

	}

}
