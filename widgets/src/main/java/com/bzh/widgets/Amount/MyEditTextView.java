package com.bzh.widgets.Amount;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import java.sql.Time;
import java.util.Timer;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/21 21:44
 */
public class MyEditTextView extends android.support.v7.widget.AppCompatEditText {
    public MyEditTextView(Context context) {
        super(context);
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        System.out.println("键盘弹出");
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            Log.i("main_activity", "键盘向下 ");
            super.onKeyPreIme(keyCode, event);
            clearFocus();
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
