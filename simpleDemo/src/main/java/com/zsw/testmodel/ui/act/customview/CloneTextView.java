package com.zsw.testmodel.ui.act.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.L;

/**
 * Create on 2016/10/28.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/28
 */
public class CloneTextView extends TextView {

    //TextView一共包含4个构造  有2个最低只支持API21的我们先拿掉
    public CloneTextView(Context context) {
        this(context, null);
    }

    public CloneTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CloneTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    这里我们要知道一个View的绘制流程中重要的几个方法，
    如果你实现过嵌套Drawlayout 或者 ScrollView嵌套ListView 就应该了解过重绘和测量
    不知道的同学自行百度View的绘制流程，篇幅过大我这里就不多赘述了，
    可以参考张兴业博客  http://blog.csdn.net/xyz_lmn/article/details/20385049
    三个方法的日志打印顺序为 - onMeasure → onLayout →  onDraw
     */

    //这个方法一般是父类来实现，控制子类布局位置,TextView内操作不多
    //可以查看更好理解的 LinearLayout 内的onLayout方法
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        L.printD("CT", "onLayout");
    }

    /*
    绘制这里仅从源码中知道有三种模式
    TextView中的源码
        public static class MeasureSpec {

               private static final int MODE_SHIFT = 30;

                  //为了秒懂后面的运算结果，这里将2进制码都写出来
                  //MODE_MASK 换算后 2进制为 11000000000000000000000000000000
                  private static final int MODE_MASK  = 0x3 << MODE_SHIFT;

                  //换算后 2进制为  01000000000000000000000000000000
                  public static final int EXACTLY     = 1 << MODE_SHIFT;// int 值 1073741824

                  //换算后 2进制为  10000000000000000000000000000000
                  public static final int AT_MOST     = 2 << MODE_SHIFT;//int 值-2147483648

                  //目前我还没看到这个模式有什么实际卵用，希望有实例的同学能能够与我共赏
                  public static final int UNSPECIFIED = 0 << MODE_SHIFT;//int 值 0

        //下面我们看看他怎么计算大小的
        public static int getSize(int measureSpec) {
            return (measureSpec & ~MODE_MASK);
             }
        }
        //高2位被忽略，这样其实就是measureSpec的大小

        //再看看如何计算模式的
         public static int getMode(int measureSpec) {
            return (measureSpec & MODE_MASK);
        }
        //因为后MODE_MASK30位全是0 ,交集是高2位
        //说明父类传入的spec高2位为测量模式，后30位为大小

    搞懂了上面的计算方式，下面onMeasure就非常容易懂了
     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    ...

          if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        } else {
    ...
             // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());

            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, width);
            }
        }
      }

    我一番折和翻译注释暂时是这样理解的，
      模式一 强制模式  EXACTLY - 父类给出实际大小，并且作为默认值直接使用（但也可以自代码中动态修改为任意大小）
      模式二 半开放模式  AT_MOST- 父类给出最大值限定，没有默认值,子类自己计算大小但是不能超过父类限定（但也可以自代码中动态修改为任意大小)
      模式三 freedom    UNSPECIFIED   大概意思就是没有限制，你想多大就多大 金箍棒模式

    实际场景还未监测，下面我们就来用代码验证

    从xml的定义到获取实际的SpecSize 到计算出测量模式和实际的Size



    所以我决定费点功夫 用测试来看看如何绘制
    我们将源码中 mode的计算 和 size的计算拷贝出来，然后使用一样的运算符来计算 最后对比一下就知道了
    方法是笨了点 但是理解吃透才是最重要的
    这边我已经将 三种测量模式的int值计算出来了，



    */
    private int widSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widSpec = widthMeasureSpec;
        L.printD("CT", "onMeasure");

    }


    //这里很简单了，和名字一样就是绘制，大小在onMeasure确定， 位置已经在onLayout 固定
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        L.printD("CT", "onDraw");
        int MODE_SHIFT = 30;
        int MODE_MASK = 0x3 << MODE_SHIFT;
        int UNSPECIFIED = 0 << MODE_SHIFT;
        int EXACTLY = 1 << MODE_SHIFT;
        int AT_MOST = 2 << MODE_SHIFT;
        L.printD("CT", "AT_MOST=" + AT_MOST);
        int specSizeH = getMeasuredHeight();
        L.printD("CT", "measuereHeight=" + specSizeH);
        int specSizeW = getMeasuredWidth();
        L.printD("CT", "measuredWidth=" + specSizeW);

        int mode = widSpec & MODE_MASK;
        L.printD("Ct", "mode==" + mode);
        int size = widSpec & ~MODE_MASK;
        L.printD("Ct", "size==" + size);


    }


}
