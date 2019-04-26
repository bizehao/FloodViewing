package com.bzh.floodview.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzh.floodview.R;
import com.bzh.floodview.model.floodsituation.Submenu;
import com.bzh.floodview.module.home.homeIndex.content.ContentActivity;
import java.util.List;

//子菜单适配器
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private List<Submenu> mItem;

    public ItemAdapter(List<Submenu> mItem){
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.submenu, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Submenu submenu = mItem.get(position);
                Intent intent = new Intent(view.getContext(), ContentActivity.class);
                intent.putExtra("childItem",submenu.getItemName());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Submenu menuItem = mItem.get(i);
        viewHolder.textView.setText(menuItem.getItemName());
        viewHolder.imageView.setImageResource(menuItem.getImgId());
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imageView = itemView.findViewById(R.id.submenu_image);
            this.textView = itemView.findViewById(R.id.submenu_text);
        }
    }
}
