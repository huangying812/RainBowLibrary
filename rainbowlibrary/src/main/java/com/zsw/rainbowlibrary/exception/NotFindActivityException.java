package com.zsw.rainbowlibrary.exception;

/**
 * Create on 2016/8/19.
 *
 * @author Ben
 *         Description-
 *         <p>
 *         github  https://github.com/HarkBen
 * @Last_update time - 2016年9月19日14:33:21
 */
public class NotFindActivityException extends  Exception{

    public NotFindActivityException(){
        this("colorful- This Activity does not extends TBaseActivity !!!!!!!!");
    }
    public NotFindActivityException(String error){
        super(error);
    }
}
