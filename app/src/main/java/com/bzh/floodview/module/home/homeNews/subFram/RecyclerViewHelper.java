package com.bzh.floodview.module.home.homeNews.subFram;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bzh.floodview.R;
import com.bzh.floodview.module.home.homeNews.MyFragmentAdapter;
import com.sothree.slidinguppanel.ScrollableViewHelper;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/28 22:43
 */
public class RecyclerViewHelper extends ScrollableViewHelper {

    @Override
    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
        if (scrollableView instanceof ViewPager) {
            MyFragmentAdapter view = (MyFragmentAdapter) ((ViewPager)scrollableView).getAdapter();
            Fragment fragment = view.getCurrentFragment();
            if(fragment != null){
                View fragmentView = fragment.getView();
                RecyclerView recyclerView = fragmentView.findViewById(R.id.fsm_rlv);
                if(isSlidingUp){
                    return recyclerView.computeVerticalScrollOffset();
                }
            }
        }
        return 0;
    }
}
