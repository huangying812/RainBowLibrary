package com.zsw.testmodel.ui.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zsw.rainbowlibrary.httputils.TBRequest;
import com.zsw.rainbowlibrary.httputils.factory.TBCallBack;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.att_post_txt)
    Button attPostTxt;
    @Bind(R.id.att_post_file)
    Button attPostFile;
    @Bind(R.id.att_post_txtAndFile)
    Button attPostTxtAndFile;
    @Bind(R.id.att_post_fileList)
    Button attPostFileList;

    private static final String URL_RESTFUL = "https://api.github.com/users";

    @Override
    public void initLayout() {
        loadContentView(R.layout.act_http_test);
        ButterKnife.bind(this);
    }

    @Override
    public Class getRuningClass() {
        return getClass();
    }


    @OnClick({R.id.att_restful, R.id.att_Normal, R.id.att_post_txt, R.id.att_post_file, R.id.att_post_txtAndFile, R.id.att_post_fileList})
    public void onClick(View view) {
        attLog.setText("---------清除日志-------------");
        switch (view.getId()) {
            case R.id.att_restful:
                GETByRestful();
                break;
            case R.id.att_Normal:
                GETByNormal();
                break;
            case R.id.att_post_txt:
                POSTByNormal();
                break;
            case R.id.att_post_file:
                break;
            case R.id.att_post_txtAndFile:
                break;
            case R.id.att_post_fileList:
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
                .GET(URL_RESTFUL, new String[]{"Harkben"}, new TBCallBack() {
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
                .GET(URL_RESTFUL, new TBCallBack() {
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

    void POSTByNormal(){
        String url ="http://180.166.66.226:43230/baoshi/mobilelogin";
        TBRequest.create()
                .put("clientMobileVersion","Redmi Note 2")
                .put("loginName","huangyw@visionet.com.cn")
                .put("password","111111")
                .put("client_flag","android")
                .put("model","SCL-AL00")
                .put("locale","zh")
                .POST(url, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        showResult(code+"--"+body);
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        showResult("error=="+errorMsg);
                    }
                });
    }

}
