package com.liyuxiang.wisdomcity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyViewHolder> {
    private List<Bus> busList = new ArrayList<>();
    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setData(List<Bus> data) {
        this.busList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.name.setText(bus.getName());
        holder.price.setText("¥ " + bus.getPrice());
        holder.first.setText("起始站：" + bus.getFirst());
        holder.end.setText("终点站：" + bus.getEnd());
        holder.startTime.setText(bus.getStartTime());
        holder.endTime.setText(bus.getEndTime());
        holder.itemView.setOnClickListener(v -> {
            itemOnClick.onClick(bus.getId());
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, price, first, end, startTime, endTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            first = itemView.findViewById(R.id.first);
            end = itemView.findViewById(R.id.end);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
        }
    }

    public interface ItemOnClick {
        void onClick(int id);
    }
}
