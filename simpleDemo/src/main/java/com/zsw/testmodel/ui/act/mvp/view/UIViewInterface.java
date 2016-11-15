package com.zsw.testmodel.ui.act.mvp.view;

import com.zsw.testmodel.ui.act.mvp.entity.MUserBean;

/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * -----View 层接口协议------
 * author Ben
 * Last_Update - 2016/11/10
 */
public interface UIViewInterface  {

    void updateUI(MUserBean userbean);

                MUserBean saveMUserBean();

}
