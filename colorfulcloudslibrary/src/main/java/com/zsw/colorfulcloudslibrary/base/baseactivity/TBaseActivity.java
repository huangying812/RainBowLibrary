package com.zsw.colorfulcloudslibrary.base.baseactivity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zsw.colorfulcloudslibrary.R;
import com.zsw.colorfulcloudslibrary.base.basetitle.TbaseTitleBar;


/**
 * BaseActivity
 * loadBaseLayout
 * loadBaseTitleBar
 * author zhusw 2016-06-16 13:43:31
 */
public abstract class TBaseActivity extends AppCompatActivity {
    /**
     * rootLayout
     */
    private LinearLayout rootLayout;
    /**
     * use to  loading childActivityLayout
     */
    private LinearLayout childContentLayout;

    private LinearLayout loadingLayout;

    private ImageView loadAnimationImageView;

    /**
     * onLoading anmination
     */
    private AnimationDrawable animationDrawable;

    private TbaseTitleBar titleBar;

    /**
     * 是否添加了statusBar
     */
    private boolean isAddStatus ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initSystemSet();
        /**
         * Warning!!!!!
         *Don't change  following  methods order call-
         * Never!!!
         */
        /**
         * about init  View
         */
        initRootLayout();
        /**
         * 默认会自动添加一个statusBar
         */
        addSetStatus(getResources().getColor(R.color.tbaseColorcolorNormal_status));
        /**
         *
         */
        setRootLayout();
        /**
         * 设置子类布局
         */
        onLayoutLoading();

    }

    /**
     * 初始化设置 和控制变量
     */
    private void initSystemSet(){
        /**
         * 为了不限制 子Activity的样式选择
         * 在这里去掉当前Activity的ActionBar
         */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //是否添加过 statusBar 默认为 false
        isAddStatus = false;
        //设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

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
        loadAnimationImageView = (ImageView) rootView.findViewById(R.id.tbasr_activity_loadingImage);

    }

    public final TbaseTitleBar getTitleBar(){
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
     * 在当前Activity删除以后 将不再提供重新设置
     */
    public final void removeBaseTitleBar(){
            if( null != titleBar){
                rootLayout.removeView(titleBar);
                titleBar = null;

        }



    }


    /**
     * @param color 状态栏颜色值
     */
    protected final void reSetStatusColor(int color){
        if(isAddStatus) rootLayout.getChildAt(0).setBackgroundColor(color);
    }

    /**
     * @param ff 状态栏颜透明度
     */
    protected void reSetStatusAlpha(float ff){
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
    protected final  void addSetStatus(int color){
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
    private  View createStatusView(Activity activity, int color) {
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

    /** * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值 */
    private  void addStatusToRootLayout(Activity activity, int color) {
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
        childContentLayout.addView(view,childContentLayout.getChildCount());
    }




    private  void showLoadAnimation(Drawable drawable){
        if( null == animationDrawable){
            initLoadAnimation(drawable);
        }
        childContentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        if(null != animationDrawable && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    private final void dismissLoadAnimation(){
        childContentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        if(null != animationDrawable && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    public void startLoadAnim(Drawable drawable){
        showLoadAnimation(drawable);
    }

    public void stopLoadAnim(){
        dismissLoadAnimation();
    }


    private void initLoadAnimation(Drawable drawable){
            loadAnimationImageView.setBackground(drawable);
            animationDrawable = (AnimationDrawable) loadAnimationImageView.getBackground();
    }


}
