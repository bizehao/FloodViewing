package com.bzh.floodview.module.home.homeIndex.content.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.module.home.homeIndex.content.Adapter.entit.Administrativearea;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;


public class LBAdapter extends BaseAdapter {

    private List<Administrativearea> list;

    private int count = 0;

    public LBAdapter(List<Administrativearea> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertViews = LayoutInflater.from(parent.getContext()).inflate(R.layout.array_item_select, parent, false);
        TextView mTextView = convertViews.findViewById(R.id.area_text);
        CheckBox mCheckBox = convertViews.findViewById(R.id.area_checkbox);
        if (position == 0) {
            mCheckBox.setVisibility(View.GONE);
            mTextView.setText("已勾选"+count+"个县域");
        } else {
            mTextView.setText(list.get(position).getAreaName());
            mCheckBox.setChecked(list.get(position).isAreaState());
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean state = mCheckBox.isChecked();
                    list.get(position).setAreaState(state);
                    if(state){
                        count++;
                    }else {
                        count--;
                    }
                    notifyDataSetChanged();
                }
            });
        }
        return convertViews;
    }

    static class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    public List<Administrativearea> getList() {
        return list;
    }
}
