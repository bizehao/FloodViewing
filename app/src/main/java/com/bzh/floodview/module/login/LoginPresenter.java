package com.bzh.floodview.module.login;

import android.content.Context;
import android.widget.Toast;

import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.model.ApiLogin;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.login.ApiLoginData;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";

    MainAttrs mainAttrs;

    RetrofitHelper retrofitHelper;

    private LoginContract.View mView;

    public LoginPresenter(RetrofitHelper retrofitHelper, MainAttrs mainAttrs) {
        this.retrofitHelper = retrofitHelper;
        this.mainAttrs = mainAttrs;
    }

    @Override
    public void login() {
        mView.openProgress();
        String username = mView.getUsername();
        String password = mView.getPassword();
        Observable<BaseApi<ApiLoginData>> observable = retrofitHelper.getServer().login(username, password);
        retrofitHelper.requestHandler(observable, (Context) mView, new RetrofitHelper.callHandler<BaseApi<ApiLoginData>>() {
            @Override
            public void run(BaseApi<ApiLoginData> apiLoginDataBaseApi) {
                mView.closeProgress();
                ApiLoginData apiLogin = apiLoginDataBaseApi.getData();
                Timber.e(apiLogin.toString());
                if (apiLogin.getState()) {
                    mainAttrs.setLoginSign(true);
                    App.setUser(apiLogin.getUsername(), apiLogin.getX_Auth_Token());
                    mView.showMessage((String) apiLoginDataBaseApi.getMessage());
                    mView.goHome();
                } else {
                    mView.showMessage((String) apiLoginDataBaseApi.getMessage());
                }
            }

            @Override
            public void handlerError() {
                mView.closeProgress();
                mView.showMessage("网络异常，请退出重试");
            }
        });
    }

    @Override
    public void takeView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
