package com.zsw.note;

import android.view.View;
import android.widget.Button;

import com.zsw.note.nor_observer.NObserverAct;
import com.zsw.note.rx_observer.RxOberverAct;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Create on 2016/11/15.
 * github  https://github.com/HarkBen
 * Description:
 * -----主页------
 * author Ben
 * Last_Update - 2016/11/15
 */
public class MainAct extends BaseActivity {

    @Bind(R.id.java_Observer)
    Button javaObserver;
    @Bind(R.id.Rx_Observer)
    Button RxObserver;
    @Bind(R.id.eventBus_Observer)
    Button eventBusObserver;
    @Override
    public void setContentView() {
        setContentView(R.layout.act_main);
    }

    @Override
    public void initView() {
        getSupportActionBar().setTitle("主页");
    }


    @OnClick({R.id.java_Observer, R.id.Rx_Observer, R.id.eventBus_Observer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.java_Observer:
                goTo(NObserverAct.class);
                break;
            case R.id.Rx_Observer:
                goTo(RxOberverAct.class);
                break;
            case R.id.eventBus_Observer:

                break;
        }
    }



}
