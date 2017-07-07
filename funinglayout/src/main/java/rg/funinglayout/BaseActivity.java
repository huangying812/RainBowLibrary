package rg.funinglayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Create on 2017/6/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2017/6/27
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCompoentBinded();
    }

    public abstract void onCompoentBinded();

    public void goTo(Class clzz){
        startActivity(new Intent(this,clzz));
    }

}
