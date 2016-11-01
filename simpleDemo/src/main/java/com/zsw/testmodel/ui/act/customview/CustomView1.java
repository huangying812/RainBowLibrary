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
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        L.printD(TAG,"getMeasuredWidth="+getMeasuredWidth());
        L.printD(TAG,"getMeasuredHeight="+getMeasuredHeight());

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

        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        /*
          这里我们绘制的宽高尺寸，有两个方法可以获取到 getWidth() 、getMeasureWidth()
          我们该如何选择呢
        第一遍看完源码我得到如下解释（这里就不贴源码了追两下变量就找到了，分析意义不大）
        getWidth()返回mRight - mLeft,他们是在layout()中调用setFrame()赋值.
        计算的是View在父窗口内被显示的尺寸，对padding、margin处理。
        getMeasureWidth() 则是在setMeasuredDimension()调用后赋值，用于确认View
        绘制的实际大小
        在layout()中 我们还会发现 onMeasure 比 onLayout先调用（有疑问请直接进View的Layout()方法中查看）

        土羊土森破的我惯性理解为 当我们设置的测量尺寸大于父窗口返回的getWidth时(例：xml中 子View设置的宽高超过父窗口时)
        两种方法返回的值将不一样
        然而日志出来后结果就像一万只草泥马向我面门踏来：
        xml 和 页面

        日志：
        D/CustomView1: rainbowLog-->>CustomView1-->>getMeasuredWidth=4000
        D/CustomView1: rainbowLog-->>CustomView1-->>getMeasuredHeight=2000
        D/CustomView1: rainbowLog-->>CustomView1-->>getWidth=4000
        D/CustomView1: rainbowLog-->>CustomView1-->>getHeight=2000

        脸已被踩肿，鼻子已骨折。

        这时候需要冷静，既然已经知道时父类在处理onLayout 那我就提刀去看看父类 ，这里View的直接父类是LinearLayout
        果然不出所料！
        在LinearLayout的onlayout()中会调用layoutHorizontal()（上面xml中是Horizontal）
        直接进入layoutHorizontal方法

         void layoutHorizontal(int left, int top, int right, int bottom) {

         ...

                for (int i = 0; i < count; i++) {
                ...
                //直接将测量值作为layout值使用
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                ...

                 childLeft += lp.leftMargin;
                 //递归会直接进入setFram进行保存，所以getWidth 和 getMeasureWid总是一样
                setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                        childWidth, childHeight);
                //中间不做其他处理，只对下一个子View进行水平位置增量计算
                childLeft += childWidth + lp.rightMargin +
                        getNextLocationOffset(child);
            }
            上面的的源码告诉我们，我们选错了用来验证的爸爸。。。大写的尴尬
            我们的目的是搞清楚 getWidth 和getMeasureWidth的情景区别，为了避免同样的窘境再次打脸，
            我们来自己写一个ViewGroup并重写onLayout()方法，修改入参。

        */
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
//        paint.setColor(color);
//        paint.setTextSize(textSize);
//        /**
//         * 这里的 X Y
//         * 是绘制 的起始位置坐标,不能超过View的测量值MAX
//         */
//        canvas.drawText(title,rect.width()/2,getMeasuredHeight()/2,paint);

    }


}
