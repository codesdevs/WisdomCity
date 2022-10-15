package com.liyuxiang.wisdomcity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.liyuxiang.wisdomcity.activity.AllServiceFragment;
import com.liyuxiang.wisdomcity.adapter.BottomNavFragmentViewPageAdapter;
import com.liyuxiang.wisdomcity.fragment.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavView;
    private List<Fragment> fragmentList;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        viewPager = (ViewPager2) findViewById(R.id.viewPager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new AllServiceFragment());
        fragmentList.add(new NewsFragment());
        fragmentList.add(new PersonalCenterFragment());
        BottomNavFragmentViewPageAdapter adapter = new BottomNavFragmentViewPageAdapter(this, fragmentList);
        //禁止左右翻页
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(adapter);
    }

    private void setListener() {
        //viewPager翻页改变底部按钮选中状态
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                menuItem = bottomNavView.getMenu().getItem(position);
//                menuItem.setChecked(true);
//            }
//        });
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.allService:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.news:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.personalCenter:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }
}