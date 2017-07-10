package rg.funinglayout;

import android.os.Handler;

import rg.funinglayout.BaseActivity;
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
        circleProgressView.setMax(100);
        circleProgressView.setProgress(100);
        circleProgressView.setOpenAnimation(true);
        circleProgressView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                circleProgressView.setText(progress + "%");
            }
        });
        circleProgressView.commit();
    }

}
