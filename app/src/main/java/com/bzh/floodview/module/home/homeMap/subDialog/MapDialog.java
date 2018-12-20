package com.bzh.floodview.module.home.homeMap.subDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseDialogFragment;
import com.bzh.floodview.model.mapData.ApiRiverMapData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/15 10:11
 */
public class MapDialog extends BaseDialogFragment {

    @BindView(R.id.dm_chart)
    BarChart mBarChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    @BindView(R.id.dm_rlv)
    RecyclerView mRecyclerView;

    @BindView(R.id.dm_title)
    TextView mTextView;

    @Inject
    RetrofitHelper mRetrofitHelper;

    public static MapDialog newInstance(String title) {
        MapDialog mapDialog = new MapDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        mapDialog.setArguments(bundle);
        return mapDialog;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mTextView.setText(title);
        Observable<ApiRiverMapData> observable = mRetrofitHelper.getServer().getStateRiver("303200", "2017-09-20", "2017-11-09");
        mRetrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRiverMapData data = (ApiRiverMapData) t;
                Timber.e(data.toString());
            }
        });

        initBarChart(mBarChart);
        List<BarEntry> barEntryList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            barEntryList.add(new BarEntry((i + 1) * 2f, (i + 1) * 3f));
        }
        BarDataSet list = new BarDataSet(barEntryList, "测试");
        initBarDataSet(list, getResources().getColor(R.color.red, null));
        BarData lineData = new BarData(list);
        mBarChart.setData(lineData);
        mBarChart.invalidate(); // refresh
        MapAdapter adapter = new MapAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
    }

    @OnClick(R.id.dm_close)
    public void imgOnClick() {
        Timber.e("点击执行");
        this.dismiss();
    }

    /**
     * 初始化BarChart图表
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(true);
        //设置动画效果
        barChart.animateY(1000, Easing.getEasingFunctionFromOption(Easing.EasingOption.Linear));
        barChart.animateX(1000, Easing.getEasingFunctionFromOption(Easing.EasingOption.Linear));
        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
//        barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }


}
