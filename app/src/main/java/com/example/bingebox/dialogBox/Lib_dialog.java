package com.example.bingebox.dialogBox;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bingebox.R;
import com.example.bingebox.api_service.MovieDetails;
import com.example.bingebox.database.Entity_Movie;
import com.example.bingebox.viewmodel.View_Model;

import java.util.Arrays;
import java.util.List;

public class Lib_dialog {
    private Context context;
    private View dialogView;
    private Entity_Movie movie;
    private View_Model view_model;
    private AlertDialog dialog;
    private Spinner dialogchangeMovieStatus;
    private List<String> statusOptions = Arrays.asList("Plan To Watch", "Dropped", "Watching", "Completed");

    public Lib_dialog(Context context, View dialogView, Entity_Movie movie, View_Model view_model) {
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
        ImageView dialogMovieStatusIcon = dialogView.findViewById(R.id.dialogMovieRating);
        dialogchangeMovieStatus = dialogView.findViewById(R.id.statusSpinner);
        Button dialogMovieReviewButton = dialogView.findViewById(R.id.movie_review);

        dialogMovieTitle.setText(movie.getTitle());
        dialogMovieYear.setText(movie.getYear());
        dialogMovieGenre.setText(movie.getType().toUpperCase());

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


        dialog.show();
        setupStatusSpinner();
    }
    private void setupStatusSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogchangeMovieStatus.setAdapter(adapter);

        // Set the initial selection
        int initialPosition = statusOptions.indexOf(movie.getStatus());
        if (initialPosition != -1) {
            dialogchangeMovieStatus.setSelection(initialPosition);
        }

        dialogchangeMovieStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = statusOptions.get(position);
                if (!selectedStatus.equals(movie.getStatus())) {
                    movie.setStatus(selectedStatus);
                    // Update the movie in the database
                    view_model.updateLB(movie);
                    Toast.makeText(context, "Status updated to: " + selectedStatus, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
