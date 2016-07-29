package com.zsw.testmodel.fragment;

import com.zsw.testmodel.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentB extends FragmentA {


    @Override
    public int getBgColor() {
        return getTBaseFGActivity().getResources().getColor(R.color.testModelcpink);
    }
    @Override
    public String getText() {
        return "Do - FragmentB";
    }


}
