package com.liyuxiang.wisdomcity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liyuxiang.wisdomcity.R;
import org.jetbrains.annotations.NotNull;

public abstract class BaseFragment extends Fragment {

    public View mContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContent == null) {
            mContent = inflater.inflate(initLayout(), container, false);
            initView();
        }
        return mContent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String msg) {
//        requireActivity().runOnUiThread(() -> {
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//        });
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void jumpPage(Intent intent){
        startActivity(intent);
    }


}