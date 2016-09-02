package com.zsw.testmodel.common;

import com.zsw.testmodel.ui.act.UserBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author  z.sw
 * time  2016/9/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public interface APIService {

    @GET("users/{user}")
    Call<UserBean> getUser(@Path("user") String user);

}
