package zsw.touchlearn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Create on 2016/12/23.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/23
 */
public class TButton extends Button {
    public static final String TAG = "TButton";

    public TButton(Context context) {
        super(context);
    }

    public TButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                L.println(TAG,"dispatchTouchEvent = ACTON_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.println(TAG,"dispatchTouchEvent = ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:

                L.println(TAG,"dispatchTouchEvent = ACTION_UP");
                break;
            default:break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                L.println(TAG,"onTouchEvent = ACTON_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.println(TAG,"onTouchEvent = ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                L.println(TAG,"onTouchEvent = ACTION_UP");
                break;
            default:break;
        }
        return super.onTouchEvent(event);
    }

}
