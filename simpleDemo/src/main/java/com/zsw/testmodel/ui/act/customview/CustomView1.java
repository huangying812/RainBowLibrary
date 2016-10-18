package com.zsw.testmodel.ui.act.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;

/**
 * Create on 2016/10/17.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/17
 */
public class CustomView1 extends View {

    private String title;
    private int color;
    private float textSize;
    private int bgColor;

    public CustomView1(Context context) {
        this(context,null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们自定义个属性数组
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomView1,defStyleAttr,0);
                    int count = a.getIndexCount();
                for(int i = 0 ; i<count;i++){
                    //获取attrId
                    int attrId = a.getIndex(i);
                    switch (attrId){
                        case  R.styleable.CustomView1_cv1_content:
                            title = a.getString(attrId);
                        break;

                        case R.styleable.CustomView1_cv1_textColor:

                            color = a.getColor(attrId,Color.GRAY);
                            break;

                        case R.styleable.CustomView1_cv1_textSize:
                            // 默认设置为16sp，TypeValue也可以把sp转化为px
                            textSize = a.getDimensionPixelOffset(attrId,40);
                            break;

                        case R.styleable.CustomView1_cv1_bgColor:
                            bgColor = a.getColor(attrId,Color.BLUE);
                            break;

                        default:break;
                }
        }
        //和游标一样 用完得还回去
        a.recycle();
    }

    //你要画 你总得需要个笔吧
    private Paint paint;
    //要画个框子 矩形对吧 好弄个矩形
    private Rect rect;


    @Override
    protected void onDraw(Canvas canvas){
        L.printD("VIEW","width="+getWidth()+"  Height="+getHeight());

        paint = new Paint();

        rect = new Rect();
        //给笔上色
        paint.setColor(bgColor);
        paint.setTextSize(50);
        paint.getTextBounds(title,0,title.length(),rect);
        //不设置默认就是填充得
        paint.setStyle(Paint.Style.STROKE);
        //画- - - - 这里得宽高参数是 系统测量得Measure值 就是XML中定义的大小咯
        canvas.drawRect(10.0f,0,getMeasuredWidth(),getMeasuredHeight(),paint);
        paint.setColor(color);
        paint.setTextSize(textSize);
        canvas.drawText(title,getMeasuredWidth()/2-rect.width()/2,getMeasuredHeight()/2-rect.height()/2,paint);

    }
}
