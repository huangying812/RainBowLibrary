package com.zsw.rainbowlibrary.httputils.manager;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Create on 2016/8/19.
 *
 * @author Ben
 *         Description-
 *         <p>
 *         github  https://github.com/HarkBen
 * @Last_update time - 2016年9月19日14:33:21
 */
public   class OkHttpClientManager {

    private volatile static OkHttpClientManager INSTANCE;
    private  boolean isDebug = true;
    /**
     * 读取超时
     */
    private   int TIMEOUT_READ = 25;
    /**
     * 链接超时
     */
    private   int TIMEOUT_CONNECTION = 25;
    /**
     * 写入超时
     * -这玩意儿为啥一般不会出现
     */
    private   int TIMEOUT_WRITE = 25;

    public OkHttpClientManager setDebug(boolean isDebug){
        this.isDebug = isDebug;
        return  INSTANCE;
    }
    public OkHttpClientManager setTimeout_read(int timeout_read){
            this.TIMEOUT_READ = timeout_read;
        return INSTANCE;
    }
    public OkHttpClientManager setTimeout_connection(int timeout_connection){
        this.TIMEOUT_CONNECTION = timeout_connection;
        return INSTANCE;
    }
    public OkHttpClientManager setTimeout_write(int TIMEOUT_WRITE){
        this.TIMEOUT_WRITE = TIMEOUT_WRITE;
        return INSTANCE;
    }


    private  OkHttpClient okHttpClient;

    public static OkHttpClientManager getInstance(){
        if(null == INSTANCE){
            synchronized (OkHttpClientManager.class){
                if(null == INSTANCE){
                    INSTANCE = new OkHttpClientManager();
                }
            }
        }
        return INSTANCE;
    }

    public final void buildOkHttpClient(Context context){
        if(null == okHttpClient){
            synchronized (OkHttpClientManager.class){
                if(null == okHttpClient){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(this.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
                    builder.readTimeout(this.TIMEOUT_READ,TimeUnit.SECONDS);
                    builder.writeTimeout(this.TIMEOUT_WRITE,TimeUnit.SECONDS);
                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
                    builder.cookieJar(cookieJar);
                    if(isDebug){
                        HttpLoggingInterceptor   httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }

                    okHttpClient = builder.build();
                }
            }
        }
    }

    public final OkHttpClient getOkHttpClient() {
        if(null == okHttpClient){
            try {
                throw  new Exception("uh~. When you initialize  OkHttpClientManager,you didn't call buildOkHttpClient method!");
            } catch (Exception e) {
                Log.e("OkHttpClientManager","uh~. When you initialize  OkHttpClientManager,you didn't call buildOkHttpClient method!");
                e.printStackTrace();
            }
        }
        return okHttpClient;
    }

}
