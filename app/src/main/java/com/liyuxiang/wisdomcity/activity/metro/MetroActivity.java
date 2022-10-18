package com.liyuxiang.wisdomcity.activity.metro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.movie.MovieActivity;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.adapter.MetroListAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.entity.MetroList;
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

public class MetroActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MetroActivity";

    private ImageButton backBtn;
    private TextView top_title;
    private Banner banner;
    private RecyclerView metroRecycleView;
    private MetroListAdapter metroListAdapter;
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
        metroRecycleView = findViewById(R.id.metroRecycleView);
        backBtn.setOnClickListener(this);
    }

    void initData() {
        top_title.setText("城市地铁");
        metroRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        metroListAdapter = new MetroListAdapter();
        metroListAdapter.setItemOnClick(new MetroListAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                Log.i(TAG, "onClick: id===>" + id);
                startActivity(new Intent(MetroActivity.this, MetroDetailsActivity.class).putExtra("id", id));
            }
        });
        metroRecycleView.setAdapter(metroListAdapter);
        getMetroBannerList();
        getMetroList("建国门");
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

    void getMetroList(String currentName) {
        apiService.getMetroList(currentName).enqueue(new Callback<DataResponse<List<MetroList>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<MetroList>>> call, Response<DataResponse<List<MetroList>>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    metroListAdapter.setData(response.body().getData());
                } else {
                    Toast.makeText(MetroActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<MetroList>>> call, Throwable t) {
                Toast.makeText(MetroActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}