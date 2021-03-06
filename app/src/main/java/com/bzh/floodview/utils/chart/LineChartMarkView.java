package com.bzh.floodview.utils.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import com.bzh.floodview.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.List;

import timber.log.Timber;

/**
 * Created by xhu_ww on 2018/6/1.
 * description:
 */
public class LineChartMarkView<T> extends MarkerView {

    private TextView tvDate;
    private TextView tvValue0;
    private TextView tvValue1;
    private IAxisValueFormatter xAxisValueFormatter;
    DecimalFormat df = new DecimalFormat("0.00");
    LineValHandler mLineValHandler;

    public LineChartMarkView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.layout_markview);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvValue0 = (TextView) findViewById(R.id.tv_value0);
        tvValue1 = (TextView) findViewById(R.id.tv_value1);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Timber.e("执行");
        Chart chart = getChartView();
        if (chart instanceof LineChart) {
            LineData lineData = ((LineChart) chart).getLineData();
            //获取到图表中的所有曲线
            List<ILineDataSet> dataSetList = lineData.getDataSets();
            for (int i = 0; i < dataSetList.size(); i++) {
                LineDataSet dataSet = (LineDataSet) dataSetList.get(i);
                //获取到曲线的所有在Y轴的数据集合，根据当前X轴的位置 来获取对应的Y轴值
                float y = dataSet.getValues().get((int) e.getX()).getY();
                if (i == 0) {
                    tvValue0.setText(dataSet.getLabel() + "：" + df.format(y ));//df.format(y * 100) + "%")
                }
                if (i == 1) {
                    tvValue1.setText(dataSet.getLabel() + "：" + df.format(y));//df.format(y * 100) + "%")
                }
            }
            Highlight[] highlights = chart.getHighlighted();
            Highlight high = highlights[0];
            Timber.e(high.toString());
            float mm = high.getX();
            //tvDate.setText(xAxisValueFormatter.getFormattedValue(e.getX(), null));//e.getX()
            tvDate.setText("时间:"+mLineValHandler.setXVal((int) mm));//e.getX()
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public void setLineValHandler(LineValHandler lineValHandler){
        this.mLineValHandler = lineValHandler;
    }

    public interface LineValHandler{
        String setXVal(int index);
    }
}
