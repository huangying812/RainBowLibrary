package zsw.touchlearn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Create on 2016/12/23.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/23
 */
public class RootLayout extends LinearLayout {
    public RootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RootLayout(Context context) {
        super(context);
    }

    public RootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptHoverEvent(MotionEvent event) {
//        boolean states  = super.onInterceptHoverEvent(event);
//        L.println("RootLayout-onInterceptHoverEvent="+states);
//        return states;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean states  = super.dispatchTouchEvent(ev);
//        L.println("RootLayout-dispatchTouchEvent="+states);
//        return states;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean states  = super.onTouchEvent(event);
//        L.println("RootLayout-onTouchEvent="+states);
//        return states;
//    }
}
