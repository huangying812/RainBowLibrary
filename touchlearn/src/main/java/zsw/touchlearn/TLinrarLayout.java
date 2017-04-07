package zsw.touchlearn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Create on 2017/4/6.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/4/6
 */
public class TLinrarLayout extends LinearLayout {
    public TLinrarLayout(Context context) {
        super(context);
    }

    public TLinrarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TLinrarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.println("TLineraLayout -dispatchTouchEvent-  MotionEvent.ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                L.println("TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                L.println("TLineraLayout - dispatchTouchEvent- MotionEvent.ACTION_UP");
                break;
            default:

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                L.println("TLineraLayout -onTouchEvent  MotionEvent.ACTION_UP");
                break;
            default:

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                L.println("TLineraLayout -onInterceptTouchEvent  MotionEvent.ACTION_UP");
                break;
            default:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
