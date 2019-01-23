package com.bzh.floodview.module.home.homeMap.subDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseDialogFragment;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.mapData.ApiRsvrMapData;
import com.bzh.floodview.utils.chart.LineChartManager;
import com.bzh.floodview.utils.chart.LineChartMarkView;
import com.github.mikephil.charting.charts.LineChart;

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
public class MapRsvrDialog extends BaseDialogFragment {

    @BindView(R.id.drm_stnm)
    TextView tx_stnm; //站名
    @BindView(R.id.drm_address)
    TextView tx_address; //站址
    @BindView(R.id.drm_data_time)
    TextView tx_dataTime; //数据时间
    @BindView(R.id.drm_rz)
    TextView tx_rz; //水库水位
    @BindView(R.id.drm_roq)
    TextView tx_roq; //出库流量
    @BindView(R.id.drm_riq)
    TextView tx_riq; //入库流量
    @BindView(R.id.drm_imp)
    TextView tx_imp; //蓄水量
    @BindView(R.id.drm_chart)
    LineChart mLineChart;


    @Inject
    RetrofitHelper mRetrofitHelper;

    public static MapRsvrDialog newInstance(String title, String stcd, String stlc, String startTM, String endTM) {
        MapRsvrDialog mapDialog = new MapRsvrDialog();
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
        return R.layout.dialog_rsvr_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String stcd = bundle.getString("stcd");
        String stlc = bundle.getString("stlc");
        String startTM = bundle.getString("startTM");
        String endTM = bundle.getString("endTM");
        Observable<BaseApi<ApiRsvrMapData>> observable = mRetrofitHelper.getServer().getStateRsvr(stcd, startTM, endTM);
        mRetrofitHelper.requestHandler(observable,getActivity(), new RetrofitHelper.callHandler<BaseApi<ApiRsvrMapData>>() {
            @Override
            public void run(BaseApi<ApiRsvrMapData> apiRsvrMapDataBaseApi) {
                ApiRsvrMapData data = apiRsvrMapDataBaseApi.getData();
                Timber.e(data.toString());
                tx_stnm.setText(title);
                tx_address.setText(stlc);
                Timber.e(data.toString());
                if(data.getReservoir() != null){
                    tx_dataTime.setText(data.getReservoir().getTm());
                    tx_rz.setText(String.valueOf(data.getReservoir().getRz()));
                    tx_roq.setText(String.valueOf(data.getReservoir().getOtq()));
                    tx_riq.setText(String.valueOf(data.getReservoir().getInq()));
                    tx_imp.setText(String.valueOf(data.getReservoir().getW()));
                }
                if(data.getReservoirtimeList() != null && data.getReservoirtimeList().size() > 0){
                    //设置x轴的数据
                    ArrayList<String> xValues = new ArrayList<>();
                    for (int i = 0; i < data.getReservoirtimeList().size(); i++) {
                        xValues.add(data.getReservoirtimeList().get(i).getSubscripttime()); //Float.valueOf(rainTwoLevels.get(i).getTtt())
                    }
                    //设置y轴的数据
                    List<Float> yValues = new ArrayList<>();
                    List<Float> zValues = new ArrayList<>();
                    float valQ;
                    float valZ;
                    for (int i = 0; i < data.getReservoirtimeList().size(); i++) {
                        valQ = Float.valueOf(data.getReservoirtimeList().get(i).getRz());
                        valZ = Float.valueOf(data.getReservoirtimeList().get(i).getW());
                        yValues.add(valQ);
                        zValues.add(valZ);
                    }
                    List<List<Float>> yLists = new ArrayList<>(); //Y轴
                    yLists.add(yValues);
                    yLists.add(zValues);
                    List<String> tabList = new ArrayList<>(); //曲线名称
                    tabList.add("库上水位");
                    tabList.add("蓄水量");
                    List<Integer> colorList = new ArrayList<>(); //曲线名称
                    colorList.add(Color.RED);
                    colorList.add(Color.BLUE);
                    LineChartManager barChartManager = new LineChartManager(mLineChart);
                    barChartManager.showMultiNormalLineChart(xValues,yLists,tabList,colorList);
                    //设置MarkerView
                    barChartManager.setMarkerView(getActivity(), new LineChartMarkView.LineValHandler() {
                        @Override
                        public String setXVal(int index) {
                            return data.getReservoirtimeList().get(index).getTm();
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
        Timber.e("点击执行");
        this.dismiss();
    }
}
