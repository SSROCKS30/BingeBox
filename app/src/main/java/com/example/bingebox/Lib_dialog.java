package com.example.bingebox;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.viewmodel.View_Model;

public class Lib_dialog {
    private Context context;
    private View dialogView;
    private Entity_Movie movie;
    private View_Model view_model;
    private AlertDialog dialog;

    Lib_dialog(Context context, View dialogView, Entity_Movie movie, View_Model view_model) {
        this.context = context;
        this.dialogView = dialogView;
        this.movie = movie;
        this.view_model = view_model;
    }

    public void display_dialog_box() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ImageView dialogMovieImage = dialogView.findViewById(R.id.dialogMovieImage);
        TextView dialogMovieTitle = dialogView.findViewById(R.id.dialogMovieTitle);
        TextView dialogMovieYear = dialogView.findViewById(R.id.dialogMovieYear);
        TextView dialogMovieGenre = dialogView.findViewById(R.id.dialogMovieGenre);
        TextView dialogMovieStatus = dialogView.findViewById(R.id.dialogMovieStatus);
        ImageView dialogMovieStatusIcon = dialogView.findViewById(R.id.dialogMovieRating);
        Button dialogchangeMovieStatusButton = dialogView.findViewById(R.id.changeMovieStatus);
        Button dialogMovieReviewButton = dialogView.findViewById(R.id.movie_review);

        dialogMovieTitle.setText(movie.getTitle());
        dialogMovieYear.setText(movie.getYear());
        dialogMovieGenre.setText(movie.getType().toUpperCase());
        dialogMovieStatus.setText("STATUS: " + movie.getStatus());

        // Load image using Glide
        if (movie.getImgUrl() != null && movie.getImgUrl() != null) {
            Glide.with(context)
                    .load(movie.getImgUrl())
                    .placeholder(R.drawable.default_movie_poster)
                    .error(R.drawable.default_movie_poster)
                    .into(dialogMovieImage);
        } else {
            dialogMovieImage.setImageResource(R.drawable.default_movie_poster);
        }

        builder.setView(dialogView);
        dialog = builder.create();
        dialogchangeMovieStatusButton.setOnClickListener(v -> {
            if (movie.getStatus().equals("Plan To Watch")) {
                movie.setStatus("Watching");
            } else if (movie.getStatus().equals("Watching")) {
                movie.setStatus("Completed");
            } else if (movie.getStatus().equals("Completed")) {
                movie.setStatus("Dropped");
            } else if (movie.getStatus().equals("Dropped")) {
                movie.setStatus("Plan To Watch");
            }
            dialogMovieStatus.setText("STATUS:  " + movie.getStatus());
            view_model.updateLB(movie);
        });

        dialog.show();
    }
}
