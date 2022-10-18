package com.liyuxiang.wisdomcity.activity.metro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.MetroDetailsAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.MetroDetails;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class MetroDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView top_title, endName, startTime, endTime;
    private ImageButton backBtn;
    private RecyclerView metroDetailsRecycleView;
    private MetroDetailsAdapter metroDetailsAdapter;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_details);
        initView();
        Bundle extras = getIntent().getExtras();
        int id = (int) extras.get("id");
        initData();
        getMetroDetailsList(id);
    }

    private void initView() {
        top_title = findViewById(R.id.top_title);
        backBtn = findViewById(R.id.backBtn);
        endName = findViewById(R.id.endName);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        backBtn.setOnClickListener(this);
        metroDetailsRecycleView = findViewById(R.id.metroDetailsRecycleView);
    }

    private void initData() {
        metroDetailsAdapter = new MetroDetailsAdapter();
        metroDetailsRecycleView.setLayoutManager(new LinearLayoutManager(MetroDetailsActivity.this, RecyclerView.HORIZONTAL, false));
        metroDetailsRecycleView.setAdapter(metroDetailsAdapter);
    }


    private void getMetroDetailsList(int id) {
        apiService.getMetroDetailsList(id).enqueue(new Callback<DataResponse<MetroDetails>>() {
            @Override
            public void onResponse(Call<DataResponse<MetroDetails>> call, Response<DataResponse<MetroDetails>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    MetroDetails data = response.body().getData();
                    metroDetailsAdapter.setData(data.getMetroStepList());
                    top_title.setText(data.getName());
                    endName.setText(data.getEnd());
                    startTime.setText(data.getStartTime());
                    endTime.setText(data.getEndTime());
                } else {
                    Toast.makeText(MetroDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<MetroDetails>> call, Throwable t) {
                Toast.makeText(MetroDetailsActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
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