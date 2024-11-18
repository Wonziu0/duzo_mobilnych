package com.example.nasaphotoapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApiService {
    @GET("apod")
    Call<NasaPhoto> getApod(@Query("api_key") String api_key);

    @GET("apod")
    Call<NasaPhoto> getApodForDate(@Query("api_key") String apiKey, @Query("date") String date);

}
