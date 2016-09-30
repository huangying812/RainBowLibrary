package com.zsw.rainbowlibrary.httputils;

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
 * 支持 GET / 表单 POST/单文件上传
 * ------
 * author Ben
 * Last_Update - 2016/9/29
 */
public class TBRequest {


    private Map<String,Object> params = null;

    /**
     * 默认创建一个Obj Map
     */
    private TBRequest(){
        params = new HashMap<>();
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

    public JSONObject Map2JSONObject(){
        return new JSONObject(params);
    }

    /**
     * 普通模式
     * @param url
     * @param tBCallBack
     */
    public void GET(String url, TBCallBack tBCallBack){
        if(null == params || params.isEmpty()){
            TBRequestFactory.getInstance().get(url,tBCallBack);
        }else {
            TBRequestFactory.getInstance().get(url, params,tBCallBack);
        }
    }

    /**
     * RESTFUL  模式
     * @param url
     * @param values
     * @param tBCallBack
     */
    public void GET(String url,String[] values, TBCallBack tBCallBack){
        TBRequestFactory.getInstance().get(url, values,tBCallBack);
    }


    // POST

    public void POST(String url,TBCallBack tbCallBack){
        TBRequestFactory.getInstance().postByJson(url,Map2JSONObject(),tbCallBack);
    }

    public void POSTByRequestBody(String url,TBCallBack tbCallBack){
        TBRequestFactory.getInstance().postByRequestBody(url,Map2JSONObject(),tbCallBack);
    }


    /**
     * Content-Type: application/x-www-form-urlencoded
     * @param url
     * @param tBCallBack
     */
    public void requestPost(String url,TBCallBack tBCallBack){
        TBRequestFactory.getInstance().postForm(url,params, tBCallBack);
    }




}
