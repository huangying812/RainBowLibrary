package com.zsw.testmodel.ui.act.menu;

import android.view.View;
import android.widget.Button;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.ui.act.customview.CustomView1Act;
import com.zsw.testmodel.ui.act.mvp.view.MVPSimpleAct;

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
    @Bind(R.id.goHttpRequest)
    Button  goHttpRequest;
    @Bind(R.id.act_mainStretchListView)
    Button  mainStretchListView;

    @Bind(R.id.act_mainCustomView)
    Button act_mainCustomView;

    @Bind(R.id.act_LearnMVP)
    Button actLearnMVP;

    @Override
    public void initLayout() {
        removeStatusBar();
        removeTBaseTitleBar();
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.act_mainCustomView,R.id.act_mainStretchListView,R.id.goRecyclerView, R.id.gosliding,
            R.id.goButtomNavigate, R.id.act_LearnMVP,R.id.goSlidingMenuAct, R.id.goObserver,R.id.goHttpRequest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gosliding:
                sendActIntent(SlidingMenuAct.class);
                break;
            case R.id.goButtomNavigate:
                sendActIntent(BottomNavigationAct.class);
                break;
            case R.id.goSlidingMenuAct:
                sendActIntent(SlidingMenuAct2.class);
                break;

            case R.id.goObserver:
                sendActIntent(UseRxJavaAct.class);
                break;
            case R.id.goRecyclerView:
                sendActIntent(UseRecyclerViewAct.class);
                break;
            case R.id.goHttpRequest:
                sendActIntent(HttpTestAct.class);
                break;
            case R.id.act_mainCustomView:
                sendActIntent(CustomView1Act.class);
                break;
            case R.id.act_mainStretchListView:
                sendActIntent(StretchListViewAct.class);
                break;
            case  R.id.act_LearnMVP:
                    sendActIntent(MVPSimpleAct.class);
                break;

            default:
                break;
        }
    }


    @Override
    public Class getRuningClass() {
        return getClass();
    }
}





