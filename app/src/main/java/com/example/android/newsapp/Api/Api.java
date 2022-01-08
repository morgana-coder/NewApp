package com.example.android.newsapp.Api;

import com.example.android.newsapp.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
   @GET("top-headlines")
    Call<News>getNews(
     @Query("country") String country,
     @Query("apikey") String apiKey
   );
}
