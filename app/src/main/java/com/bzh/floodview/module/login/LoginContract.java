package com.bzh.floodview.module.login;

import com.bzh.floodview.base.presenter.BasePresenter;
import com.bzh.floodview.base.view.BaseView;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public interface LoginContract {

    interface View extends BaseView{

        //获取用户名
        String getUsername();

        //获取密码
        String getPassword();

        //跳转到主页
        void goHome();

    }

    interface Presenter extends BasePresenter<LoginContract.View>{

        //登录
        void login();
    }
}
