package com.zsw.testmodel.ui.act.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.ui.act.customview.StretchListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create on 2016/11/6.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/6
 */
public class StretchListViewAct extends AbActivity {

    @Bind(R.id.sl_listView)
    StretchListView slListView;

    @Override
    public void initLayout() {

        loadContentView(R.layout.act_stretchlistview);
        ButterKnife.bind(this);
        slListView.setAdapter(new MAdapter(this));
    }



    class  MAdapter extends BaseAdapter{
    private Context mContext;
        public MAdapter(Context con){
            this.mContext = con;
        }

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText("我的马儿不吃草");

            return textView;
        }
    }
}
