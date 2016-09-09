package com.zsw.rainbowlibrary.httputils.manager;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author  z.sw
 * time  2016/9/1.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public  class RetrofitManager {

    private   Retrofit retrofit;
    private volatile static RetrofitManager retorfitManager;

    /**
     * 构造私有掉，保证retorfitManager唯一，retrofit也就唯一
     * @param baseUrl
     */
    private RetrofitManager(String baseUrl){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(OkHttpClientManager.getInstance().getOkHttpClient());
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        Log.d("RetrofitManager","create RetrofitManager");
    }


    public static RetrofitManager getInstance(String baseUrl){
        if(null == retorfitManager){
            synchronized (RetrofitManager.class){
                if(null == retorfitManager){
                    retorfitManager = new RetrofitManager(baseUrl);
                }
            }
        }
        return  retorfitManager;
    }
    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }

}
