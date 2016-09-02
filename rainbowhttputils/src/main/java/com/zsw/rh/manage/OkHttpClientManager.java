package com.zsw.rh.manage;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author  z.sw
 * time  2016/9/1.
 * email  zhusw@visionet.com.cn
 * Description- okHttpClientManager 提供单例，和配置设置
 */
public   enum OkHttpClientManager {
    /**
     * 枚举单利-
     * -避免多线程同步问题，但能防止反序列化重新创建新的对象
     * -还不是太清除~
     */
    INSTANCE;

    private boolean isDebug = false;
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
     * -这玩意儿为啥一般不会出现呢~
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



    private final OkHttpClient okHttpClient;

     OkHttpClientManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(this.TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        builder.readTimeout(this.TIMEOUT_READ,TimeUnit.SECONDS);
        builder.writeTimeout(this.TIMEOUT_WRITE,TimeUnit.SECONDS);
        if(this.isDebug){
            HttpLoggingInterceptor   httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient = builder.build();
    }

    public final OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }






}
