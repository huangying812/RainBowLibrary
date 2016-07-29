package com.zsw.testmodel.fragment;

import com.zsw.testmodel.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentC extends FragmentA {

    @Override
    public int getBgColor() {
        return getTBaseFGActivity().getResources().getColor(R.color.testmodelf);
    }
    @Override
    public String getText() {
        return "Rest -FragmentC";
    }
}
