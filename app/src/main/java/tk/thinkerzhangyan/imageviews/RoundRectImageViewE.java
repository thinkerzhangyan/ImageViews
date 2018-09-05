package tk.thinkerzhangyan.imageviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 圆形控件
 *
 * @author zhangyan
 *
 */
public class RoundRectImageViewE extends ImageView {

	private Paint paint = new Paint();

	public RoundRectImageViewE(Context context) {
		super(context);
	}

	public RoundRectImageViewE(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundRectImageViewE(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
            Bitmap rawBitmap =((BitmapDrawable)drawable).getBitmap();

//            //处理Bitmap 转成正方形
//            Bitmap newBitmap = dealRawBitmap(rawBitmap);
//            //将newBitmap转换成圆形，在toRoundCorner方法中创建了一个新的Bitmap，所以可能会导致内存占用过高，所以我们可以直接在原来的画布上进行裁剪
//            Bitmap circleBitmap = toRoundCorner(newBitmap,30);
//
//            final Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
//            paint.reset();
//            //绘制到画布上
//            canvas.drawBitmap(circleBitmap, rect, rect, paint);

			//在toRoundCorner方法中创建了一个新的Bitmap，所以可能会导致内存占用过高，所以我们可以直接在原来的画布上进行裁剪

			//处理Bitmap 转成正方形
			Bitmap circleBitmap = dealRawBitmap(rawBitmap);

			Path path = new Path();
			path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), 30, 30, Path.Direction.CW);
			canvas.clipPath(path, Region.Op.INTERSECT);

			Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());

			paint.reset();
			//绘制到画布上
			canvas.drawBitmap(circleBitmap, rect, rect, paint);

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
		return  scaleBitmap(newBitmap);
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
	private Bitmap toRoundCorner(Bitmap bitmap,int radius) {


        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Path path = new Path();
		path.addRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(path, Region.Op.INTERSECT);

        canvas.drawBitmap(bitmap,rect,rect,paint);

        return output;
	}

}
