package com.zsw.rainbowlibrary.httputils.factory;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Create on 2016/10/3.
 * github  https://github.com/HarkBen
 * Description:
 * -----构建参数和选择请求方式
 * 支持： GET 普通/REST FUL
 *       POST 表单/json/文件上传
 * ------
 * author Ben
 * Last_Update - 2016/10/3
 */
public interface RequestInterface {

    void get(String url, TBCallBack tbCallBack);

    void get(String url, String[] values,TBCallBack tbCallBack);

    void get(String url, Map<String, Object> map, TBCallBack tbCallBack);

    void postJson(String url, JSONObject json, TBCallBack tbCallBack);

    void postRequestBody(String url, RequestBody body, TBCallBack tbCallBack);

    void postFormData(String url, Map<String, Object> map, TBCallBack tbCallBack);

    void uploadFile(String url, File file, MediaType contentType,TBCallBack tbCallBack);

    void uploadFiles(String url, List<File> files, MediaType contentType, TBCallBack tbCallBack);

    void uploadPartFiles(String url, List<File> files, MediaType contentType, TBCallBack tbCallBack);
}
