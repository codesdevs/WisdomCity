package com.liyuxiang.wisdomcity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.bus.BusActivity;
import com.liyuxiang.wisdomcity.activity.metro.MetroActivity;
import com.liyuxiang.wisdomcity.activity.movie.MovieActivity;
import com.liyuxiang.wisdomcity.activity.parking.ParkingActivity;
import com.liyuxiang.wisdomcity.activity.takeout.TakeOutActivity;
import com.liyuxiang.wisdomcity.activity.work.WorkActivity;
import com.liyuxiang.wisdomcity.adapter.ServiceAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.Service;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.fragment.BaseFragment;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class AllServiceFragment extends BaseFragment {


    public static final String TAG = "AllServiceFragment";
    RecyclerView serviceRecycleView;
    ServiceAdapter serviceAdapter;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected int initLayout() {
        return R.layout.fragment_all_service;
    }

    @Override
    protected void initView() {
        serviceRecycleView = mContent.findViewById(R.id.serviceRecycleView);
        serviceAdapter = new ServiceAdapter();
    }

    @Override
    protected void initData() {

        serviceRecycleView.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        serviceAdapter.setItemOnClick(new ServiceAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                Intent intent = null;
                switch (id) {
                    case 17:
                        intent = new Intent(requireActivity(), ParkingActivity.class);
                        break;
                    case 21:
                        intent = new Intent(requireActivity(), WorkActivity.class);
                        break;
                    case 18:
                        intent = new Intent(requireActivity(), MovieActivity.class);
                        break;
                    case 2:
                        intent = new Intent(requireActivity(), MetroActivity.class);
                        break;
                    case 19:
                        intent = new Intent(requireActivity(), TakeOutActivity.class);
                        break;
                    case 3:
                        jumpPage(new Intent(requireActivity(), BusActivity.class));
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
        serviceRecycleView.setAdapter(serviceAdapter);
        getServiceList();
    }

    private void getServiceList() {
        apiService.getServiceList().enqueue(new Callback<RowsResponse<Service>>() {
            @Override
            public void onResponse(Call<RowsResponse<Service>> call, Response<RowsResponse<Service>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    serviceAdapter.setData(response.body().getRows());
                } else {
                    showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Service>> call, Throwable t) {
                showToast("数据加载失败，网络异常");
            }
        });
    }
}