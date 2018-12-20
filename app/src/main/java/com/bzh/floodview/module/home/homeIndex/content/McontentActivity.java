package com.bzh.floodview.module.home.homeIndex.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bzh.floodview.R;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.file.property;
import com.bzh.floodview.module.home.homeIndex.content.fragment.LeftFragment;
import com.bzh.floodview.module.home.homeIndex.content.fragment.RightFragment;

import java.text.MessageFormat;

public class McontentActivity extends BaseActivity implements McontentContract.View {

    private McontentContract.Present present;
    private String itemTitle; //主标题
    private String viceItemTitle; //副标题
    private String stcd; //站号
    private String address1; //地址1
    private String address2; //地址2
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mTabTitles = new String[2];
    private Fragment[] mFragmentArrays = new Fragment[2];
    private TextView textViewTitle, viceTextViewTitle;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mcontent;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initViews();
        initParams();
    }

    public void initViews() {
        Intent intent = getIntent();
        toolbar = findViewById(R.id.mcontent_toolbar);
        textViewTitle = findViewById(R.id.mcontent_title);
        viceTextViewTitle = findViewById(R.id.vice_mcontent_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true); //显示
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);//去除默认显示的标题
        itemTitle = intent.getStringExtra("itemTitle");
        viceItemTitle = intent.getStringExtra("viceItemTitle");
        stcd = intent.getStringExtra("viceItemStcd");
        address1 = intent.getStringExtra("address1");
        address2 = intent.getStringExtra("address2");
        tabLayout = findViewById(R.id.mcontent_tablayout);
        viewPager = findViewById(R.id.mcontent_viewpager);
        mTabTitles[0] = "过程线";
        mTabTitles[1] = "详细信息";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        if(address1.equals(property.Route+"rainInfo/rainfalls_one")|| address1.equals(property.Route+"rainInfo/rainIntensity_one")){
            String startTime = intent.getStringExtra("start_time");
            String endTime = intent.getStringExtra("end_time");
            mFragmentArrays[0] = LeftFragment.newInstance(stcd, address1, startTime, endTime);
        }else{
            mFragmentArrays[0] = LeftFragment.newInstance(stcd, address1);
        }
        mFragmentArrays[1] = RightFragment.newInstance(stcd, address2);
        PagerAdapter pagerAdapter = new McontentActivity.MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);

        //tablayout之间加竖杠
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

        tabLayout.getTabAt(0).select();
        textViewTitle.setText(MessageFormat.format("{0}过程线", itemTitle));
        viceTextViewTitle.setText(viceItemTitle);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("选中的" + tab.getText());
                textViewTitle.setText(MessageFormat.format("{0}{1}", itemTitle, tab.getText()));
                viceTextViewTitle.setText(viceItemTitle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    //toolbar的返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //添加标题
    public void addMiddleTitle(Context context, CharSequence title, Toolbar toolbar) {

        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(22);
        textView.setTextColor(Color.parseColor("#ffffff"));

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        toolbar.addView(textView, params);

    }

    //适配器
    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }

    //加载右菜单（toolbar）
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }*/

    private void initParams(){
        present = new McontentPresenter(this);
    }

}
