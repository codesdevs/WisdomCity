package com.liyuxiang.wisdomcity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.commons.entity.HomeBannerData;
import com.youth.banner.adapter.BannerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragmentBannerAdapter extends BannerAdapter<HomeBannerData, HomeFragmentBannerAdapter.BannerViewHolder> {
    private List<HomeBannerData> dataList;
    private ItemOnclick itemOnclick;

    public void setItemOnclick(ItemOnclick itemOnclick) {
        this.itemOnclick = itemOnclick;
    }

    public HomeFragmentBannerAdapter(List<HomeBannerData> datas) {
        super(datas);
        this.dataList = datas;
    }

    @Override
    public HomeFragmentBannerAdapter.BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(HomeFragmentBannerAdapter.BannerViewHolder holder, HomeBannerData data, int position, int size) {
//        HomeBannerData homeBannerData = dataList.get(position);
//        holder.imageView.se(homeBannerData.getAdvImg());
        Glide.with(holder.imageView)
                .load("http://124.93.196.45:10001" + data.getAdvImg())
                .thumbnail(Glide.with(holder.itemView)
                        .load(R.drawable.loading)) //加载缩略图
                .error(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            itemOnclick.onClick(data.getTargetId());
        });

    }

    public interface ItemOnclick {
        void onClick(int id);
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull @NotNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}
