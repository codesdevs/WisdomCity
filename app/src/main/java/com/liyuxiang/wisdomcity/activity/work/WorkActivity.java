package com.liyuxiang.wisdomcity.activity.work;

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
import com.liyuxiang.wisdomcity.adapter.WorkAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Work;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title;
    RecyclerView workRecycleView;
    WorkAdapter workAdapter;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        initView();
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        workRecycleView = findViewById(R.id.workRecycleView);
        backBtn.setOnClickListener(this);
    }

    public void initData() {
        top_title.setText("");
        workAdapter = new WorkAdapter();
        workAdapter.setItemOnClick(new WorkAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                startActivity(new Intent(WorkActivity.this, WorkDetailsActivity.class).putExtra("id", id));
            }
        });
        getWorkList();
        workRecycleView.setLayoutManager(new LinearLayoutManager(WorkActivity.this, RecyclerView.VERTICAL, false));
        workRecycleView.setAdapter(workAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    public void getWorkList() {
        apiService.getWorkList().enqueue(new Callback<RowsResponse<Work>>() {
            @Override
            public void onResponse(Call<RowsResponse<Work>> call, Response<RowsResponse<Work>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    workAdapter.setData(response.body().getRows());
                } else {
                    Toast.makeText(WorkActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Work>> call, Throwable t) {
                Toast.makeText(WorkActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}