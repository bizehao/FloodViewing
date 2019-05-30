package com.bzh.widgets.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * User: bizehao
 * Date: 2019-05-23
 * Time: 下午4:55
 * Description:
 */
public class SuperTextView extends android.support.v7.widget.AppCompatTextView {
    public SuperTextView(Context context) {
        super(context);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TextView默认设置是第一个获取到的光标，
    //如果想让所有的TextView都有跑马灯效果,则让所有的TextView都获取到光标就行了
    //这里return true 就是让所有的TextView都获取到光标
    @Override
    public boolean isFocused() {
        return true;
    }
}
