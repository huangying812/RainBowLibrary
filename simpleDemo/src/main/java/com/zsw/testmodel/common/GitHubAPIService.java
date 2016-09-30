package com.zsw.testmodel.common;


import com.zsw.testmodel.ui.act.UserBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Retrofit 定义接口 所有的注解使用
 */
public  interface GitHubAPIService {

    @GET(API.GETUSERINFO)
    Call<UserBean> getUser(@Path("user") String user);

    @GET(API.GETUSERINFO)
    Call<ResponseBody> getAll(@Path("user") String user);

    //Rx模式
    @GET(API.GETUSERINFO)
    Observable<UserBean> rxGetUser(@Path("user") String name);

    //Retrofit 注解详解

    //第一类：HTTP请请求方法
    // GET POST PUT   DELETE PATCH HEAD OPTIONS HTTP
    @HTTP(method = "get",path = API.GETUSERINFO)//hasBody 默认为false 可以不写
    Call<UserBean> getUserInfo1(@Path("user") String user);

    /**
     * 简单字符串
     * @param user
     * @return
     */
    @GET(API.GETUSERINFO)
    Call<ResponseBody> getUserInfo4(@Query("user") String user);

    /**
     * 支持数组
     * @param list
     * @return
     */
    @GET(API.GETUSERINFO)
    Call<RequestBody> getUuserInfo5(@Query("ids[]") List<Integer> list);


    @GET(API.GETUSERINFO)
    Call<ResponseBody> getUserInfo2(@QueryMap Map<String,String> map);
    /**
     *
     * @param url 如果GET() 没有指定url 则必须在 参数入口内使用{@link Url} 这样会摒弃BaseUrl
     * @return
     */
    @GET
    Call<ResponseBody> getUserInfo3(@Url String url);

    //第二类 标记类
    //1.表单请求 FormUrlEncoded  表单数据 {Content-Type:application/x-www-from-urlencoded}
                //使用 @Field  和 @FieldMap   Map<String,String> 注：value 值不为Strings时会被toString

                 //Multipart 拓展表单数据+File {Content-Type:multipart/from-data}
                //使用 @Part 和 @PartMap Map<String,RequestBody> 注：value 值不为RequestBody 时 会通过Converter

                //Streaming  接受流式响应，不加此注解响应结果会全部加载到内存在被model读取
                //在返回数据体积非常大时使用 （>1M）
    /**
     * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST(API.GETUSERINFO)
    @FormUrlEncoded                //     KEY            Value
    Call<ResponseBody> fromUrlEncoded1(@Field("user") String name);

    /**
     * Map 的 Key 作为 表单的键
     * @param map
     * @return
     */
    @POST(API.GETUSERINFO)
    @FormUrlEncoded
    Call<ResponseBody> fromUrlEncoded2(@FieldMap Map<String,String> map);

    /**
     * {@link retrofit2.http.Part } 后面支持三种类型 {@link okhttp3.RequestBody} ,{@link okhttp3.MultipartBody.Part} ,任意类型
     * @return
     */
    @POST(API.GETUSERINFO)
    @Multipart
    Call<ResponseBody> getUserInfo3(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     *{@link PartMap} 支持一个Map作为参数，支持{@link RequestBody} 类型，
     * 如果有那其他的类型会被转换，所以map里不能装 MultipartBody.Part,上传文件只能单独用MultipartBody.Part.
     * @param map 普通表单参数
     * @param file 文件
     * @return
     */
    @POST(API.GETUSERINFO)
    @Multipart
    Call<ResponseBody> getUserInfo4(@PartMap Map<String,RequestBody> map,
                                    @Part MultipartBody.Part file);

    /**
     * 发送一个Bean到服务器
     * @param user
     * @return
     */
    @POST(API.GETUSERINFO)
    Call<UserBean> getUserinfo5(@Body UserBean user);










}
