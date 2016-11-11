package com.zsw.testmodel.ui.act.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Create on 2016/11/6.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/6
 */
public class StretchListView extends ListView {
    private static final int STRECTCH_MAX_RANGE = 100;

    public StretchListView(Context context) {
        this(context,null);
    }

    public StretchListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StretchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, getStrectchRange(), isTouchEvent);
    }

    private int getStrectchRange(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) density*STRECTCH_MAX_RANGE;
    }


}
