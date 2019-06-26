package com.bzh.widgets.banner;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/10 12:51
 */
public class Conversion {

    public static int getRatioDimension(Context context, int value, boolean isWidth) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        float STANDARD_WIDTH = 1080F;
        float STANDARD_HEIGHT = 1920F;
        if (isWidth) {
            return (int) (value / STANDARD_WIDTH * widthPixels);
        } else {
            return (int) (value / STANDARD_HEIGHT * heightPixels);
        }
    }
}
