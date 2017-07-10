package rg.funinglayout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

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
public class CircleProgressView extends View implements HelpHandler.OnStartCuttinProgressListener {

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
    private String text;
    //文字颜色
    @ColorInt
    private int textColor = Color.BLUE;
    //文字大小
    private float textSize = TypedValue.complexToDimensionPixelOffset(15, getResources().getDisplayMetrics());

    private OnProgressChangedListener onProgressChangedListener;
    private boolean openAnimation = false;
    private HelpHandler helpHandler;
    //是否反转绘制角度
    private boolean reverseAngle = false;
    //是否绘制文字
    private boolean drawText = false;

    private void calculateArcAngle(int progress) {
        if (progress > 0) {
            progress = reverseAngle ? ~progress : progress;
            BigDecimal decimal1 = new BigDecimal(progress);
            BigDecimal decimal2 = new BigDecimal(max);
            BigDecimal divide = decimal1.divide(decimal2, 3, BigDecimal.ROUND_HALF_EVEN);
            sweepAngle = 360 * divide.floatValue();
        } else {
            sweepAngle = 0;
        }


    }


    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helpHandler = new HelpHandler(this);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            //获取attr属性Id
            int attrId = typedArray.getIndex(i);
            switch (attrId) {
                case R.styleable.CircleProgressView_cp_text:
                    text = typedArray.getString(attrId);
                    break;
                case R.styleable.CircleProgressView_cp_textColor:
                    textColor = typedArray.getColor(attrId, Color.BLUE);
                    break;

                case R.styleable.CircleProgressView_cp_textSize:
                    textSize = typedArray.getDimensionPixelOffset(attrId
                            , TypedValue.complexToDimensionPixelOffset(15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_underColor:
                    underColor = typedArray.getColor(attrId, Color.GRAY);
                    break;
                case R.styleable.CircleProgressView_cp_progressColor:
                    progressColor = typedArray.getColor(attrId, Color.BLUE);
                    break;
                case R.styleable.CircleProgressView_cp_max:
                    max = typedArray.getInt(attrId, 100);
                    break;
                case R.styleable.CircleProgressView_cp_progress:
                    progress = typedArray.getInteger(attrId, 0);
                    calculateArcAngle(progress);
                    break;
                case R.styleable.CircleProgressView_cp_strokeWidth:
                    strokeWidth = typedArray.getDimensionPixelOffset(attrId,
                            TypedValue.complexToDimensionPixelOffset(20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_padding:
                    padding = typedArray.getDimensionPixelOffset(attrId,
                            TypedValue.complexToDimensionPixelOffset(10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressView_cp_startAngle:
                    startAngle = typedArray.getInt(attrId, 0);
                    break;
                case R.styleable.CircleProgressView_cp_reverseAngle:
                    reverseAngle = typedArray.getBoolean(attrId, false);
                    break;
                case R.styleable.CircleProgressView_cp_drawText:
                    drawText = typedArray.getBoolean(attrId, false);
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
        rectF.top =  padding;
        rectF.left =  padding;
        rectF.bottom = getMeasuredHeight() - padding ;
        rectF.right = getMeasuredWidth() - padding ;
        drawUnderArc(canvas, rectF);
        drawArc(canvas, rectF);
        drawText(canvas);
    }

    //绘制文字
    private void drawText(Canvas canvas) {
        if (!drawText) {
            return;
        }
        if (null == text || "".equals(text) || text.length() == 0) {
            text = progress + "%";
        }
        Rect bounds = new Rect();
        Paint paint = obtainPaint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int baseLine = getMeasuredHeight() / 2 - ~(fontMetrics.bottom - fontMetrics.top) + bounds.height() / 2;
        canvas.drawText(text, (getMeasuredWidth() - bounds.width()) / 2, baseLine, paint);
        L.printD("绘制 文字 -- ");


    }

    //绘制underArc
    private void drawUnderArc(Canvas canvas, RectF bounds) {
        Paint paint = obtainPaint();
        paint.setColor(underColor);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(bounds, 0, 360, false, mPaint);

    }

    //绘制圆弧
    private void drawArc(Canvas canvas, RectF bounds) {
        Paint paint = obtainPaint();
        paint.setColor(progressColor);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(bounds, startAngle, sweepAngle, false, mPaint);
    }


    private Paint obtainPaint() {
        if (null != mPaint) {
            mPaint.reset();
        } else {
            mPaint = new Paint();
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        return mPaint;

    }

    public void commit() {
        if (openAnimation) {
            helpHandler.startCutting(progress);
        } else {
            postInvalidate();
        }

    }

    @Override
    public void onCutting(int progress) {
        this.progress = progress;
        calculateArcAngle(progress);
        invalidate();
        if (null != onProgressChangedListener) {
            onProgressChangedListener.onProgressChanged(progress);
        }

    }


    public interface OnProgressChangedListener {
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
        invalidate();
    }

    public void setReverseAngle(boolean reverseAngle) {
        this.reverseAngle = reverseAngle;
    }

    public void setDrawText(boolean drawText) {
        this.drawText = drawText;
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
