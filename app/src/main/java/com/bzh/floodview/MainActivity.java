package com.bzh.floodview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.apilibrary.badge.BGABadgeTextView;
import com.bzh.apilibrary.badge.BGABadgeable;
import com.bzh.apilibrary.badge.BGADragDismissDelegate;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.test.Test;
import com.bzh.floodview.module.home.HomeActivity;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.ui.widget.PopupList;
import com.bzh.floodview.utils.TimeUtils;
import com.bzh.floodview.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Inject
    RetrofitHelper helper;

    @BindView(R.id.btn_spotify)
    FancyButton fancyButton;

    private static final String TAG = "MainActivity";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
        /*fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<BaseApi<Test>> observable = helper.getServer().getSay();
                helper.requestHandler(observable, (BaseApi<Test> testBaseApi) -> {
                    Test test = testBaseApi.getData();
                    Timber.e(test.toString());
                });
            }
        });*/
    }
}
