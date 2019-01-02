package com.bzh.floodview.module.home.homeIndex.content.fragment;

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
import com.bzh.floodview.utils.DataHolder;

/**
 * 汛情信息
 */
public class FloodSituationFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mTabTitles = new String[3];
    private Fragment[] mFragmentArrays = new Fragment[3];
    private View rootView;

    public static FloodSituationFragment newInstance(Object map) {
        Object[] mapObjects = (Object[]) map;
        FloodSituationFragment floodSituationFragment = new FloodSituationFragment();
        DataHolder.getInstance().save("map1", mapObjects[0]);
        DataHolder.getInstance().save("map2", mapObjects[1]);
        DataHolder.getInstance().save("map3", mapObjects[2]);
        return floodSituationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_toptab, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        tabLayout = rootView.findViewById(R.id.tablayout);
        viewPager = rootView.findViewById(R.id.tab_viewpager);
        viewPager.setOffscreenPageLimit(3);
        mTabTitles[0] = "雨情";
        mTabTitles[1] = "河道";
        mTabTitles[2] = "水库";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = TabFloodOfRainFragment.newInstance(0);
        mFragmentArrays[1] = TabFloodOfRainFragment.newInstance(1);
        mFragmentArrays[2] = TabFloodOfRainFragment.newInstance(2);
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }

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
}
