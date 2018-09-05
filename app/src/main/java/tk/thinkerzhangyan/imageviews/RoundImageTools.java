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
import android.graphics.Region;
import android.graphics.Shader;

public class RoundImageTools {


    //对图片裁剪成圆形
    public static Bitmap toRoundCornerA(Bitmap bitmap, int widgetWidth) {
        //裁剪除方形的Bitmap
        Bitmap rawBitmap = dealRawBitmap(bitmap);
        //对Bitmap进行缩放
        Bitmap newBitmap = scaleBitmap(rawBitmap, widgetWidth);

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0,newBitmap.getWidth(),newBitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = newBitmap.getWidth();
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(newBitmap, rect, rect, paint);
        return output;
    }


    //对图片裁剪成圆形
    public static Bitmap toRoundCornerB(Bitmap abimap, int widgetWidth) {

        //裁剪除方形的Bitmap
        Bitmap rawBitmap = dealRawBitmap(abimap);
        //对Bitmap进行缩放
        Bitmap newBitmap = scaleBitmap(rawBitmap, widgetWidth);

        /// 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(newBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔将纹理图绘制到画布上面
        canvas.drawCircle(newBitmap.getWidth()/2,newBitmap.getWidth()/2,newBitmap.getWidth()/2,paint);

        return output;
    }


    //对图片裁剪成圆形
    public static Bitmap toRoundCornerC(Bitmap abimap, int widgetWidth) {

        //裁剪除方形的Bitmap
        Bitmap rawBitmap = dealRawBitmap(abimap);
        //对Bitmap进行缩放
        Bitmap newBitmap = scaleBitmap(rawBitmap, widgetWidth);

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        Rect rect = new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight());

        Path path = new Path();
        path.addCircle(newBitmap.getWidth()/2, newBitmap.getWidth()/2,newBitmap.getWidth()/2, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.INTERSECT);
        // 利用画笔将纹理图绘制到画布上面
        canvas.drawBitmap(newBitmap,rect,rect,paint);

        return output;
    }


    //将原始图像裁剪成正方形
    private static Bitmap dealRawBitmap(Bitmap bitmap){
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


    //将图像按比例缩放
    private static Bitmap scaleBitmap(Bitmap bitmap,int widgetWidth){
        //一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
        float scale = (float)widgetWidth/(float)bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }



}
