# Android中的圆形和圆角实现

在实际开发中我们经常会对图片进行圆角和圆形处理，这里做一个总结。

图片的圆角和圆形显示，从实现方法上讲分为两种做法：

1. 在图片上做
2. 在控件上做

圆角和圆形的处理方式可以统一成一种，当圆角大小等于图片宽的一半时候，就变成了圆形。

## 在控件上做

### 图层叠加

图层叠加原理很简单，就是在一张图片上面叠加一层图，覆盖部分，让图片展示成圆角。

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin">

    <FrameLayout
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:background="@drawable/demo_icon_android_logo">
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/demo_icon_shader"/>
    </FrameLayout>
</RelativeLayout>
```
效果图：
![此处输入图片的描述][1]

缺点：

1. 要准备两张图片，会增加额外的包体积；
2. 图片叠加一起，必然导致过度绘制；
3. 该方法太死板，使用时候要考虑图片大小，控件大小，以及蒙层图圆角锯齿效果，非常不灵。

所以这种方法不建议使用，了解即可。

### 系统控件

通过CardView和ViewOutlineProvider来圆角圆形需求，而且这两种方式主要是针对控件。

[CardView][2]是Android 5.0引入的卡片显示控件，可以实现阴影和圆角。

[ViewOutlineProvider][3]是Android 5.x引入的新特性，用于实现View的阴影和轮廓。

#### ViewOutlineProvider

**ViewOutlineProvider实现圆角**

```
roundImage.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        });
roundImage.setClipToOutline(true);
```

**ViewOutlineProvider实现圆形**

```
circleImage.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
circleImage.setClipToOutline(true);
```

setClipToOutline方法可以在前设置也可以在为设置，如果设置为false则表示禁止裁剪，setOutlineProvider方法将无效。

#### CardView

**CardViews实现圆形**

```
<android.support.v7.widget.CardView
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_gravity="center"
    android:layout_marginTop="10dp"
    app:cardCornerRadius="50dp">

    <ImageView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:scaleType="centerCrop"
        android:src="@drawable/girl" />
</android.support.v7.widget.CardView>

```

**CardViews实现圆角矩形**

```
<android.support.v7.widget.CardView
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_gravity="center"
    android:layout_marginTop="10dp"
    app:cardCornerRadius="10dp">

    <ImageView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:scaleType="centerCrop"
        android:src="@drawable/girl" />
</android.support.v7.widget.CardView>
```

上面两种方式主要是设置CardView的cardCornerRadius属性，如果要展示指定的圆角，把这个值设置成你想要的圆角值就行，**如果展示为圆形，首先要设置CardView长宽等值，而且cardCornerRadius为长宽的一半。**

注意：

1. 如果我们的应用设置了android:hardwareAccelerated="false"，CardView和ViewOutlineProvider两种方式都将无效。
2. CardView和ViewOutlineProvider两种方式只适合在Android 5.0及其以上的系统中，对于Android 5.0以下是不可以的，所以如果我们的应用需要运行在Android 5.0以下的系统上，我们需要做兼容工作。

下面我们介绍的两种方式实现的效果在各个版本的Android系统上都是相同的，不需要我们做兼容处理。


### Xfermode方式

[自定义圆形的ImageView][4]&&[自定义圆角矩形的ImageView][5]。

### BitmapShader方式

[自定义圆形的ImageView][6]&&[自定义圆角矩形的ImageView][7]。

### 通过画布裁剪方式

[自定义圆形的ImageView][6]&&[自定义圆角矩形的ImageView][7]。

## 在图片上做

### Xfermode方式

[图片的圆形效果][8]&&[图片的圆角矩形效果][9]。

### BitmapShader方式

[图片的圆形效果][8]&&[图片的圆角矩形效果][9]。

Demo链接：[ImageViews][12]


参考链接：

[你不知道的圆形圆角处理方式][10]

[Android - 实现图片圆角显示的几种方式][11]


  [1]: http://ogts8rw5s.bkt.clouddn.com/roundimageview1.png
  [2]: https://www.zybuluo.com/946898963/note/1270330
  [3]: https://www.zybuluo.com/946898963/note/1270259
  [4]: https://www.zybuluo.com/946898963/note/1186540
  [5]: https://www.zybuluo.com/946898963/note/1268593
  [6]: https://www.zybuluo.com/946898963/note/1186540
  [7]: https://www.zybuluo.com/946898963/note/1268593
  [8]: https://www.zybuluo.com/946898963/note/1271357
  [9]: https://www.zybuluo.com/946898963/note/1271408
  [10]: https://juejin.im/post/5a7a6bd1f265da4e9c630fa9
  [11]: http://gavinliu.cn/2016/04/12/Android-%E5%AE%9E%E7%8E%B0%E5%9B%BE%E7%89%87%E5%9C%86%E8%A7%92%E6%98%BE%E7%A4%BA%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F/
  [12]: https://github.com/thinkerzhangyan/ImageViews.git


各个类的说明：

**RoundImageViewA**  

利用Xfermode方式实现的圆形的ImageView，缩放方式采用的Matrix方式。

**RoundImageViewB** 

利用Xfermode方式实现的圆形的ImageView，缩放方式采用的Rect方式。

**RoundImageViewC**  

利用BitmapShader方式实现的圆形的ImageView，缩放方式采用的Matrix方式。

**RoundImageViewD** 

利用BitmapShader方式实现的圆形的ImageView，缩放方式采用的Rect方式。

**RoundImageViewE**  

利用画布裁剪的方式实现的圆形的ImageView，缩放方式采用的Matrix方式。

**RoundImageViewF** 

利用画布裁剪的方式实现的圆形的ImageView，缩放方式采用的Rect方式。

**RoundRectImageViewA**  

利用Xfermode方式实现的圆角矩形的ImageView，缩放方式采用的Matrix方式。

**RoundRectImageViewB**  

利用Xfermode方式实现的圆角矩形的ImageView，缩放方式采用的Rect方式（有问题，还未搞清楚原因）。

**RoundRectImageViewC**  

利用BitmapShader方式实现的圆角矩形的ImageView，缩放方式采用的Matrix方式。

**RoundRectImageViewD** 

利用BitmapShader方式实现的圆角矩形的ImageView，缩放方式采用的Rect方式（有问题，还未搞清楚原因）。

**RoundRectImageViewE**  

利用画布裁剪的方式实现的圆角矩形的ImageView，缩放方式采用的Matrix方式。

**RoundRectImageViewF**  

用画布裁剪的方式实现的圆角矩形的ImageView，缩放方式采用的Rect方式（没有任何问题）。

**RoundImageTools** 

利用Xfermode方式，BitmapShader方式和画布裁剪的方式对图片进行圆形化处理的工具类。

**RoundRectImageTools** 

利用Xfermode方式，BitmapShader方式和画布裁剪的方式对图片进行圆角矩形化处理的工具类。

