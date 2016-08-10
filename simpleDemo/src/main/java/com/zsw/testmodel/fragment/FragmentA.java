package com.zsw.testmodel.fragment;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbFragment;

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
    public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a, container, false);
        setContentLayout(view);
        ButterKnife.bind(this,view);
        view.setBackgroundColor(getBgColor());
        content.setText(getText());

        if(showProgress()){
            startLoadAnimInAct(getResources().getDrawable(R.drawable.tbase_animlist_loading));
        }
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




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
