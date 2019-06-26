package com.bzh.widgets.linkageMenus;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Timer;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/26 9:09
 */
public class CenterLayoutManager extends LinearLayoutManager {

    private static final String TAG = "CenterLayoutManager";

    public CenterLayoutManager(Context context) {
        super(context);
    }

    public CenterLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        Log.e(TAG, "recyclerView: "+recyclerView );
        Log.e(TAG, "RecyclerView.State: "+state );
        Log.e(TAG, "position: "+position );

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(position);
        this.startSmoothScroll(linearSmoothScroller);
        // 第一个可见位置
        int behindFirstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        int movePosition = position - behindFirstItem;
        if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
            int top = recyclerView.getChildAt(movePosition).getTop();
            recyclerView.smoothScrollBy(0, top);
        }
    }
}
