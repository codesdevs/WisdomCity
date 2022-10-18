package com.liyuxiang.wisdomcity.activity.movie;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.BusStop;
import com.liyuxiang.wisdomcity.commons.entity.MovieDetails;
import com.liyuxiang.wisdomcity.commons.repsone.DataResponse;
import com.liyuxiang.wisdomcity.commons.repsone.RowsResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backBtn;
    private TextView top_title, name, likeNum, language, duration, playDate, introduction;
    private ImageView cover;
    int[] id;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle extras = getIntent().getExtras();
        id = extras.getIntArray("id");
        initView();
        initData();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        top_title = findViewById(R.id.top_title);
        cover = findViewById(R.id.cover);
        name = findViewById(R.id.name);
        likeNum = findViewById(R.id.likeNum);
        language = findViewById(R.id.language);
        duration = findViewById(R.id.duration);
        playDate = findViewById(R.id.playDate);
        introduction = findViewById(R.id.introduction);

        backBtn.setOnClickListener(this);
    }

    private void initData() {
        top_title.setText("影视详情");
        if (id[0] == 1) {
            getHotMovieDetails(id[1]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }

    private void getHotMovieDetails(int id) {
        apiService.getHotMovieDetails(id).enqueue(new Callback<DataResponse<MovieDetails>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DataResponse<MovieDetails>> call, Response<DataResponse<MovieDetails>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    MovieDetails movieDetails = response.body().getData();
                    Glide.with(MovieDetailsActivity.this)
                            .load("http://124.93.196.45:10001" + movieDetails.getCover())
                            .error(R.drawable.loading)
                            .into(cover);
                    name.setText(movieDetails.getName());
                    likeNum.setText(movieDetails.getLikeNum() + "人想看");
                    language.setText(movieDetails.getLanguage());
                    duration.setText(movieDetails.getDuration() + "分钟");
                    playDate.setText(movieDetails.getPlayDate() + "上映");
                    introduction.setText(Html.fromHtml(movieDetails.getIntroduction(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    Toast.makeText(MovieDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<MovieDetails>> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, "数据加载失败，网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}