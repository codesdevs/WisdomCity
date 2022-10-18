package com.liyuxiang.wisdomcity.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.MetroDetails;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MetroDetailsAdapter extends RecyclerView.Adapter<MetroDetailsAdapter.MyViewHolder> {
    private List<MetroDetails.MetroStepListDTO> metroStepListDTOList = new ArrayList<>();

    public void setData(List<MetroDetails.MetroStepListDTO> data) {
        this.metroStepListDTOList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metro_details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MetroDetails.MetroStepListDTO metroStepListDTO = metroStepListDTOList.get(position);
        holder.stepName.setText(metroStepListDTO.getName());
//        if (position % 2 == 0) {
//            holder.lines.setBackgroundColor(Color.RED);
//        }
    }

    @Override
    public int getItemCount() {
        return metroStepListDTOList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stepName;
        ImageView lines;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.stepName);
        }
    }
}
