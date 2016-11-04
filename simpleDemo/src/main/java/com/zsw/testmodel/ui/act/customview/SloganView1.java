package com.zsw.testmodel.ui.act.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;

import static android.R.attr.factor;
import static android.R.attr.mode;
import static android.R.attr.strokeColor;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.R.string.no;
import static android.os.Build.VERSION_CODES.M;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Create on 2016/10/17.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/17
 */
public class SloganView1 extends View {
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
    private int normalWidth = 600;
    private int normalHeight = 300;

    public SloganView1(Context context) {
        this(context, null);
    }

    public SloganView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //1.构造器中属性获取并且保存下来
    public SloganView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 这里其实不是context为我们获取的样式参数，里面会调用getTheme().obtain...，
        // 这里应该有疑问 从何知道用这样的方式获取自定义属性，
        // 下意识应该点开TextView的源码，进入最后一个构造器内，一看便知
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
                     //看完源码具体计算，再加以我蹩脚的英语理解为 合成一个像素值,直接舍去小数
                     getDimensionPixelOffset()
                     //和上面一样返回一个像素值，但是对小数会进行四舍五入 差别不大
                     getDimensionPixelSize()
                     （在TypedArray和Resources中都有这三个函数，
                     功能类似，TypedArray中的函数是获取自定义属性的，Resources中的函数是获取android预置属性的）
                     注：Resources 最里面还是用的TypedArray 来查找和获取 Res 属性值的。
                     */
                    //偏移直接去整
                    textSize = a.getDimensionPixelOffset(attrId

                            , TypedValue.complexToDimensionPixelOffset(15, getResources().getDisplayMetrics()));

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

    private int mWidthSize;
    private int mHeightSize;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        L.printD(TAG, "onMeasure");
        L.printD(TAG,"getMeasuredWidth="+getMeasuredWidth());
        L.printD(TAG,"getMeasuredHeight="+getMeasuredHeight());
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widMode == MeasureSpec.AT_MOST) {
            mWidthSize = normalWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            mHeightSize = normalHeight;
        }

        setMeasuredDimension(mWidthSize, mHeightSize);

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
    //用来测量text宽高的矩形框
    private Rect bounds;

    void drawTest(Canvas canvas){
        //下面的测试代码来源，http://blog.csdn.net/hursing/article/details/18703599
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        String testString = "测试Text:的绘制";
        Rect bounds1 = new Rect();
        paint.getTextBounds("测", 0, 1, bounds1);
        Rect bounds2 = new Rect();
        paint.getTextBounds("测试Text:", 0, 6, bounds2);
        // 随意设一个位置作为baseline
        int x = 200;
        int y = 400;
        // 把testString画在baseline上
        canvas.drawText(testString, x, y, paint);
        // bounds1
        paint.setStyle(Paint.Style.STROKE);  // 画空心矩形
        canvas.save();
        canvas.translate(x, y);  // 注意这里有translate。getTextBounds得到的矩形也是以baseline为基准的
        paint.setColor(Color.GREEN);
        canvas.drawRect(bounds1, paint);
        canvas.restore();
        // bounds2
        canvas.save();
        paint.setColor(Color.MAGENTA);
        canvas.translate(x, y);
        canvas.drawRect(bounds2, paint);
        canvas.restore();
        // baseline
        paint.setColor(Color.RED);
        canvas.drawLine(x, y, 1024, y, paint);
        // ascent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(x, y+fmi.ascent, 1024, y+fmi.ascent, paint);
        // descent
        paint.setColor(Color.BLUE);
        canvas.drawLine(x, y+fmi.descent, 1024, y+fmi.descent, paint);
        // top
        paint.setColor(Color.DKGRAY);
        canvas.drawLine(x, y+fmi.top, 1024, y+fmi.top, paint);
        // bottom
        paint.setColor(Color.GREEN);
        canvas.drawLine(x, y+fmi.bottom, 1024, y+fmi.bottom, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawTest(canvas);
        L.printD(TAG,"onDraw----");
        L.printD(TAG,"getWidth="+getWidth());
        L.printD(TAG,"getHeight="+getHeight());
        L.printD(TAG,"getMeasuredWidth="+getMeasuredWidth());
        L.printD(TAG,"getMeasuredHeight="+getMeasuredHeight());
        paint = new Paint();
        paint.setColor(strokeColor);
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
                由此，得知这里绘制应该使用getMeasureWidth()获取的尺寸来计算，保证图形和文字能够
                完全绘制出来。如果getWidth 小于 getMeasureWidth()的大小，那么应该是xml中我们给的布局尺寸不够
                举个小例子，在xml中写了一个TextView 字体很大，行数很多，屏幕上只显示了半行字。
    */
        canvas.drawRect(0, 0, getMeasuredWidth()
                , getMeasuredHeight(), paint);
        paint.setStrokeWidth(5);
        canvas.drawLine(0,getMeasuredHeight()/2,getMeasuredWidth(),getMeasuredHeight()/2,paint);
        /*
            这里绘制文字的方法是 canvas.drawText(text,x,y,paint);
            猛的一看，臆想四个参数(文本，x,y(横纵起点)，一个画笔)
            但是注释是这样解释的
              * @param text  绘制的文本
              * @param x     x轴绘制起点坐标
              * @param y     基准线baseline的起点坐标(下面会解释baseline是什么)
              * @param paint 画笔
          //这里的x起点很好计算，(getMeasuredWidth()-rect.width())/2  左右边距相同即水平居中
          //首先要记住： baseline 是基准线！基准线！上面注释里说的很清除，
          //可以理解为在单独绘制文字时,根据局部文本尺寸建立新的坐标系  baseLineY = 0;
          //下面是对中心y坐标的计算，我也是翻了很多老司机的博客，最后根据自己实际测试代码来学习的
          //这里给上最一篇我认为分析【字体绘制位置的计算】的最清晰的博客 http://blog.csdn.net/wan778899/article/details/51460849
          //当然你也可以像笔者一样把FontMetrics中的几个边界线绘制一遍，再推敲计算公式
          //**上面边线的绘制是参照TextView源码中的BoringLayout中对文字绘制的算法**
          //最后我得到的算法如下： int baseLine = getMeasuredHeight()/2 - ~(fontMetrics.bottom - fontMetrics.top)+rect.height()/2;
          //博主给出的公式，理解上没错但是实际效果确总是靠上一些，希望发现我问题出现在那里的小伙伴能评论告诉我

    */
        paint.reset();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        //测量text
        bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        //先取得View高度得一半，减去文本得top到view的边距，再下降文本高度得一半 得到baseline
        int baseLine = getMeasuredHeight()/2 - ~(fontMetrics.bottom - fontMetrics.top)+bounds.height()/2;
        canvas.drawText(text,(getMeasuredWidth()-bounds.width())/2,baseLine,paint);

    }


}
