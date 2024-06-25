package com.example.bingebox.api_service;

import com.example.bingebox.api_service.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    @GET("auto-complete")
    Call<ApiResponse> getAutoComplete(
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost,
            @Query("q") String query
    );
}
