package com.awesome.vicky.newsinfinite.util;

import com.awesome.vicky.newsinfinite.pojo.Search;
import com.awesome.vicky.newsinfinite.pojoSearch.Main2;
import com.awesome.vicky.newsinfinite.pojoSections.Main;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class RetrofitApi {

    public static final String baseUrl = "http://content.guardianapis.com/";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceInstance() {
        return getRetrofitInstance().create(ApiInterface.class);
    }

    public interface ApiInterface {
        @GET("search")
        Call<Search> search(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("technology")
        Call<Main> technology(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("sport")
        Call<Main> sport(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("politics")
        Call<Main> politics(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("travel")
        Call<Main> travel(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("world")
        Call<Main> world(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("business")
        Call<Main> business(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("education")
        Call<Main> education(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("film")
        Call<Main> film(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("football")
        Call<Main> football(
                @Query("api-key") String api_key,
                @Query("page") String page
        );

        @GET("search")
        Call<Main2> actualSearch(
                @Query("q") String q,
                @Query("api-key") String api_key,
                @Query("page") String page
        );
    }
}
