package com.zsw.testmodel.common;

import com.zsw.rh.manage.RetrofitManager;

/**
 * author  z.sw
 * time  2016/9/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public enum APIManager {
    INSTANCE;
    /**
     * BASEURL将用于设置到 retorfit 中，所以必须以/收尾否则会抛出 require baseUrl异常
     */
    public static final String BASEURL = "https://api.github.com/";
    private final   APIService apiService;

     APIManager(){
        apiService = RetrofitManager.getInstance(BASEURL).createService(APIService.class);
    }

    public  APIService getApiService(){
        return apiService;
    }


}
