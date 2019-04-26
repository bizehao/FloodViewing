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
import java.util.List;


public class LBAdapter extends BaseAdapter {

    private List<Administrativearea> list;

    private int count = 0;

    private boolean allCheck = true;

    public LBAdapter(List<Administrativearea> list) {
        this.list = list;
        count = list.size();
    }



    @Override
    public int getCount() {
        return list.size() + 2;
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
            mTextView.setText("已勾选" + count + "个县域");
        } else if(position == 1){
            mCheckBox.setChecked(allCheck);
            mTextView.setText("全选");
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allCheck = mCheckBox.isChecked();
                    if (allCheck) {
                        for (Administrativearea obj : list) {
                            obj.setAreaState(true);
                        }
                        count = list.size();
                    } else {
                        for (Administrativearea obj : list) {
                            obj.setAreaState(false);
                        }
                        count = 0;
                    }
                    notifyDataSetChanged();
                }
            });
        }else {
            Administrativearea obj = list.get(position-2);
            mTextView.setText(obj.getAreaName());
            mCheckBox.setChecked(obj.isAreaState());
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean state = mCheckBox.isChecked();
                    obj.setAreaState(state);
                    if (state) {
                        allCheck = ++count == list.size();
                    } else {
                        count--;
                        allCheck = false;
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
