package com.liyuxiang.wisdomcity.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.NewsCommentsAdapter;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.News;
import com.liyuxiang.wisdomcity.commons.entity.NewsComments;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "NewsDetailsActivity";

    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);
    RecyclerView commentsRecycleView;
    NewsCommentsAdapter newsCommentsAdapter;
    private ImageButton backBtn;
    private TextView topTitle, title, content, author, date;
    private ImageView cover;
    private Integer total = 0;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initView();
        initData();
    }

    private void initData() {
        newsCommentsAdapter = new NewsCommentsAdapter();
        commentsRecycleView.setLayoutManager(new LinearLayoutManager(NewsDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        commentsRecycleView.setAdapter(newsCommentsAdapter);
        Bundle extras = getIntent().getExtras();
        id = (int) extras.get("id");
        Log.i(TAG, "initData: id===>" + id);
        topTitle.setText("");
        getNewsDetail(id);
        Map<String, Integer> map = new HashMap<>();
        map.put("pageNum", null);
        map.put("pageSize", null);
        getNewsCommentsList(map, id);
    }

    private void initView() {
        commentsRecycleView = findViewById(R.id.commentsRecycleView);
//        commentsRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
////                if (dy > 0) {
//                //是否滑到底部
//                if (!recyclerView.canScrollVertically(1)) {
//                    //相应处理操作
//                    Map<String, Integer> map = new HashMap<>();
//                    map.put("pageNum", 2);
//                    map.put("pageSize", 10);
//                    getNewsCommentsList(map, id);
//                }
////                }
//            }
//        });
        topTitle = findViewById(R.id.top_title);
        backBtn = findViewById(R.id.backBtn);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        date = findViewById(R.id.date);
        content = findViewById(R.id.content);
        backBtn.setOnClickListener(this);
        cover = findViewById(R.id.cover);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                NewsDetailsActivity.this.finish();
                break;
        }
    }

    public void getNewsDetail(int id) {
        apiService.getNewsDetail(id).enqueue(new Callback<DataResponse<News>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DataResponse<News>> call, Response<DataResponse<News>> response) {
                News data = response.body().getData();
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, "onResponse: response" + response.body());
                    content.setText(Html.fromHtml(data.getContent(), Html.FROM_HTML_MODE_COMPACT));
                    title.setText(data.getTitle());
                    date.setText(data.getCreateTime());
                    if (!TextUtils.isEmpty(data.getCover())) {
                        Log.i(TAG, "onBindViewHolder: news.getCover()" + data.getCover());
                        try {
                            Glide.with(NewsDetailsActivity.this)
                                    .load("http://124.93.196.45:10001" + data.getCover())
                                    .transform(new RoundedCorners(10))
                                    .error(R.drawable.loading)
                                    .into(cover);
                        } catch (Exception e) {
                            Log.e(TAG, "onBindViewHolder: ", e);
                        }
                    } else {
                        Glide.with(NewsDetailsActivity.this)
                                .load(R.drawable.loading)
                                .transform(new RoundedCorners(10))
                                .error(R.drawable.loading)
                                .into(cover);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse<News>> call, Throwable t) {

            }
        });
    }

    public void getNewsCommentsList(Map<String, Integer> page, int id) {
        apiService.getNewsCommentsList(id).enqueue(new Callback<RowsResponse<NewsComments>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<RowsResponse<NewsComments>> call, Response<RowsResponse<NewsComments>> response) {
                Log.i(TAG, "onResponse: response===>");
                response.body().getRows().forEach(t -> {
                    System.out.println(t.toString());
                });
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    total = response.body().getTotal();
                    newsCommentsAdapter.setData(response.body().getRows());
                } else {
                    Toast.makeText(NewsDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RowsResponse<NewsComments>> call, Throwable t) {
                Toast.makeText(NewsDetailsActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


}