package com.zsw.testmodel.common;

import com.zsw.rainbowlibrary.httputils.factory.TBRetrofitFactory;

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
     * 这里并不推荐这样使用，在APIService上直接给出全路径会比较好，维护和可读性都好一些
     * 这里先给一个假的就行
     */
    public static final String BASEURL = "http://www.xx.com/";

    private final GitHubAPIService apiService;
    private TBRetrofitFactory TBRetrofitFactory;

     APIManager(){
         TBRetrofitFactory = TBRetrofitFactory.getInstance(BASEURL);
         apiService = TBRetrofitFactory.createService(GitHubAPIService.class);

     }

    public TBRetrofitFactory getTBRetrofitFactory() {
        return TBRetrofitFactory;
    }
    public GitHubAPIService getApiService(){
        return apiService;
    }

}
