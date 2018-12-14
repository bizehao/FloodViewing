package com.bzh.floodview.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzh.floodview.R;
import com.bzh.floodview.model.floodsituation.Submenu;
import com.bzh.floodview.module.content.ContentActivity;
import com.bzh.floodview.module.content.ShowitemActivity;
import com.bzh.floodview.utils.MapIntent;

import java.util.LinkedHashMap;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private Context mContext;

    private List<Submenu> mSubmenu;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = itemView.findViewById(R.id.show_item_icon);
            textView = itemView.findViewById(R.id.show_item_name);
        };
    }

    public ShowAdapter(List<Submenu> mSubmenu) {
        this.mSubmenu = mSubmenu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null){
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_item, viewGroup, false);
        ShowAdapter.ViewHolder holder = new ShowAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Submenu submenu = mSubmenu.get(position);
                String name = submenu.getItemName();
                int sign = submenu.getSign();
                LinkedHashMap<String,Integer> subMap = submenu.getSubMap();
                Intent intent = null;
                switch (sign){
                    case 1:
                        intent = new Intent(mContext, ContentActivity.class);
                        intent.putExtra("childItem", "汛情摘要");
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        MapIntent mapIntent = new MapIntent(subMap);
                        intent = new Intent(mContext, ShowitemActivity.class);
                        intent.putExtra("menus", mapIntent);
                        intent.putExtra("title", name);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        Toast.makeText(mContext,"该模块尚未开发完成",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAdapter.ViewHolder viewHolder, int i) {
        Submenu submenu = mSubmenu.get(i);
        viewHolder.textView.setText(submenu.getItemName());
        Glide.with(mContext).load(submenu.getImgId()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mSubmenu.size();
    }
}
