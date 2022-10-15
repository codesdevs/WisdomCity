package com.liyuxiang.wisdomcity.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.NewsCategory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.MyViewHolder> {
    public static final String TAG = "HomeNewsCategoryAdapter";
    private List<NewsCategory> newsCategoryList = new ArrayList<>();
    public static int currId = 0;
    private AdapterView.OnItemClickListener onItemClickListener;

    private ItemClickListener clickListener;

    public void setData(List<NewsCategory> data) {
        newsCategoryList.clear();
        newsCategoryList.addAll(data);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cacategory_item, parent, false);
        return new MyViewHolder(view);
    }
    public void setCurrId(int currId) {
        this.currId = currId;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        NewsCategory newsCategory = newsCategoryList.get(position);
        holder.title.setText(newsCategory.getName());
        View itemView = holder.itemView;
        if (position == currId) {
            holder.title.setTextSize(16);
//            holder.categoryTitle.getPaint().setFakeBoldText(true);
            holder.title.setTextColor(Color.BLACK);
            holder.bottomView.setBackgroundColor(Color.RED);
            holder.bottomView.setVisibility(View.VISIBLE);
        } else {
            holder.title.setTextSize(14);
//            holder.categoryTitle.getPaint().setFakeBoldText(false);
            holder.title.setTextColor(0xFF888888);
            holder.bottomView.setVisibility(View.GONE);
        }
        itemView.setOnClickListener(view -> {
            clickListener.click(newsCategory.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return newsCategoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final View bottomView;
        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bottomView = itemView.findViewById(R.id.newCategoryBottomView);
            this.title = itemView.findViewById(R.id.newCategoryTitle);
        }
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void click(int id, int position);
    }
}
