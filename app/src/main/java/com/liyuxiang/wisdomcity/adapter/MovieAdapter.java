package com.liyuxiang.wisdomcity.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.Movie;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private List<Movie> movieList = new ArrayList<>();
    private ItemOnClick itemOnClick;
    /**
     * 用来区分是否正在热映的电影
     */
    private boolean isFilm = true;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setFilmData(List<Movie> data) {
        this.movieList.addAll(data);
        this.isFilm = true;
        notifyDataSetChanged();
    }

    public void setPreviewData(List<Movie> data) {
        this.movieList.addAll(data);
        this.isFilm = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Glide.with(holder.itemView)
                .load("http://124.93.196.45:10001" + movie.getCover())
                .error(R.drawable.loading)
                .into(holder.cover);
        holder.name.setText(movie.getName());
        holder.buy.setOnClickListener(v -> {
            //如果isFilm==true那么点击的代表是正在放映
            itemOnClick.onClick(isFilm ? 1 : 0, movie.getId());
        });
        if (!isFilm) {
            holder.buy.setText("想看");
            holder.buy.setTextColor(Color.WHITE);
            holder.buy.setBackgroundColor(Color.rgb(255, 203, 30));
        }
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView name;
        private Button buy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            name = itemView.findViewById(R.id.name);
            buy = itemView.findViewById(R.id.buy);
        }
    }

    public interface ItemOnClick {
        //type:0正在放映    1预告
        void onClick(int type, int id);
    }
}
