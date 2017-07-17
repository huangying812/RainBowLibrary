package rg.funinglayout.views.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import rg.funinglayout.R;

import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Create on 2017/7/17.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/7/17
 */
public class Canvas1 extends View {
    public Canvas1(Context context) {
        this(context, null);
    }

    public Canvas1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Canvas1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmapHero = BitmapFactory.decodeResource(getResources(), R.mipmap.hero);
        Bitmap bitmapImp = BitmapFactory.decodeResource(getResources(), R.mipmap.imp).copy(Bitmap.Config.ARGB_8888, true);
        canvas.drawBitmap(bitmapHero, 0, 0, null);
        canvas.drawBitmap(bitmapImp, getWidth()/3, getHeight()/3, null);

        Canvas canvas1 = new Canvas(bitmapImp);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20F);
        Rect rect = new Rect();
        rect.top = 20;
        rect.left = 20;
        rect.bottom = getMeasuredHeight();
        rect.right  = getMeasuredWidth();
        canvas1.drawRect(rect,paint);
        canvas.drawBitmap(bitmapImp, 0, 0, null);
        //bitmap是画布的具体体现，即：都是在底片bitmap上绘制
    }

}
