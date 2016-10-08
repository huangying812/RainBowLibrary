package com.zsw.testmodel.common;

import android.app.Application;

import com.zsw.rainbowlibrary.httputils.factory.TBOkHttpClientFactory;
import com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory;
import com.zsw.rainbowlibrary.httputils.factory.TBRetrofitFactory;
import com.zsw.rainbowlibrary.utils.L;


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
        TBOkHttpClientFactory.Builder.create()
                .setSyncCookie(this)
                .setTimeout_connection(10)
                .setTimeout_read(10)
                .setTimeout_write(10)
                .build();
        TBRequestFactory.build(TBRetrofitFactory.getInstance("https://xx.x.x/"));
        L.setDeBug(true);

    }
}
