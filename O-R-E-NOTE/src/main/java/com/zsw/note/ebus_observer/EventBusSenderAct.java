package com.zsw.note.ebus_observer;

import android.widget.Button;

import com.zsw.note.BaseActivity;
import com.zsw.note.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Create on 2016/11/16.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/16
 */
public class EventBusSenderAct extends BaseActivity {
    @Bind(R.id.aesender_btn)
    Button aesenderBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_esender);
    }

    @Override
    public void initView() {

    }


    @OnClick(R.id.aesender_btn)
    public void onClick() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                EventBus.getDefault().post(new String("EBS-MSG:SenderThreadID：" + Thread.currentThread().getId()));
            }
        };
        thread.start();


    }
}
