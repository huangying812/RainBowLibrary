package com.zsw.colorfulcloudslibrary.base.basefragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zsw.colorfulcloudslibrary.base.baseactivity.TBaseFragmentGroupActivity;
import com.zsw.colorfulcloudslibrary.base.baseexception.NotFindFGActivityException;

/**
 * from support v4 packages
 * Created by z.sw on 2016/7/4.
 */
public abstract class TBaseFragment extends Fragment {
    private TBaseFragmentGroupActivity fgActivity;

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


    //防止重复引用同一个Activity  Fragment重叠
    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof  TBaseFragmentGroupActivity){
            fgActivity = (TBaseFragmentGroupActivity) activity;
        }else{
            try {
                throw new NotFindFGActivityException();
            } catch (NotFindFGActivityException e) {
                e.printStackTrace();
            }
        }
        super.onAttach(activity);
    }

    public TBaseFragmentGroupActivity getTBaseFGActivity() throws NotFindFGActivityException {
        Activity activity = getActivity();
        if(activity instanceof  TBaseFragmentGroupActivity){
            return (TBaseFragmentGroupActivity) activity;
        }else{
            throw new NotFindFGActivityException();
        }

    }

    public void startLoadAnim(Drawable drawable){
        fgActivity.startLoadAnim(drawable);
    }

    public void stopLoadAnim(){
        fgActivity.stopLoadAnim();
    }





}
