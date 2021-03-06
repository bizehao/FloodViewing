package com.bzh.floodview.module.home.homeChat.talk.model;

import com.bzh.chatkit.commons.models.IUser;

import java.io.Serializable;

/*
 * Created by troy379 on 04.04.17.
 */
public class User implements IUser,Serializable {

    private String id;
    private String name;
    private String avatar;
    private boolean online;

    public User(String id, String name, String avatar, boolean online) { //头像是路径
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", online=" + online +
                '}';
    }
}
