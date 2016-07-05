package com.zsw.colorfulcloudslibrary.base.baseactivity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.zsw.colorfulcloudslibrary.base.basefragment.TBaseFragment;

/**
 * 切换Fragment的Activity
 * author z.sw 2016-06-16 13:43:31
 */
public abstract class TBaseFragmentGroupActivity extends TBaseActivity{
    private TBaseFragment showFragment;
    @Override
    public void onLayoutLoading() {
        super.setContentLayout(setLayoutView());

        onLayoutloaded();
    }

    /**
     * 在布局加载完成以后调用
     */
    public abstract  void onLayoutloaded();
    /**
     * @return FrameLayout  view id
     */
    public abstract  int fragmentContainerId();

    /**
     * @return  layout View
     */
    public abstract View setLayoutView();

    public TBaseFragment switchFragment(Class<?extends TBaseFragment> clazz){
       return switchFragment(fragmentContainerId(),clazz);
    }

    public TBaseFragment findFragmentByTag(Class<?extends TBaseFragment> clazz){
        FragmentManager  fm = getSupportFragmentManager();
        return (TBaseFragment) fm.findFragmentByTag(clazz.getName());
    }

    /**
     * @param frameId frameLayout container
     * @param clazz position fragment
     * @return current show fragment
     */
    private TBaseFragment switchFragment(int frameId,Class<?extends TBaseFragment> clazz){
         FragmentManager  fm = getSupportFragmentManager();
        FragmentTransaction  ft = fm.beginTransaction();
        TBaseFragment currentFragment = (TBaseFragment) fm.findFragmentByTag(clazz.getName());
            if(null  == currentFragment){
                try {
                    currentFragment = clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
                //如果选择的fragment 不是第一个也不是正在显示的 则隐藏正在显示的
            if(null != showFragment && showFragment != currentFragment){
                ft.hide(showFragment);
            }
            //对选择的fragment 处理
            if(!currentFragment.isAdded()){
                ft.add(frameId,currentFragment,clazz.getName());
            }else{
                ft.show(currentFragment);
            }
                ft.commitAllowingStateLoss();
        showFragment = currentFragment;
        return currentFragment;
    }

    @Override
    public void startLoadAnim(Drawable drawable) {
        super.startLoadAnim(drawable);
    }

    @Override
    public void stopLoadAnim() {
        super.stopLoadAnim();
    }



}
