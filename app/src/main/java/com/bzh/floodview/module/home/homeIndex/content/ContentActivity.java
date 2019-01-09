package com.bzh.floodview.module.home.homeIndex.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.draw.MultiLineDrawFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.PageTableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bzh.floodview.R;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.file.property;
import com.bzh.floodview.model.ApiRainInfo;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.ApiRiverInfo;
import com.bzh.floodview.model.ApiRsvrInfo;
import com.bzh.floodview.module.home.homeIndex.content.Adapter.LBAdapter;
import com.bzh.floodview.module.home.homeIndex.content.Adapter.entit.Administrativearea;
import com.bzh.floodview.module.home.homeIndex.content.fragment.FloodSituationFragment;
import com.bzh.floodview.module.home.homeIndex.content.fragment.IntensityOfRainFragment;
import com.bzh.floodview.ui.widget.FreeNumberPicker;
import com.bzh.floodview.utils.DensityUtils;
import com.bzh.floodview.utils.TimeUtils;
import com.bzh.floodview.utils.Tools;
import com.mingle.widget.LoadingView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ContentActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        View.OnClickListener, ContentContract.View {
    //标题栏
    //private CommonTitleBar titleBar;
    //日期
    private DatePickerDialog dpd;
    //时间
    private TimePickerDialog tpd;
    //显示一
    private LinearLayout mContentView;
    //显示二
    private FrameLayout contentView_fragment;
    //表格1
    private SmartTable smartTable;

    private PageTableData tableData;
    private TextView totalPage, currentPage;
    //时间框
    private TextView date_begin, date_end;
    //时间上下框的标志 0不做 1上框 2下框
    private int dateSign = 0;
    private TextView timeText;//时间头
    private PopupWindow mPopWindow;//上面的弹窗
    private View contentView;

    private Calendar now;
    private String date; //日期
    private String time; //时间

    @Inject
    ContentPresenter mPresent;

    private LoadingView loadingView;
    private String title;

    private String secondLevelSelectAddress1; //二级查询的地址
    private String secondLevelSelectAddress2; //二级查询的地址

    private String start_time; //开始时间
    private String end_time; //结束时间
    private LinearLayout contentJump;// 点击弹框 跳转那一页
    private FreeNumberPicker numberPicker; //数字增减框
    private TextView textView_totalpage; //弹框的总页数
    private String xunHead;
    private int xunSign;
    private RelativeLayout notDatas;
    private Toolbar contentToolbar;//标题栏
    private TextView contentTitle, content_jilu;//标题 记录


    private Spinner spinner;

    List<Administrativearea>  areaList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_content;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 获取界面布局文件中的Spinner组件



        contentToolbar = findViewById(R.id.content_toolbar);
        contentTitle = findViewById(R.id.content_title);
        setSupportActionBar(contentToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回样式
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去除默认显示的标题

        Intent intent = getIntent();
        xunSign = intent.getIntExtra("xunSign", 0);
        if (xunSign == 1) {
            title = intent.getStringExtra("title");
        } else {
            title = intent.getStringExtra("childItem");
        }
        contentTitle.setText(title);
        initParams();
        initViews();
        if (xunSign == 1) {
            xunHead = intent.getStringExtra("xunHead");
            start_time = intent.getStringExtra("startTime");
            end_time = intent.getStringExtra("endTime");
            timeText.setText(String.format("%s至%s", start_time, end_time));
            date_begin.setText(TimeUtils.fromDataTime(start_time));
            date_end.setText(TimeUtils.fromDataTime(end_time));
            displayTable(start_time, end_time);
        } else {
            Calendar now = Calendar.getInstance();
            Date date = new Date();
            now.setTime(date);
            now.set(Calendar.MINUTE, 0);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            end_time = sdf.format(date);
            if (hour < 8) {
                now.add(Calendar.DATE, -1);
            }
            now.set(Calendar.HOUR_OF_DAY, 8);
            now.set(Calendar.MINUTE, 0);
            Date str_date = now.getTime();
            start_time = sdf.format(str_date);
            timeText.setText(String.format("%s至%s", start_time, end_time));
            date_begin.setText(TimeUtils.fromDataTime(start_time));
            date_end.setText(TimeUtils.fromDataTime(end_time));
            displayTable(start_time, end_time);
        }
    }

    public void initViews() {
        content_jilu = findViewById(R.id.content_jilu);
        notDatas = findViewById(R.id.content_notDatas);
        timeText = findViewById(R.id.show_time);
        contentJump = findViewById(R.id.content_jump);
        totalPage = findViewById(R.id.content_totalPage);
        currentPage = findViewById(R.id.content_currentPage);
        mContentView = findViewById(R.id.contentView);
        contentView_fragment = findViewById(R.id.contentView_fragment);
        smartTable = findViewById(R.id.content_smart_table);
        //select = findViewById(R.id.select);
        //progress_bar = findViewById(R.id.progress_bar);
        loadingView = findViewById(R.id.loadView);
        contentJump.setOnClickListener(this);
        //弹框
        if (contentView == null) {
            contentView = LayoutInflater.from(this).inflate(R.layout.disply_condition, null, false);//disply_condition
        }
        //处理popWindow 显示内容
        handleLogic(contentView);
        //select.setOnClickListener(this);
        //date_begin.setOnClickListener(this);
        //date_end.setOnClickListener(this);
    }

    //点击确定日期
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "年" + (++monthOfYear) + "月" + dayOfMonth + "日";
        now = Calendar.getInstance();
        if (tpd == null) {
            tpd = TimePickerDialog.newInstance(
                    ContentActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
            );
        } else {
            tpd.initialize(
                    ContentActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
            );
        }
        tpd.setAccentColor(Color.parseColor("#0195ff"));
        tpd.show(getFragmentManager(), "Timepickerdialog");
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

    //弹出日期框
    public void ejectDate() {
        now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    ContentActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            //dpd.setStyle();
        } else {
            dpd.initialize(
                    ContentActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }
        dpd.setAccentColor(Color.parseColor("#0195ff"));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    //表格数据
    public void displayTable(final String start_time, final String end_time) {
        if (notDatas.getVisibility() == View.VISIBLE) {//无数据
            notDatas.setVisibility(View.GONE);
        }
        if (loadingView.getVisibility() == View.GONE) {//开启加载动画
            loadingView.setVisibility(View.VISIBLE);
        }
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Map<String, String> map;
                switch (msg.what) {
                    case 0://没有数据的情况
                        if (mContentView.getVisibility() == View.VISIBLE) {
                            mContentView.setVisibility(View.GONE);
                        }
                        if (contentView_fragment.getVisibility() == View.VISIBLE) {
                            contentView_fragment.setVisibility(View.GONE);
                        }
                        loadingView.setVisibility(View.GONE);//隐藏加载动画
                        if (notDatas.getVisibility() == View.GONE) {//无数据
                            notDatas.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 3://汛情
                        if (notDatas.getVisibility() == View.VISIBLE) {
                            notDatas.setVisibility(View.GONE);
                        }
                        if (mContentView.getVisibility() == View.VISIBLE) {
                            mContentView.setVisibility(View.GONE);
                        }
                        if (contentView_fragment.getVisibility() == View.GONE) {
                            contentView_fragment.setVisibility(View.VISIBLE);
                        }
                        Object maps = msg.obj;
                        transaction.replace(R.id.contentView_fragment, new FloodSituationFragment().newInstance(maps));
                        transaction.commit();
                        loadingView.setVisibility(View.INVISIBLE);//隐藏加载动画
                        break;
                }
            }
        };
        switch (title) {
            case "降雨信息":
                if (xunSign == 1) {
                    mPresent.getRainfallInfoXun(xunHead, start_time, end_time, handler);
                } else {
                    mPresent.getRainfallInfo(start_time, end_time);
                }
                secondLevelSelectAddress1 = property.Route + "rainInfo/rainfalls_one";
                secondLevelSelectAddress2 = property.Route + "rainInfo/rain_detailed";
                break;
            case "雨强信息":
                mPresent.getIntensityOfRainInfo(start_time, end_time);
                break;
            case "河道站":
                mPresent.getRiverInfo(start_time, end_time);
                secondLevelSelectAddress1 = property.Route + "waterInfo/river_one";
                secondLevelSelectAddress2 = property.Route + "waterInfo/river_detailed";
                break;
            case "水库站":
                mPresent.getRsvrInfo(start_time, end_time, handler);
                secondLevelSelectAddress1 = property.Route + "waterInfo/reservoir_one";
                secondLevelSelectAddress2 = property.Route + "waterInfo/reservoir_detailed";
                break;
            case "汛情摘要":
                mPresent.getFloodSituation(start_time, end_time, handler);
                break;
        }
    }

    /**
     * 显示PopupWindow 同时背景变暗
     */
    private void showPopTopWithDarkBg(View v) {
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
        mPopWindow.showAsDropDown(v, 0, 0);

        /*PopupWindow ff = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ff.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//
        ff.setFocusable(false);
        ff.setOutsideTouchable(false);
        ff.setTouchable(true);
        int offsetX = -window.getContentView().getMeasuredWidth();
        int offsetY = 0;
        ff.showAsDropDown(window, mButton, offsetX, offsetY, Gravity.START);*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.date_begin:
                        dateSign = 1;
                        //showDatePickDialog(DateType.TYPE_YMDHM);
                        ejectDate();
                        break;
                    case R.id.date_end:
                        dateSign = 2;
                        //showDatePickDialog(DateType.TYPE_YMDHM);
                        ejectDate();
                        break;
                    case R.id.select:  //点击查询按钮
                        System.out.println("点击查询按钮");
                        getDatas();
                        break;
                    case R.id.cancel:
                        mPopWindow.dismiss();
                        for (int i=0;i<areaList.size();i++){
                            areaList.get(i).setAreaState(false);
                        }
                        break;
                }
            }
        };
        date_begin = contentView.findViewById(R.id.date_begin);
        date_end = contentView.findViewById(R.id.date_end);
        contentView.findViewById(R.id.date_begin).setOnClickListener(listener);
        contentView.findViewById(R.id.date_end).setOnClickListener(listener);
        contentView.findViewById(R.id.select).setOnClickListener(listener);
        contentView.findViewById(R.id.cancel).setOnClickListener(listener);

        spinner=contentView.findViewById(R.id.sp_select_email);
        areaList=new ArrayList<>();
        areaList.add(new Administrativearea("张三",false));
        areaList.add(new Administrativearea("李四",false));
        areaList.add(new Administrativearea("王五",false));
        areaList.add(new Administrativearea("赵柳",false));
        // 创建ArrayAdapter对象
        LBAdapter adapter=new LBAdapter(areaList);
        // 为Spinner设置Adapter
        spinner.setAdapter(adapter);
        spinner.setSelection(0,true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Administrativearea data = areaList.get(position);
                System.out.println(data);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println(parent);
            }
        });
    }

    public void initParams() {

    }

    public void getDatas() {
        System.out.println("----------------------------------------------------------------------------------------");
        String showContent = "";
        String dateBegin = date_begin.getText().toString();
        String dateEnd = date_end.getText().toString();
        if (dateBegin.equals("") && dateEnd.equals("")) {
            showContent = "请选择时间"; //暂时
            //initDisplayOpinion();
            //displayTable();
            //mPopWindow.dismiss();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            try {
                Date date1 = sdf.parse(dateBegin);
                Date date2 = sdf.parse(dateEnd);
                if (date1.getTime() < date2.getTime()) {
                    start_time = Tools.handleTime(date1);
                    end_time = Tools.handleTime(date2);
                    timeText.setText(String.format("%s至%s", start_time, end_time));
                    String zhanhao="";
                    for (int i=0;i<areaList.size();i++){
                        if (areaList.get(i).isAreaState()){
                            zhanhao+=areaList.get(i).getAreaName()+",";
                        }
                    }
                    System.out.println(zhanhao);
                    displayTable(start_time, end_time);
                    mPopWindow.dismiss();
                } else {
                    showContent = "开始时间必须小于结束时间";

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.left:
                tableData.setCurrentPage(tableData.getCurrentPage() - 1);
                smartTable.notifyDataChanged();
                int bsetCurrentPageshang = tableData.getCurrentPage() + 1;
                currentPage.setText("第" + bsetCurrentPageshang + "页/");
                break;
            case R.id.right:
                tableData.setCurrentPage(tableData.getCurrentPage() + 1);
                smartTable.notifyDataChanged();
                int bsetCurrentPagexia = tableData.getCurrentPage() + 1;
                currentPage.setText("第" + bsetCurrentPagexia + "页/");
                break;
            case R.id.content_jump:
                AlertDialog.Builder builder = new AlertDialog.Builder(ContentActivity.this);
                View viewDialog = View.inflate(this, R.layout.display_jumppage, null);
                Button jump_determine = viewDialog.findViewById(R.id.jump_determine);
                Button jump_cancel = viewDialog.findViewById(R.id.jump_cancel);
                textView_totalpage = viewDialog.findViewById(R.id.display_jumppage_totalpage);
                numberPicker = viewDialog.findViewById(R.id.display_jumppage_jumppage);
                int settotalPage = tableData.getTotalPage();//总页数
                int nowPage = tableData.getCurrentPage() + 1;//当前页
                textView_totalpage.setText("共" + settotalPage + "页");
                numberPicker.setMaxValue(settotalPage);
                numberPicker.setMinValue(1);
                numberPicker.setNumber(nowPage);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();
                jump_determine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int va = numberPicker.getNumber();
                        tableData.setCurrentPage(va - 1);
                        smartTable.notifyDataChanged();
                        currentPage.setText("第" + Integer.toString(va) + "页");
                        dialog.dismiss();
                    }
                });
                jump_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

    //初始化表格
    public <T> void initsmartTable(final String tableTitle, Map<String, String> map, final List<T> list) {
        //表格样式
        smartTable.getConfig().setShowTableTitle(false).setShowXSequence(false).setShowYSequence(false)
                .setColumnTitleStyle(new FontStyle(60, Color.parseColor("#030607")))//设置标题字体
                .setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.bg_blue, null)));//设置标题背景色

        final Column[] columns = new Column[map.size()];
        int size = DensityUtils.dp2px(ContentActivity.this, 20);
        int i = -1;
        Column<String> nameColumn = null;

        class df extends TextDrawFormat<String> {
            @Override
            public void setTextPaint(TableConfig config, CellInfo<String> cellInfo, Paint paint) {
                super.setTextPaint(config, cellInfo, paint);
                paint.setColor(Color.BLUE);
            }

            @Override
            public void draw(Canvas c, Rect rect, CellInfo<String> cellInfo, TableConfig config) {
                Paint paint = config.getPaint();
                paint.setUnderlineText(true);
                paint.setTextSkewX(-0.5f);
                super.draw(c, rect, cellInfo, config);
                paint.setUnderlineText(false);
                paint.setTextSkewX(0);
            }
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            if (i == 0) {
                nameColumn = new Column<String>(entry.getKey(), entry.getValue(), new df());
                nameColumn.setFixed(true);
            } else if (i == map.size() - 1) {
                nameColumn = new Column<>(entry.getKey(), entry.getValue(), new TextImageDrawFormat<String>(size, size, TextImageDrawFormat.TOP, 40) {
                    @Override
                    protected Context getContext() {
                        return ContentActivity.this;
                    }

                    @Override
                    protected int getResourceID(String s, String value, int position) {
                        return 0;
                    }
                });
            } else {
                nameColumn = new Column<>(entry.getKey(), entry.getValue());
                nameColumn.setDrawFormat(new MultiLineDrawFormat<String>(ContentActivity.this, 100));
            }
            columns[i] = nameColumn;
        }
        tableData = new PageTableData<>(tableTitle, list, columns);
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size, size, TextImageDrawFormat.RIGHT, 10) {
            @Override //设置标题样式
            protected Context getContext() {
                return ContentActivity.this;
            }

            @Override
            protected int getResourceID(Column column) {
                if (tableData.getSortColumn() == column) {
                    setDirection(TextImageDrawFormat.RIGHT);
                    if (column.isReverseSort()) {
                        return R.mipmap.sort_up;
                    }
                    return R.mipmap.sort_down;
                }
                //setDirection(TextImageDrawFormat.TOP);
                return 0;
            }
        });
        //第一列的点击事件
        columns[0].setOnColumnItemClickListener(new OnColumnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int position) {
                try {
                    Intent intent = new Intent(ContentActivity.this, McontentActivity.class);
                    intent.putExtra("itemTitle", title);
                    Field fielDStcd = list.get(position).getClass().getDeclaredField("stnm");
                    fielDStcd.setAccessible(true);
                    String stcd = fielDStcd.get(list.get(position)).toString();
                    intent.putExtra("viceItemTitle", stcd);
                    intent.putExtra("viceItemStcd", value);
                    intent.putExtra("address1", secondLevelSelectAddress1);
                    intent.putExtra("address2", secondLevelSelectAddress2);
                    if (title.equals("降雨信息")) {
                        intent.putExtra("start_time", start_time);
                        intent.putExtra("end_time", end_time);
                    }
                    startActivity(intent);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        smartTable.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)))
                .setShowXSequence(false).setShowYSequence(false);
        smartTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return ContextCompat.getColor(ContentActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }
        });
        tableData.setPageSize(7);
        int settotalPage = tableData.getTotalPage();
        int setcurrentPage = tableData.getCurrentPage() + 1;
        totalPage.setText("共" + settotalPage + "页");
        currentPage.setText("第" + setcurrentPage + "页/");
        smartTable.setTableData(tableData);
    }

    //toolbar的返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_data:
                showPopTopWithDarkBg(contentToolbar);
                break;
        }
        return true;
    }

    //加载右菜单（toolbar）
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbars, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresent.takeView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(ContentActivity.this, 20)); //设置全局字体大小
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresent.dropView();
    }

    //雨量表格设置
    @Override
    public void setRainTable(Map<String, String> map, List<ApiRainInfo.DataBean> apiRainInfo) {
        if (apiRainInfo != null && apiRainInfo.size() > 0) {
            if (notDatas.getVisibility() == View.VISIBLE) {
                notDatas.setVisibility(View.GONE);
            }
            if (mContentView.getVisibility() == View.GONE) {
                mContentView.setVisibility(View.VISIBLE);
            }
            if (contentView_fragment.getVisibility() == View.VISIBLE) {
                contentView_fragment.setVisibility(View.GONE);
            }
            initsmartTable("降雨", map, apiRainInfo);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        } else {
            notDatas.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        }
    }

    //雨强表格设置
    @Override
    public void setRainStTable(ApiRainStInfo.DataBean apiRainSsInfo) {
        if (apiRainSsInfo.get_$1hours() != null && apiRainSsInfo.get_$1hours().size() > 0
                && apiRainSsInfo.get_$3hours() != null && apiRainSsInfo.get_$3hours().size() > 0
                && apiRainSsInfo.get_$6hours() != null && apiRainSsInfo.get_$6hours().size() > 0) {

            if (notDatas.getVisibility() == View.VISIBLE) {
                notDatas.setVisibility(View.GONE);
            }
            if (mContentView.getVisibility() == View.VISIBLE) {
                mContentView.setVisibility(View.GONE);
            }
            if (contentView_fragment.getVisibility() == View.GONE) {
                contentView_fragment.setVisibility(View.VISIBLE);
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.contentView_fragment, IntensityOfRainFragment.newInstance(apiRainSsInfo));
            transaction.commit();
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        } else {
            notDatas.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        }
    }

    //河道表格设置
    @Override
    public void setRiverStTable(Map<String,String> map, List<ApiRiverInfo.DataBean> apiRiverInfo) {
        if (apiRiverInfo != null && apiRiverInfo.size() > 0) {
            if (notDatas.getVisibility() == View.VISIBLE) {
                notDatas.setVisibility(View.GONE);
            }
            if (mContentView.getVisibility() == View.GONE) {
                mContentView.setVisibility(View.VISIBLE);
            }
            if (contentView_fragment.getVisibility() == View.VISIBLE) {
                contentView_fragment.setVisibility(View.GONE);
            }
            initsmartTable("河道", map, apiRiverInfo);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        } else {
            notDatas.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        }
    }

    //水库表格设置
    @Override
    public void setRsvrStTable(Map<String,String> map, List<ApiRsvrInfo.DataBean> apiRsvrInfo) {
        if (apiRsvrInfo != null && apiRsvrInfo.size() > 0) {
            if (notDatas.getVisibility() == View.VISIBLE) {
                notDatas.setVisibility(View.GONE);
            }
            if (mContentView.getVisibility() == View.GONE) {
                mContentView.setVisibility(View.VISIBLE);
            }
            if (contentView_fragment.getVisibility() == View.VISIBLE) {
                contentView_fragment.setVisibility(View.GONE);
            }
            initsmartTable("水库", map, apiRsvrInfo);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        } else {
            notDatas.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);//隐藏加载动画
        }
    }
}
