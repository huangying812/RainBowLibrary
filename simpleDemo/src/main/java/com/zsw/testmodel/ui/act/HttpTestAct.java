package com.zsw.testmodel.ui.act;

import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zsw.rainbowlibrary.httputils.TBRequest;
import com.zsw.rainbowlibrary.httputils.factory.TBCallBack;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.common.API;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.X;

/**
 * Create on 2016/9/30.
 * github  https://github.com/HarkBen
 * Description:
 * ----提供TBRequest 各个请求的示例
 * 具体请求详情 直接看日志更清楚 接口内容和返回内容
 * 在 日志过滤器 选中debug 输入过滤 rainbow-------
 * author Ben
 * Last_Update - 2016/9/30
 */
public class HttpTestAct extends AbActivity {
    @Bind(R.id.att_log)
    EditText attLog;
    @Bind(R.id.att_restful)
    Button attRestful;
    @Bind(R.id.att_Normal)
    Button attNormal;
    @Bind(R.id.att_post_json)
    Button attPostTxt;
    @Bind(R.id.att_post_file)
    Button attPostFile;
    @Bind(R.id.att_post_txtAndFile)
    Button attPostTxtAndFile;
    @Bind(R.id.att_post_fileList)
    Button attPostFileList;
    @Bind(R.id.att_post_formData)
    Button attPostFormData;

    private static final String GITHUB_RESTFUL = "https://api.github.com/users";



    @Override
    public void initLayout() {
        loadContentView(R.layout.act_http_test);
        ButterKnife.bind(this);
    }

    @Override
    public Class getRuningClass() {
        return getClass();
    }


    @OnClick({R.id.att_restful, R.id.att_post_formData,R.id.att_Normal, R.id.att_post_json, R.id.att_post_file, R.id.att_post_txtAndFile, R.id.att_post_fileList})
    public void onClick(View view) {
        attLog.setText("---------清除日志-------------");
        switch (view.getId()) {
            case R.id.att_restful:
                GETByRestful();
                break;
            case R.id.att_Normal:
                GETByNormal();
                break;
            case R.id.att_post_json:
                POSTByJson();
                break;
            case R.id.att_post_file:
                uploadFile();

                break;
            case R.id.att_post_txtAndFile:

                break;
            case R.id.att_post_fileList:
                uploadFiles();
                break;
            case R.id.att_post_formData:
                    finishAllAct();
                break;
        }
    }

    void showResult(String result) {
        attLog.setText(result);
    }

    /**
     * GET
     * RESTFUL 接口模式调用
     */
    void GETByRestful() {
        TBRequest.create()
                .get(GITHUB_RESTFUL, new String[]{"Harkben"}, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult(errorMsg);
                    }
                });
    }

    void GETByNormal() {
        TBRequest.create()
                .put("user", "HarkBen")
                .get(GITHUB_RESTFUL, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult(errorMsg);

                    }

                });

    }

    void POSTByJson() {
        String url = API.LOGINTOBR;
        TBRequest.create()
                .put("username", "zhusw")
                .put("password", "333333")
                .put("client_flag", "android")
                .put("model", "SCL-AL00")
                .put("locale", "zh")
                .postJson(url, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code + "--" + body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("error==" + errorMsg);
                    }
                });
    }

    void uploadFile(){
        File file = new File(parentPath+"291733413425432.png");
        TBRequest.create()
                .put("name","小二郎")
                .put("arg",34)
                .put("gender","男")
                .postFormDataFile(API.UPLOADFILE, file, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code+"--"+body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("onFailed--"+errorMsg);
                    }
                });
    }

  static  String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separatorChar
            +"tencent/Qtl/find/";

    void uploadFiles(){
        File file1 = new File(parentPath+"291739323217867.png");
        File file2 = new File(parentPath+"291733413425432.png");
        File file3 = new File(parentPath+"222004413632569.png");
        File file4 = new File(parentPath+"291739323217867.png");
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        TBRequest.create()
                .put("name","小二郎他爸")
                .put("arg",54)
                .put("gender","男")
                .postFormDataFiles(API.UPLOADFILE, files, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code+"--"+body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("onFailed--"+errorMsg);
                    }
                });
    }

}
