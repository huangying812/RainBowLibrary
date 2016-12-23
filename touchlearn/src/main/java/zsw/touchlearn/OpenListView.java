package zsw.touchlearn;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Create on 2016/12/23.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/23
 */
public class OpenListView extends ListView {
    public OpenListView(Context context) {
        super(context);
    }

    public OpenListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenListView(Context context, AttributeSet attrs, int defStleAttr) {
        super(context, attrs, defStleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //模仿源码，类似父布局给出测量尺寸允许的最大值。
        // 给出max上限为int值前30位，省去高两位的模式位。
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }
}
