package com.zsw.testmodel.common;


import com.zsw.testmodel.ui.act.UserBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * author  z.sw
 * time  2016/9/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public  interface GitHubAPIService {

    //传统模式
    @GET("https://api.github.com/users/{user}")
    Call<UserBean> getUser(@Path("user") String user);

    @GET("https://api.github.com/users/{user}")
    Call<ResponseBody> getAll(@Path("user") String user);

    //Rx模式
    @GET("https://api.github.com/users/{user}")
    Observable<UserBean> rxGetUser(@Path("user") String name);

}
