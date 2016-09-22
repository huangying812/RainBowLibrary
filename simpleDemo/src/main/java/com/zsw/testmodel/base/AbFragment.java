package com.zsw.testmodel.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsw.rainbowlibrary.uibase.baseactivity.TBaseActivity;
import com.zsw.rainbowlibrary.uibase.basefragment.TBaseFragment;

/**
 * Created by z.sw on 2016/7/5.
 */
public abstract class AbFragment extends TBaseFragment {

    /**
     * 避免库中TbaseFragment 直接持有子类的activity 在getResoures 查找资源失败
     */
    private TBaseActivity tBaseActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);//父类检查activity 是否继承子TBaseActivity
        //一定要在super 之后调用
        this.tBaseActivity = (TBaseActivity) activity;
    }


    @Override
    public TBaseActivity getTBaseActivity(){
        return tBaseActivity;
    }

    @Override
    public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }


}
