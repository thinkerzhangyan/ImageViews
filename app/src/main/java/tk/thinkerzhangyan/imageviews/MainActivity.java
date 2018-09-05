package tk.thinkerzhangyan.imageviews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.view.ViewOutlineProvider;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageViewOne;
    private ImageView mImageViewTwo;
    private ImageView mImageViewSeven;

    private ImageView mImageViewThree;
    private ImageView mImageViewFour;
    private ImageView mImageViewEight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageViewOne = findViewById(R.id.iv_round_image_one);
        mImageViewTwo = findViewById(R.id.iv_round_image_two);
        mImageViewSeven = findViewById(R.id.iv_round_image_seven);

        mImageViewThree = findViewById(R.id.iv_round_image_three);
        mImageViewFour = findViewById(R.id.iv_round_image_four);
        mImageViewEight = findViewById(R.id.iv_round_image_eight);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        mImageViewOne.setImageBitmap(RoundImageTools.toRoundCornerA(bitmap,(int)getResources().getDimension(R.dimen.round_bitmap_width)));
        mImageViewTwo.setImageBitmap(RoundImageTools.toRoundCornerB(bitmap,(int)getResources().getDimension(R.dimen.round_bitmap_width)));
        mImageViewSeven.setImageBitmap(RoundImageTools.toRoundCornerC(bitmap,(int)getResources().getDimension(R.dimen.round_bitmap_width)));

        mImageViewThree.setImageBitmap(RoundRectImageTools.toRoundRectA(bitmap,(int)getResources().getDimension(R.dimen.round_rect_bitmap_width),(int)getResources().getDimension(R.dimen.round_rect_bitmap_height),30));
        mImageViewFour.setImageBitmap(RoundRectImageTools.toRoundRectB(bitmap,(int)getResources().getDimension(R.dimen.round_rect_bitmap_width),(int)getResources().getDimension(R.dimen.round_rect_bitmap_height),30));
        mImageViewEight.setImageBitmap(RoundRectImageTools.toRoundRectC(bitmap,(int)getResources().getDimension(R.dimen.round_rect_bitmap_width),(int)getResources().getDimension(R.dimen.round_rect_bitmap_height),30));

        final ImageView ivFive = findViewById(R.id.iv_round_image_five);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0,0,view.getWidth(),view.getHeight());
            }
        };


        ivFive.setOutlineProvider(viewOutlineProvider);
        ivFive.setClipToOutline(true);

        final ImageView ivSix = findViewById(R.id.iv_round_image_six);

        ViewOutlineProvider viewOutlineProvider2 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),30);
            }
        };


        ivSix.setOutlineProvider(viewOutlineProvider2);
        ivSix.setClipToOutline(true);

    }
}
