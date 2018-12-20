package com.bzh.floodview.di.module;

import com.bzh.floodview.module.home.homeMap.subDialog.MapDialog;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRainDialog;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRiverDialog;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRsvrDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/15 10:28
 */
@Module
public abstract class FragmentDialogModule {
    //地图弹框
    @ContributesAndroidInjector
    abstract MapDialog providesMapDialog();
    //地图河道弹框
    @ContributesAndroidInjector
    abstract MapRiverDialog providesMapRiverDialog();
    //地图降雨量弹框
    @ContributesAndroidInjector
    abstract MapRainDialog providesMapRainDialog();
    //地图水库弹框
    @ContributesAndroidInjector
    abstract MapRsvrDialog providesMapRsvrDialog();
}
