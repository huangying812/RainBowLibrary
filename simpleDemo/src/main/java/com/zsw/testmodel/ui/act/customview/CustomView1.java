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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;

import static android.R.attr.mode;
import static android.R.attr.x;

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
 * 我们先模仿一个简单的TextView ，直接继承自View
 * 步骤
 * 1.获取自定义属性并且保存下来
 * 2.测量onMeasure()为View确定尺寸
 * 3.绘制onDraw()根据保存的属性来绘制图形和文字
 */
    //我们预设的自定义属性
    private String title;
    private int color;
    private float textSize;
    private int bgColor;

    //默认尺寸大小  单位 px
    private int normalWidth = 150;
    private int normalHeight = 150;

    public CustomView1(Context context) {
        this(context,null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    //1.构造器中属性获取并且保存下来
    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 这里其实不是context为我们获取的样式参数，里面会调用getTheme().obtain...，
        // 这里应该有疑问 从何知道用这样的方式获取自定义属性，
        // 下意识你应该点开TextView的源码，进入最后一个构造器内，一看便知
        TypedArray a =  context.obtainStyledAttributes(attrs,R.styleable.CustomView1,defStyleAttr,0);
                    //这里获取所有索引数量，下面根据每一个索引值循环取值
                    int count = a.getIndexCount();
                for(int i = 0 ; i<count;i++){
                    //遍历获取所有的属性，并保存
                    int attrId = a.getIndex(i);
                    switch (attrId){
                        case  R.styleable.CustomView1_cv1_text:
                            title = a.getString(attrId);
                        break;
                        case R.styleable.CustomView1_cv1_textColor:
                            color = a.getColor(attrId,Color.GRAY);
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
                                    ,TypedValue.complexToDimensionPixelOffset(15,getResources().getDisplayMetrics()));
                            //和上面一样返回一个像素值，但是对小数会进行四舍五入 差别不大
//                            textSize = a.getDimensionPixelSize(attrId,40);
                            break;
                        case R.styleable.CustomView1_cv1_stokeColor:
                            bgColor = a.getColor(attrId,Color.BLACK);
                            break;

                        default:break;
                }
        }
        //上面我们的属性获取和默认值设置已经搞定
        //TypedArray用完记得释放，其他地方才能继续使用
        a.recycle();
    }

    //你要画 你总得需要个笔吧
    private Paint paint;
    //要画个框子 矩形对吧 好弄个矩形
    private Rect rect;
    private  static final String TAG = "CustomView1";

    private int widthSize;
    private int heightSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //这里我们判断了模式为AT_MOST时给出默认大小，不知道为什么这样做的同学可以参考我另一篇文章：
        //自定义View 测量的所有细节。个人觉得这里还是很重要的，因为每一种View的实现都需要考虑到
        //xml直接影响父类对测量模式的确定继而确定尺寸参数，我们根据模式才能确定自己绘制的大小
        if(widMode == MeasureSpec.AT_MOST){
            L.printD(TAG,"model== AT_MOST");
            widthSize = TypedValue.complexToDimensionPixelOffset(normalWidth,getResources().getDisplayMetrics());
        }

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = TypedValue.complexToDimensionPixelOffset(normalHeight,getResources().getDisplayMetrics());
        }

        setMeasuredDimension(widthSize,heightSize);
        //调用setMeasuredDimension直接将新尺寸作为参数覆盖，从这个方法进去最终会进入下面的源码
        /*
        private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        mMeasuredWidth = measuredWidth;
        mMeasuredHeight = measuredHeight;
        mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;
    }
         */
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint = new Paint();
        rect = new Rect();
        paint.setColor(bgColor);
        paint.setTextSize(textSize);

        //这里我们设置不填充，为了方便看效果
        paint.setStrokeWidth(50);
        paint.setStyle(Paint.Style.STROKE);

        //绘制坐标 分为 left起点坐标 top起点坐标 我们可以看作是平移量
        //注：平移的是绘制内容 而不是View 也就是layout参数不变（允许绘制到View边界外面，但不可见）
        //right bottom 分别用来计算绘制长度 实际面积  ~(right-left)* ~(bottom-top)

        canvas.drawRect(0,0,getWidth(),getHeight(),paint);

//        paint.setColor(color);
//        paint.setTextSize(textSize);
//        /**
//         * 这里的 X Y
//         * 是绘制 的起始位置坐标,不能超过View的测量值MAX
//         */
//        canvas.drawText(title,rect.width()/2,getMeasuredHeight()/2,paint);

    }


}
