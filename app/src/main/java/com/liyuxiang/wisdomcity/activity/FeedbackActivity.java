package com.liyuxiang.wisdomcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Feedback;
import com.liyuxiang.wisdomcity.commons.repsone.BaseResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "FeedbackActivity";

    private ImageButton backBtn;
    private TextView top_title;
    private EditText title, content;
    private Button submit;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        submit = (Button) findViewById(R.id.submit);

        backBtn.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void initData() {
        top_title.setText("意见反馈");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String titleString = title.getText().toString().trim();
        if (TextUtils.isEmpty(titleString)) {
            Toast.makeText(this, "请输入反馈标题", Toast.LENGTH_SHORT).show();
            return;
        }

        String contentString = content.getText().toString().trim();
        if (TextUtils.isEmpty(contentString)) {
            Toast.makeText(this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contentString.length() >= 150) {
            Toast.makeText(this, "反馈内容不得超过150字", Toast.LENGTH_SHORT).show();
            return;
        }

        submitFeedback(new Feedback(titleString, contentString));


    }

    public void submitFeedback(Feedback feedback) {
        apiService.feedback(feedback).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: response===>" + response.body().toString());
                Gson gson = new Gson();
                try {
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    int code = Integer.parseInt(jsonObject.get("code").toString());
                    if (response.code() == HttpURLConnection.HTTP_OK && code == 200) {
                        Toast.makeText(FeedbackActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(FeedbackActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "提交失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}