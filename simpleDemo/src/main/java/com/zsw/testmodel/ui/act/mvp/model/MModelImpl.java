package com.zsw.testmodel.ui.act.mvp.model;

import com.zsw.testmodel.ui.act.mvp.entity.MUserBean;

/**
 * Create on 2016/11/10.
 * github  https://github.com/HarkBen
 * Description:
 * ------Model层实现类，
 *  业务逻辑层，并提供return数据的方法-----
 * author Ben
 * Last_Update - 2016/11/10
 */
public class MModelImpl implements MModel{

    private  MUserBean mUserBean;

    /**
     * 这里我们模拟UI逻辑
     * 强行将 数据进行重组
     * @param bean
     */
    @Override
    public void saveMUserbean(MUserBean bean) {
        bean.setUserName("我就叫三毛");
        bean.setAddress("我住在:"+bean.getAddress());
        bean.setPhoneNumber("别打这个电话："+bean.getPhoneNumber());
            this.mUserBean = bean;
    }

    @Override
    public MUserBean getMuserbean() {
        return mUserBean;
    }


}
