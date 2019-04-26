package com.bzh.floodview.module.home.homeIndex;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseFragment;
import com.bzh.floodview.model.ApiCounty;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.floodsituation.Submenu;
import com.bzh.floodview.ui.adapter.ShowAdapter;
import com.bzh.floodview.utils.DensityUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * 主页 index
 */
public class IndexFragment extends BaseFragment {

    @BindView(R.id.show_recycle)
    RecyclerView recyclerView;

    private List<Submenu> submenus;
    private ShowAdapter showAdapter;

    @Inject
    RetrofitHelper retrofitHelper;

    @Inject
    MainAttrs mainAttrs;

    @Inject
    public IndexFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initDatas();
        initShow();
        Observable<BaseApi<List<ApiCounty>>> observable = retrofitHelper.getServer().getCounty();
        retrofitHelper.requestHandler(observable, getActivity(), new RetrofitHelper.callHandler<BaseApi<List<ApiCounty>>>() {
            @Override
            public void run(BaseApi<List<ApiCounty>> listBaseApi) {
                List<ApiCounty> counties = listBaseApi.getData();
                mainAttrs.setCounties(counties);
            }
        });
    }

    public void initDatas() {
        submenus = new ArrayList<>();
        LinkedHashMap<String, Integer> subMap = new LinkedHashMap<>();//空的
        LinkedHashMap<String, Integer> subMapRa = new LinkedHashMap<>();
        subMapRa.put("降雨信息", R.drawable.rain);
        subMapRa.put("雨强信息", R.drawable.strong_rain);
        LinkedHashMap<String, Integer> subMapxunRv = new LinkedHashMap<>();
        subMapxunRv.put("河道站", R.drawable.river);
        subMapxunRv.put("水库站", R.drawable.rsvr);
        submenus.add(new Submenu("汛情摘要", R.drawable.show_xun, 1, subMap));
        submenus.add(new Submenu("雨情信息", R.drawable.show_rain, 2, subMapRa));
        submenus.add(new Submenu("水情信息", R.drawable.show_water, 2, subMapxunRv));
        submenus.add(new Submenu("巡查影像", R.drawable.show_xuncha, 3, subMap));
        submenus.add(new Submenu("即时通讯", R.drawable.show_phone, 3, subMap));
        submenus.add(new Submenu("气象信息", R.drawable.show_qixiang, 3, subMap));
        submenus.add(new Submenu("水文分析", R.drawable.show_shuiwen, 3, subMap));
        submenus.add(new Submenu("墒情信息", R.drawable.show_diqing, 3, subMap));

    }

    public void initShow() {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                showAdapter = new ShowAdapter(submenus,recyclerView.getHeight() / submenus.size() * 2 - 35);
                recyclerView.setAdapter(showAdapter);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }
}
