package com.bzh.floodview.di.module;

import com.bzh.floodview.MainActivity;
import com.bzh.floodview.di.module.TwoGrade.FriendsModule;
import com.bzh.floodview.di.module.TwoGrade.HomeModule;
import com.bzh.floodview.module.home.homeIndex.content.ContentActivity;
import com.bzh.floodview.module.home.homeIndex.content.McontentActivity;
import com.bzh.floodview.module.home.homeIndex.content.ShowitemActivity;
import com.bzh.floodview.module.home.HomeActivity;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.module.setting.SettingActivity;
import com.bzh.floodview.module.home.homeChat.talk.talkFriends.FriendsActivity;
import com.bzh.floodview.module.home.homeChat.talk.talkMessage.MessageActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    //主页
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity providesHomeActivity();

    @ContributesAndroidInjector
    abstract LoginActivity providesLoginActivity();

    @ContributesAndroidInjector(modules = FriendsModule.class)
    abstract FriendsActivity providesFriendsActivity();

    @ContributesAndroidInjector
    abstract MessageActivity providesMessageActivity();

    @ContributesAndroidInjector
    abstract MainActivity providesMainActivity();

    @ContributesAndroidInjector
    abstract SettingActivity providesSettingActivity();

    @ContributesAndroidInjector
    abstract ShowitemActivity providesShowitemActivity();

    @ContributesAndroidInjector
    abstract ContentActivity providesContentActivity();

    @ContributesAndroidInjector
    abstract McontentActivity providesMcontentActivity();

    /*@ContributesAndroidInjector
    abstract ShowitemActivity providesShowitemActivity();*/

    @ContributesAndroidInjector
    abstract AboutusActivity providesAboutusActivity();


}
