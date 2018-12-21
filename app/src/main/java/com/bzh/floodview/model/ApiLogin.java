package com.bzh.floodview.model;

/**
 * 登陆的实体类
 */
public class ApiLogin {

    /**
     * code : 200
     * data : {"X_Auth_Token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxYXoiLCJpc3MiOiJiaXplaGFvIiwiYXVkIjoibnVsbCIsImlhdCI6MTU0NTI5NDMzMSwiZXhwIjoxNTQ1ODk5MTMxfQ.Lo8MFtZ8s3zAaoX3RFDAM3cqX_zSwGt5QIARzQFusleN3IP212Ph5L64XsLHG6YhyDTwHTOW0NOkn2zsJ34e_A","username":"qaz"}
     * RequestMessage : 登录成功
     * state : true
     */

    private String code;
    private DataBean data;
    private String RequestMessage;
    private boolean state;

    public String getCode() {
        return code;
    }

    public DataBean getData() {
        return data;
    }

    public String getRequestMessage() {
        return RequestMessage;
    }

    public boolean getState() {
        return state;
    }

    public static class DataBean {
        /**
         * X_Auth_Token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxYXoiLCJpc3MiOiJiaXplaGFvIiwiYXVkIjoibnVsbCIsImlhdCI6MTU0NTI5NDMzMSwiZXhwIjoxNTQ1ODk5MTMxfQ.Lo8MFtZ8s3zAaoX3RFDAM3cqX_zSwGt5QIARzQFusleN3IP212Ph5L64XsLHG6YhyDTwHTOW0NOkn2zsJ34e_A
         * username : qaz
         */

        private String X_Auth_Token;
        private String username;

        public String getX_Auth_Token() {
            return X_Auth_Token;
        }

        public String getUsername() {
            return username;
        }
    }

    @Override
    public String toString() {
        return "ApiLogin{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", RequestMessage='" + RequestMessage + '\'' +
                ", state=" + state +
                '}';
    }
}
