package rg.funinglayout.views;

import rg.funinglayout.BaseActivity;
import rg.funinglayout.R;
import rg.funinglayout.views.CircleRingView;

/**
 * Create on 2017/7/5.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/7/5
 */
public class CircleProgressActivity extends BaseActivity {

    CircleRingView circleRingView;

    @Override
    public void onCompoentBinded() {
        setContentView(R.layout.act_circlrprogressview);
        circleRingView = (CircleRingView) findViewById(R.id.ac_CircleRingView);

    }

}
