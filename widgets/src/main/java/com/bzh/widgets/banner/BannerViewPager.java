package com.bzh.widgets.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.widgets.R;
import com.bzh.widgets.common.ImageLoaderInterface;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 轮播图
 * @time 2018/11/10 10:55
 */
public class BannerViewPager extends FrameLayout {

    private ViewPager mViewPager;
    private LinearLayout mIndicators; //指示器容器
    private TextView mTitle; //标题

    private ImageLoaderInterface mImageLoader;
    private BannerAdapter mBannerAdapter;
    private int mItemCount; //图片标题的个数
    private BaseIndicator mIndicatorView; //指示器
    private boolean mHaveTitle; //是否展示标题
    private OnBannerItemClickListener mOnBannerClickListener;
    private boolean mAutoPlay; //自动播放
    private static final int MSG_WHAT = 0;
    private int delayMillis; //间隔时间
    private ViewPager.PageTransformer mPageTransformer; //切换效果
    private ViewPagerScroller scroller; //切换速度

    public BannerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public BannerViewPager(@NonNull final Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHaveTitle = true;
        mAutoPlay = true;
        mItemCount = 1;
        delayMillis = 5000;
        initView();
        initListener();
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, delayMillis);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mAutoPlay) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1,true);
                this.sendEmptyMessageDelayed(MSG_WHAT, delayMillis);
            }
        }
    };

    private void initView(){
        View.inflate(getContext(), R.layout.layout_bannerviewpager, this);
        mViewPager = findViewById(R.id.viewPager);
        mIndicators = findViewById(R.id.bannerIndicators);
        mTitle = findViewById(R.id.bannerTitle);
        setPageSpeed(2000);
    }

    private void initListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTitleSlogan(position);
                for (int i = 0; i < mIndicators.getChildCount(); i++) {
                    if (i == position % mItemCount) {
                        ((BaseIndicator) mIndicators.getChildAt(i)).setState(true);
                    } else {
                        ((BaseIndicator) mIndicators.getChildAt(i)).setState(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public BannerViewPager setData(List<BannerItemBean> data, ImageLoaderInterface imageLoader) {
        mImageLoader = imageLoader;
        mBannerAdapter = new BannerAdapter(this);
        mBannerAdapter.setData(data);
        mItemCount = data.size();
        mViewPager.setAdapter(mBannerAdapter);
        mViewPager.setCurrentItem(mItemCount * 1000);
        setIndicators(data.size()); //设置数据(图片)
        setTitleSlogan(0); //设置标题
        return this;
    }

    public void displayImg(Context context, ImageView imageView, Object s) {
        mImageLoader.displayImage(context, s, imageView);
    }

    private void setTitleSlogan(int i) {
        if (mHaveTitle) {
            if (mTitle.getVisibility() == GONE) {
                mTitle.setVisibility(VISIBLE);
            }
            String s = mBannerAdapter.getData().get(i % mItemCount).getTitle();
            mTitle.setText(s);
        } else if (mTitle.getVisibility() == VISIBLE) {
            mTitle.setVisibility(GONE);
        }
    }

    private void setIndicators(int dataCount) {
        mIndicators.removeAllViews();
        for (int i = 0; i < dataCount; i++) {
            if (mIndicatorView == null) {
                Indicator indicator = new Indicator(getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (Conversion.getRatioDimension(getContext(), 20, false),
                                Conversion.getRatioDimension(getContext(), 20, false));
                layoutParams.setMargins(Conversion.getRatioDimension(getContext(), 10, true), 0,
                        Conversion.getRatioDimension(getContext(), 10, true), 0);
                indicator.setLayoutParams(layoutParams);
                mIndicators.addView(indicator);
            } else {
                //Log.e("iii", "count" + i);
                BaseIndicator baseIndicator = mIndicatorView;
                ViewParent vp = baseIndicator.getParent();
                if (vp != null) {
                    ViewGroup parent = (ViewGroup) vp;
                    parent.removeView(baseIndicator);
                }
                mIndicators.addView(baseIndicator);
                //Log.e("iii", "" + mIndicators.getChildCount());
            }
        }
        //Log.e("iii", "" + mIndicators.getChildCount());
        ((BaseIndicator) mIndicators.getChildAt(0)).setState(true);
    }

    //定义轮播图点击事件
    public interface OnBannerItemClickListener {
        void OnClickLister(View view, int currentItem);
    }

    public BannerViewPager setOnBannerItemClickListener(OnBannerItemClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
        return this;
    }

    //轮播点击事件
    protected void OnBannerItemClick(View view) {
        if (mOnBannerClickListener != null) {
            mOnBannerClickListener.OnClickLister(view, mViewPager.getCurrentItem() % mItemCount);
        }
    }

    //是否显示标题
    public BannerViewPager setHaveTitle(boolean haveTitle) {
        mHaveTitle = haveTitle;
        setTitleSlogan(mViewPager.getCurrentItem());
        return this;
    }

    //设置自动播放的间隔时间
    public BannerViewPager setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
        return this;
    }

    //获取自动播放的状态
    public boolean isAutoPlay() {
        return mAutoPlay;
    }

    //设置自动播放
    public BannerViewPager setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
        return this;
    }

    //设置切换效果
    public BannerViewPager setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        mPageTransformer = pageTransformer;
        mViewPager.setPageTransformer(true, mPageTransformer);
        return this;
    }

    //设置切换速度
    public BannerViewPager setPageSpeed(int speed){
        if(scroller == null){
            scroller = new ViewPagerScroller(getContext());
        }
        scroller.setScrollDuration(speed);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒
        return this;
    }
}
