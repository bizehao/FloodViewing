package com.bzh.floodview.module.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.bzh.floodview.R;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.ui.widget.MyOneLineView;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout root;

    @BindView(R.id.setting_toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("关于我们");
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //icon + 文字 + 箭头
        root.addView(new MyOneLineView(this)
                .init(R.mipmap.ic_launcher, "第一行")
                .setRootPaddingTopBottom(20, 20));
        //icon + 文字 + 文字 + 箭头
        root.addView(new MyOneLineView(this)
                .init(R.mipmap.ic_launcher, "第二行")
                .setRootPaddingTopBottom(20, 20));
        //icon + 文字 + 输入框
        root.addView(new MyOneLineView(this)
                .init(R.mipmap.ic_launcher, "第三行")
                .setRootPaddingTopBottom(20, 20));
    }

    public static void open(Context context){
        context.startActivity(new Intent(context,SettingActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
