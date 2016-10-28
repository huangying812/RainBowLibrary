package com.zsw.testmodel.ui.act.customview;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

/**
 * Create on 2016/10/17.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/10/17
 */
public class CustomView1Act extends AbActivity {
    @Override
    public void initLayout() {
        loadContentView(R.layout.act_customview1);
    }

    @Override
    public Class getRuningClass() {
        return CustomView1Act.class;
    }
}
