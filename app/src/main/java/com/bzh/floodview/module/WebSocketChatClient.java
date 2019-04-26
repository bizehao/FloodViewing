package com.bzh.floodview.module;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.data.AppDatabase;
import com.bzh.floodview.data.model.MessageInfo;
import com.bzh.floodview.model.Talk;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.utils.AppManager;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static WebSocketChatClient mWebSocketChatClient;

    private static final String TAG = "IndexFragment";

    private MainAttrs mainAttrs;

    private Gson gson;

    private DialogHandler dialogHandler; //会话处理

    private MessageHandler messagehandler; //消息处理

    public WebSocketChatClient(URI serverUri, Gson gson, MainAttrs mainAttrs) {
        super(serverUri);
        this.gson = gson;
        this.mainAttrs = mainAttrs;
    }

    public static WebSocketChatClient InstanceWebSocket(URI serverUri, Gson gson, MainAttrs mainAttrs){
        mWebSocketChatClient = new WebSocketChatClient(serverUri,gson,mainAttrs);
        return mWebSocketChatClient;
    }

    public static WebSocketChatClient getWebSocketInstance(){
        return mWebSocketChatClient;
    }



    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Timber.e("webSocket连接成功");
        Talk talk = new Talk();
        talk.setCode("100");
        talk.setSender(App.getUsername());
        talk.setReceiver("服务器");
        talk.setMessage("连接服务器成功");
        String talkJson = gson.toJson(talk, Talk.class);
        send(talkJson);
    }

    @Override
    public void onMessage(String message) {
        Timber.e(message);
        Talk talk = gson.fromJson(message, Talk.class);
        switch (talk.getCode()) {
            case "200":
                //消息存储到数据库
                MessageInfo messageInfo = new MessageInfo(talk, false);
                AppDatabase database = AppDatabase.getAppDatabase();
                database.messageInfoDao().insert(messageInfo);

                //会话处理
                if (dialogHandler != null) {
                    dialogHandler.handler(talk);
                } else {
                    Timber.d("会话界面未初始化");
                }
                //消息处理
                if (messagehandler != null) {
                    messagehandler.handler(talk);
                } else {
                    Timber.d("消息界面未初始化");
                }
                break;
            case "300":
                //将头像更新数据库中
                /*FriendsInfo friendsInfo = FriendsInfoHandler.selectByUsername(talk.getSender());
                friendsInfo.setHeadportrait(talk.getMessage());
                FriendsInfoHandler.update(friendsInfo);*/
                break;
            case "455":
                Looper.prepare();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Timber.e(Thread.currentThread().getName());
                        mainAttrs.setLoginSign(false);
                        MaterialDialog dialog = new MaterialDialog.Builder(AppManager.getAppManager().currentActivity()).title("警告")
                                .content("账号异常，程序退出，请您重新登录")
                                .positiveText("确认").onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        LoginActivity.open(AppManager.getAppManager().currentActivity());
                                        AppManager.getAppManager().finishAllActivityExceptTop();
                                    }
                                })
                                .cancelable(false)
                                .build();
                        dialog.show();
                    }
                });
                Looper.loop();
                break;
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        //尝试重新连接
        if (mainAttrs.getLoginSign().getValue() != null && mainAttrs.getLoginSign().getValue()) {
            Timber.e("执行重试连接");
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    mWebSocketChatClient.reconnect();
                }
            });
        }else {
            Timber.e("WebSocket关闭成功");
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "WebSocket链接错误");
        Log.d(TAG, "" + ex.getMessage());
    }

    public void setDialogHandler(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    public void setMessagehandler(MessageHandler messagehandler) {
        this.messagehandler = messagehandler;
    }

    //会话处理
    public interface DialogHandler {
        void handler(Talk talk);
    }

    //消息处理
    public interface MessageHandler {
        void handler(Talk talk);
    }

}
