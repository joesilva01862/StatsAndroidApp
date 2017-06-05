package com.example.joe.model;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Joe on 6/2/2017.
 */

public interface GitHubService {
    @GET("{year}/{month}")
    Call getBody(@Path("year") String year, @Path("month") String month);

    @GET("/")
    Call getBodyFull();

}
