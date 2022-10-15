package com.liyuxiang.wisdomcity.utils;

import com.liyuxiang.wisdomcity.interceptor.TokenInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new TokenInterceptor())
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://124.93.196.45:10001")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }


}
