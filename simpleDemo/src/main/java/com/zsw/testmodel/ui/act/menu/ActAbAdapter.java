package com.zsw.testmodel.ui.act.menu;

import android.os.Bundle;
import android.widget.ListView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create on 2016/12/1.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/12/1
 */
public class ActAbAdapter extends AbActivity {
    @Bind(R.id.aa_listView)
    ListView aaListView;
    private List<String> datas;

    @Override
    public void initLayout() {
        getTitleBar().setCenterNormalTextView("AbAdapter");
        loadContentView(R.layout.act_abapter);
        ButterKnife.bind(this);

    }

}
