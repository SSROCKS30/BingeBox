package com.example.bingebox;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class View_Model extends AndroidViewModel{
    private MovieRepository movieRepository;
    public View_Model(Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
    }

    public LiveData<List<MovieDetails>> getMovieDetails(String query){
        return movieRepository.getMutableLiveData(query);
    }
}
