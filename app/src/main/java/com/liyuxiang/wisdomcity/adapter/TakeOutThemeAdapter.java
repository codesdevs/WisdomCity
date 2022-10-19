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
import com.liyuxiang.wisdomcity.commons.entity.TakeOutTheme;

import java.util.ArrayList;
import java.util.List;

public class TakeOutThemeAdapter extends RecyclerView.Adapter<TakeOutThemeAdapter.MyViewHolder> {
    List<TakeOutTheme> takeOutThemeList = new ArrayList<>();

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    ItemOnClick itemOnClick;

    public void setData(List<TakeOutTheme> data) {
        this.takeOutThemeList.clear();
        this.takeOutThemeList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TakeOutThemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeOutThemeAdapter.MyViewHolder holder, int position) {
        TakeOutTheme takeOutTheme = takeOutThemeList.get(position);
        Glide.with(holder.itemView)
                .load("http://124.93.196.45:10001" + takeOutTheme.getImgUrl())
                .error(R.drawable.loading)
                .into(holder.icon);
        holder.title.setText(takeOutTheme.getThemeName());
        holder.itemView.setOnClickListener(v -> {
            itemOnClick.onClick(takeOutTheme.getId());
        });
    }

    @Override
    public int getItemCount() {
        return takeOutThemeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface ItemOnClick {
        void onClick(int id);
    }
}