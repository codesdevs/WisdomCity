package com.liyuxiang.wisdomcity.activity.bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.LoginActivity;
import com.liyuxiang.wisdomcity.activity.UserInfoSettingActivity;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Bus;
import com.liyuxiang.wisdomcity.commons.entity.BusStop;
import com.liyuxiang.wisdomcity.commons.entity.User;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.commons.repsone.UserResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.liyuxiang.wisdomcity.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class BusPayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title, userName, userPhone;
    private Spinner upBus, downBus;
    private Button payBtn;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pay);
        Bundle extras = getIntent().getExtras();
        id = (int) extras.get("id");
        initView();
        initData();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        upBus = findViewById(R.id.upBus);
        downBus = findViewById(R.id.downBus);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        payBtn = findViewById(R.id.payBtn);

        backBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);
    }

    private void initData() {
        getLoginUserInfo();
        getBusStopList(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.payBtn:

                break;
        }
    }

    private void getBusStopList(int id) {
        apiService.getBusStopList(id).enqueue(new Callback<RowsResponse<BusStop>>() {
            @Override
            public void onResponse(Call<RowsResponse<BusStop>> call, Response<RowsResponse<BusStop>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    List<BusStop> rows = response.body().getRows();
                    List<String> nameList = new ArrayList<>();
                    for (int i = 0; i < rows.size(); i++) {
                        nameList.add(rows.get(i).getName());
                    }
                    upBus.setAdapter(new ArrayAdapter<>(BusPayActivity.this, R.layout.spinner_item, nameList));
                    downBus.setAdapter(new ArrayAdapter<>(BusPayActivity.this, R.layout.spinner_item, nameList));
                    if (nameList.size() > 0) {
                        upBus.setSelection(0);
                        downBus.setSelection(1);
                    }
                } else {
                    Toast.makeText(BusPayActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<BusStop>> call, Throwable t) {
                Toast.makeText(BusPayActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLoginUserInfo() {
        apiService.getLoginUserInfo().enqueue(new Callback<UserResponse<User>>() {
            @Override
            public void onResponse(Call<UserResponse<User>> call, Response<UserResponse<User>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    User user = response.body().getUser();
                    userName.setText(user.getUserName());
                    userPhone.setText(user.getPhonenumber());
                } else {
                    if (response.body().getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Toast.makeText(BusPayActivity.this, "登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                        SPUtils.getInstance(BusPayActivity.this).remove("token");
                        startActivity(new Intent(BusPayActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse<User>> call, Throwable t) {
                Toast.makeText(BusPayActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}