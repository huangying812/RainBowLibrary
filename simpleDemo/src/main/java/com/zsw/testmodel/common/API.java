package com.zsw.testmodel.common;

/**
 * Create on 2016/9/22.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/9/22
 */

public class API {
    private static String getAPI(String path){
        return BASEURL+path;
    }
    public static final String BASEURL = "https://api.github.com/users/";

    /**
     * 获取GitHub 上的用户信息
     */
    public static final String GETUSERINFO =  BASEURL+"{user}";
    public static final String GETUSERINFO2 =  BASEURL+"{user}";






}
