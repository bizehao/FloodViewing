package com.bzh.floodview.model;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/12/21 13:58
 */
public class BaseApi<T> {

    /**
     * code : 401
     * message : 小弟弟，你没有携带 token 或者 token 无效！
     * data : null
     */

    private int code;
    private Object message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseApi{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
