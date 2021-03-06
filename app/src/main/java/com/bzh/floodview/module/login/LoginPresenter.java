package com.bzh.floodview.module.login;

import android.content.Context;
import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.model.ApiLogin;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.utils.AppManager;
import io.reactivex.Observable;

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
        Observable<BaseApi<ApiLogin>> observable = retrofitHelper.getServer().login(username, password);
        retrofitHelper.requestHandler(observable, (Context) mView, new RetrofitHelper.callHandler<BaseApi<ApiLogin>>() {
            @Override
            public void run(BaseApi<ApiLogin> apiLoginDataBaseApi) {
                mView.closeProgress();
                if (apiLoginDataBaseApi.getCode() == 200) {
                    mView.showMessage("登录成功");
                    mView.goHome();
                    mainAttrs.setLoginSign(true);
                    App.userId = apiLoginDataBaseApi.getData().getUserId();
                    App.username = apiLoginDataBaseApi.getData().getUsername();
                    AppManager.getAppManager().finishActivity();
                } else {
                    mView.showMessage("登录失败");
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
