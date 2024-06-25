package com.example.bingebox.api_service;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("d")
    @Expose
    private List<MovieDetails> movieDetails;

    public List<MovieDetails> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MovieDetails> movieDetails) {
        this.movieDetails = movieDetails;
    }

}