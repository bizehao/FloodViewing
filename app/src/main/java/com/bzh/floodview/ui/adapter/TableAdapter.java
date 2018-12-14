package com.bzh.floodview.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bzh.floodview.R;

import java.util.List;
import java.util.Map;

public class TableAdapter extends BaseAdapter {

    private List<Map<String, Object>> dataList;

    private LayoutInflater mInflater;

    public TableAdapter(Context context, List<Map<String, Object>> dataList){
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_main_list, null);
        }
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        tvTitle.setBackgroundColor(Color.parseColor("#FF91E782"));
        Map<String, Object> item = dataList.get(position);
        tvTitle.setText((String)item.get("Title"));
        return convertView;
    }

}
