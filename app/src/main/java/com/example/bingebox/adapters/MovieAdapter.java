package com.example.bingebox.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bingebox.R;
import com.example.bingebox.api_service.MovieDetails;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieDetails> movies;

    public MovieAdapter(List<MovieDetails> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDetails movie = movies.get(position);
        holder.movieTitle.setText(movie.getL());

        String imageUrl = null;
        if (movie.getI() != null) {
            imageUrl = movie.getI().getImageUrl();
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.default_movie_poster) // Add a placeholder image
                    .error(R.drawable.default_movie_poster) // Add an error image
                    .into(holder.movieImage);
        } else {
            // Load default image if no URL is available
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_movie_poster)
                    .into(holder.movieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<MovieDetails> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;

        MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
        }
    }
}