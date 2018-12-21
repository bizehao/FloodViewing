package com.bzh.floodview.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.bzh.floodview.utils.ToastUtil;
import com.bzh.sofialibrary.Sofia;
import com.bzh.floodview.R;
import com.bzh.floodview.base.view.BaseView;
import com.bzh.floodview.utils.AppManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * 基础 activity
 *
 */
public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView {

    protected Unbinder unBinder;
    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit(); //初始化之前
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            initView(savedInstanceState); //初始化
            initSystemBarTint(); //状态栏
        }
        AppManager appManager = AppManager.getAppManager();
        appManager.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unBinder = ButterKnife.bind(this); //设置 ButterKnife
        ToastUtil.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }
    }

    /**
     * 界面初始化前期准备
     */
    protected void beforeInit() { };

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
     Sofia.with(this)
                .statusBarBackground(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarBackground(ContextCompat.getDrawable(this, R.color.colorNavigation));
    }

    private Toast mToast;

    /**
     * 消息框
     *
     * @param desc
     */
    protected void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void showMessage(String message) {
        //showToast(message);
        ToastUtil.show(message);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void shutDownLoading() {

    }
}
