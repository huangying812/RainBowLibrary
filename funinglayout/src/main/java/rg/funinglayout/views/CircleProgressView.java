package rg.funinglayout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.math.BigDecimal;

import rg.funinglayout.L;
import rg.funinglayout.R;

/**
 * Create on 2017/7/4.
 * github  https://github.com/HarkBen
 * Description:
 * -----圓形進度條------
 * author Ben
 * Last_Update - 2017/7/4
 */
public class CircleProgressView extends View implements HelpHandler.OnHelpHandlerSendNotice{

    private Paint mPaint;
    private float sweepAngle = 0;
    private int startAngle = 0;
    //diy属性
    //圆环宽度
    private float strokeWidth = 20.0f;
    //圆环边距
    private float padding = 5.0f;
    //最大进度值
    private int max = 100;
    //进度值
    private int progress = 0;

    //圆环底色
    @ColorInt
    private int underColor = Color.GRAY;
    //进度条
    @ColorInt
    private int progressColor = Color.BLUE;
   //文字
    private String text ;
    //文字颜色
    @ColorInt
    private int textColor = Color.BLUE;
    //文字大小
    private float textSize = TypedValue.complexToDimensionPixelOffset(15,getResources().getDisplayMetrics());

    private OnProgressChangedListener onProgressChangedListener;
    private boolean openAnimation = false;
    private HelpHandler helpHandler;



   private void  calculateArcAngle(int progress){
       BigDecimal decimal1 = new BigDecimal(progress);
       BigDecimal decimal2 = new BigDecimal(max);
       BigDecimal divide = decimal1.divide(decimal2,3,BigDecimal.ROUND_HALF_EVEN);
       sweepAngle = 360 * divide.floatValue();

   }


    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helpHandler = new HelpHandler();
        helpHandler.setOnHelpHandlerSendNotice(this);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        int indexCount = typedArray.getIndexCount();
        for(int i = 0;i<indexCount;i++){
            //获取attr属性Id
            int attrId = typedArray.getIndex(i);
            switch (attrId){
                case R.styleable.CircleProgressView_cp_text:
                    text = typedArray.getString(attrId);
                    break;
                case R.styleable.CircleProgressView_cp_textColor:
                    textColor = typedArray.getColor(attrId,Color.BLUE);
                    break;

                case R.styleable.CircleProgressView_cp_textSize:
                    textSize = typedArray.getDimensionPixelOffset(attrId
                            , TypedValue.complexToDimensionPixelOffset(15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_underColor:
                    underColor = typedArray.getColor(attrId,Color.GRAY);
                    break;
                case R.styleable.CircleProgressView_cp_progressColor:
                    progressColor = typedArray.getColor(attrId,Color.BLUE);
                    break;
                case R.styleable.CircleProgressView_cp_max:
                    max = typedArray.getInt(attrId,100);
                    break;
                case R.styleable.CircleProgressView_cp_progress:
                    progress = typedArray.getInteger(attrId,0);
                    calculateArcAngle(progress);
                    break;
                case R.styleable.CircleProgressView_cp_strokeWidth:
                    strokeWidth = typedArray.getDimensionPixelOffset(attrId,
                            TypedValue.complexToDimensionPixelOffset(20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_padding:
                    padding = typedArray.getDimensionPixelOffset(attrId,
                            TypedValue.complexToDimensionPixelOffset(10,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_startAngle:
                    startAngle = typedArray.getInt(attrId,0);
                    break;
                default:
                    break;
            }


        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.top = strokeWidth+padding;
        rectF.left = strokeWidth+padding;
        rectF.bottom =  getMeasuredHeight()-padding-strokeWidth;
        rectF.right = getMeasuredWidth()-padding-strokeWidth;
        drawUnderArc(canvas,rectF);
        drawArc(canvas,rectF);
        drawText(canvas);
        L.printD("绘制完成");
    }

    //绘制文字
    private void drawText(Canvas canvas){
        if(null  == text||"".equals(text) || text.length() == 0){
            text = progress+"%";
        }
        Rect bounds = new Rect();
        Paint paint =  obtainPaint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        int baseLine = getMeasuredHeight()/2 - ~(fontMetrics.bottom - fontMetrics.top)+bounds.height()/2;
        paint.getTextBounds(text,0,text.length(),bounds);
        canvas.drawText(text,(getMeasuredWidth()-bounds.width())/2,baseLine,paint);
        L.printD("绘制 文字 -- ");


    }

    //绘制underArc
    private void drawUnderArc(Canvas canvas,RectF bounds){
        Paint paint =  obtainPaint();
        paint.setColor(underColor);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(bounds,0,360,false,mPaint);

    }

    //绘制圆弧
    private void drawArc(Canvas canvas,RectF bounds){
        Paint paint =  obtainPaint();
        paint.setColor(progressColor);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(bounds,startAngle,sweepAngle,false,mPaint);
    }


    private Paint obtainPaint(){
        if(null != mPaint){
            mPaint.reset();
        }else {
            mPaint = new Paint();
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        return  mPaint;

    }

    public void commit(){
        if(openAnimation){
            helpHandler.startCutting(progress);
        }else {
            postInvalidate();
        }

    }

    @Override
    public void receiver(int progress) {
        this.progress = progress;
        if(null != onProgressChangedListener ){
            onProgressChangedListener.onProgressChanged(progress);
        }
        postInvalidate();
        L.printD("收到进度="+progress);
    }


    public interface OnProgressChangedListener{
        void onProgressChanged(int progress);
    }


    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        calculateArcAngle(progress);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setUnderColor(int underColor) {
        this.underColor = underColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public void setOpenAnimation(boolean openAnimation) {
        this.openAnimation = openAnimation;
    }
}
