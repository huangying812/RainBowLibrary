package com.zsw.testmodel.ui.act.menu;

import android.app.Activity;
import android.database.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.entity.Phone;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.*;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * author  z.sw
 * time  2016/9/7.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class UseRxJavaAct extends AbActivity {
    private static final String TAG = "UseRxJavaAct";
    private String msg = "";
    private List<Phone> phones;

    @Bind(R.id.excute)
    Button excute;
    @Bind(R.id.display)
    TextView display;
    @Bind(R.id.ux_image)
    ImageView uxImage;

    @Override
    public void initLayout() {
        getTitleBar().setCenterNormalTextView("RxJava&RxAndroid");

        loadContentView(R.layout.act_userxjava);
        ButterKnife.bind(this);
        phones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Phone p = new Phone();
            p.setModels("型号诺基亚" + i);
            p.setName("诺基亚");
            p.setPrice(3500);
            List<Integer> colors = new ArrayList<>();
            colors.add(1);
            colors.add(2);

            p.setColors(colors);
            phones.add(p);
        }
    }

    void showMsg(String s) {
        msg += s + "\n";
        display.setText(msg);
    }


    @OnClick(R.id.excute)
    public void onClick() {
        msg = "";
        //同步观察者
        createObserver1();
        //异步观察者
        createObserver2();
        //  线程控制范例
        createObserver3();
        //使用单次  一对一 转换
        createObserver4();
        //使用多次 一对多转换
        createObserver5();
        //多次 一对多转换
        createObserver6();
    }

    /**
     * Subscriber订阅者抽象类（Subscriber<T> implements Observer<T>, Subscription） 干的勾当和Observer 一样
     * 增加3个方法
     * 1.onStart() 在事件开始前调用 类似于 prepare-  但是onStart 是subscriber来调用的，所以onStart和subscriber是在同一线程。
     * 2.unSubscriber()取消订阅 --这里除了取消订阅，还会关闭订阅线程，且取消后不会调用 onCompleted（）方法
     * 3.isUnsubscribed 判断是否取消了订阅
     */
    public void createObserver1() {
        display.setText("构建观察者，注册监听");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                L.printD(TAG, "onStart");

            }


            @Override
            public void onCompleted() {
                L.printD(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                L.printD(TAG, "onError=" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                L.printD(TAG, s);
            }
        };


        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (i == 6) {
                        subscriber.unsubscribe();
                    } else {
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
        //给observable订阅，这里我们没有指定 订阅线程和观察者线程所以默认他们都运行在UI主线程，同步阻塞在耗时较长时会ANR
        observable.subscribe(subscriber);

        //快速订阅的两种方法-呃还没发小有啥鸟用 this is useless
//        Observable observable1 = Observable.just("AAA","BBB","CCC");
//        String[] strings = {"S1","S2","S3"};
//        Observable observable2 = Observable.from(strings);


    }

    /**
     * 使用调度器，实现异步观察者（即：事件产生的线程，（被观察者）额就是干活的线程，事件消费线程，（观察者）收钱的线程）
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler,额就是所以操作都原路径返回。
     * Schedulers.newThread(): 总是启用新线程，就是new一个子线程。
     * Schedulers.io()://是一个线程池（老司机博客说无数量上限的线程池，可以重用空闲的线程，看源码暂时没理解，没注释呀 -- ~~~。），
     * -io()本质上和newThread()一模一样，都来自NewThreadWorker类，
     * Schedulers.computation():（老司机抛物线-） 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
     * -这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * Android 特别的线程为 AndroidSchedulers.mainThread()-指定运行在UI主线程
     */
    void createObserver2() {
            Observable.just("t1", "t2", "t3")
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
     * 使用 Scheduler 调度器
     * 警察抓小偷的故事
     */
    void createObserver3() {
        /**
         * 观察者-警察
         * 创建一个观察者-Subscriber
         */
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                showMsg("onStart 警察发现 小偷--- -要开始动手了");
            }

            /**
             * 结束任务
             */
            @Override
            public void onCompleted() {
                showMsg("onCompleted 警察发现 小偷--- 跑了");
            }

            @Override
            public void onError(Throwable e) {
                showMsg("onError 出了技术问题");
            }

            /**
             * 执行任务
             * @param s
             */
            @Override
            public void onNext(String s) {
                L.printD(TAG, s);
                showMsg("警察发现 小偷---" + s);

            }
        };

        /**
         * 被观察者-小偷儿
         * 创建一个被观察者(Observable)，并订阅观察者(Observer)（小偷作案前要告诉警察我要作案了，记得抓我---快枪手Observable）
         *
         */
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            /**
             * 在这里偷东西，并告诉警察每个细节
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super String> subscriber) {

                for (int i = 1; i <= 10; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext("在偷第" + i + "块鸡腿");
                }

                /**
                 * 偷够了我走了，
                 */
                subscriber.onCompleted();
            }


        });
        observable.observeOn(AndroidSchedulers.mainThread())//警察在明处
                .subscribeOn(Schedulers.newThread())//小偷在暗处 还有其他 方式 大招 io()线程,
                .subscribe(subscriber);//游戏开始！

    }


    /**
     * 使用 转换，将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列
     * 简单转换 map()，一对一转换，转换后返回 --具体对象
     */
    void createObserver4() {

        Observable.just(R.mipmap.back_f)
                .map(new Func1<Integer, Bitmap>() {//使用变换，更改回掉

                    @Override
                    public Bitmap call(Integer s) {
                        L.printD(TAG, "bitmap path =" + s);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), s.intValue());
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(new Action1<Bitmap>() {//观察者直接拿到 指定返回值
                    @Override
                    public void call(Bitmap bitmap) {
                        uxImage.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * map() 一对一转换 实用
     * 给观察者直接 传出 手机 型号
     */
    void createObserver5() {
        Observable.from(phones)
                .map(new Func1<Phone, String>() {
                    @Override
                    public String call(Phone phone) {
                        return phone.getModels();
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                L.printD(TAG, "手机型号==" + s);
            }
        });

    }

    /**
     * flatMap 一对多转换，
     * 一对多 结构 -- <A,Observer<T>>
     * 第二个泛型的时候，转换返回的是 Oberver<T> 可以是任意对象 就此实现一对多转换
     */
    void createObserver6() {

        Observable.from(phones)
                .flatMap(new Func1<Phone, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Phone phone) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return Observable.from(phone.getColors());//逐个返回Colors 中的 Integer对象

                      /*OK这里的转换就是通过在创建一个Observable 来逐个转换出来Integer,同样的这里可以继续使用调度器
                      切换线程，默认当然依然是阻塞的，所以这里单次返回结果是 Colors中所有的对象。
                      总结：flatMap 转换 就是将多个Map装进Observable 统一返回给Observer,
                      所以如果你想，这里可以无限拆分数据结构，直到具体到对象
                        */
//                        Most of the modern Android applications just use View-Model architecture，everything is connected with Activity.
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        L.printD(TAG, "颜色值==" + integer);
                        showMsg("颜色值==" + integer);

                    }
                });





    }


    @Override
    public Class getRuningClass() {
        return getClass();
    }
}
