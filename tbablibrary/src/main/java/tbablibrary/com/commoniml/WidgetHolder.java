package tbablibrary.com.commoniml;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.R.attr.id;

/**
 * Create on 2016/12/1.
 * github  https://github.com/HarkBen
 * Description:
 * ------widget容器-----
 * author Ben
 * Last_Update - 2016/12/1
 */
public class WidgetHolder {
    private SparseArray<View> views;
    private View convertView;

    public WidgetHolder(Context context, ViewGroup parent, @LayoutRes int id){
        views = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(id,parent,false);
        convertView.setTag(this);
    }
    public WidgetHolder(View itemView){
        views = new SparseArray<>();
        convertView = itemView;
        convertView.setTag(this);
    }
    public static WidgetHolder getHolder(View convertView,View itemView){
        if(null  == convertView){
            return new WidgetHolder(itemView);
        }else{
            WidgetHolder holder = (WidgetHolder) convertView.getTag();
            return  holder;
        }
    }
    public static WidgetHolder getHolder(Context context,View convertView,ViewGroup parent,@LayoutRes int id){
        if(null  == convertView){
            return new WidgetHolder(context,parent,id);
        }else{
            WidgetHolder holder = (WidgetHolder) convertView.getTag();
            return  holder;
        }
    }

    /**
     * 查找View,并进行缓存
     * @param id
     * @param <T>
     * @return
     */
    public  <T extends View> T findView(@IdRes int id){
        View view = views.get(id);
        if(null  == view){
            view = convertView.findViewById(id);
            views.put(id,view);
        }
        return (T)view;
    }

    public View getConvertView(){
        return  convertView;
    }


}
