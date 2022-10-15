package com.liyuxiang.wisdomcity.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.GuideActivity;
import com.liyuxiang.wisdomcity.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    private List<View> viewList = new ArrayList<>();
    public static final String TAG = "GuideAdapter";
    ItemClickListener clickListener;

    public GuideAdapter(int[] imgArr, String[] guideTitles, Context mContext) {
        for (int i = 0; i < imgArr.length; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.guide_item, null);
            ImageView imageView = view.findViewById(R.id.guide_img);
            imageView.setImageResource(imgArr[i]);
            RadioGroup radioGroup = view.findViewById(R.id.rg_indicate);
            Button netWorkBtn = view.findViewById(R.id.netWorkSetting);
            Button enterBtn = view.findViewById(R.id.enterBtn);
            TextView guideTitle = view.findViewById(R.id.guideTitle);
            guideTitle.setText(guideTitles[i]);
            Intent intent = new Intent(mContext, LoginActivity.class);
            enterBtn.setOnClickListener(v -> {
                mContext.startActivity(intent);
                GuideActivity guideActivity = (GuideActivity) mContext;
                guideActivity.finish();
                Toast.makeText(guideActivity, "欢迎进入智慧城市", Toast.LENGTH_SHORT).show();
            });
            for (int j = 0; j < imgArr.length; j++) {
                RadioButton radioButton = new RadioButton(mContext);
                radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setPadding(10, 10, 10, 10);
                radioButton.setOnCheckedChangeListener((v, t) -> {
                    Log.i(TAG, "GuideAdapter: " + v);
                });
                radioButton.setOnClickListener((v) -> {
//                    clickListener.click(v);
                    int i1 = radioGroup.indexOfChild(view.findViewById(v.getId()));
                    Log.i(TAG, "GuideAdapter: i1===>" + i1);
                });
                radioGroup.addView(radioButton);
            }
            ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
            if (i == imgArr.length - 1) {
                netWorkBtn.setVisibility(View.VISIBLE);
                enterBtn.setVisibility(View.VISIBLE);
            }
            viewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.i(TAG, "instantiateItem: position===>" + position);
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }

    public void setItemClickListener(GuideAdapter.ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void click(View id);
    }
}
