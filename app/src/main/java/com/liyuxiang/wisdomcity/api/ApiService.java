package com.liyuxiang.wisdomcity.api;

import com.google.gson.JsonObject;
import com.liyuxiang.wisdomcity.commons.entity.*;
import com.liyuxiang.wisdomcity.commons.repsone.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {
    @POST("/prod-api/api/register")
    Call<ResponseBody> register(@Body UserRegister user);

    @GET("/prod-api/api/common/user/getInfo")
    @Headers({"Content-type:application/json;charset=UTF-8;", "isToken:true"})
    Call<UserResponse<User>> getLoginUserInfo();

    @POST("/prod-api/api/login")
    Call<ResponseBody> login(@Body UserLogin user);

    @GET("/prod-api/api/rotation/list")
    Call<RowsResponse<HomeBannerData>> getHomeBannerList();

    @GET("/prod-api/press/category/list")
    Call<DataResponse<List<NewsCategory>>> getNewsCategoryList();

    @GET("/prod-api/press/press/list")
    Call<RowsResponse<News>> getNewsListById(@Query("type") int type);

    @GET("/prod-api/api/service/list")
    Call<RowsResponse<Service>> getServiceList();

    //    @GET("/prod-api/press/comments/list")
//    Call<RowsResponse<NewsComments>> getNewsCommentsList(@Query("newsId") int id, @QueryMap Map<String, Integer> page);
    @GET("/prod-api/press/comments/list")
    Call<RowsResponse<NewsComments>> getNewsCommentsList(@Query("newsId") int id);

    @GET("/prod-api/press/press/{id}")
    Call<DataResponse<News>> getNewsDetail(@Path("id") int id);

    @PUT("/prod-api/api/common/user/resetPwd")
    @Headers({"Content-type:application/json;charset=UTF-8;", "isToken:true"})
    Call<ResponseBody> resetPwd(@Body Password pwd);

    @POST("/prod-api/api/common/feedback")
    @Headers({"Content-type:application/json;charset=UTF-8", "isToken:true"})
    Call<ResponseBody> feedback(@Body Feedback feedback);

    @GET("/prod-api/api/park/rotation/list")
    Call<RowsResponse<HomeBannerData>> getParkBannerList();

    @GET("/prod-api/api/park/lot/list")
    /**
     * 获取停车场列表
     */
    Call<RowsResponse<Parking>> getParkingList();

    @GET("/prod-api/api/job/post/list")
//    @Headers({"Content-type:application/json;charset=UTF-8;","isToken:true"})
    Call<RowsResponse<Work>> getWorkList();

    @GET("/prod-api/api/job/post/{id}")
    Call<DataResponse<WorkDetails>> getWorkDetails(@Path("id") int id);

    @GET("/prod-api/api/movie/rotation/list")
    Call<RowsResponse<HomeBannerData>> getMovieBannerList();

    @GET("/prod-api/api/movie/film/list")
    Call<RowsResponse<Movie>> getHotMovieList();
    @GET("/prod-api/api/movie/film/detail/{linesId}")
    Call<DataResponse<MovieDetails>> getHotMovieDetails(@Path("linesId") int id);

    @GET("/prod-api/api/movie/film/preview/list")
    Call<RowsResponse<Movie>> getPreviewMovieList();

    @GET("/prod-api/api/metro/rotation/list")
    Call<RowsResponse<HomeBannerData>> getMetroBannerList();

    @GET("/prod-api/api/takeout/rotation/list")
    Call<RowsResponse<HomeBannerData>> getTakeOutBannerList();

    @GET("/prod-api/api/metro/list")
    Call<DataResponse<List<MetroList>>> getMetroList(@Query("currentName") String currentName);

    @GET("/prod-api/api/metro/line/{id}")
    Call<DataResponse<MetroDetails>> getMetroDetailsList(@Path("id") int id);

    @GET("/prod-api/api/bus/line/list")
    Call<RowsResponse<Bus>> getBusList();

    @GET("/prod-api/api/bus/stop/list")
    Call<RowsResponse<BusStop>> getBusStopList(@Query("linesId") int id);
}
