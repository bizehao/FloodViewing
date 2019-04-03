package com.bzh.floodview.module.home.homeMap.subFram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.floodview.R;

import java.util.List;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/20 15:09
 */
public class SubMapAdapter<T> extends RecyclerView.Adapter<SubMapAdapter<T>.ViewHolder> {

    private Context mContext;

    private int cols; //列数

    private List<T> list;

    private AdapterRun adapterRun;

    SubMapAdapter(Context context, int cols) {
        this.mContext = context;
        this.cols = cols;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sub_four_cols, viewGroup, false);
        if (cols == 5) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sub_five_cols, viewGroup, false);
        }
        if (cols == 4) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sub_four_cols, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Timber.e("展示 "+viewHolder);
        if (adapterRun != null) {
            adapterRun.run(i, viewHolder);
        }

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        TextView mTextView5;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (cols == 4) {
                this.mLinearLayout = itemView.findViewById(R.id.four_group);
                this.mTextView1 = itemView.findViewById(R.id.four_one);
                this.mTextView2 = itemView.findViewById(R.id.four_two);
                this.mTextView3 = itemView.findViewById(R.id.four_three);
                this.mTextView4 = itemView.findViewById(R.id.four_four);
            }
            if (cols == 5) {
                this.mLinearLayout = itemView.findViewById(R.id.five_group);
                this.mTextView1 = itemView.findViewById(R.id.five_one);
                this.mTextView2 = itemView.findViewById(R.id.five_two);
                this.mTextView3 = itemView.findViewById(R.id.five_three);
                this.mTextView4 = itemView.findViewById(R.id.five_four);
                this.mTextView5 = itemView.findViewById(R.id.five_five);
            }
        }
    }

    interface AdapterRun {
        void run(int i, SubMapAdapter.ViewHolder viewHolder);
    }

    void setAdapterRun(AdapterRun adapterRun) {
        this.adapterRun = adapterRun;
    }

    public List<T> getList() {
        return list;
    }
}
