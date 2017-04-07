package com.zsw.testmodel.ui.act.mvp.view;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.ui.act.mvp.entity.MUserBean;
import com.zsw.testmodel.ui.act.mvp.Presenter.Presenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/10
 */
public class MVPSimpleAct extends AbActivity implements UIViewInterface{
    @Bind(R.id.am_name)
    TextInputEditText amName;
    @Bind(R.id.am_phoneNumber)
    TextInputEditText amPhoneNumber;
    @Bind(R.id.am_address)
    TextInputEditText amAddress;
    @Bind(R.id.am_commit)
    Button amCommit;
    @Bind(R.id.am_showName)
    TextView amShowName;
    @Bind(R.id.am_showPhoneNumber)
    TextView amShowPhoneNumber;
    @Bind(R.id.am_showAddress)
    TextView amShowAddress;
    @Bind(R.id.am_loadData)
    Button amLoadData;

    Presenter presenter;
    @Override
    public void initLayout() {
        loadContentView(R.layout.act_mvp);
        ButterKnife.bind(this);

        presenter = new Presenter(this);
    }




    @OnClick({R.id.am_commit, R.id.am_loadData})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.am_commit:
                presenter.saveData();
                break;
            case R.id.am_loadData:
                presenter.loadData();
                break;
            default:break;
        }
    }
    
    @Override
    public void updateUI(MUserBean userbean) {
        amShowName.setText(userbean.getUserName());
        amShowPhoneNumber.setText(userbean.getPhoneNumber());
        amShowAddress.setText(userbean.getAddress());
    }
    @Override
    public MUserBean saveMUserBean() {
        MUserBean mUserBean = new MUserBean();
        mUserBean.setAddress(amAddress.getText().toString());
        mUserBean.setPhoneNumber(amPhoneNumber.getText().toString());
        mUserBean.setUserName(amName.getText().toString());
        return mUserBean;
    }

}
