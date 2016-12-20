package com.zsw.testmodel.ui.act.menu;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.ui.act.adapter.MyRecycleAdapter;
import com.zsw.testmodel.ui.act.customview.CustomView1Act;
import com.zsw.testmodel.ui.act.mvp.view.MVPSimpleAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.os.Build.VERSION_CODES.M;


/**
 * Created by zhhusw on 2016/6/19.
 */
public class MainAct extends AbActivity {
    @Bind(R.id.am_recycle)
    RecyclerView amRecycle;
    private List<Class> datas = new ArrayList<>();
    @Override
    public void initLayout() {
        getTitleBar().setCenterNormalTextView("MENU");
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initData();
        MyRecycleAdapter adapter = new MyRecycleAdapter(this,datas);
        amRecycle.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        amRecycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View convertView, Class clzz) {
                sendActIntent(clzz);
            }
        });
    }

    private void initData() {
        datas.add(SlidingMenuAct.class);
        datas.add(BottomNavigationAct.class);
        datas.add(SlidingMenuAct2.class);
        datas.add(UseRxJavaAct.class);
        datas.add(UseRecyclerViewAct.class);
        datas.add(HttpTestAct.class);
        datas.add(CustomView1Act.class);
        datas.add(StretchListViewAct.class);
        datas.add(MVPSimpleAct.class);
        datas.add(ActAbAdapter.class);

    }






    @Override
    public Class getRuningClass() {
        return getClass();
    }
}





