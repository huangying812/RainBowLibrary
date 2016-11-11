package com.zsw.testmodel.ui.act.mvp.Presenter;

import com.zsw.testmodel.ui.act.mvp.model.MModel;
import com.zsw.testmodel.ui.act.mvp.model.MModelImpl;
import com.zsw.testmodel.ui.act.mvp.view.UIViewInterface;


/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * -----控制层，负责model → view 交互控制------
 * author Ben
 * Last_Update - 2016/11/10
 */
public class Presenter {

    private MModel mModel;

    private UIViewInterface uiView;

    public Presenter (UIViewInterface uiView){
        this.uiView = uiView;
        mModel = new MModelImpl();

    }

    public void saveData(){
        mModel.saveMUserbean(uiView.saveMUserBean());
    }

    public void loadData(){
        uiView.updateUI(mModel.getMuserbean());
    }



}
