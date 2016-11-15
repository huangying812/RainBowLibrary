package com.zsw.testmodel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsw.testmodel.R;
import com.zsw.testmodel.ui.event.FirstEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentB extends FragmentA {
    @Override
    public Class getRuningClass() {
        return getClass();
    }

    @Override
    public int getBgColor() {
        return getTBaseActivity().getResources().getColor(R.color.testModelcpink);
    }
    @Override
    public String getText() {
        return "Do - FragmentB";
    }

    @Override
    public boolean showProgress() {
        return true;
    }

    @Override
    public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onInitLayout(inflater, container, savedInstanceState);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送消息
                EventBus.getDefault().post(new FirstEvent("FragmentB 发来了一条消息"));
            }
        });
    }
}
