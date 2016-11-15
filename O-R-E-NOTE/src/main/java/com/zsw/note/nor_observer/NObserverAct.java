package com.zsw.note.nor_observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.zsw.note.BaseActivity;
import com.zsw.note.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Create on 2016/11/15.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/15
 */
public class NObserverAct extends BaseActivity {
    private static final String TAG = "NObserverAct";

    @Bind(R.id.sendMsg)
    Button sendMsg;
    @Bind(R.id.content)
    TextView content;
    Watched watched;

    @Override
    public void initView() {
        getSupportActionBar().setTitle("java-观察者模式");
        sendMsg.setText("出发");
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.act_norobserver);

        //声明被观察者
        watched = new ArmorCashCarrier();

        //所有观察者都订阅 运钞车
        Watcher police = new PoliceWatcher();
        watched.addWatcher(police);

        Watcher bandit = new BanditWatcher();
        watched.addWatcher(bandit);

        Watcher bank = new BankWatcher();
        watched.addWatcher(bank);


    }



    @OnClick(R.id.sendMsg)
    public void onClick() {
        //运钞车出动 并被观察者们知晓
        watched.notifyAllWatcher("运钞车出发");
        //日志结果
        /*
        D/NObserverAct: 警察收到情报：运钞车出发
        D/NObserverAct: 罪犯获得情报：运钞车出发
        D/NObserverAct: 银行得到消息：运钞车出发
            */
    }

    //下面我们来具体实现具体的  被观察者，观察者 并进行订阅

    /**
     * 被观察者运钞车
     */
    class ArmorCashCarrier implements Watched {

        private List<Watcher> watchers = new ArrayList<>();

        @Override
        public void addWatcher(Watcher watcher) {
            watchers.add(watcher);
        }

        @Override
        public void removeWatcher(Watcher watcher) {
            watchers.remove(watcher);
        }

        @Override
        public void notifyAllWatcher(String msg) {
            for (Watcher w : watchers) {
                w.notify(msg);
            }
        }


    }


    /**
     * 具体观察者1  - 警察
     */
    class PoliceWatcher implements Watcher {

        @Override
        public void notify(String msg) {
            Log.d(TAG, "警察收到情报：" + msg);
        }
    }

    /**
     * 具体观察者 - 抢劫团伙
     */
    class BanditWatcher implements Watcher {

        @Override
        public void notify(String msg) {
            Log.d(TAG, "罪犯获得情报：" + msg);
        }
    }


    /**
     * 具体观察者 - 银行
     */
    class BankWatcher implements Watcher {

        @Override
        public void notify(String msg) {
            Log.d(TAG, "银行得到消息：" + msg);
        }
    }
}
