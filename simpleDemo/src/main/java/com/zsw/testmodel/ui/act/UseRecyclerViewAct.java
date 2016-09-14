package com.zsw.testmodel.ui.act;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author  z.sw
 * time  2016/9/13.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class UseRecyclerViewAct extends AbActivity {

    @Bind(R.id.urv_recyclerView)
    RecyclerView urvRecyclerView;

    private List<Message> messages;
    private MyAdapter myAdapter;
    ;

    @Override
    public void initLayout() {
        loadContentView(R.layout.act_urv);
        ButterKnife.bind(this);
        //搞点烂数据
        messages = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Message msg = new Message();
            msg.arg1 = i;
            msg.arg2 = R.mipmap.bg_login;
            messages.add(msg);
        }

        //设置方向
        urvRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        urvRecyclerView.setAdapter(myAdapter = new MyAdapter());//多么妖娆的写法


    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ZViewHolder> {


        //手动 生成一个 viewHolder
        @Override
        public ZViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ZViewHolder holder = new ZViewHolder(LayoutInflater
                    .from(UseRecyclerViewAct.this).inflate(R.layout.item_urv_rec, parent, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(ZViewHolder holder, int position) {
            holder.content.setText("Message" + messages.get(position).arg1);
            holder.imageView.setBackgroundResource(messages.get(position).arg2);


        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        /**
         * 实现 ViewHolder
         */
        class ZViewHolder extends RecyclerView.ViewHolder {
            //初始化
            TextView content;
            ImageView imageView;

            public ZViewHolder(View itemView) {
                super(itemView);

                bindView(itemView);

            }

            void bindView(View itemView) {
                content = (TextView) itemView.findViewById(R.id.urv_rec_text);
                imageView = (ImageView) itemView.findViewById(R.id.urv_rec_iamge);
            }


        }

    }

}
