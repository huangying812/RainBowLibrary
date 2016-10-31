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
 * OK 名字取好了，开始弄！
 * 欸，卧槽懵逼，该干嘛 ？下意识就要去度娘？ 这一次我们要说 NO!
 * 马丹，为什么不看看系统控件怎么做的，widget 不都是继承View的吗
 * 下面我们去看看 就去 TextView 吧 应该简单些
 * ps:如果你立马就想把TextView的源码看完，NO 你会看死，
 * 提前需要补充下 View 绘制过程的方法调用 可以查查  脑子里有个大概流程
 * 我这里就不啰嗦了，我们需要做的事情
 * 重点 即构造器中属性获取并且保存下来 → 测量onMeasure()计算View实际参数 → 绘制onDraw()根据保存的属性来绘制
 */
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

        // 其实里面会调用getTheme().obtain.....，
        // 所以她最后的直接来源是Resourse.Theme类提供的获取参数方法
        //获取自定义属性时我们应该先想到如何获取Theme/context.getTheme
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomView1,defStyleAttr,0);
                    //这里获取所有索引数量，下面根据每一个索引值循环取值
                    int count = a.getIndexCount();
                for(int i = 0 ; i<count;i++){
                    //遍历获取所有的属性，并保存
                    int attrId = a.getIndex(i);
                    switch (attrId){
                        case  R.styleable.CustomView1_cv1_content:
                            title = a.getString(attrId);
                        break;
                        case R.styleable.CustomView1_cv1_textColor:
                            color = a.getColor(attrId,Color.GRAY);
                            break;
                        case R.styleable.CustomView1_cv1_textSize:
                            //偏移直接去整
                            textSize = a.getDimensionPixelOffset(attrId
                                    //以我蹩脚的英语理解为 合成为一个像素值,直接舍去小数
                                    ,TypedValue.complexToDimensionPixelOffset(15,getResources().getDisplayMetrics()));
                            //和上面一样返回一个像素值，但是对小数会进行四舍五入 差别不大
                            textSize = a.getDimensionPixelSize(attrId,40);
                            //源码--
                            /**
                             *  在自定义控件中使用自定义属性时，经常需要使用java代码获取在xml中定义的尺寸，
                             *  相关有以下三个函数
                             getDimension()
                             getDimensionPixelOffset()
                             getDimensionPixelSize()
                             （在TypedArray和Resources中都有这三个函数，
                             功能类似，TypedArray中的函数是获取自定义属性的，Resources中的函数是获取android预置属性的）
                             详细地址：原文作者 zhugogogo http://www.eoeandroid.com/thread-322627-1-1.html?_dsign=91c59c8f
                             */
                            break;

                        case R.styleable.CustomView1_cv1_bgColor:
                            bgColor = a.getColor(attrId,Color.BLACK);
                            break;

                        default:break;
                }
        }
        //用完记得释放，其他地方才能继续使用
        a.recycle();
    }

    //你要画 你总得需要个笔吧
    private Paint paint;
    //要画个框子 矩形对吧 好弄个矩形
    private Rect rect;

    //这里我们先不考虑 onMeasure 方法 因为暂时我们不知道覆盖他来解决什么

    private  static final String TAG = "CustomView1";

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

        canvas.drawRect(50,50,getWidth()-50,getHeight()-50,paint);

//        paint.setColor(color);
//        paint.setTextSize(textSize);
//        /**
//         * 这里的 X Y
//         * 是绘制 的起始位置坐标,不能超过View的测量值MAX
//         */
//        canvas.drawText(title,rect.width()/2,getMeasuredHeight()/2,paint);

    }


}
