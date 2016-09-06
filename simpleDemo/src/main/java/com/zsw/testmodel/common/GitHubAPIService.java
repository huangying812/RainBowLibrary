package com.zsw.testmodel.common;

import com.zsw.testmodel.ui.act.UserBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * author  z.sw
 * time  2016/9/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public  interface GitHubAPIService {

    @GET("https://api.github.com/users/{user}")
    Call<UserBean> getUser(@Path("user") String user);

    @GET("https://api.github.com/users/{user}")
    Call<ResponseBody> getAll(@Path("user") String user);


}
