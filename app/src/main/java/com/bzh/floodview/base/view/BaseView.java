package com.bzh.floodview.base.view;

/**
 * view的定义
 */
public interface BaseView {

    /**
     * 展示消息
     * @param message
     */
    void showMessage(String message);

    /**
     * 显示错误
     */
    void showError();

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 结束加载
     */
    void shutDownLoading();

}
