package com.bzh.floodview.sideslip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bzh.floodview.R;
import com.bzh.floodview.base.activity.BaseActivity;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Button buttonfanhui=findViewById(R.id.fa_fanhui);
        buttonfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
