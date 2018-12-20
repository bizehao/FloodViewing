package com.bzh.floodview.di.module;

import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.module.home.homeMap.MapSubViewModle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author 毕泽浩
 * @Description: 地图相关
 * @time 2018/11/23 10:53
 */
@Module
public abstract class MapModule {

    @Singleton
    @Provides
    static MapSubViewModle mapSubViewModle(RetrofitHelper retrofitHelper){
        return new MapSubViewModle(retrofitHelper);
    }
}
