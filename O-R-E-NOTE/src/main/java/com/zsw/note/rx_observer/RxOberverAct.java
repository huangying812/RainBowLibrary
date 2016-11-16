package com.zsw.note.rx_observer;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TextView;

import com.zsw.note.BaseActivity;
import com.zsw.note.R;

import java.util.concurrent.Future;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Create on 2016/11/15.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/15
 */
public class RxOberverAct extends BaseActivity {


    @Bind(R.id.startTask)
    Button startTask;
    @Bind(R.id.content)
    TextView content;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_rxobserver);

    }
    @Override
    public void initView() {
        getSupportActionBar().setTitle("RxObserver");
        startTask.setText("开始任务");
    }



    @OnClick(R.id.startTask)
    public void onClick() {

        startTask();
    }


    Bitmap getBitmap(String path){
        return null;
    }
    //模拟一次传入图片路径，然后读取出bitmap显示到UI上
    //这里用到了 map  一对一转换  也会是最常用的 异步处理事件。替换AsyncTask
    //这里被观察者 Observable 在主动订阅观察者 subscriber

    void startTask(){
        final String path  =" xx/xx.png";
     Observable observable =  Observable.just(path)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {

                        return getBitmap(s);
                    }
                });
        observable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {

                    }
                });

    }



}
