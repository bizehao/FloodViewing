package com.bzh.floodview.module.home.homeMap.subDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseDialogFragment;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.mapData.ApiRainMapData;
import com.bzh.floodview.ui.widget.LoadingView;
import com.bzh.floodview.utils.chart.BarChartManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/15 10:11
 */
public class MapRainDialog extends BaseDialogFragment {

    @BindView(R.id.dm_chart)
    BarChart mBarChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例

    @BindView(R.id.dm_rlv)
    RecyclerView mRecyclerView;

    @BindView(R.id.drm_rain_loading)
    LoadingView mLoadingView;

    @BindView(R.id.drm_rain_layout)
    LinearLayout mLinearLayout;

    @BindView(R.id.dm_title)
    TextView mTextView;
    @BindView(R.id.drm_start_time)
    TextView startTime;
    @BindView(R.id.drm_end_time)
    TextView endTime;
    private MapAdapter adapter;
    @Inject
    RetrofitHelper mRetrofitHelper;

    public static MapRainDialog newInstance(String title, String stcd, String stlc, String startTM, String endTM) {
        MapRainDialog mapDialog = new MapRainDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("stcd", stcd);
        bundle.putString("stlc", stlc);
        bundle.putString("startTM", startTM);
        bundle.putString("endTM", endTM);
        mapDialog.setArguments(bundle);
        return mapDialog;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_rain_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLoadingView.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.GONE);
        adapter = new MapAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String stcd = bundle.getString("stcd");
        String stlc = bundle.getString("stlc");
        String startTM = bundle.getString("startTM");
        String endTM = bundle.getString("endTM");
        mTextView.setText(title);
        startTime.setText(startTM);
        endTime.setText(endTM);

        Observable<BaseApi<List<ApiRainMapData>>> observable = mRetrofitHelper.getServer().getStateRain(stcd, startTM, endTM);
        mRetrofitHelper.requestHandler(observable, new RetrofitHelper.callHandler<BaseApi<List<ApiRainMapData>>>() {
            @Override
            public void run(BaseApi<List<ApiRainMapData>> listBaseApi) {
                List<ApiRainMapData> data = listBaseApi.getData();
                adapter.setList(data);
                //设置x轴的数据
                ArrayList<String> xValues = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    xValues.add(data.get(i).getSubscripttime());

                }
                //Float.valueOf(rainTwoLevels.get(i).getTtt())

                //设置y轴的数据
                ArrayList<Float> yValues = new ArrayList<>();
                float val = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDrp().equals("--")) {
                        val = 0;
                    } else {
                        val = Float.valueOf(data.get(i).getDrp());
                    }
                    yValues.add(val);
                }
                BarChartManager barChartManager = new BarChartManager(mBarChart);
                barChartManager.showBarChart(xValues, yValues, "降雨信息", Color.RED);
                mLoadingView.setVisibility(View.INVISIBLE);
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.dm_close)
    public void imgOnClick() {
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
