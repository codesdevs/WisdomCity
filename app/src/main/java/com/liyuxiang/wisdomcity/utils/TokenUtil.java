package com.liyuxiang.wisdomcity.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.widget.Toast;
import com.liyuxiang.wisdomcity.MainActivity;
import com.liyuxiang.wisdomcity.activity.LoginActivity;
import okhttp3.Request;

public class TokenUtil {
    private static final String TAG = "TokenUtil";
    private static Context context = MyApplication.getContext();

    /**
     * 检测api是否需要token
     */
    public static Request checkIsHavaToken(Request request) {
        String isTokenStr = request.header("isToken");
        boolean isToken = false;
        if (isTokenStr != null) {
            isToken = true;
        }
        if (isToken) {
            Log.i(TAG, "checkToken: 需要token");
//            获取缓存中的token
            String token = getToken();
            Log.i(TAG, "checkToken: token====>" + token);
//            if (token.equals("")) {
////                Toast.makeText(context, "登录身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
            return request.newBuilder().addHeader("Authorization", token).build();
        }
        Log.i(TAG, "checkToken: 不需要token");
        return request;
    }

    public static boolean checkToken() {
        return getToken().equals("") ? false : true;
    }

    /**
     * 获取缓存中的token
     *
     * @return token
     */
    public static String getToken() {
        SPUtils spUtils = SPUtils.getInstance(context);
        String token = spUtils.getString("token", "");
        return token;
    }
}
