package rg.funinglayout.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
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

    private int widthMeasure;
    private int heightmeasure;
    private Paint mPaint;
    private int strokeWidth = 10;



    public CircleArcView(Context context) {
        super(context, null);
        init();

    }

    public CircleArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();

    }

    public CircleArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        heightmeasure = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthMeasure,heightmeasure);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        rect.top = 0;
        rect.left = 0;
        rect.bottom = getMeasuredHeight();
        rect.right  = getMeasuredWidth();

        drawRect(canvas,rect);

        RectF rectF = new RectF();
        rectF.top = strokeWidth;
        rectF.left = strokeWidth;
        rectF.bottom =  getMeasuredHeight()-strokeWidth;
        rectF.right = getMeasuredWidth()-strokeWidth;
        drawRectF(canvas,rectF);
        drawArc(canvas,rectF);

    }

    //绘制圆弧
    private void drawArc(Canvas canvas,RectF rectF){
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF,0,120,false,mPaint);
    }

    //绘制一个圆弧的矩形框
    private void drawRectF(Canvas canvas,RectF rectF){
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectF,mPaint);

    }



    //先绘制一个 和View绘制面积相同的 矩形边框
    private void drawRect(Canvas canvas,Rect rect){
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth()
                , getMeasuredHeight(), mPaint);
    }



    private void init(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

}
