package com.liyuxiang.wisdomcity.activity.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.adapter.ParkingAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.entity.Parking;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class ParkingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    private Banner banner;
    private RecyclerView parkingRecycleView;

    private ParkingAdapter parkingAdapter;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        initView();
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        banner = (Banner) findViewById(R.id.banner);
        parkingRecycleView = findViewById(R.id.parkingRecycleView);
        backBtn.setOnClickListener(this);
    }

    public void initData() {
        top_title.setText("");
        useBanner();
        getParkingList();
        parkingAdapter = new ParkingAdapter();
        parkingRecycleView.setLayoutManager(new LinearLayoutManager(ParkingActivity.this, RecyclerView.VERTICAL, false));
        parkingRecycleView.setAdapter(parkingAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    public void useBanner() {
        apiService.getParkBannerList().enqueue(new Callback<RowsResponse<HomeBannerData>>() {
            @Override
            public void onResponse(Call<RowsResponse<HomeBannerData>> call, Response<RowsResponse<HomeBannerData>> response) {

                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == 200) {
                    banner.addBannerLifecycleObserver(ParkingActivity.this)
                            .setAdapter(new HomeFragmentBannerAdapter(response.body().getRows()))
                            .setIndicator(new CircleIndicator(ParkingActivity.this));
                } else {
                    Toast.makeText(ParkingActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<HomeBannerData>> call, Throwable t) {
                Toast.makeText(ParkingActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getParkingList() {
        apiService.getParkingList().enqueue(new Callback<RowsResponse<Parking>>() {
            @Override
            public void onResponse(Call<RowsResponse<Parking>> call, Response<RowsResponse<Parking>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    parkingAdapter.setData(response.body().getRows());
                } else {
                    Toast.makeText(ParkingActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Parking>> call, Throwable t) {
                Toast.makeText(ParkingActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}