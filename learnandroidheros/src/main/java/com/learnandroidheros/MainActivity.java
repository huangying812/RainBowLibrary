package com.learnandroidheros;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Create on 2016/10/16.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/16
 */
public class MainActivity  extends AppCompatActivity{

    private LinearLayout contentLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params =
                new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        ,LinearLayout.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(params);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(contentLayout);
        loadBtn();
    }

    void loadBtn(){
        Button buttonViewMeasure = new Button(this);
        buttonViewMeasure.setText("琪宝宝来袭");
        contentLayout.addView(buttonViewMeasure);

        Button ss = new Button(this);
        ss.setText("大琪");
        contentLayout.addView(ss);

        Button aa = new Button(this);
        aa.setText("二琪");
        contentLayout.addView(aa);

        Button dd = new Button(this);
        dd.setText("幺琪");
        contentLayout.addView(dd);

        Button cc = new Button(this);
        cc.setText("琪四哥");
        contentLayout.addView(cc);

    }


}
