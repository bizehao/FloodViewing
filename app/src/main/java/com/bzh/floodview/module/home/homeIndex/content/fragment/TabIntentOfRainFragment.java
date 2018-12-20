package com.bzh.floodview.module.home.homeIndex.content.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.bin.david.form.utils.DensityUtils;
import com.bzh.floodview.R;
import com.bzh.floodview.file.property;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.floodsituation.IntensityOfRain;
import com.bzh.floodview.module.home.homeIndex.content.McontentActivity;
import com.bzh.floodview.ui.widget.FreeNumberPicker;
import com.bzh.floodview.utils.DataHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 雨强信息的子碎片
 */
public class TabIntentOfRainFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private SmartTable smartTable;
    private PageTableData tableData;
    private LinearLayout fragment_intentRain_jump;
    private TextView fragment_intentRain_currentPage, fragment_intentRain_totalPage; //第？页，共？页
    private TextView textView_totalpage; //弹框跳转页数
    private FreeNumberPicker numberPicker;   //数字框
    private Button left, right;
    private TextView timeText;
    private String[] timeString;//时间
    List<IntensityOfRain> intentList;

    public TabIntentOfRainFragment() {

    }

    public static Fragment newInstance(int sign) {
        TabIntentOfRainFragment fragment = new TabIntentOfRainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sign", sign);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        int sign = bundle.getInt("sign");
        switch (sign) {
            case 1:
                intentList = new ArrayList<>();
                List<ApiRainStInfo.DataBean._$1hoursBean> list1 = (List<ApiRainStInfo.DataBean._$1hoursBean>) DataHolder.getInstance().retrieve("1hours");
                if(list1 != null){
                    for (ApiRainStInfo.DataBean._$1hoursBean hoursBean : list1){
                        IntensityOfRain gg = new IntensityOfRain();
                        gg.setAdnm(hoursBean.getAdnm());
                        gg.setStcd(hoursBean.getStcd());
                        gg.setStnm(hoursBean.getStnm());
                        gg.setDrp(Double.parseDouble(hoursBean.getDrp()));
                        gg.setTm(String.valueOf(hoursBean.getTm()));
                        gg.setTtt(hoursBean.getTtt());
                        intentList.add(gg);
                    }
                }
                break;
            case 3:
                intentList = new ArrayList<>();
                List<ApiRainStInfo.DataBean._$3hoursBean> list3 = (List<ApiRainStInfo.DataBean._$3hoursBean>) DataHolder.getInstance().retrieve("3hours");
                if(list3 != null){
                    for (ApiRainStInfo.DataBean._$3hoursBean hoursBean : list3){
                        IntensityOfRain gg = new IntensityOfRain();
                        gg.setAdnm(hoursBean.getAdnm());
                        gg.setStcd(hoursBean.getStcd());
                        gg.setStnm(hoursBean.getStnm());
                        gg.setDrp(Double.parseDouble(hoursBean.getDrp()));
                        gg.setTm(String.valueOf(hoursBean.getTm()));
                        gg.setTtt(hoursBean.getTtt());
                        intentList.add(gg);
                    }
                }
                break;
            case 6:
                intentList = new ArrayList<>();
                List<ApiRainStInfo.DataBean._$6hoursBean> list6 = (List<ApiRainStInfo.DataBean._$6hoursBean>) DataHolder.getInstance().retrieve("6hours");
                if(list6 != null){
                    for (ApiRainStInfo.DataBean._$6hoursBean hoursBean : list6){
                        IntensityOfRain gg = new IntensityOfRain();
                        gg.setAdnm(hoursBean.getAdnm());
                        gg.setStcd(hoursBean.getStcd());
                        gg.setStnm(hoursBean.getStnm());
                        gg.setDrp(Double.parseDouble(hoursBean.getDrp()));
                        gg.setTm(String.valueOf(hoursBean.getTm()));
                        gg.setTtt(hoursBean.getTtt());
                        intentList.add(gg);
                    }
                }
                break;
            default:
                intentList = new ArrayList<>();
                break;
        }
        Map<String, String> map = new LinkedHashMap<>();
        map.put("站号", "stcd");
        map.put("站名", "stnm");
        map.put("雨量(mm)", "drp");
        map.put("时间", "ttt");
        map.put("政区", "adnm");
        initView();
        String time = (String) timeText.getText();
        timeString = time.split("至");
        initsmartTable("雨强", map, intentList);
        return rootView;
    }

    private void initView() {
        smartTable = rootView.findViewById(R.id.fragment_intentRain_smart_table);
        fragment_intentRain_jump = rootView.findViewById(R.id.fragment_intentRain_jump);
        fragment_intentRain_currentPage = rootView.findViewById(R.id.fragment_intentRain_currentPage);
        fragment_intentRain_totalPage = rootView.findViewById(R.id.fragment_intentRain_totalPage);
        left = rootView.findViewById(R.id.fragment_intentRain_left);
        right = rootView.findViewById(R.id.fragment_intentRain_right);
        timeText = Objects.requireNonNull(getActivity()).findViewById(R.id.show_time);
        fragment_intentRain_jump.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_intentRain_left:
                tableData.setCurrentPage(tableData.getCurrentPage() - 1);
                smartTable.notifyDataChanged();
                int bsetCurrentPageshang = tableData.getCurrentPage() + 1;
                fragment_intentRain_currentPage.setText("第" + bsetCurrentPageshang + "页/");
                break;
            case R.id.fragment_intentRain_right:
                tableData.setCurrentPage(tableData.getCurrentPage() + 1);
                smartTable.notifyDataChanged();
                int bsetCurrentPagexia = tableData.getCurrentPage() + 1;
                fragment_intentRain_currentPage.setText("第" + bsetCurrentPagexia + "页/");
                break;
            case R.id.fragment_intentRain_jump:
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                View viewDialog = View.inflate(getContext(), R.layout.display_jumppage, null);
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
                        fragment_intentRain_currentPage.setText("第" + Integer.toString(va) + "页");
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
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(Objects.requireNonNull(getContext()), 20)); //设置全局字体大小
        smartTable.getConfig().setShowTableTitle(false).setShowXSequence(false).setShowYSequence(false)
                .setColumnTitleStyle(new FontStyle(60, Color.parseColor("#030607")))//设置标题字体
                .setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.bg_blue)));//设置标题背景色
        final Column[] columns = new Column[map.size()];
        int size = DensityUtils.dp2px(getContext(), 20);
        int i = -1;
        Column<String> nameColumn = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            if (i == 0) {
                nameColumn = new Column<>(entry.getKey(), entry.getValue(), new TextDrawFormat<String>() {
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
                });
                nameColumn.setFixed(true);
            } else if (i == map.size() - 1) {
                nameColumn = new Column<>(entry.getKey(), entry.getValue(), new TextImageDrawFormat<String>(size, size, TextImageDrawFormat.TOP, 40) {
                    @Override
                    protected Context getContext() {
                        return getActivity();
                    }

                    @Override
                    protected int getResourceID(String s, String value, int position) {
                        return 0;
                    }
                });
                nameColumn.setFixed(true);
            } else {
                nameColumn = new Column<>(entry.getKey(), entry.getValue());
                nameColumn.setDrawFormat(new MultiLineDrawFormat<String>(getContext(), 100));
            }
            columns[i] = nameColumn;
        }
        tableData = new PageTableData<>(tableTitle, list, columns);
        //第一列的点击事件
        columns[0].setOnColumnItemClickListener(new OnColumnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int position) {
                try {
                    //Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    //intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    Intent intent = new Intent(getActivity(), McontentActivity.class);
                    intent.putExtra("itemTitle", "降雨信息");
                    Field fielDStcd = list.get(position).getClass().getDeclaredField("stnm");
                    fielDStcd.setAccessible(true);
                    String stcd = fielDStcd.get(list.get(position)).toString();
                    intent.putExtra("viceItemTitle", stcd);
                    intent.putExtra("viceItemStcd", value);
                    intent.putExtra("address1", property.Route + "rainInfo/rainIntensity_one");
                    intent.putExtra("address2", property.Route + "rainInfo/rain_detailed");
                    intent.putExtra("start_time", timeString[0]);
                    intent.putExtra("end_time", timeString[1]);
                    startActivity(intent);
                    //getActivity().finish();
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
                    return ContextCompat.getColor(getContext(), R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }
        });
        if(list.size() > 0){
            tableData.setPageSize(7);
        }

        int settotalPage = tableData.getTotalPage();
        int setcurrentPage = tableData.getCurrentPage() + 1;
        fragment_intentRain_totalPage.setText("共" + settotalPage + "页");
        fragment_intentRain_currentPage.setText("第" + setcurrentPage + "页/");
        smartTable.setTableData(tableData);
    }
}
