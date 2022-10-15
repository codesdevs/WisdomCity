package com.liyuxiang.wisdomcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Password;
import com.liyuxiang.wisdomcity.commons.repsone.BaseResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    private EditText oldPassword, newPassword, confirmPassword;
    private Button submit;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        initData();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        submit = findViewById(R.id.submit);

        backBtn.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void initData() {
        top_title.setText("修改密码");
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
        String oldPasswordString = oldPassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldPasswordString)) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPasswordString = newPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPasswordString)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmPasswordString = confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPasswordString)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPasswordString.length() < 6) {
            Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPasswordString.equals(confirmPasswordString)) {
            Toast.makeText(this, "确认密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
        //提交密码
        resetPwd(new Password(newPasswordString, oldPasswordString));

    }

    public void resetPwd(Password pwd) {
        apiService.resetPwd(pwd).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Gson gson = new Gson();
                try {
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    int code = Integer.parseInt(jsonObject.get("code").toString());
                    if (response.code() == HttpURLConnection.HTTP_OK && code == 200) {
                        Toast.makeText(ChangePasswordActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        ChangePasswordActivity.this.finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "修改失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

}