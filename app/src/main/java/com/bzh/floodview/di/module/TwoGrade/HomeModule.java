package com.bzh.floodview.di.module.TwoGrade;

import com.bzh.floodview.module.home.HomeContract;
import com.bzh.floodview.module.home.HomePresenter;
import com.bzh.floodview.module.home.homeIndex.IndexFragment;
import com.bzh.floodview.module.home.homeMap.MapFragment;
import com.bzh.floodview.module.home.homeChat.TalkFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/23 9:27
 */
@Module
public abstract class HomeModule {

    @ContributesAndroidInjector
    abstract MapFragment providesNewsFragment();

    @ContributesAndroidInjector
    abstract TalkFragment providesTalkFragment();

    @ContributesAndroidInjector
    abstract IndexFragment providesSportFragment();

    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter presenter);
}
