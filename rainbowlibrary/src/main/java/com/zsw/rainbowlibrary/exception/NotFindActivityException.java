package com.zsw.rainbowlibrary.exception;

/**
 * NotFindActivityException
 * author @zhusw
 */
public class NotFindActivityException extends  Exception{

    public NotFindActivityException(){
        this("colorful- This Activity does not extends TBaseActivity !!!!!!!!");
    }
    public NotFindActivityException(String error){
        super(error);
    }
}
