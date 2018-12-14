package com.bzh.floodview.module.content.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.floodview.R;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.floodsituation.IntensityOfRain;
import com.bzh.floodview.utils.DataHolder;
import java.util.List;

/**
 * 雨强标签页
 */
public class IntensityOfRainFragment extends Fragment {

    private String[] mTabTitles = new String[3];
    private Fragment[] mFragmentArrays = new Fragment[3];
    private View rootView;

    public static IntensityOfRainFragment newInstance(ApiRainStInfo.DataBean apiRainSsInfo){
        IntensityOfRainFragment intensityOfRainFragment = new IntensityOfRainFragment();
        DataHolder.getInstance().save("1hours",apiRainSsInfo.get_$1hours());
        DataHolder.getInstance().save("3hours",apiRainSsInfo.get_$3hours());
        DataHolder.getInstance().save("6hours",apiRainSsInfo.get_$6hours());
        return intensityOfRainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_toptab, container, false);
        initViews();
        return rootView;
    }

    public void initViews(){
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout);
        ViewPager viewPager = rootView.findViewById(R.id.tab_viewpager);
        mTabTitles[0] = "1小时最大";
        mTabTitles[1] = "3小时最大";
        mTabTitles[2] = "6小时最大";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = TabIntentOfRainFragment.newInstance(1);
        mFragmentArrays[1] = TabIntentOfRainFragment.newInstance(3);
        mFragmentArrays[2] = TabIntentOfRainFragment.newInstance(6);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentArrays[i];
            }

            @Override
            public int getCount() {
                return mFragmentArrays.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles[position];

            }
        });
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }
}
