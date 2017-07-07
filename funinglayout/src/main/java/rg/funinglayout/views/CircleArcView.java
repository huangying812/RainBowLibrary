package rg.funinglayout.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import rg.funinglayout.L;

/**
 * Create on 2017/7/4.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/7/4
 */
public class CircleArcView extends View {


    public CircleArcView(Context context) {
        super(context, null);
        L.printD("Construtor1");
        initTools();
    }

    public CircleArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        L.printD("Construtor2");
        initTools();
    }

    public CircleArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        L.printD("Construtor3");
        initTools();
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
    private Paint rectFPaint;
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
//        canvas.drawCircle(width / 2, height / 2, radiusCenter, progressPaint);
        L.printD("radiusOut = " + radiusOutside);
        L.printD("radiusInside = " + radiusInside);
        L.printD("radiusCenter = " + radiusCenter);

        int arcWidth = getArcRadius();
        RectF rectF = new RectF();
        //确定位置
        rectF.left =  progresWidth/2;
        rectF.top = progresWidth/2;
        rectF.right = arcWidth ;
        rectF.bottom = arcWidth ;
        L.printD("left="+rectF.left);
        L.printD("right="+rectF.right);
        L.printD("top="+rectF.top);
        L.printD("bottom="+rectF.bottom);
        canvas.drawArc(rectF,0,90,true,rectFPaint);
        rectFPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectF,rectFPaint);
    }

    private int getArcRadius(){
        //减去 progresWidth*2 剩下的是矩形内可绘制最大直径
        int width = this.getWidth() - progresWidth * 2;
        //因为你要画的是一个半圆,半圆一定是在一个矩形里的
        //而在画半圆的时候其实是在一个正方形里绘制一个整圆的一部分
        //那么我们整圆的半径就是半圆进度条的宽度
        return width;
    }

    private void initTools() {
        ringWidth = 10;
        progresWidth = 12;
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
        rectFPaint = new Paint();
        rectFPaint.setAntiAlias(true);
        rectFPaint.setColor(Color.RED);
        rectFPaint.setStyle(Paint.Style.STROKE);
        rectFPaint.setStrokeWidth(10);
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
