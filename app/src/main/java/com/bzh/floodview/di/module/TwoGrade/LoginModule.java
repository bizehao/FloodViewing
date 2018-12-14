package com.bzh.floodview.di.module.TwoGrade;

import com.bzh.floodview.module.login.loginInLogin.LoginContract;
import com.bzh.floodview.module.login.loginInLogin.LoginFragment;
import com.bzh.floodview.module.login.loginInLogin.LoginPresenter;
import com.bzh.floodview.module.login.loginInRegister.RegisterContract;
import com.bzh.floodview.module.login.loginInRegister.RegisterFragment;
import com.bzh.floodview.module.login.loginInRegister.RegisterPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/23 9:30
 */
@Module
public abstract class LoginModule {

    //登录
    @ContributesAndroidInjector
    abstract LoginFragment providesLoginFragment();

    //注册
    @ContributesAndroidInjector
    abstract RegisterFragment providesRegisterFragment();

    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);

    @Binds
    abstract RegisterContract.Presenter registerPresenter(RegisterPresenter presenter);
}
