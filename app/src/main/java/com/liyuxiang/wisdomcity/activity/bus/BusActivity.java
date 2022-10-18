package com.liyuxiang.wisdomcity.activity.bus;

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
import com.liyuxiang.wisdomcity.activity.work.WorkDetailsActivity;
import com.liyuxiang.wisdomcity.adapter.BusAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Bus;
import com.liyuxiang.wisdomcity.commons.entity.WorkDetails;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.List;

public class BusActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "BusActivity";

    private ImageButton backBtn;
    private TextView top_title;
    private RecyclerView busRecycleView;
    private BusAdapter busAdapter;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        initView();
        initData();
        getBusList();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        busRecycleView = findViewById(R.id.busRecycleView);
        backBtn.setOnClickListener(this);
    }

    private void initData() {
        busRecycleView.setLayoutManager(new LinearLayoutManager(BusActivity.this, RecyclerView.VERTICAL, false));
        busAdapter = new BusAdapter();
        busAdapter.setItemOnClick(new BusAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                startActivity(new Intent(BusActivity.this,BusPayActivity.class).putExtra("id", id));
            }
        });
        busRecycleView.setAdapter(busAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    private void getBusList() {
        apiService.getBusList().enqueue(new Callback<RowsResponse<Bus>>() {
            @Override
            public void onResponse(Call<RowsResponse<Bus>> call, Response<RowsResponse<Bus>> response) {
                Log.i(TAG, "onResponse: response====>" + response.body().getRows());
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    busAdapter.setData(response.body().getRows());
                } else {
                    Toast.makeText(BusActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Bus>> call, Throwable t) {
                Toast.makeText(BusActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}