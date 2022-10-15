package com.liyuxiang.wisdomcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.User;
import com.liyuxiang.wisdomcity.commons.repsone.UserResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class UserInfoSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private ImageView avatar;
    private TextView top_title, id, username, nikeName, email, sex, idCard, balance;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);
        initView();
        initData();
    }

    public void getLoginUserInfo() {
        apiService.getLoginUserInfo().enqueue(new Callback<UserResponse<User>>() {
            @Override
            public void onResponse(Call<UserResponse<User>> call, Response<UserResponse<User>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    User user = response.body().getUser();
                    Glide.with(UserInfoSettingActivity.this)
                            .load("http://124.93.196.45:10001" + user.getAvatar())
                            .error(R.drawable.img_2)
                            .into(avatar);
                    id.setText(user.getUserId() + "");
                    username.setText(user.getUserName());
                    nikeName.setText(user.getNickName());
                    email.setText(user.getEmail());
                    sex.setText(user.getSex().equals("0") ? "男" : "女");
                    idCard.setText(user.getIdCard());
                    balance.setText(user.getBalance() + "");
                } else {
                    if (response.body().getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Toast.makeText(UserInfoSettingActivity.this, "登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserInfoSettingActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse<User>> call, Throwable t) {
                Toast.makeText(UserInfoSettingActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        avatar = findViewById(R.id.avatar);
        id = findViewById(R.id.id);
        username = findViewById(R.id.username);
        nikeName = findViewById(R.id.nikeName);
        email = findViewById(R.id.email);
        sex = findViewById(R.id.sex);
        idCard = findViewById(R.id.idCard);
        balance = findViewById(R.id.balance);
        backBtn.setOnClickListener(this);
    }

    public void initData() {
        top_title.setText("个人信息");
        getLoginUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }
}