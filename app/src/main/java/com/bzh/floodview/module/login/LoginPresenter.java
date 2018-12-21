package com.bzh.floodview.module.login;

import android.content.Context;
import android.widget.Toast;

import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.model.ApiLogin;
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

    public LoginPresenter(RetrofitHelper retrofitHelper,MainAttrs mainAttrs) {
        this.retrofitHelper = retrofitHelper;
        this.mainAttrs = mainAttrs;
    }

    @Override
    public void login() {
        String username = mView.getUsername();
        String password = mView.getPassword();
        if(password.length()<6){
            mView.showMessage("密码不能少于六位");
            return;
        }
        Observable<ApiLogin> observable = retrofitHelper.getServer().login(username, password);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiLogin apiLogin = (ApiLogin) t;
                Timber.e(apiLogin.toString());
                if(apiLogin.getState()){
                    mainAttrs.setLoginSign(true);
                    App.setUser(apiLogin.getData().getUsername(),apiLogin.getData().getX_Auth_Token());
                    mView.showMessage(apiLogin.getRequestMessage());
                    mView.goHome();
                }else {
                    mView.showMessage(apiLogin.getRequestMessage());
                }
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
