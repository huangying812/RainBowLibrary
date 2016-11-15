package com.zsw.testmodel.ui.act.mvp.model;

import com.zsw.testmodel.ui.act.mvp.entity.MUserBean;

/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/11/10
 */
public interface MModel {
    void saveMUserbean(MUserBean bean);
            MUserBean getMuserbean();
}
