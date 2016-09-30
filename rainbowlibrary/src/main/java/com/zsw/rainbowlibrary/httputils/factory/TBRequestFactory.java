package com.zsw.rainbowlibrary.httputils.factory;

import com.zsw.rainbowlibrary.utils.L;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.os.Build.VERSION_CODES.M;

/**
 * Create on 2016/9/27.
 * github  https://github.com/HarkBen
 * Description:
 * -----------
 * author Ben
 * Last_Update - 2016/9/27
 */
public class TBRequestFactory {

    private static TBRetrofitService TBRetrofitService;

    private static TBRequestFactory TBRequestFactory;

    private TBRequestFactory(TBRetrofitFactory rm) {
        TBRetrofitService = rm.createService(TBRetrofitService.class);
    }

    /**
     * 保证内部的RetrofitService 单利
     * @param rm
     */
    public static void build(TBRetrofitFactory rm) {
        if (null == TBRequestFactory) {
            synchronized (TBRequestFactory.class) {
                if (null == TBRequestFactory)
                    TBRequestFactory = new TBRequestFactory(rm);
            }
        }
    }

    public static TBRequestFactory getInstance() {
        if (null == TBRequestFactory) {
            try {
                throw new NullPointerException("com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory does not build!");
            } catch (NullPointerException e) {
                L.printE("TBRequestFactory", "com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory does not build!");
                e.printStackTrace();
            }
        } else {
            return TBRequestFactory;
        }
        return null;
    }

    //GET 请求
    public void get(String url, TBCallBack tBCallBack) {
        TBRetrofitService.get(url).enqueue(tBCallBack);
    }

    /**
     * RESTFUL 模式
     * @RequestMapping（/users/{name}/{id}）
     * @param url
     * @param tBCallBack
     * @param values
     */
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
    public void get(String url, Map<String, Object> map, TBCallBack tBCallBack) {
        TBRetrofitService.get(url, map).enqueue(tBCallBack);
    }



    //POST---------------------------------------------------------------------------

    /**
     * 接口泛型 String
     * @param url
     * @param json
     * @param tbCallBack
     */
   public void postByJson(String url, JSONObject json, TBCallBack tbCallBack){
        TBRetrofitService.postJson(url,json.toString()).enqueue(tbCallBack);
    }

    /**
     * 接口泛型 RequestBody
     * @param url
     * @param json
     * @param tbCallBack
     */
    public void postByRequestBody(String url, JSONObject json, TBCallBack tbCallBack){
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());
        TBRetrofitService.postJson(url,body).enqueue(tbCallBack);
    }


    /**
     * 单纯表单提交
     * Content-Type:application/x-www-form-urlencoded
     * @param url
     * @param map
     * @param tBCallBack
     */
    public void postForm(String url, Map<String, Object> map, TBCallBack tBCallBack) {
        TBRetrofitService.postForm(url, map).enqueue(tBCallBack);
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

}
