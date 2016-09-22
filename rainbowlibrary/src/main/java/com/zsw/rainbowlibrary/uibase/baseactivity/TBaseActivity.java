package com.zsw.rainbowlibrary.uibase.baseactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.victor.loading.rotate.RotateLoading;
import com.zsw.rainbowlibrary.R;
import com.zsw.rainbowlibrary.customview.basetitle.TbaseTitleBar;
import com.zsw.rainbowlibrary.utils.L;
import com.zsw.rainbowlibrary.utils.LanguageTAG;
import com.zsw.rainbowlibrary.utils.SharedPUtils;
import com.zsw.rainbowlibrary.utils.V;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * author  z.sw
 * time  2016/8/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public abstract class TBaseActivity extends AppCompatActivity {
    /**
     * 根布局
     */
    private LinearLayout rootLayout;
    /**
     * 子布局容器
     */
    private LinearLayout childContentLayout;

    private LinearLayout loadingLayout;

    private RotateLoading rotateLoading;

    /**
     * 自定义的 加载动画
     */
    private AnimationDrawable animationDrawable;

    private TbaseTitleBar titleBar;

    /**
     * 是否添加了statusBar
     */
    private boolean isAddStatus ;

    private List<TBaseActivity> actList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLogE("");
        if(SharedPUtils.getInt(this,SharedPUtils.THEME_ID) != 0){
            setTheme(SharedPUtils.getInt(this,SharedPUtils.THEME_ID));

        }
        if(!V.isNull(SharedPUtils.getString(this,SharedPUtils.LANGUAGE))){
            reSetLanguageConfiguration(getResources(),SharedPUtils.getString(this,SharedPUtils.LANGUAGE));
        }

        initSystemSet();
        /**
         * 无路如何请不要替换下面方法的调用顺序
         */
        initRootLayout();
        addSetStatus(getStatusBarColor());
        setRootLayout();
        onLayoutLoading();
        actList.add(this);
    }

    public String printLogD(String log){
        L.printD(getClass().getName(),log);
        return getClass()+">msg=="+log;
    }
    public String printLogE(String log){
        L.printE(getClass().getName(),log);
        return getClass()+">msg=="+log;
    }

    /**
     * 返回当前页面类名 方便日志查看
     * @return
     */
    public abstract Class getRuningClass();

    public void finishAllAct(){
        if(null != actList && actList.size()>0){
            for(TBaseActivity act:actList){
                if(null != act){
                    act.finish();
                }
            }
        }

    }

    private int getStatusBarColor(){
        int color = SharedPUtils.getInt(this,SharedPUtils.THEME_COLOR);
        L.printD("TbaseActivity", "-statusBarColor - SP = " + color);
        if(color == 0){
            color = getResources().getColor(R.color.tbaseColorcolorNormal_status);
        }
        return color;
    }

    /**
     *
     * @param resources res资源管理器 getResources
     * @param tag LanguageTAG 中的语言TAG
     */
    private void reSetLanguageConfiguration(Resources resources , String tag){
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        L.printD("TBaseActivity", "-language setting tag== " + tag);
        if (tag.equals(LanguageTAG.EN)) {//英语

            config.locale = Locale.ENGLISH;

        } else if (tag.equals(LanguageTAG.KOREAN)) {//韩语

            config.locale = Locale.KOREAN;

        } else if (tag.equals(LanguageTAG.ZH)) {//中文

            config.locale = Locale.CHINA;

        } else if(tag.equals(LanguageTAG.FRENCH)){
            config.locale = Locale.FRENCH;
        } else {

            config.locale = Locale.getDefault();
        }
        resources.updateConfiguration(config, dm);
        SharedPUtils.saveLanguageSetting(this,tag);
    }

    /**
     *如果要单页面执行语言切换 请在不要设置当前Act的启动模式 为 single task 否则不起效果
     * -详情看act的4种启动模式
     * @param resources res资源管理器
     * @param tag LanguageTAG 中的语言TAG
     */
    public void switchLanguage(Resources resources , String tag){
        reSetLanguageConfiguration(resources,tag);
        reStartWindow();
        }

    /**
     * 启用重绘 更换主题
     * @param styleId
     */
    public void switchTheme(@StyleRes int styleId, @ColorInt int color){
        SharedPUtils.saveInt(this,SharedPUtils.THEME_ID,styleId);
        SharedPUtils.saveInt(this,SharedPUtils.THEME_COLOR,color);
        reStartWindow();
    }

    /**
     * 重启当前windows
     */
    public void reStartWindow(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 初始化设置 和控制变量
     */
    private void initSystemSet(){
        /**
         * 为了不限制 子Activity的样式选择
         * 在这里动态去掉当前Activity的ActionBar 子类可以在配置文件重新定义
         */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //是否添加过 statusBar 默认为 false
        isAddStatus = false;
        //设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //导航栏背景 一般不要设置设置会影响布局
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 为子类提供抽象 设置子类布局
     */
    public abstract  void onLayoutLoading();


    private void setRootLayout(){
        setContentView(rootLayout);
    }
    private void initRootLayout(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.tbase_activity_layout,null);
        rootLayout = (LinearLayout) rootView.findViewById(R.id.tbase_activity_rootlayout);
        childContentLayout = (LinearLayout) rootView.findViewById(R.id.tbase_activity_childContentLayout);
        titleBar = (TbaseTitleBar) rootView.findViewById(R.id.tbase_activity_titleBar);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.tbase_activity_loadingLayout);
        rotateLoading = (RotateLoading) rootView.findViewById(R.id.tbasr_activity_loadingImage);
        rotateLoading.setLoadingColor(getStatusBarColor());
    }

    public  TbaseTitleBar getTitleBar(){
        if( null != titleBar) return titleBar;
        else  return  null;
    }

    public  void hiddenTitleBar(){
        if( null != titleBar)  titleBar.setVisibility(View.GONE);
    }
    public  void showTitleBar(){
        if( null != titleBar) titleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 在当前Activity删除以后 将不能再重新添加
     */
    public final void removeTBaseTitleBar() {
            if( null != titleBar){
                rootLayout.removeView(titleBar);
                titleBar = null;
        }
    }


    /**
     * @param color 状态栏颜色值
     */
    protected final void reSetStatusColor(@ColorInt int color){
        if(isAddStatus) rootLayout.getChildAt(0).setBackgroundColor(color);
    }

    /**
     * @param ff 状态栏颜透明度
     */
    protected void reSetStatusAlpha(@FloatRange(from = 0.0,to = 1.0) float ff){
        if(isAddStatus) rootLayout.getChildAt(0).setAlpha(ff);
    }

    /**
     * 从根布局删除 statusbar
     * 在子类中 就需要在当前 activity的布局文件中声明 firstSystemWindows  true
     * 来指向拉伸到状态栏的view
     */
    protected final void removeStatusBar(){
        if(isAddStatus){
            rootLayout.removeViewAt(0);
            isAddStatus = false;
        }

    }

    /**
     * 添加 statusBar
     * 如果没有添加 statusBar则新建并添加到rootLayout
     * 并将isAddStatus = true
     * */
    protected final  void addSetStatus(@ColorInt int color){
        if(!isAddStatus){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                addStatusToRootLayout(this,color);
            }
        }


    }
    /** * 生成一个和状态栏大小相同的矩形条
     * * @param activity 需要设置的activity
     * * @param color 状态栏颜色值
     * @return 状态栏矩形条 */
    private  View createStatusView(Activity activity, @ColorInt int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


    private  void addStatusToRootLayout(Activity activity,@ColorInt int color) {
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            statusView.setFitsSystemWindows(true);
            // 添加到 rootLayout最顶层
            rootLayout.addView(statusView,0);
            isAddStatus = true;

        }




    /**
     * 设置当前打开的Activity的布局
     * @param view
     */
    public final void  setContentLayout(View view){
            if(childContentLayout.getChildCount()>0) childContentLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        childContentLayout.addView(view,childContentLayout.getChildCount(),params);
    }





    /**
     *  设置自定义的动画
     * @param drawable
     */
    @CallSuper
    private  void showLoadAnimation(Drawable drawable){
        rotateLoading.setBackground(drawable);
        animationDrawable = (AnimationDrawable) rotateLoading.getBackground();
        animationDrawable.start();
        childContentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);

    }


    /**
     * 播放自定义的加载动画
     * @param drawable
     */
    @CallSuper
    public void startLoadAnim(Drawable drawable){

        showLoadAnimation(drawable);
    }


    private  void showLoadAnimation(){
        if(null != animationDrawable){
            rotateLoading.setBackground(getResources().getDrawable(R.drawable.sh_tp_bg));
        }
        rotateLoading.start();
        childContentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 播放默认加载动画
     */
    @CallSuper
    public void startLoadAnim(){

        showLoadAnimation();
    }

    /**
     * 取消所有加载动画
     */
    @CallSuper
    public void stopLoadAnim(){
        dismissLoadAnimation();
    }

    /**
     * 停止动画，
     */
    private final void dismissLoadAnimation(){
        if(null != animationDrawable && animationDrawable.isRunning()){
            animationDrawable.stop();
        }else{
            rotateLoading.stop();
        }
        childContentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
