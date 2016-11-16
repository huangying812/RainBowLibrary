package com.zsw.note.ebus_observer;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsw.note.BaseActivity;
import com.zsw.note.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Create on 2016/11/16.
 * github  https://github.com/HarkBen
 * Description:
 * ----这里作为消息接收Act,我们将四种接收方式都用上-------
 * author Ben
 * Last_Update - 2016/11/16
 */
public class EventBusReceiverAct extends BaseActivity {

    @Bind(R.id.aebr_btn)
    Button aebrBtn;
    @Bind(R.id.aebr_showMsg1)
    TextView aebrShowMsg1;
    @Bind(R.id.aebr_showMsg2)
    TextView aebrShowMsg2;
    @Bind(R.id.aebr_showMsg3)
    TextView aebrShowMsg3;
    @Bind(R.id.aebr_showMsg4)
    TextView aebrShowMsg4;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_ebr);

        //进行注册
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void initView() {

    }
    //订阅四种不同的接收线程
    //注！ 订阅方法必须是共有的，不能省略。

    //1.与事件发布线程同一线程,也是默认的方式
    @Subscribe//(threadMode = ThreadMode.POSTING)
    public void receiverNormal(String msg) {
        aebrShowMsg1.setText("receiverNormal:"+msg+"\n"+"接收线程ID："+Thread.currentThread().getId());
    }

    //2.在UI主线程接收
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverOnMainThread(String msg) {
        aebrShowMsg2.setText("receiverOnMainThread:"+msg+"\n"+"接收线程ID："+Thread.currentThread().getId());
    }

    //下面3 4 的都是在子线程中，更新UI日志会报 CalledFromWrongThreadException 的异常，但没有ANR
    //差点让我怀疑人生！翻很久没翻到，等待正解

    //3.在子线程中执行,(如果发布线程本来就为子线程，那将在原子线程接收)
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public  void receiverOnBackground(String msg) {

        aebrShowMsg3.setText("receiverOnBackground:"+msg+"\n"+"接收线程ID："+Thread.currentThread().getId());

    }

    //4.在子线程程接收
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public  void receiverOnAsync(String msg) {

        aebrShowMsg4.setText("receiverOnAsync:"+msg+"\n"+"接收线程ID："+Thread.currentThread().getId());
    }

    /**
     *
     */

    @OnClick(R.id.aebr_btn)
    public void onClick() {
        goTo(EventBusSenderAct.class);
    }

}
