package com.zsw.colorfulcloudslibrary.base.baseexception;

/**
 * Created by Administrator on 2016/7/5.
 */
public class NotFindActivityException extends  Exception{

    public NotFindActivityException(){
        this("colorful- This Activity does not extends TBaseActivity !!!!!!!!");
    }
    public NotFindActivityException(String error){
        super(error);
    }
}
