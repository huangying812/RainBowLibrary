package com.zsw.rainbowlibrary.httputils.factory;

import com.zsw.rainbowlibrary.utils.L;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

import static android.os.Build.VERSION_CODES.M;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----真正执行请求的类------
 * author Ben
 * Last_Update - 2016/9/27
 */
public class TBRequestFactory implements RequestInterface{

    private static TBRetrofitService tBRetrofitService;

    private static TBRequestFactory tBRequestFactory;

    private TBRequestFactory(TBRetrofitFactory rm) {
        tBRetrofitService = rm.createService(TBRetrofitService.class);
    }

    /**
     * 保证内部的RetrofitService 单例
     * @param rm
     */
    public static void build(TBRetrofitFactory rm) {
        if (null == tBRequestFactory) {
            synchronized (TBRequestFactory.class) {
                if (null == tBRequestFactory)
                    tBRequestFactory = new TBRequestFactory(rm);
            }
        }
    }

    public static TBRequestFactory getInstance() {
        if (null == tBRequestFactory) {
            //这里选择抛出ANR，切断后续的引用保持清晰
                throw new NullPointerException(TBRequestFactory.class.getPackage()+ ".TBRequestFactory does not build!");
        } else {
            return tBRequestFactory;
        }
    }

    //GET 请求--------------------------------------

    /**
     * 无参
     * @param url
     * @param tBCallBack
     */
    @Override
    public void get(String url, TBCallBack tBCallBack) {
        tBRetrofitService.get(url).enqueue(tBCallBack);
    }

    /**
     * RESTFUL 模式
     * @RequestMapping（/users/{name}/{id}）
     * @param url
     * @param tBCallBack
     * @param values
     */
    @Override
    public void get(String url, String[] values,TBCallBack tBCallBack) {
        if(values == null || values.length== 0){
            get(url,tBCallBack);
        }else{
            String va = "";
                for (String value : values) {
                    va += "/" + value;
                }
                url = url +va;
              get(url,tBCallBack);
        }
    }

    /**
     * 普通模式
     * @RequestMapping（/users/?name=xx&id=xx）
     * @param url
     * @param map
     * @param tBCallBack
     */
    @Override
    public void get(String url, Map<String, Object> map, TBCallBack tBCallBack) {
        tBRetrofitService.get(url, map).enqueue(tBCallBack);
    }


    //POST---------------------------------------------------------------------------

    /**
     * 接口泛型 String
     * @param url
     * @param json
     * @param tbCallBack
     */
    @Override
   public void postJson(String url, JSONObject json, TBCallBack tbCallBack){
        tBRetrofitService.postJson(url,json.toString()).enqueue(tbCallBack);
    }

    /**
     * 接口泛型 RequestBody
     * @param url
     * @param body
     * @param tbCallBack
     */
    @Override
    public void postRequestBody(String url, RequestBody body, TBCallBack tbCallBack){
        tBRetrofitService.post(url,body).enqueue(tbCallBack);
    }


    /**
     * 单纯表单提交
     * Content-Type:application/x-www-form-urlencoded
     * @param url
     * @param map
     * @param tBCallBack
     */
    @Override
    public void postFormData(String url, Map<String, Object> map, TBCallBack tBCallBack) {
        tBRetrofitService.postForm(url, map).enqueue(tBCallBack);
    }


    @Override
    public void uploadFile(String url, File file, MediaType contentType,TBCallBack tBCallBack) {
        if(null == file) throw new NullPointerException("Hi Man!  the file is null!");
        if(file.isDirectory()) throw new NullPointerException("oh Shit! the file is Directory,don't use floder!");
        RequestBody requestBody = RequestBody.create(contentType,file);
//        MultipartBody.Part part = MultipartBody.Part.create(requestBody);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file","xx.jpg",requestBody);
        tBRetrofitService.upLoadFile(url,part).enqueue(tBCallBack);
    }

    @Override
    public void uploadPartFiles(String url, List<File> files, MediaType contentType, TBCallBack tbCallBack) {
        if(null == files ){
            throw  new NullPointerException("files is null!");
        }
        if(files.size() == 0){
            throw new IllegalArgumentException("files size  equal 0,You must  include at least one file!");
        }
        List<MultipartBody.Part> parts = new ArrayList<>();
        for(File file : files){
            RequestBody body = RequestBody.create(contentType,file);
//            parts.add(MultipartBody.Part.create(body));
            parts.add(MultipartBody.Part.createFormData("file",file.getName(),body));
        }
        tBRetrofitService.upLoadFiles(url,parts).enqueue(tbCallBack);
    }

    @Override
    public void uploadFiles(String url, List<File> files, MediaType contentType, TBCallBack tbCallBack) {
        if(null == files ){
          throw  new NullPointerException("files is null!");
        }
        if(files.size() == 0){
           throw new IllegalArgumentException("files size  equal 0,You must  include at least one file!");
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for(File file : files){
            RequestBody body = RequestBody.create(contentType,file);

//            builder.addPart(body);//里面依然是Part.create(body);
            builder.addFormDataPart("file",file.getName(),body);
        }
        builder.addFormDataPart("test","来自天堂的彩虹");
        MultipartBody multipartBody =  builder.build();
        tBRetrofitService.upLoadFiles(url,multipartBody).enqueue(tbCallBack);
    }



    //
//    Map<String, RequestBody> a = new HashMap<>();
//    MediaType textType = MediaType.parse("text/plain");
//    RequestBody name = RequestBody.create(textType, "怪盗kidou");
//    RequestBody age = RequestBody.create(textType, "24");
//    a.put("ss",name);
    //支持单文件
//    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),new File("xx"));
//    MultipartBody.Part part = MultipartBody.Part.create(requestBody);
      ;
}
