package com.zsw.testmodel.base;

import android.view.LayoutInflater;
import android.view.View;

import com.andexert.library.RippleView;
import com.zsw.rainbowlibrary.customview.basetitle.TbaseTitleBar;
import com.zsw.rainbowlibrary.uibase.baseactivity.TBaseActivity;
import com.zsw.testmodel.R;
import com.zsw.testmodel.common.APIManager;
import com.zsw.testmodel.common.GitHubAPIService;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class AbActivity extends TBaseActivity implements RippleView.OnRippleCompleteListener {


    public GitHubAPIService getAPIService(){
       return  APIManager.INSTANCE.getApiService();
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
