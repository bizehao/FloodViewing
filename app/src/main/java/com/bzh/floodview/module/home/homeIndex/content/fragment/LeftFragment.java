package com.bzh.floodview.module.home.homeIndex.content.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import com.rey.material.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bzh.floodview.R;
import com.bzh.floodview.model.floodsituation.RainInfo;
import com.bzh.floodview.model.floodsituation.RainTwoLevel;
import com.bzh.floodview.model.floodsituation.RiverTwoLevel;
import com.bzh.floodview.model.floodsituation.RsvrTwoLevel;
import com.bzh.floodview.utils.HttpUtil;
import com.bzh.floodview.utils.TimeUtils;
import com.bzh.floodview.utils.Tools;
import com.bzh.floodview.utils.chart.BarChartManager;
import com.bzh.floodview.utils.chart.LineChartManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mingle.widget.LoadingView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bzh.floodview.file.property.Route;

/**
 * 二级左
 */
public class LeftFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {
    private Toolbar contentToolbar;//标题栏
    //日期
    private DatePickerDialog dpd;
    //时间
    private TimePickerDialog tpd;
    //时间框
    private TextView date_begin, date_end;
    //时间上下框的标志 0不做 1上框 2下框
    private int dateSign = 0;
    private View view;
    private String address;
    private String stcd;
    //图表
    LineChart mLineChart;//曲线图
    BarChart mBarChart;//柱状图
    private LineChartManager lineChartManager;
    private String itemTitle;
    //表格1
    private SmartTable smartTable;
    private PopupWindow mPopWindow; //弹框
    private View contentView;
    private Calendar now;
    private String date; //日期
    private String time; //时间
    private TextView fragment_left_datetv;
    private String startTime;
    private String endTime;
    private Button button1, button2, button3, button4, button5;//1,3,6,12,24  按钮
    private Button[] buttons;
    private String start_time, end_time;

    private CheckBox jingjie, baozheng, xunxian, ruku, chuku;

    private float wrz, grz, fsltdz;//警戒水位,保证水位,汛限水位,入库流量,出库流量
    private List<RsvrTwoLevel> inotq; //入库出库数据源

    private RelativeLayout left_notDatas; //没有数据展示
    private LoadingView left_loadingView; //加载过渡动画
    private TextView left_jilu; //记录
    private String nowz, minz, maxz; //当前 最小 最大

    public LeftFragment() {
    }

    public static LeftFragment newInstance(String stcd, String address) {
        LeftFragment leftFragment = new LeftFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemStcd", stcd);
        bundle.putString("itemAddress", address);
        leftFragment.setArguments(bundle);
        return leftFragment;
    }

    public static LeftFragment newInstance(String stcd, String address, String startTime, String endTime) {
        LeftFragment leftFragment = new LeftFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemStcd", stcd);
        bundle.putString("itemAddress", address);
        bundle.putString("startTime", startTime);
        bundle.putString("endTime", endTime);
        leftFragment.setArguments(bundle);
        return leftFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//开启右上角menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        stcd = bundle.getString("itemStcd");
        address = bundle.getString("itemAddress");

        if (address.equals(Route + "rainInfo/rainfalls_one") || address.equals(Route + "rainInfo/rainIntensity_one")) {
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            view = inflater.inflate(R.layout.fragment_left_rain, container, false);

            smartTable = view.findViewById(R.id.smart_table);
            mLineChart = view.findViewById(R.id.lineChart);
            mBarChart = view.findViewById(R.id.barChart);
            left_notDatas = view.findViewById(R.id.left_notDatas);
            left_loadingView = view.findViewById(R.id.left_loadView);

            fragment_left_datetv = view.findViewById(R.id.fragment_left_rain_datetv);
            button1 = view.findViewById(R.id.fragment_left_rain_1);
            button2 = view.findViewById(R.id.fragment_left_rain_2);
            button3 = view.findViewById(R.id.fragment_left_rain_3);
            button4 = view.findViewById(R.id.fragment_left_rain_4);
            button5 = view.findViewById(R.id.fragment_left_rain_5);
            buttons=new Button[]{button1,button2,button3,button4,button5};
            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);
            button5.setOnClickListener(this);
            fragment_left_datetv.setText(String.format("%s至%s", startTime, endTime));
            getHttpsDatas(stcd, startTime, endTime, address, 1);
            buttons[0].setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            view = inflater.inflate(R.layout.fragment_left, container, false);

            smartTable = view.findViewById(R.id.smart_table);
            mLineChart = view.findViewById(R.id.lineChart);
            mBarChart = view.findViewById(R.id.barChart);
            left_notDatas = view.findViewById(R.id.left_notDatas);
            left_loadingView = view.findViewById(R.id.left_loadView);
            left_jilu = view.findViewById(R.id.left_jilu);
            initViews();
            //默认时间
            Calendar now = Calendar.getInstance();
            Date date = new Date();
            now.setTime(date);
            now.set(Calendar.MINUTE,0);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            end_time = sdf.format(date);
            if(hour<8){
                now.add(Calendar.DATE,-1);
            }
            now.set(Calendar.HOUR_OF_DAY, 8);
            now.set(Calendar.MINUTE, 0);
            Date str_date = now.getTime();
            start_time = sdf.format(str_date);
            fragment_left_datetv.setText(String.format("%s至%s", start_time, end_time));
            date_begin.setText(TimeUtils.fromDataTime(start_time));
            date_end.setText(TimeUtils.fromDataTime(end_time));
            getHttpsDatas(stcd, start_time, end_time, address);
        }
        return view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()//格式化输出
                    //.setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式
                    .disableHtmlEscaping() //防止特殊字符出现乱码// ;
                    .create();
            String data = (String) msg.obj;
            //设置数据
            /*WindowManager wm = getActivity().getWindowManager();
            int screenWith = wm.getDefaultDisplay().getWidth();
            System.out.println("=================="+screenWith);*/
            smartTable.getConfig().setShowTableTitle(false).setShowXSequence(false).setShowYSequence(false).setMinTableWidth(1080)
                    .setColumnTitleStyle(new FontStyle(50, Color.parseColor("#030607")))
                    .setColumnTitleBackground(new IBackgroundFormat() {
                        @Override //标题栏背景色
                        public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                            paint.setColor(Color.parseColor("#F13811"));
                            canvas.drawRect(rect, paint);
                        }
                    });
            smartTable.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
                @Override //隔行换色
                public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                    if (cellInfo.row % 2 == 0) {
                        paint.setColor(Color.parseColor("#8EA9B0"));
                        canvas.drawRect(rect, paint);
                    }
                }

                @Override
                public int getTextColor(CellInfo cellInfo) {
                    return 0;
                }
            });
            FontStyle.setDefaultTextSize(DensityUtils.sp2px(getActivity(), 20)); //设置全局字体大小 setColumnTitleStyle
            smartTable.getConfig().setColumnTitleStyle(new FontStyle(60,Color.BLACK));
            switch (msg.what) {
                case 1:
                    if(left_notDatas.getVisibility() == View.VISIBLE){
                        left_notDatas.setVisibility(View.GONE);
                    }
                    if (address.equals(Route + "waterInfo/river_one")) { //河道
                        Tools.setViewVisible(jingjie);
                        Tools.setViewVisible(baozheng);
                        Tools.setViewInVisible(xunxian);
                        Tools.setViewInVisible(ruku);
                        Tools.setViewInVisible(chuku);
                        List<RiverTwoLevel> riverTwoLevels = gson.fromJson(data, new TypeToken<List<RiverTwoLevel>>() {
                        }.getType());
                        if (riverTwoLevels.size() > 0) {
                            wrz = Float.parseFloat(riverTwoLevels.get(0).getWrz());
                            grz = Float.parseFloat(riverTwoLevels.get(0).getGrz());
                        }
                        //left_jilu.setText(riverTwoLevels.get(0).getNowz());
                        nowz = riverTwoLevels.get(0).getNowz();
                        minz = riverTwoLevels.get(0).getMinz();
                        maxz = riverTwoLevels.get(0).getMaxz();
                        left_jilu.setText(nowz);
                        handler.postDelayed(timeRunable,1000);
                        //普通列
                        Column<String> column1 = new Column<>("时间", "ttt");
                        column1.setMinHeight(100);
                        Column<Integer> column2 = new Column<>("水位(m)", "z");
                        Column<String> column3 = new Column<>("流量(m³/s)", "q");
                        //表格数据 datas是需要填充的数据
                        TableData<RiverTwoLevel> tableData = new TableData<>("河道信息", riverTwoLevels, column1, column2, column3);
                        smartTable.setTableData(tableData);
                        //图表
                        lineChartManager = new LineChartManager(mLineChart);
                        lineChartManager.showLineChart(riverTwoLevels, "河道水位信息", getResources().getColor(R.color.blue), "ttt", "z");
                        lineChartManager.addLine(riverTwoLevels, "河道流量信息", getResources().getColor(R.color.red), "q");
                        //设置曲线填充色 以及 MarkerView
                        Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
                        lineChartManager.setChartFillDrawable(drawable);
                        lineChartManager.setMarkerView(getActivity());
                    }
                    if (address.equals(Route + "waterInfo/reservoir_one")) { //水库
                        Tools.setViewInVisible(jingjie);
                        Tools.setViewInVisible(baozheng);
                        Tools.setViewVisible(xunxian);
                        Tools.setViewVisible(ruku);
                        Tools.setViewVisible(chuku);
                        List<RsvrTwoLevel> rsvrTwoLevels = gson.fromJson(data, new TypeToken<List<RsvrTwoLevel>>() {
                        }.getType());
                        if (rsvrTwoLevels.size() > 0) {
                            fsltdz = Float.parseFloat(rsvrTwoLevels.get(0).getFsltdz());
                            inotq = rsvrTwoLevels;
                            System.out.println("++++++++++++++++++");
                            System.out.println(fsltdz);
                        }
                        nowz = rsvrTwoLevels.get(0).getNowz();
                        minz = rsvrTwoLevels.get(0).getMinz();
                        maxz = rsvrTwoLevels.get(0).getMaxz();
                        left_jilu.setText(nowz);
                        handler.postDelayed(timeRunable,1000);
                        //普通列
                        Column<String> column1 = new Column<>("时间", "ttt");
                        column1.setMinHeight(100);
                        Column<Integer> column2 = new Column<>("水位(m)", "rz");
                        Column<String> column3 = new Column<>("入库(m³/s)", "inq");
                        Column<String> column4 = new Column<>("出库(m³/s)", "otq");
                        //表格数据 datas是需要填充的数据
                        TableData<RsvrTwoLevel> tableData = new TableData<>("水库信息", rsvrTwoLevels, column1, column2, column3, column4);
                        smartTable.setTableData(tableData);
                        //图表
                        lineChartManager = new LineChartManager(mLineChart);
                        lineChartManager.showLineChart(rsvrTwoLevels, "水库水位信息", getResources().getColor(R.color.blue), "ttt", "rz");
                        //设置曲线填充色 以及 MarkerView
                        Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
                        lineChartManager.setChartFillDrawable(drawable);
                        lineChartManager.setMarkerView(getActivity());
                    }
                    left_loadingView.setVisibility(View.GONE);
                    break;
                case 0:
                    left_loadingView.setVisibility(View.GONE);//隐藏加载动画
                    System.out.println("无数据");
                    if(left_notDatas.getVisibility() == View.GONE){
                        left_notDatas.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    if(left_notDatas.getVisibility() == View.VISIBLE){
                        left_notDatas.setVisibility(View.GONE);
                    }
                    System.out.println("降水二级");
                    System.out.println(data);
                    List<RainTwoLevel> rainTwoLevels = gson.fromJson(data, new TypeToken<List<RainTwoLevel>>() {
                    }.getType());
                    //普通列
                    Column<String> column1 = new Column<>("时间", "ttt");
                    column1.setMinHeight(100);
                    Column<Integer> column2 = new Column<>("雨量(mm)", "drp");
                    //表格数据 datas是需要填充的数据
                    TableData<RainTwoLevel> tableData = new TableData<>("降雨信息", rainTwoLevels, column1, column2);
                    smartTable.setTableData(tableData);
                    //图表
                    BarChartManager barChartManager = new BarChartManager(mBarChart);
                    //设置x轴的数据
                    ArrayList<String> xValues = new ArrayList<>();
                    for(int i=0; i<rainTwoLevels.size(); i++){
                        xValues.add(rainTwoLevels.get(i).getTtt());//Float.valueOf(rainTwoLevels.get(i).getTtt())
                    }
                    //设置y轴的数据
                    ArrayList<Float> yValues = new ArrayList<>();
                    for(int i=0; i<rainTwoLevels.size(); i++){
                        yValues.add(Float.valueOf(rainTwoLevels.get(i).getDrp()));
                    }
                    barChartManager.showBarChart(xValues,yValues,"降雨信息",Color.RED);
                    left_loadingView.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //表格数据
    public void displayTable() {
        //普通列
        Column<String> column1 = new Column<>("时间", "ttt");
        Column<Integer> column2 = new Column<>("水位(m)", "z");
        Column<String> column3 = new Column<>("流量(m³/s)", "q");
        List<RainInfo> rainInfoList = new ArrayList<>();
        //表格数据 datas是需要填充的数据
        final TableData<RainInfo> tableData = new TableData<>("河道信息", rainInfoList, column1, column2, column3);
        //设置数据
        WindowManager wm = getActivity().getWindowManager();
        int screenWith = wm.getDefaultDisplay().getWidth();
        smartTable.getConfig().setShowTableTitle(false).setShowXSequence(false).setMinTableWidth(screenWith);
        int size = DensityUtils.dp2px(getActivity(), 50);
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size, size, TitleImageDrawFormat.RIGHT, 10) {
            @Override
            protected Context getContext() {
                return getActivity();
            }

            @Override
            protected int getResourceID(Column column) {
                setDirection(TextImageDrawFormat.LEFT);
                return 0;
            }
        });
        smartTable.getConfig().setContentStyle(new FontStyle(20,Color.RED)); //设置全局字体大小)
        smartTable.setTableData(tableData);
    }

    private void initViews() {
        fragment_left_datetv = view.findViewById(R.id.fragment_left_datetv);
        contentToolbar = getActivity().findViewById(R.id.mcontent_toolbar);
        jingjie = view.findViewById(R.id.jingjie);
        baozheng = view.findViewById(R.id.baozheng);
        xunxian = view.findViewById(R.id.xunxian);
        ruku = view.findViewById(R.id.ruku);
        chuku = view.findViewById(R.id.chuku);
        jingjie.setOnCheckedChangeListener(this);
        baozheng.setOnCheckedChangeListener(this);
        xunxian.setOnCheckedChangeListener(this);
        ruku.setOnCheckedChangeListener(this);
        chuku.setOnCheckedChangeListener(this);
        //显示PopupWindow 同时背景变暗
        if (contentView == null) {
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.disply_condition, null, false);//disply_condition
        }
        //处理popWindow 显示内容
        handleLogic(contentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_left_rain_1:
                getHttpsDatas(stcd, startTime, endTime, address, 1);
                setColor(buttons,0);
                break;
            case R.id.fragment_left_rain_2:
                getHttpsDatas(stcd, startTime, endTime, address, 3);
                setColor(buttons,1);
                break;
            case R.id.fragment_left_rain_3:
                getHttpsDatas(stcd, startTime, endTime, address, 6);
                setColor(buttons,2);
                break;
            case R.id.fragment_left_rain_4:
                getHttpsDatas(stcd, startTime, endTime, address, 12);
                setColor(buttons,3);
                break;
            case R.id.fragment_left_rain_5:
                getHttpsDatas(stcd, startTime, endTime, address, 24);
                setColor(buttons,4);
                break;
        }
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     **/
    private void handleLogic(View contentView) {
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.date_begin:
                    dateSign = 1;
                    ejectDate();
                    break;
                case R.id.date_end:
                    dateSign = 2;
                    ejectDate();
                    break;
                case R.id.select:  //点击查询按钮
                    getDatas();
                    break;
                case R.id.cancel:
                    mPopWindow.dismiss();
                    break;
            }
        };
        date_begin = contentView.findViewById(R.id.date_begin);
        date_end = contentView.findViewById(R.id.date_end);
        contentView.findViewById(R.id.date_begin).setOnClickListener(listener);
        contentView.findViewById(R.id.date_end).setOnClickListener(listener);
        contentView.findViewById(R.id.select).setOnClickListener(listener);
        contentView.findViewById(R.id.cancel).setOnClickListener(listener);
    }

    //弹出日期框
    public void ejectDate() {
        now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    LeftFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    LeftFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }
        dpd.setAccentColor(Color.parseColor("#0195ff"));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    //点击确定日期
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "年" + (++monthOfYear) + "月" + dayOfMonth + "日";
        now = Calendar.getInstance();
        if (tpd == null) {
            tpd = TimePickerDialog.newInstance(
                    LeftFragment.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
            );
        } else {
            tpd.initialize(
                    LeftFragment.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
            );
        }
        tpd.setAccentColor(Color.parseColor("#0195ff"));
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    //点击确定时间
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = hourOfDay + "时" + minute + "分";
        if (dateSign == 1) {
            date_begin.setText(date + time);
        } else if (dateSign == 2) {
            date_end.setText(date + time);
        }
    }

    //获取数据
    public void getDatas() {
        System.out.println("------------------------------------------------------------------");
        String showContent = "";
        String dateBegin = date_begin.getText().toString();
        String dateEnd = date_end.getText().toString();
        if (dateBegin.equals("") && dateEnd.equals("")) {
            showContent = "请选择时间"; //暂时
            //initDisplayOpinion();
            //displayTable();
            //mPopWindow.dismiss();
            //ShowToast.showToast(showContent);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            try {
                Date date1 = sdf.parse(dateBegin);
                Date date2 = sdf.parse(dateEnd);
                if (date1.getTime() < date2.getTime()) {
                    //loadingView.setVisibility(View.VISIBLE);//开启加载动画
                    //initDisplayOpinion();
                    start_time = Tools.handleTime(date1);
                    end_time = Tools.handleTime(date2);
                    fragment_left_datetv.setText(String.format("%s至%s", start_time, end_time));
                    getHttpsDatas(stcd, start_time, end_time, address);
                    mPopWindow.dismiss();
                } else {
                    showContent = "开始时间必须小于结束时间";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    //网络请求
    private void getHttpsDatas(String stcd, String beginTime, String endTime, String address) {
        if(left_notDatas.getVisibility() == View.VISIBLE){
            left_notDatas.setVisibility(View.GONE);
        }
        if(left_loadingView.getVisibility() == View.GONE){
            left_loadingView.setVisibility(View.VISIBLE);
        }
        final RequestBody requestBody = new FormBody.Builder()
                .add("stcd", stcd)
                .add("stm", beginTime)
                .add("etm", endTime)
                .build();
        HttpUtil.sendPostOKHttpRequest(address, requestBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("二级查询", "失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    System.out.println(responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    int state = jsonObject.getInt("state");
                    Message message = new Message();
                    if (state == 0) {
                        String data = jsonObject.getString("data");
                        /*Gson gson = new Gson();
                        List<RiverTwoLevel> riverTwoLevels = gson.fromJson(data,new TypeToken<List<RiverTwoLevel>>(){}.getType());*/
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //网络请求(降雨)
    private void getHttpsDatas(String stcd, String beginTime, String endTime, String address, int timeLength) {
        if(left_notDatas.getVisibility() == View.VISIBLE){
            left_notDatas.setVisibility(View.GONE);
        }
        if(left_loadingView.getVisibility() == View.GONE){
            left_loadingView.setVisibility(View.VISIBLE);
        }
        final RequestBody requestBody = new FormBody.Builder()
                .add("stcd", stcd)
                .add("stm", beginTime)
                .add("etm", endTime)
                .add("timelength", String.valueOf(timeLength))
                .build();
        HttpUtil.sendPostOKHttpRequest(address, requestBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("二级查询", "失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    System.out.println(responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    int state = jsonObject.getInt("state");
                    Message message = new Message();
                    if (state == 0) {
                        String data = jsonObject.getString("data");
                        /*Gson gson = new Gson();
                        List<RiverTwoLevel> riverTwoLevels = gson.fromJson(data,new TypeToken<List<RiverTwoLevel>>(){}.getType());*/
                        message.what = 2;
                        message.obj = data;
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //限制线的选择框
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.jingjie:
                if (isChecked) {
                    lineChartManager.setHighLimitLine(wrz, "警戒水位", Color.parseColor("#F50A0A"));
                } else {
                    lineChartManager.removeHighLimitLine();
                }
                break;
            case R.id.baozheng:
                if (isChecked) {
                    lineChartManager.setLowLimitLine(grz, "保证水位", Color.parseColor("#0AF518"));
                } else {
                    lineChartManager.removeLowLimitLine();
                }
                break;
            case R.id.xunxian:
                if (isChecked) {
                    lineChartManager.setInLimitLine(fsltdz, "汛限水位", Color.parseColor("#F50A0A"));
                } else {
                    lineChartManager.removeInLimitLine();
                }
                break;
            case R.id.ruku:
                if (isChecked) {
                    lineChartManager.addLine(inotq, "入库流量", Color.parseColor("#7f1ce2"), "inq");
                } else {
                    lineChartManager.removeInqLine();
                }
                break;
            case R.id.chuku:
                if (isChecked) {
                    lineChartManager.addLine(inotq, "出库流量", Color.parseColor("#ebc312"), "otq");
                } else {
                    lineChartManager.removeOtqLine();
                }
                break;
        }
    }

    /*****************计时器*******************/
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {

            currentSecond = currentSecond + 1;
            if(currentSecond%9 == 0){
                left_jilu.setText(nowz);
            }else if(currentSecond%6 == 0){
                left_jilu.setText(maxz);
            }else if(currentSecond%3 == 0){
                left_jilu.setText(minz);
            }

            if (!isPause) {
                //递归调用本runable对象，实现每隔一秒一次执行任务
                mhandle.postDelayed(this, 1000);
            }
        }
    };
    //计时器
    private Handler mhandle = new Handler();
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数
/*****************计时器*******************/

    public void setColor(Button[] buttons,int btn){
        for(int i=0; i<buttons.length; i++){
            if(i == btn){
                buttons[i].setBackgroundColor(getResources().getColor(R.color.red));
            }else {
                buttons[i].setBackgroundColor(getResources().getColor(R.color.surance_blue));
            }
        }
    }

    //menu 事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_data:
                if (null == mPopWindow || !mPopWindow.isShowing()) {
                    //创建并显示popWindow
                    // 创建PopupWindow对象，其中：
                    // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                    // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                    mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    // 设置PopupWindow的背景
                    mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//
                    // 设置PopupWindow是否能响应外部点击事件
                    mPopWindow.setFocusable(false);
                    mPopWindow.setOutsideTouchable(false);
                    // 设置PopupWindow是否能响应点击事件
                    mPopWindow.setTouchable(true);
                    // 显示PopupWindow，其中：
                    // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                    mPopWindow.showAsDropDown(contentToolbar, 0, 0);
                }
                break;
        }
        return true;
    }

    //添加 menu 菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!(address.equals(Route + "rainInfo/rainfalls_one") || address.equals(Route + "rainInfo/rainIntensity_one"))){
            //inflater.inflate(R.menu.toolbar, menu);
        }
    }

}
