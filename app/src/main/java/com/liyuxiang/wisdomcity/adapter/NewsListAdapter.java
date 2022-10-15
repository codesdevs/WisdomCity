package com.liyuxiang.wisdomcity.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.News;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    public static final String TAG = "NewsListAdapter";
    private List<News> newsList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News news = newsList.get(position);
        if (!TextUtils.isEmpty(news.getCover())) {
            Log.i(TAG, "onBindViewHolder: news.getCover()" + news.getCover());
            try {
                Glide.with(holder.cover)
                        .load("http://124.93.196.45:10001" + news.getCover())
                        .error(R.drawable.loading)
                        .thumbnail(Glide.with(holder.itemView)
                                .load(R.drawable.loading)) //加载缩略图
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.cover);
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: ", e);
            }
        } else {
            Glide.with(holder.itemView)
                    .load(R.drawable.loading)
                    .into(holder.cover);
        }
        holder.title.setText(news.getTitle());
        holder.content.setText(news.getContent().replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("&nbsp;", ""));
        holder.itemView.setOnClickListener(v -> {
            itemClickListener.click(news.getId());
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setData(List<News> data) {
        newsList.clear();
        newsList.addAll(data);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cover;
        private final TextView title, content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cover = itemView.findViewById(R.id.cover);
            this.title = itemView.findViewById(R.id.title);
            this.content = itemView.findViewById(R.id.content);
        }
    }

    public interface ItemClickListener {
        void click(int id);
    }


}
