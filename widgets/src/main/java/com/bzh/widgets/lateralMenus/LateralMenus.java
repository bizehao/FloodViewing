package com.bzh.widgets.lateralMenus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.widgets.R;
import com.bzh.widgets.common.ImageLoaderInterface;
import com.bzh.widgets.util.WidgetUtils;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 横向菜单
 * @time 2018/11/10 22:14
 */
public class LateralMenus extends HorizontalScrollView {

    private LinearLayout mainLinearLayout1;
    private LinearLayout mainLinearLayout2;

    private ImageLoaderInterface mImageLoader;
    private List<LaterMenusBean> datas;
    private OnMenusItemClickListener listener;

    public LateralMenus(Context context) {
        this(context, null);
    }

    public LateralMenus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_latermenus, this);
        mainLinearLayout1 = findViewById(R.id.later_menu_layout1);
        mainLinearLayout2 = findViewById(R.id.later_menu_layout2);
        mainLinearLayout1.getLayoutParams().width = 2 * WidgetUtils.getScreenWidth(getContext());
        mainLinearLayout2.getLayoutParams().width = 2 * WidgetUtils.getScreenWidth(getContext());
    }

    public LateralMenus setData(List<LaterMenusBean> data, ImageLoaderInterface imageLoader) {
        this.mImageLoader = imageLoader;
        this.datas = data;
        return this;
    }

    public void build() {
        if (datas != null && datas.size() > 0) {
            LinearLayout linearLayout;
            ImageView imageView;
            TextView textView;
            for (int i = 0; i < datas.size(); i++) {
                linearLayout = new LinearLayout(getContext());
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                linearLayout.setLayoutParams(lineParams);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final int finalI = i;
                linearLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnClickLister(v, finalI);
                    }
                });
                if (i % 2 == 0) {
                    mainLinearLayout1.addView(linearLayout);
                } else {
                    mainLinearLayout2.addView(linearLayout);
                }

                imageView = new ImageView(getContext());
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(imgParams);
                mImageLoader.displayImage(getContext(), datas.get(i).getImg_path(), imageView);
                linearLayout.addView(imageView);

                textView = new TextView(getContext());
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(textParams);
                textView.setGravity(Gravity.CENTER);
                textView.setText(datas.get(i).getTitle());
                linearLayout.addView(textView);
            }
        }

    }

    //定义点击事件
    public interface OnMenusItemClickListener {
        void OnClickLister(View view, int currentItem);
    }

    public LateralMenus setListener(OnMenusItemClickListener listener) {
        this.listener = listener;
        return this;
    }
}
