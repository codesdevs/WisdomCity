package com.liyuxiang.wisdomcity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.adapter.GuideAdapter;

public class GuideActivity extends AppCompatActivity {
    private int[] imgArr = {R.drawable.guide, R.drawable.guide, R.drawable.guide, R.drawable.guide, R.drawable.guide};
    private String[] guideTitles = {"便民出行", "城市地铁", "门诊预约", "智慧交通", "生活缴费"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager = findViewById(R.id.guideViewPager);
        GuideAdapter adapter = new GuideAdapter(imgArr, guideTitles,GuideActivity.this);
        adapter.setItemClickListener(new GuideAdapter.ItemClickListener() {
            @Override
            public void click(View id) {

            }
        });
        viewPager.setAdapter(adapter);
    }
}