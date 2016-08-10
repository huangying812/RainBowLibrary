package com.zsw.rainbowlibrary.customview;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

/**
 * CustomDrawLayout -覆盖onMeasure 重新计算高度，否则不能作为childlayout嵌套使用
 * author @zhusw
 */
public class CustomDrawLayout extends DrawerLayout {

    public CustomDrawLayout(Context context){
        super(context);
    }

    public CustomDrawLayout(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public CustomDrawLayout(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.EXACTLY);
        heightMeasureSpec  = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
