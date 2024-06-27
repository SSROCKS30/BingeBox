package com.example.bingebox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bingebox.api_service.MovieDetails;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> lastSearchQuery = new MutableLiveData<>();
    private MutableLiveData<List<MovieDetails>> lastMovieResults = new MutableLiveData<>();

    public void setLastSearchQuery(String query) {
        lastSearchQuery.setValue(query);
    }

    public LiveData<String> getLastSearchQuery() {
        return lastSearchQuery;
    }

    public void setLastMovieResults(List<MovieDetails> movies) {
        lastMovieResults.setValue(movies);
    }

    public LiveData<List<MovieDetails>> getLastMovieResults() {
        return lastMovieResults;
    }
}