package com.liyuxiang.wisdomcity.interceptor;


import android.util.Log;

import androidx.annotation.NonNull;

import com.liyuxiang.wisdomcity.utils.TokenUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liyuxiang
 * token拦截器
 */
public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = TokenUtil.checkIsHavaToken(chain.request());
        Log.i(TAG, "intercept: request=====>" + request.toString());
        Response response = chain.proceed(request);
        Log.i(TAG, "intercept: response====>" + response);
        return response;
    }


}
