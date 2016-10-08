package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsw.rainbowlibrary.httputils.TBRequest;
import com.zsw.rainbowlibrary.httputils.factory.TBCallBack;
import com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;
import com.zsw.testmodel.common.API;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * author  z.sw
 * time  2016/8/15.
 * email  zhusw@visionet.com.cn
 * Description-登陆
 */
public class LoginAct extends AbActivity {
    private final static String TAG = "LoginAct";

    @Bind(R.id.loginName)
    EditText loginName;
    @Bind(R.id.pw)
    EditText pw;

    @Bind(R.id.sign_in)
    Button signIn;
    @Bind(R.id.sign_up)
    TextView signUp;

    //声明一个全局的 订阅协议/ 和生命周期绑定 解除订阅
    private Subscription subscription;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void initLayout() {
        removeTBaseTitleBar();
        removeStatusBar();
        loadContentView(R.layout.act_login);
        ButterKnife.bind(this);


    }



    @OnClick({R.id.sign_in, R.id.sign_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                    login();
                break;
            case R.id.sign_up:
                    logintoBr();
                break;
        }
    }

    private void logintoBr() {
        String url = API.LOGINTOBR;
        TBRequest.create()
                .put("client_flag","android")
                .put("clientMobileVersion","Redmi Note 2")
                .put("locale","zh")
                .put("loginName","huangyw@visionet.com.cn")
                .put("password","111111")
                .postJson(url, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(body);
                            showSnackbar(code+"--"+ json.getString("phoneNumber"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailed(String errorMsg) {

                    }
                });
    }

    void login(){
       String url = API.LOGIN;
       TBRequest.create()
               .put("username","zhusw")
               .put("password","333333")
               .put("client_flag","android")
               .put("model","SCL-AL00")
               .put("locale","zh")
               .postJson(url, new TBCallBack() {
                   @Override
                   public void onSuccess(int code, String body) {
                       JSONObject json = null;
                       try {
                           json = new JSONObject(body);
                           showSnackbar(code+"--"+ json.getString("phoneNumber"));
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }

                   @Override
                   public void onFailed(String errorMsg) {
                       showSnackbar("error=="+errorMsg);
                   }
               });
   }

    public void showSnackbar(String msg) {
        Snackbar.make(signIn, msg, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.GRAY)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginAct.this, MainAct.class);
                        startActivity(intent);
                    }
                })
                .show();

    }


    @Override
    public Class getRuningClass() {
        return getClass();
    }
}






