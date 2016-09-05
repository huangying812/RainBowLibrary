package com.zsw.testmodel.common;

import android.app.Application;

import com.zsw.rainbowlibrary.httputils.manager.OkHttpClientManager;


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
        OkHttpClientManager.getInstance()
                .setTimeout_connection(20)
                .setTimeout_write(20)
                .setTimeout_read(20)
                .setDebug(true)
                .buildOkHttpClient();

    }
}
