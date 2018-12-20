package com.bzh.floodview.module.home.homeMap.subFram;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bzh.floodview.R;
import com.bzh.floodview.base.fragment.BaseFragment;
import com.bzh.floodview.model.mapData.ApiRsvrTable;
import com.bzh.floodview.module.home.homeMap.MapSubViewModle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/20 14:46
 */
public class SubRsvrMapFragment extends BaseFragment {

    @BindView(R.id.fsm_rlv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fs_rsvrsearch)
    SearchView mSearchView;

    @Inject
    MapSubViewModle mMapSubViewModle;

    private List<ApiRsvrTable.DataBean> listDataBeanAB = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_subrsvrmap;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        SubMapAdapter<ApiRsvrTable.DataBean> adapter = new SubMapAdapter<>(getActivity(),5);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        adapter.setAdapterRun((i, viewHolder) -> {
            viewHolder.mTextView1.setText(String.valueOf(i+1));
            viewHolder.mTextView2.setText(adapter.getList().get(i).getStnm());
            viewHolder.mTextView3.setText(String.valueOf(String.valueOf(adapter.getList().get(i).getRz())));
            viewHolder.mTextView4.setText(String.valueOf(adapter.getList().get(i).getInq()));
            viewHolder.mTextView5.setText(String.valueOf(adapter.getList().get(i).getOtq()));
            viewHolder.mLinearLayout.setOnClickListener(v -> {
                mMapSubViewModle.setStcd(adapter.getList().get(i).getStcd());
            });
        });

        mMapSubViewModle.getRsvrInfo().observeForever(dataBeans -> {
            adapter.setList(dataBeans);
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> searchViewClass = mSearchView.getClass();
                try {
                    Method method = searchViewClass.getDeclaredMethod("onSearchClicked");
                    method.setAccessible(true);
                    method.invoke(mSearchView);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
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
                    for (ApiRsvrTable.DataBean dataBean : mMapSubViewModle.getRsvrInfoAB()) {
                        Matcher matcher = pattern.matcher(dataBean.getStnm());
                        if (matcher.find()) {
                            listDataBeanAB.add(dataBean);
                        }
                    }
                }else {
                    if(mMapSubViewModle.getRsvrInfoAB() != null){
                        listDataBeanAB.addAll(mMapSubViewModle.getRsvrInfoAB());
                    }
                }
                mMapSubViewModle.setRsvrInfo(listDataBeanAB);
                return false;
            }
        });
    }
}
