package com.example.bingebox.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.repository.MovieRepository;

public class View_Model extends AndroidViewModel{
    private MovieRepository movieRepository;
    public View_Model(Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
    }

    public LiveData<List<MovieDetails>> getMovieDetails(String query){
        return movieRepository.getMutableLiveData(query);
    }
    public void insertLB(Entity_Movie entityClass){
        movieRepository.insertLB(entityClass);
    }
    public void updateLB(Entity_Movie entityClass){
        movieRepository.updateLB(entityClass);
    }
    public void deleteLB(Entity_Movie entityClass){
        movieRepository.deleteLB(entityClass);
    }
    public LiveData<List<Entity_Movie>> getLibMovies(){
        return movieRepository.getLibMovieDetails();
    }
}
