package com.bzh.floodview.module.content;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.model.floodsituation.Submenu;
import com.bzh.floodview.ui.adapter.ItemAdapter;
import com.bzh.floodview.utils.MapIntent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 子菜单展示
 */
public class ShowitemActivity extends BaseActivity {
    private List<Submenu> submenus = new ArrayList<>();
    private Submenu submenu = null;
    private TextView titleText;
    private Toolbar toolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_showitem;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.showitem_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回样式
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去除默认显示的标题

        Intent intent = getIntent();
        MapIntent sumMapss = (MapIntent) intent.getSerializableExtra("menus");
        LinkedHashMap sumMaps = sumMapss.getMap();
        String title = intent.getStringExtra("title");
        titleText = findViewById(R.id.showitem_title);
        titleText.setText(title);
        //commonTitleBar = findViewById(R.id.showitem_title);
        initSubmenus(sumMaps);
        RecyclerView recyclerView = findViewById(R.id.recycler_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(submenus);
        recyclerView.setAdapter(itemAdapter);
    }

    private void initSubmenus(Map<String,Integer> sumMaps){
        for (Map.Entry<String, Integer> sumMap : sumMaps.entrySet()){
            submenu = new Submenu();
            submenu.setItemName(sumMap.getKey());
            submenu.setImgId(sumMap.getValue());
            submenus.add(submenu);
        }
    }

    //toolbar的返回按钮
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
