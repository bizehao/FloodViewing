package com.bzh.widgets.linkageMenus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.widgets.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 14:24
 */
public class LinkageLeftAdapter extends RecyclerView.Adapter<LinkageLeftAdapter.ViewHolder> {

    private List<LinkLeftBean> list;
    private onLinkageLeftListener listener;
    private Context mContext;
    private int CheckedItem;

    LinkageLeftAdapter(Context context, List<LinkLeftBean> data) {
        this.list = data;
        this.mContext = context;
        if(list != null && list.size() > 0){
            list.get(0).setClickState(true);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_linkage_left, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final LinkLeftBean linkLeftBean = list.get(i);
        viewHolder.textView.setText(linkLeftBean.getTypeName());
        if(linkLeftBean.getClickState()){
            viewHolder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        }else {
            viewHolder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorGray001));
        }
        final int position = i;
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckedItem(position);
                if (listener != null) {
                    listener.onClick(v,position, linkLeftBean.getTypeName());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //获取被选中的item
    int getCheckedItem(){
        return CheckedItem;
    }

    //设置被选中的item
    public void setCheckedItem(int position){
        this.CheckedItem = position;
        for (LinkLeftBean bean:list){
            bean.setClickState(false);
        }
        list.get(position).setClickState(true);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ill_text);
        }
    }

    interface onLinkageLeftListener {
        void onClick(View view,int position, String text);
    }

    void setOnClickListener(onLinkageLeftListener listener) {
        this.listener = listener;
    }
}
