package com.bzh.floodview.module.home.homeNews.subFram;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bzh.floodview.R;
import com.bzh.floodview.base.fragment.BaseFragment;
import com.bzh.floodview.model.mapData.ApiRainTable;
import com.bzh.floodview.module.home.homeNews.MapSubViewModle;
import com.bzh.floodview.ui.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author 毕泽浩
 * @Description: //降雨量表格展示
 * @time 2018/11/20 14:46
 */
public class SubRainMapFragment extends BaseFragment {

    @BindView(R.id.fsm_rlv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fs_rainsearch)
    SearchView mSearchView;

    @Inject
    MapSubViewModle mMapSubViewModle;

    private List<ApiRainTable.DataBean> listDataBeanAB = new ArrayList<>();
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_subrainmap;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        SubMapAdapter<ApiRainTable.DataBean> adapter = new SubMapAdapter<>(getActivity(), 4);
        adapter.setAdapterRun((i, viewHolder) -> {
            viewHolder.mTextView1.setText(String.valueOf(i + 1));
            viewHolder.mTextView2.setText(adapter.getList().get(i).getStnm());
            viewHolder.mTextView3.setText(adapter.getList().get(i).getAdnm());
            viewHolder.mTextView4.setText(String.valueOf(adapter.getList().get(i).getDyp()));
            viewHolder.mLinearLayout.setOnClickListener(v -> {
                mMapSubViewModle.setStcd(adapter.getList().get(i).getStcd());
            });
        });
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mMapSubViewModle.getRainInfo().observeForever(dataBeans -> {
            adapter.setList(dataBeans);
        });

        //搜索框
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listDataBeanAB.clear();
                String name = s.trim();
                if(!s.equals("")){
                    Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
                    for (ApiRainTable.DataBean dataBean : mMapSubViewModle.getRainInfoAB()) {
                        Matcher matcher = pattern.matcher(dataBean.getStnm());
                        if (matcher.find()) {
                            listDataBeanAB.add(dataBean);
                        }
                    }
                }else {
                    if(mMapSubViewModle.getRainInfoAB() != null){
                        listDataBeanAB.addAll(mMapSubViewModle.getRainInfoAB());
                    }
                }
                mMapSubViewModle.setRainInfo(listDataBeanAB);
                return false;
            }
        });

    }
}
