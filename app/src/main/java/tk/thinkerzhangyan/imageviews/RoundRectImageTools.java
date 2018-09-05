package tk.thinkerzhangyan.imageviews;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

public class RoundRectImageTools {

    //对图片裁剪成圆形
    public static Bitmap toRoundRectA(Bitmap rawBitmap, int widgetWidth,int widgetHeight,int radius) {

        Bitmap bitmap = scaleBitmap(rawBitmap, widgetWidth, widgetHeight);

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0,bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(new RectF(0,0,bitmap.getWidth(),bitmap.getHeight()), radius,radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    //对图片裁剪成圆形
    public static Bitmap toRoundRectB(Bitmap rawBitmap, int widgetWidth,int widgetHeight,int radius) {

        Bitmap bitmap = scaleBitmap(rawBitmap, widgetWidth, widgetHeight);

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

    //对图片裁剪成圆形
    public static Bitmap toRoundRectC(Bitmap rawBitmap, int widgetWidth,int widgetHeight,int radius) {

        Bitmap bitmap = scaleBitmap(rawBitmap, widgetWidth, widgetHeight);

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        // 初始化画笔
        Paint paint = new Paint();
        final int color = 0xff424242;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(path, Region.Op.INTERSECT);

        // 利用画笔将纹理图绘制到画布上面
        canvas.drawBitmap(bitmap,rect,rect,paint);

        return output;

    }



    //将头像按比例缩放
    private static Bitmap scaleBitmap(Bitmap bitmap,int widgetWidth,int widgetHeight){

        //一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
        float scaleX = (float)widgetWidth/(float)bitmap.getWidth();
        float scaleY = (float) widgetHeight / (float) bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }



}
