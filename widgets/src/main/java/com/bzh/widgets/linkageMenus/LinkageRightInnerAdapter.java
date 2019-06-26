package com.bzh.widgets.linkageMenus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Magnifier;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bzh.widgets.R;

import org.w3c.dom.Text;

import java.util.List;


/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/11 19:11
 */
public class LinkageRightInnerAdapter extends RecyclerView.Adapter<LinkageRightInnerAdapter.ViewHolder> {

    private static final String TAG = "LinkageRightInner...";

    private List<LinkBean.InnerType> list;

    private Magnifier magnifier;

    private PopupWindow mPopupWindow;

    LinkageRightInnerAdapter(List<LinkBean.InnerType> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_linkage_rignt_inner, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressWarnings("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final LinkBean.InnerType innerType = list.get(i);
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
        if (!innerType.isShow()) {
            if (viewHolder.itemView.getVisibility() != View.GONE) {
                viewHolder.itemView.setVisibility(View.GONE);
            }
            param.height = 0;
            param.width = 0;
        } else {
            if (viewHolder.itemView.getVisibility() != View.VISIBLE) {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            }
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
        viewHolder.itemView.setLayoutParams(param);

        viewHolder.mTextView.setText(innerType.getInnerTypeName());
        viewHolder.mCheckBox.setChecked(innerType.isStatus());
        viewHolder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View contentView = LayoutInflater.from(v.getContext()).inflate(
                        com.bzh.widgets.R.layout.pop_showtext, (ViewGroup) v.getParent(), false);
                TextView textView = contentView.findViewById(R.id.show_text);
                textView.setText(viewHolder.mTextView.getText());
                mPopupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mPopupWindow.setTouchable(true);
                float mY = v.getY();

                mPopupWindow.showAtLocation(v, Gravity.TOP, 0, (int) mY-30);
                return true;
            }
        });

        viewHolder.mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
                return false;
            }
        });

        viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerType.setStatus(((CheckBox) v).isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCheckBox;
        private TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.ilri_cb);
            mTextView = itemView.findViewById(R.id.ilri_tx);
        }
    }
}
