package com.zsw.testmodel.ui.act;

import android.widget.Button;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.LOG;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.*;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * author  z.sw
 * time  2016/9/7.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class UseRxJavaAct extends AbActivity {
    private static final String TAG = "UseRxJavaAct";


    @Bind(R.id.excute)
    Button excute;
    @Bind(R.id.display)
    TextView display;

    @Override
    public void initLayout() {
        getTitleBar().setCenterNormalTextView("RxJava&RxAndroid");

        loadContentView(R.layout.act_userxjava);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.excute)
    public void onClick() {

        //同步观察者
        createObserver1();

        //异步观察者
//        createObserver2();

    }

    /**
     * 使用调度器，实现异步观察者（即：事件产生的线程，（被观察者）额就是干活的线程，事件消费线程，（观察者）收钱的线程）
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler,额就是所以操作都原路径返回。
     * Schedulers.newThread(): 总是启用新线程，就是new一个子线程。
     * Schedulers.io()://是一个线程池（老司机博客说无数量上限的线程池，可以重用空闲的线程，看源码暂时没理解，没注释呀 -- ~~~。），
     * -io()本质上和newThread()一模一样，都来自NewThreadWorker类，
     * Schedulers.computation():（抛物线-） 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
     * -这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * Android 特别的线程为 AndroidSchedulers.mainThread()-指定运行在UI主线程
     */
    void createObserver2() {
        Observable.just("t1","t2","t3")
                .subscribeOn(Schedulers.io())//subscribe() 执行订阅 为io线程（处理事件的线程）
                .observeOn(AndroidSchedulers.mainThread())// schedulers 指定回调线程为ui主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        display.setText("onStart");
                    }

                    @Override
                    public void onCompleted() {
                        display.setText("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        display.setText(s);
                    }
                });


    }

    /**
     *Subscriber订阅者抽象类（Subscriber<T> implements Observer<T>, Subscription） 干的勾当和Observer 一样
     *增加3个方法
     * 1.onStart() 在事件开始前调用 类似于 prepare-  但是onStart 是subscriber来调用的，所以onStart和subscriber是在同一线程。
     * 2.unSubscriber()取消订阅 --这里除了取消订阅，还会关闭订阅线程，且取消后不会调用 onCompleted（）方法
     * 3.isUnsubscribed 判断是否取消了订阅
     */
    public void createObserver1() {


        display.setText("构建观察者，注册监听");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                LOG.printD(TAG, "onStart");

            }


            @Override
            public void onCompleted() {
                LOG.printD(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LOG.printD(TAG, "onError=" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                LOG.printD(TAG, s);
            }
        };


        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if(i == 6){
                        subscriber.unsubscribe();
                    }else{
                        subscriber.onNext("被观察者-onNext" + i);
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });
        //给observable订阅
        observable.subscribe(subscriber);

        //快速订阅的两种方法-呃还没发小有啥鸟用 this is useless
//        Observable observable1 = Observable.just("AAA","BBB","CCC");
//        String[] strings = {"S1","S2","S3"};
//        Observable observable2 = Observable.from(strings);


    }


}
