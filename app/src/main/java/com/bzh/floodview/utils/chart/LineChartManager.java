package com.bzh.floodview.utils.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by loptop on 2017/6/2.
 */
public class LineChartManager {

    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYAxis;           //右侧Y轴 自定义XY轴值
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    public LineChartManager(LineChart lineChart) {
        this.lineChart = lineChart;
        initChart();
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        /***图表设置***/
        lineChart.setDrawGridBackground(false); //是否展示网格线
        lineChart.setBackgroundColor(Color.WHITE); //背景色
        lineChart.setDrawBorders(false); //是否显示边界
        lineChart.setDragEnabled(true); //是否可以拖动
        lineChart.setDoubleTapToZoomEnabled(false); //是否将双击设置为缩放启用
        lineChart.animateY(500); //设置Y轴动画效果
        lineChart.animateX(500); //设置X轴动画效果

        Description description = new Description();
        description.setText("需要描述的内容");
        description.setEnabled(false); //是否启用图表描述
        lineChart.setDescription(description);


        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();

        /* 添加表格线 */
        xAxis.setDrawGridLines(true);
        rightYAxis.setDrawGridLines(true);
        leftYAxis.setDrawGridLines(true);
        //设置Y轴网格线为虚线
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYAxis.enableGridDashedLine(10f, 10f, 0f);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        //rightYAxis.setEnabled(false);

        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        //是否显示
        legend.setEnabled(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);

        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }


    /**
     * 展示一条曲线 默认x轴
     *
     * @param xAxisValues    y轴的数据
     * @param lineName 曲线名称
     * @param color    曲线颜色
     */
    public void showOneLineChart(List<String> xAxisValues, List<Float> yAxisValues, String lineName, int color) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new Entry(i, yAxisValues.get(i)));
        }

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, lineName);
        // CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
        //标志
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        //data.addDataSet(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() { //x轴自定义
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = xAxisValues.get((int) value % xAxisValues.size());
                return tradeDate;
            }
        });
        xAxis.setLabelCount(6,false);
        lineChart.setData(data);
    }

    /**
     * 注意 集合的长度一致，在此未做处理
     * 多条曲线
     * @param yDataList List<Integer> 代表一条曲线的数据  yDataList.size 代表曲线的条数
     * @param lineNames 曲线名称
     * @param colors    曲线颜色
     */
    public void showMultiNormalLineChart(List<String> xAxisValues,List<List<Float>> yDataList, List<String> lineNames, List<Integer> colors) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        List<Entry> entries;
        float min = 0;
        for (int i = 0; i < yDataList.size(); i++) {
            entries = new ArrayList<>();
            for (int j = 0; j < yDataList.get(i).size(); j++) {
                entries.add(new Entry(j, yDataList.get(i).get(j)));
                if( yDataList.get(i).get(j) < min){
                    min = yDataList.get(i).get(j);
                }
            }
            LineDataSet lineDataSet = new LineDataSet(entries, lineNames.get(i));
            initLineDataSet(lineDataSet, colors.get(i), LineDataSet.Mode.CUBIC_BEZIER);
            dataSets.add(lineDataSet);
        }
        LineData lineData = new LineData(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        //x轴自定义
        xAxis.setValueFormatter((value, axis) -> xAxisValues.get((int) value % xAxisValues.size()));
        xAxis.setLabelCount(6,false);

        /*final float min[] = {entries.get(0).getY()};
        leftYAxis.setValueFormatter((value, axis) -> {
            if(value < min[0]){
                min[0] = value;
            }
            return String.format(Locale.ENGLISH,"%1.1f", value);//((int) (value * 100)) + "%"
        });*/


        leftYAxis.setAxisMinimum(min==0?0:min-0.5f);

        lineChart.setData(lineData);
    }

    /**
     * 设置X轴的显示值
     *
     * @param min        x轴最小值
     * @param max        x轴最大值
     * @param labelCount x轴的分割数量
     */
    public void setXAxisData(float min, float max, int labelCount) {
        xAxis.setAxisMinimum(min);
        xAxis.setAxisMaximum(max);
        xAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    /**
     * 自定义的 X轴显示内容
     *
     * @param xAxisStr
     * @param labelCount x轴的分割数量
     */
    public void setXAxisData(final List<String> xAxisStr, int labelCount) {
        xAxis.setLabelCount(labelCount, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisStr.get((int) value % xAxisStr.size());
            }
        });
        lineChart.invalidate();
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxisData(float max, float min, int labelCount) {
        leftYAxis.setAxisMaximum(max);
        leftYAxis.setAxisMinimum(min);
        leftYAxis.setLabelCount(labelCount, false);

        rightYAxis.setAxisMaximum(max);
        rightYAxis.setAxisMinimum(min);
        rightYAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    /**
     * 自定义的 y轴显示内容
     *
     * @param yAxisStr
     * @param labelCount y轴的分割数量
     */
    public void setYAxisData(final List<String> yAxisStr, int labelCount) {
        xAxis.setLabelCount(labelCount, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return yAxisStr.get((int) value % yAxisStr.size());
            }
        });
        lineChart.invalidate();
    }

    private LimitLine highLimit;

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHighLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        highLimit = new LimitLine(high, name);
        highLimit.setLineWidth(2f);
        highLimit.setTextSize(10f);
        highLimit.setLineColor(color);
        highLimit.setTextColor(color);
        leftYAxis.addLimitLine(highLimit);
        lineChart.invalidate();
    }

    /**
     * 移除高限制线
     */
    public void removeHighLimitLine() {
        leftYAxis.removeLimitLine(highLimit);
        lineChart.invalidate();
    }

    private LimitLine lowLimit;

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(float low, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        lowLimit = new LimitLine(low, name);
        lowLimit.setLineWidth(2f);
        lowLimit.setTextSize(10f);
        lowLimit.setLineColor(color);
        lowLimit.setTextColor(color);
        leftYAxis.addLimitLine(lowLimit);
        lineChart.invalidate();
    }

    /**
     * 移除低限制线
     */
    public void removeLowLimitLine() {
        leftYAxis.removeLimitLine(lowLimit);
        lineChart.invalidate();
    }

    private LimitLine InLimit;

    /**
     * 设置中限制线
     *
     * @param low
     * @param name
     */
    public void setInLimitLine(float low, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        InLimit = new LimitLine(low, name);
        InLimit.setLineWidth(2f);
        InLimit.setTextSize(10f);
        InLimit.setLineColor(color);
        InLimit.setTextColor(color);
        leftYAxis.addLimitLine(InLimit);
        lineChart.invalidate();
    }

    /**
     * 移除中限制线
     */
    public void removeInLimitLine() {
        leftYAxis.removeLimitLine(InLimit);
        lineChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }


    /*****************以下方法无法通用，根据自己数据的不同进行相应的处理********************/
    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public <T> void showLineChart(final List<T> dataList, String name, int color, final String x, final String y) { // List<T> dataList

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            T datay = dataList.get(i);
            Field field = null;
            try { //反射获取属性的值
                field = datay.getClass().getDeclaredField(y);
                field.setAccessible(true);
                /**
                 * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
                 * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示*/
                Entry entry = new Entry(i, Float.parseFloat((String) field.get(datay)));
                entries.add(entry);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(6, false);
        //设置是否绘制刻度
        //xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() { //x轴自定义
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (dataList.size() > 0) {
                    T datax = dataList.get((int) value % dataList.size());
                    try {
                        Field field = datax.getClass().getDeclaredField(x);
                        field.setAccessible(true);
                        return (String) field.get(datax);//DateUtil.formatDateToMD(tradeDate);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("没有数据，数据为空");
                }
                return "";
            }
        });

        //leftYAxis.setLabelCount(20);
        //leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);

        final float min[] = {entries.get(0).getY(),entries.get(0).getY()};
        leftYAxis.setValueFormatter((value, axis) -> {
            if(value < min[0]){
                min[0] = value;
            }
            if(value > min[1]){
                min[1] = value;
            }
            return String.format(Locale.ENGLISH,"%1.1f", value);//((int) (value * 100)) + "%"
        });
        leftYAxis.setAxisMinimum(min[0]-2f);
        leftYAxis.setAxisMaximum(min[1]+2f);
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return df.format(value);//df.format(value * 100) + "%"
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    //移除入库曲线
    public void removeInqLine() {
        lineChart.getLineData().removeDataSet(inqLineDataSet);
        lineChart.invalidate();
    }

    //移除出库曲线
    public void removeOtqLine() {
        lineChart.getLineData().removeDataSet(otqLineDataSet);
        lineChart.invalidate();
    }

    private LineDataSet inqLineDataSet;//入库流量线
    private LineDataSet otqLineDataSet;//出库流量线

    /**
     * 添加曲线 专用
     */
    public <T> void addLine(final List<T> dataList, String name, int color, final String y) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            T datay = dataList.get(i);
            try {
                Field field = datay.getClass().getDeclaredField(y);
                field.setAccessible(true);
                Entry entry = new Entry(i, Float.parseFloat((String) field.get(datay)));
                entries.add(entry);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        LineDataSet lineDataSet = null;
        // 每一个LineDataSet代表一条线
        if (name.equals("入库流量")) {
            inqLineDataSet = new LineDataSet(entries, name);
            initLineDataSet(inqLineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
            lineChart.getLineData().addDataSet(inqLineDataSet);
        } else if (name.equals("出库流量")) {
            otqLineDataSet = new LineDataSet(entries, name);
            initLineDataSet(otqLineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
            lineChart.getLineData().addDataSet(otqLineDataSet);
        } else {
            lineDataSet = new LineDataSet(entries, name);
            initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
            lineChart.getLineData().addDataSet(lineDataSet);
        }
        //lineDataSet.setHighlightEnabled(true);


        /*rightYAxis.setTextColor(ColorTemplate.getHoloBlue());
        rightYAxis.setAxisMaximum(20);
        rightYAxis.setAxisMinimum(0);
        rightYAxis.setLabelCount(8);
        //leftYAxis.setDrawScale(true);
        rightYAxis.setDrawZeroLine(true); // draw a zero line
        rightYAxis.setZeroLineColor(Color.GRAY);
        rightYAxis.setZeroLineWidth(1f);
        rightYAxis.setAxisLineWidth(1f);
        rightYAxis.setAxisLineColor(Color.GRAY);*/

        lineChart.invalidate();
    }

    /**
     * 重置某条曲线 position 从 0 开始
     */
    /*public void resetLine(int position, List<CompositeIndexBean> dataList, String name, int color) {
        LineData lineData = lineChart.getData();
        List<ILineDataSet> list = lineData.getDataSets();
        if (list.size() <= position) {
            return;
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            CompositeIndexBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);

        lineData.getDataSets().set(position, lineDataSet);
        lineChart.invalidate();
    }*/

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public <T> void setMarkerView(Context context, LineChartMarkView.LineValHandler lineValHandler) {
        LineChartMarkView<T> mv = new LineChartMarkView<>(context, xAxis.getValueFormatter());
        mv.setChartView(lineChart);
        mv.setLineValHandler(lineValHandler);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    public YAxis getLeftYAxis() {
        return leftYAxis;
    }
}
