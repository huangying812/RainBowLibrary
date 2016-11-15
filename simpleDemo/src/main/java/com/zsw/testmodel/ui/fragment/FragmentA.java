package com.zsw.testmodel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbFragment;
import com.zsw.testmodel.ui.event.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentA extends AbFragment {

    @Bind(R.id.content)
    TextView content;
    private View view;

    @Override
    public Class getRuningClass() {
        return getClass();
    }

    @Override
    public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       //注册为观察者
        EventBus.getDefault().register(this);

        view = inflater.inflate(R.layout.fragment_a, container, false);
        setContentLayout(view);
        ButterKnife.bind(this,view);
        view.setBackgroundColor(getBgColor());
        content.setText(getText());


    }


    public boolean showProgress(){
        return false;
    }
    public @ColorInt int getBgColor() {
        return getTBaseActivity().getResources().getColor(R.color.testmodelblue);
    }

    public String getText() {
        return "Plan- FragmentA";
    }

    //订阅，接收通知
    @Subscribe
    public void onEventMainThread(FirstEvent event){
//        content.setText(event.getMsg());
        Snackbar.make(content,event.getMsg(),Snackbar.LENGTH_LONG).show();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(FirstEvent event){
        content.setText("onMessage"+event.getMsg());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
