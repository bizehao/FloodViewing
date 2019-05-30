package com.bzh.floodview.module.setting;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.bzh.floodview.App;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.model.ApiStations;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.ui.dialog.CustomDiaFrag;
import com.bzh.widgets.linkageMenus.LinkBean;
import com.bzh.widgets.linkageMenus.LinkLeftBean;
import com.bzh.widgets.linkageMenus.LinkageMenus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import timber.log.Timber;

public class StationActivity extends BaseActivity {

    @BindView(R.id.fs_link_menus)
    LinkageMenus mLinkageMenus;

    @Inject
    RetrofitHelper retrofitHelper;

    @BindView(R.id.commit_stations)
    FloatingActionButton mCommitStations;

    @BindView(R.id.radio_selected)
    RadioButton radioSelected;

    @BindView(R.id.radio_not_selected)
    RadioButton radioNotSelected;

    @BindView(R.id.radio_all_select)
    RadioButton radioAllSelect;

    private List<LinkLeftBean> list1;
    private List<LinkBean> list2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_station;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Observable<BaseApi<List<ApiStations>>> observable = retrofitHelper.getServer().getAdcdAndStations(1);
        retrofitHelper.requestHandler(observable, this, apiStationsBaseApi -> {
            list1 = new ArrayList<>();
            list2 = new ArrayList<>();
            List<LinkBean.InnerType> llsts;
            List<ApiStations> ApiStations = apiStationsBaseApi.getData();
            for (ApiStations adnmStations : ApiStations) {
                list1.add(new LinkLeftBean(adnmStations.getAdnm()));
                llsts = new ArrayList<>();
                for (ApiStations.StationListBean station : adnmStations.getStationList()) {
                    llsts.add(new LinkBean.InnerType(station.getStnm(), station.getStcd(), station.isWhether()));
                }
                LinkBean linkBean = new LinkBean(adnmStations.getAdnm(), llsts);
                list2.add(linkBean);
            }
            mLinkageMenus.setData(list1).setLinkBean(list2).build();
        });

        mCommitStations.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            for (LinkBean linkBean1 : list2) {
                for (LinkBean.InnerType listBean2 : linkBean1.getInnerTypes()) {
                    if (listBean2.isStatus()) {
                        list.add(listBean2.getInnerTypeId());
                    }
                }
            }

            Observable<BaseApi<String>> observable1 = retrofitHelper.getServer().addUserStcd(App.userId, list);
            retrofitHelper.requestHandler(observable1, this, stringBaseApi -> {
                CustomDiaFrag diaFrag = CustomDiaFrag.newInstance("测试", "添加成功");
                diaFrag.show(getSupportFragmentManager(), "diaFrag");
            });


        });

        radioSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Timber.e("已选中的");
                List<LinkBean> list = new ArrayList<>();
                for (LinkBean bean : list2) {
                    for (LinkBean.InnerType a : bean.getInnerTypes()) {
                        if (a.isStatus()) {
                            a.setShow(true);
                        } else {
                            a.setShow(false);
                        }
                    }
                    list.add(bean);
                }
                mLinkageMenus.refreshRightMenu();
            }
        });
        radioNotSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Timber.e("未选中的");
                List<LinkBean> list = new ArrayList<>();
                for (LinkBean bean : list2) {
                    for (LinkBean.InnerType a : bean.getInnerTypes()) {
                        if (!a.isStatus()) {
                            a.setShow(true);
                        } else {
                            a.setShow(false);
                        }
                    }
                    list.add(bean);
                }
                mLinkageMenus.refreshRightMenu();
            }
        });
        radioAllSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Timber.e("全选的");
                for (LinkBean bean : list2) {
                    for (LinkBean.InnerType a : bean.getInnerTypes()) {
                        a.setShow(true);
                    }
                }
                mLinkageMenus.refreshRightMenu();
            }
        });

    }
}
