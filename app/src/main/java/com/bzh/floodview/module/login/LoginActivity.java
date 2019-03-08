package com.bzh.floodview.module.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.module.home.HomeActivity;
import com.bzh.floodview.module.WebSocketChatClient;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

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
    WebSocketChatClient webSocket;

    @Inject
    RetrofitHelper retrofitHelper;

    LoginContract.Presenter mPresenter;

    MaterialDialog dialog;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter(retrofitHelper, mainAttrs);
        loginBut.setOnClickListener((View v) -> {

            if (getUsername() == null || getUsername().length() < 6) {
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("用户名至少6位")
                        .positiveText("确定")
                        .show();
                return;
            }
            if (getPassword() == null || getPassword().length() < 6) {
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("密码至少6位")
                        .positiveText("确定")
                        .show();
                return;
            }
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
    public void openProgress() {
        dialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content("正在登陆，请稍等...")
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override
    public void closeProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
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
