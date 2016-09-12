package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsw.rainbowlibrary.utils.LOG;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author  z.sw
 * time  2016/8/15.
 * email  zhusw@visionet.com.cn
 * Description-登陆
 */
public class LoginAct extends AbActivity {

    @Bind(R.id.loginName)
    EditText loginName;
    @Bind(R.id.pw)
    EditText pw;

    @Bind(R.id.sign_in)
    Button signIn;
    @Bind(R.id.sign_up)
    TextView signUp;

    @Override
    public void initLayout() {
        removeBaseTitleBar();
        removeStatusBar();
        //这里是登陆页 所以不需要顶部工具栏布局
        loadContentView(R.layout.act_login);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }



    @OnClick({R.id.sign_in, R.id.sign_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                sendHttp();
                break;
            case R.id.sign_up:
                sendHttp();
                break;
        }
    }

    public void login(){
        String userName = loginName.getText().toString().trim();
        getAPIService().getAll(userName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LOG.printD("retrofit","-onResponse code=="+ response.code());
                LOG.printD("retrofit","-onResponse errorBody=="+ response.errorBody());
                LOG.printD("retrofit","-onResponse body=="+ response.body());
                LOG.printD("retrofit","-onResponse isSuccess=="+ response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                LOG.printD("retrofit","-onFailure 请求失败="+t.getMessage());
            }
        });
    }

    public void sendHttp() {
        String userName = loginName.getText().toString().trim()+"";
        getAPIService().getUser(userName).enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                LOG.printD("retrofit", "-onResponse code==" + response.code());
                LOG.printD("retrofit", "-onResponse errorBody==" + response.errorBody());
                LOG.printD("retrofit", "-onResponse body==" + response.body());
                LOG.printD("retrofit", "-onResponse isSuccess==" + response.isSuccessful());
                if(response.code() == 200){
                    LOG.printD("retrofit", "-onResponse UserBean Email==" + response.body().getEmail());
                    showSnackbar(response.code()+"-email="+response.body().getEmail());
                }else{
                    showSnackbar(response.code()+"-失败");
                }

            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                t.printStackTrace();
                LOG.printD("retrofit", "-onFailure 请求失败=" + t.getMessage());

            }
        });

    }
        public void showSnackbar(String msg){
        Snackbar.make(signIn,msg,Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.GRAY)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginAct.this,MainAct.class);
                        startActivity(intent);
                    }
                })
                .show();

         }



    }






