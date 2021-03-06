package com.bzh.floodview.module.home.homeMap.subDialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseDialogFragment;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.mapData.ApiRiverMapData;
import com.bzh.floodview.utils.chart.LineChartManager;
import com.bzh.floodview.utils.chart.LineChartMarkView;
import com.github.mikephil.charting.charts.LineChart;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description: 河道信息弹框
 * @time 2018/11/15 10:11
 */
public class MapRiverDialog extends BaseDialogFragment {

    @BindView(R.id.drm_stnm)
    TextView tx_stnm; //站名
    @BindView(R.id.drm_address)
    TextView tx_address; //站址
    @BindView(R.id.drm_data_time)
    TextView tx_dataTime; //数据时间
    @BindView(R.id.drm_z)
    TextView tx_z; //水位
    @BindView(R.id.drm_q)
    TextView tx_q; //流量
    @BindView(R.id.drm_wrz)
    TextView tx_wrz; //警戒水位
    @BindView(R.id.drm_chaochu)
    TextView tx_chaochu; //超警戒水位
    @BindView(R.id.drm_chart)
    LineChart mLineChart;


    @Inject
    RetrofitHelper mRetrofitHelper;

    public static MapRiverDialog newInstance(String title, String stcd, String stlc, String startTM, String endTM) {
        MapRiverDialog mapDialog = new MapRiverDialog();
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
        return R.layout.dialog_river_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String stcd = bundle.getString("stcd");
        String stlc = bundle.getString("stlc");
        String startTM = bundle.getString("startTM");
        String endTM = bundle.getString("endTM");
        Timber.e("时间");
        Timber.e(startTM);
        Timber.e(endTM);
        Observable<BaseApi<ApiRiverMapData>> observable = mRetrofitHelper.getServer().getStateRiver(stcd, startTM, endTM);
        mRetrofitHelper.requestHandler(observable,getActivity(), new RetrofitHelper.callHandler<BaseApi<ApiRiverMapData>>() {
            @Override
            public void run(BaseApi<ApiRiverMapData> apiRiverMapDataBaseApi) {
                ApiRiverMapData data = apiRiverMapDataBaseApi.getData();
                Timber.e("数据显示");
                Timber.e(data.toString());
                tx_stnm.setText(title);
                tx_address.setText(stlc);
                if(data.getRiver() != null){
                    tx_dataTime.setText(data.getRiver().getYmdhm());
                    tx_z.setText(String.valueOf(data.getRiver().getZ()));
                    tx_q.setText(String.valueOf(data.getRiver().getQ()));
                    tx_wrz.setText(String.valueOf(data.getRiver().getWrz()));
                    tx_chaochu.setText(String.valueOf(data.getRiver().getChaochu()));
                }
                if(data.getRivertimeList() != null && data.getRivertimeList().size() > 0){
                    //设置x轴的数据
                    ArrayList<String> xValues = new ArrayList<>();
                    for (int i = 0; i < data.getRivertimeList().size(); i++) {
                        xValues.add(data.getRivertimeList().get(i).getSubscripttime()); //Float.valueOf(rainTwoLevels.get(i).getTtt())
                    }
                    //设置y轴的数据
                    List<Float> yValues = new ArrayList<>();
                    List<Float> zValues = new ArrayList<>();
                    float valQ;
                    float valZ;
                    for (int i = 0; i < data.getRivertimeList().size(); i++) {
                        valQ = (float) data.getRivertimeList().get(i).getQ();
                        valZ = (float)data.getRivertimeList().get(i).getZr();
                        yValues.add(valQ);
                        zValues.add(valZ);
                    }
                    List<List<Float>> yLists = new ArrayList<>(); //Y轴
                    yLists.add(yValues);
                    yLists.add(zValues);
                    List<String> tabList = new ArrayList<>(); //曲线名称
                    tabList.add("流量");
                    tabList.add("水位");
                    List<Integer> colorList = new ArrayList<>(); //曲线名称
                    colorList.add(Color.RED);
                    colorList.add(Color.BLUE);
                    LineChartManager barChartManager = new LineChartManager(mLineChart);
                    barChartManager.showMultiNormalLineChart(xValues,yLists,tabList,colorList);
                    //设置MarkerView
                    barChartManager.setMarkerView(getActivity(), new LineChartMarkView.LineValHandler() {
                        @Override
                        public String setXVal(int index) {
                            return data.getRivertimeList().get(index).getYmdhm();
                        }
                    });
                }else {
                    mLineChart.clear();
                    mLineChart.notifyDataSetChanged();
                    mLineChart.setNoDataText("没有数据");
                    mLineChart.setNoDataTextColor(Color.BLACK);;
                    // 记得最后要刷新一下
                    mLineChart.invalidate();
                }

            }

            @Override
            public void handlerError() {
                tx_stnm.setText(title);
                tx_address.setText(stlc);
            }
        });
    }

    @OnClick(R.id.dm_close)
    public void imgOnClick() {
        this.dismiss();
    }

}
