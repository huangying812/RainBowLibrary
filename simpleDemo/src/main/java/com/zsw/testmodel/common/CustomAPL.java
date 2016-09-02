package com.zsw.testmodel.common;

import android.app.Application;

import com.zsw.rh.manage.OkHttpClientManager;
import com.zsw.rh.manage.RetrofitManager;

/**
 * author  z.sw
 * time  2016/9/2.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class CustomAPL extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClientManager.INSTANCE.setTimeout_connection(20)
                .setTimeout_write(20)
                .setTimeout_read(20)
                .setDebug(true);
        RetrofitManager.getInstance(APIManager.BASEURL);

    }
}
