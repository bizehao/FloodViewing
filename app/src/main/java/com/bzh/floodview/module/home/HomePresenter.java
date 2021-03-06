package com.bzh.floodview.module.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.data.AppDatabase;
import com.bzh.floodview.data.model.FriendsInfo;
import com.bzh.floodview.model.ApiCommon;
import com.bzh.floodview.model.ApiFriends;
import com.bzh.floodview.model.ApiUserInfo;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.module.WebSocketChatClient;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private HomeContract.View mView;

    @Inject
    RetrofitHelper retrofitHelper;

    @Inject
    MainAttrs mainAttrs;

    private WebSocketChatClient webSocketChatClient = WebSocketChatClient.getWebSocketInstance();

    @Inject
    public HomePresenter() {
    }

    @Override
    public void loadData(String username) {
        Observable<ApiUserInfo> observable = retrofitHelper.getServer().getUserInfo(username);
        retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() { //获取该用户的个人信息
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public <T> void run(T t) {
                ApiUserInfo apiUserInfo = (ApiUserInfo) t;
                if(apiUserInfo.getData().getHeadportrait() != null){
                    //解码
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] bytes = decoder.decode(apiUserInfo.getData().getHeadportrait());
                    Bitmap bitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
                    App.setImg(apiUserInfo.getData().getHeadportrait());
                    mView.setHeadPortrait(bitMap);
                }
                mView.setHeadName(apiUserInfo.getData().getName());
                mView.setHeadMotto(apiUserInfo.getData().getMotto());
            }
        });
        Observable<ApiFriends> friends = retrofitHelper.getServer().getFriends(username);
        retrofitHelper.successHandler(friends, new RetrofitHelper.callBack() { //缓存该用户的好友信息
            @Override
            public <T> void run(T t) {
                new Thread(() -> {
                    ApiFriends apiUserInfo = (ApiFriends) t;
                    //daoSession.getMessageInfoDao().deleteAll();
                    List<ApiFriends.DataBean> list = apiUserInfo.getData();
                    List<FriendsInfo> infos = new ArrayList<>();
                    for (ApiFriends.DataBean item : list) {
                        FriendsInfo info = new FriendsInfo();
                        info.setUsername(item.getUsername());
                        info.setName(item.getName());
                        info.setAddress(item.getAddress());
                        info.setDescript(item.getDescript());
                        info.setHeadportrait(item.getHeadportrait());
                        info.setMotto(item.getMotto());
                        info.setRemarkname(item.getRemarkName());
                        infos.add(info);
                    }
                    AppDatabase.getAppDatabase().friendsInfoDao().insert(infos);
                }).start();
            }
        });
    }

    @Override
    public void loginOut(String username) {
        retrofitHelper.successHandler(retrofitHelper.getServer().loginOut(username), new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiCommon apiCommon = (ApiCommon) t;
                if ((boolean) apiCommon.getData()) {
                    mView.failSettring();
                    mainAttrs.setLoginSign(false);
                    LoginActivity.open((Context) mView);
                }
            }
        });
    }

    @Override
    public void takeView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
