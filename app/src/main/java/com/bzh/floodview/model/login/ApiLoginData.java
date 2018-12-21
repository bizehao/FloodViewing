package com.bzh.floodview.model.login;

/**
 * @author 毕泽浩
 * @Description: 登录实体类
 * @time 2018/12/21 15:02
 */
public class ApiLoginData {

    /**
     * X_Auth_Token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTYiLCJpc3MiOiJiaXplaGFvIiwiYXVkIjoibnVsbCIsImlhdCI6MTU0NTM3NTkwNiwiZXhwIjoxNTQ1OTgwNzA2fQ.unhIUcjmNIdZePbAByTNwKuX2Vz8admmJBt7FNXLOppmdQ6OZG-dsFGo3uBIUiSryyZBslK3Dic_IP4tQSJURw
     * state : true
     * username : 123456
     */

    private String X_Auth_Token;
    private boolean state;
    private String username;

    public String getX_Auth_Token() {
        return X_Auth_Token;
    }

    public void setX_Auth_Token(String X_Auth_Token) {
        this.X_Auth_Token = X_Auth_Token;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ApiLoginData{" +
                "X_Auth_Token='" + X_Auth_Token + '\'' +
                ", state=" + state +
                ", username='" + username + '\'' +
                '}';
    }
}
