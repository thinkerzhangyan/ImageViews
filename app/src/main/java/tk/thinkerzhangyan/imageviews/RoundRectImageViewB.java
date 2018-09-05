package tk.thinkerzhangyan.imageviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
public class RoundRectImageViewB extends ImageView {

	private Paint paint = new Paint();

	public RoundRectImageViewB(Context context) {
		super(context);
	}

	public RoundRectImageViewB(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundRectImageViewB(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap rawBitmap =((BitmapDrawable)drawable).getBitmap();


			//将newBitmap 转换成圆形
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

		//指定为 ARGB_4444 可以减小图片大小
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Rect rect = new Rect(0, 0,bitmap.getWidth(), bitmap.getHeight());
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();
		canvas.drawRoundRect(new RectF(0,0,bitmap.getWidth(),bitmap.getHeight()), radius,radius, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}
