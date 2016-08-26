package com.zsw.testmodel.ui.act;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

/**
 * Created by Administrator on 2016/6/26.
 */
public class SlidingMenuAct extends AbActivity {

    @Override
    public void initLayout() {
        setStatusColor(R.color.testModelcpink);
        removeBaseTitleBar();
        loadContentView(R.layout.activity_slidinglayout);
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





}
