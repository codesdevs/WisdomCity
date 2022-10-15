package com.liyuxiang.wisdomcity.fragment;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.activity.NewsDetailsActivity;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.NewsCategoryAdapter;
import com.liyuxiang.wisdomcity.adapter.NewsListAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.News;
import com.liyuxiang.wisdomcity.commons.entity.NewsCategory;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class NewsFragment extends BaseFragment {
    public static final String TAG = "NewsFragment";
    RecyclerView titleRecycleView, newsListRecycleView;
    NewsCategoryAdapter titleAdapter;
    NewsListAdapter newListAdapter;

    Retrofit retrofit = RetrofitManager.getRetrofit();
    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        titleRecycleView = mContent.findViewById(R.id.newCategoryTitleRecyclerView);
        newsListRecycleView = mContent.findViewById(R.id.newsListRecyclerView);
        titleAdapter = new NewsCategoryAdapter();
        newListAdapter = new NewsListAdapter();
    }

    @Override
    protected void initData() {
        getNewsListByType(9);
        getNewsCategoryList();
        titleRecycleView.setAdapter(titleAdapter);
        titleRecycleView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        titleAdapter.setItemClickListener(new NewsCategoryAdapter.ItemClickListener() {
            @Override
            public void click(int id, int position) {
                requireActivity().runOnUiThread(() -> {
                    titleAdapter.setCurrId(position);
                });
                getNewsListByType(id);
            }
        });
        newsListRecycleView.setAdapter(newListAdapter);
        newsListRecycleView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        newListAdapter.setItemClickListener(new NewsListAdapter.ItemClickListener() {
            @Override
            public void click(int id) {
                jumpPage(new Intent(requireContext(), NewsDetailsActivity.class).putExtra("id", id));
            }
        });

    }

    public void getNewsCategoryList() {
//        apiService.getNewsCategoryList().enqueue(new Callback<DataResponse<List<NewsCategory>>>() {
//            @Override
//            public void onResponse(Call<DataResponse<List<NewsCategory>>> call, Response<List<NewsCategory>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<NewsCategory>> call, Throwable t) {
//                showToast("加载失败，网络异常");
//            }
//        });
        apiService.getNewsCategoryList().enqueue(new Callback<DataResponse<List<NewsCategory>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<NewsCategory>>> call, Response<DataResponse<List<NewsCategory>>> response) {
                if (response.code() == 200) {
                    titleAdapter.setData(response.body().getData());
                } else {
                    showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<NewsCategory>>> call, Throwable t) {

            }
        });
    }

    public void getNewsListByType(int typeId) {
        apiService.getNewsListById(typeId).enqueue(new Callback<RowsResponse<News>>() {
            @Override
            public void onResponse(Call<RowsResponse<News>> call, Response<RowsResponse<News>> response) {
                if (response.code() == 200) {
                    newListAdapter.setData(response.body().getRows());
                } else {
                    showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<News>> call, Throwable t) {
                showToast("加载失败，网络异常");
            }
        });
    }
}
