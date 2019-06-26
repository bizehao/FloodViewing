package com.bzh.widgets.linkageMenus;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.bzh.widgets.R;
import java.util.List;
import java.util.Timer;

/**
 * @author 毕泽浩
 * @Description:联动菜单
 * @time 2018/11/11 14:09
 */
public class LinkageMenus extends FrameLayout {

    private RecyclerView mRecyclerViewLeft;
    private RecyclerView mRecyclerViewRight;
    private List<LinkLeftBean> datas;
    private List<LinkBean> linkBeans;
    private LinkageLeftAdapter leftAdapter;
    private LinkageRightAdapter rightAdapter;
    //private boolean positioningRolling = true; //是否是手动滚动
    //private int itemPosition; //用户点击的item的序号；

    public LinkageMenus(@NonNull Context context) {
        this(context, null);
    }

    public LinkageMenus(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_linkagemenus, this);
        mRecyclerViewLeft = findViewById(R.id.rcv_left);
        mRecyclerViewRight = findViewById(R.id.rcv_right);
    }

    public LinkageMenus setData(List<LinkLeftBean> datas) {
        this.datas = datas;
        return this;
    }

    public LinkageMenus setLinkBean(List<LinkBean> datas) {
        this.linkBeans = datas;
        return this;
    }

    //刷新内部的状态
    public void refreshRightMenu(){
        rightAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("all")
    public void build() {
        rightAdapter = new LinkageRightAdapter(getContext(), linkBeans);
        LinearLayoutManager rightLayoutManager = new LinearLayoutManager(getContext());
        //设置为垂直布局，这也是默认的
        rightLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerViewRight.setLayoutManager(rightLayoutManager);
        mRecyclerViewRight.setAdapter(rightAdapter);
        /*mRecyclerViewRight.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                positioningRolling = true;
                return false;
            }
        });*/
        /*mRecyclerViewRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "混动开始"+newState);
                if(newState == 0){
                    int behindFirstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                    int movePosition = itemPosition - behindFirstItem;
                    if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                        int top = recyclerView.getChildAt(movePosition).getTop();
                        recyclerView.smoothScrollBy(0, top);
                    }
                }
            }
        });*/
        /*mRecyclerViewRight.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (positioningRolling) {
                    RecyclerView RecyclerView = (RecyclerView) v;
                    View view = RecyclerView.getChildAt(0);
                    int position = RecyclerView.getChildLayoutPosition(view);
                    int val = view.getTop();
                    LinearLayoutManager leftManager = ((LinearLayoutManager) mRecyclerViewLeft.getLayoutManager());
                    if (val < -1100) {
                        if (leftAdapter.getCheckedItem() == position && position != rightAdapter.getItemCount() - 1) {
                            leftAdapter.setCheckedItem(position + 1);
                            leftManager.scrollToPositionWithOffset(position, 0);
                        }
                    } else {
                        if (leftAdapter.getCheckedItem() != position && leftAdapter.getCheckedItem() != 0) {
                            leftAdapter.setCheckedItem(position);
                            leftManager.scrollToPositionWithOffset(position, 0);
                        }
                    }
                    if (!mRecyclerViewRight.canScrollVertically(1)) {
                        leftManager.scrollToPositionWithOffset(leftAdapter.getItemCount()-1, 0);
                        leftAdapter.setCheckedItem(leftAdapter.getItemCount()-1);
                    }
                }
            }
        });*/

        leftAdapter = new LinkageLeftAdapter(getContext(), datas);
        leftAdapter.setOnClickListener(new LinkageLeftAdapter.onLinkageLeftListener() {
            @Override
            public void onClick(View view, final int position, String text) {
                Log.d(TAG, "onClick: "+position);
                //positioningRolling = false;
                //itemPosition = position;
                mRecyclerViewRight.scrollToPosition(position);
                mRecyclerViewRight.post(new Runnable() {
                    @Override
                    public void run() {
                        // 第一个可见位置
                        int firstItem = mRecyclerViewRight.getChildLayoutPosition(mRecyclerViewRight.getChildAt(0));

                        if(firstItem != position){
                            int viewIndex = 0;
                            for(int i=0; i<mRecyclerViewRight.getChildCount(); i++){
                                int itemPosition =  mRecyclerViewRight.getChildLayoutPosition(mRecyclerViewRight.getChildAt(i));
                                if(itemPosition == position){
                                    viewIndex = i;
                                    break;
                                }
                            }
                            int top = mRecyclerViewRight.getChildAt(viewIndex).getTop();
                            mRecyclerViewRight.scrollBy(0,top);
                        }
                    }
                });

            }
        });

        LinearLayoutManager leftLayoutManager = new LinearLayoutManager(getContext());
        //设置为垂直布局，这也是默认的
        leftLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerViewLeft.setLayoutManager(leftLayoutManager);
        mRecyclerViewLeft.setAdapter(leftAdapter);


    }

    /**
     * right滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    /*private void smoothMoveToPosition(final RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(position,0);
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
        }
    }*/
    private static final  String TAG = "LinkageMenus";
}
