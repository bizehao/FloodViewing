package com.bzh.widgets.linkageMenus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bzh.widgets.R;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 14:24
 */
public class LinkageRightAdapter extends RecyclerView.Adapter<LinkageRightAdapter.ViewHolder> {

    private List<LinkBean> datas;
    private onTvGetAllClick onTvGetAllClick;
    private Context mContext;

    LinkageRightAdapter(Context mContext, List<LinkBean> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_linkage_right, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        LinkBean linkBean = datas.get(i);
        viewHolder.mTvTitle.setText(linkBean.getTypeName());

        GridLayoutManager rightLayoutManager = new GridLayoutManager(mContext,3);
        //设置为垂直布局，这也是默认的
        rightLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.mRecyclerView.setLayoutManager(rightLayoutManager);
        LinkageRightInnerAdapter adapter = new LinkageRightInnerAdapter(linkBean.getInnerTypes());
        viewHolder.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private RecyclerView mRecyclerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.ilr_title);
            mRecyclerView = itemView.findViewById(R.id.ilr_recy);
        }
    }

    interface onTvGetAllClick {
        void onclick(View view, int position);
    }

    public void setOnTvGetAllClick(LinkageRightAdapter.onTvGetAllClick onTvGetAllClick) {
        this.onTvGetAllClick = onTvGetAllClick;
    }
}
