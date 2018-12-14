package com.bzh.floodview.base.presenter;

import com.bzh.floodview.base.view.BaseView;

public interface BasePresenter<T extends BaseView> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();
}
