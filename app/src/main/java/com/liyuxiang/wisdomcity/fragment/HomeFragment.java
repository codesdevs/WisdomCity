package com.liyuxiang.wisdomcity.fragment;

import android.content.Intent;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.activity.NewsDetailsActivity;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.metro.MetroActivity;
import com.liyuxiang.wisdomcity.activity.movie.MovieActivity;
import com.liyuxiang.wisdomcity.activity.parking.ParkingActivity;
import com.liyuxiang.wisdomcity.activity.work.WorkActivity;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.adapter.NewsCategoryAdapter;
import com.liyuxiang.wisdomcity.adapter.NewsListAdapter;
import com.liyuxiang.wisdomcity.adapter.ServiceAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.entity.News;
import com.liyuxiang.wisdomcity.commons.entity.NewsCategory;
import com.liyuxiang.wisdomcity.commons.entity.Service;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.HttpURLConnection;
import java.util.List;

public class HomeFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    RecyclerView titleRecycleView, newsListRecycleView, serviceRecycleView;
    NewsCategoryAdapter titleAdapter;
    NewsListAdapter newListAdapter;
    ServiceAdapter serviceAdapter;

    Retrofit retrofit = RetrofitManager.getRetrofit();
    ApiService apiService = retrofit.create(ApiService.class);

    Banner banner;

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        banner.stop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: ");
        super.onDestroyView();
        banner.destroy();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        titleRecycleView = mContent.findViewById(R.id.newCategoryTitleRecyclerView);
        newsListRecycleView = mContent.findViewById(R.id.newsListRecyclerView);
        serviceRecycleView = mContent.findViewById(R.id.serviceRecycleView);
        titleAdapter = new NewsCategoryAdapter();
        newListAdapter = new NewsListAdapter();
        serviceAdapter = new ServiceAdapter();
        banner = mContent.findViewById(R.id.banner);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        useBanner();
        getNewsCategoryList();
        getServiceList();
        titleRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        titleRecycleView.setAdapter(titleAdapter);
        newsListRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        serviceRecycleView.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        serviceRecycleView.setAdapter(serviceAdapter);
        titleAdapter.setItemClickListener(new NewsCategoryAdapter.ItemClickListener() {
            @Override
            public void click(int id, int position) {
                Log.i(TAG, "click: id===>" + id);
                requireActivity().runOnUiThread(() -> {
                    titleAdapter.setCurrId(position);
                });
                getNewsListByType(id);
            }
        });
        newsListRecycleView.setAdapter(newListAdapter);
        newListAdapter.setItemClickListener(new NewsListAdapter.ItemClickListener() {
            @Override
            public void click(int id) {
                jumpPage(new Intent(requireActivity(), NewsDetailsActivity.class).putExtra("id", id));
            }
        });
        serviceAdapter.setItemOnClick(new ServiceAdapter.ItemOnClick() {
            @Override
            public void onClick(int id) {
                switch (id) {
                    case 17:
                        jumpPage(new Intent(requireActivity(), ParkingActivity.class));
                        break;
                    case 21:
                        jumpPage(new Intent(requireActivity(), WorkActivity.class));
                        break;
                    case 18:
                        jumpPage(new Intent(requireActivity(), MovieActivity.class));
                        break;
                    case 2:
                        jumpPage(new Intent(requireActivity(), MetroActivity.class));
                        break;
                }
            }
        });
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

    public void getNewsCategoryList() {
        apiService.getNewsCategoryList().enqueue(new Callback<DataResponse<List<NewsCategory>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<NewsCategory>>> call, Response<DataResponse<List<NewsCategory>>> response) {
                if (response.code() == 200) {
                    showToast(response.body().getMsg());
                    List<NewsCategory> data = response.body().getData();
                    titleAdapter.setData(data);
                    getNewsListByType(9);
                } else {
                    showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<NewsCategory>>> call, Throwable t) {
                showToast("数据加载失败");
            }
        });
    }

    public void getNewsListByType(int typeId) {
        apiService.getNewsListById(typeId).enqueue(new Callback<RowsResponse<News>>() {
            @Override
            public void onResponse(Call<RowsResponse<News>> call, Response<RowsResponse<News>> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: " + response.body().getRows());
                    newListAdapter.setData(response.body().getRows());
                } else {
                    showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<News>> call, Throwable t) {
                showToast("数据加载失败");
            }
        });
    }

    public void useBanner() {
        apiService.getHomeBannerList().enqueue(new Callback<RowsResponse<HomeBannerData>>() {
            @Override
            public void onResponse(Call<RowsResponse<HomeBannerData>> call, Response<RowsResponse<HomeBannerData>> response) {
                if (response.code() == 200) {
                    List<HomeBannerData> data = response.body().getRows();
                    HomeFragmentBannerAdapter homeFragmentBannerAdapter = new HomeFragmentBannerAdapter(data);
                    banner.addBannerLifecycleObserver(HomeFragment.this)//添加生命周期观察者
                            .setAdapter(homeFragmentBannerAdapter)
                            .setIndicator(new CircleIndicator(HomeFragment.this.getContext()));
                    homeFragmentBannerAdapter.setItemOnclick(new HomeFragmentBannerAdapter.ItemOnclick() {
                        @Override
                        public void onClick(int id) {
                            jumpPage(new Intent(requireActivity(), NewsDetailsActivity.class).putExtra("id", id));
                        }
                    });
                } else {
                    showToast("数据加载失败");
                }
            }


            @Override
            public void onFailure(Call<RowsResponse<HomeBannerData>> call, Throwable t) {
                showToast("数据加载失败，网络异常");
            }
        });

    }
}
