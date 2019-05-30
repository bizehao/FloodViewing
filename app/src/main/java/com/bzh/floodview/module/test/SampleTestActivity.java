package com.bzh.floodview.module.test;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bzh.floodview.R;

import timber.log.Timber;

public class SampleTestActivity extends AppCompatActivity {

    private PopupWindow popupWindow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_test);
        TextView textView = findViewById(R.id.test_text);

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Timber.e("按下去了 展示文本的全称");
                        View contentView = LayoutInflater.from(v.getContext()).inflate(
                                com.bzh.widgets.R.layout.pop_showtext, (ViewGroup) v.getParent(), false);
                        /*AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                        dialog.setView(contentView);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.x = 100;
                        params.y = 50;
                        params.gravity = Gravity.TOP;
                        window.setAttributes(params);
                        dialog.show();*/

                        popupWindow = new PopupWindow(contentView,
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        popupWindow.setTouchable(true);
                        float mY = v.getY();

                        popupWindow.showAtLocation(v, Gravity.TOP, 0, (int) mY-30);


                        break;
                    case MotionEvent.ACTION_UP:
                        Timber.e("手松开了 取消展示的文字框");
                        Timber.e("popwindow宽度 " + popupWindow.getWidth());
                        Timber.e("popwindow高度 " + popupWindow.getHeight());
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        break;
                }
                return true;
            }
        });
    }
}
