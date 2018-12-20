package com.bzh.floodview.module.home.homeMap;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/28 23:01
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    private Fragment currentFragment;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    MyFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> title) {
        super(fm);
        this.fragmentList=list;
        this.titleList = title;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.currentFragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
