package com.bzh.floodview;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bzh.floodview.data.AppDatabase;
import com.bzh.floodview.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    public static final String ip = "192.168.1.220:8762/";//222.128.52.101:5225/floodserver/

    public static final String socketIp = "192.168.1.220:8763/";//222.128.52.101:5302/

    //当前会话的cookie
    public static String cookid = "";
    //当前用户的id
    public static int userId = -1;
    //当前用户的id
    public static String username;

    //用户
    private static User user;

    private static String friend; //当前会话的朋友

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 仅在Debug时初始化Timber
         */
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        user = new User();

        AppDatabase.initAppDatabase(this); //初始化数据库

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    //设置当前会话的朋友
    public static void setFriend(String friend) {
        App.friend = friend;
    }

    //获取当前会话的朋友
    public static String getFriend() {
        return friend;
    }

    /**
     * 设置用户信息
     *
     * @param username
     * @param token
     */
    public static void setUser(String username, String token) {
        user.setUsername(username);
        user.setToken(token);
    }

    public static void setLoginSign(boolean loginSign) {
        if (!loginSign) {
            user.setUsername(null);
            user.setToken(null);
            user.setImg(null);
        }
    }

    public static String getUsername() {
        return user.username;
    }

    public static String getToken() {
        return user.token;
    }

    public static String getImg() {
        return user.img;
    }

    public static void setImg(String img) {
        user.img = img;
    }

    private static class User {
        private String username;
        private String img;
        private String token;

        private void setUsername(String username) {
            this.username = username;
        }

        private void setToken(String token) {
            this.token = token;
        }

        public void setImg(String img) {
            this.img = img;
        }

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
