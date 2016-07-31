package com.zsw.colorfulcloudslibrary.base.basefragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.victor.loading.rotate.RotateLoading;
import com.zsw.colorfulcloudslibrary.R;
import com.zsw.colorfulcloudslibrary.base.baseactivity.TBaseActivity;
import com.zsw.colorfulcloudslibrary.base.baseexception.NotFindActivityException;

/**
 * from support v4 packages
 * Created by z.sw on 2016/7/4.
 */
public abstract class TBaseFragment extends Fragment {
    private TBaseActivity fgActivity;

    /**
     * 根布局
     * @param activity
     */
    private LinearLayout rootView;

    /**
     * 内容布局容器
     * @param activity
     */
    private ViewGroup contentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           rootView = new LinearLayout(this.getActivity());
           rootView.setOrientation(LinearLayout.VERTICAL);
           contentView = new RelativeLayout(this.getActivity());
        LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT
                ,LayoutParams.MATCH_PARENT);
            contentView.setLayoutParams(params);
            rootView.addView(contentView);
        onInitLayout(inflater,container,savedInstanceState);
        return rootView;
    }

    /**
     * 提供给子类加载器
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    public abstract  void onInitLayout(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);


    /**
     * 设置子类布局
     * @param view
     */
    public void setContentLayout(View view){
        if(null != contentView && null != view){
            if(contentView.getChildCount() > 0 ){
                contentView.removeAllViews();
            }
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            contentView.addView(view);
        }
    }


    /**
     *   //防止重复引用同一个Activity  Fragment重叠
      * @param activity -检查是否继承自TbaseActivity 并抛出NotFindActivityException
     */
    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof TBaseActivity){
            fgActivity = (TBaseActivity) activity;
        }else{
            try {
                throw new NotFindActivityException();
            } catch (NotFindActivityException e) {
                e.printStackTrace();
            }
    }
        super.onAttach(activity);
    }

    public TBaseActivity getTBaseActivity() throws NotFindActivityException {
        Activity activity = getActivity();
        if(activity instanceof TBaseActivity){
            return (TBaseActivity) activity;
        }else{
            throw new NotFindActivityException();
        }

    }

    public void startLoadAnimInAct(){
        fgActivity.startLoadAnim();
    }
    public void startLoadAnimInAct(Drawable drawable){
        fgActivity.startLoadAnim(drawable);
    }
    public void stopLoadAnimInAct(){
        fgActivity.stopLoadAnim();
    }
}
