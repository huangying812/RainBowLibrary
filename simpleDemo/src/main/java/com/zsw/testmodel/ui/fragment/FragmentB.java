package com.zsw.testmodel.ui.fragment;

import com.zsw.testmodel.R;

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

}
