package tbablibrary.com.commoniml;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2016/12/1.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/1
 */
public abstract   class AbAdapter<T> extends BaseAdapter  {

    private Context mContext;
    private List<T> mList;
    private int mLayoutId;

    public AbAdapter(Context mcontext, int mLayoutId) {
        this(mcontext,mLayoutId,new ArrayList<T>());
    }

    public AbAdapter(Context mcontext, int mLayoutId, List<T> mList) {
        this.mContext = mcontext;
        this.mList = mList;
        this.mLayoutId = mLayoutId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 替换集合
     * @param list
     * @return mList size
     */
    public int reSetDatas(List<T> list) {
        if(null == list){
            new NullPointerException("collection == null");
        }
        this.mList  = list;
        notifyDataSetChanged();
        return mList.size();
    }

    /**
     * 追加集合
     * @param list
     * @return mList size
     */
    public int addDatas(List<T> list) {
        if(null == list){
            new NullPointerException("collection == null");
        }
        mList.addAll(list);
        notifyDataSetChanged();
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WidgetHolder widgetHolder = WidgetHolder.getHolder(mContext,convertView,parent,mLayoutId);
        bindData(widgetHolder,mList.get(position));
        return widgetHolder.getConvertView();
    }

    public abstract  void bindData(WidgetHolder holder,T model);

}
