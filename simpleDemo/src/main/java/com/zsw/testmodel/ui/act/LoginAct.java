package com.zsw.testmodel.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zsw.rainbowlibrary.httputils.factory.TBRequestFactory;
import com.zsw.rainbowlibrary.httputils.TBRequest;
import com.zsw.rainbowlibrary.httputils.factory.TBCallBack;
import com.zsw.rainbowlibrary.utils.L;
import com.zsw.testmodel.R;
import com.zsw.testmodel.base.AbActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                requestGet_RESTFUL();
                break;
            case R.id.sign_up:

                break;
        }
    }

    private Subscriber<UserBean> observer = new Subscriber<UserBean>() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            L.printD(TAG, "onError=");


        }

        @Override
        public void onNext(UserBean userBean) {
            L.printD(TAG, "onNext=" + userBean.getEmail() + "\n" + userBean.getAvatar_url() + "\n"
                    + userBean.getId() + "\n" + userBean.getCompany());
            showSnackbar(userBean.getEmail());
        }
    };

    public void rxRequest() {
        final String userName = loginName.getText().toString().trim();
        subscription = getAPIService()
                .rxGetUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void requestGet_RESTFUL() {
        TBRequest.create()
                .GET("https://api.github.com/users", new String[]{"HarkBen"}, new TBCallBack() {
                    @Override
                    public void onSuccess(int code, String body) {
                        printLogD(code + "");
                        printLogD(body);
                        Gson gson = new Gson();
                        UserBean user= gson.fromJson(body,UserBean.class);
                        showSnackbar(code+"=="+user.getEmail());


                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        printLogD(errorMsg);
                    }
                });
    }

    public void requestGet_Normal() {
        String userName = loginName.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("user", userName);
        TBRequestFactory.getInstance().get("https://api.github.com/", map, new TBCallBack() {
            @Override
            public void onSuccess(int code, String body) {
                printLogD(code + "");
                printLogD(body);


            }

            @Override
            public void onFailed(String errorMsg) {
                printLogD(errorMsg);
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






