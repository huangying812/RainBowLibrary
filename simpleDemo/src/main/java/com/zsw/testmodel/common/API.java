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

    public static final String BASEURL = "http://vn-functional.chinacloudapp.cn/wsk-ns/api/";

    /**
     * 测试登陆
     * 参数
     * {"username":"zhusw",
     * "password":"333333",
     * "client_flag":"android",
     * "model":"SCL-AL00",
     * "locale":"zh"}
     */
    public static final String LOGIN = BASEURL +"/mobilelogin";


    public static final String UPLOADFILE = "http://180.166.66.226:43230/baoshi/upload";
//public static final String UPLOADFILE = "http://192.168.0.30:8080/baoshi/upload";

    /**
     * 参数
     * {"clientMobileVersion":"Redmi Note 2",
     * "client_flag":"android",
     * "locale":"zh",
     * "loginName":"huangyw@visionet.com.cn",
     * "password":"111111"}
     */
    public static final String LOGINTOBR = "http://180.166.66.226:43230/baoshi/mobilelogin";
//    public static final String LOGINTOBR = "http://192.168.0.30:8080/baoshi/mobilelogin";

}
