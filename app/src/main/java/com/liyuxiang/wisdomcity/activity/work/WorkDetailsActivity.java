package com.liyuxiang.wisdomcity.activity.work;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.WorkDetails;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class WorkDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "WorkDetailsActivity";
    int id;

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);
    private ImageButton backBtn;
    private TextView top_title, name, salary, address, contacts, companyName, obligation, need;
    private Button sendCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);
        initView();
        Bundle extras = getIntent().getExtras();
        id = (int) extras.get("id");
        initData();
    }

    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        top_title = (TextView) findViewById(R.id.top_title);
        name = (TextView) findViewById(R.id.name);
        salary = (TextView) findViewById(R.id.salary);
        address = (TextView) findViewById(R.id.address);
        contacts = (TextView) findViewById(R.id.contacts);
        companyName = (TextView) findViewById(R.id.companyName);
        obligation = (TextView) findViewById(R.id.obligation);
        need = (TextView) findViewById(R.id.need);
        sendCV = (Button) findViewById(R.id.sendCV);

        backBtn.setOnClickListener(this);
        sendCV.setOnClickListener(this);
    }

    void initData() {
        top_title.setText("");
        getWorkDetails();
    }

    public void getWorkDetails() {
        apiService.getWorkDetails(id).enqueue(new Callback<DataResponse<WorkDetails>>() {
            @Override
            public void onResponse(Call<DataResponse<WorkDetails>> call, Response<DataResponse<WorkDetails>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, "onResponse: response====>" + response.body().getData());
                    WorkDetails workDetails = response.body().getData();
                    name.setText(workDetails.getName());
                    salary.setText(workDetails.getSalary());
                    address.setText(workDetails.getAddress());
                    contacts.setText(workDetails.getContacts());
                    companyName.setText(String.valueOf(workDetails.getCompanyName()));
                    obligation.setText(workDetails.getObligation());
                    need.setText(workDetails.getNeed());
                } else {
                    Toast.makeText(WorkDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<WorkDetails>> call, Throwable t) {
                Toast.makeText(WorkDetailsActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.sendCV:

                break;
        }
    }
}