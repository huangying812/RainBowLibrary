package com.zsw.testmodel.base;

import android.view.LayoutInflater;
import android.view.View;

import com.andexert.library.RippleView;
import com.zsw.rainbowlibrary.uibase.baseactivity.TBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class AbActivity extends TBaseActivity implements RippleView.OnRippleCompleteListener {


    public void setOnRippleComplete(RippleView...rippleView){
        for(RippleView rippleV:rippleView){
            rippleV.setOnRippleCompleteListener(this);
        }
    }
    /**
     * 当view的水波纹动画播放完毕后
     * @param rippleView
     */
    @Override
    public void onComplete(RippleView rippleView) {
        onRippleComplete(rippleView.getId());
    }

    public void onRippleComplete(int id){

    };

    @Override
    public void onLayoutLoading() {

        initLayout();

    }

    public abstract  void initLayout();


    public void setStatusColor(int resid){
        super.reSetStatusColor(getResources().getColor(resid));
    }

    public void loadContentView(int layoutId){
        loadContentView(inflater(layoutId));
    }

    public void loadContentView(View view){
        super.setContentLayout(view);
    }

    public View inflater(int layoutId){
        return LayoutInflater.from(this).inflate(layoutId,null);
    }

}
