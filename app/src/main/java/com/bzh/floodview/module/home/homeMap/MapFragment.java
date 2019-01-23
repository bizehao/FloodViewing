package com.bzh.floodview.module.home.homeMap;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.bumptech.glide.Glide;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.fragment.BaseFragment;
import com.bzh.floodview.data.AppDatabase;
import com.bzh.floodview.data.model.StationInfo;
import com.bzh.floodview.model.mapData.ApiStcd;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRainDialog;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRiverDialog;
import com.bzh.floodview.module.home.homeMap.subDialog.MapRsvrDialog;
import com.bzh.floodview.module.home.homeMap.subFram.RecyclerViewHelper;
import com.bzh.floodview.module.home.homeMap.subFram.SubRainMapFragment;
import com.bzh.floodview.module.home.homeMap.subFram.SubRiverMapFragment;
import com.bzh.floodview.module.home.homeMap.subFram.SubRsvrMapFragment;
import com.bzh.floodview.ui.widget.LoadingView;
import com.rey.material.widget.FloatingActionButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

public class MapFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, OnGetDistricSearchResultListener, MKOfflineMapListener {

    private static final String TAG = "MapFragment";
    @BindView(R.id.mmap)
    MapView mMapView;

    @BindView(R.id.fm_load)
    LoadingView mLoadingView;

    @BindView(R.id.fm_refresh)
    FloatingActionButton actionButton;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingUpPanelLayout;

    @BindView(R.id.fm_tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.fm_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.fm_stedit)
    EditText stEditText;

    @BindView(R.id.fm_enedit)
    EditText enEditText;

    @BindView(R.id.fm_btn)
    TextView mButton;

    @BindView(R.id.fm_page_load)
    LoadingView pageViewLoadingView;

    @Inject
    MapSubViewModle mMapSubViewModle;

    @BindView(R.id.fm_img)
    ImageView mImageView;

    private Calendar now;
    //日期
    private DatePickerDialog dpd;
    //时间
    private TimePickerDialog tpd;
    private String date; //日期
    private String time; //时间
    //时间上下框的标志 0不做 1上框 2下框
    private int dateSign = 0;

    @Inject
    RetrofitHelper retrofitHelper;

    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private DistrictSearch mDistrictSearch;
    private List<ApiStcd.DataBean> beans;
    //地图站点
    private Map<String, Marker> overlayMap = new HashMap<>();
    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.now_location); //当前选中的站点图表
    private Marker oldMarker; //上一个站点
    private BitmapDescriptor oldBitmapDescriptor; //上一个站点图表

    //构建Marker图标
    BitmapDescriptor bitmapRain = BitmapDescriptorFactory.fromResource(R.drawable.rain_sign);
    BitmapDescriptor bitmapRiver = BitmapDescriptorFactory.fromResource(R.drawable.river_sign);
    BitmapDescriptor bitmapRsvr = BitmapDescriptorFactory.fromResource(R.drawable.rsvr_sign);
    BitmapDescriptor bitmapOther = BitmapDescriptorFactory.fromResource(R.drawable.other_sign);
    //构建Marker图标
    BitmapDescriptor bitmapRainBig = BitmapDescriptorFactory.fromResource(R.drawable.rain_sign_big);
    BitmapDescriptor bitmapRiverBig = BitmapDescriptorFactory.fromResource(R.drawable.river_sign_big);
    BitmapDescriptor bitmapRsvrBig = BitmapDescriptorFactory.fromResource(R.drawable.rsvr_sign_big);
    BitmapDescriptor bitmapOtherBig = BitmapDescriptorFactory.fromResource(R.drawable.other_sign_big);

    boolean bigOrSmall = false; //大还是小  true=大  false=小

    @Inject
    public MapFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_map;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView(Bundle savedInstanceState) {

        mLoadingView.setVisibility(View.VISIBLE);
        mMapView.setVisibility(View.GONE);
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(this);
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setBuildingsEnabled(false);
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setOverlookingGesturesEnabled(false); //俯视3D
        mUiSettings.setRotateGesturesEnabled(false); //地图旋转
        initData();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SubRainMapFragment());
        fragmentList.add(new SubRiverMapFragment());
        fragmentList.add(new SubRsvrMapFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("雨情");
        titleList.add("河道");
        titleList.add("水库");

        mViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), fragmentList, titleList));
        mTabLayout.setupWithViewPager(mViewPager);
        mSlidingUpPanelLayout.setScrollableViewHelper(new RecyclerViewHelper());//拖动面板的嵌套recyclerview处理
        setTime(); //设置默认时间

        stEditText.setOnClickListener(v -> {
            dateSign = 1;
            ejectDate();
        });

        enEditText.setOnClickListener(v -> {
            dateSign = 2;
            ejectDate();
        });

        //点击查询信息
        mButton.setOnClickListener(v -> {
            mMapSubViewModle.setLoading(0);
            String stTime = stEditText.getText().toString();
            String enTime = enEditText.getText().toString();
            mMapSubViewModle.getTableInfo(stTime, enTime, getActivity());
        });

        //地图点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String stcd = marker.getExtraInfo().getString("stcd");
                triggerClickEvent(stcd);
                return false;
            }
        });

        //刷新
        actionButton.setOnClickListener(v -> {

            if (oldMarker != null && oldBitmapDescriptor != null) {
                oldMarker.setIcon(oldBitmapDescriptor);
            }

            //mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName("河北").districtName("邢台"));
            LatLng cenpt = new LatLng(37.276, 114.806);
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(cenpt)
                    .zoom(10)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(10));
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);

            for (Map.Entry<String, Marker> markerMap : overlayMap.entrySet()) {
                Marker marker = markerMap.getValue();
                String sttp = marker.getExtraInfo().getString("sttp");
                switch (sttp) {
                    case "PP":
                        if(marker.getIcon() != bitmapRain){
                            marker.setIcon(bitmapRain);
                        }
                        break;
                    case "ZZ":
                        if(marker.getIcon() != bitmapRiver){
                            marker.setIcon(bitmapRiver);
                        }
                        break;
                    case "RR":
                        if(marker.getIcon() != bitmapRsvr){
                            marker.setIcon(bitmapRsvr);
                        }
                        break;
                    default:
                        if(marker.getIcon() != bitmapOther){
                            marker.setIcon(bitmapOther);
                        }
                        break;
                }
            }
        });

        //其他child触发点击事件
        mMapSubViewModle.getStcd().observe(this, s -> {
            triggerClickEvent(s);
        });

        mMapSubViewModle.getLoading().observe(this, integer -> {
            if (integer == 3) {
                pageViewLoadingView.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
            } else if (integer == 0) {
                mViewPager.setVisibility(View.GONE);
                pageViewLoadingView.setVisibility(View.VISIBLE);
            }
        });

        mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) { //坍塌
                    Glide.with(getActivity()).load(R.drawable.ic_expand_less_black_24dp).into(mImageView);
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) { //扩大
                    Glide.with(getActivity()).load(R.drawable.ic_expand_more_black_24dp).into(mImageView);
                }
            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new MapStatusChangeListener() {
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (mapStatus.zoom >= 12.0) {
                    if (!bigOrSmall) {
                        bigOrSmall = !bigOrSmall;
                        for (Map.Entry<String, Marker> markerMap : overlayMap.entrySet()) {
                            Marker marker = markerMap.getValue();
                            String sttp = marker.getExtraInfo().getString("sttp");
                            switch (sttp) {
                                case "PP":
                                    marker.setIcon(bitmapRainBig);
                                    break;
                                case "ZZ":
                                    marker.setIcon(bitmapRiverBig);
                                    break;
                                case "RR":
                                    marker.setIcon(bitmapRsvrBig);
                                    break;
                                default:
                                    marker.setIcon(bitmapOtherBig);
                                    break;
                            }
                        }
                        /*MapStatus mapStatu = new MapStatus.Builder().target();
                        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus()
                        //改变地图状态
                        mBaiduMap.setMapStatus(mMapStatusUpdate);*/
                    }
                } else {
                    if (bigOrSmall) {
                        bigOrSmall = !bigOrSmall;
                        for (Map.Entry<String, Marker> markerMap : overlayMap.entrySet()) {
                            Marker marker = markerMap.getValue();
                            String sttp = marker.getExtraInfo().getString("sttp");
                            switch (sttp) {
                                case "PP":
                                    marker.setIcon(bitmapRain);
                                    break;
                                case "ZZ":
                                    marker.setIcon(bitmapRiver);
                                    break;
                                case "RR":
                                    marker.setIcon(bitmapRsvr);
                                    break;
                                default:
                                    marker.setIcon(bitmapOther);
                                    break;
                            }
                        }
                    }
                }
            }
        });

    }

    //获取地图站点数据
    private void initData() {
        AppDatabase appDatabase = AppDatabase.getAppDatabase();
        List<StationInfo> stationInfos = appDatabase.stationInfoDao().getStations();
        if (stationInfos != null && stationInfos.size() > 0) {
            beans = new ArrayList<>();
            for (StationInfo stationInfo : stationInfos) {
                beans.add(new ApiStcd.DataBean(stationInfo.getStcd(), stationInfo.getStlc(),
                        stationInfo.getSttp(), stationInfo.getLttd(),
                        stationInfo.getLgtd(), stationInfo.getStnm()));
            }
            mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName("河北").districtName("邢台"));
        } else {
            Observable<ApiStcd> observable = retrofitHelper.getServer().getAoordinate();
            retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
                @Override
                public <T> void run(T t) {
                    ApiStcd apiStcd = (ApiStcd) t;
                    beans = apiStcd.getData();
                    List<StationInfo> stationInfos = new ArrayList<>();
                    for (ApiStcd.DataBean bean : beans) {
                        stationInfos.add(new StationInfo(bean.getStcd(), bean.getStlc(),
                                bean.getSttp(), bean.getLttd(),
                                bean.getLgtd(), bean.getStnm()));
                    }
                    appDatabase.stationInfoDao().addStations(stationInfos);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName("河北").districtName("邢台"));
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (mDistrictSearch != null) {
            mDistrictSearch.destroy();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (++monthOfYear) + "-" + dayOfMonth + " ";
        now = Calendar.getInstance();
        if (tpd == null) {
            tpd = TimePickerDialog.newInstance(
                    MapFragment.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
            );
        } else {
            tpd.initialize(
                    MapFragment.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
            );
        }
        tpd.setAccentColor(Color.parseColor("#0195ff"));
        tpd.show(getActivity().getFragmentManager(), getTag());
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = hourOfDay + ":" + minute;
        if (dateSign == 1) {
            stEditText.setText(date + time);
        } else if (dateSign == 2) {
            enEditText.setText(date + time);
        }
    }

    //弹出日期框
    public void ejectDate() {
        now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    MapFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            //dpd.setStyle();
        } else {
            dpd.initialize(
                    MapFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }
        dpd.setAccentColor(Color.parseColor("#0195ff"));
        if (!dpd.isAdded()) {
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        if (calendar.get(Calendar.MINUTE) != 0) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }
        String endTm = dateFormat.format(calendar.getTime());
        enEditText.setText(endTm);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 8) {
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        String startTm = dateFormat.format(calendar.getTime());
        stEditText.setText(startTm);
        mMapSubViewModle.setLoading(0);
        mMapSubViewModle.getTableInfo(startTm, endTm, getActivity());
    }

    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        mBaiduMap.clear();
        if (districtResult == null) {
            return;
        }
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null) {
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines) {
                OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                        .stroke(new Stroke(10, getResources().getColor(R.color.map_line)))
                        .fillColor(getResources().getColor(R.color.map_over)); //覆盖层的颜色
                mBaiduMap.addOverlay(ooPolygon);
                for (LatLng latLng : polyline) {
                    builder.include(latLng);
                }
            }
            if (beans != null) {
                List<OverlayOptions> options = new ArrayList<>();

                for (ApiStcd.DataBean dataBean : beans) {
                    if (dataBean.getLgtd() != null && dataBean.getLttd() != null) {
                        //定义Maker坐标点
                        LatLng point = new LatLng(Double.parseDouble(dataBean.getLttd()), Double.parseDouble(dataBean.getLgtd()));
                        //添加额外信息
                        Bundle bundle = new Bundle();
                        bundle.putString("sttp", dataBean.getSttp());
                        bundle.putString("stcd", dataBean.getStcd());
                        bundle.putString("stlc", dataBean.getStlc());
                        //构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(point).title(dataBean.getStnm()).extraInfo(bundle);
                        switch (dataBean.getSttp()) {
                            case "PP":
                                ((MarkerOptions) option).icon(bitmapRain);
                                break;
                            case "ZZ":
                                ((MarkerOptions) option).icon(bitmapRiver);
                                break;
                            case "RR":
                                ((MarkerOptions) option).icon(bitmapRsvr);
                                break;
                            default:
                                ((MarkerOptions) option).icon(bitmapOther);
                                break;
                        }
                        options.add(option);
                    }
                }
                //在地图上添加Marker，并显示
                List<Overlay> addOverlays = mBaiduMap.addOverlays(options);

                for (Overlay overlay : addOverlays) {
                    overlayMap.put(overlay.getExtraInfo().getString("stcd"), ((Marker) overlay));
                }

                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                LatLng cenpt = new LatLng(37.276, 114.806);
                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(cenpt)
                        .zoom(10)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMapView.setVisibility(View.VISIBLE);
                        mLoadingView.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        }
    }

    //触发点击事件
    private void triggerClickEvent(String stcd) {
        if (oldMarker != null && oldBitmapDescriptor != null) {
            oldMarker.setIcon(oldBitmapDescriptor);
        }
        if (overlayMap.containsKey(stcd)) {
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED); //面板缩回去
            Marker marker = overlayMap.get(stcd);
            oldMarker = marker; //给上一个赋值
            oldBitmapDescriptor = marker.getIcon(); //给上一个赋值
            marker.setIcon(bitmap);
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(marker.getPosition())
                    .zoom(13)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);

            String sign = marker.getExtraInfo().getString("sttp");
            String stlc = marker.getExtraInfo().getString("stlc");
            String startTM = stEditText.getText().toString();
            String endTM = enEditText.getText().toString();
            String stnm = marker.getTitle();
            switch (sign) {
                case "PP": //雨量站
                    MapRainDialog.newInstance(stnm, stcd, stlc, startTM, endTM).show(getChildFragmentManager(), getTag());
                    break;
                case "ZZ": //河道水位水文站
                    MapRiverDialog.newInstance(stnm, stcd, stlc, startTM, endTM).show(getChildFragmentManager(), getTag());
                    break;
                default:  //水库水文站"RR":
                    MapRsvrDialog.newInstance(stnm, stcd, stlc, startTM, endTM).show(getChildFragmentManager(), getTag());
                    break;
            }
        }
    }

    private void offlineMap() {
        MKOfflineMap mOffline = new MKOfflineMap();
        mOffline.init(this);
        mOffline.start(266);
    }

    @Override
    public void onGetOfflineMapState(int i, int i1) {
    }
}
