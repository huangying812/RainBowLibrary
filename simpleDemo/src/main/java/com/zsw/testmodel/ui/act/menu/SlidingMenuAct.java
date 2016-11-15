package com.zsw.testmodel.ui.act.menu;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.zsw.rainbowlibrary.uibase.baseactivity.TBaseFragmentGroupActivity;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

/**
 * Created by Administrator on 2016/6/26.
 */
public class SlidingMenuAct extends TBaseFragmentGroupActivity {

    @Override
    public void onLayoutloaded() {
        removeTBaseTitleBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("骚气侧漏");
//        toolbar.setLogo(getResources().getDrawable(R.mipmap.ic_account_balance_white_24dp));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public int fragmentContainerId() {
        return R.id.asl_frameLayout;
    }

    @Override
    public View setLayoutView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_slidinglayout,null);
    }

    @Override
    public Class getRuningClass() {
        return getClass();
    }
}
