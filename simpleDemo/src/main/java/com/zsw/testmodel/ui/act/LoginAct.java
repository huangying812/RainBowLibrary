package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.yinfork.linedlayout.LinedRelativeLayout;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author  z.sw
 * time  2016/8/15.
 * email  zhusw@visionet.com.cn
 * Description-登陆
 */
public class LoginAct extends AbActivity {

    @Bind(R.id.loginName)
    EditText loginName;
    @Bind(R.id.pw)
    EditText pw;

    @Bind(R.id.sign_in)
    Button signIn;
    @Bind(R.id.sign_up)
    TextView signUp;

    @Override
    public void initLayout() {
        removeBaseTitleBar();
        removeStatusBar();
        //这里是登陆页 所以不需要顶部工具栏布局
        loadContentView(R.layout.act_login);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }



    @OnClick({R.id.sign_in, R.id.sign_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                Intent intent = new Intent(LoginAct.this,MainAct.class);
                LoginAct.this.startActivity(intent);

                break;
            case R.id.sign_up:

                break;
        }
    }
}
