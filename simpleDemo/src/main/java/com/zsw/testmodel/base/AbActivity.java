package com.zsw.testmodel.base;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.andexert.library.RippleView;

import com.zsw.rainbowlibrary.uibase.baseactivity.TBaseActivity;
import com.zsw.testmodel.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class AbActivity extends TBaseActivity implements RippleView.OnRippleCompleteListener {
    @Override
    public Class getRuningClass() {
        return null;
    }

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

    }

    public void sendActIntent(Class<? extends TBaseActivity> clzz){
        Intent intent = new Intent(this,clzz);
        startActivity(intent);
    }

    @Override
    public void onLayoutLoading() {
        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.testmodelblue));
        setStatusColor(R.color.testmodelblue);
        getTitleBar().setLeftNormalButton(new TbaseTitleBar.OnTbaseTitleLeftViewClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setBackgroundResource(R.mipmap.back_f);
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
