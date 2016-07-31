package com.zsw.testmodel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zsw.testmodel.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentC extends FragmentA {

    @Override
    public int getBgColor() {
        return getTBaseActivity().getResources().getColor(R.color.testmodelf);
    }
    @Override
    public String getText() {
        return "Rest -FragmentC";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTBaseActivity().startLoadAnim();
    }
}
