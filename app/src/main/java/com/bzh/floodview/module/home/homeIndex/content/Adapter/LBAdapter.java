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

import timber.log.Timber;


public class LBAdapter extends BaseAdapter {

    private List<Administrativearea> list;
    private View mView;

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

    View mViewOne;
    View mViewTwo;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.administrative_area_textviews, parent, false);
            TextView textview_moren = mView.findViewById(R.id.textview_moren);
            textview_moren.setText("没有选中任何县区");
        } else {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.administrative_area_textview, parent, false);
            Timber.e("下拉列表：" + position);
            TextView areaText = mView.findViewById(R.id.area_text);
            CheckBox areaCheckBox = mView.findViewById(R.id.area_checkbox);
            areaText.setText(list.get(position).getAreaName());
            areaCheckBox.setChecked(list.get(position).isAreaState());

            areaCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(position).setAreaState(!areaCheckBox.isActivated());
                }
            });
        }
        Timber.e("第一个值："+convertView);
        Timber.e("第二个值："+mView);
        return mView;
    }
}
