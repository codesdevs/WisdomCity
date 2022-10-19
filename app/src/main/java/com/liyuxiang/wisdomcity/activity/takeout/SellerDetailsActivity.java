package com.liyuxiang.wisdomcity.activity.takeout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.LoginActivity;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.SellerDetails;
import com.liyuxiang.wisdomcity.commons.repsone.BaseResponse;
import com.liyuxiang.wisdomcity.commons.repsone.CollectSellerResponse;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class SellerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    private ImageView cover;
    private TextView name;
    private TextView collect;
    private TextView score;
    private TextView introduction;
    private TextView address;
    int id = 2;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);
    CollectSellerResponse collectSellerResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);
        Bundle extras = getIntent().getExtras();
        id = (int) extras.get("id");
        initView();
        initData();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        cover = findViewById(R.id.cover);
        name = findViewById(R.id.name);
        collect = findViewById(R.id.collect);
        score = findViewById(R.id.score);
        introduction = findViewById(R.id.introduction);
        address = findViewById(R.id.address);

        backBtn.setOnClickListener(this);
    }

    private void initData() {
        getCheckCollect(id);
        getSellerDetails(id);
        collect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.collect:
                if (collect.getText().toString().equals("收藏")) {
                    //调用收藏接口
                    addCollectTakeOut();
                } else if (collect.getText().toString().equals("取消收藏")) {
                    deleteCollectTakeOut();
                }
                break;
        }
    }

    private void getSellerDetails(int id) {
        apiService.getSellerDetails(id).enqueue(new Callback<DataResponse<SellerDetails>>() {
            @Override
            public void onResponse(Call<DataResponse<SellerDetails>> call, Response<DataResponse<SellerDetails>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    SellerDetails sellerDetails = response.body().getData();
                    Glide.with(SellerDetailsActivity.this)
                            .load("http://124.93.196.45:10001" + sellerDetails.getImgUrl())
                            .thumbnail(Glide.with(SellerDetailsActivity.this).load(R.drawable.loading))
                            .error(R.drawable.loading)
                            .into(cover);
                    name.setText(sellerDetails.getName());
                    score.setText(sellerDetails.getScore().toString());
                    if (sellerDetails.getIntroduction() != null) {
                        introduction.setText(sellerDetails.getIntroduction());
                    } else {
                        introduction.setVisibility(View.GONE);
                    }
                    address.setText(sellerDetails.getAddress());
                } else {
                    Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<SellerDetails>> call, Throwable t) {
                Toast.makeText(SellerDetailsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCheckCollect(int id) {
        apiService.getCheckCollect(id).enqueue(new Callback<CollectSellerResponse>() {
            @Override
            public void onResponse(Call<CollectSellerResponse> call, Response<CollectSellerResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    collect.setText(response.body().getMsg().equals("未收藏") ? "收藏" : "取消收藏");
                    collectSellerResponse = response.body();
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Toast.makeText(SellerDetailsActivity.this, "登录身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SellerDetailsActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CollectSellerResponse> call, Throwable t) {

            }
        });
    }

    public void addCollectTakeOut() {
        Map<String, Object> map = new HashMap<>();
        map.put("sellerId", id);
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        apiService.addCollectTakeOut(body).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    collect.setText("取消收藏");
                    getCheckCollect(id);
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Toast.makeText(SellerDetailsActivity.this, "登录身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SellerDetailsActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(SellerDetailsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCollectTakeOut() {
        apiService.deleteCollectTakeOut(collectSellerResponse.getId()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    collect.setText("收藏");
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Toast.makeText(SellerDetailsActivity.this, "登录身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SellerDetailsActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SellerDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(SellerDetailsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}