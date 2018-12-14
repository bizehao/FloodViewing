package com.bzh.floodview.module.login.loginInRegister;

import com.bzh.floodview.base.presenter.BasePresenter;
import com.bzh.floodview.base.view.BaseView;

import java.util.Map;

public interface RegisterContract {

    interface View extends BaseView{

        //获取所有用户信息
        Map<String, String> getAllUserInfo();



    }

    interface Presenter extends BasePresenter<RegisterContract.View>{

        //注册操作
        void register();
    }
}
