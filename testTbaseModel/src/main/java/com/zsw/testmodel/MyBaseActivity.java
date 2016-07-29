package com.zsw.testmodel;

import android.view.LayoutInflater;
import android.view.View;

import com.andexert.library.RippleView;
import com.zsw.colorfulcloudslibrary.base.baseactivity.TBaseActivity;
import com.zsw.colorfulcloudslibrary.base.basetitle.TbaseTitleBar;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class  MyBaseActivity extends TBaseActivity implements View.OnClickListener,RippleView.OnRippleCompleteListener {

    @Override
    public void onClick(View v) {

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
