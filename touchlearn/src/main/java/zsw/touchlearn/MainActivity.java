package zsw.touchlearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.button;

/**
 * Create on 2016/12/23.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/23
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.am_tbutton)
    TButton tbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        L.println(TAG,"onTouch = ACTON_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        L.println(TAG,"onTouch = ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        L.println(TAG,"onTouch = ACTION_UP");
                        break;
                    default:break;
                }
                //不消费
                return false;
            }
        });

        tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tbutton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }



}
