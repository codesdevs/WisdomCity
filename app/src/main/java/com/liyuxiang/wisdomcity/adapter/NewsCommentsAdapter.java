package com.liyuxiang.wisdomcity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.NewsComments;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsCommentsAdapter extends RecyclerView.Adapter<NewsCommentsAdapter.MyViewHolder> {
    private List<NewsComments> newsCommentsList = new ArrayList<>();

    public void setData(List<NewsComments> newsCommentsList) {
        Collections.reverse(newsCommentsList);
        this.newsCommentsList.addAll(newsCommentsList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_user_comments_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsComments comments = newsCommentsList.get(position);
        holder.name.setText(comments.getUserName());
        holder.content.setText(comments.getContent());
        holder.date.setText(comments.getCommentDate());
//        Glide.with(holder.itemView).load("http://124.93.196.45:10001")
    }

    @Override
    public int getItemCount() {
        return newsCommentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, content, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }
}
