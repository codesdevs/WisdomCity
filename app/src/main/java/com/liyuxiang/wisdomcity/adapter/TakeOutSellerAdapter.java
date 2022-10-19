package com.liyuxiang.wisdomcity.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.Seller;

import java.util.ArrayList;
import java.util.List;

public class TakeOutSellerAdapter extends RecyclerView.Adapter<TakeOutSellerAdapter.MyViewHolder> {
    List<Seller> sellerList = new ArrayList<>();


    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    ItemOnClick itemOnClick;

    public void setData(List<Seller> data) {
        this.sellerList.clear();
        this.sellerList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Seller seller = sellerList.get(position);
        Glide.with(holder.itemView)
                .load("http://124.93.196.45:10001" + seller.getImgUrl())
                .thumbnail(Glide.with(holder.itemView)
                        .load(R.drawable.loading))
                .error(R.drawable.loading)
                .into(holder.cover);
        holder.name.setText(seller.getName());
        holder.score.setText(seller.getScore().toString());
        holder.saleQuantity.setText("月售：" + seller.getSaleQuantity());
        holder.distance.setText("距离：" + seller.getDistance().toString());
        holder.avgCost.setText("人均：" + seller.getAvgCost().toString());
        if (seller.getIntroduction() != null) {
            holder.introduction.setText(seller.getIntroduction());
        } else {
            holder.introduction.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            itemOnClick.onClick(seller.getId());
        });
    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView name, score, saleQuantity, distance, avgCost, introduction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
            saleQuantity = itemView.findViewById(R.id.saleQuantity);
            distance = itemView.findViewById(R.id.distance);
            avgCost = itemView.findViewById(R.id.avgCost);
            introduction = itemView.findViewById(R.id.introduction);
        }
    }

    public interface ItemOnClick {
        void onClick(int id);
    }
}