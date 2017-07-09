package rg.funinglayout;

import android.os.Handler;

import rg.funinglayout.BaseActivity;
import rg.funinglayout.R;
import rg.funinglayout.views.CircleProgressView;
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

    CircleProgressView circleProgressView;

    @Override
    public void onCompoentBinded() {
        setContentView(R.layout.act_circlrprogressview);
        circleProgressView = (CircleProgressView) findViewById(R.id.ac_CircleProgressView);
                circleProgressView.setProgress(80);
                circleProgressView.setOpenAnimation(true);
                circleProgressView.commit();
    }

}
