package rg.funinglayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2017/6/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/6/27
 */
public class MenuActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<Class> datas;
    private MyRecycleAdapter adapter;

    private void initDatas() {
        datas = new ArrayList<>();
        adapter = new MyRecycleAdapter(datas, this);
        datas.add(CircleProgressActivity.class);

    }

    @Override
    public void onCompoentBinded() {
        setContentView(R.layout.act_menu);
        recyclerView = (RecyclerView) findViewById(R.id.am_recycleView);
        initDatas();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Class clzz, View view) {
                goTo(clzz);
            }
        });
    }


    class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Class> datas;
        private Context context;
        @Nullable
        private OnItemClickListener onItemClickListener = null;
        public MyRecycleAdapter(List<Class> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.content.setText("funing-"+datas.get(position).getName());
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener)
                        onItemClickListener.onItemClick(datas.get(position), v);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Class clzz, View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content;
        public MyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.im_centent);
        }
    }

}
