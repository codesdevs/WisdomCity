package com.liyuxiang.wisdomcity.activity.movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.HomeFragmentBannerAdapter;
import com.liyuxiang.wisdomcity.adapter.MovieAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.liyuxiang.wisdomcity.commons.entity.Movie;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MovieActivity";

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);
    private Banner banner;
    private RecyclerView hotMovieRecycleView, previewMovieRecycleView;
    private MovieAdapter movieAdapter, previewMovieAdapter;
    private ImageButton backBtn;
    private TextView top_title;

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
        setContentView(R.layout.activity_movie);
        initView();
        initData();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
        top_title = findViewById(R.id.top_title);
        banner = findViewById(R.id.banner);
        hotMovieRecycleView = findViewById(R.id.hotMovieRecycleView);
        previewMovieRecycleView = findViewById(R.id.previewMovieRecycleView);
        hotMovieRecycleView.setLayoutManager(new LinearLayoutManager(MovieActivity.this, RecyclerView.HORIZONTAL, false));
        previewMovieRecycleView.setLayoutManager(new LinearLayoutManager(MovieActivity.this, RecyclerView.HORIZONTAL, false));
    }

    void initData() {
        movieAdapter = new MovieAdapter();
        previewMovieAdapter = new MovieAdapter();
        hotMovieRecycleView.setAdapter(movieAdapter);
        previewMovieRecycleView.setAdapter(previewMovieAdapter);
        getMovieBannerList();
        getHotMovieList();
        getPreviewMovieList();
        movieAdapter.setItemOnClick(new MovieAdapter.ItemOnClick() {
            @Override
            public void onClick(int type, int id) {
                int[] extra = {type, id};
                startActivity(new Intent(MovieActivity.this, MovieDetailsActivity.class).putExtra("id", extra));
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

    void getMovieBannerList() {
        apiService.getMovieBannerList().enqueue(new Callback<RowsResponse<HomeBannerData>>() {
            @Override
            public void onResponse(Call<RowsResponse<HomeBannerData>> call, Response<RowsResponse<HomeBannerData>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    banner.addBannerLifecycleObserver(MovieActivity.this)
                            .setIndicator(new CircleIndicator(MovieActivity.this))
                            .setAdapter(new HomeFragmentBannerAdapter(response.body().getRows()));
                } else {
                    Toast.makeText(MovieActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<HomeBannerData>> call, Throwable t) {
                Toast.makeText(MovieActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getHotMovieList() {
        apiService.getHotMovieList().enqueue(new Callback<RowsResponse<Movie>>() {
            @Override
            public void onResponse(Call<RowsResponse<Movie>> call, Response<RowsResponse<Movie>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    movieAdapter.setFilmData(response.body().getRows());
                } else {
                    Toast.makeText(MovieActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Movie>> call, Throwable t) {
                Toast.makeText(MovieActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getPreviewMovieList() {
        apiService.getPreviewMovieList().enqueue(new Callback<RowsResponse<Movie>>() {
            @Override
            public void onResponse(Call<RowsResponse<Movie>> call, Response<RowsResponse<Movie>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, "onResponse: response===>" + response.body().getRows().get(0));
                    previewMovieAdapter.setPreviewData(response.body().getRows());
                } else {
                    Toast.makeText(MovieActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<Movie>> call, Throwable t) {
                Toast.makeText(MovieActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


}