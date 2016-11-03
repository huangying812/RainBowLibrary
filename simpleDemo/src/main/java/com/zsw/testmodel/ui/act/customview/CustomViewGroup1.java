package com.zsw.testmodel.ui.act.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zsw.rainbowlibrary.utils.L;

/**
 * Create on 2016/11/1.
 * github  https://github.com/HarkBen
 * Description:
 * ----用于测试getWidth 和getMeasureWidth的 布局容器类-------
 * author Ben
 * Last_Update - 2016/11/1
 */
public class CustomViewGroup1 extends ViewGroup {
    private static final String  TAG = "CustomViewGroup1";

    public CustomViewGroup1(Context context) {
        this(context,null);
    }

    public CustomViewGroup1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //我们这里式容器类了，作为父类要对子类进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        //我们这里什么都不做，就强行设置子View的宽高为 300，300
        for(int i = 0;i<count;i++){
            View childView = getChildAt(i);
            L.printD(TAG,"childView - me width=="+childView.getMeasuredWidth());
            L.printD(TAG,"cheildView - me Height = "+childView.getMeasuredHeight());
            childView.layout(0,0,childView.getMeasuredWidth(),childView.getMeasuredHeight());
        }
    }
}
