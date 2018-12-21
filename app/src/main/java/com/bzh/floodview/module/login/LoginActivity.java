package com.bzh.floodview.module.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.module.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.login_user_text)
    TextInputEditText loginUserText;

    @BindView(R.id.password_text)
    TextInputEditText passwordText;

    @BindView(R.id.login_but)
    AppCompatButton loginBut;

    @Inject
    MainAttrs mainAttrs;

    @Inject
    RetrofitHelper retrofitHelper;

    LoginContract.Presenter mPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter(retrofitHelper, mainAttrs);
        loginBut.setOnClickListener((View v) -> {
            mPresenter.login();
        });
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getUsername() {
        return loginUserText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordText.getText().toString();
    }

    @Override
    public void goHome() {
        HomeActivity.open(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
