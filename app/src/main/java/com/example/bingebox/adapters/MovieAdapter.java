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
import com.example.bingebox.RVInterface;
import com.example.bingebox.api_service.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieDetails> movies;
    private RVInterface rvInterface;


    public MovieAdapter(List<MovieDetails> movies, RVInterface rvInterface) {
        this.movies = movies;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<MovieDetails> newMovies) {
        movies = newMovies != null ? newMovies : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        public MovieViewHolder(@NonNull View itemView, RVInterface rvInterface) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movieImage);
            titleTextView = itemView.findViewById(R.id.movieTitle);

            itemView.setOnClickListener(v -> {
                if (rvInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        rvInterface.onItemClick(pos);
                    }
                }
            });
        }

        public void bind(MovieDetails movie) {
            if (movie == null) return;
            
            titleTextView.setText(movie.getTitle() != null ? movie.getTitle() : "");
            if (movie.getImage() != null && movie.getImage().getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(movie.getImage().getImageUrl())
                        .placeholder(R.drawable.default_movie_poster)
                        .error(R.drawable.default_movie_poster)
                        .into(posterImageView);
            } else {
                posterImageView.setImageResource(R.drawable.default_movie_poster);
            }
        }
    }
}