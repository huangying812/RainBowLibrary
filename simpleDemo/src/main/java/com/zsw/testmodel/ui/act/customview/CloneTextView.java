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

                  //目前我还没看到这个模式有什么实际卵用，希望有实例的同学能能够@我
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
      模式三 完全开放    UNSPECIFIED   大概意思就是没有限制，你想多大就多大 金箍棒模式

    实际场景还未验证，下面我们就来用代码验证
    从xml的定义到获取实际的SpecSize 到计算出测量模式和实际的Size
    我们将源码中 mode的计算 和 size的计算拷贝出来，然后使用一样的运算符来计算 最后对比一下就知道了
    方法是笨了点 但是理解吃透才是最重要的
    日志代码
        private int widSpec;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widSpec = widthMeasureSpec;
        L.printD("CT", "onMeasure");
    }
    //这里很简单了，就是绘制，大小已在onMeasure确定， 位置已经在onLayout 固定
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
        L.printD("CT", "EXACTLY=" + EXACTLY);
        int mode = widSpec & MODE_MASK;
        L.printD("Ct", "mode==" + mode);
        int size = widSpec & ~MODE_MASK;
        L.printD("Ct", "size==" + size);

    }

    用上面的代码一共验证三个场景，
    match_parent
    log-
    AT_MOST=-2147483648
    EXACTLY=1073741824
    mode==1073741824
    size==720
    模式为 EXACTLY 父类指定大小

    wrap_content
    log-
    AT_MOST=-2147483648AT_MOST
    EXACTLY=1073741824
    mode==-2147483648
    size==720
     模式为 AT_MOST 这里会有疑问咯，specSize还是720 那为什么显示的大小仅仅是包裹文本内容呢
     细心的同学会发现上面的代码已经给出了答案
     在TextView的onMeasure方法中，当模式为AT_MOST时他是这样处理的
      // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, width);
            }
            这里的widthSize 是父类传下来的值，再计算出文本包裹需要的大小，两者取最小
            一般都是文本包裹值下，所以显示出来是刚好包裹文本。

    200dip
    log-
    AT_MOST=-2147483648
    EXACTLY=1073741824
    mode==1073741824
    size==400
    模式为 EXACTLY 大小

    根据实际验证我们可以得到如下结论
        xml-attribute value = match_parent → MODE = EXCATLY 子类期望与父类一样大小，父类传入允许子类使用的最大值，并且默认使用这个值
        xml-attribute value = wrap_content → MODE = AT_MOST  父类传入允许的最大值，子类需要为自己重新计算大小并且参考父类给出的上限
        xml-attribute value = 200dp →        MODE = EXCATLY 父类测量出子类的参数，再传给子类直接使用
        当然了这三种模式我们都可以再onMeasure方法中取动态更改绘制模式和大小
    */
    private int widSpec;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widSpec = widthMeasureSpec;
        L.printD("CT", "onMeasure");

    }
    //这里很简单了，就是绘制，大小已在onMeasure确定， 位置已经在onLayout 固定
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
        L.printD("CT", "EXACTLY=" + EXACTLY);

        int mode = widSpec & MODE_MASK;
        L.printD("Ct", "mode==" + mode);
        int size = widSpec & ~MODE_MASK;
        L.printD("Ct", "size==" + size);


    }


}
