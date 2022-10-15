package com.liyuxiang.wisdomcity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.liyuxiang.wisdomcity.R;
import com.liyuxiang.wisdomcity.activity.ChangePasswordActivity;
import com.liyuxiang.wisdomcity.activity.FeedbackActivity;
import com.liyuxiang.wisdomcity.activity.LoginActivity;
import com.liyuxiang.wisdomcity.activity.UserInfoSettingActivity;
import com.liyuxiang.wisdomcity.api.ApiService;
import com.liyuxiang.wisdomcity.commons.entity.User;
import com.liyuxiang.wisdomcity.commons.repsone.UserResponse;
import com.liyuxiang.wisdomcity.utils.RetrofitManager;
import com.liyuxiang.wisdomcity.utils.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {
    private ImageView avatar;
    private TextView name;
    private Button logout, loginBtn;
    private View userSetting, changePassword, feedback;
    ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);

    @Override
    protected int initLayout() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void initView() {
        avatar = mContent.findViewById(R.id.avatar);
        name = mContent.findViewById(R.id.name);
        userSetting = mContent.findViewById(R.id.userSetting);
        userSetting.setOnClickListener(this);
        changePassword = mContent.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(this);
        feedback = mContent.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);
        logout = mContent.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        loginBtn = mContent.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getLoginUserInfo();
    }

    public void getLoginUserInfo() {
        apiService.getLoginUserInfo().enqueue(new Callback<UserResponse<User>>() {
            @Override
            public void onResponse(Call<UserResponse<User>> call, Response<UserResponse<User>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body().getCode() == HttpURLConnection.HTTP_OK) {
                    showToast(response.body().getMsg());
                    User user = response.body().getUser();
                    Glide.with(requireView()).load("http://124.93.196.45:10001" + user.getAvatar())
                            .error(R.drawable.img_2)
                            .into(avatar);
                    name.setText(user.getNickName());
                    loginBtn.setVisibility(View.GONE);
                } else {
                    if (response.body().getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        logout.setVisibility(View.GONE);
                        avatar.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse<User>> call, Throwable t) {
                showToast("数据加载失败，网络异常");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                logout.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
                avatar.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                SPUtils.getInstance(requireContext()).remove("token");
                break;
            case R.id.userSetting:
                jumpPage(new Intent(requireActivity(), UserInfoSettingActivity.class));
                break;
            case R.id.changePassword:
                jumpPage(new Intent(requireActivity(), ChangePasswordActivity.class));
                break;
            case R.id.feedback:
                jumpPage(new Intent(requireActivity(), FeedbackActivity.class));
                break;
            case R.id.loginBtn:
                jumpPage(new Intent(requireActivity(), LoginActivity.class));
                break;
        }
    }
}