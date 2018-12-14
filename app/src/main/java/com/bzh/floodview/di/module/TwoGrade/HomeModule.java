package com.bzh.floodview.di.module.TwoGrade;

import com.bzh.floodview.di.module.TwoGrade.ThreeGrade.SportModule;
import com.bzh.floodview.module.home.HomeContract;
import com.bzh.floodview.module.home.HomePresenter;
import com.bzh.floodview.module.home.homeIndex.IndexFragment;
import com.bzh.floodview.module.home.homeNews.NewsFragment;
import com.bzh.floodview.module.home.homePlan.TalkFragment;

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
    abstract NewsFragment providesNewsFragment();

    @ContributesAndroidInjector
    abstract TalkFragment providesTalkFragment();

    @ContributesAndroidInjector(modules = SportModule.class)
    abstract IndexFragment providesSportFragment();

    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter presenter);
}
