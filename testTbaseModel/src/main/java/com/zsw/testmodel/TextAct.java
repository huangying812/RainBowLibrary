package com.zsw.testmodel;

import android.content.Intent;
import android.graphics.Color;

import com.andexert.library.RippleView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhhusw on 2016/6/19.
 */
public class TextAct extends MyBaseActivity {


    @Bind(R.id.gosliding)RippleView gosliding;
    @Bind(R.id.goButtomNavigate) RippleView goButtomNavigate;

    @Override
    public void initLayout() {
        setStatusColor(R.color.testmodelblue);
        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.testmodelblue));
        getTitleBar().setCenterNormalTextView("引用colorfulCloundsLibrary").setTextColor(Color.WHITE);
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setOnRippleComplete(gosliding,goButtomNavigate);
    }


    @Override
    public void onRippleComplete(int id) {
        switch (id) {
            case R.id.gosliding:
                Intent intent = new Intent(TextAct.this, SlidingMenuAct.class);
                startActivity(intent);
                break;
            case R.id.goButtomNavigate:
                Intent intent2 = new Intent(TextAct.this, AbFragmentGroupAct.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }



}





