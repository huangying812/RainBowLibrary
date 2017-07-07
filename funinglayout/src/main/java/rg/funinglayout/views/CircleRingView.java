package rg.funinglayout.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import rg.funinglayout.L;

import static android.R.attr.radius;

/**
 * Create on 2017/7/4.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/7/4
 */
public class CircleRingView extends View {


    public CircleRingView(Context context) {
        super(context, null);
    }

    public CircleRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CircleRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        L.printD(width + "");
        L.printD(height + "");
        setMeasuredDimension(width, height);
    }


    private Paint outPaint;
    private Paint insidePaint;
    private Paint progressPaint;
    private int width;// view 绘制宽度
    private int height;//view 绘制高度
    private int radiusOutside;//半径
    private int radiusInside;//半径
    private int progresWidth;
    private int ringWidth;
    private int radiusCenter;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initTools();
        canvas.drawCircle(width / 2, height / 2, radiusOutside, outPaint);
        canvas.drawCircle(width / 2, height / 2, radiusInside, insidePaint);
        canvas.drawCircle(width / 2, height / 2, radiusCenter, progressPaint);
//        L.printD("radiusOut = " + radiusOutside);
//        L.printD("radiusInside = " + radiusInside);
//        L.printD("radiusCenter = " + radiusCenter);


    }

    private void initTools() {
        ringWidth = 20;
        progresWidth = 50;
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setColor(Color.RED);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(ringWidth);
        insidePaint = new Paint();
        insidePaint.setAntiAlias(true);
        insidePaint.setColor(Color.CYAN);
        insidePaint.setStyle(Paint.Style.STROKE);
        insidePaint.setStrokeWidth(ringWidth);
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.BLUE);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progresWidth);
        initRadius();

    }

    //计算出可用最大半径
    private void initRadius() {
        if (width > height) {
            radiusOutside = (height / 2) - 10;
        } else {
            radiusOutside = (width / 2) - 10;
        }
        radiusInside = radiusOutside - ringWidth - progresWidth;
        radiusCenter = radiusOutside - ringWidth / 2 - progresWidth / 2;
    }

}
