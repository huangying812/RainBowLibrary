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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;

import static android.R.attr.mode;
import static android.R.attr.strokeColor;
import static android.R.attr.x;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Create on 2016/10/17.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/17
 */
public class CustomView1 extends View {
    /**
     * 我们先模仿一个带边框的TextView ,用来显示个slogan，直接继承自View
     * 步骤
     * 1.获取自定义属性并且保存下来
     * 2.测量onMeasure()为View确定尺寸
     * 3.绘制onDraw()根据保存的属性来绘制图形和文字
     */
    //下面的变量用来保存自定义属性
    private String text;
    private int textColor;
    private float textSize;
    private int strokeColor;
    private int strokeWidth;

    //默认尺寸大小  单位 px
    private int normalWidth = 260;
    private int normalHeight = 140;

    public CustomView1(Context context) {
        this(context, null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //1.构造器中属性获取并且保存下来
    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 这里其实不是context为我们获取的样式参数，里面会调用getTheme().obtain...，
        // 这里应该有疑问 从何知道用这样的方式获取自定义属性，
        // 下意识你应该点开TextView的源码，进入最后一个构造器内，一看便知
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView1, defStyleAttr, 0);
        //这里获取所有索引数量，下面根据每一个索引值循环取值
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            //遍历获取所有的属性，并保存
            int attrId = a.getIndex(i);
            switch (attrId) {
                case R.styleable.CustomView1_cv1_text:
                    text = a.getString(attrId);
                    break;
                case R.styleable.CustomView1_cv1_textColor:
                    textColor = a.getColor(attrId, Color.GRAY);
                    break;
                case R.styleable.CustomView1_cv1_textSize:
                    /**
                     *  在自定义控件中使用自定义属性时，需要在java代码中动态的设置默认值
                     *  就需要用到以下三个方法
                     getDimension()
                     getDimensionPixelOffset()
                     getDimensionPixelSize()
                     （在TypedArray和Resources中都有这三个函数，
                     功能类似，TypedArray中的函数是获取自定义属性的，Resources中的函数是获取android预置属性的）
                     注：Resources 最里面还是用的TypedArray 来查找和获取 Res 属性值的。
                     */
                    //偏移直接去整
                    textSize = a.getDimensionPixelOffset(attrId
                            //看完源码具体计算，再加以我蹩脚的英语理解为 合成一个像素值,直接舍去小数
                            , TypedValue.complexToDimensionPixelOffset(15, getResources().getDisplayMetrics()));
                    //和上面一样返回一个像素值，但是对小数会进行四舍五入 差别不大
//                            textSize = a.getDimensionPixelSize(attrId,40);
                    break;
                case R.styleable.CustomView1_cv1_strokeWidth:
                    strokeWidth = a.getDimensionPixelSize(attrId
                            , TypedValue.complexToDimensionPixelOffset(1, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomView1_cv1_strokeColor:
                    strokeColor = a.getColor(attrId, Color.BLACK);
                    break;

                default:
                    break;
            }
        }
        //上面我们的属性获取和默认值设置已经搞定
        //TypedArray用完记得释放，其他地方才能继续使用
        a.recycle();
    }

    private static final String TAG = "CustomView1";

    private int widthSize;
    private int heightSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.printD(TAG, "onMeasure");
        L.printD(TAG,"getMeasuredWidth="+getMeasuredWidth());
        L.printD(TAG,"getMeasuredHeight="+getMeasuredHeight());
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //这里我们判断了模式为AT_MOST时给出默认大小包裹text，不知道为什么这样做的同学可以参考我另一篇文章：
        //自定义View 测量的所有细节。个人觉得这里还是很重要的，因为每一种View的实现都需要考虑到
        //xml直接影响父类对测量模式的确定继而确定尺寸参数，我们根据模式才能确定自己绘制的大小
        if (widMode == MeasureSpec.AT_MOST) {
            widthSize = normalWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = normalHeight;
        }

        setMeasuredDimension(widthSize, heightSize);

        //从这个方法进去最终会进入下面的源码，调用setMeasuredDimension直接将新尺寸作为参数覆盖，
        /*
        private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        mMeasuredWidth = measuredWidth;
        mMeasuredHeight = measuredHeight;
        mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;
    }
         */
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(true, left, top, right, bottom);
        L.printD(TAG, "onLayout");

    }

    //你要画 你总得需要个笔吧
    private Paint paint;
    //要画个框子 矩形对吧 准备一个矩形
    private Rect rect;

    @Override
    protected void onDraw(Canvas canvas) {
        L.printD(TAG,"onDraw----");
        paint = new Paint();
        rect = new Rect();
        paint.setColor(strokeColor);

        L.printD(TAG,"getWidth="+getWidth());
        L.printD(TAG,"getHeight="+getHeight());
        L.printD(TAG,"getMeasuredWidth="+getMeasuredWidth());
        L.printD(TAG,"getMeasuredHeight="+getMeasuredHeight());
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        /*
          这里我们绘制的宽高尺寸，有两个方法可以获取到 getWidth() 、getMeasureWidth()
          我们该如何选择呢
        第一遍看完源码我得到如下解释（这里就不贴源码了追两下变量就找到了）
        getWidth()返回mRight - mLeft,他们是在layout()中调用setFrame()赋值.
        计算的是View在父窗口内被显示的尺寸，对padding、margin及其他布局参数处理，
        最后保存为子View布局大小参数，确定子View显示尺寸大小。
        getMeasureWidth() 则是在setMeasuredDimension()调用后赋值，用于确认View绘制的实际大小
        在layout()中 我们还会发现 onMeasure 比 onLayout先调用（有疑问请直接进View的Layout()方法中查看）

        为了更直观的了解 ，我们写一个父布局容器来重写onLayout方法，对子类的布局大小进行设置
        public class CustomViewGroup1 extends ViewGroup {
                ...
                @Override
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    measureChildren(widthMeasureSpec, heightMeasureSpec);
                     }

                @Override
                protected void onLayout(boolean changed, int l, int t, int r, int b) {

                            int count = getChildCount();
                            //我们这里什么都不做，就强行设置子View的宽高为 100，200
                             for(int i = 0;i<count;i++){
                             View childView = getChildAt(i);
                             L.printD(TAG,"childView - me width=="+childView.getMeasuredWidth());
                             L.printD(TAG,"cheildView - me Height = "+childView.getMeasuredHeight());
                             childView.layout(0,0,300,300);
                      }
                  }
                ...
                xml:和布局图
                日志打印：
                //父类测量子类传入onMeasure的尺寸
                CustomView1-->>onMeasure
                CustomView1-->>getMeasuredWidth=1280
                CustomView1-->>getMeasuredHeight=2108
                //父类 onLayout方法中获取子类自己重写测量后的尺寸，并设置布局尺寸为300，300
                CustomViewGroup1-->>childView - me width==260
                CustomViewGroup1-->>cheildView - me Height = 140

                CustomView1-->>onLayout
                CustomView1-->>onDraw----
                //最终用于绘制的布局尺寸和测量尺寸
                CustomView1-->>getWidth=300
                CustomView1-->>getHeight=300
                CustomView1-->>getMeasuredWidth=260
                CustomView1-->>getMeasuredHeight=140

                }
                由此，得知这里绘制应该使用getMeasureWidth()获取的尺寸来绘制，保证图形和文字能够
                完全绘制出来。如果getWidth 小于 getMeasureWidth()的大小，那么应该是xml中我们给的布局尺寸不够
                举个小例子，在xml中写了一个TextView 字体很大，行数很多，屏幕上只显示了半行字。
        */
        canvas.drawRect(strokeWidth, strokeWidth, getMeasuredWidth()+strokeWidth, getMeasuredHeight()+strokeWidth, paint);
        paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        L.printD(TAG,textColor+"");
        L.printD(TAG,text);
        L.printD(TAG,textSize+"");
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,getMeasuredWidth()/2,getMeasuredHeight()/2,paint);

    }


}
