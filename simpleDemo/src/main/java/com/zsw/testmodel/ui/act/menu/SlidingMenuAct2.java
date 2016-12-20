package com.zsw.testmodel.ui.act.menu;

import android.view.LayoutInflater;
import android.view.View;

import com.zsw.rainbowlibrary.customview.TbaseTitleBar;
import com.zsw.rainbowlibrary.uibase.baseactivity.SlidingMenuActivity;
import com.zsw.testmodel.R;
import com.zsw.testmodel.ui.fragment.FragmentA;

/**
 * author  z.sw
 * time  2016/8/26.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class SlidingMenuAct2 extends SlidingMenuActivity {

    @Override
    public void onLayoutInitialized() {
        //初始化菜单按钮的事件 及其他view的绑定都在这里操作
        getTitleBar().setCenterNormalTextView("侧滑菜单");
        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.mohei_tp));
        getTitleBar().setLeftNormalButton(new TbaseTitleBar.OnTbaseTitleLeftViewClickListener() {
            @Override
            public void onClick(View v) {
               openMenu();
            }
        }).setBackgroundResource(R.mipmap.back_f);
        switchFragment(FragmentA.class);

    }

    @Override
    public View setMenuView() {
        return LayoutInflater.from(this).inflate(R.layout.menu_layout,null);
    }


    @Override
    public Class getRuningClass() {
        return getClass();
    }
}
