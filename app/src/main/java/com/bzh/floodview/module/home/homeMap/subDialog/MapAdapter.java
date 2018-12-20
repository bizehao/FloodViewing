package com.bzh.floodview.module.home.homeMap.subDialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.model.mapData.ApiRainMapData;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/15 17:04
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {

    private Context mContext;
    private List<ApiRainMapData.DataBean> list;

    public MapAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_map, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ApiRainMapData.DataBean dataBean = list.get(i);
        viewHolder.timeTextView.setText(dataBean.getTm());
        viewHolder.drpTextView.setText(dataBean.getDrp());
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public void setList(List<ApiRainMapData.DataBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        private TextView drpTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.im_time);
            drpTextView = itemView.findViewById(R.id.im_drp);
        }
    }
}
