package com.zsw.testmodel.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.zsw.colorfulcloudslibrary.base.baseactivity.TBaseFragmentGroupActivity;
import com.zsw.colorfulcloudslibrary.base.baseexception.NotFindFGActivityException;
import com.zsw.colorfulcloudslibrary.base.basefragment.TBaseFragment;
import com.zsw.testmodel.AbFragmentGroupAct;
import com.zsw.testmodel.R;

/**
 * Created by z.sw on 2016/7/5.
 */
public class AbFragment extends TBaseFragment {

    /**
     * 避免库中TbaseFragment 直接持有子类的activity 在getResoures 查找资源失败
     */
    private AbFragmentGroupAct abGroupActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);//父类检查activity 是否继承子TBaseFragmentGroupActivity
        //一定要在super 之后调用
        this.abGroupActivity = (AbFragmentGroupAct) activity;
    }


    @Override
    public TBaseFragmentGroupActivity getTBaseFGActivity() {
        return abGroupActivity;
    }

    @Override
    public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }


}
