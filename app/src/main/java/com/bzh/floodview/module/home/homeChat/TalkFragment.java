package com.bzh.floodview.module.home.homeChat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.dialogs.DialogsList;
import com.bzh.chatkit.dialogs.DialogsListAdapter;
import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.R;
import com.bzh.floodview.base.fragment.BaseFragment;
import com.bzh.floodview.data.AppDatabase;
import com.bzh.floodview.data.model.FriendsInfo;
import com.bzh.floodview.data.model.MessageInfo;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.module.WebSocketChatClient;
import com.bzh.floodview.module.home.homeChat.talk.model.Dialog;
import com.bzh.floodview.module.home.homeChat.talk.model.Message;
import com.bzh.floodview.module.home.homeChat.talk.model.User;
import com.bzh.floodview.module.home.homeChat.talk.talkMessage.MessageActivity;
import com.bzh.floodview.ui.widget.PageLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TalkFragment extends BaseFragment {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;
    @BindView(R.id.is_online)
    FrameLayout mFrameLayout;

    @Inject
    WebSocketChatClient webSocketChatClient;

    @Inject
    Gson gson;
    @Inject
    MainAttrs mainAttrs;
    @Inject
    SharedPreferences sharedPreferences;
    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private User receiver;
    private Map<String, Dialog> dialogsMap = new LinkedHashMap<>();//当前会话
    Map<String, Dialog> temporaryDialogMap = new LinkedHashMap<>();//临时会话
    //存放当前用户的会话
    private TalkViewModel mTalkViewModel;
    private PageLayout mPageLayout;

    @Inject
    public TalkFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTalkViewModel = ViewModelProviders.of(this).get(TalkViewModel.class);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        online();
    }

    //在线的处理
    public void online() {
        if (mPageLayout != null) {
            mPageLayout.hide();
        }
        imageLoader = new ImageLoader() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                if (url == null) {
                    Glide.with(getActivity()).load(R.mipmap.no_login_user).into(imageView);
                } else {
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] bytes = decoder.decode(url);
                    mBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
                    Glide.with(getActivity()).load(mBitmap).into(imageView);
                }
            }
        };
        dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, SubDialogViewHolder.class, imageLoader);
        readData();
        for (Map.Entry<String, Dialog> dialogEntry : dialogsMap.entrySet()) {
            dialogEntry.getValue().setUnreadCount(0);
            dialogsAdapter.addItem(dialogEntry.getValue());
        }
        mTalkViewModel.getMessageInfos().observe(this, messageInfos -> {
            if (messageInfos.size() > 0) {
                for (int i = 0; i < messageInfos.size(); i++) {
                    addDialog(messageInfos.get(i));
                }
            }
        });
        mTalkViewModel.getMessageInfoLiveData().observe(this, messageInfo -> {
            addDialog(messageInfo);
        });
        mainAttrs.getClearZeroName().observe(this, s -> { //进入对话界面，清空未读消息
            Dialog dialog = dialogsMap.get(s);
            if (dialog != null && dialog.getUnreadCount() != 0) {
                dialog.setUnreadCount(0);
                dialogsAdapter.updateItemById(dialog);
                saveData(dialogsAdapter.getItems());
            }
        });
        mainAttrs.getOwnSendMsg().observe(this, messageInfo -> {
            addDialogOfOwn(messageInfo);
        });

        dialogsAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<Dialog>() {
            @Override
            public void onDialogClick(Dialog dialog) { //点击进入对话
                User user = dialog.getUsers().get(0);
                MessageActivity.open(getActivity(), user);
            }
        });
        dialogsAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener<Dialog>() {
            @Override
            public void onDialogLongClick(Dialog dialog) { //长按对话
                showToast(dialog.getDialogName());
            }
        });
        dialogsAdapter.setOnSlidingMenuClickListener(new DialogsListAdapter.OnSlidingMenuClickListener<Dialog>() {
            @Override
            public void onSetTopClick(Dialog dialog) { //将某一项置顶
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
                saveData(dialogsAdapter.getItems());
            }

            @Override
            public void onDeleteClick(Dialog dialog) { //删除某一项
                AppDatabase.getAppDatabase().messageInfoDao().updateReadSign(dialog.getUsers().get(0).getId(), true);
                dialogsAdapter.deleteById(dialog.getId());
                dialogsMap.remove(dialog.getUsers().get(0).getId());
                saveData(dialogsAdapter.getItems());
            }
        });
        dialogsAdapter.setDragDismissDelegate((Dialog dialog) -> { //拖动事件
            AppDatabase.getAppDatabase().messageInfoDao().updateReadSign(dialog.getUsers().get(0).getId(), true);
            dialog.setUnreadCount(0);
            dialogsAdapter.updateItemById(dialog);
            saveData(dialogsAdapter.getItems());
        });

        dialogsList.setAdapter(dialogsAdapter);

        webSocketChatClient.setDialogHandler(talk -> {
            mTalkViewModel.setMessageInfoLiveData(new MessageInfo(talk, false));
        });
    }

    //添加会话他人发送
    @SuppressWarnings("CheckResult")
    public void addDialog(MessageInfo messageInfo) {
        if (!dialogsMap.containsKey(messageInfo.getSender())) {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                String id = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
                FriendsInfo friendsInfo = AppDatabase.getAppDatabase().friendsInfoDao().findByUsername(messageInfo.getSender());
                receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                ArrayList<User> users = new ArrayList<>();
                users.add(receiver);
                Message message = new Message(messageInfo.getId().toString(), receiver, messageInfo.getMessage(), messageInfo.getTime());
                Dialog dialog;
                if (App.getFriend() != null && App.getFriend().equals(messageInfo.getSender())) {
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 0);
                } else {
                    int count = messageInfo.getCount() == 0 ? 1 : messageInfo.getCount();
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, count);
                }
                dialogsMap.put(friendsInfo.getUsername(), dialog);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.addItem(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
                saveData(dialogsAdapter.getItems());
            });
        } else {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog = dialogsMap.get(messageInfo.getSender());
                if (App.getFriend() != null && App.getFriend().equals(messageInfo.getSender())) {
                    dialog.setUnreadCount(0);
                } else {
                    dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                }
                Message message = dialog.getLastMessage();
                message.setText(messageInfo.getMessage());
                message.setCreatedAt(messageInfo.getTime());
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.updateItemById(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
                saveData(dialogsAdapter.getItems());
            });
        }
    }

    //添加会话自己发送
    @SuppressWarnings("CheckResult")
    public void addDialogOfOwn(MessageInfo messageInfo) {
        if (!dialogsMap.containsKey(messageInfo.getReceiver())) {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                String id = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
                FriendsInfo friendsInfo = AppDatabase.getAppDatabase().friendsInfoDao().findByUsername(messageInfo.getReceiver());
                receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                ArrayList<User> users = new ArrayList<>();
                users.add(receiver);
                Message message = new Message(messageInfo.getId().toString(), receiver, messageInfo.getMessage(), messageInfo.getTime());
                Dialog dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 0);
                dialogsMap.put(friendsInfo.getUsername(), dialog);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.addItem(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
                saveData(dialogsAdapter.getItems());
            });
        } else {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog = dialogsMap.get(messageInfo.getReceiver());
                dialog.setUnreadCount(0);
                Message message = dialog.getLastMessage();
                message.setText(messageInfo.getMessage());
                message.setCreatedAt(messageInfo.getTime());
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.updateItemById(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
                dialogsAdapter.getItems();
                saveData(dialogsAdapter.getItems());
            });
        }
    }

    /**
     * 保存临时数据
     */
    private void saveData(List<Dialog> dialogs) {
        temporaryDialogMap.clear();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Dialog dialog : dialogs) {
            temporaryDialogMap.put(dialog.getUsers().get(0).getId(), dialog);
        }
        String dialogsMapJson = gson.toJson(temporaryDialogMap);
        edit.putString(App.getUsername(), dialogsMapJson);
        edit.apply();
    }

    /**
     * 保存临时数据 取出
     */
    private void readData() {
        String dialogsMapJson = sharedPreferences.getString(App.getUsername(), null);
        if (dialogsMapJson != null) {
            //从本地读取列表信息
            JsonObject obj = new JsonParser().parse(dialogsMapJson).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                dialogsMap.put(entryKey, gson.fromJson(value, Dialog.class));
            }
        }
    }
}
