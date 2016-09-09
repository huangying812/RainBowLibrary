package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.graphics.Color;

import com.andexert.library.RippleView;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhhusw on 2016/6/19.
 */
public class MainAct extends AbActivity {


    @Bind(R.id.gosliding)RippleView gosliding;
    @Bind(R.id.goButtomNavigate) RippleView goButtomNavigate;
    @Bind(R.id.goSlidingMenuAct) RippleView goSlidingMenuAct;
    @Bind(R.id.goObserver) RippleView goObserver;

    @Override
    public void initLayout() {
        getTitleBar().setCenterNormalTextView("Jungle Man").setTextColor(Color.WHITE);
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setOnRippleComplete(gosliding,goButtomNavigate,goSlidingMenuAct,goObserver);
    }


    @Override
    public void onRippleComplete(int id) {
        switch (id) {
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

            default:
                break;
        }
    }



}





