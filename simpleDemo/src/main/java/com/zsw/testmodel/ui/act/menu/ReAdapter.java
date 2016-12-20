package com.zsw.testmodel.ui.act.menu;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsw.testmodel.R;

import java.util.List;

/**
 * Create on 2016/11/25.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/25
 */
public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ReViewHolder> {
    private Context context;
    private List<String> datas;
    public ReAdapter(Context con,List<String> l){
        this.context = con;
        this.datas = l;
    }

    @Override
    public ReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reitem,null);
            ReViewHolder holder = new ReViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(ReViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ReViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ReViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.reitem_tv);
        }
    }
}
