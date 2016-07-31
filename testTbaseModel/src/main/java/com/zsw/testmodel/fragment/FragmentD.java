package com.zsw.testmodel.fragment;

import com.zsw.testmodel.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class FragmentD extends  FragmentA {

    @Override
    public int getBgColor() {
        return getTBaseActivity().getResources().getColor(R.color.testModelgreen);
    }

    @Override
    public String getText() {
        return "DoAgain -FragmentD";
    }

}
