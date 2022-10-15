package com.liyuxiang.wisdomcity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {
    private List<Work> workList = new ArrayList<>();

    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public void setData(List<Work> data) {
        this.workList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Work work = workList.get(position);
        holder.name.setText(work.getName());
        holder.companyName.setText(work.getCompanyName());
        holder.salary.setText(work.getSalary());
        holder.address.setText(work.getAddress());
        holder.itemView.setOnClickListener(v -> {
            itemOnClick.onClick(work.getId());
        });
    }

    @Override
    public int getItemCount() {
        return workList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, companyName, salary, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            companyName = itemView.findViewById(R.id.companyName);
            salary = itemView.findViewById(R.id.salary);
            address = itemView.findViewById(R.id.address);
        }
    }
    public interface ItemOnClick{
        void onClick(int id);
    }
}
