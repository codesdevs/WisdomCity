package com.liyuxiang.wisdomcity.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.Parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.MyViewHolder> {
    List<Parking> parkingList = new ArrayList<>();

    public void setData(List<Parking> data) {
        this.parkingList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Parking parking = parkingList.get(position);
        holder.name.setText(parking.getParkName());
        holder.address.setText(parking.getAddress());
        holder.distance.setText(parking.getDistance());
        holder.open.setText(parking.getOpen().equals("Y") ? "是" : "否");
        holder.allPark.setText(parking.getAllPark());
        holder.vacancy.setText(parking.getVacancy());
        holder.rates.setText(parking.getRates());
        try {
            Glide.with(holder.itemView)
                    .load("http://124.93.196.45:10001" + parking.getImgUrl())
                    .error(R.drawable.parking_cover)
                    .into(holder.cover);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return parkingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView name, address, distance, open, allPark, vacancy, rates;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);
            open = itemView.findViewById(R.id.open);
            allPark = itemView.findViewById(R.id.allPark);
            vacancy = itemView.findViewById(R.id.vacancy);
            rates = itemView.findViewById(R.id.rates);
        }
    }
}
