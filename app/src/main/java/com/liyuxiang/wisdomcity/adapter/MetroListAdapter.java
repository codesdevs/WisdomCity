package com.liyuxiang.wisdomcity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.MetroList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MetroListAdapter extends RecyclerView.Adapter<MetroListAdapter.MyViewHolder> {

    private List<MetroList> metroLis = new ArrayList<>();

    public void setData(List<MetroList> data) {
        this.metroLis.addAll(data);
        notifyDataSetChanged();
    }

    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metro_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MetroList metroList = metroLis.get(position);
        holder.name.setText(metroList.getLineName());
        holder.time.setText(metroList.getReachTime() + "分钟");
        holder.nextName.setText("下一站：" + metroList.getNextStep().getName());
        holder.itemView.setOnClickListener(v->{
            itemOnClick.onClick(metroList.getLineId());
        });
    }

    @Override
    public int getItemCount() {
        return metroLis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, time, nextName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            nextName = itemView.findViewById(R.id.nextName);
        }
    }

    public interface ItemOnClick {
        void onClick(int id);
    }
}
