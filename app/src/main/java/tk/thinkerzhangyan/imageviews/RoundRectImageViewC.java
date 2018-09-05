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
import android.widget.ImageView;


/**
 * 圆形控件
 *
 * @author ljnalex
 *
 */
public class RoundRectImageViewC extends ImageView {

	private Paint paint = new Paint();

	public RoundRectImageViewC(Context context) {
		super(context);
	}

	public RoundRectImageViewC(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundRectImageViewC(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap rawBitmap =((BitmapDrawable)drawable).getBitmap();

			Bitmap newBitmap = scaleBitmap(rawBitmap);

			Bitmap circleBitmap = toRoundCorner(newBitmap, 30);

			final Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());

			paint.reset();

			//绘制到画布上
			canvas.drawBitmap(circleBitmap, rect, rect, paint);
		} else {
			super.onDraw(canvas);
		}
	}



	//将头像按比例缩放
	private Bitmap scaleBitmap(Bitmap bitmap){
		int width = getWidth();
		int height = getHeight();
		//一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
		float scaleX = (float)width/(float)bitmap.getWidth();
		float scaleY = (float) height / (float) bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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
