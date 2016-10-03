package com.zsw.rainbowlibrary.httputils;

import com.zsw.rainbowlibrary.httputils.factory.RequestInterface;
import com.zsw.rainbowlibrary.httputils.factory.TBCallBack;
import com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory;
import com.zsw.rainbowlibrary.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Create on 2016/9/28.
 * github  https://github.com/HarkBen
 * Description:
 * -----构建参数和选择请求方式
 * 支持： GET 普通/REST FUL
 *       POST 表单/json/文件上传
 * ------
 * author Ben
 * Last_Update - 2016/9/29
 */
public class TBRequest {


    private Map<String,Object> params = null;
    private RequestInterface request;

    /**
     * 默认创建一个Obj Map
     * 填充基础参数
     */
    private TBRequest(){
        params = new HashMap<>();
        request = TBRequestFactory.getInstance();
    }

    public static TBRequest create(){
        return new TBRequest();
    }

    public TBRequest put(String key, Object value){
        params.put(key,value);
        return  this;
    }

    public TBRequest setParams(Map<String,Object> params){
        this.params = params;
        return  this;
    }

    public JSONObject map2JSONObject(){
        return new JSONObject(params);
    }

    /**
     * 普通模式
     * 拼接参数直接使用 map即可
     * @param url
     * @param tBCallBack
     */
    public void get(String url, TBCallBack tBCallBack){
        if(null == params || params.isEmpty()){
            request.get(url,tBCallBack);
        }else {
            request.get(url, params,tBCallBack);
        }
    }

    /**
     * RESTFUL  模式
     * @param url
     * @param values
     * @param tBCallBack
     */
    public void get(String url,String[] values, TBCallBack tBCallBack){
        request.get(url, values,tBCallBack);
    }


    // POST

    /**
     * 将map参数转换为 JSON格式提交
     * @param url
     * @param tbCallBack
     */
    public void post(String url,TBCallBack tbCallBack){
        request.postJson(url,map2JSONObject(),tbCallBack);
    }



    public void postRequestBody(String url,RequestBody body,TBCallBack tbCallBack){
        request.postRequestBody(url,body,tbCallBack);
    }


    /**
     * 单纯表单
     * Content-Type: application/x-www-form-urlencoded
     * @param url
     * @param tBCallBack
     */
    public void postFormData(String url,TBCallBack tBCallBack){
        request.postFormData(url,params, tBCallBack);
    }




}
