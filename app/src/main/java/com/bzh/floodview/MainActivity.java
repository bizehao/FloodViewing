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
import com.bzh.floodview.module.home.HomeActivity;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.ui.widget.PopupList;
import com.bzh.floodview.utils.TimeUtils;
import com.bzh.floodview.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FancyButton fancyButton;

    private BGABadgeTextView bgaBadgeTextView;
    private List<String> popupMenuItemList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void show() {

    }
}
