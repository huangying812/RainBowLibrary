package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andexert.library.RippleView;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhhusw on 2016/6/19.
 */
public class MainAct extends AbActivity {
    @Bind(R.id.gosliding)
    Button gosliding;
    @Bind(R.id.goButtomNavigate)
    Button goButtomNavigate;
    @Bind(R.id.goSlidingMenuAct)
    Button goSlidingMenuAct;
    @Bind(R.id.goObserver)
    Button goObserver;
    @Bind(R.id.goRecyclerView)
    Button goRecyclerView;

    @Override
    public void initLayout() {
        removeStatusBar();
        removeTBaseTitleBar();
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.goRecyclerView, R.id.gosliding,
            R.id.goButtomNavigate, R.id.goSlidingMenuAct, R.id.goObserver})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gosliding:
                Intent intent = new Intent(MainAct.this, SlidingMenuAct.class);
                startActivity(intent);
                break;
            case R.id.goButtomNavigate:
                Intent intent2 = new Intent(MainAct.this, BottomNavigationAct.class);
                startActivity(intent2);
                break;
            case R.id.goSlidingMenuAct:
                Intent intent3 = new Intent(MainAct.this, SlidingMenuAct2.class);
                startActivity(intent3);
                break;

            case R.id.goObserver:
                Intent intent4 = new Intent(MainAct.this, UseRxJavaAct.class);
                startActivity(intent4);
                break;
            case R.id.goRecyclerView:
                Intent intent5 = new Intent(MainAct.this, UseRecyclerViewAct.class);
                startActivity(intent5);
                break;

            default:
                break;
        }
    }


}





