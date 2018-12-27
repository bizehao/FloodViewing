package com.bzh.floodview.di.component;

import com.bzh.floodview.App;
import com.bzh.floodview.di.module.ActivityModule;
import com.bzh.floodview.di.module.CommModule;
import com.bzh.floodview.di.module.FragmentDialogModule;
import com.bzh.floodview.di.module.FragmentModule;
import com.bzh.floodview.di.module.MapModule;
import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        CommModule.class,
        ActivityModule.class, FragmentDialogModule.class,
        FragmentModule.class,MapModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

}
