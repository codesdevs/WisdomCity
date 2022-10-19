package com.liyuxiang.wisdomcity.activity.takeout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.parking.ParkingActivity;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.adapter.TakeOutSellerAdapter;
import com.liyuxiang.wisdomcity.adapter.TakeOutThemeAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.entity.Seller;
import com.liyuxiang.wisdomcity.commons.entity.TakeOutTheme;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.List;

public class TakeOutActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    private Banner banner;
    private RecyclerView themeRecycleView, sellerRecycleView;
    private TakeOutThemeAdapter themeAdapter;
    private TakeOutSellerAdapter sellerAdapter;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out);
        initView();
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        banner = (Banner) findViewById(R.id.banner);
        themeRecycleView = findViewById(R.id.themeRecycleView);
        sellerRecycleView = findViewById(R.id.sellerRecycleView);
        backBtn.setOnClickListener(this);
    }

    void initData() {
        getTakeOutBannerList();
        themeRecycleView.setLayoutManager(new GridLayoutManager(TakeOutActivity.this, 5));
        themeAdapter = new TakeOutThemeAdapter();
        themeRecycleView.setAdapter(themeAdapter);
        sellerRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        sellerAdapter = new TakeOutSellerAdapter();
        sellerRecycleView.setAdapter(sellerAdapter);
        sellerAdapter.setItemOnClick(new TakeOutSellerAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                startActivity(new Intent(TakeOutActivity.this,SellerDetailsActivity.class).putExtra("id", id));
            }
        });
        getTakeOutTheme();
        getNearSellerList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    void getTakeOutBannerList() {
        apiService.getTakeOutBannerList().enqueue(new Callback<RowsResponse<HomeBannerData>>() {
            @Override
            public void onResponse(Call<RowsResponse<HomeBannerData>> call, Response<RowsResponse<HomeBannerData>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    banner.addBannerLifecycleObserver(TakeOutActivity.this)
                            .setAdapter(new HomeFragmentBannerAdapter(response.body().getRows()))
                            .setIndicator(new CircleIndicator(TakeOutActivity.this));
                } else {
                    Toast.makeText(TakeOutActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<HomeBannerData>> call, Throwable t) {
                Toast.makeText(TakeOutActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getTakeOutTheme() {
        apiService.getTakeOutTheme().enqueue(new Callback<DataResponse<List<TakeOutTheme>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<TakeOutTheme>>> call, Response<DataResponse<List<TakeOutTheme>>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    themeAdapter.setData(response.body().getData());
                } else {
                    Toast.makeText(TakeOutActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<TakeOutTheme>>> call, Throwable t) {
                Toast.makeText(TakeOutActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getNearSellerList() {
        apiService.getNearSellerList().enqueue(new Callback<RowsResponse<Seller>>() {
            @Override
            public void onResponse(Call<RowsResponse<Seller>> call, Response<RowsResponse<Seller>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    sellerAdapter.setData(response.body().getRows());
                } else {
                    Toast.makeText(TakeOutActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Seller>> call, Throwable t) {
                Toast.makeText(TakeOutActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}