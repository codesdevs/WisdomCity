package com.liyuxiang.wisdomcity.activity;

import android.content.Intent;
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
import com.liyuxiang.wisdomcity.commons.entity.UserRegister;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "RegisterActivity";

    private EditText username, password, phoneNumber;
    private RadioButton sex_man, sex_woman;
    private Button registerBtn, getLoginUserInfo;
    private int sex = 0;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        sex_man = (RadioButton) findViewById(R.id.sex_man);
        sex_woman = (RadioButton) findViewById(R.id.sex_woman);
        sex_man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.sex_woman) {
                    sex = 1;
                }
            }
        });
        registerBtn = (Button) findViewById(R.id.registerBtn);
//        getLoginUserInfo = (Button) findViewById(R.id.getLoginUserInfo);
        registerBtn.setOnClickListener(this);
//        getLoginUserInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                submit();
                break;
//            case R.id.getLoginUserInfo:
//                apiService.getLoginUserInfo().enqueue(new Callback<UserResponse<User>>() {
//                    @Override
//                    public void onResponse(Call<UserResponse<User>> call, Response<UserResponse<User>> response) {
//                        Log.i(TAG, "onResponse: response.code===>" + response.code());
//                        Log.i(TAG, "onResponse: response.message===>" + response.body().getUser());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserResponse<User>> call, Throwable t) {
//
//                    }
//                });
//                break;
        }
    }

    private void submit() {
        // validate
        String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String phoneNumberString = phoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumberString)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRegister userRegister = new UserRegister();
        userRegister.setUserName(usernameString);
        userRegister.setPassword(passwordString);
        userRegister.setPhonenumber(phoneNumberString);
        userRegister.setSex(sex + "");
        apiService.register(userRegister).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: response.code===>" + response.code());
                try {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    int code = Integer.parseInt(jsonObject.get("code").toString());
                    if (response.code() == HttpURLConnection.HTTP_OK && code == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(RegisterActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.i(TAG, "onFailure: " + call);
                Log.i(TAG, "onFailure: " + throwable);
                Toast.makeText(RegisterActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }
}