package rg.funinglayout;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import rg.funinglayout.views.progress.CircleProgressView;

/**
 * Create on 2017/7/5.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/7/5
 */
public class CircleProgressActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    CircleProgressView circleProgressView;
    Switch aSwitchAnimation,aSwitchReverseAngle;
    SeekBar seekBar;

    @Override
    public void onCompoentBinded() {
        setContentView(R.layout.act_circlrprogressview);
        circleProgressView = (CircleProgressView) findViewById(R.id.ac_CircleProgressView);
        aSwitchAnimation = (Switch) findViewById(R.id.ac_switchAnimation);
        aSwitchReverseAngle = (Switch) findViewById(R.id.ac_switchReverseAngle);
        aSwitchReverseAngle.setOnCheckedChangeListener(this);
        aSwitchAnimation.setOnCheckedChangeListener(this);
        seekBar = (SeekBar) findViewById(R.id.ac_seekBar);
        circleProgressView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                circleProgressView.setText(progress + "%");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circleProgressView.setOpenAnimation(false);
                circleProgressView.setProgress(progress);
                circleProgressView.setText(progress+"%");
                circleProgressView.commit();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.ac_switchAnimation:
                circleProgressView.setOpenAnimation(isChecked);
                break;
            case R.id.ac_switchReverseAngle:
                circleProgressView.setProgress(70);
                circleProgressView.setReverseAngle(isChecked);
                circleProgressView.commit();
                break;
        }

    }
}
