package com.zsw.colorfulcloudslibrary.base.customview;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

/**
 * Created by zhusw1 on 2016/6/26.
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
