package com.liyuxiang.wisdomcity.activity.metro;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.movie.MovieActivity;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class MetroActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    private Banner banner;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);
        initView();
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        banner = (Banner) findViewById(R.id.banner);
        backBtn.setOnClickListener(this);
    }

    void initData() {
        getMetroBannerList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    void getMetroBannerList() {
        apiService.getMetroBannerList().enqueue(new Callback<RowsResponse<HomeBannerData>>() {
            @Override
            public void onResponse(Call<RowsResponse<HomeBannerData>> call, Response<RowsResponse<HomeBannerData>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    banner.addBannerLifecycleObserver(MetroActivity.this)
                            .setAdapter(new HomeFragmentBannerAdapter(response.body().getRows()))
                            .setIndicator(new CircleIndicator(MetroActivity.this));
                } else {
                    Toast.makeText(MetroActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<HomeBannerData>> call, Throwable t) {
                Toast.makeText(MetroActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();

            }
        });
    }
}