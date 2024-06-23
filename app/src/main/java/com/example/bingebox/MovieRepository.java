package com.example.bingebox;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final Application application;
    private List<MovieDetails> movieDetails = new ArrayList<>();
    private MutableLiveData<List<MovieDetails>> mutableLiveData = new MutableLiveData<>();

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<MovieDetails>> getMutableLiveData(String query) {
        ApiService apiService = RetrofitClient.getClient();
        Call<ApiResponse> call = apiService.getAutoComplete(application.getApplicationContext().getString(R.string.api_key), application.getApplicationContext().getString(R.string.api_host), query);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null && apiResponse.getMovieDetails() != null) {
                    movieDetails = (List<MovieDetails>) apiResponse.getMovieDetails();
                    mutableLiveData.setValue(movieDetails);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
