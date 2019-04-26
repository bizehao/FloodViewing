package com.bzh.floodview.di.module;

import com.bzh.floodview.module.home.homeMap.subFram.SubRainMapFragment;
import com.bzh.floodview.module.home.homeMap.subFram.SubRiverMapFragment;
import com.bzh.floodview.module.home.homeMap.subFram.SubRsvrMapFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/20 14:47
 */
@Module
public abstract class FragmentModule {
    //雨情信息表格
    @ContributesAndroidInjector
    abstract SubRainMapFragment providesSubRainMapFragment();
    //河道信息表格
    @ContributesAndroidInjector
    abstract SubRiverMapFragment providesSubRiverMapFragment();
    //水库信息表格
    @ContributesAndroidInjector
    abstract SubRsvrMapFragment providesSubRsvrMapFragment();
}
