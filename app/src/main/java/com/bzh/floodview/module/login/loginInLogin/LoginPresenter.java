package com.bzh.floodview.module.login.loginInLogin;

import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.model.ApiLogin;
import com.bzh.floodview.module.home.HomeActivity;
import com.bzh.floodview.module.login.LoginActivity;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";

    @Inject
    MainAttrs mainAttrs;

    @Inject
    RetrofitHelper retrofitHelper;

    private LoginContract.View mView;

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login() {
        mView.showLoading();
        String username = mView.getUsername();
        String password = mView.getPassword();
        Observable<ApiLogin> observable = retrofitHelper.getServer().login(username, password);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiLogin apiLogin = (ApiLogin) t;
                if(apiLogin.getCode().equals("200")){
                    mainAttrs.setLoginSign(true);
                    App.setUser(apiLogin.getData().getUsername(),apiLogin.getData().getX_Auth_Token());
                    HomeActivity.open(((LoginFragment) mView).getActivity());
                    mView.shutDownLoading();
                    LoginActivity loginActivity = (LoginActivity) ((LoginFragment) mView).getActivity();
                    loginActivity.finish();
                }else {
                    mView.showErrorMsg("登录失败");
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
