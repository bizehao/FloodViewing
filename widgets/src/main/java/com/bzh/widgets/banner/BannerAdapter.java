package com.bzh.widgets.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/12/6 11:20.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class BannerAdapter extends PagerAdapter {

    private BannerViewPager bannerViewPager;
    private int mItemCount = 1;
    private List<BannerItemBean> mData;
    private ImageView.ScaleType mScaleType;

    public List<BannerItemBean> getData() {
        return mData;
    }

    public void setData(List<BannerItemBean> data) {
        mData = data;
        if (mData != null && mData.size() != 0) {
            mItemCount = mData.size();
        }
    }

    private ImageView.ScaleType getScaleType() {
        return mScaleType == null ? ImageView.ScaleType.CENTER_CROP : mScaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
    }

    BannerAdapter(BannerViewPager bannerViewPager) {
        this.bannerViewPager = bannerViewPager;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((ImageView) object));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(getScaleType());
        bannerViewPager.displayImg(container.getContext(), imageView, mData.get(position % mItemCount).getImg_path());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerViewPager.OnBannerItemClick(view);
            }
        });
        container.addView(imageView);
        return imageView;
    }

}
