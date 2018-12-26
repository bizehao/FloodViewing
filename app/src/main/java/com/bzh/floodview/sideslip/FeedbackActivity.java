package com.bzh.floodview.sideslip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bzh.floodview.App;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.back.Feedback;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feedback;
    }

    @BindView(R.id.fa_fanhui)
    Button buttonfanhui;

    @BindView(R.id.fa_submission)
    Button buttonsubmission;

    @BindView(R.id.edittextfb)
    EditText edittextfb;
    @Inject
    RetrofitHelper helper;

    @Override
    protected void initView(Bundle savedInstanceState) {

        buttonfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonsubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents=edittextfb.getText().toString();
                if (contents==null || contents.equals("")){
                    Toast.makeText(FeedbackActivity.this,"您输入的内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e(TAG, "onClick: ------"+ App.getUsername() );
                    Observable<BaseApi<Boolean>> observable =helper.getServer().getaddFeedBack(contents, App.getUsername());
                    helper.requestHandler(observable, FeedbackActivity.this, new RetrofitHelper.callHandler<BaseApi<Boolean>>() {
                        @Override
                        public void run(BaseApi<Boolean> feedbackBaseApi) {
                            Boolean boo=feedbackBaseApi.getData();
                            if (boo){
                                Toast.makeText(FeedbackActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(FeedbackActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private static final String TAG = "FeedbackActivity";


}
