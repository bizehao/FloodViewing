package com.bzh.floodview.module.content;

import com.bzh.floodview.base.presenter.BasePresenter;
import com.bzh.floodview.base.view.BaseView;

public interface McontentContract {

    //视图层
    interface View extends BaseView {

    }

    //中间主持层
    interface Present extends BasePresenter {
        //降雨信息
        void getSecondlevelRiverInfo(String stcd, String start_time, String end_time);

    }
}
