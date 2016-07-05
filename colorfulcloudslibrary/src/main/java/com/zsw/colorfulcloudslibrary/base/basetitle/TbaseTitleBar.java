package com.zsw.colorfulcloudslibrary.base.basetitle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsw.colorfulcloudslibrary.R;

/**
 * TbaseActivity titleBar
 * author @zhusw
 */
public class TbaseTitleBar extends LinearLayout implements View.OnClickListener {

    private LinearLayout tBaseTitleLayoutLeft;
    private LinearLayout tBaseTitleLayoutCenter;
    private LinearLayout tBaseTitleLayoutRight;
    private LayoutInflater inflater;
    private LinearLayout titleRootLayout;


    private OnTbaseTitleLeftViewClickListener onTbaseTitleLeftViewClickListener;

    private OnTbaseTitleCenterViewClickListener onTbaseTitleCenterViewClickListener;

    private OnTbaseTitleRightViewClickListener onTbaseTitleRightViewClickListener;

    public interface OnTbaseTitleLeftViewClickListener {
        public void onClick(View v);
    }

    public interface OnTbaseTitleCenterViewClickListener {
        public void onClick(View v);
    }

    public interface OnTbaseTitleRightViewClickListener {
        public void onClick(View v);
    }

    /**
     * new  Object  in class  call this Constructor
     *
     * @param context
     */
    public TbaseTitleBar(Context context) {
        super(context);
        initLayout(context);
    }

    /**
     * Edit in XML  but no point android.style,  call this Constructor
     *
     * @param context
     * @param attrs
     */
    public TbaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    /**
     * Edit in XML  and point android.style,call this Constructor
     *
     * @param context
     * @param attrs    layout_width layout_height
     * @param defStyle
     */
    public TbaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
    }

    private final void initLayout(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        titleRootLayout = (LinearLayout) inflaterView(R.layout.tbase_titlebar_layout,this,true);
        tBaseTitleLayoutLeft = (LinearLayout) titleRootLayout.findViewById(R.id.tbase_titleLayout_left);

        tBaseTitleLayoutCenter = (LinearLayout) titleRootLayout.findViewById(R.id.tbase_titleLayout_center);
        tBaseTitleLayoutRight = (LinearLayout) titleRootLayout.findViewById(R.id.tbase_titleLayout_right);
    }

    private View inflaterView(int LayoutId, ViewGroup root, boolean attachToRoot) {
        return inflater.inflate(LayoutId, root, attachToRoot);
    }

    private View inflaterView(int layoutId) {
        return inflater.inflate(layoutId, null);
    }


//    public LinearLayout getTBaseTitleLayoutLeft() {
//        return tBaseTitleLayoutLeft;
//    }
//
//    public LinearLayout getTBaseTitleLayoutCenter() {
//        return tBaseTitleLayoutCenter;
//    }
//
//    public LinearLayout getTBaseTitleLayoutRight() {
//        return tBaseTitleLayoutRight;
//    }


    /**
     * 设置图片或xml资源为背景
     * @param drawable
     */
    public void setTitleBarBackground(Drawable drawable){
        titleRootLayout.setBackground(drawable);
    }

    /**
     * 设置颜色值为背景
     * @param color
     */
    public void setTitleBarBackgroundColor(@ColorInt int color){
        titleRootLayout.setBackgroundColor(color);
    }
    /**
     * add view to rootLayout
     * warning !!!! before rootLayout will be perform removeAllViews.
     * So rootLayout only edit the last  View
     *
     * @param view
     * @param rootLayout titleLayout titlelayoutLeft  | titleLayoutright | titleLayoutcenter
     *                   example:tbaseTitleBar.addView(view,tbaseTitleBar.getTBaseTitleLayoutRight())
     */
    private void addView(View view, LinearLayout rootLayout) {
        if (rootLayout.getChildCount() > 0) {
            rootLayout.removeAllViews();
        }
        rootLayout.addView(view, rootLayout.getChildCount());
    }


    // tBaseTitleLayoutLeft-------------------------------

    /**
     * add view to  TbaseTitleBar LeftLayout
     * @param view
     * @return
     */
    public View setLeftView(View view) {
        addView(view, tBaseTitleLayoutLeft);
        return view;
    }

    /**
     * 给titleBar 左边布局添加一个自定义的View
     * @param drawable
     * @return
     */
    public View setLeftView(View view, Drawable drawable) {
        if (null != drawable) {
            view.setBackground(drawable);
        }
        addView(view, tBaseTitleLayoutLeft);
        return view;
    }

    /**
     * add Normal Button to TbaseTitleBar LeftLayout
     *
     * @param leftListener
     * @return
     */
    public Button setLeftNormalButton(OnTbaseTitleLeftViewClickListener leftListener) {

        Button button = (Button) inflaterView(R.layout.tbase_leftbtn_layout);
        setLeftView(button);

        if (null != leftListener) {
            this.onTbaseTitleLeftViewClickListener = leftListener;
            button.setOnClickListener(this);
        }

        return button;
    }
    public Button setLeftNormalButton(String str,OnTbaseTitleLeftViewClickListener leftListener) {

        Button button = (Button) inflaterView(R.layout.tbase_leftbtn_layout);
        if(null != str){
            button.setText(str);
        }
        setLeftView(button);

        if (null != leftListener) {
            this.onTbaseTitleLeftViewClickListener = leftListener;
            button.setOnClickListener(this);
        }

        return button;
    }
    public void setLeftBtnOnClickListener( OnTbaseTitleLeftViewClickListener leftListener){
        if(null != leftListener){
            this.onTbaseTitleLeftViewClickListener = leftListener;
        }
    }
    public void removeLeftBtnOnClickListener() {
        this.onTbaseTitleLeftViewClickListener = null;
    }


    // tBaseTitleLayoutCenter------------------------------
    /**
     * 给titleBar 中间布局添加一个自定义的View
     * @param drawable
     * @return
     */
    public View seCenterView(View view, Drawable drawable) {
        if (null != drawable) {
            view.setBackground(drawable);
        }
        addView(view, tBaseTitleLayoutCenter);
        return view;
    }

    public TextView setCenterNormalTextView(String title){
        TextView textView = (TextView) inflaterView(R.layout.tbase_centertextview_layout);
        if(null != title){
            textView.setText(title);
        }
        seCenterView(textView,null);
        return  textView;
    }
    public TextView setCenterNormalTextView(String title,OnTbaseTitleCenterViewClickListener onTbaseTitleCenterViewClickListener){
        TextView textView = (TextView) inflaterView(R.layout.tbase_centertextview_layout);
        if(null != title){
            textView.setText(title);
        }
        seCenterView(textView,null);
        if(null != onTbaseTitleCenterViewClickListener){
            this.onTbaseTitleCenterViewClickListener = onTbaseTitleCenterViewClickListener;
            textView.setOnClickListener(this);
        }
        return  textView;
    }
    public void setCenterTextViewOnClickListener( OnTbaseTitleCenterViewClickListener centerListener){
        if(null != centerListener){
            this.onTbaseTitleCenterViewClickListener = centerListener;
        }
    }
    public void removeCenterTextViewOnClickListener() {
        this.onTbaseTitleCenterViewClickListener = null;
    }


    // tBaseTitleLayoutRight----------------------------------
    /**
     * add view to  TbaseTitleBar RightLayout
     * @param view
     * @return
     */
    public View setRightView(View view) {
        addView(view, tBaseTitleLayoutRight);
        return view;
    }

    /**
     * 给titleBar 右边布局添加一个自定义的View
     * @param drawable
     * @return
     */
    public View setRightView(View view, Drawable drawable) {
        if (null != drawable) {
            view.setBackground(drawable);
        }
        addView(view, tBaseTitleLayoutRight);
        return view;
    }

    /**
     * add Normal Button to TbaseTitleBar LeftLayout
     * @param rightListener
     * @return
     */
    public Button setRightNormalButton(OnTbaseTitleRightViewClickListener rightListener) {

        Button button = (Button) inflaterView(R.layout.tbase_rightbtn_layout);
        setRightView(button);

        if (null != rightListener) {
            this.onTbaseTitleRightViewClickListener = rightListener;
            button.setOnClickListener(this);
        }
        return button;
    }

    public Button setRightNormalButton(String str,OnTbaseTitleRightViewClickListener rightListener) {

        Button button = (Button) inflaterView(R.layout.tbase_rightbtn_layout);
        if(null != str){
            button.setText(str);
        }
        setRightView(button);

        if (null != rightListener) {
            this.onTbaseTitleRightViewClickListener = rightListener;
            button.setOnClickListener(this);
        }

        return button;
    }


    public void setRightBtnOnClickListener( OnTbaseTitleRightViewClickListener rightListener){
        if(null != rightListener){
            this.onTbaseTitleRightViewClickListener = rightListener;
        }
    }
    public void removeRightBtnOnClickListener() {
        this.onTbaseTitleRightViewClickListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tbase_titlelayout_left_btn) {
            if (null != onTbaseTitleLeftViewClickListener) {
                onTbaseTitleLeftViewClickListener.onClick(v);
            }
            return;
        }
        if(v.getId() == R.id.tbase_titleLayout_centerTextView){
            if(null != onTbaseTitleCenterViewClickListener){
                onTbaseTitleCenterViewClickListener.onClick(v);
            }
            return;
        }
        if(v.getId() == R.id.tbase_titlelayout_right_btn){
            if(null != onTbaseTitleRightViewClickListener){
                onTbaseTitleRightViewClickListener.onClick(v);
            }
            return;
        }


    }


}
