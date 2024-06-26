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

public class Home_dialog {
    private Context context;
    private View dialogView;
    private MovieDetails movie;
    private View_Model view_model;
    private AlertDialog dialog;

    Home_dialog(Context context, View dialogView, MovieDetails movie, View_Model view_model) {
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
        Button dialogAddToLibraryButton = dialogView.findViewById(R.id.dialogAddToLibraryButton);

        dialogMovieTitle.setText(movie.getTitle());
        dialogMovieYear.setText(movie.getYear());
        dialogMovieGenre.setText(movie.getType().toUpperCase());

        // Load image using Glide
        if (movie.getImage() != null && movie.getImage().getImageUrl() != null) {
            Glide.with(context)
                    .load(movie.getImage().getImageUrl())
                    .placeholder(R.drawable.default_movie_poster)
                    .error(R.drawable.default_movie_poster)
                    .into(dialogMovieImage);
        } else {
            dialogMovieImage.setImageResource(R.drawable.default_movie_poster);
        }

        builder.setView(dialogView);
        dialog = builder.create();
        dialogAddToLibraryButton.setOnClickListener(v -> {
            Entity_Movie entityMovie = new Entity_Movie(movie.getImage().getImageUrl(), movie.getTitle(), movie.getType(), movie.getYear(), "Plan To Watch");
            view_model.insertLB(entityMovie);
            dialog.dismiss();
            Toast.makeText(context, "Added to Library", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
}
