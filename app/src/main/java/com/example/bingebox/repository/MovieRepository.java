package com.example.bingebox.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bingebox.api_service.ApiResponse;
import com.example.bingebox.api_service.ApiService;
import com.example.bingebox.BuildConfig;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.api_service.RetrofitClient;
import com.example.bingebox.database.MovieDB;
import com.example.bingebox.database.MovieDao;
import com.example.bingebox.database.Entity_Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private List<MovieDetails> movieDetails = new ArrayList<>();
    private MutableLiveData<List<MovieDetails>> mutableLiveData = new MutableLiveData<>();
    private final MovieDao movieDao;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public MovieRepository(Application application) {
        MovieDB database = MovieDB.getInstance(application);
        this.movieDao = database.movieDao();
    }

    public MutableLiveData<List<MovieDetails>> getMutableLiveData(String query) {
        ApiService apiService = RetrofitClient.getClient();
        Call<ApiResponse> call = apiService.getAutoComplete(BuildConfig.API_KEY, BuildConfig.API_HOST, query);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null && apiResponse.getMovieDetails() != null) {
                    movieDetails = apiResponse.getMovieDetails();
                    mutableLiveData.setValue(movieDetails);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                mutableLiveData.setValue(new ArrayList<>());
            }
        });
        return mutableLiveData;
    }

    public void insertLB(Entity_Movie entityClass) {
        executor.execute(() -> {
            movieDao.insert(entityClass);
        });
    }

    public void updateLB(Entity_Movie entityClass) {
        executor.execute(() -> movieDao.update(entityClass));
    }

    public void deleteLB(Entity_Movie entityClass) {
        executor.execute(() -> movieDao.delete(entityClass));
    }

    public LiveData<List<Entity_Movie>> getLibMovieDetails() {
        return movieDao.getAllMovies();
    }
}
